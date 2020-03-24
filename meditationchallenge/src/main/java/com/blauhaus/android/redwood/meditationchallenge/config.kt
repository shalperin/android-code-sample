package com.blauhaus.android.redwood.meditationchallenge


class Config: IConfig {
    override fun achievementThresholdInDays() = 14
    override fun goldMedalThresholdInMinutes() = 25
    override fun silverMedalThresholdInMinutes() = 15
    override fun bronzeMedalThresholdInMinutes() = 10
}

interface IConfig {
    fun achievementThresholdInDays():Int
    fun goldMedalThresholdInMinutes() :Int
    fun silverMedalThresholdInMinutes():Int
    fun bronzeMedalThresholdInMinutes() :Int
}
