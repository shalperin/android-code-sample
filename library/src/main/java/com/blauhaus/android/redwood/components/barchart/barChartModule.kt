package com.blauhaus.android.redwood.components.barchart

import org.koin.dsl.module


val barChartModule = module {
    single { BarChartViewModel() }
    factory { BarChartFragment() }
}
