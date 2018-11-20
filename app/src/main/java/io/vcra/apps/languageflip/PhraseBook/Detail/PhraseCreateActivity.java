package io.vcra.apps.languageflip.PhraseBook.Detail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import io.vcra.apps.languageflip.R;
import io.vcra.apps.languageflip.data.phrase.Phrase;

public class PhraseCreateActivity extends AppCompatActivity {

    private EditText etLang1;
    private EditText etLang2;


    public static final String LANG1REPLY = "LANG1";
    public static final String LANG2REPLY = "LANG2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phrase_create);
        etLang1 = findViewById(R.id.edit_phrase_lang1);
        etLang2 = findViewById(R.id.edit_phrase_lang2);
        PhraseCreateActivity.this.setTitle("Create a new Phrase");

        final Button button = findViewById(R.id.button_save);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(etLang1.getText()) || TextUtils.isEmpty(etLang2.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String lang1 = etLang1.getText().toString();
                    String lang2 = etLang2.getText().toString();
                    replyIntent.putExtra(LANG1REPLY, lang1);
                    replyIntent.putExtra(LANG2REPLY, lang2);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });

    }
}

