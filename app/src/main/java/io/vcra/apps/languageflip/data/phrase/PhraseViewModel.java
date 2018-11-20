package io.vcra.apps.languageflip.data.phrase;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class PhraseViewModel extends AndroidViewModel {

    private PhraseRepository repository;
    private LiveData<List<Phrase>> phrases;

    public PhraseViewModel(@NonNull Application application) {
        super(application);
        repository = new PhraseRepository(application);
    }

    public void setPhraseBookID(int phraseBookID) {
        phrases = repository.getPhrasesFromBook(phraseBookID);
    }

    public LiveData<List<Phrase>> getPhrases() {
        return phrases;
    }

    public void insert(Phrase phrase) {
        repository.insert(phrase);
    }

    public void update(Phrase phrase) {
        repository.update(phrase);
    }

    public void remove(Phrase phrase) {
        repository.delete(phrase);
    }
}
