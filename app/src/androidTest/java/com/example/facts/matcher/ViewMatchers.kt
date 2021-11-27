package com.example.facts.matcher

import android.view.View
import android.widget.EditText
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

object ViewMatchers {

    fun withType(type: Class<out View>): Matcher<View> = object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description) {
            description.appendText("withType: ").appendValue(type.name)
        }

        override fun matchesSafely(item: View): Boolean = item.javaClass == type
    }
}