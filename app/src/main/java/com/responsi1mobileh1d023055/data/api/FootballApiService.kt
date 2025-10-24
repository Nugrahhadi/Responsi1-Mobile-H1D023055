package com.responsi1mobileh1d023055.data.api

import com.responsi1mobileh1d023055.data.model.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FootballApiService {
    @GET("teams/{id}")
    suspend fun getTeam(@Path("id") teamId: Int): Response<ApiResponse>
}
