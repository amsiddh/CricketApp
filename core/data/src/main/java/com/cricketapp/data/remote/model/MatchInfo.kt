package com.cricketapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class MatchInfo (
    val id: String,
    val name: String,
    val matchType: String,
    val status: String,
    val venue: String,
    val date: String,
    val dateTimeGMT: String,
    val teams: List<String>,
    val teamInfo: List<TeamInfo>?,
    val score: List<Score>,
    val tossWinner: String?,
    val tossChoice: String?,

    @SerializedName("series_id")
    val seriesID: String,

    val fantasyEnabled: Boolean,
    val bbbEnabled: Boolean,
    val hasSquad: Boolean,
    val matchStarted: Boolean,
    val matchEnded: Boolean
)

data class Score (
    val r: Long,
    val w: Long,
    val o: Double,
    val inning: String
)

data class TeamInfo (
    val name: String,
    val shortname: String,
    val img: String
)
