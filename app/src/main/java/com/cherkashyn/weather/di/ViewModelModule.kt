package com.cherkashyn.weather.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cherkashyn.weather.viewmodel.SharedViewModel
import com.cherkashyn.weather.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SharedViewModel::class)
    abstract fun bindCitiesListViewModel(viewModel: SharedViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}