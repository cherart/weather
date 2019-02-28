package com.cherkashyn.weather.model

import androidx.room.Ignore
import com.google.gson.annotations.SerializedName

class DataDaily {

    @Ignore
    var expanded: Boolean = false

    @SerializedName("icon")
    var icon: String? = null
    @SerializedName("time")
    var time: Int? = null
    @SerializedName("humidity")
    var humidity: Double? = null
    @SerializedName("windSpeed")
    var windSpeed: Double? = null
    @SerializedName("precipIntensity")
    var precipIntensity: Double? = null
    @SerializedName("temperatureMax")
    var temperatureMax: Double? = null
    @SerializedName("sunriseTime")
    var sunriseTime: Int? = null
    @SerializedName("sunsetTime")
    var sunsetTime: Int? = null

//    @SerializedName("temperatureMin")
//    var temperatureMin: Double? = null

//    @SerializedName("summary")
//    @Expose
//    var summary: String? = null
//    @SerializedName("moonPhase")
//    @Expose
//    var moonPhase: Double? = null
//    @SerializedName("precipIntensityMax")
//    @Expose
//    var precipIntensityMax: Double? = null
//    @SerializedName("precipIntensityMaxTime")
//    @Expose
//    var precipIntensityMaxTime: Double? = null
//    @SerializedName("precipProbability")
//    @Expose
//    var precipProbability: Double? = null
//    @SerializedName("precipType")
//    @Expose
//    var precipType: String? = null
//    @SerializedName("temperatureHigh")
//    @Expose
//    var temperatureHigh: Double? = null
//    @SerializedName("temperatureHighTime")
//    @Expose
//    var temperatureHighTime: Int? = null
//    @SerializedName("temperatureLow")
//    @Expose
//    var temperatureLow: Double? = null
//    @SerializedName("temperatureLowTime")
//    @Expose
//    var temperatureLowTime: Int? = null
//    @SerializedName("apparentTemperatureHigh")
//    @Expose
//    var apparentTemperatureHigh: Double? = null
//    @SerializedName("apparentTemperatureHighTime")
//    @Expose
//    var apparentTemperatureHighTime: Int? = null
//    @SerializedName("apparentTemperatureLow")
//    @Expose
//    var apparentTemperatureLow: Double? = null
//    @SerializedName("apparentTemperatureLowTime")
//    @Expose
//    var apparentTemperatureLowTime: Int? = null
//    @SerializedName("dewPoint")
//    @Expose
//    var dewPoint: Double? = null

//    @SerializedName("pressure")
//    @Expose
//    var pressure: Double? = null

//    @SerializedName("windGust")
//    @Expose
//    var windGust: Double? = null
//    @SerializedName("windGustTime")
//    @Expose
//    var windGustTime: Int? = null
//    @SerializedName("windBearing")
//    @Expose
//    var windBearing: Int? = null
//    @SerializedName("cloudCover")
//    @Expose
//    var cloudCover: Double? = null
//    @SerializedName("uvIndex")
//    @Expose
//    var uvIndex: Int? = null
//    @SerializedName("uvIndexTime")
//    @Expose
//    var uvIndexTime: Int? = null
//    @SerializedName("visibility")
//    @Expose
//    var visibility: Double? = null
//    @SerializedName("ozone")
//    @Expose
//    var ozone: Double? = null

//    @SerializedName("temperatureMinTime")
//    @Expose
//    var temperatureMinTime: Int? = null

//    @SerializedName("temperatureMaxTime")
//    @Expose
//    var temperatureMaxTime: Int? = null
//    @SerializedName("apparentTemperatureMin")
//    @Expose
//    var apparentTemperatureMin: Double? = null
//    @SerializedName("apparentTemperatureMinTime")
//    @Expose
//    var apparentTemperatureMinTime: Int? = null
//    @SerializedName("apparentTemperatureMax")
//    @Expose
//    var apparentTemperatureMax: Double? = null
//    @SerializedName("apparentTemperatureMaxTime")
//    @Expose
//    var apparentTemperatureMaxTime: Int? = null

}
