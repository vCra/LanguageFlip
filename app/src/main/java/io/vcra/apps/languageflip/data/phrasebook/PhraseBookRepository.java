package io.vcra.apps.languageflip.data.phrasebook;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import io.vcra.apps.languageflip.data.LFDB;

import java.util.List;


class PhraseBookRepository {

    private PhraseBookDAO PhraseBookDAO;
    private LiveData<List<PhraseBook>> AllPhraseBooks;

    PhraseBookRepository(Application application) {
        LFDB db = LFDB.Companion.getDatabase(application);
        PhraseBookDAO = db.phraseBookDAO();
        AllPhraseBooks = PhraseBookDAO.getPhraseBooks();
    }

    LiveData<List<PhraseBook>> getAllPhraseBooks() {
        return AllPhraseBooks;
    }

    void insert(PhraseBook phraseBook) {
        new insertAsyncTask(PhraseBookDAO).execute(phraseBook);
    }

    void remove(PhraseBook phraseBook) {
        new removeAsyncTask(PhraseBookDAO).execute(phraseBook);
    }

    void update(PhraseBook phraseBook) {
        new updateAsyncTask(PhraseBookDAO).execute(phraseBook);
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
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }
}
