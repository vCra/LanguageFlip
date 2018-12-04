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
import io.vcra.apps.languageflip.data.phrasebook.PhraseBook
import io.vcra.apps.languageflip.data.phrasebook.PhraseBookDAO

import junit.framework.TestCase.assertEquals

@RunWith(AndroidJUnit4::class)
class TestPhraseBookDB {
    private lateinit var dao: PhraseBookDAO
    private lateinit var db: LFDB

    @Before
    fun createDb() {
        val appContext = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(appContext, LFDB::class.java).build()
        dao = db.phraseBookDAO()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun createPhraseBook() {
        val count = dao.count()
        val pb = PhraseBook("Phrasebook 1")
        dao.insert(pb)
        assertEquals(dao.count(), count+1)
    }

    @Test
    fun deletePhraseBook() {
        val pb = PhraseBook("Phrasebook 1")
        dao.insert(pb)
        dao.deleteAll();
        assertEquals(dao.count(), 0)



    }
}