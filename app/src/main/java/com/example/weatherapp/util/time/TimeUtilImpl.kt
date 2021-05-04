package com.example.weatherapp.util.time

import java.text.SimpleDateFormat
import java.util.*

class TimeUtilImpl :ITimeUtil{

    override fun getDate(): String = getTimeByPattern("yy/MM/dd - EEE")

    override fun getTime(): String = getTimeByPattern("HH:mm")

    override fun getTimeFromDate(dateText: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = if (dateText.isEmpty()) Date() else sdf.parse(dateText)

        return  getTimeByPattern("dd-MM   HH:mm", date)
    }

    override fun createTimeByLong(time: Long) : String{

        val date = Date(time)

        return getTimeByPattern("HH:mm", date)
    }

    override fun createTimeByTwoLong(firstTime: Long, secondTime: Long): String {
        val finalTime = secondTime - firstTime

        val result = String.format(
            "%dH %dM",
            (finalTime / (1000 * 60 * 60) % 24).toInt(),
            (finalTime / (1000 * 60) % 60).toInt()
        )

        return result
    }

    private fun getTimeByPattern(pattern : String, date : Date = Date()): String {
        val timeToFormat = date.time

        return SimpleDateFormat(pattern).format(timeToFormat)
    }

}