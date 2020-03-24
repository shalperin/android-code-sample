package com.blauhaus.android.redwood.meditationchallenge.data

data class Content (
    val image: Int,
    val title: String,
    val pretitle: String,
    val description:String,
    val audioId:Int? = null,
    val videoId:String? = null) {
 fun isPlayable() : Boolean {
     return audioId != null || videoId != null
 }

}

data class ADaysWorthOfMeditationData (
    val minutesMeditated: Float,
    val description: String
)

data class MeditationDataByPersonName (
    val name: String,
    val meditationData: List<ADaysWorthOfMeditationData>
)

data class PersonInCircle(val fname:String, val days:Int, val avg:Int,
                          val didCompleteChallenge:Boolean, val progress:Int)

data class GlobalStats(val participants: Int, val totalMinutes:Int, val avgMinutesPerSesssion:Int)