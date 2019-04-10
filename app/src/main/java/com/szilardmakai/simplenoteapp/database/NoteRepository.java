package com.szilardmakai.simplenoteapp.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.os.AsyncTask;

public class NoteRepository {

    private static final int PAGE_SIZE = 20;
    private static final int INITIAL_LOAD = 40;

    private NoteDao mNoteDao;
    private LiveData<PagedList<Note>> mAllNotes;

    public NoteRepository(Application application) {
        NoteRoomDatabase database = NoteRoomDatabase.getDatabase(application);
        mNoteDao = database.noteDao();
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(INITIAL_LOAD)
                .setPageSize(PAGE_SIZE)
                .build();
        mAllNotes = new LivePagedListBuilder<>(mNoteDao.getAllNotes(), pagedListConfig).build();
    }

    public void addNote(Note note) {
        new AddNoteAsyncTask(mNoteDao).execute(note);
    }

    public void updateNote(Note note) {
        new UpdateNoteAsyncTask(mNoteDao).execute(note);
    }

    public void deleteNote(Note note) {
        new DeleteNoteAsyncTask(mNoteDao).execute(note);
    }

    public LiveData<PagedList<Note>> getNotes() {
        return mAllNotes;
    }


    private static class AddNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao asyncNoteDao;

        AddNoteAsyncTask(NoteDao noteDao) {
            asyncNoteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            asyncNoteDao.addNote(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao asyncDao;

        UpdateNoteAsyncTask(NoteDao noteDao) {
            this.asyncDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            asyncDao.updateNote(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao asyncDao;

        DeleteNoteAsyncTask(NoteDao noteDao) {
            this.asyncDao = noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            asyncDao.deleteNote(notes[0]);
            return null;
        }
    }
}
