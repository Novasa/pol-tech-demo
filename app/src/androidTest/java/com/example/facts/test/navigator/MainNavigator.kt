package com.example.facts.test.navigator

import androidx.annotation.StringRes
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.facts.R
import org.hamcrest.core.AllOf
import org.hamcrest.core.AllOf.allOf

object MainNavigator {

    fun navigateToAnimalFacts() = navigateToMainSectionWithText(R.string.main_page_animal_facts)

    private fun navigateToMainSectionWithText(@StringRes res: Int) {

        // Note: static method imports to remove clutter
        onView(
            // We have to uniquely define the view we want to click, and since it has no known ID, we need to find it another way
            allOf(
                // Child view of bottom navigation view
                isDescendantOfA(withId(R.id.mainBottomNavigation)),

                // Has the text of the menu option we want
                withText(res),

                // Is currently displayed.
                // Required because there are 2 text views with the text in the bottom navigation view,
                // one large for when the option is selected, and a smaller one when not
                isDisplayed()
            )
        ).perform(click())
    }
}