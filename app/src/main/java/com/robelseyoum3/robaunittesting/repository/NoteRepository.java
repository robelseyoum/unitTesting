package com.robelseyoum3.robaunittesting.repository;

import androidx.annotation.NonNull;

import com.robelseyoum3.robaunittesting.persistene.NoteDao;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class NoteRepository {

    @NonNull
    private final NoteDao noteDao;

    @Inject
    public NoteRepository(@NonNull NoteDao noteDao) {
        this.noteDao = noteDao;
    }

}
