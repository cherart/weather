package com.cherkashyn.weather.ui.adapters

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.cherkashyn.weather.R
import com.cherkashyn.weather.model.City
import com.cherkashyn.weather.ui.fragments.CitiesListFragment
import com.cherkashyn.weather.utils.getIcon
import com.cherkashyn.weather.utils.isCityDay
import kotlinx.android.synthetic.main.item_city.view.*
import javax.inject.Inject

class CitiesListAdapter @Inject constructor() : RecyclerView.Adapter<CitiesListAdapter.ViewHolder>() {

    lateinit var cities: List<City>
    private lateinit var listener: CitiesListFragment.OnItemClickListener

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
        if (::cities.isInitialized && ::listener.isInitialized) {
            ViewCompat.setTransitionName(holder.itemView.cityViewIcon, cities[position].name)
            holder.bind(cities[position], listener, position)
        }
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

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(
            city: City,
            listener: CitiesListFragment.OnItemClickListener,
            position: Int
        ) {
            itemView.cityName.text = city.name
            itemView.cityViewIcon.setImageResource(getIcon(city.currently!!.icon!!, true))
            itemView.setOnClickListener { listener.onItemClick(position, itemView, city) }
            itemView.cityCardView.apply {
                val currentColor = cardBackgroundColor.defaultColor
                val nextColor =
                    if (isCityDay(city))
                        Color.parseColor("#2196F3")
                    else
                        Color.parseColor("#3F51B5")
                if (currentColor != nextColor) {
                    ValueAnimator.ofObject(ArgbEvaluator(), currentColor, nextColor).apply {
                        duration = 300
                        addUpdateListener { animation ->
                            setCardBackgroundColor(animation.animatedValue as Int)
                        }
                        start()
                    }
                }
            }
        }
    }
}