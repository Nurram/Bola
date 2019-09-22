package com.nurram.project.bola.Network

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitRepo {

    fun getLastMatch(id: String, callback: RetrofitCallback<TeamResponse>) {
        RetrofitEndpoint.createService(RetrofitEndpoint::class.java)
            .getLastMatch(id)
            .enqueue(object : Callback<TeamResponse?> {
                override fun onFailure(call: Call<TeamResponse?>, t: Throwable) {
                    callback.onError()
                }

                override fun onResponse(call: Call<TeamResponse?>, response: Response<TeamResponse?>) {
                    response.let {
                        if (it.isSuccessful) {
                            callback.onLoaded(it.body()!!)
                        } else {
                            callback.onError()
                        }
                    }
                }
            })
    }

    fun geNextMatch(id: String, callback: RetrofitCallback<TeamResponse>) {
        RetrofitEndpoint.createService(RetrofitEndpoint::class.java)
            .getNextMatch(id)
            .enqueue(object : Callback<TeamResponse?> {
                override fun onFailure(call: Call<TeamResponse?>, t: Throwable) {
                    callback.onError()
                }

                override fun onResponse(call: Call<TeamResponse?>, response: Response<TeamResponse?>) {
                    response.let {
                        if (it.isSuccessful) {
                            callback.onLoaded(it.body()!!)
                        } else {
                            callback.onError()
                        }
                    }
                }
            })
    }

    fun getTeam(id: String, callback: RetrofitCallback<TeamResponse>) {
        RetrofitEndpoint.createService(RetrofitEndpoint::class.java)
            .getTeam(id)
            .enqueue(object : Callback<TeamResponse> {
                override fun onFailure(call: Call<TeamResponse>, t: Throwable) {
                    callback.onError()
                }

                override fun onResponse(call: Call<TeamResponse>, response: Response<TeamResponse>) {
                    response.let {
                        if (it.isSuccessful) {
                            callback.onLoaded(it.body()!!)
                        } else {
                            callback.onError()
                        }
                    }
                }
            })
    }

    fun findMatch(id: String, callback: RetrofitCallback<TeamResponse>) {
        RetrofitEndpoint.createService(RetrofitEndpoint::class.java)
            .findMatch(id)
            .enqueue(object : Callback<TeamResponse?> {
                override fun onFailure(call: Call<TeamResponse?>, t: Throwable) {
                    callback.onError()
                }

                override fun onResponse(call: Call<TeamResponse?>, response: Response<TeamResponse?>) {
                    response.let {
                        if (it.isSuccessful) {
                            callback.onLoaded(it.body()!!)
                        } else {
                            callback.onError()
                        }
                    }
                }
            })
    }

    fun findTeamList(id: String, callback: RetrofitCallback<TeamListResponse>) {
        RetrofitEndpoint.createService(RetrofitEndpoint::class.java)
            .getTeamList(id)
            .enqueue(object : Callback<TeamListResponse?> {
                override fun onFailure(call: Call<TeamListResponse?>, t: Throwable) {
                    callback.onError()
                }

                override fun onResponse(call: Call<TeamListResponse?>, response: Response<TeamListResponse?>) {
                    response.let {
                        if (it.isSuccessful) {
                            it.body()?.let { it1 -> callback.onLoaded(it1) }
                        } else {
                            callback.onError()
                        }
                    }
                }
            })
    }

    fun requestPlayerList(team: String, callback: RetrofitCallback<TeamListResponse>) {
        RetrofitEndpoint.createService(RetrofitEndpoint::class.java)
            .getPlayerList(team)
            .enqueue(object : Callback<TeamListResponse?> {
                override fun onFailure(call: Call<TeamListResponse?>, t: Throwable) {
                    callback.onError()
                }

                override fun onResponse(call: Call<TeamListResponse?>, response: Response<TeamListResponse?>) {
                    response.let {
                        if (it.isSuccessful) {
                            it.body()?.let { it1 -> callback.onLoaded(it1) }
                        } else {
                            Log.e("Tag", response.errorBody().toString())
                            callback.onError()
                        }
                    }
                }
            })
    }

    fun requestFindTeam(team: String, callback: RetrofitCallback<TeamListResponse>) {
        RetrofitEndpoint.createService(RetrofitEndpoint::class.java)
            .findTeam(team)
            .enqueue(object : Callback<TeamListResponse?> {
                override fun onFailure(call: Call<TeamListResponse?>, t: Throwable) {
                    callback.onError()
                }

                override fun onResponse(call: Call<TeamListResponse?>, response: Response<TeamListResponse?>) {
                    response.let {
                        if (it.isSuccessful) {
                            it.body()?.let { it1 -> callback.onLoaded(it1) }
                        } else {
                            Log.e("Tag", response.errorBody().toString())
                            callback.onError()
                        }
                    }
                }
            })
    }

}