package com.blauhaus.android.redwood.meditationchallenge.data

import com.blauhaus.android.redwood.meditationchallenge.R

val meditationHistoryData= listOf(
    ADaysWorthOfMeditationData(
        5f,
        "5 min @ Jan 1"
    ),
    ADaysWorthOfMeditationData(
        10f,
        "10 min @ Jan 2"
    ),
    ADaysWorthOfMeditationData(
        2f,
        "2 min @ Jan 3"
    ),
    ADaysWorthOfMeditationData(
        30f,
        "30 min @ Jan 4"
    ),
    ADaysWorthOfMeditationData(
        15f,
        "15 min @ Jan 5"
    ),
    ADaysWorthOfMeditationData(
        12f,
        "12 min @ Jan 6"
    ),
    ADaysWorthOfMeditationData(
        5f,
        "5 min @ Jan 7"
    ),
    ADaysWorthOfMeditationData(
        25f,
        "25 min @ Jan 8"
    ),
    ADaysWorthOfMeditationData(
        0f,
        "0 min @ Jan 9"
    ),
    ADaysWorthOfMeditationData(
        0f,
        "0 min @ Jan 10"
    ),
    ADaysWorthOfMeditationData(
        15f,
        "15 min @ Jan 11"
    ),
    ADaysWorthOfMeditationData(
        15f,
        "15 min @ Jan 12"
    ),
    ADaysWorthOfMeditationData(
        25f,
        "25 min @ Jan 13"
    ),
    ADaysWorthOfMeditationData(
        35f,
        "35 min @ Jan 14"
    ),
    ADaysWorthOfMeditationData(
        35f,
        "35 min @ Jan 14"
    ),
    ADaysWorthOfMeditationData(
        35f,
        "35 min @ Jan 14"
    ),
    ADaysWorthOfMeditationData(
        35f,
        "35 min @ Jan 14"
    ),
    ADaysWorthOfMeditationData(
        35f,
        "35 min @ Jan 14"
    ),
    ADaysWorthOfMeditationData(
        0f,
        "0 min @ Jan 14"
    ),
    ADaysWorthOfMeditationData(
        35f,
        "35 min @ Jan 14"
    ),
    ADaysWorthOfMeditationData(
        20f,
        "35 min @ Jan 14"
    ),
    ADaysWorthOfMeditationData(
        35f,
        "35 min @ Jan 14"
    ),
    ADaysWorthOfMeditationData(
        0f,
        "0 min @ Jan 14"
    ),
    ADaysWorthOfMeditationData(
        35f,
        "35 min @ Jan 14"
    ),
    ADaysWorthOfMeditationData(
        0f,
        "0 min @ Jan 14"
    ),
    ADaysWorthOfMeditationData(
        35f,
        "35 min @ Jan 14"
    ),
    ADaysWorthOfMeditationData(
        20f,
        "35 min @ Jan 14"
    ),
    ADaysWorthOfMeditationData(
        35f,
        "35 min @ Jan 14"
    ),
    ADaysWorthOfMeditationData(
        35f,
        "35 min @ Jan 14"
    )
    )

val myCircleData = listOf(
    // Just grabbing random demo data here.
    MeditationDataByPersonName(
        "Dan",
        meditationHistoryData.subList(2, 8)
    ),
    MeditationDataByPersonName(
        "Alexis",
        meditationHistoryData.subList(4, 14)
    )
)

// random
val globalStatsData = GlobalStats(54580, 12707803, 19)

// nice use of named fields.
val currentSessionData = Content(
    image = R.drawable.josephgoldstein,
    title = "Meditate With Joseph",
    pretitle = "UP NEXT",
    description = "A beginners mindfulness medition focusing on breath following with Joseph Goldstein.  Joseph Goldstein is founder of the Insight Meditation Society in Barre Mass.",
    videoId = "O44d1U7aYAo",
    audioId = R.raw.goldstein_audio
)

val challengeUpdateData = Content(
    image = R.drawable.dog_party_hat,
    title = "The challenge is over, but you just got started!",
    pretitle = "CONGRATS",
    description = "Great job in working through the meditation challenge.  Hopefully this is a jumping off point for your meditation practice, and that you will keep finding this app useful.  Thanks for participating!"

)

val demoUserName = "Sam"
