package com.nurram.project.bola.View

import android.content.ContentValues
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.gson.Gson
import com.nurram.project.bola.Db.DbContract
import com.nurram.project.bola.Db.matchDatabase
import com.nurram.project.bola.Model.Team
import com.nurram.project.bola.Network.RetrofitCallback
import com.nurram.project.bola.Network.RetrofitRepo
import com.nurram.project.bola.Network.TeamResponse
import com.nurram.project.bola.R
import com.nurram.project.bola.Utils.DateUtil
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_match_detail.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.item_list.*
import org.jetbrains.anko.db.delete

class MatchDetailActivity : AppCompatActivity() {
    private var team: Team? = null
    private lateinit var dbTeam: DbContract

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_detail)

        setSupportActionBar(toolbar)
        title = getString(R.string.match_detail)

        val gson = Gson()
        val gsonData = intent.getStringExtra("team")
        dbTeam = gson.fromJson(gsonData, DbContract::class.java)

        if (intent.getStringExtra("key") == "notDb") {
            team = gson.fromJson(gsonData, Team::class.java)
            lastUI()
        } else {
            dbUI()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        if (intent.getStringExtra("key") != "notDb") {
            menu?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_fav)
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_fav_button ->
                if (intent.getStringExtra("key") == "notDb") {
                    item.icon = ContextCompat.getDrawable(this, R.drawable.ic_fav)
                    addToDb()
                } else {
                    item.icon = ContextCompat.getDrawable(this, R.drawable.ic_fav_border)
                    deleteData()
                }
        }

        return true
    }

    private fun lastUI() {
        home_name.text = team?.strHomeTeam
        home_score.text = team?.intHomeScore
        detail_home_card.text = cutString(team?.strHomeYellowCards)
        detail_home_scorer.text = cutString(team?.strHomeGoalDetails)
        detail_home_goal.text = cutString(team?.strHomeLineupGoalkeeper)
        detail_home_fwd.text = cutString(team?.strHomeLineupForward)
        detail_home_def.text = cutString(team?.strHomeLineupDefense)
        detail_home_mid.text = cutString(team?.strHomeLineupMidfield)
        detail_home_sub.text = cutString(team?.strHomeLineupSubstitutes)

        opponent_name.text = team?.strAwayTeam
        opponent_score.text = team?.intAwayScore
        detail_away_card.text = cutString(team?.strAwayYellowCards)
        detail_away_scorer.text = cutString(team?.strAwayGoalDetails)
        detail_away_goal.text = cutString(team?.strAwayLineupGoalkeeper)
        detail_away_fwd.text = cutString(team?.strAwayLineupForward)
        detail_away_def.text = cutString(team?.strAwayLineupDefense)
        detail_away_mid.text = cutString(team?.strAwayLineupMidfield)
        detail_away_sub.text = cutString(team?.strAwayLineupSubstitutes)
        match_date.text = DateUtil.convertTime(team?.strTime, team?.dateEvent)


        requestFirstData(team?.idHomeTeam!!, team?.idAwayTeam!!)
    }

    private fun dbUI() {
        home_name.text = dbTeam.homeName
        home_score.text = dbTeam.homeScore
        detail_home_card.text = cutString(dbTeam.homeCard)
        detail_home_scorer.text = cutString(dbTeam.homeScorer)
        detail_home_goal.text = cutString(dbTeam.homeGoalKeeper)
        detail_home_fwd.text = cutString(dbTeam.homeFwd)
        detail_home_def.text = cutString(dbTeam.homeDefense)
        detail_home_mid.text = cutString(dbTeam.homeMiddle)
        detail_home_sub.text = cutString(dbTeam.homeSubstitute)

        opponent_name.text = dbTeam.awayName
        opponent_score.text = dbTeam.awayScore
        detail_away_card.text = cutString(dbTeam.awayCard)
        detail_away_scorer.text = cutString(dbTeam.awayScorer)
        detail_away_goal.text = cutString(dbTeam.awayGoalKeeper)
        detail_away_fwd.text = cutString(dbTeam.awayFwd)
        detail_away_def.text = cutString(dbTeam.awayDefense)
        detail_away_mid.text = cutString(dbTeam.awayMiddle)
        detail_away_sub.text = cutString(dbTeam.awaySubstitute)
        match_date.text = dbTeam.matchDate

        requestFirstData(dbTeam.homeId, dbTeam.awayId)
    }


    private fun cutString(word: String?): String? {

        if (word.isNullOrEmpty()) {
            return getString(R.string.none)
        }

        return word?.replace(";", "\n")
    }

    private fun requestFirstData(id: String, awayId: String) {

        RetrofitRepo().getTeam(id, object :
            RetrofitCallback<TeamResponse> {
            override fun onLoaded(data: TeamResponse) {
                val teams: List<Team> = data.teams

                Picasso.get().load(teams.get(0).strTeamBadge).into(home_badge)
            }

            override fun onError() {
                Toast.makeText(this@MatchDetailActivity, R.string.error_text, Toast.LENGTH_SHORT).show()
            }
        })

        requestSecondData(awayId)

    }

    private fun requestSecondData(id: String) {
        RetrofitRepo().getTeam(id, object :
            RetrofitCallback<TeamResponse> {
            override fun onLoaded(data: TeamResponse) {
                val teams: List<Team> = data.teams

                Picasso.get().load(teams.get(0).strTeamBadge).into(opponent_badge)
            }

            override fun onError() {
                Toast.makeText(this@MatchDetailActivity, R.string.error_text, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun addToDb() {

        try {
            matchDatabase.use {
                val contentValues = ContentValues()
                contentValues.put(DbContract.HOME_ID, team?.idHomeTeam)
                contentValues.put(DbContract.HOME_NAME, home_name.text.toString())
                contentValues.put(DbContract.HOME_SCORE, home_score.text.toString())
                contentValues.put(DbContract.HOME_SCORER, detail_home_scorer.text.toString())
                contentValues.put(DbContract.HOME_CARD, detail_home_card.text.toString())
                contentValues.put(DbContract.HOME_GOALKEEPER, detail_home_goal.text.toString())
                contentValues.put(DbContract.HOME_FWD, detail_home_fwd.text.toString())
                contentValues.put(DbContract.HOME_DEFENSE, detail_home_def.text.toString())
                contentValues.put(DbContract.HOME_MIDDLE, detail_home_mid.text.toString())
                contentValues.put(DbContract.HOME_SUBSTITUTE, detail_home_sub.text.toString())
                contentValues.put(DbContract.AWAY_ID, team?.idAwayTeam)
                contentValues.put(DbContract.AWAY_NAME, opponent_name.text.toString())
                contentValues.put(DbContract.AWAY_SCORE, opponent_score.text.toString())
                contentValues.put(DbContract.AWAY_SCORER, detail_away_scorer.text.toString())
                contentValues.put(DbContract.AWAY_CARD, detail_away_card.text.toString())
                contentValues.put(DbContract.AWAY_GOALKEEPER, detail_away_goal.text.toString())
                contentValues.put(DbContract.AWAY_FWD, detail_away_fwd.text.toString())
                contentValues.put(DbContract.AWAY_DEFENSE, detail_away_def.text.toString())
                contentValues.put(DbContract.AWAY_MIDDLE, detail_away_mid.text.toString())
                contentValues.put(DbContract.AWAY_SUBSTITUTE, detail_away_sub.text.toString())
                contentValues.put(DbContract.MATCH_DATE, match_date.text.toString())

                insertWithOnConflict(DbContract.TABLE_FAVOURITE, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE)
            }
            Snackbar.make(detail_scrollview, R.string.data_saved, Snackbar.LENGTH_SHORT).show()
        } catch (e: SQLiteConstraintException) {
            Snackbar.make(detail_scrollview, e.toString(), Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun deleteData() {

        try {
            matchDatabase.use {
                delete(DbContract.TABLE_FAVOURITE, "(HOME_ID = {id})", "id" to dbTeam.homeId)
                Snackbar.make(detail_scrollview, getString(R.string.data_deleted), Snackbar.LENGTH_SHORT).show()
            }
        } catch (e: SQLiteConstraintException) {
            Snackbar.make(detail_linear, e.toString(), Snackbar.LENGTH_SHORT).show()
        }
    }
}
