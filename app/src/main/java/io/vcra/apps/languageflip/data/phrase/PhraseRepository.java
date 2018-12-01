package io.vcra.apps.languageflip.data.phrase;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import io.vcra.apps.languageflip.data.LFDB;

import java.util.List;

public class PhraseRepository {

    private PhraseDAO PhraseDAO;
    private LiveData<List<Phrase>> allPhrases;


    PhraseRepository(Application application){
        LFDB db = LFDB.Companion.getDatabase(application);
        PhraseDAO = db.phraseDAO();
        allPhrases = PhraseDAO.getAllPhrases();
    }

    LiveData<List<Phrase>> getAllPhrases() {
        return allPhrases;
    }

    LiveData<List<Phrase>> getPhrasesFromBook(int phraseBookID){
        return PhraseDAO.getPhraseByBookId(phraseBookID);
    }

    void insert(Phrase phrase){
        new insertAsyncTask(PhraseDAO).execute(phrase);
    }

    void delete(Phrase phrase){
        new removeAsyncTask(PhraseDAO).execute(phrase);
    }
    void update(Phrase phrase){
        new updateAsyncTask(PhraseDAO).execute(phrase);
    }

    private static class insertAsyncTask extends AsyncTask<Phrase, Void, Void> {

        private PhraseDAO mAsyncTaskDao;
        insertAsyncTask(PhraseDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Phrase... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }


    private static class removeAsyncTask extends AsyncTask<Phrase, Void, Void> {

        private PhraseDAO mAsyncTaskDao;
        removeAsyncTask(PhraseDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Phrase... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Phrase, Void, Void> {

        private PhraseDAO mAsyncTaskDao;
        updateAsyncTask(PhraseDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Phrase... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }

}

