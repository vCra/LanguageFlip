package io.vcra.apps.languageflip.data.phrase

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

import io.vcra.apps.languageflip.data.phrasebook.PhraseBook

import android.arch.persistence.room.ForeignKey.CASCADE

@Entity(foreignKeys = arrayOf(ForeignKey(entity = PhraseBook::class, parentColumns = arrayOf("id"), childColumns = arrayOf("bookId"), onDelete = CASCADE)))
class Phrase(primaryWord: String, var secondaryWord: String?, var bookId: Int) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    var primaryWord: String? = null
        private set

    init {
        this.primaryWord = primaryWord
    }

    fun setPrimarysasWord(primaryWord: String) {
        this.primaryWord = primaryWord
    }
}
