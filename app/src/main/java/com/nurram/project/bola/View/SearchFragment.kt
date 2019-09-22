package com.nurram.project.bola.View

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.nurram.project.bola.MatchPresenter
import com.nurram.project.bola.Network.MatchView
import com.nurram.project.bola.Network.RetrofitRepo
import com.nurram.project.bola.Network.TeamResponse
import com.nurram.project.bola.R
import com.nurram.project.bola.Utils.RecyclerAdapter
import kotlinx.android.synthetic.main.fragment_search.view.*

class SearchFragment : Fragment(), MatchView {
    private lateinit var presenter: MatchPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_search, container, false)
        v.search_recycler.layoutManager = LinearLayoutManager(context)
        v.search_recycler.setItemViewCacheSize(50)
        v.search_recycler.setHasFixedSize(true)

        presenter = MatchPresenter(context, this, null, RetrofitRepo())
        v.search_et.setOnEditorActionListener(TextView.OnEditorActionListener { p0, p1, p2 ->
            if (p1 == EditorInfo.IME_ACTION_SEARCH) {

                presenter.requestFindMatch(p0?.text.toString())

                return@OnEditorActionListener true
            }

            false
        })

        return v
    }

    override fun onLoaded(data: TeamResponse) {
        view?.search_recycler?.adapter = RecyclerAdapter(context, data.event) {
            val intent = Intent(context, MatchDetailActivity::class.java)
            val gson = Gson()
            val teamData = gson.toJson(it)
            intent.putExtra("team", teamData)
            intent.putExtra("key", "notDb")
            startActivity(intent)
        }
    }

    override fun onError() {
        Toast.makeText(context, R.string.error_text, Toast.LENGTH_SHORT).show()
    }

    override fun showProgress() {
        view?.search_progress?.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        view?.search_progress?.visibility = View.GONE
    }

}
