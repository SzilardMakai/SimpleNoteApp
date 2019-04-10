package com.szilardmakai.simplenoteapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.szilardmakai.simplenoteapp.database.Note;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_NOTE_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_NOTE_ACTIVITY_REQUEST_CODE = 2;

    public static final String EXTRA_NOTE_CONTENT = "extra_note_to_be_updated";
    public static final String EXTRA_NOTE_ID = "extra_note_id";
    public static final String EXTRA_NOTE_CREATION_DATE = "extra_note_creation_date";

    private NoteViewModel mNoteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView mRecyclerView = findViewById(R.id.rv_notes);

        mNoteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        mNoteViewModel.init(getApplication());

        final NoteListAdapter adapter = new NoteListAdapter();
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mNoteViewModel.getNotes().observe(this, new Observer<PagedList<Note>>() {
            @Override
            public void onChanged(@Nullable PagedList<Note> notes) {
                adapter.submitList(notes);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivityForResult(intent, NEW_NOTE_ACTIVITY_REQUEST_CODE);
            }
        });

        ItemTouchHelper touchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                int position = viewHolder.getAdapterPosition();
                Note note = adapter.getNoteAtPosition(position);
                mNoteViewModel.deleteNote(note);
            }
        });
        touchHelper.attachToRecyclerView(mRecyclerView);

        adapter.setOnItemClickListener(new NoteListAdapter.ClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Note note = adapter.getNoteAtPosition(position);
                launchEditNoteActivity(note);
            }
        });
    }

    

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (requestCode == NEW_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
                long date = Calendar.getInstance().getTimeInMillis();
                Note note = new Note(data.getStringExtra(AddNoteActivity.EXTRA_REPLY), date, date);
                mNoteViewModel.addNote(note);
                Toast.makeText(this, getString(R.string.note_saved), Toast.LENGTH_SHORT).show();
            } else if (requestCode == UPDATE_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
                String noteContent = data.getStringExtra(AddNoteActivity.EXTRA_REPLY);
                int id = data.getIntExtra(AddNoteActivity.EXTRA_REPLY_ID, -1);
                long creationDate = data.getLongExtra(AddNoteActivity.EXTRA_REPLY_CREATION_DATE, -1);

                if (id != -1) {
                    Note note = new Note(id, noteContent, creationDate, Calendar.getInstance().getTimeInMillis());
                    mNoteViewModel.updateNote(note);
                    Toast.makeText(this, getString(R.string.note_updated), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void launchEditNoteActivity(Note note) {
        Intent intent = new Intent(this, AddNoteActivity.class);
        intent.putExtra(EXTRA_NOTE_CONTENT, note.getContent());
        intent.putExtra(EXTRA_NOTE_ID, note.getId());
        intent.putExtra(EXTRA_NOTE_CREATION_DATE, note.getCreationDate());
        startActivityForResult(intent, UPDATE_NOTE_ACTIVITY_REQUEST_CODE);
    }
}