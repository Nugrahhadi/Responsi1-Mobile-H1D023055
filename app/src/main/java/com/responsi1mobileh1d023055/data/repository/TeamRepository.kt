package com.responsi1mobileh1d023055.data.repository

import com.responsi1mobileh1d023055.data.api.RetrofitClient
import com.responsi1mobileh1d023055.data.model.ApiResponse
import retrofit2.Response

class TeamRepository {
    private val apiService = RetrofitClient.apiService

    suspend fun getTeamData(teamId: Int): Response<ApiResponse> {
        return apiService.getTeam(teamId)
    }
}
