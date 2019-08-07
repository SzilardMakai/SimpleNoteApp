package com.szilardmakai.simplenoteapp.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

@Entity(tableName = "notes_table")
public class Note {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;

    @NonNull
    @ColumnInfo(name = "content")
    private String mContent;

    @ColumnInfo(name = "creation_date")
    private long mCreationDate;

    @ColumnInfo(name = "modified_date")
    private long mModifyDate;

    public Note(@NonNull String mContent, long mCreationDate, long mModifyDate) {
        this.mContent = mContent;
        this.mCreationDate = mCreationDate;
        this.mModifyDate = mModifyDate;
    }

    @Ignore
    public Note(int mId, @NonNull String mContent, long mCreationDate, long mModifyDate) {
        this.mId = mId;
        this.mContent = mContent;
        this.mCreationDate = mCreationDate;
        this.mModifyDate = mModifyDate;
    }

    public static DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note note, @NonNull Note t1) {
            return note.getId() == t1.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note note, @NonNull Note t1) {
            return note.equals(t1);
        }
    };

    public int getId() {
        return mId;
    }


    @NonNull
    public String getContent() {
        return mContent;
    }

    public long getCreationDate() {
        return mCreationDate;
    }

    public long getModifyDate() {
        return mModifyDate;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public void setContent(@NonNull String mContent) {
        this.mContent = mContent;
    }

    public void setCreationDate(long mCreationDate) {
        this.mCreationDate = mCreationDate;
    }

    public void setModifyDate(long mModifyDate) {
        this.mModifyDate = mModifyDate;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}