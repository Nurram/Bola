package com.nurram.project.bola.View


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.nurram.project.bola.MatchPresenter
import com.nurram.project.bola.Network.MatchView
import com.nurram.project.bola.Network.RetrofitRepo
import com.nurram.project.bola.Network.TeamResponse
import com.nurram.project.bola.R
import com.nurram.project.bola.Utils.RecyclerAdapter
import kotlinx.android.synthetic.main.fragment_match.view.*

class NextMatchFragment : Fragment(), MatchView, AdapterView.OnItemSelectedListener {
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
        if (data.events != null) {
            view?.match_recycler?.adapter = RecyclerAdapter(context, data.events) {
                Toast.makeText(context, getString(R.string.warning), Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, getString(R.string.data_not_found), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        presenter.requestNextMatch(getString(R.string.league_id))
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        when (p2) {
            0 ->
                presenter.requestNextMatch("4328")
            1 ->
                presenter.requestNextMatch("4480")
            2 ->
                presenter.requestNextMatch("4332")
            3 ->
                presenter.requestNextMatch("4335")
            4 ->
                presenter.requestNextMatch("4429")
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