package io.vcra.apps.languageflip.PhraseBook.Detail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.vcra.apps.languageflip.PhraseBook.Detail.ui.phrasebookdetail.PhraseBookDetailFragment;
import io.vcra.apps.languageflip.R;

public class PhraseBookDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phrase_book_detail_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, PhraseBookDetailFragment.newInstance())
                    .commitNow();
        }
    }
}
