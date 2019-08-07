package com.szilardmakai.simplenoteapp.database;

import android.app.Application;
import android.arch.paging.PagedList;
import android.arch.paging.RxPagedListBuilder;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class NoteRepository {

    private static final int PAGE_SIZE = 20;
    private static final int INITIAL_LOAD = 40;

    private NoteDao mNoteDao;
    private Observable<PagedList<Note>> mAllNotes;

    public NoteRepository(Application application) {
        NoteRoomDatabase database = NoteRoomDatabase.getDatabase(application);
        mNoteDao = database.noteDao();
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(INITIAL_LOAD)
                .setPageSize(PAGE_SIZE)
                .build();
        mAllNotes = new RxPagedListBuilder<>(mNoteDao.getAllNotes(), pagedListConfig).buildObservable();
    }

    public void addNote(final Note note) {
        Completable.fromAction(new Action() {
            @Override
            public void run() {
                mNoteDao.addNote(note);
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void updateNote(final Note note) {
        Completable.fromAction(new Action() {
            @Override
            public void run() {
                mNoteDao.updateNote(note);
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void deleteNote(final Note note) {
        Completable.fromAction(new Action() {
            @Override
            public void run() {
                mNoteDao.deleteNote(note);
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public Observable<PagedList<Note>> getNotes() {
        return mAllNotes;
    }
}
