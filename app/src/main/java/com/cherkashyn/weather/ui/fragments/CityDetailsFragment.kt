package com.cherkashyn.weather.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.ChangeBounds
import com.cherkashyn.weather.R
import com.cherkashyn.weather.model.City
import com.cherkashyn.weather.model.DataHourly
import com.cherkashyn.weather.ui.adapters.CityDaysAdapter
import com.cherkashyn.weather.ui.adapters.HoursListAdapter
import com.cherkashyn.weather.utils.getIcon
import com.cherkashyn.weather.utils.isCityDay
import com.cherkashyn.weather.viewmodel.SharedViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_city_details.view.*
import kotlinx.android.synthetic.main.item_expanded_day.view.*
import javax.inject.Inject

class CityDetailsFragment : DaggerFragment() {

    var position: Int = 0
    var cityId: Int = 0

    lateinit var mainView: View

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: SharedViewModel

    @Inject
    lateinit var hoursListAdapter: HoursListAdapter
    @Inject
    lateinit var cityDaysAdapter: CityDaysAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        sharedElementEnterTransition = ChangeBounds().apply {
            duration = 750

        }
        sharedElementReturnTransition = ChangeBounds().apply {
            duration = 750
        }

        mainView = inflater.inflate(R.layout.fragment_city_details, container, false)
        position = arguments!!.getInt("position")
        cityId = arguments!!.getInt("id")

        return mainView
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initViewModel()
        initRecyclerView()
        initCityPage()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory)[SharedViewModel::class.java]
    }


    private fun initRecyclerView() {
        mainView.cityDetailsRecyclerView.apply {
            adapter = cityDaysAdapter
            layoutManager = LinearLayoutManager(activity)
            isNestedScrollingEnabled = false
        }
        adapterSetOnItemClickListener()
    }

    private fun adapterSetOnItemClickListener() {
        cityDaysAdapter.setOnItemClickListener(object : CityDetailsCallback {
            override fun call(position: Int, view: View, dataHourly: List<DataHourly>) {
                view.expandedSubRecyclerView.apply {
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    adapter = HoursListAdapter().apply {
                        setData(dataHourly.subList(position, position + 25))
                    }
                }
            }
        })
    }

    private fun initCityPage() {
        val city = viewModel.getCityWeather(cityId)
        mainView.cityDetails.setCardBackgroundColor(
            if (isCityDay(city)) resources.getColor(R.color.colorArcBackgroundDay) else resources.getColor(
                R.color.colorArcBackgroundNight
            )
        )
        mainView.cityPageSummary.text = city.currently!!.summary
        mainView.cityPageCityName.text = city.name
        mainView.cityPageTemperature.text = city.currently!!.temperature!!.toInt().toString() + "º"
        mainView.cityPageIcon.setImageResource(getIcon(city.currently!!.icon!!, true))

        cityDaysAdapter.setData(city)
    }

    interface CityDetailsCallback {
        fun call(position: Int, view: View, dataHourly: List<DataHourly>)
    }
}