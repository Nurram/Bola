package com.nurram.project.bola.Network

interface TeamView :
    RetrofitCallback<TeamListResponse> {
    fun hideProgress()
    fun showProgress()
}