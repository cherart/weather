package com.cherkashyn.weather.di

import com.cherkashyn.weather.WeatherApplication
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityModule::class,
        FragmentModule::class]
)
interface AppComponent : AndroidInjector<WeatherApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<WeatherApplication>()
}