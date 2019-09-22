package com.nurram.project.bola.Utils

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.nurram.project.bola.Model.Team
import com.nurram.project.bola.Network.RetrofitCallback
import com.nurram.project.bola.Network.RetrofitRepo
import com.nurram.project.bola.Network.TeamResponse
import com.nurram.project.bola.R
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_list.*

class RecyclerAdapter(
    private val context: Context?,
    private val teams: List<Team>,
    private val listener: (Team) -> Unit
) : RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RecyclerHolder(LayoutInflater.from(context).inflate(R.layout.item_list, parent, false))

    override fun getItemCount() = teams.size

    override fun onBindViewHolder(holder: RecyclerHolder, pos: Int) {
        holder.bind(teams[pos], listener)
    }

    inner class RecyclerHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bind(team: Team, listener: (Team) -> Unit) {
            itemView.setOnClickListener { listener(team) }

            home_name.text = team.strHomeTeam
            home_score.text = team.intHomeScore
            opponent_name.text = team.strAwayTeam
            opponent_score.text = team.intAwayScore
            match_date.text = DateUtil.convertTime(team.strTime, team.dateEvent)
            requestFirstData(team)
        }

        private fun requestFirstData(team: Team) {
            team.idHomeTeam?.let {
                RetrofitRepo().getTeam(it, object :
                    RetrofitCallback<TeamResponse> {
                    override fun onLoaded(data: TeamResponse) {
                        val teams: List<Team> = data.teams

                        Picasso.get().load(teams.get(0).strTeamBadge).into(home_badge)
                    }

                    override fun onError() {
                        Toast.makeText(context, "Something Occured", Toast.LENGTH_SHORT).show()
                    }
                })

                requestSecondData(team)

            }
        }

        private fun requestSecondData(team: Team) {
            team.idAwayTeam?.let {
                RetrofitRepo().getTeam(it, object :
                    RetrofitCallback<TeamResponse> {
                    override fun onLoaded(data: TeamResponse) {
                        val teams: List<Team> = data.teams

                        Picasso.get().load(teams.get(0).strTeamBadge).into(opponent_badge)
                    }

                    override fun onError() {
                        Toast.makeText(context, "Something Occured", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }
}