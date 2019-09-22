package com.nurram.project.bola.Model

data class Team(
    var strHomeTeam: String? = null,
    var intHomeScore: String? = null,
    var strTeamBadge: String? = null,
    var strAwayTeam: String? = null,
    var intAwayScore: String? = null,
    var dateEvent: String? = null,
    var strTime: String? = null,
    var idHomeTeam: String? = null,
    var idAwayTeam: String? = null,

    var strHomeGoalDetails: String? = null,
    var strHomeYellowCards: String? = null,
    var strAwayGoalDetails: String? = null,
    var strAwayYellowCards: String? = null,
    var strHomeLineupGoalkeeper: String? = null,
    var strHomeLineupDefense: String? = null,
    var strHomeLineupMidfield: String? = null,
    var strHomeLineupForward: String? = null,
    var strHomeLineupSubstitutes: String? = null,
    var strAwayLineupGoalkeeper: String? = null,
    var strAwayLineupDefense: String? = null,
    var strAwayLineupMidfield: String? = null,
    var strAwayLineupForward: String? = null,
    var strAwayLineupSubstitutes: String? = null
)