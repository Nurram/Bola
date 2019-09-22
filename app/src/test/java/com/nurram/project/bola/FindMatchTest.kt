package com.nurram.project.bola

import android.content.Context
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.nurram.project.bola.Network.MatchView
import com.nurram.project.bola.Network.RetrofitCallback
import com.nurram.project.bola.Network.RetrofitRepo
import com.nurram.project.bola.Network.TeamResponse
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class FindMatchTest {
    private lateinit var presenter: MatchPresenter

    @Mock
    private lateinit var view: MatchView

    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var repo: RetrofitRepo


    @Mock
    private lateinit var matchResponse: TeamResponse

    @Before
    fun initTest() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchPresenter(context, view, null, repo)
    }

    @Test
    fun testRespond() {
        presenter.requestFindMatch("Arsenal")

        argumentCaptor<RetrofitCallback<TeamResponse>>().apply {

            verify(repo).findMatch(eq("Arsenal"), capture())
            firstValue.onLoaded(matchResponse)
        }

        Mockito.verify(view).onLoaded(matchResponse)
    }
}