package com.apiguave.tinderclonedata.source.extension

import com.google.firebase.Timestamp
import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar


fun Timestamp.toLocalDate(): LocalDate {
    return this.toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
}

fun Timestamp.toAge(): Int {
    val now = GregorianCalendar()
    now.time = Date()
    val birthdate = GregorianCalendar()
    birthdate.time = this.toDate()
    return now.get(Calendar.YEAR) - birthdate.get(Calendar.YEAR)
}