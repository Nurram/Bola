package com.nurram.project.bola.Network

interface RetrofitCallback<T> {

    fun onLoaded(data: T)
    fun onError()
}