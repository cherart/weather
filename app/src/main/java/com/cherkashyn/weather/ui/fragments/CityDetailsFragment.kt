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
import com.cherkashyn.weather.R
import com.cherkashyn.weather.model.City
import com.cherkashyn.weather.model.DataHourly
import com.cherkashyn.weather.ui.adapters.CitiesPagesAdapter
import com.cherkashyn.weather.ui.adapters.CityDaysAdapter
import com.cherkashyn.weather.ui.adapters.HoursListAdapter
import com.cherkashyn.weather.viewmodel.SharedViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_city_details.view.*
import kotlinx.android.synthetic.main.item_expanded_day.view.*
import javax.inject.Inject

class CityDetailsFragment : DaggerFragment() {

    var position: Int = 0
    lateinit var liveAllCities: LiveData<List<City>>

    lateinit var mainView: View

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: SharedViewModel

    @Inject
    lateinit var hoursListAdapter: HoursListAdapter
    @Inject
    lateinit var citiesPagesAdapter: CitiesPagesAdapter
    @Inject
    lateinit var cityDaysAdapter: CityDaysAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mainView = inflater.inflate(R.layout.fragment_city_details, container, false)
        position = arguments!!.getInt("position")
        return mainView
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initViewModel()
        initDiscreteScrollView()
        initRecyclerView()
        initLiveAllCities()
        initOnItemChangedListener()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory)[SharedViewModel::class.java]
    }

    private fun initDiscreteScrollView() {
        mainView.cityDetailsDiscreteScrollView.apply {
            adapter = citiesPagesAdapter
            scrollToPosition(3)//TODO
        }
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

    private fun initLiveAllCities() {
        liveAllCities = viewModel.getAllCities().apply {
            observe(viewLifecycleOwner, Observer { cities ->
                citiesPagesAdapter.setData(cities)
            })
        }
    }

    private fun initOnItemChangedListener() {
        mainView.cityDetailsDiscreteScrollView.addOnItemChangedListener { holder, position ->
            val city = citiesPagesAdapter.getData()[position]
            cityDaysAdapter.setData(city)
        }
    }

    interface CityDetailsCallback {
        fun call(position: Int, view: View, dataHourly: List<DataHourly>)
    }
}