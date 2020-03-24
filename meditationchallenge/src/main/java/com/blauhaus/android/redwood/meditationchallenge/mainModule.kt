package com.blauhaus.android.redwood.meditationchallenge

import com.blauhaus.android.redwood.meditationchallenge.components.meditationchallenge.MeditationChallengeViewModel
import com.blauhaus.android.redwood.meditationchallenge.data.IRepository
import com.blauhaus.android.redwood.meditationchallenge.data.Repository
import com.blauhaus.android.redwood.meditationchallenge.components.statspager.StatsViewModel
import com.blauhaus.android.redwood.meditationchallenge.components.mycircle.MyCircleViewModel
import org.koin.dsl.module


val mainModule = module {
    single { Config() as IConfig}
    single { Repository() as IRepository }
    single { StatsViewModel(get(), get()) }
    single { MyCircleViewModel(get(), get())}
    single { MeditationChallengeViewModel( get() ) }
}
