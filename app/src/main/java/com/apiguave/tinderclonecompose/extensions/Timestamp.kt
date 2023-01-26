package com.apiguave.tinderclonecompose.extensions

import com.google.firebase.Timestamp
import java.util.*

fun Timestamp.toAge(): Int{
    val now = GregorianCalendar()
    now.time = Date()
    val birthdate = GregorianCalendar()
    birthdate.time = this.toDate()
    return now.get(Calendar.YEAR) - birthdate.get(Calendar.YEAR)
}