package com.apiguave.match_data.extensions

import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar


fun Date.toLocalDate(): LocalDate {
    return this.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
}

fun Date.toAge(): Int {
    val now = GregorianCalendar()
    now.time = Date()
    val birthdate = GregorianCalendar()
    birthdate.time = this
    return now.get(Calendar.YEAR) - birthdate.get(Calendar.YEAR)
}