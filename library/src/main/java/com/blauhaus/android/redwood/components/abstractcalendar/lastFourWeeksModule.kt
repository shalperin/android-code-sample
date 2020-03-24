package com.blauhaus.android.redwood.components.abstractcalendar

import org.koin.dsl.module


val lastFourWeeksModule = module {
    single { LastFourWeeksViewModel() }
    factory { LastFourWeeksFragment() }
}
