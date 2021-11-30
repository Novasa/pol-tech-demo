package com.example.facts.test.category

import androidx.databinding.DataBindingUtil
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.facts.R
import com.example.facts.app.activity.MainActivity
import com.example.facts.database.dao.FactsDao
import com.example.facts.databinding.DataBindingComponentImplementation
import com.example.facts.model.Category
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class CreateCategoryUITests {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Inject
    lateinit var dataBindingComponent: DataBindingComponentImplementation

    @Inject
    lateinit var factsDao: FactsDao

    @Before
    fun inject() {
        hiltRule.inject()
        DataBindingUtil.setDefaultComponent(dataBindingComponent)
    }

    @Test
    fun createCategory() {

        // Mock the insert function in the dao
        coEvery { factsDao.insertCategory(any()) } returns 1L

        onView(withId(R.id.categoriesFabAdd))
            .perform(click())

        onView(withId(R.id.categoriesFabAddCategory))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withHint("Indtast navn"))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
            .perform(click())
            .perform(typeText("Test category"))

        onView(withText("OK"))
            .inRoot(isDialog())
            .perform(click())

        val categorySlot = slot<Category>()

        // coVerify for suspend function
        coVerify(exactly = 1) { factsDao.insertCategory(capture(categorySlot)) }

        assertEquals("Test category", categorySlot.captured.name)
    }
}