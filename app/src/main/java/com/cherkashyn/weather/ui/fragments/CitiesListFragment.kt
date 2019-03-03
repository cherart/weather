package com.cherkashyn.weather.ui.fragments

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.transition.ChangeBounds
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.recyclerview.widget.LinearLayoutManager
import com.cherkashyn.weather.R
import com.cherkashyn.weather.model.City
import com.cherkashyn.weather.ui.adapters.CitiesListAdapter
import com.cherkashyn.weather.ui.adapters.HoursListAdapter
import com.cherkashyn.weather.utils.getDayOfWeek
import com.cherkashyn.weather.utils.getIcon
import com.cherkashyn.weather.utils.getTime
import com.cherkashyn.weather.utils.isCityDay
import com.cherkashyn.weather.viewmodel.SharedViewModel
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.yarolegovich.discretescrollview.transform.DiscreteScrollItemTransformer
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_cities_list.view.*
import kotlinx.android.synthetic.main.item_city.view.*
import kotlinx.android.synthetic.main.item_expanded_day.*
import kotlinx.android.synthetic.main.item_expanded_day.view.*
import java.util.*
import javax.inject.Inject


class CitiesListFragment : DaggerFragment() {

    var isFirst = true
    var color = 0
    var image = 0

    lateinit var mainView: View

    lateinit var liveAllCities: LiveData<List<City>>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: SharedViewModel

    @Inject
    lateinit var hoursListAdapter: HoursListAdapter
    @Inject
    lateinit var citiesListAdapter: CitiesListAdapter
    @Inject
    lateinit var discreteScrollItemTransformer: DiscreteScrollItemTransformer
    @Inject
    lateinit var locationManager: LocationManager
    @Inject
    lateinit var placesClient: PlacesClient

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_toolbar, menu)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mainView = inflater.inflate(R.layout.fragment_cities_list, container, false)
        setHasOptionsMenu(true)

        if (isFirst) {
            mainView.cardTransition.visibility = View.GONE
            isFirst = false
        } else {
            mainView.imageTransition.apply {
                setImageResource(image)
                postDelayed({
                    setImageDrawable(null)
                }, 800)
            }
            mainView.cardTransition.apply {
                setCardBackgroundColor(color)
                postDelayed({
                    visibility = View.GONE
                }, 800)
            }
        }

        return mainView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initViewModel()
        initDiscreteScrollView()
        initRecyclerView()
        initLocationManager()
        initPlaceAutocomplete()
        initLiveAllCities()
        initOnItemChangedListener()
        (activity as AppCompatActivity).apply {
            setSupportActionBar(mainView.citiesListToolbar)
            supportActionBar!!.setDisplayShowTitleEnabled(false)
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory)[SharedViewModel::class.java]
    }

    private fun initDiscreteScrollView() {
        mainView.citiesListDiscreteScrollView.apply {
            adapter = citiesListAdapter
            setItemTransformer(discreteScrollItemTransformer)
            setSlideOnFling(true)
        }
        adapterSetOnItemClickListener()
    }

    private fun adapterSetOnItemClickListener() {
        citiesListAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(position: Int, itemView: View, city: City) {
                if (mainView.citiesListDiscreteScrollView.currentItem == position) {
                    val bundle = Bundle()
                    bundle.putInt("position", position)
                    bundle.putInt("id", city.id!!)

                    image = getIcon(city.currently!!.icon!!, true)
                    mainView.imageTransition.setImageResource(image)
                    mainView.cardTransition.apply {
                        color =
                            resources.getColor(if (isCityDay(city)) R.color.colorArcBackgroundDay else R.color.colorArcBackgroundNight)
                        setCardBackgroundColor(color)
                        visibility = View.VISIBLE
                    }

                    val extras = FragmentNavigator.Extras.Builder()
                        .addSharedElement(mainView.imageTransition, "secondTransition")
                        .addSharedElement(mainView.cardTransition, "sixTransition")
                        .build()

                    activity?.findNavController(R.id.navHostFragment)
                        ?.navigate(R.id.cityDetailsFragment, bundle, null, extras)
                }
            }
        })
    }

    private fun initRecyclerView() {
        mainView.expandedSubRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = hoursListAdapter
        }
    }

    @SuppressLint("MissingPermission")//TODO
    private fun initLocationManager() {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10f, object : LocationListener {
            override fun onLocationChanged(location: Location?) {
                viewModel.addCity(location!!.latitude, location.longitude, true)
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
            override fun onProviderEnabled(provider: String?) {}
            override fun onProviderDisabled(provider: String?) {}
        })
    }

    private fun initPlaceAutocomplete() {
        val autocomplete: AutocompleteSupportFragment =
            childFragmentManager.findFragmentById(R.id.citiesListAutocompleteFragment) as AutocompleteSupportFragment
        mainView.findViewById<ImageView>(R.id.places_autocomplete_search_button).apply {
            setImageResource(R.drawable.ic_add_24px)
            val typedValue = TypedValue()
            activity!!.theme.resolveAttribute(android.R.attr.actionBarItemBackground, typedValue, true)
            if (typedValue.resourceId != 0) {
                this.setBackgroundResource(typedValue.resourceId)
            } else {
                this.setBackgroundColor(typedValue.data)
            }
        }

        autocomplete.apply {
            a.visibility = View.GONE
            setPlaceFields(Arrays.asList(Place.Field.LAT_LNG))
            setOnPlaceSelectedListener(object : PlaceSelectionListener {

                override fun onPlaceSelected(place: Place) {
                    val latLng = place.latLng!!
                    viewModel.addCity(latLng.latitude, latLng.longitude, false)
                }

                override fun onError(status: Status) {}
            })
        }
    }

    private fun initLiveAllCities() {
        liveAllCities = viewModel.getAllCities().apply {
            observe(viewLifecycleOwner, androidx.lifecycle.Observer { cities ->
                citiesListAdapter.setData(cities)
            })
        }
    }

    private fun initOnItemChangedListener() {
        var colorPrevious = ContextCompat.getColor(context!!, R.color.colorArcBackgroundDay)

        mainView.citiesListDiscreteScrollView.addOnItemChangedListener { holder, position ->

            if (citiesListAdapter.getData().isNotEmpty()) {
                val city = citiesListAdapter.getData()[position]

                mainView.cityNameBig.text = city.name

                val layerDrawable: LayerDrawable = mainView.arcView.background as LayerDrawable
                val drawable: GradientDrawable = layerDrawable.findDrawableByLayerId(R.id.arc) as GradientDrawable
                val colorCurrent = ContextCompat.getColor(
                    context!!,
                    if (isCityDay(city)) R.color.colorArcBackgroundDay else R.color.colorArcBackgroundNight
                )
                ValueAnimator.ofObject(ArgbEvaluator(), colorPrevious, colorCurrent).apply {
                    duration = 300
                    addUpdateListener { animation ->
                        mainView.citiesListToolbar.setBackgroundColor(animation.animatedValue as Int)
                        activity!!.window.statusBarColor = animation.animatedValue as Int
                        drawable.setColor(animation.animatedValue as Int)
                    }
                    start()
                }
                colorPrevious = colorCurrent



                if (System.currentTimeMillis() / 1000L - city.currently!!.time!! > 2000) {
                    viewModel.refresh(city.latitude!!, city.longitude!!, city.id!!)
                } else {
                    with(city.currently!!) {

                        val temperature: String = temperature?.toInt().toString() + "º"
                        val dayOfWeek: String = getDayOfWeek(time!!)
                        val time: String = getTime(time!!)
                        val humidity: String = (humidity!! * 100).toInt().toString() + " %"
                        val windSpeed: String = windSpeed.toString() + " м/c"
                        val precip: String = precipIntensity.toString() + " мм/ч"
                        val iconColored: Int = getIcon(icon!!, true)
                        val iconBlue: Int = getIcon(icon!!, false)

                        expandedTemperature.text = temperature
                        expandedDayOfWeek.text = "Сейчас"
                        expandedSubDayOfWeek.text = dayOfWeek
                        expandedSubTime.text = time
                        expandedSubTemperature.text = temperature
                        expandedSubHumidityValue.text = humidity
                        expandedSubWindValue.text = windSpeed
                        expandedSubPrecipitationValue.text = precip
                        expandedIcon.setImageResource(iconColored)
                        expandedSubIcon.setImageResource(iconBlue)
                    }

                    with(city.hourly!!) {
                        hoursListAdapter.setData(dataHourlies!!.subList(0, 25))
                    }
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, itemView: View, city: City)
    }
}