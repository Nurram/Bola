package com.nurram.project.bola.Network

import com.nurram.project.bola.BuildConfig
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitEndpoint {
    @GET("api/v1/json/1/eventspastleague.php")
    fun getLastMatch(@Query("id") id: String): Call<TeamResponse>

    @GET("api/v1/json/1/eventsnextleague.php")
    fun getNextMatch(@Query("id") id: String): Call<TeamResponse>

    @GET("https://www.thesportsdb.com/api/v1/json/1/lookupteam.php")
    fun getTeam(@Query("id") id: String): Call<TeamResponse>

    @GET("api/v1/json/1/searchevents.php")
    fun findMatch(@Query("e") matchName: String): Call<TeamResponse>

    @GET("api/v1/json/1/searchteams.php")
    fun findTeam(@Query("t") teamName: String): Call<TeamListResponse>

    @GET("api/v1/json/1/lookup_all_teams.php")
    fun getTeamList(@Query("id") id: String): Call<TeamListResponse>

    @GET("api/v1/json/1/searchplayers.php")
    fun getPlayerList(@Query("t") team: String): Call<TeamListResponse>

    companion object RetrofitClient {

        private fun getClient(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun <T> createService(service: Class<T>): T {
            return getClient().create(service)
        }
    }
}