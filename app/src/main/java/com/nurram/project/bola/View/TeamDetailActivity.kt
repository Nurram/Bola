package com.nurram.project.bola.View

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.DividerItemDecoration.VERTICAL
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.nurram.project.bola.Db.TeamDb.TeamDbContract
import com.nurram.project.bola.Db.TeamDb.database
import com.nurram.project.bola.MatchPresenter
import com.nurram.project.bola.Model.TeamList
import com.nurram.project.bola.Network.RetrofitRepo
import com.nurram.project.bola.Network.TeamListResponse
import com.nurram.project.bola.Network.TeamView
import com.nurram.project.bola.R
import com.nurram.project.bola.Utils.PlayerAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_team_detail.*
import kotlinx.android.synthetic.main.app_bar_main.*
import org.jetbrains.anko.db.delete

class TeamDetailActivity : AppCompatActivity(), TeamView {
    private lateinit var presenter: MatchPresenter
    private lateinit var team: TeamList
    private lateinit var dbTeam: TeamDbContract

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)
        setSupportActionBar(toolbar)

        val gson = Gson()
        val gsonData = intent.getStringExtra("team")
        dbTeam = gson.fromJson(gsonData, TeamDbContract::class.java)

        if (intent.getStringExtra("key") == "notDb") {
            team = gson.fromJson(gsonData, TeamList::class.java)
            teamUI()
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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
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

    override fun hideProgress() {
        team_detail_progress.visibility = View.GONE
    }

    override fun showProgress() {
        team_detail_progress.visibility = View.VISIBLE
    }

    override fun onLoaded(data: TeamListResponse) {
        if (data.player != null) {
            team_detail_recycler.adapter = PlayerAdapter(this, data.player) {
                val intent = Intent(this, PlayerDetailActivity::class.java)
                val gson = Gson()
                val playerData = gson.toJson(it)
                intent.putExtra("player", playerData)
                startActivity(intent)
            }
        }
    }

    override fun onError() {
        Toast.makeText(this, R.string.error_text, Toast.LENGTH_SHORT).show()
    }

    private fun teamUI() {
        title = team.strTeam

        team_detail_name.text = team.strTeam
        team_detail_established.append(team.intFormedYear)
        team_detail_website.append(team.strWebsite)
        team_detail_youtube.append(team.strYoutube)
        Picasso.get().load(team.strTeamBadge).into(team_detail_cover)

        presenter = MatchPresenter(this, null, this, RetrofitRepo())
        team.strTeam?.let { presenter.requestPlayerList(it) }
        team_detail_recycler.layoutManager = LinearLayoutManager(this)
        team_detail_recycler.setItemViewCacheSize(50)
        team_detail_recycler.setHasFixedSize(true)
        team_detail_recycler.addItemDecoration(DividerItemDecoration(this, VERTICAL))
    }

    private fun dbUI() {
        title = dbTeam.strTeam

        team_detail_name.text = dbTeam.strTeam
        team_detail_established.append(dbTeam.intFormedYear)
        team_detail_website.append(dbTeam.strWebsite)
        team_detail_youtube.append(dbTeam.strYoutube)
        Picasso.get().load(dbTeam.strTeamBadge).into(team_detail_cover)

        presenter = MatchPresenter(this, null, this, RetrofitRepo())
        dbTeam.strTeam?.let { presenter.requestPlayerList(it) }
        team_detail_recycler.layoutManager = LinearLayoutManager(this)
        team_detail_recycler.setItemViewCacheSize(50)
        team_detail_recycler.setHasFixedSize(true)
        team_detail_recycler.addItemDecoration(DividerItemDecoration(this, VERTICAL))
    }

    private fun addToDb() {

        try {
            database.use {
                val contentValue = ContentValues()
                contentValue.put(TeamDbContract.TEAM_ID, team.idTeam.toString())
                contentValue.put(TeamDbContract.TEAM_NAME, team_detail_name.text.toString())
                contentValue.put(TeamDbContract.TEAM_FORMED_YEAR, team_detail_established.text.toString())
                contentValue.put(TeamDbContract.TEAM_WEBSITE, team_detail_website.text.toString())
                contentValue.put(TeamDbContract.TEAM_BADGE, team.strTeamBadge)
                contentValue.put(TeamDbContract.TEAM_YOUTUBE, team_detail_youtube.text.toString())

                insertWithOnConflict(
                    TeamDbContract.TABLE_FAVOURITE,
                    null,
                    contentValue,
                    SQLiteDatabase.CONFLICT_REPLACE
                )
            }
            Snackbar.make(team_detail_root, R.string.data_saved, Snackbar.LENGTH_SHORT).show()
        } catch (e: SQLiteConstraintException) {
            Snackbar.make(team_detail_root, e.toString(), Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun deleteData() {

        try {
            database.use {
                delete(TeamDbContract.TABLE_FAVOURITE, "(TEAM_ID = ${dbTeam.idTeam})")
                Snackbar.make(team_detail_root, R.string.data_deleted, Snackbar.LENGTH_SHORT).show()
            }
        } catch (e: SQLiteConstraintException) {
            Snackbar.make(team_detail_root, e.toString(), Snackbar.LENGTH_SHORT).show()
        }
    }
}
