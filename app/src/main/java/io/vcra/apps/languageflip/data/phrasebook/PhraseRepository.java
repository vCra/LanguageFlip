package io.vcra.apps.languageflip.data.phrasebook;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import io.vcra.apps.languageflip.data.LFDB;


class PhraseRepository {

    private PhraseBookDAO mPhraseBookDAO;
    private LiveData<List<PhraseBook>> mAllWords;

    PhraseRepository(Application application) {
        LFDB db = LFDB.getDatabase(application);
        mPhraseBookDAO = db.phraseBookDAO();
        mAllWords = mPhraseBookDAO.getPhraseBooks();
    }

    LiveData<List<PhraseBook>> getAllWords() {
        return mAllWords;
    }

    void insert(PhraseBook phraseBook) {
        new insertAsyncTask(mPhraseBookDAO).execute(phraseBook);
    }

    void remove(PhraseBook... phraseBooks) { new removeAsyncTask(mPhraseBookDAO).execute(phraseBooks);}

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

    private static class removeAsyncTask extends AsyncTask<PhraseBook, Void, Void> {

        private PhraseBookDAO mAsyncTaskDao;
        removeAsyncTask(PhraseBookDAO dao) {
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final PhraseBook... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }
    private static class updateAsyncTask extends AsyncTask<PhraseBook, Void, Void> {

        private PhraseBookDAO mAsyncTaskDao;
        updateAsyncTask(PhraseBookDAO dao) {
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final PhraseBook... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }
}
