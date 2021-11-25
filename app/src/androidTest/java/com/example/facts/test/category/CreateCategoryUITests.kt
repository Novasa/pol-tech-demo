package com.example.facts.test.category

import androidx.databinding.DataBindingUtil
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.facts.app.activity.MainActivity
import com.example.facts.databinding.DataBindingComponentImplementation
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class CreateCategoryUITests {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Inject
    lateinit var dataBindingComponent: DataBindingComponentImplementation

    @Before
    fun inject() {
        hiltRule.inject()
        DataBindingUtil.setDefaultComponent(dataBindingComponent)
    }

    @Test
    fun createCategory() {

    }
}