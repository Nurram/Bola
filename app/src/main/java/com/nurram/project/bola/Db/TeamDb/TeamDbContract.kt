package com.nurram.project.bola.Db.TeamDb

data class TeamDbContract(
    var idTeam: String?,
    var strTeam: String?,
    var intFormedYear: String?,
    var strWebsite: String?,
    var strTeamBadge: String?,
    var strYoutube: String?
) {

    companion object {
        const val TABLE_FAVOURITE: String = "TABLE_FAVOURITE_TEAM"
        const val TEAM_ID: String = "TEAM_ID"
        const val TEAM_NAME: String = "TEAM_NAME"
        const val TEAM_FORMED_YEAR: String = "TEAM_FORMED_YEAR"
        const val TEAM_WEBSITE: String = "TEAM_WEBSITE"
        const val TEAM_BADGE: String = "TEAM_BADGE"
        const val TEAM_YOUTUBE: String = "TEAM_YOUTUBE"
    }
}