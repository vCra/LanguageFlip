package io.vcra.apps.languageflip.data.phrasebook;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;


public class PhraseBookViewModel extends AndroidViewModel {

    private PhraseBookRepository mRepository;
    // Using LiveData and caching what getPhraseBooks returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    private LiveData<List<PhraseBook>> mAllWords;

    public PhraseBookViewModel(Application application) {
        super(application);
        mRepository = new PhraseBookRepository(application);
        mAllWords = mRepository.getAllWords();
    }

    public LiveData<List<PhraseBook>> getAllWords() {
        return mAllWords;
    }

    public void insert(PhraseBook phraseBook) {
        mRepository.insert(phraseBook);
    }

    public void remove(PhraseBook phraseBook){
        mRepository.remove(phraseBook);
    }
    public void rename(PhraseBook phraseBook, String str){
        mRepository.
    }
}