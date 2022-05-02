package com.example.week_5_task

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import org.junit.Before
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import org.junit.Test
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        scenario = launchActivity()
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun register() {
        val name = "Samuel Oraginzer"
        val email = "organizersamuel@yahoo.com"
        val phone = "08181785509"
        val gender = "male"

        onView(withId(R.id.username)).perform(ViewActions.typeText(name))
        onView(withId(R.id.phone)).perform(ViewActions.typeText(phone))
        onView(withId(R.id.email)).perform(ViewActions.typeText(email))
        onView(withId(R.id.gender)).perform(click())
        onView(withText(gender)).inRoot(RootMatchers.isPlatformPopup()).perform(click())

        Espresso.closeSoftKeyboard()
        onView(withId(R.id.submitter)).perform(click())

        onView(withText(name)).check(matches(isDisplayed()))
        onView(withText(email)).check(matches(isDisplayed()))
        onView(withText(phone)).check(matches(isDisplayed()))
        onView(withText(gender)).check(matches(isDisplayed()))
    }
}