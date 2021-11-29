package com.example.facts.test.animalfacts

import androidx.databinding.DataBindingUtil
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.base.IdlingResourceRegistry
import androidx.test.espresso.idling.CountingIdlingResource
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.catfacts.model.CatFactsResponse
import com.example.catfacts.service.CatFactsService
import com.example.dogfacts.model.DogFact
import com.example.dogfacts.service.DogFactsService
import com.example.facts.app.activity.MainActivity
import com.example.facts.databinding.DataBindingComponentImplementation
import com.example.facts.test.navigator.MainNavigator
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class AnimalFactsIdlingResourceTests {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Inject
    lateinit var dataBindingComponent: DataBindingComponentImplementation

    @Inject
    lateinit var dogFactsService: DogFactsService

    @Inject
    lateinit var catFactsService: CatFactsService

    private val countingIdlingResource = CountingIdlingResource("counter")

    @Before
    fun inject() {
        hiltRule.inject()
        DataBindingUtil.setDefaultComponent(dataBindingComponent)
    }

    @Before
    fun registerIdlingResources() {
        IdlingRegistry.getInstance().register(countingIdlingResource)
    }

    @After
    fun unregisterIdlingResources() {
        IdlingRegistry.getInstance().unregister(countingIdlingResource)
    }

    @Test
    fun displayAnimalFactWithDelay() {
        coEvery { dogFactsService.getDogFacts(any()) } coAnswers {
            countingIdlingResource.increment()
            delay(1000)
            countingIdlingResource.decrement()

            listOf(
                DogFact("Test delayed dog fact")
            )
        }

        coEvery { catFactsService.getCatFacts(any(), any()) } returns CatFactsResponse(emptyList())

        MainNavigator.navigateToAnimalFacts()

        onView(withText("Test delayed dog fact"))
            .check(matches(isDisplayed()))
    }
}