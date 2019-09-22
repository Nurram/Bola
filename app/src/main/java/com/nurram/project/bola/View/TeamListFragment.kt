package com.nurram.project.bola.View

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.gson.Gson
import com.nurram.project.bola.MatchPresenter
import com.nurram.project.bola.R
import com.nurram.project.bola.Network.RetrofitRepo
import com.nurram.project.bola.Network.TeamListResponse
import com.nurram.project.bola.Utils.TeamRecyclerAdapter
import com.nurram.project.bola.Network.TeamView
import kotlinx.android.synthetic.main.fragment_team_list.view.*

class TeamListFragment : Fragment(), TeamView, AdapterView.OnItemSelectedListener {
    private lateinit var presenter: MatchPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_team_list, container, false)
        v.team_list_recycler.layoutManager = LinearLayoutManager(context)
        v.team_list_recycler.setItemViewCacheSize(50)
        v.team_list_recycler.setHasFixedSize(true)

        presenter = MatchPresenter(context, null, this, RetrofitRepo())

        addSpinnerItem(v)

        return v
    }

    override fun hideProgress() {
        view?.team_list_progress?.visibility = View.GONE
    }

    override fun showProgress() {
        view?.team_list_progress?.visibility = View.VISIBLE
    }

    override fun onLoaded(data: TeamListResponse) {
        view?.team_list_recycler?.adapter = TeamRecyclerAdapter(context, data.teams){
            val intent = Intent(context, TeamDetailActivity::class.java)
            val gson = Gson()
            val teamData = gson.toJson(it)
            intent.putExtra("team", teamData)
            intent.putExtra("key", "notDb")
            startActivity(intent)
        }
    }

    override fun onError() {
        Toast.makeText(context, R.string.error_text, Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        presenter.requestTeamList(getString(R.string.league_id))
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        when (p2) {
            0 ->
                presenter.requestTeamList("4328")
            1 ->
                presenter.requestTeamList("4331")
            2 ->
                presenter.requestTeamList("4332")
            3 ->
                presenter.requestTeamList("4335")
            4 ->
                presenter.requestTeamList("4429")
        }
    }

    private fun addSpinnerItem(view: View) {
        val list = activity?.resources?.getStringArray(R.array.team_match_list)
        val adapter = ArrayAdapter(activity, android.R.layout.simple_spinner_item, list)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        view.team_list_spinner.adapter = adapter
        view.team_list_spinner.onItemSelectedListener = this
    }
}
