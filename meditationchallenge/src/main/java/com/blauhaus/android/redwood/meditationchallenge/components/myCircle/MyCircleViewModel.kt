package com.blauhaus.android.redwood.meditationchallenge.components.mycircle

import androidx.lifecycle.*
import com.blauhaus.android.redwood.meditationchallenge.IConfig
import com.blauhaus.android.redwood.meditationchallenge.data.IRepository
import com.blauhaus.android.redwood.meditationchallenge.data.ADaysWorthOfMeditationData
import com.blauhaus.android.redwood.meditationchallenge.data.MeditationDataByPersonName
import com.blauhaus.android.redwood.meditationchallenge.data.PersonInCircle

class MyCircleViewModel(private val repo: IRepository, private val config: IConfig) : ViewModel() {

    private val _myCircleBackingData = MediatorLiveData<List<MeditationDataByPersonName>>()
    private fun combineMediationData(
        _me: LiveData<MeditationDataByPersonName>,
        _friends: LiveData<List<MeditationDataByPersonName>>) {

        val me = _me.value
        val friends = _friends.value
        
        if (me == null || friends == null) {
            return
        } else {
            _myCircleBackingData.value = listOf(me).union(friends).toList()
        }
    }
    
    val _me = MediatorLiveData<MeditationDataByPersonName>()
    private fun combineMyData(
        _myData: LiveData<List<ADaysWorthOfMeditationData>>,
        _myUsername: LiveData<String>
        ) {

        val myData = _myData.value
        val myUsername = _myUsername.value

        if (myData == null || myUsername == null) {
            return
        } else {
            _me.value =
                MeditationDataByPersonName(
                    myUsername,
                    myData
                )
        }
    }

    //TODO needs test.
    fun myCircle():LiveData<List<PersonInCircle>> {
        return Transformations.map(_myCircleBackingData){
            it.map{
                val name:String = it.name
                val days:Int = it.meditationData.filter{
                    it.minutesMeditated > 0
                }.size
                val avg:Int = it.meditationData.map{it.minutesMeditated}.average().toInt()
                val didCompleteChallenge = days > config.achievementThresholdInDays()

                //geeze paranoid of truncation or what?
                val progress = (days.toFloat() / config.achievementThresholdInDays().toFloat() * 100.00).toInt()

                PersonInCircle(
                    name,
                    days,
                    avg,
                    didCompleteChallenge,
                    progress
                )
            }
        }
    }

    init{
        _me.addSource(repo.userName()) {
            combineMyData(repo.meditationData(), repo.userName())
        }
        _me.addSource(repo.meditationData()) {
            combineMyData(repo.meditationData(), repo.userName())
        }

        _myCircleBackingData.addSource(repo.myCircle()) {
            combineMediationData(_me, repo.myCircle())
        }
        _myCircleBackingData.addSource(_me) {
            combineMediationData(_me, repo.myCircle())
        }
    }
}