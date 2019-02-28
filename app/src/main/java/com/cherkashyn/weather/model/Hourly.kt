package com.cherkashyn.weather.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Hourly {

    @SerializedName("icon")
    @Expose
    var icon: String? = null

    @SerializedName("data")
    @Expose
    var dataHourlies: List<DataHourly>? = null

}
