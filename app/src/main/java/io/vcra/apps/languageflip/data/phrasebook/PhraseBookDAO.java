package io.vcra.apps.languageflip.data.phrasebook;



import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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

    @Query("DELETE FROM PhraseBook")
    void deleteAll();
}
