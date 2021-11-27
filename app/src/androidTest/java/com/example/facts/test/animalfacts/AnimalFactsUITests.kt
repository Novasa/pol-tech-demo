package com.example.facts.test.animalfacts

import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.catfacts.model.CatFact
import com.example.catfacts.model.CatFactsResponse
import com.example.catfacts.model.CatUser
import com.example.catfacts.service.CatFactsService
import com.example.dogfacts.model.DogFact
import com.example.dogfacts.service.DogFactsService
import com.example.facts.R
import com.example.facts.app.activity.MainActivity
import com.example.facts.databinding.DataBindingComponentImplementation
import com.example.facts.injection.TestServiceModule
import com.example.facts.matcher.ViewMatchers
import com.example.facts.matcher.ViewMatchers.withType
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import kotlinx.coroutines.delay
import org.hamcrest.core.AllOf.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class AnimalFactsUITests {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Inject
    lateinit var dataBindingComponent: DataBindingComponentImplementation

    /** These are mocks defined in the [TestServiceModule] */
    @Inject
    lateinit var dogFactsService: DogFactsService

    @Inject
    lateinit var catFactsService: CatFactsService

    @Before
    fun inject() {
        hiltRule.inject()
        DataBindingUtil.setDefaultComponent(dataBindingComponent)
    }

    private fun navigateToAnimalFacts() {

        // Note: static method imports to remove clutter
        onView(
            // We have to uniquely define the view we want to click, and since it has no known ID, we need to find it another way
            allOf(
                // Child view of bottom navigation view
                isDescendantOfA(withId(R.id.mainBottomNavigation)),

                // Has the text of the menu option we want
                withText(R.string.main_page_animal_facts),

                // Is currently displayed.
                // Required because there are 2 text views with the text in the bottom navigation view,
                // one large for when the option is selected, and a smaller one when not
                isDisplayed()
            )
        ).perform(click())
    }

    @Test
    fun displayAnimalFacts() {

        // Mock the required service functions to return mock data
        // coEvery for suspend functions
        coEvery { dogFactsService.getDogFacts(any()) } returns listOf(
            DogFact("Test dog fact 1")
        )

        coEvery { catFactsService.getCatFacts(any(), any()) } returns CatFactsResponse(
            listOf(
                CatFact("Test cat fact 1", 100)
            )
        )

        navigateToAnimalFacts()

        fun onCellWithText(text: String) = onView(
            allOf(
                isDescendantOfA(withId(R.id.animalFactsRecyclerView)),
                withText(text)
            )
        )

        onCellWithText("Test dog fact 1")
            .check(matches(isDisplayed()))

        onCellWithText("Test cat fact 1")
            .check(matches(isDisplayed()))
    }

    /**
     * Here we mock the backend logic of the cat facts api, to change the returned user when it is updated
     */
    @Test
    fun testUpdateCatUser() {
        coEvery { catFactsService.getCatUser() } returns CatUser("Test name")

        coEvery { catFactsService.updateCatUser(any()) } answers {
            // "answers" is similar to "returns", but allows for this lambda to be executed when the function is called
            // "firstArg" refers to the argument that "updateCatUser" is called with
            coEvery { catFactsService.getCatUser() } returns firstArg()
        }

        navigateToAnimalFacts()

        onView(withId(R.id.animalFactsFabUser))
            .perform(click())

        onView(withId(R.id.catUserName))
            .check(matches(allOf(isDisplayed(), withText("Test name"))))

        onView(withId(R.id.catUserFabEdit))
            .perform(click())

        // Custom view matcher to check for the view type
        onView(withType(EditText::class.java))
            .inRoot(isDialog())
            .perform(click())
            .perform(typeText("New test name"))

        onView(withText("OK"))
            .inRoot(isDialog())
            .perform(click())

        // We expect the ui to automatically update when we edit the name
        onView(withId(R.id.catUserName))
            .check(matches(withText("New test name")))
    }
}
