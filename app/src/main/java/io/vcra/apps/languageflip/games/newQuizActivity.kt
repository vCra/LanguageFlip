package io.vcra.apps.languageflip.games

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import io.vcra.apps.languageflip.R

class newQuizActivity : AppCompatActivity() {

    private var phraseBookid = -1

    private lateinit var questionText: TextView
    private var buttons: ArrayList<Button> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quiz_activity_old)

        questionText = findViewById(R.id.questionText)
        buttons.add(findViewById(R.id.button1))
        buttons.add(findViewById(R.id.button2))
        buttons.add(findViewById(R.id.button3))
        buttons.add(findViewById(R.id.button4))

        phraseBookid = intent.getIntExtra("PhraseBook", -1)

        var questionHelper = QuizHelper(phraseBookid)

        questionText.setText(questionHelper.getQuestion())
        buttons.forEach { buttons -> buttons.text = questionHelper.getAnswer() }
    }
}
