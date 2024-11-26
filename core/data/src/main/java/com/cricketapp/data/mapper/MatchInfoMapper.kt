package com.cricketapp.data.mapper

import com.cricketapp.data.remote.model.MatchInfo
import com.cricketapp.data.remote.model.Score
import com.cricketapp.data.remote.model.TeamInfo
import com.cricketapp.domain.model.MatchInfoDomain
import com.cricketapp.domain.model.ScoreDomain
import com.cricketapp.domain.model.TeamInfoDomain

fun TeamInfo.toDomain(): TeamInfoDomain {
    return TeamInfoDomain(
        name = this.name,
        shortname = this.shortname,
        img = this.img
    )
}

fun Score.toDomain(): ScoreDomain {
    return ScoreDomain(
        r = this.r,
        w = this.w,
        o = this.o,
        inning = this.inning
    )
}

fun MatchInfo.toDomain(): MatchInfoDomain {
    return MatchInfoDomain(
        id = this.id,
        name = this.name,
        matchType = this.matchType,
        status = this.status,
        venue = this.venue,
        date = this.date,
        dateTimeGMT = this.dateTimeGMT,
        teams = this.teams,
        teamInfo = this.teamInfo?.let { it.map { item -> item.toDomain() } } ?: emptyList(),
        score = this.score.map { it.toDomain() },
        tossWinner = this.tossWinner ?: "",
        tossChoice = this.tossChoice ?: "",
        seriesID = this.seriesID,
        fantasyEnabled = this.fantasyEnabled,
        bbbEnabled = this.bbbEnabled,
        hasSquad = this.hasSquad,
        matchStarted = this.matchStarted,
        matchEnded = this.matchEnded
    )
}

fun List<MatchInfo>.toDomainList(): List<MatchInfoDomain> {
    return this.map { it.toDomain() }
}


