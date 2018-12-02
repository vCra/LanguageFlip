package io.vcra.apps.languageflip.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

import io.vcra.apps.languageflip.data.phrase.Phrase
import io.vcra.apps.languageflip.data.phrase.PhraseDAO
import io.vcra.apps.languageflip.data.phrasebook.PhraseBook
import io.vcra.apps.languageflip.data.phrasebook.PhraseBookDAO
import io.vcra.apps.languageflip.data.settings.Setting
import io.vcra.apps.languageflip.data.settings.SettingDAO


@Database(entities = arrayOf(PhraseBook::class, Phrase::class, Setting::class), version = 1)
abstract class LFDB : RoomDatabase() {

    abstract fun phraseBookDAO(): PhraseBookDAO
    abstract fun phraseDAO(): PhraseDAO
    abstract fun settingDAO(): SettingDAO

    companion object {

        // marking the instance as volatile to ensure atomic access to the variable
        @Volatile
        private var INSTANCE: LFDB? = null

        fun getDatabase(context: Context): LFDB? {
            if (INSTANCE == null) {
                synchronized(LFDB::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder<LFDB>(context.applicationContext,
                                LFDB::class.java, "database")
                                .fallbackToDestructiveMigration()
                                .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}
