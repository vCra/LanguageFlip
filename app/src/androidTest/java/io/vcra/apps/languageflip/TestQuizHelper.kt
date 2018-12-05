package io.vcra.apps.languageflip

import android.app.Instrumentation
import android.arch.persistence.room.Room
import android.content.Context

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import java.io.IOException

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.vcra.apps.languageflip.data.LFDB
import io.vcra.apps.languageflip.data.phrase.Phrase
import io.vcra.apps.languageflip.data.phrase.PhraseDAO
import io.vcra.apps.languageflip.data.phrasebook.PhraseBook
import io.vcra.apps.languageflip.data.phrasebook.PhraseBookDAO
import io.vcra.apps.languageflip.games.QuizHelper

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.assertFalse

// TODO: FOREIGN KEY constraint failed

class TestQuizHelper {
    private lateinit var context: Context
    private lateinit var pbdao: PhraseBookDAO
    private lateinit var pdao: PhraseDAO
    private lateinit var db: LFDB
    private lateinit var pb: PhraseBook

    @Before
    fun createDb() {
        context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, LFDB::class.java).build()
        pbdao = db.phraseBookDAO()
        pdao = db.phraseDAO()
        pb = PhraseBook("Phrasebook 1")
        pbdao.insert(pb)
    }


    fun createPhrase(){
        pdao.insert(Phrase("Hello", "Salut", 0))
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun testAnswerCheckerRight(){
        createPhrase()
        val qh = QuizHelper(pb.id, context)
        qh.beginRound()
        assertTrue(qh.checkAnswer("Salut"))
        assertEquals(qh.score, 1)
    }

    @Test
    fun testAnswerCheckerWrong(){
        createPhrase()
        val qh = QuizHelper(pb.id, context)
        qh.beginRound()
        assertFalse(qh.checkAnswer("Gutan Tag"))
        assertEquals(qh.score, 0)

    }
}