package com.cherkashyn.weather.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cherkashyn.weather.R
import com.cherkashyn.weather.model.City
import com.cherkashyn.weather.model.DataDaily
import com.cherkashyn.weather.utils.*
import kotlinx.android.synthetic.main.item_expanded_day.view.*
import javax.inject.Inject

class CityDaysAdapter @Inject constructor() : RecyclerView.Adapter<CityDaysAdapter.ViewHolder>() {

    lateinit var city: City
    lateinit var dataDaily: List<DataDaily>
    lateinit var subView: View
    lateinit var daysCallback: DaysCallback
    lateinit var listener: DaysListOnItemClickListener


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
            daysCallback.call(position, holder.itemView, city.hourly!!.dataHourlies!!)
            holder.itemView.setOnClickListener { view ->
                dataDaily[position].expanded = !dataDaily[position].expanded
                notifyItemChanged(position)
                listener.onItemClick(holder.itemView)
            }
        }
    }

    fun setData(city: City) {
        this.city = city
        dataDaily = city.daily!!.dataDailies!!
        notifyDataSetChanged()
    }

    fun setCallback(daysCallback: DaysCallback) {
        this.daysCallback = daysCallback
    }

    fun setOnItemClickListener(listener: DaysListOnItemClickListener) {
        this.listener = listener
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        fun bind(data: DataDaily) {
            val expanded = data.expanded
            itemView.expandedLayout.visibility = if (expanded) View.VISIBLE else View.GONE
            itemView.expandedDayOfWeek.text = getDayOfWeek(data.time!!)
            itemView.expandedArrow.animate().rotation(if (expanded) 180F else 0F).start()

            data.apply {

                val temperature: String = temperatureMax!!.toInt().toString() + "º"
                val dayOfWeek: String = getDayOfWeek(time!!)
                val humidity: String = (humidity!! * 100).toInt().toString() + " %"
                val windSpeed: String = Math.round(windSpeed!!).toString() + " м/c"
                val precip: String = Math.round(precipIntensity!!).toString() + " мм/ч"
                val iconColored: Int = getIcon(icon!!, true)
                val iconBlue: Int = getIcon(icon!!, false)

                itemView.apply {
                    expandedTemperature.text = temperature
                    expandedDayOfWeek.text = dayOfWeek
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