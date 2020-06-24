package com.robelseyoum3.robaunittesting.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.robelseyoum3.robaunittesting.ui.note.NoteViewModel;
import com.robelseyoum3.robaunittesting.viewmodels.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory viewModelProviderFactory);

    @Binds
    @IntoMap
    @ViewModelKey(NoteViewModel.class)
    public abstract ViewModel bindNoteViewModel(NoteViewModel noteViewModel);

}
