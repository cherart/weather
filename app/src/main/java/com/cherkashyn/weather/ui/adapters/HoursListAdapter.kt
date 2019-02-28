package com.cherkashyn.weather.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cherkashyn.weather.R
import com.cherkashyn.weather.model.DataHourly
import com.cherkashyn.weather.utils.getIcon
import com.cherkashyn.weather.utils.getTime
import kotlinx.android.synthetic.main.item_hour.view.*
import javax.inject.Inject

class HoursListAdapter @Inject constructor() : RecyclerView.Adapter<HoursListAdapter.ViewHolder>() {

    private var dataHourly: List<DataHourly> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_hour, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataHourly.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.hourTemperature.text = dataHourly[position].temperature!!.toInt().toString() + "º"
        holder.itemView.hourTime.text = getTime(dataHourly[position].time!!)
        holder.itemView.hourIcon.setImageResource(getIcon(dataHourly[position].icon!!, true))
    }

    fun setData(dataHourly: List<DataHourly>) {
        this.dataHourly = dataHourly
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}