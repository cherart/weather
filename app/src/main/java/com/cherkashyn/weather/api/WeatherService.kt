package com.cherkashyn.weather.api

import com.cherkashyn.weather.model.City
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherService {

    @GET("forecast/e93305f783c3a0fb83203fdc88a7ded2/{lat},{lon}?exclude=flags,alerts,offset&units=si&extend=hourly&lang=ru")
    fun getCityWeather(@Path("lat") lat: String, @Path("lon") lon: String): Call<City>

    @GET("forecast/e93305f783c3a0fb83203fdc88a7ded2/{lat},{lon}?exclude=minutely,flags,alerts,daily,hourly,offset")
    fun getCurrentCityWeather(@Path("lat") lat: String, @Path("lon") lon: String): Call<City>

    @GET("forecast/e93305f783c3a0fb83203fdc88a7ded2/{lat},{lon}?exclude=minutely,flags,alerts,daily,currently,offset")
    fun getHourlyCityWeather(@Path("lat") lat: String, @Path("lon") lon: String): Call<City>

    @GET("forecast/e93305f783c3a0fb83203fdc88a7ded2/{lat},{lon}?exclude=minutely,flags,alerts,hourly,currently,offset")
    fun getDailyCityWeather(@Path("lat") lat: String, @Path("lon") lon: String): Call<City>
}