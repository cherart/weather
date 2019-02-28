package com.cherkashyn.weather.ui.adapters

import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.cherkashyn.weather.R
import com.cherkashyn.weather.model.City
import com.cherkashyn.weather.ui.fragments.CitiesListFragment
import com.cherkashyn.weather.utils.getBackgroundColor
import com.cherkashyn.weather.utils.getIcon
import kotlinx.android.synthetic.main.item_city.view.*
import javax.inject.Inject

class CitiesListAdapter @Inject constructor() : RecyclerView.Adapter<CitiesListAdapter.ViewHolder>() {

    lateinit var cities: List<City>
    private lateinit var listener: CitiesListFragment.OnItemClickListener
    private lateinit var callback: CitiesListFragment.Callback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (::cities.isInitialized)
            cities.count()
        else
            0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (::cities.isInitialized && ::listener.isInitialized)
            holder.bind(cities[position], listener, position, callback)
    }

    fun setData(cities: List<City>) {
        this.cities = cities
        notifyDataSetChanged()
    }

    fun getData(): List<City> {
        return cities
    }

    fun setOnItemClickListener(listener: CitiesListFragment.OnItemClickListener) {
        this.listener = listener
    }

    fun setCallback(callback: CitiesListFragment.Callback) {
        this.callback = callback
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(
            city: City,
            listener: CitiesListFragment.OnItemClickListener,
            position: Int,
            callback: CitiesListFragment.Callback
        ) {
            itemView.cityName.text = city.name
            itemView.cityViewIcon.setImageResource(getIcon(city.currently!!.icon!!, true))
            itemView.setOnClickListener { listener.onItemClick(position) }
            callback.callback(city.currently!!.icon!!)
        }
    }
}