package com.nurram.project.bola.View

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.gson.Gson
import com.nurram.project.bola.Model.Player
import com.nurram.project.bola.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_player_detail.*

class PlayerDetailActivity : AppCompatActivity() {
    private lateinit var player: Player

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail)

        val gson = Gson()
        val gsonData = intent.getStringExtra("player")

        player = gson.fromJson(gsonData, Player::class.java)

        Picasso.get().load(player.strFanart1).into(detail_player_fanart)
        detail_player_name.text = player.strPlayer
        detail_player_position.text = player.strPosition
        detail_player_weight.append(player.strWeight)
        detail_player_height.append(player.strHeight)
        detail_player_deskripsi.text = player.strDescriptionEN
    }
}
