package com.szilardmakai.simplenoteapp;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;

import com.szilardmakai.simplenoteapp.database.Note;
import com.szilardmakai.simplenoteapp.database.NoteRepository;

public class NoteViewModel extends ViewModel {

    private NoteRepository mNoteRepository;
    private LiveData<PagedList<Note>> mAllNotes;

    public NoteViewModel() {
    }

    void init(Application application) {
        mNoteRepository = new NoteRepository(application);
        mAllNotes = mNoteRepository.getNotes();
    }

    LiveData<PagedList<Note>> getNotes() {
        return mAllNotes;
    }

    void addNote(Note note) {
        mNoteRepository.addNote(note);
    }

    void updateNote(Note note) {
        mNoteRepository.updateNote(note);
    }

    void deleteNote(Note note) {
        mNoteRepository.deleteNote(note);
    }

}