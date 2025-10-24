package com.responsi1mobileh1d023055.data.model

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    val area: Area?,
    val id: Int,
    val name: String,
    val shortName: String,
    val tla: String,
    val crest: String,
    val address: String,
    val website: String,
    val founded: Int,
    val clubColors: String,
    val venue: String,
    val coach: Coach?,
    val squad: List<Player>
)

data class Area(
    val id: Int?,
    val name: String?
)

data class Coach(
    val id: Int?,
    val firstName: String?,
    val lastName: String?,
    val name: String?,
    val dateOfBirth: String?,
    val nationality: String?,
    val contract: Contract?
)

data class Contract(
    val start: String?,
    val until: String?
)

data class Player(
    val id: Int,
    val name: String,
    val position: String?,
    val dateOfBirth: String?,
    val nationality: String?
)
