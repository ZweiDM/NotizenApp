package com.example.marlin.notizenapp;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

/**
 * Die MainActivity zeigt eine scrollbare dynamische Liste der vom Benutzer angelegten Notizen.
 * Der Inhalt der Notizen liegt in der Room Datenbank (notizen_db)
 * im Pfad: data/data/com.example.marlin.notizenapp/databases/
 * (Am Emulator können Sie die Daten mit dem Android Device Monitor einsehen, sobald Sie sich mit
 * entsprechenden Konsolenbefehlen Zugriff auf den "data" Ordner verschafft haben
 * oder Sie benutzen den Device File Explorer)
 * Das Layout der Activity wird dynamisch aus der activity_main.xml, der content_main.xml
 * und der note_row.xml verschachtelt generiert.
 */
public class MainActivity extends AppCompatActivity {

    private AppDatabase db;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerAdapter;
    private FloatingActionButton newNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        newNote = findViewById(R.id.button_new_note);
        recyclerView = findViewById(R.id.recycler_View);

        //Erzeugung der Datenbankschnittstelle: notizen_db
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "notizen_db")
                .fallbackToDestructiveMigration().allowMainThreadQueries().build();

        //Erzeugung der dynamischen Notizenliste
        recyclerAdapter = new NoteAdapter(db.noteDao().getAll());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerAdapter);
    }

    /**
     * Methode für den FloatingActionButton, der eine neue Notiz hinzufügt,
     * indem er einen Intent der CreateNoteActivity erzeugt.
     */
    public void newNoteClick(View view) {
        Intent i = new Intent(MainActivity.this, CreateNoteActivity.class);
        i.putExtra("mode", "add");
        startActivity(i);
    }

    /**
     * Methode, die die Notizenliste aktualisiert, sobald der Life cycle der Activity onPostResume
     * erreicht. Dadurch aktualisiert sich die Notizenliste zeitgleich mit dem Wechsel
     * aus der CreateNoteActivity in die MainActivity
     */
    @Override
    protected void onPostResume() {
        super.onPostResume();
        recyclerAdapter = new NoteAdapter(db.noteDao().getAll());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerAdapter);
    }

    /**
     * Methode für den Delete-Button, die die zu löschenden Notizen aus der Datenbank entfernt
     * und die Notizenliste danach aktualisiert.
     */
    public void deleteNote(View view) {
        //Benutzerfeedback bei Aufruf durch einen Toast
        Toast.makeText(this, "Notiz gelöscht", Toast.LENGTH_SHORT).show();

        //Änderung in der Datenbank
        db.noteDao().delete(db.noteDao().getNoteById((int)view.getTag()));

        //Aktualisierung des Layouts
        recyclerAdapter = new NoteAdapter(db.noteDao().getAll());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerAdapter);
    }

    /**
     * Methode für den Edit-Button, die einen Intent der CreateNoteActivity erzeugt
     * und als extra den edit mode, sowie die ID der Notiz übergibt
     */
    public void editNote(View view) {
        int id = (int)view.getTag();
        Intent intent = new Intent(MainActivity.this, CreateNoteActivity.class);
        intent.putExtra("mode", "edit");
        intent.putExtra("id", id);
        startActivity(intent);
    }

    /**
     * Methode für den Color-Button, die die Hintergrundfarbe einer Notiz ändert.
     * Standardgemäß sind Notizen Grau, aber man kann sie auch in Cyan oder Gelb hervorheben
     */
    public void colorNote(View view) {
        //Datenbankzugriff mit zwischenspeicherung der Informationen im Objekt "selektierteNotiz"
        Note selektierteNotiz = db.noteDao().getNoteById((int)view.getTag());

        //Farbendeklaration
        int grau = ResourcesCompat.getColor(getResources(), R.color.colorGrayNote, null);
        int gelb = ResourcesCompat.getColor(getResources(), R.color.colorYellowNote, null);
        int orange = ResourcesCompat.getColor(getResources(), R.color.colorOrangeNote, null);
        int rot = ResourcesCompat.getColor(getResources(), R.color.colorRedNote, null);
        int gruen = ResourcesCompat.getColor(getResources(), R.color.colorGreenNote, null);
        int blau = ResourcesCompat.getColor(getResources(), R.color.colorBlueNote, null);
        int lila = ResourcesCompat.getColor(getResources(), R.color.colorPurpleNote, null);
        int farbe = selektierteNotiz.getColor();

        //Überschreibung der Notiz mit neuer Farbe
        if (farbe == grau) {
            selektierteNotiz.setColor(gelb);
        } else if (farbe == gelb){
            selektierteNotiz.setColor(orange);
        } else if (farbe == orange){
            selektierteNotiz.setColor(rot);
        } else if (farbe == rot){
            selektierteNotiz.setColor(lila);
        } else if (farbe == lila){
            selektierteNotiz.setColor(blau);
        } else if (farbe == blau){
            selektierteNotiz.setColor(gruen);
        }  else {
            selektierteNotiz.setColor(grau);
        }

        //Aktualisierung der Datenbank
        db.noteDao().bearbeite(selektierteNotiz);

        //Aktualisierung des Layouts
        recyclerAdapter = new NoteAdapter(db.noteDao().getAll());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerAdapter);
    }
}
