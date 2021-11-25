package com.example.facts.test.animalfacts

import androidx.databinding.DataBindingUtil
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.catfacts.model.CatFact
import com.example.catfacts.model.CatFactsResponse
import com.example.catfacts.service.CatFactsService
import com.example.dogfacts.model.DogFact
import com.example.dogfacts.service.DogFactsService
import com.example.facts.R
import com.example.facts.app.activity.MainActivity
import com.example.facts.databinding.DataBindingComponentImplementation
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import org.hamcrest.core.AllOf.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class AnimalFactsUITests {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Inject
    lateinit var dataBindingComponent: DataBindingComponentImplementation

    /** These are mocks defined in the [com.example.facts.injection.TestServiceModule] */
    @Inject
    lateinit var dogFactsService: DogFactsService

    @Inject
    lateinit var catFactsService: CatFactsService

    @Before
    fun inject() {
        hiltRule.inject()
        DataBindingUtil.setDefaultComponent(dataBindingComponent)
    }

    @Test
    fun displayAnimalFacts() {

        // coEvery for suspend functions
        coEvery { dogFactsService.getDogFacts(any()) } answers {
            listOf(
                DogFact("Test hund 1")
            )
        }

        coEvery { catFactsService.getCatFacts(any(), any()) } answers {
            CatFactsResponse(
                listOf(
                    CatFact("Test kat 1", 100)
                )
            )
        }

        // Note: static methods imports to remove clutter
        onView(
            allOf(
                // Child view of bottom navigation view
                isDescendantOfA(withId(R.id.mainBottomNavigation)),

                // Has the text of the menu option we want
                withText(R.string.main_page_animal_facts),

                // Is currently displayed.
                // Required because there are 2 text views with the text, one large for when the option is selected, and a smaller one when not
                isDisplayed()
            )
        ).perform(click())

        fun onCellWithText(text: String) = onView(
            allOf(
                isDescendantOfA(withId(R.id.animalFactsRecyclerView)),
                withText(text)
            )
        )

        onCellWithText("Test hund 1")
            .check(matches(isDisplayed()))

        onCellWithText("Test kat 1")
            .check(matches(isDisplayed()))
    }
}
