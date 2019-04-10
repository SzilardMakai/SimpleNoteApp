package com.szilardmakai.simplenoteapp;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.szilardmakai.simplenoteapp.database.Note;
import com.szilardmakai.simplenoteapp.utils.DateFormatUtils;

public class NoteListAdapter extends PagedListAdapter<Note, NoteListAdapter.NoteViewHolder> {

    private static ClickListener clickListener;

    NoteListAdapter() {
        super(Note.DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.note_list_item, viewGroup, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder noteViewHolder, int i) {
        Note currentNote = getItem(i);
        if (currentNote != null) {
            noteViewHolder.bindTo(currentNote);
        }
    }

    Note getNoteAtPosition(int position) {
        return getItem(position);
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        private TextView mNoteContent;
        private TextView mNoteLastModified;

        NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            mNoteContent = itemView.findViewById(R.id.tv_note_item_list_content);
            mNoteLastModified = itemView.findViewById(R.id.tv_note_item_list_last_modified);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClick(v, getAdapterPosition());
                }
            });
        }

        private void bindTo(Note note) {
            mNoteContent.setText(note.getContent());
            String lastModified = "Last modified on: " + DateFormatUtils.convertLongToDateString(note.getModifyDate());
            mNoteLastModified.setText(lastModified);
        }
    }

    void setOnItemClickListener(ClickListener clickListener) {
        NoteListAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View v, int position);
    }
}