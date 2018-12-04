package io.vcra.apps.languageflip;


import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.test.espresso.action.ViewActions;
import androidx.test.rule.ActivityTestRule;
import io.vcra.apps.languageflip.welcome.WelcomeActivity;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;

public class TestWelcomeScreen{

    private SharedPreferences sharedPreferences;

    @Rule
    public ActivityTestRule<WelcomeActivity> mActivityRule = new ActivityTestRule<>(WelcomeActivity.class);

    @Before
    public void cleanSheredPrefs(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mActivityRule.getActivity().getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    @Test
    public void testFirstStartWelcomeFull(){
        onView(allOf(withId(R.id.button_next), withText("Next"))).perform(click());
        onView(withId(R.id.editLang1)).perform(replaceText("Test Language 1"));
        onView(withId(R.id.editLang2)).perform(replaceText("Test Language 2"), ViewActions.closeSoftKeyboard());
        onView(allOf(withId(R.id.button_next), withText("Begin"))).perform(click());
        onView(withText("PhraseBooks")).check(matches(isDisplayed()));
        assertEquals(sharedPreferences.getString("lang1", ""), ("Test Language 1"));
        assertEquals(sharedPreferences.getString("lang2", ""), ("Test Language 2"));
    }

    @Test
    public void testFirstStartWelcomeSkip(){
        onView(withId(R.id.button_skip)).perform(click());
        onView(withText("PhraseBooks")).check(matches(isDisplayed()));
    }

}