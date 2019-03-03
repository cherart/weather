package com.cherkashyn.weather.utils

import com.cherkashyn.weather.R
import com.cherkashyn.weather.model.City
import java.text.SimpleDateFormat
import java.util.*

fun getDayOfWeek(unixTime: Int): String {
    val calendar = Calendar.getInstance()
    calendar.time = Date(unixTime * 1000L)
    var day = ""
    when (calendar.get(Calendar.DAY_OF_WEEK)) {
        1 -> day = "Воскресенье"
        2 -> day = "Понедельник"
        3 -> day = "Вторник"
        4 -> day = "Среда"
        5 -> day = "Четверг"
        6 -> day = "Пятница"
        7 -> day = "Суббота"
    }
    return day
}

fun getTime(unixTime: Int): String {
    val formatter = SimpleDateFormat("HH:mm")
    return formatter.format(Date(unixTime * 1000L))
}

fun getIcon(icon: String, colored: Boolean): Int {
    return when (icon) {
        "clear-day" -> if (colored) R.drawable.clear_day_colored else R.drawable.clear_day_blue
        "clear-night" -> if (colored) R.drawable.clear_night_colored else R.drawable.clear_night_blue
        "rain" -> if (colored) R.drawable.rain_colored else R.drawable.rain_blue
        "snow" -> if (colored) R.drawable.snow_colored else R.drawable.snow_blue
        "sleet" -> if (colored) R.drawable.sleet_colored else R.drawable.sleet_blue
        "wind" -> if (colored) R.drawable.wind_colored else R.drawable.wind_blue
        "fog" -> if (colored) R.drawable.fog_colored else R.drawable.fog_blue
        "cloudy" -> if (colored) R.drawable.cloudy_colored else R.drawable.cloudy_blue
        "partly-cloudy-day" -> if (colored) R.drawable.partly_cloudy_day_colored else R.drawable.partly_cloudy_day_blue
        "partly-cloudy-night" -> if (colored) R.drawable.partly_cloudy_night_colored else R.drawable.partly_cloudy_night_blue
        "hail" -> if (colored) R.drawable.hail_colored else R.drawable.hail_blue
        "thunderstorm" -> if (colored) R.drawable.thunderstorm_colored else R.drawable.thunderstorm_blue
        "tornado" -> if (colored) R.drawable.tornado_colored else R.drawable.torando_blue
        else -> R.drawable.clear_day_colored
    }
}

//fun getBackgroundColor(icon: String): Int {
//    return when (icon) {
//        "clear-day" -> R.color.colorBackgroundClearDay
//        "clear-night" -> R.color.colorBackgroundClearNight
//        "rain" -> R.color.colorBackgroundRain
//        "snow" -> R.color.colorBackgroundSnow
//        "sleet" -> R.color.colorBackgroundSleet
//        "wind" -> R.color.colorBackgroundWind
//        "fog" -> R.color.colorBackgroundFog
//        "cloudy" -> R.color.colorBackgroundCloudy
//        "partly-cloudy-day" -> R.color.colorBackgroundPartlyCloudyDay
//        "partly-cloudy-night" -> R.color.colorBackgroundPartlyCloudyNight
//        "hail" -> R.color.colorBackgroundHail
//        "thunderstorm" -> R.color.colorBackgroundThunderstorm
//        "tornado" -> R.color.colorBackgroundTornado
//        else -> R.color.colorBackgroundClearDay
//    }
//}

fun isCityDay(city: City): Boolean {
    return city.currently!!.time!! > city.daily!!.dataDailies!![0].sunriseTime!! &&
            city.currently!!.time!! < city.daily!!.dataDailies!![0].sunsetTime!!
}
