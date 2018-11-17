package io.vcra.apps.languageflip.data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import io.vcra.apps.languageflip.data.phrase.Phrase;
import io.vcra.apps.languageflip.data.phrase.PhraseDAO;
import io.vcra.apps.languageflip.data.phrasebook.PhraseBook;
import io.vcra.apps.languageflip.data.phrasebook.PhraseBookDAO;


@Database(entities = {PhraseBook.class, Phrase.class}, version = 1)
public abstract class LFDB extends RoomDatabase {

    public abstract PhraseBookDAO phraseBookDAO();
    public abstract PhraseDAO phraseDAO();

    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile LFDB INSTANCE;

    public static LFDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (LFDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            LFDB.class, "database")
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this codelab.
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
