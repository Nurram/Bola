package com.nurram.project.bola.Db

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.nurram.project.bola.Model.Team
import com.nurram.project.bola.R
import com.nurram.project.bola.Network.RetrofitRepo
import com.nurram.project.bola.Network.TeamResponse
import com.nurram.project.bola.Network.RetrofitCallback
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_list.*

class DbAdapter(
    private val context: Context?,
    private val teams: List<DbContract>,
    private val listener: (DbContract) -> Unit
) :
    RecyclerView.Adapter<DbAdapter.RecyclerHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RecyclerHolder(LayoutInflater.from(context).inflate(R.layout.item_list, parent, false))

    override fun getItemCount() = teams.size

    override fun onBindViewHolder(holder: RecyclerHolder, pos: Int) {
        holder.bind(teams[pos], listener)
    }

    inner class RecyclerHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bind(dbContract: DbContract, listener: (DbContract) -> Unit) {
            itemView.setOnClickListener { listener(dbContract) }

            home_name.text = dbContract.homeName
            home_score.text = dbContract.homeScore
            opponent_name.text = dbContract.awayName
            opponent_score.text = dbContract.awayScore
            match_date.text = dbContract.matchDate
            requestFirstData(dbContract)
        }

        private fun requestFirstData(dbContract: DbContract) {
            RetrofitRepo().getTeam(dbContract.homeId, object :
                RetrofitCallback<TeamResponse> {
                override fun onLoaded(data: TeamResponse) {
                    val teams: List<Team> = data.teams

                    Picasso.get().load(teams.get(0).strTeamBadge).into(home_badge)
                }

                override fun onError() {
                    Toast.makeText(context, "Something Occured", Toast.LENGTH_SHORT).show()
                }
            })

            requestSecondData(dbContract)

        }

        private fun requestSecondData(dbContract: DbContract) {
            RetrofitRepo().getTeam(dbContract.awayId, object :
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