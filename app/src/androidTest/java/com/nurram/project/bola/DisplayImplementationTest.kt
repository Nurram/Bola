package com.nurram.project.bola

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.DrawerActions
import android.support.test.espresso.contrib.NavigationViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DisplayImplementationTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testBehaviour() {

        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        onView(withText("Watford")).check(matches(isDisplayed()))
        onView(withText("Watford")).perform(click())
        onView(withId(R.id.menu_fav_button)).perform(click())
        pressBack()

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.menu_next_match))

        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        onView(withText("Fulham")).check(matches(isDisplayed()))
        onView(withText("Fulham")).perform(click())

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.menu_team_list))

        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        onView(withText("Cardiff")).check(matches(isDisplayed()))
        onView(withText("Cardiff")).perform(click())
        onView(withId(R.id.menu_fav_button)).perform(click())
        pressBack()

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.menu_fav_match))

        Thread.sleep(1000)
        onView(withText("Watford")).check(matches(isDisplayed()))
        onView(withText("Watford")).perform(click())
        onView(withId(R.id.menu_fav_button)).perform(click())
        pressBack()

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.menu_fav_team))

        Thread.sleep(1000)
        onView(withText("Cardiff")).check(matches(isDisplayed()))
        onView(withText("Cardiff")).perform(click())
        onView(withId(R.id.menu_fav_button)).perform(click())
        pressBack()
    }
}