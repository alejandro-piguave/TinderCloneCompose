package com.apiguave.tinderclonedata.source.extension

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.Locale

fun Date.toAge(): Int{
    val now = GregorianCalendar()
    now.time = Date()
    val birthdate = GregorianCalendar()
    birthdate.time = this
    return now.get(Calendar.YEAR) - birthdate.get(Calendar.YEAR)
}

val shortFormatter = SimpleDateFormat("dd/MM/yy")
val longFormatter = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
fun Timestamp.toShortString(): String{
    return shortFormatter.format(this.toDate())
}

fun Date.toLongString(): String {
    return longFormatter.format(this)
}


