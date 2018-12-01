package io.vcra.apps.languageflip.PhraseBook.List

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import io.vcra.apps.languageflip.R

import android.app.PendingIntent.getActivity

/**
 * Activity for creating a new PhraseBook
 *
 * Currently only allows allow for a name to be specified
 * - we can do the adding of phrases later
 */
class PhraseBookCreateActivity : AppCompatActivity() {

    private var mEditPhraseBookView: EditText? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.phrase_book_new_activity)
        mEditPhraseBookView = findViewById(R.id.edit_phrasebook_name)
        this@PhraseBookCreateActivity.title = "New PhraseBook"
        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(mEditPhraseBookView!!.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val name = mEditPhraseBookView!!.text.toString()
                replyIntent.putExtra(EXTRA_REPLY, name)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        val EXTRA_REPLY = "REPLY"
    }
}

