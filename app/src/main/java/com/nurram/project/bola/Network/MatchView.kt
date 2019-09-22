package com.nurram.project.bola.Network

interface MatchView : RetrofitCallback<TeamResponse> {
    fun hideProgress()
    fun showProgress()
}