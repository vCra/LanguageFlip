package io.vcra.apps.languageflip.PhraseBook.Detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import io.vcra.apps.languageflip.R

class PhraseCreateActivity : AppCompatActivity() {

    private lateinit var etLang1: EditText
    private lateinit var etLang2: EditText
    private lateinit var lblLang1: TextView
    private lateinit var lblLang2: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.phrase_create)
        etLang1 = findViewById(R.id.edit_phrase_lang1)
        etLang2 = findViewById(R.id.edit_phrase_lang2)
        lblLang1 = findViewById(R.id.label_lang1)
        lblLang2 = findViewById(R.id.label_lang2)

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)


        lblLang1.text = prefs.getString("lang1", "Main language")
        lblLang2.text = prefs.getString("lang2", "Secondary Language")

        this@PhraseCreateActivity.title = "Create a new Phrase"

        val button = findViewById<Button>(R.id.button_save)

        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(etLang1.text) || TextUtils.isEmpty(etLang2.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val lang1 = etLang1.text.toString()
                val lang2 = etLang2.text.toString()
                replyIntent.putExtra(LANG1REPLY, lang1)
                replyIntent.putExtra(LANG2REPLY, lang2)
                setResult(Activity.RESULT_OK, replyIntent)
            }

            finish()

        }

    }

    companion object {


        val LANG1REPLY = "LANG1"
        val LANG2REPLY = "LANG2"
    }
}

