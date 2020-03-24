package com.blauhaus.android.redwood.meditationchallenge

import android.app.Application
import com.blauhaus.android.redwood.components.barchart.barChartModule
import com.blauhaus.android.redwood.components.abstractcalendar.lastFourWeeksModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class MainApplication : Application(){

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@MainApplication)
            modules(listOf(lastFourWeeksModule, barChartModule, mainModule))
        }
    }


}