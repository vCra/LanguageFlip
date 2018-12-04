package io.vcra.apps.languageflip.data.phrasebook;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.*;

import java.util.List;


@Dao
public interface PhraseBookDAO {


    @Insert
    void insert(PhraseBook phraseBook);

    @Update
    void update(PhraseBook... phraseBooks);

    @Delete
    void delete(PhraseBook... phraseBooks);

    @Query("SELECT * from PhraseBook ORDER BY phrase ASC")
    LiveData<List<PhraseBook>> getPhraseBooks();

    @Query("SELECT COUNT(*) FROM phrasebook")
    Integer count();

    @Query("DELETE FROM PhraseBook")
    void deleteAll();
}
