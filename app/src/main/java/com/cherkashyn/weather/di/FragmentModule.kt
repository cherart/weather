package com.cherkashyn.weather.di

import com.cherkashyn.weather.ui.fragments.CitiesListFragment
import com.cherkashyn.weather.ui.fragments.CityDetailsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector(modules = [CitiesListFragmentModule::class])
    abstract fun bindCitiesListFragment(): CitiesListFragment

    @ContributesAndroidInjector(modules = [CityDetailsFragmentModule::class])
    abstract fun bindCityDetailsFragment(): CityDetailsFragment
}