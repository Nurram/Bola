package com.nurram.project.bola.Db.TeamDb

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nurram.project.bola.R
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.team_list_item.*

class TeamDbAdapter(
    private val context: Context?,
    private val teams: List<TeamDbContract>,
    private val listener: (TeamDbContract) -> Unit
) : RecyclerView.Adapter<TeamDbAdapter.RecyclerHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RecyclerHolder(LayoutInflater.from(context).inflate(R.layout.team_list_item, parent, false))

    override fun getItemCount() = teams.size

    override fun onBindViewHolder(holder: RecyclerHolder, pos: Int) {
        holder.bind(teams[pos], listener)
    }

    inner class RecyclerHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bind(team: TeamDbContract, listener: (TeamDbContract) -> Unit) {
            itemView.setOnClickListener { listener(team) }
            team_list_name.append(team.strTeam)

            Picasso.get().load(team.strTeamBadge).into(team_list_cover)
        }
    }
}