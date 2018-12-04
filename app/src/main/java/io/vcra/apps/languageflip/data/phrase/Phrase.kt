package io.vcra.apps.languageflip.data.phrase

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.PrimaryKey
import io.vcra.apps.languageflip.data.phrasebook.PhraseBook

@Entity(foreignKeys = arrayOf(ForeignKey(entity = PhraseBook::class, parentColumns = arrayOf("id"), childColumns = arrayOf("bookId"), onDelete = CASCADE)))
class Phrase(var primaryWord: String, var secondaryWord: String?, var bookId: Int) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
