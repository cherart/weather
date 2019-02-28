package com.cherkashyn.weather.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cherkashyn.weather.R
import com.cherkashyn.weather.model.City
import com.cherkashyn.weather.model.Currently
import com.cherkashyn.weather.model.DataHourly
import com.cherkashyn.weather.utils.getIcon
import kotlinx.android.synthetic.main.item_city_page.view.*
import javax.inject.Inject

class CitiesPagesAdapter @Inject constructor() : RecyclerView.Adapter<CitiesPagesAdapter.ViewHolder>() {

    lateinit var cities: List<City>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_city_page, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (::cities.isInitialized)
            cities.count()
        else
            0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cities[position])
    }

    fun setData(cities: List<City>) {
        this.cities = cities
        notifyDataSetChanged()
    }

    fun getData(): List<City> {
        return cities
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(city: City) {
            itemView.cityPageSummary.text = city.currently!!.summary
            itemView.cityPageCityName.text = city.name
            itemView.cityPageTemperature.text = city.currently!!.temperature!!.toInt().toString() + "º"
            itemView.cityPageIcon.setImageResource(getIcon(city.currently!!.icon!!, false))
        }
    }
}