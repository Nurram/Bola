package com.nurram.project.bola.View

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.nurram.project.bola.Db.TeamDb.TeamDbAdapter
import com.nurram.project.bola.Db.TeamDb.TeamDbContract
import com.nurram.project.bola.Db.TeamDb.database
import com.nurram.project.bola.R
import kotlinx.android.synthetic.main.fragment_fav_team.view.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class FavTeamFragment : Fragment() {
    private var teams: MutableList<TeamDbContract> = mutableListOf()
    private lateinit var adapter: TeamDbAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_fav_team, container, false)

        adapter = TeamDbAdapter(context, teams) {
            val intent = Intent(context, TeamDetailActivity::class.java)
            val gson = Gson()
            val teamData = gson.toJson(it)

            intent.putExtra("team", teamData)
            intent.putExtra("key", "fav")
            startActivity(intent)
        }
        getDbData(v)

        v.team_fragment_recycler.adapter = adapter
        v.team_fragment_recycler.layoutManager = LinearLayoutManager(context)

        v.team_fragment_swipe.setOnRefreshListener {
            teams.clear()
            getDbData(v)
        }
        v.team_fragment_swipe.setColorSchemeResources(
            R.color.colorAccent,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )


        return v
    }

    fun getDbData(v: View) {
        context?.database?.use {
            v.team_fragment_swipe.isRefreshing = false
            val result = select(TeamDbContract.TABLE_FAVOURITE)
            val data = result.parseList(classParser<TeamDbContract>())
            teams.addAll(data)
            adapter.notifyDataSetChanged()

        }
    }
}
