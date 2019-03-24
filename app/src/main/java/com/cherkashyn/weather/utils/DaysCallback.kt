package com.cherkashyn.weather.utils

import android.view.View
import com.cherkashyn.weather.model.DataHourly

interface DaysCallback {
    fun call(position: Int, view: View, dataHourly: List<DataHourly>)
}