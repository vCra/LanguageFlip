package io.vcra.apps.languageflip.PhraseBook.List;

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

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;
import io.vcra.apps.languageflip.PhraseBook.Create.NewPhraseBookActivity;
import io.vcra.apps.languageflip.PhraseBook.DAO.PhraseBook;
import io.vcra.apps.languageflip.PhraseBook.DAO.PhraseBookListAdapter;
import io.vcra.apps.languageflip.PhraseBook.DAO.PhraseBookViewModel;
import io.vcra.apps.languageflip.R;

import java.util.List;


public class PhraseBookListActivity extends AppCompatActivity {

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    private PhraseBookViewModel mPhraseBookViewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrase_book_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final PhraseBookListAdapter adapter = new PhraseBookListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get a new or existing ViewModel from the ViewModelProvider.
        mPhraseBookViewModel = ViewModelProviders.of(this).get(PhraseBookViewModel.class);

        // Add an observer on the LiveData returned by getPhraseBooks.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        mPhraseBookViewModel.getAllWords().observe(this, new Observer<List<PhraseBook>>() {
            @Override
            public void onChanged(@Nullable final List<PhraseBook> phraseBooks) {
                // Update the cached copy of the words in the adapter.
                adapter.setWords(phraseBooks);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhraseBookListActivity.this, NewPhraseBookActivity.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            PhraseBook phraseBook = new PhraseBook(data.getStringExtra(NewPhraseBookActivity.EXTRA_REPLY));
            mPhraseBookViewModel.insert(phraseBook);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
}
