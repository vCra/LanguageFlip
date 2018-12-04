package io.vcra.apps.languageflip.games

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import io.vcra.apps.languageflip.R
import io.vcra.apps.languageflip.data.phrase.Phrase

class QuizActivity : AppCompatActivity() {

    private lateinit var word: TextView
    private lateinit var score: TextView
    private lateinit var learner: TextView
    private lateinit var editBox: EditText
    private lateinit var button: Button
    private lateinit var currentPhrase: Phrase
    private lateinit var quizHelper: QuizHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quiz_activity)

        val pbid = intent.getIntExtra("PhraseBook", -1)

        quizHelper = QuizHelper(pbid, this)

        word = findViewById(R.id.label_lang1)
        score = findViewById(R.id.score_label)
        learner = findViewById(R.id.learner_label)
        word = findViewById(R.id.label_lang1)
        editBox = findViewById(R.id.edit_phrase_lang2)
        button = findViewById(R.id.button_save)

        button.setOnClickListener {
            if (quizHelper.checkAnswer(editBox.text.toString())) {
                learner.text = getString(R.string.correct_message)
            } else {
                learner.text = getString(R.string.actually_means).format(currentPhrase.primaryWord, currentPhrase.secondaryWord)
            }
            nextQuestion()
        }
        currentPhrase = quizHelper.beginRound()
        setQuestion()
    }

    private fun setQuestion() {
        word.text = currentPhrase.primaryWord
        editBox.setText("")
    }


    private fun nextQuestion() {
        //Check if we have run out of questions
        if (quizHelper.round == quizHelper.numberOfRounds) {
            finish()
            Toast.makeText(
                    applicationContext,
                    getString(R.string.you_got_score).format(quizHelper.score),
                    Toast.LENGTH_LONG
            ).show()

        } else {
            score.text = getString(R.string.score_int).format(quizHelper.score.toString())
            currentPhrase = quizHelper.beginRound()
            setQuestion()
        }
    }

}
