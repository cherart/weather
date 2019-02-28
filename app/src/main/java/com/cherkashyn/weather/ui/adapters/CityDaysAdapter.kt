package com.cherkashyn.weather.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cherkashyn.weather.R
import com.cherkashyn.weather.model.City
import com.cherkashyn.weather.model.DataDaily
import com.cherkashyn.weather.ui.fragments.CityDetailsFragment
import com.cherkashyn.weather.utils.getDayOfWeek
import com.cherkashyn.weather.utils.getIcon
import com.cherkashyn.weather.utils.getTime
import kotlinx.android.synthetic.main.item_expanded_day.view.*
import javax.inject.Inject

class CityDaysAdapter @Inject constructor() : RecyclerView.Adapter<CityDaysAdapter.ViewHolder>() {

    lateinit var city: City
    lateinit var dataDaily: List<DataDaily>
    lateinit var subView: View
    lateinit var callback: CityDetailsFragment.CityDetailsCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_expanded_day, parent, false)
        subView = view.findViewById<View>(R.id.expandedLayout)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (::city.isInitialized) {
            city.daily!!.dataDailies!!.size
        } else {
            0
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (::city.isInitialized) {
            holder.bind(dataDaily[position])
            callback.call(position, holder.itemView, city.hourly!!.dataHourlies!!)
            holder.itemView.setOnClickListener { view ->
                dataDaily[position].expanded = !dataDaily[position].expanded
                notifyItemChanged(position)
            }
        }
    }

    fun setData(city: City) {
        this.city = city
        dataDaily = city.daily!!.dataDailies!!
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(callback: CityDetailsFragment.CityDetailsCallback) {
        this.callback = callback
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: DataDaily) {
            val expanded = data.expanded
            itemView.expandedLayout.visibility = if (expanded) View.VISIBLE else View.GONE
            itemView.expandedDayOfWeek.text = getDayOfWeek(data.time!!)
            data.apply {

                val temperature: String = temperatureMax!!.toInt().toString() + "º"
                val dayOfWeek: String = getDayOfWeek(time!!)
                val time: String = getTime(time!!)
                val humidity: String = (humidity!! * 100).toInt().toString() + " %"
                val windSpeed: String = windSpeed.toString() + " м/c"
                val precip: String = precipIntensity.toString() + " мм/ч"
                val iconColored: Int = getIcon(icon!!, true)
                val iconBlue: Int = getIcon(icon!!, false)

                itemView.apply {
                    expandedTemperature.text = temperature
                    expandedDayOfWeek.text = dayOfWeek
                    expandedSubDayOfWeek.text = dayOfWeek
                    expandedSubTime.text = time
                    expandedSubTemperature.text = temperature
                    expandedSubHumidityValue.text = humidity
                    expandedSubWindValue.text = windSpeed
                    expandedSubPrecipitationValue.text = precip
                    expandedIcon.setImageResource(iconColored)
                    expandedSubIcon.setImageResource(iconBlue)
                }
            }
        }
    }
}