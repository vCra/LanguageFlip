package io.vcra.apps.languageflip.data.phrasebook;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;


public class PhraseBookViewModel extends AndroidViewModel {

    private PhraseBookRepository repository;
    private LiveData<List<PhraseBook>> allPhraseBooks;

    public PhraseBookViewModel(Application application) {
        super(application);
        repository = new PhraseBookRepository(application);
        allPhraseBooks = repository.getAllPhraseBooks();
    }

    public LiveData<List<PhraseBook>> getAllWords() {
        return allPhraseBooks;
    }

    public void insert(PhraseBook phraseBook) {
        repository.insert(phraseBook);
    }

    public void remove(PhraseBook phraseBook) {
        repository.remove(phraseBook);
    }

    public void rename(PhraseBook phraseBook, String str) {
        phraseBook.setPhrase(str);
        repository.update(phraseBook);
    }
}