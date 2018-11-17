package io.vcra.apps.languageflip.data.phrase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface PhraseDAO {

    @Insert
    void insert(Phrase phrase);

    @Update
    void update(Phrase... phrases);

    @Delete
    void delete(Phrase... phrases);

    @Query("SELECT * FROM Phrase")
    List<Phrase> getAllPhrases();

    @Query("SELECT * FROM Phrase WHERE bookId=:bookId")
    List<Phrase> getPhraseByBookId(int bookId);

}
