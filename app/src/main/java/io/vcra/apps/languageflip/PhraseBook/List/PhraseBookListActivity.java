package io.vcra.apps.languageflip.PhraseBook.List;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;
import io.vcra.apps.languageflip.PhraseBook.Create.NewPhraseBookActivity;
import io.vcra.apps.languageflip.PhraseBook.Detail.PhraseBookDetailActivity;
import io.vcra.apps.languageflip.data.phrasebook.PhraseBook;
import io.vcra.apps.languageflip.data.phrasebook.PhraseBookListAdapter;
import io.vcra.apps.languageflip.data.phrasebook.PhraseBookViewModel;
import io.vcra.apps.languageflip.R;
import io.vcra.apps.languageflip.tools.RecyclerItemClickListener;

import java.util.List;


public class PhraseBookListActivity extends AppCompatActivity {

    public static final int NEW_PHRASE_ACTIVITY_REQUEST_CODE = 1;

    private PhraseBookViewModel mPhraseBookViewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phrase_book_list_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get a new or existing ViewModel from the ViewModelProvider.
        mPhraseBookViewModel = ViewModelProviders.of(this).get(PhraseBookViewModel.class);


        final RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final PhraseBookListAdapter adapter = new PhraseBookListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(
            new RecyclerItemClickListener(PhraseBookListActivity.this, recyclerView, new RecyclerItemClickListener.OnItemClickListener(){
                @Override public void onItemClick(View view, int position){
                    PhraseBook pb = adapter.getFromPosition(position);
                    Intent intent = new Intent(getBaseContext(), PhraseBookDetailActivity.class);
                    intent.putExtra("PhraseBook", pb.getId());
                    startActivity(intent);
                }

                @Override
                public void onLongItemClick(View view, final int position) {
                    PopupMenu popup = new PopupMenu(PhraseBookListActivity.this, view);
                    popup.getMenuInflater().inflate(R.menu.phrase_book_list_item_menu, popup.getMenu());
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            //TODO: Do this by not using the titles but instead the ID's
                            if (item.getTitle().equals("Remove")) {
                                Log.i("lf.pb.l", "Remove button pressed for " + String.valueOf(adapter.getFromPosition(position).getId()));

                                AlertDialog alertDialog = new AlertDialog.Builder(PhraseBookListActivity.this).create();
                                alertDialog.setTitle(getString(R.string.areyousure));
                                alertDialog.setMessage(getString(R.string.removephrasebooktext));
                                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.sure),
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (which == AlertDialog.BUTTON_POSITIVE){
                                                    mPhraseBookViewModel.remove(adapter.getFromPosition(position));
                                                }
                                            }
                                        });
                                alertDialog.show();
                            } else if (item.getTitle().equals(getString(R.string.rename))) {
                                Log.i("lf.pb.l", "Rename button pressed for " + String.valueOf(adapter.getFromPosition(position).getId()));
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(PhraseBookListActivity.this);
                                alertDialog.setView(new EditText(PhraseBookListActivity.this));
                                alertDialog.setTitle(R.string.rename_phrasebook);
                                alertDialog.setMessage(R.string.rename_msg);
                                AlertDialog alertDialog1 = alertDialog.create();
                                alertDialog1.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.rename),
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (which == AlertDialog.BUTTON_POSITIVE){
                                                    mPhraseBookViewModel.remove(adapter.getFromPosition(position));
                                                }
                                            }
                                        });
                                alertDialog1.show();
                            } else {
                                Log.e("lf", "Context menu item selected which is not implemented for");
                            }
                            return true;
                        }
                    });
                    popup.show();
                }
            })
        );



        // Add an observer on the LiveData returned by getPhraseBooks.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        mPhraseBookViewModel.getAllWords().observe(this, new Observer<List<PhraseBook>>() {
            @Override
            public void onChanged(@Nullable final List<PhraseBook> phraseBooks) {
                // Update the cached copy of the words in the adapter.
                adapter.setPhraseBooks(phraseBooks);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhraseBookListActivity.this, NewPhraseBookActivity.class);
                startActivityForResult(intent, NEW_PHRASE_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
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
