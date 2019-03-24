package com.cherkashyn.weather.utils

import android.view.View
import com.cherkashyn.weather.model.City

interface CitiesListOnItemClickListener {
    fun onItemClick(position: Int, itemView: View, city: City)
}