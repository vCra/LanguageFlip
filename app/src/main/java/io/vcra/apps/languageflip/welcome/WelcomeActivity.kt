package io.vcra.apps.languageflip.welcome

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.vcra.apps.languageflip.PhraseBook.List.PhraseBookListActivity
import io.vcra.apps.languageflip.R
import io.vcra.apps.languageflip.data.settings.FirstRunDetector

// This was inspired from https://www.androidhive.info/2016/05/android-build-intro-slider-app/

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        finishOnFirstRun()


    }

    private fun finishOnFirstRun() {
        val check = FirstRunDetector(application)
        if (!check.isFirstRun) {
            startActivity(Intent(this@WelcomeActivity, PhraseBookListActivity::class.java))
        }
        finish()
    }


}
