package com.jj.sensorcollector.presentation.activities

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import com.jj.sensorcollector.R
import com.jj.sensorcollector.data.utils.getAboutVersionText
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private lateinit var mDevice: UiDevice

    @get:Rule
    var rule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    }

    @Test
    fun mainLayoutShouldBeVisible() {
        onView(withId(R.id.mainLayout)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldShowProperInfoInMainLabel() {
        val expectedText = getAboutVersionText()
        onView(withId(R.id.mainLabel)).check(matches(withText(expectedText)))
    }
}