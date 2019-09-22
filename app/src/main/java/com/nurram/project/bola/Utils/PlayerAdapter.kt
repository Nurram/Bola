package com.nurram.project.bola.Utils

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nurram.project.bola.Model.Player
import com.nurram.project.bola.R
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.player_item_list.*

class PlayerAdapter(
    private val context: Context?,
    private val players: List<Player>,
    private val listener: (Player) -> Unit
) : RecyclerView.Adapter<PlayerAdapter.Holder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Holder {
        return Holder(LayoutInflater.from(context).inflate(R.layout.player_item_list, p0, false))
    }

    override fun getItemCount(): Int {
        return players.size
    }

    override fun onBindViewHolder(p0: Holder, p1: Int) {
        p0.bind(players[p1], listener)
    }

    inner class Holder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(player: Player, listener: (Player) -> Unit) {
            itemView.setOnClickListener { listener(player) }
            player_name.append(player.strPlayer)
            player_position.append(player.strPosition)

            if (player.strWage != null) {
                player_wage.append(player.strWage)
            }

            Picasso.get().load(player.strCutout).into(player_thumbnail)
        }
    }
}