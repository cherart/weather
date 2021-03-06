package com.cherkashyn.weather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.cherkashyn.weather.model.City
import com.cherkashyn.weather.repository.CityRepository
import javax.inject.Inject

class SharedViewModel @Inject constructor(var cityRepository: CityRepository) : ViewModel() {

    fun refresh(lat: Double, lon: Double, id: Int) {
        cityRepository.refreshRemoteWeather(lat, lon, id)
    }

    fun getAllCities(): LiveData<List<City>> {
        return cityRepository.getAllCities()
    }

    fun addCity(lat: Double, lon: Double, cur: Boolean) {
        cityRepository.addCity(lat, lon, cur)
    }

    fun getCityWeather(id: Int): City {
        return cityRepository.getCityWeather(id)
    }

    fun deleteCity(city: City) {
        cityRepository.deleteCity(city)
    }
}