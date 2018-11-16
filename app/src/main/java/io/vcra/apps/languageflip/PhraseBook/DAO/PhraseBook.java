package io.vcra.apps.languageflip.PhraseBook.DAO;



import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;



@Entity(tableName = "phrasebook")
public class PhraseBook {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "phrase")
    private String mPhrase;

    public PhraseBook(@NonNull String phrase) {
        this.mPhrase = phrase;
    }

    @NonNull
    public String getPhrase() {
        return this.mPhrase;
    }
}