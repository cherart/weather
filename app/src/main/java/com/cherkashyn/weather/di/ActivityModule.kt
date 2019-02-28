package com.cherkashyn.weather.di

import com.cherkashyn.weather.ui.activities.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity
}