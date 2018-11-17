package io.vcra.apps.languageflip.PhraseBook.Create;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import io.vcra.apps.languageflip.R;

import static android.app.PendingIntent.getActivity;

/**
 * Activity for creating a new PhraseBook
 *
 * Currently only allows allow for a name to be specified
 *   - we can do the adding of phrases later
 */
public class NewPhraseBookActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "REPLY";

    private EditText mEditPhraseBookView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_phrase_book);
        mEditPhraseBookView = findViewById(R.id.edit_phrasebook_name);
        NewPhraseBookActivity.this.setTitle("New PhraseBook");
        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditPhraseBookView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String name = mEditPhraseBookView.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY, name);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}

