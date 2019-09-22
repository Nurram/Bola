package com.nurram.project.bola.View

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.gson.Gson
import com.nurram.project.bola.MatchPresenter
import com.nurram.project.bola.Network.MatchView
import com.nurram.project.bola.Network.RetrofitRepo
import com.nurram.project.bola.Network.TeamResponse
import com.nurram.project.bola.R
import com.nurram.project.bola.Utils.RecyclerAdapter
import kotlinx.android.synthetic.main.fragment_match.view.*

class LastMatchFragment : Fragment(), MatchView, AdapterView.OnItemSelectedListener {
    private lateinit var presenter: MatchPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_match, container, false)
        view.match_recycler.layoutManager = LinearLayoutManager(context)
        view.match_recycler.setItemViewCacheSize(20)
        view.match_recycler.setHasFixedSize(true)

        presenter = MatchPresenter(context, this, null, RetrofitRepo())

        addSpinnerItem(view)

        return view
    }

    override fun hideProgress() {
        view?.progress?.visibility = View.GONE
    }

    override fun showProgress() {
        view?.progress?.visibility = View.VISIBLE
    }

    override fun onLoaded(data: TeamResponse) {
        view?.match_recycler?.adapter = RecyclerAdapter(context, data.events) {
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

    override fun onNothingSelected(p0: AdapterView<*>?) {
        presenter.requestLastMatch("4328")
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        when (p2) {
            0 ->
                presenter.requestLastMatch("4328")
            1 ->
                presenter.requestLastMatch("4480")
            2 ->
                presenter.requestLastMatch("4332")
            3 ->
                presenter.requestLastMatch("4335")
            4 ->
                presenter.requestLastMatch("4429")
        }
    }

    private fun addSpinnerItem(view: View) {
        val list = activity?.resources?.getStringArray(R.array.match_list)
        val adapter = ArrayAdapter(activity, android.R.layout.simple_spinner_item, list)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        view.match_spinner.adapter = adapter
        view.match_spinner.onItemSelectedListener = this
    }

}
