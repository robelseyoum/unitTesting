package com.robelseyoum3.robaunittesting.di;

import androidx.lifecycle.ViewModelProvider;

import com.robelseyoum3.robaunittesting.viewmodels.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory viewModelProviderFactory);
}
