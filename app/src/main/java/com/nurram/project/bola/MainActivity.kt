package com.nurram.project.bola

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.nurram.project.bola.View.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )

        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        switchFragment(LastMatchFragment(), "")
        nav_view.setCheckedItem(R.id.menu_last_match)
        title = getString(R.string.last_match)
        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawer_layout.closeDrawer(GravityCompat.START)
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.menu_find_match -> {
                switchFragment(SearchFragment(), "match")
                title = getString(R.string.find_match)
            }
            R.id.menu_last_match -> {
                switchFragment(LastMatchFragment(), "")
                title = getString(R.string.last_match)
            }
            R.id.menu_next_match -> {
                switchFragment(NextMatchFragment(), "")
                title = getString(R.string.next_match)
            }
            R.id.menu_find_team -> {
                switchFragment(SearchTeamFragment(), "team")
                title = getString(R.string.find_team)
            }
            R.id.menu_team_list -> {
                switchFragment(TeamListFragment(), "")
                title = getString(R.string.team_list)
            }
            R.id.menu_fav_match -> {
                switchFragment(FavMatchFragment(), "")
                title = getString(R.string.fav_match)
            }
            R.id.menu_fav_team -> {
                switchFragment(FavTeamFragment(), "")
                title = getString(R.string.fav_team)
            }
        }

        return true
    }

    private fun switchFragment(fragment: Fragment, args: String) {
        fragment.arguments = addArgs(args)
        supportFragmentManager.beginTransaction().replace(R.id.container_layout, fragment).commit()
    }

    private fun addArgs(menu: String): Bundle {
        val args = Bundle()
        args.putString("menu", menu)

        return args
    }
}
