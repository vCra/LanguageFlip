package io.vcra.apps.languageflip;


import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.InstrumentationRegistry;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.action.ViewActions;
import androidx.test.rule.ActivityTestRule;
import io.vcra.apps.languageflip.PhraseBook.List.PhraseBookListActivity;
import io.vcra.apps.languageflip.data.phrasebook.PhraseBook;
import io.vcra.apps.languageflip.welcome.WelcomeActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static java.lang.Thread.sleep;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;

public class TestPhraseBook {

    private SharedPreferences sharedPreferences;

    @Rule
    public ActivityTestRule<PhraseBookListActivity> mActivityRule = new ActivityTestRule<>(PhraseBookListActivity.class);

    @Before
    public void cleanSheredPrefs(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mActivityRule.getActivity().getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    @BeforeClass
    public static void removeDb() {
        ApplicationProvider.getApplicationContext().deleteDatabase("database");
    }

    @Test
    public void testCreatePhraseBook(){
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.edit_phrasebook_name)).perform(replaceText("Phrase Book 1"));
        onView(allOf(withId(R.id.button_save), withText("Save"))).perform(click());
        onView(withText("PhraseBooks")).check(matches(isDisplayed()));
        onView(withText("Phrase Book 1")).check(matches(isDisplayed()));
    }

    @Test
    public void testRemovePhraseBook(){
        testCreatePhraseBook();
        onView(withText("Phrase Book 1")).perform(longClick());
        onView(withText("Remove")).perform(click());
        onView(withText("I'M SURE")).perform(click());
        onView(withText("Phrase Book 1")).check(doesNotExist());

    }

    @Test
    public void testRenamePhraseBook(){
        testCreatePhraseBook();
        onView(withText("Phrase Book 1")).perform(longClick());
        onView(withText("Rename")).perform(click());
        onView(withId(R.id.edit_phrasebook_name)).perform(replaceText("Phrase Book Renamed"));
        onView(withText("Phrase Book 1")).check(doesNotExist());
        onView(withText("Phrase Book Renamed")).check(matches(isDisplayed()));


    }

}