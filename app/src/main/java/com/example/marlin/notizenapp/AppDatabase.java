package com.example.marlin.notizenapp;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by Marlin on 04.02.2018.
 * AppDatabase Klasse für die Einbindung von Room.
 */

@Database(entities = {Note.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();
}
