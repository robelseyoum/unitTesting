package com.robelseyoum3.robaunittesting.di;

import com.robelseyoum3.robaunittesting.ui.note.NoteActivity;
import com.robelseyoum3.robaunittesting.ui.notelist.NotesListActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract NotesListActivity contributeNotesListActivity();

    @ContributesAndroidInjector
    abstract NoteActivity contributeNotesActivity();
}
