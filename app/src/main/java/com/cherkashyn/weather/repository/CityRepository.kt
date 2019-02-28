package com.cherkashyn.weather.repository

import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cherkashyn.weather.api.WeatherService
import com.cherkashyn.weather.db.CityDao
import com.cherkashyn.weather.model.City
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class CityRepository @Inject constructor(var webservice: WeatherService, var cityDao: CityDao, var geocoder: Geocoder) {

    fun getAllCities(): LiveData<List<City>> {
        return cityDao.getAllCities()
    }

    fun addCity(lat: Double, lon: Double, cur: Boolean) {
        getRemoteWeather(lat, lon, cur)
    }

    fun getRemoteWeather(lat: Double, lon: Double, cur: Boolean) {
        webservice
            .getCityWeather(lat.toString(), lon.toString())
            .enqueue(object : Callback<City> {
                override fun onFailure(call: Call<City>, t: Throwable) {}

                override fun onResponse(call: Call<City>, response: Response<City>) {
                    if (response.code() == 200) {
                        cityDao.insertCity(response.body()!!.apply {
                            name = geocoder.getFromLocation(lat, lon, 1)[0].locality
                            if (cur) {
                                id = 0
                                name += " "
                            }
                        })
                    }
                }
            })
    }
}