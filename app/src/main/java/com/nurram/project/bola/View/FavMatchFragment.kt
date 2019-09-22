package com.nurram.project.bola.View

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.nurram.project.bola.Db.DbAdapter
import com.nurram.project.bola.Db.DbContract
import com.nurram.project.bola.Db.matchDatabase
import com.nurram.project.bola.R
import kotlinx.android.synthetic.main.fragment_fav_match.view.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class FavMatchFragment : Fragment() {
    private var teams: MutableList<DbContract> = mutableListOf()
    private lateinit var adapter: DbAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_fav_match, container, false)

        adapter = DbAdapter(context, teams) {
            val intent = Intent(context, MatchDetailActivity::class.java)
            val gson = Gson()
            val teamData = gson.toJson(it)

            intent.putExtra("team", teamData)
            intent.putExtra("key", "fav")
            startActivity(intent)
        }
        getDbData(v)

        v.fragment_recycler.adapter = adapter
        v.fragment_recycler.layoutManager = LinearLayoutManager(context)

        v.fragment_swipe.setOnRefreshListener {
            teams.clear()
            getDbData(v)
        }
        v.fragment_swipe.setColorSchemeResources(
            R.color.colorAccent,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )


        return v
    }

    fun getDbData(v: View) {
        context?.matchDatabase?.use {
            v.fragment_swipe.isRefreshing = false
            val result = select(DbContract.TABLE_FAVOURITE)
            val data = result.parseList(classParser<DbContract>())
            teams.addAll(data)
            adapter.notifyDataSetChanged()

        }
    }
}

