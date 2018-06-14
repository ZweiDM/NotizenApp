package com.example.marlin.notizenapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;

/**
 * Created by Marlin on 04.02.2018.
 * Die Klasse die für die Füllung der Notizen zuständig ist, also die ListenItems gemäß der content_row.xml
 * für den RecyclerView generiert.
 */

class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private List<Note> notes;

    public NoteAdapter(List<Note> notes){
        this.notes = notes;
    }

    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteAdapter.ViewHolder holder, int position) {
        //Festlegung der IDs für die Views der Notizen
        holder.notizView.setText(notes.get(position).getNote());
        //Durch die iterrierende id bekommen die Views der Notizen eine passende ID zur Datenbank,
        //um die Notizen später noch dem entsprechendem Speicher der Datenbank zuordnen zu können.
        int id = notes.get(position).getId();
        holder.notizId.setText(""+id);
        holder.notizDatum.setText(notes.get(position).getDate());
        holder.notizLayout.setBackgroundColor(notes.get(position).getColor());
        holder.delButton.setTag(id);
        holder.editButton.setTag(id);
        holder.colorButton.setTag(id);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView notizView;
        public TextView notizId;
        public TextView notizDatum;
        public LinearLayout notizLayout;
        public ImageButton delButton;
        public ImageButton editButton;
        public ImageButton colorButton;


        public ViewHolder(View itemView) {
            super(itemView);

            notizLayout = itemView.findViewById(R.id.note_item);
            notizView = itemView.findViewById(R.id.noteLine);
            notizId = itemView.findViewById(R.id.note_id);
            notizDatum = itemView.findViewById(R.id.note_date);
            delButton = itemView.findViewById(R.id.delete_note_button);
            editButton = itemView.findViewById(R.id.edit_note_button);
            colorButton = itemView.findViewById(R.id.color_note_button);
        }
    }
}
