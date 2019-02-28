package com.cherkashyn.weather.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cherkashyn.weather.model.City

@Database(entities = [City::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao
}