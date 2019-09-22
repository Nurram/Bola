package com.nurram.project.bola.Network

import com.nurram.project.bola.Model.Player
import com.nurram.project.bola.Model.TeamList

data class TeamListResponse(
    val teams: MutableList<TeamList>,
    val player: MutableList<Player>
)