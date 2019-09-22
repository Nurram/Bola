package com.nurram.project.bola

import android.content.Context
import com.nurram.project.bola.Network.*

class MatchPresenter(
    val context: Context?,
    val view: MatchView?,
    val teamView: TeamView?,
    var retrofitRepo: RetrofitRepo
) {

    fun requestLastMatch(id: String) {
        view?.showProgress()

        retrofitRepo.getLastMatch(id, object :
            RetrofitCallback<TeamResponse> {
            override fun onLoaded(data: TeamResponse) {
                view?.onLoaded(data)
                view?.hideProgress()
            }

            override fun onError() {
                view?.onError()
                view?.hideProgress()
            }
        })
    }

    fun requestNextMatch(id: String) {
        view?.showProgress()

        retrofitRepo.geNextMatch(id, object :
            RetrofitCallback<TeamResponse> {
            override fun onLoaded(data: TeamResponse) {
                view?.onLoaded(data)
                view?.hideProgress()
            }

            override fun onError() {
                view?.onError()
                view?.hideProgress()
            }
        })
    }

    fun requestFindMatch(keyword: String) {
        view?.showProgress()

        retrofitRepo.findMatch(keyword, object :
            RetrofitCallback<TeamResponse> {
            override fun onLoaded(data: TeamResponse) {
                view?.onLoaded(data)
                view?.hideProgress()
            }

            override fun onError() {
                view?.onError()
                view?.hideProgress()
            }
        })
    }

    fun requestTeamList(keyword: String) {
        teamView?.showProgress()

        retrofitRepo.findTeamList(keyword, object :
            RetrofitCallback<TeamListResponse> {
            override fun onLoaded(data: TeamListResponse) {
                teamView?.onLoaded(data)
                teamView?.hideProgress()
            }

            override fun onError() {
                teamView?.onError()
                teamView?.hideProgress()
            }
        })
    }

    fun requestPlayerList(name: String) {
        teamView?.showProgress()

        retrofitRepo.requestPlayerList(name, object :
            RetrofitCallback<TeamListResponse> {
            override fun onLoaded(data: TeamListResponse) {
                teamView?.onLoaded(data)
                teamView?.hideProgress()
            }

            override fun onError() {
                teamView?.onError()
                teamView?.hideProgress()
            }
        })
    }

    fun requestfindTeam(team: String) {
        teamView?.showProgress()

        retrofitRepo.requestFindTeam(team, object :
            RetrofitCallback<TeamListResponse> {
            override fun onLoaded(data: TeamListResponse) {
                teamView?.onLoaded(data)
                teamView?.hideProgress()
            }

            override fun onError() {
                teamView?.onError()
                teamView?.hideProgress()
            }
        })
    }

}