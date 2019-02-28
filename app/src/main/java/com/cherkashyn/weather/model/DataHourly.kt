package com.cherkashyn.weather.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DataHourly {

    @SerializedName("time")
    @Expose
    var time: Int? = null
//    @SerializedName("summary")
//    @Expose
//    var summary: String? = null
    @SerializedName("icon")
    @Expose
    var icon: String? = null
//    @SerializedName("precipIntensity")
//    @Expose
//    var precipIntensity: Double? = null
//    @SerializedName("precipProbability")
//    @Expose
//    var precipProbability: Double? = null
//    @SerializedName("precipType")
//    @Expose
//    var precipType: String? = null
    @SerializedName("temperature")
    @Expose
    var temperature: Double? = null
//    @SerializedName("apparentTemperature")
//    @Expose
//    var apparentTemperature: Double? = null
//    @SerializedName("dewPoint")
//    @Expose
//    var dewPoint: Double? = null
//    @SerializedName("humidity")
//    @Expose
//    var humidity: Double? = null
//    @SerializedName("pressure")
//    @Expose
//    var pressure: Double? = null
//    @SerializedName("windSpeed")
//    @Expose
//    var windSpeed: Double? = null
//    @SerializedName("windGust")
//    @Expose
//    var windGust: Double? = null
//    @SerializedName("windBearing")
//    @Expose
//    var windBearing: Int? = null
//    @SerializedName("cloudCover")
//    @Expose
//    var cloudCover: Double? = null
//    @SerializedName("uvIndex")
//    @Expose
//    var uvIndex: Int? = null
//    @SerializedName("visibility")
//    @Expose
//    var visibility: Double? = null
//    @SerializedName("ozone")
//    @Expose
//    var ozone: Double? = null

}
