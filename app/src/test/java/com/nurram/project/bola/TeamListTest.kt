package com.nurram.project.bola

import android.content.Context
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.nurram.project.bola.Network.RetrofitCallback
import com.nurram.project.bola.Network.RetrofitRepo
import com.nurram.project.bola.Network.TeamListResponse
import com.nurram.project.bola.Network.TeamView
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class TeamListTest {
    private lateinit var presenter: MatchPresenter

    @Mock
    private lateinit var view: TeamView

    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var repo: RetrofitRepo


    @Mock
    private lateinit var matchResponse: TeamListResponse

    @Before
    fun initTest() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchPresenter(context, null, view, repo)
    }

    @Test
    fun testRespond() {
        presenter.requestTeamList("4328")
        argumentCaptor<RetrofitCallback<TeamListResponse>>().apply {

            verify(repo).findTeamList(eq("4328"), capture())
            firstValue.onLoaded(matchResponse)
        }

        Mockito.verify(view).onLoaded(matchResponse)
    }
}