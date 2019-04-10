package com.szilardmakai.simplenoteapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddNoteActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.szilardmakai.remindme.REPLY";
    public static final String EXTRA_REPLY_ID = "com.szilardmakai.remindme.REPLY_ID";
    public static final String EXTRA_REPLY_CREATION_DATE = "com.szilardmakai.remindme.CREATION_DATE";
    private EditText mNoteEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        mNoteEditText = findViewById(R.id.et_add_note_text);

        final Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String note = extras.getString(MainActivity.EXTRA_NOTE_CONTENT, "");
            if (!note.isEmpty()) {
                mNoteEditText.setText(note);
                mNoteEditText.setSelection(note.length());
                mNoteEditText.requestFocus();
            }
        }

        final Button saveButton = findViewById(R.id.btn_add_note_save);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mNoteEditText.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String noteText = mNoteEditText.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY, noteText);

                    if (extras != null && extras.containsKey(MainActivity.EXTRA_NOTE_ID)) {
                        int id = extras.getInt(MainActivity.EXTRA_NOTE_ID, -1);
                        long creationDate = extras.getLong(MainActivity.EXTRA_NOTE_CREATION_DATE);
                        if (id != -1) {
                            replyIntent.putExtra(EXTRA_REPLY_ID, id);
                            replyIntent.putExtra(EXTRA_REPLY_CREATION_DATE, creationDate);
                        }
                    }
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });

        Button cancelButton = findViewById(R.id.btn_add_note_cancel);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
