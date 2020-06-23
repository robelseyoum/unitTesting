package com.robelseyoum3.robaunittesting.persistene;

import androidx.room.RoomDatabase;

public abstract class NoteDatabase extends RoomDatabase {

    public static final String DATABASE_NAME ="notes_db";

    public abstract NoteDao getNoteDao();
}
