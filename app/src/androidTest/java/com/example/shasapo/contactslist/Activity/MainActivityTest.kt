package com.example.shasapo.contactslist.Activity

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.shasapo.contactslist.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Created by shasapo on 12/6/17.
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var activityTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java)


    @Test
    fun shouldOpenInsertUpdateActivityWhenClickOnAddButton() {

        activityTestRule.launchActivity(Intent())

        try {
            Intents.init()
            Intents.intending(IntentMatchers.anyIntent())
                    .respondWith(Instrumentation.ActivityResult(Activity.RESULT_OK, Intent()))

            Espresso.onView(ViewMatchers.withId(R.id.fabAddContact)).perform(ViewActions.click())


            Intents.intended(IntentMatchers.hasComponent(InsertUpdateContactActivity::class.java.name))

        } finally {
            Intents.release()
        }


    }

}