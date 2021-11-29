package com.example.facts.matcher

import android.view.View
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

object ViewMatchers {


    /**
     * Example of a custom hamcrest matcher.
     * [TypeSafeMatcher] ensures that the item supplied to matchesSafely() is not null, and of the correct type
     */
    fun withType(type: Class<out View>): Matcher<View> = object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description) {
            description.appendText("withType: ").appendValue(type.name)
        }

        override fun matchesSafely(item: View): Boolean = item.javaClass == type
    }
}