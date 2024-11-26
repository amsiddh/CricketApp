package com.cricketapp.data.remote.model

data class MatchInfoListResponse (
    val data: List<MatchInfo> = emptyList()
) : BaseResponse()

