package com.robelseyoum3.robaunittesting.di;

import android.app.Application;

import androidx.room.Dao;
import androidx.room.Room;
import com.robelseyoum3.robaunittesting.persistene.NoteDao;
import com.robelseyoum3.robaunittesting.persistene.NoteDatabase;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static com.robelseyoum3.robaunittesting.persistene.NoteDatabase.DATABASE_NAME;

@Module
public class AppModule {

    @Singleton
    @Provides
    static NoteDatabase providesNoteDatabase(Application application){
        return Room.databaseBuilder(
                application,
                NoteDatabase.class,
                DATABASE_NAME
                ).build();
    }

    @Singleton
    @Provides
    static NoteDao provideNoteDao(NoteDatabase noteDatabase){
        return  noteDatabase.getNoteDao();
    }


}
