package io.vcra.apps.languageflip.data.phrase;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.*;

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
    LiveData<List<Phrase>> getAllPhrases();

    @Query("SELECT * FROM Phrase WHERE bookId=:bookId")
    LiveData<List<Phrase>> getPhraseByBookId(int bookId);

    @Query("SELECT * FROM Phrase WHERE bookId=:bookId")
    List<Phrase> getDeadPhraseByBookId(int bookId);

    @Query("SELECT COUNT(*) FROM Phrase where bookId=:bookID")
    Integer getPhrasesInBook(int bookID);

    @Query("DELETE FROM phrase")
    void deleteAll();
}
