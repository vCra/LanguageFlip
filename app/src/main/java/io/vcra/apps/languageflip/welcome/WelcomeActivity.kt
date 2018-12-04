package io.vcra.apps.languageflip.welcome

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.content.ContextCompat
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import io.vcra.apps.languageflip.PhraseBook.List.PhraseBookListActivity
import io.vcra.apps.languageflip.R
import java.util.*


// This was inspired from https://www.androidhive.info/2016/05/android-build-intro-slider-app/

class WelcomeActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private lateinit var dots: LinearLayout
    private lateinit var dotTracker: ArrayList<TextView>
    private lateinit var btnSkip: Button
    private lateinit var btnNext: Button
    private lateinit var lang1Edit: EditText

    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private var layouts: IntArray = intArrayOf(
            R.layout.welcome_page1_slide,
            R.layout.welcome_page3_slide)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // If we don't need to show the welcome screen, then start the Phrase Book List Activity
        setContentView(R.layout.welcome_activity)
        skipUnlessFirstRun()

        viewPager = findViewById(R.id.viewPager)
        dots = findViewById(R.id.dots)
        btnNext = findViewById(R.id.button_next)
        btnSkip = findViewById(R.id.button_skip)

        viewPagerAdapter = ViewPagerAdapter()
        viewPager.adapter = ViewPagerAdapter()
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener)

        // When we skip, we do not need to change the value of the 2 languages
        btnSkip.setOnClickListener {
            launchHomeScreen()
        }

        btnNext.setOnClickListener {
            // checking for last page
            // if last page home screen will be launched
            val current = getItem(+1)
            if (current < layouts.size) {
                // move to next screen
                viewPager.currentItem = current
            } else {
                //We've gone past the last page
                val lang2Edit: EditText = findViewById(R.id.editLang2)
                val prefs = PreferenceManager.getDefaultSharedPreferences(this)
                val editor: SharedPreferences.Editor = prefs.edit()
                editor.putString("lang1", lang1Edit.text.toString())
                editor.putString("lang2", lang2Edit.text.toString())
                editor.apply()
                launchHomeScreen()
            }
        }

        setDots(0)


    }

    /**
     * Shows the PhraseBookListActivity, and finishes the Welcome Activity
     */
    private fun launchHomeScreen() {
        startActivity(Intent(this@WelcomeActivity, PhraseBookListActivity::class.java))
        finishAfterTransition()

    }

    /**
     * On the first run, display this welcome activity, and save firstRun as a preference
     * After this, this welcome page will not be shown, unless the user removes the app data
     */
    private fun skipUnlessFirstRun() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        if (!prefs.getBoolean("firstRun", true)) {
            launchHomeScreen()
        } else prefs.edit().putBoolean("firstRun", false).apply()

    }

    private fun getItem(i: Int): Int {
        return viewPager.currentItem + i
    }

    // The dot's don't really work sadly :/
    private fun setDots(currentPage: Int) {
        dotTracker = arrayListOf()

        dots.removeAllViews()

        for (i in 0 until dotTracker.size) {
            dotTracker[i] = TextView(this)
            dotTracker[i].text = "O"
            dotTracker[i].textSize = 35F
            dotTracker[i].setTextColor(ContextCompat.getColor(this, R.color.colorBubbleDark))
            dots.addView(dotTracker[i])
        }

        if (dotTracker.size > 0)
            dotTracker[currentPage].setTextColor(ContextCompat.getColor(this, R.color.colorBubbleLight))
    }

    var viewPagerPageChangeListener: ViewPager.OnPageChangeListener = object : ViewPager.OnPageChangeListener {

        override fun onPageSelected(position: Int) {
            setDots(position)

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.size - 1) {
                // last page. make button text to GOT IT
                btnNext.text = getString(R.string.begin)
                btnSkip.visibility = View.GONE
                lang1Edit = findViewById(R.id.editLang1)
                lang1Edit.setText(Locale.getDefault().displayLanguage.toString())
            } else {
                // still pages are left
                btnNext.text = getString(R.string.next)
                btnSkip.visibility = View.VISIBLE
            }
        }

        override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {

        }

        override fun onPageScrollStateChanged(arg0: Int) {

        }
    }

    inner class ViewPagerAdapter : PagerAdapter() {
        private lateinit var layoutInflater: LayoutInflater

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            layoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            val view = layoutInflater.inflate(layouts[position], container, false)
            container.addView(view)

            return view
        }

        override fun getCount(): Int {
            return layouts.size
        }

        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view === obj
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            val view = `object` as View
            container.removeView(view)
        }
    }

}
