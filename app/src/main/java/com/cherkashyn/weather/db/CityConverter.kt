package com.cherkashyn.weather.db

import androidx.room.TypeConverter
import com.cherkashyn.weather.model.DataDaily
import com.cherkashyn.weather.model.DataHourly

object CityConverter {

    @TypeConverter
    @JvmStatic
    fun fromDataHourly(data: List<DataHourly>): String {
        val stringBuilder = StringBuilder()
        data.forEach {
            stringBuilder
                .append(it.icon).append(",")
                .append(it.temperature).append(",")
                .append(it.time).append(",")
        }
        return stringBuilder.toString()
    }

    @TypeConverter
    @JvmStatic
    fun toDataHourly(data: String): List<DataHourly> {
        val list = data.split(",")
        val result = ArrayList<DataHourly>()
        for (i in 0 until list.lastIndex step 3) {
            val dataHourly = DataHourly()
            dataHourly.icon = list[i]
            dataHourly.temperature = list[i + 1].toDouble()
            dataHourly.time = list[i + 2].toInt()
            result.add(dataHourly)
        }
        return result
    }

    @TypeConverter
    @JvmStatic
    fun fromDataDaily(data: List<DataDaily>): String {
        val stringBuilder = StringBuilder()
        data.forEach {
            stringBuilder
                .append(it.icon).append(",")
                .append(it.time).append(",")
                .append(it.humidity).append(",")
                .append(it.windSpeed).append(",")
                .append(it.precipIntensity).append(",")
                .append(it.temperatureMax).append(",")
        }
        return stringBuilder.toString()
    }

    @TypeConverter
    @JvmStatic
    fun toDataDaily(data: String): List<DataDaily> {
        val list = data.split(",")
        val result = ArrayList<DataDaily>()
        for (i in 0 until list.lastIndex step 6) {
            val dataDaily = DataDaily()
            dataDaily.icon = list[i]
            dataDaily.time = list[i + 1].toInt()
            dataDaily.humidity = list[i + 2].toDouble()
            dataDaily.windSpeed = list[i + 3].toDouble()
            dataDaily.precipIntensity = list[i + 4].toDouble()
            dataDaily.temperatureMax = list[i + 5].toDouble()
            result.add(dataDaily)
        }
        return result
    }
}