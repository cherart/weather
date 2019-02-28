package com.cherkashyn.weather.di

import android.content.Context
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.yarolegovich.discretescrollview.transform.DiscreteScrollItemTransformer
import com.yarolegovich.discretescrollview.transform.Pivot
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import dagger.Module
import dagger.Provides

@Module
class CitiesListFragmentModule {

    @Provides
    fun provideDiscreteScrollItemTransformer(): DiscreteScrollItemTransformer =
        ScaleTransformer.Builder()
            .setMaxScale(1.05f)
            .setMinScale(0.75f)
            .setPivotX(Pivot.X.CENTER)
            .setPivotY(Pivot.Y.TOP)
            .build()

    @Provides
    fun providePlacesClient(context: Context): PlacesClient {
        Places.initialize(context, "AIzaSyATFPUoGC8-p1d_5exQJJAlGW1UzAiBXBA")
        return Places.createClient(context)
    }
}
