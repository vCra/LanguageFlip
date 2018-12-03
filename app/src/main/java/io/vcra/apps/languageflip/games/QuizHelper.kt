package io.vcra.apps.languageflip.games

import android.content.Context
import io.vcra.apps.languageflip.data.LFDB
import io.vcra.apps.languageflip.data.phrase.Phrase


class QuizHelper(val ph: Int, context: Context) {

    var unusedPhrases: ArrayList<Phrase> = ArrayList(LFDB.getDatabase(context = context)?.phraseDAO()!!.getDeadPhraseByBookId(ph))
    var score = 0

    var currentPhrase: Phrase? = null

    var round = 0
    val numberOfRounds: Int = unusedPhrases.size

    fun beginRound(): Phrase{
        round++
        val question = unusedPhrases.random()
        unusedPhrases.remove(question)
        currentPhrase = question
        return question
    }

    fun checkAnswer(answer: String): Boolean{
        if (answer.equals(currentPhrase?.secondaryWord, true)){
            score ++
            return true
        }
        return false
    }

}