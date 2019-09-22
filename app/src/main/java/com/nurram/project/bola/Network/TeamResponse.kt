package com.nurram.project.bola.Network

import com.nurram.project.bola.Model.Team

data class TeamResponse(
    val events: MutableList<Team>,
    val teams: MutableList<Team>,
    val event: MutableList<Team>
)