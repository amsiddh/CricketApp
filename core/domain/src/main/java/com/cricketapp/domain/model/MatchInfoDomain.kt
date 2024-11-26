package com.cricketapp.domain.model

import com.google.gson.annotations.SerializedName

data class MatchInfoDomain (
    val id: String,
    val name: String,
    val matchType: String,
    val status: String,
    val venue: String,
    val date: String,
    val dateTimeGMT: String,
    val teams: List<String>,
    val teamInfo: List<TeamInfoDomain>,
    val score: List<ScoreDomain>,
    val tossWinner: String,
    val tossChoice: String,

    @SerializedName("series_id")
    val seriesID: String,

    val fantasyEnabled: Boolean,
    val bbbEnabled: Boolean,
    val hasSquad: Boolean,
    val matchStarted: Boolean,
    val matchEnded: Boolean
)

data class ScoreDomain (
    val r: Long,
    val w: Long,
    val o: Double,
    val inning: String
)

data class TeamInfoDomain (
    val name: String,
    val shortname: String,
    val img: String
)
