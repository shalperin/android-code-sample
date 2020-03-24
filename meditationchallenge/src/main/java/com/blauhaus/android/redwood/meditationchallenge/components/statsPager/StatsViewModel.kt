package com.blauhaus.android.redwood.meditationchallenge.components.statspager

import androidx.lifecycle.*
import com.blauhaus.android.redwood.components.abstractcalendar.DayView
import com.blauhaus.android.redwood.meditationchallenge.*
import com.blauhaus.android.redwood.meditationchallenge.data.IRepository

class StatsViewModel(val repo: IRepository, val config:IConfig): ViewModel() {

    val lastFourWeeksBackingModel: LiveData<List<DayView.ViewState>> =
        Transformations.map(repo.meditationData()) { data ->
            val adapted = data.map {
                if (it.minutesMeditated == 0f) {
                    DayView.ViewState.Skipped()
                } else {
                    DayView.ViewState.Met()
                }
            }.toMutableList()
            adapted.add(DayView.ViewState.DidntMeetYetToday())
            while (adapted.size <= 28) {
                adapted.add(DayView.ViewState.Future())
            }
            adapted
        }

    val barChartBackingModel = Transformations.map(repo.meditationData()) {
        it.map {
            // Pair(bar height: Float, bar label: String)
            Pair(it.minutesMeditated, it.description)
        }
    }

    val totalDaysMeditated: LiveData<Int> =
        Transformations.map(lastFourWeeksBackingModel) {
            it.filterIsInstance<DayView.ViewState.Met>().size -1
        }

    val averageMinutesMeditated: LiveData<Int> =
        Transformations.map(repo.meditationData()) {
            it.map{it.minutesMeditated}.filter{it != 0f}.average().toInt()
        }

    val medalClass = MediatorLiveData<Pair<MedalClass, MedalProgress>>()
    private fun combineMetalClassData(
        daysMeditated: LiveData<Int>,
        avgMinutesMeditated: LiveData<Int>
    ){

        val days:Int? = daysMeditated.value
        val avg:Int? = avgMinutesMeditated.value

        if (days == null || avg == null) {
            medalClass.value = Pair(
                MedalClass.None(),
                MedalProgress.No()
            )
            return
        } else {
            val _medalClass = if (avg >= config.goldMedalThresholdInMinutes()) {
                MedalClass.Gold()
            } else if (avg >= config.silverMedalThresholdInMinutes()) {
                MedalClass.Silver()
            } else if (avg >= config.bronzeMedalThresholdInMinutes()) {
                MedalClass.Bronze()
            } else {
                MedalClass.None()
            }

            val medalProgress = if (days >= config.achievementThresholdInDays()) {
                MedalProgress.Got()
            } else if (_medalClass is MedalClass.None) {
                MedalProgress.No()
            } else {
                MedalProgress.OnTrack()
            }

            medalClass.value = Pair(_medalClass, medalProgress)
        }
    }

    //TODO get rid of these indecies and replace with data class.
    val globalParticipants = Transformations.map(repo.globalStats()){ it.participants}
    val globalMinutes = Transformations.map(repo.globalStats()){ it.totalMinutes}
    val globalAverage = Transformations.map(repo.globalStats()){ it.avgMinutesPerSesssion}


    init {
        medalClass.addSource(totalDaysMeditated) { combineMetalClassData(totalDaysMeditated, averageMinutesMeditated) }
        medalClass.addSource(averageMinutesMeditated) { combineMetalClassData(totalDaysMeditated, averageMinutesMeditated) }
    }

    sealed class MedalClass() {
        class Gold(): MedalClass()
        class Silver(): MedalClass()
        class Bronze(): MedalClass()
        class None(): MedalClass()
    }

    sealed class MedalProgress() {
         class Got() : MedalProgress()
         class OnTrack(): MedalProgress()
        class No() : MedalProgress()
    }
}
