package com.robelseyoum3.robaunittesting.di;

import com.robelseyoum3.robaunittesting.NotesListActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract NotesListActivity contributeNotesListActivity();
}
