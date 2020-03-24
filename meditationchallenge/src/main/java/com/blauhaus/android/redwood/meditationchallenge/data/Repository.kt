package com.blauhaus.android.redwood.meditationchallenge.data

import androidx.lifecycle.MutableLiveData

class Repository : IRepository {

    // TODO refactor.
    // This should be an object with named fields.  What is the Float? What is the String?
    private val _meditationData = MutableLiveData<List<ADaysWorthOfMeditationData>>()
    //TODO Same Comment
    private val _globalStats = MutableLiveData<GlobalStats>()
    // TODO Same comment.
    private val _myCircle = MutableLiveData<List<MeditationDataByPersonName>>()
    private val _currentSession =MutableLiveData<Content>()
    private val _updates = MutableLiveData<Content>()
    // See this is OK, generic, because it's obvious.
    val _username =  MutableLiveData<String>()

    // TODO see above
    override fun meditationData(): MutableLiveData<List<ADaysWorthOfMeditationData>> {
        return _meditationData
    }

    override fun globalStats() : MutableLiveData<GlobalStats> { return _globalStats }

    override fun myCircle(): MutableLiveData<List<MeditationDataByPersonName>>{
        return _myCircle
    }
    override fun currentSession(): MutableLiveData<Content>{ return _currentSession}
    override fun updates(): MutableLiveData<Content>{ return _updates}

    override fun userName() :MutableLiveData<String> {return _username}

    init { loadData() }

    private fun loadData() {
        _meditationData.postValue(meditationHistoryData)
        _globalStats.postValue(globalStatsData)
        _myCircle.postValue(myCircleData)
        _currentSession.postValue(currentSessionData)
        _updates.postValue(challengeUpdateData)
        _username.postValue(demoUserName)
    }
}

interface IRepository {
    fun meditationData(): MutableLiveData<List<ADaysWorthOfMeditationData>>
    fun globalStats(): MutableLiveData<GlobalStats>
    fun myCircle(): MutableLiveData<List<MeditationDataByPersonName>>
    fun currentSession(): MutableLiveData<Content>
    fun updates(): MutableLiveData<Content>
    fun userName() :MutableLiveData<String>
}