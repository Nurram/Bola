package com.nurram.project.bola.Db

data class DbContract(
    val homeId: String,
    val homeName: String,
    val homeScore: String,
    val homeScorer: String,
    val homeCard: String,
    val homeGoalKeeper: String,
    val homeFwd: String,
    val homeDefense: String,
    val homeMiddle: String,
    val homeSubstitute: String,
    val awayId: String,
    val awayName: String,
    val awayScore: String,
    val awayScorer: String,
    val awayCard: String,
    val awayGoalKeeper: String,
    val awayFwd: String,
    val awayDefense: String,
    val awayMiddle: String,
    val awaySubstitute: String,
    val matchDate: String
) {

    companion object {
        const val TABLE_FAVOURITE: String = "TABLE_FAVOURITE"
        const val HOME_ID: String = "HOME_ID"
        const val HOME_NAME: String = "HOME_NAME"
        const val HOME_SCORE: String = "HOME_SCORE"
        const val HOME_SCORER: String = "HOME_SCORER"
        const val HOME_CARD: String = "HOME_CARD"
        const val HOME_GOALKEEPER: String = "HOME_GOALKEEPER"
        const val HOME_FWD: String = "HOME_FORWARD"
        const val HOME_DEFENSE: String = "HOME_DEFENSE"
        const val HOME_MIDDLE: String = "HOME_MIDDLE"
        const val HOME_SUBSTITUTE: String = "HOME_SUBSTITUTE"
        const val AWAY_ID: String = "AWAY_ID"
        const val AWAY_NAME: String = "AWAY_NAME"
        const val AWAY_SCORE: String = "AWAY_SCORE"
        const val AWAY_SCORER: String = "AWAY_SCORER"
        const val AWAY_CARD: String = "AWAY_CARD"
        const val AWAY_GOALKEEPER: String = "AWAY_GOALKEEPER"
        const val AWAY_FWD: String = "AWAY_FORWARD"
        const val AWAY_DEFENSE: String = "AWAY_DEFENSE"
        const val AWAY_MIDDLE: String = "AWAY_MIDDLE"
        const val AWAY_SUBSTITUTE: String = "AWAY_SUBSTITUTE"
        const val MATCH_DATE: String = "DATE"
    }
}