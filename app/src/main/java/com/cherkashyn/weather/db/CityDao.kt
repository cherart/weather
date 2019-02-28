package com.cherkashyn.weather.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.cherkashyn.weather.model.City

@Dao
interface CityDao {

    @Query("SELECT * FROM city")
    fun getAllCities(): LiveData<List<City>>

    @Query("SELECT * FROM city WHERE id = :id")
    fun getCityById(id: Int): City

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCity(city: City)

    @Update
    fun updateCity(city: City)

    @Delete
    fun deleteCity(city: City)
}