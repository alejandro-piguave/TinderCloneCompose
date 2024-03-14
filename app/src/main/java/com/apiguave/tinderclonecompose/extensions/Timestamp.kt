package com.apiguave.tinderclonecompose.extensions

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*

fun Timestamp.toAge(): Int{
    val now = GregorianCalendar()
    now.time = Date()
    val birthdate = GregorianCalendar()
    birthdate.time = this.toDate()
    return now.get(Calendar.YEAR) - birthdate.get(Calendar.YEAR)
}

val shortFormatter = SimpleDateFormat("dd/MM/yy")
val longFormatter = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
fun Timestamp.toShortString(): String{
    return shortFormatter.format(this.toDate())
}

fun Timestamp.toLongString(): String {
    return longFormatter.format(this.toDate())
}


