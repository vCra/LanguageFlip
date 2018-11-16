package io.vcra.apps.languageflip.PhraseBook.DAO;

/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import io.vcra.apps.languageflip.PhraseBook.DAO.PhraseBook;
import io.vcra.apps.languageflip.PhraseBook.DAO.PhraseBookDAO;
import io.vcra.apps.languageflip.PhraseBook.DAO.PhraseRoomDB;

import java.util.List;

/**
 * Abstracted Repository as promoted by the Architecture Guide.
 * https://developer.android.com/topic/libraries/architecture/guide.html
 */

class PhraseRepository {

    private PhraseBookDAO mPhraseBookDAO;
    private LiveData<List<PhraseBook>> mAllWords;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    PhraseRepository(Application application) {
        PhraseRoomDB db = PhraseRoomDB.getDatabase(application);
        mPhraseBookDAO = db.wordDao();
        mAllWords = mPhraseBookDAO.getPhraseBooks();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<PhraseBook>> getAllWords() {
        return mAllWords;
    }

    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    void insert(PhraseBook phraseBook) {
        new insertAsyncTask(mPhraseBookDAO).execute(phraseBook);
    }

    private static class insertAsyncTask extends AsyncTask<PhraseBook, Void, Void> {

        private PhraseBookDAO mAsyncTaskDao;

        insertAsyncTask(PhraseBookDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final PhraseBook... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
