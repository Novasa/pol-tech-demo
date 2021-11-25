package com.example.facts.matcher

import android.view.View
import android.widget.EditText
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

object EditTextMatchers {

    fun withHint(hint: CharSequence): Matcher<View> = object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description) {
            description.appendText("withHint: ").appendValue(hint)
        }

        override fun matchesSafely(item: View): Boolean = item is EditText && item.hint == hint
    }
}