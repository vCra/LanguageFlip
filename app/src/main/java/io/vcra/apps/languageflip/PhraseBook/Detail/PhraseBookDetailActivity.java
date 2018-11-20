package io.vcra.apps.languageflip.PhraseBook.Detail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Toast;
import io.vcra.apps.languageflip.PhraseBook.List.PhraseBookCreateActivity;
import io.vcra.apps.languageflip.R;
import io.vcra.apps.languageflip.data.phrase.Phrase;
import io.vcra.apps.languageflip.data.phrase.PhraseRepository;
import io.vcra.apps.languageflip.data.phrase.PhraseViewModel;
import io.vcra.apps.languageflip.data.phrasebook.PhraseBook;

public class PhraseBookDetailActivity extends AppCompatActivity {

    PhraseBook phraseBook;
    public static final int NEW_PHRASE_ACTIVITY_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phrase_book_detail_activity);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, PhraseBookDetailFragment.newInstance())
                    .commitNow();
        }

        FloatingActionButton fab = this.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhraseBookDetailActivity.this, PhraseCreateActivity.class);
                startActivityForResult(intent, NEW_PHRASE_ACTIVITY_REQUEST_CODE);
            }
        });


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Phrase phrase = new Phrase(
                data.getStringExtra(PhraseCreateActivity.LANG1REPLY),
                data.getStringExtra(PhraseCreateActivity.LANG2REPLY),
                this.getIntent().getIntExtra("PhraseBook", -1)
            );
            PhraseViewModel vm = ViewModelProviders.of(this).get(PhraseViewModel.class);
            int id = this.getIntent().getIntExtra("PhraseBook", -1);
            vm.setPhraseBookID(id);
            vm.insert(phrase);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
}
