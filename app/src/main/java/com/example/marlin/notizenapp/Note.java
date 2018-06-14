package com.example.marlin.notizenapp;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Marlin on 04.02.2018.
 * Klasse zur Erzeugung von Note Objekten, die sämtliche Informationen für die Datenbank enthalten
 * und durch Abfrage- und Änderungsmethoden manipulierbar sind.
 */

@Entity
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "notiz")
    String note;

    @ColumnInfo(name = "datum")
    String date;

    @ColumnInfo(name = "farbe")
    int color;


    public Note(String note, String date, int color) {
        this.note = note;
        this.date = date;
        this.color = color;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
