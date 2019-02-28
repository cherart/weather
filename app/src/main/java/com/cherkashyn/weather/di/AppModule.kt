package com.cherkashyn.weather.di

//import com.cherkashyn.weather.db.AppDatabase
import android.content.Context
import android.location.Geocoder
import android.location.LocationManager
import androidx.room.Room
import com.cherkashyn.weather.WeatherApplication
import com.cherkashyn.weather.api.WeatherService
import com.cherkashyn.weather.db.AppDatabase
import com.cherkashyn.weather.db.CityDao
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Provides
    fun provideContext(application: WeatherApplication): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideWeatherService(): WeatherService {
        return Retrofit.Builder()
            .baseUrl("https://api.darksky.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherService::class.java)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "weather").allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    fun provideCityDao(database: AppDatabase): CityDao {
        return database.cityDao()
    }

    @Provides
    @Singleton
    fun provideGeocoder(context: Context) = Geocoder(context, Locale.getDefault())

    @Provides
    @Singleton
    fun provideLocationManager(context: Context): LocationManager {
        return context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }
}