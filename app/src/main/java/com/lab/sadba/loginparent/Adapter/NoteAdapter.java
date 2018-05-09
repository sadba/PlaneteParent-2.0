package com.lab.sadba.loginparent.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.lab.sadba.loginparent.Model.Note;
import com.lab.sadba.loginparent.R;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<Note> notes;

    public NoteAdapter(Context context, List<Note> notes){
        this.context = context;
        this.notes = notes;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public NoteAdapter.NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.note_layout, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteAdapter.NoteViewHolder holder, int position) {
        Note not = notes.get(position);
        holder.setItemContent(not);
    }

    @Override
    public int getItemCount(){
        return notes.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView discipline, date_note, note;

        public NoteViewHolder(View itemView){
            super(itemView);
            discipline = itemView.findViewById(R.id.discipline_note);
            date_note = itemView.findViewById(R.id.date_note);
            note = itemView.findViewById(R.id.note_note);
        }

        void setItemContent(Note not){
            discipline.setText(not.getLibelle_discipline());
            date_note.setText(not.getDate_eval());
            note.setText(not.getNote());
        }
    }
}
