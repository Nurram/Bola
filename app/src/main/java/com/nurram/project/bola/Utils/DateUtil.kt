package com.nurram.project.bola.Utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtil{
    fun convertTime(time: String?, date: String?): String{
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        dateFormatter.timeZone = TimeZone.getTimeZone("UTC")

        val dateTime = "$date $time"

        return dateToString(dateFormatter.parse(dateTime))
    }

    private fun dateToString(date: Date):String{
        val formatter = SimpleDateFormat("dd-MM-yyyy \n HH:mm:ss")

        return formatter.format(date)
    }
}