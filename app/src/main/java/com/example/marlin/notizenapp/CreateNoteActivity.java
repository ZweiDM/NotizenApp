package com.example.marlin.notizenapp;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Die CreateNoteActivity beinhaltet ein Textfeld in dem der Nutzer seine Notizen eintragen kann.
 * Von hier aus werden sowohl neue Notizen erstellt, wie auch vorhandene bearbeitet.
 * Created by Marlin on 04.02.2018.
 */

public class CreateNoteActivity extends AppCompatActivity{

    private AppDatabase db;
    private EditText editNotizText;
    private TextView titleText;
    private TextView date;
    private Bundle mode;
    private ImageButton addButton;

    /**
     * In der onCreate Methode werden Views, Datenbank und der mode zur Unterscheidung zwischen
     * einer neuen und einer zu bearbeitenden Notiz initialisiert.
     * Wenn eine Notiz bearbeitet werden soll ändert sich der Titel der Activity
     * und das gekkNotizblatt wird mit dem
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        mode = getIntent().getExtras();
        editNotizText = findViewById(R.id.edit_note);
        addButton = findViewById(R.id.button_add);
        titleText = findViewById(R.id.create_note_title);
        date = findViewById(R.id.note_date);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "notizen_db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        //Bei mode:edit soll die Activity anders initialisiert werden
        if (mode.getString("mode").equals("edit")){
            titleText.setText(R.string.edit_note_title);
            //Mit übergebener ID aus der MainActivity wird zugriff auf den Inhalt der Notiz geschaffen
            //und dann in editTextview geschrieben.
            int id = mode.getInt("id");
            String textAusDatenBank = db.noteDao().getNoteById(id).getNote();
            editNotizText.setText(textAusDatenBank);
        }

    }

    /**
     * Die Methode für den addClick Button zum hinzufügen einer Notiz
     */
    public void addClick(View view) {
        //Variablendeklaration für die darauf folgende Speicherung in die Datenbank
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String datum = dateFormat.format(date);
        String notiztext = editNotizText.getText().toString();

        //Speicherung (mode: add) in die Datenbank oder überschreibung bei (mode:edit)
        //der vorhandenen Daten und finish() der Activity.
        if(mode.getString("mode").equals("add")){
            Note notiz = new Note(notiztext, datum, ResourcesCompat.getColor(getResources(), R.color.colorGrayNote, null));
            db.noteDao().insertAll(notiz);
            finish();
        } else if (mode.getString("mode").equals("edit")){
            //Datenbankzugriff mit zwischenspeicherung der Informationen im Objekt "neueNotiz"
            Note neueNotiz = db.noteDao().getNoteById(mode.getInt("id"));
            //Überschreibung der Notiz mit neuem Notiztext und Datum
            neueNotiz.setDate(datum);
            neueNotiz.setNote(editNotizText.getText().toString());
            //Aktualisierung der Datenbank
            db.noteDao().bearbeite(neueNotiz);
            finish();
        }
    }

    /**
     * Die Methode, die die Tastatur öffnet, sobald der Nutzer auf das Notizfeld tippt.
     * Ohne sie müsste der Nutzer genau in den EditView tippen.
     */
    public void setEditFocus(View view) {

        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        editNotizText.requestFocus();
        inputManager.showSoftInput(editNotizText, InputMethodManager.SHOW_IMPLICIT);
    }
}
