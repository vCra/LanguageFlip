package io.vcra.apps.languageflip.welcome


import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.view.ViewGroup
import io.vcra.apps.languageflip.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class WelcomeScreenOnFirstRun {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(WelcomeActivity::class.java)

    @Test
    fun welcomeScreenOnFirstRun() {
        val textView = onView(
                allOf(withId(R.id.text_slide_welcome), withText("Welcome to Language Flip"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.instanceOf(android.view.ViewGroup::class.java),
                                        0),
                                1),
                        isDisplayed()))
        textView.check(matches(withText("Welcome to Language Flip")))
    }

    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
