package com.example.weatherapp.util.time

interface ITimeUtil {

    /**
     * Get the curr date
     */
    fun getDate(): String

    /**
     * Get the curr time
     */
    fun getTime(): String

    /**
     * Get the time from the date
     * @param dateText - date that need to parse
     */
    fun getTimeFromDate(dateText : String): String

    /**
     * Create time (HH:mm)
     * @param time - [Long]
     */
    fun createTimeByLong(time: Long): String

    /**
     * Create the time by start and end time
     */
    fun createTimeByTwoLong(firstTime: Long, secondTime: Long) : String
}