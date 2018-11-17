package io.vcra.apps.languageflip.data.phrase;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import io.vcra.apps.languageflip.data.phrasebook.PhraseBook;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(
        entity = PhraseBook.class,
        parentColumns = "id",
        childColumns = "bookId",
        onDelete = CASCADE
))
public class Phrase {
    @PrimaryKey
    private int id;
    private String primaryWord;
    private String secondaryWord;
    private int bookId;

    public Phrase(String primaryWord, String secondaryWord, int bookId) {
        this.primaryWord = primaryWord;
        this.secondaryWord = secondaryWord;
        this.bookId = bookId;
    }


    public String getSecondaryWord() {
        return secondaryWord;
    }

    public void setSecondaryWord(String secondaryWord) {
        this.secondaryWord = secondaryWord;
    }

    public String getPrimaryWord() {
        return primaryWord;
    }

    public void setPrimaryWord(String primaryWord) {
        this.primaryWord = primaryWord;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}
