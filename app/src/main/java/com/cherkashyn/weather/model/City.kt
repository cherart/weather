package com.cherkashyn.weather.model

import androidx.room.*
import com.cherkashyn.weather.db.CityConverter
import com.google.gson.annotations.SerializedName

@Entity(indices = [Index(value = ["name"], unique = true)])
@TypeConverters(CityConverter::class)
class City {

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    var name: String? = null

    @SerializedName("latitude")
    var latitude: Double? = null

    @SerializedName("longitude")
    var longitude: Double? = null

    @Embedded(prefix = "currently")
    @SerializedName("currently")
    var currently: Currently? = null

    @Embedded(prefix = "hourly")
    @SerializedName("hourly")
    var hourly: Hourly? = null

    @Embedded(prefix = "daily")
    @SerializedName("daily")
    var daily: Daily? = null
}
