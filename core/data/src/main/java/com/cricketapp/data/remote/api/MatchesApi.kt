package com.cricketapp.data.remote.api

import com.cricketapp.data.remote.model.MatchInfoListResponse
import com.cricketapp.data.remote.model.MatchInfoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MatchesApi {
    @GET("v1/currentMatches")
    suspend fun getCurrentMatches(
        @Query("apikey") apiKey: String, @Query("offset") offset: Int = 0
    ): Response<MatchInfoListResponse>

    @GET("v1/match_info")
    suspend fun getMatchDetails(
        @Query("apikey") apiKey: String, @Query("id") matchId: String
    ): Response<MatchInfoResponse>
}