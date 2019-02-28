package com.cherkashyn.weather.model

import com.google.gson.annotations.SerializedName

class Daily {

    @SerializedName("icon")
    var icon: String? = null
    @SerializedName("data")
    var dataDailies: List<DataDaily>? = null

}
