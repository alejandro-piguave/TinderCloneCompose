package com.apiguave.tinderclonecompose.extension

import java.time.LocalDate
import java.time.format.DateTimeFormatter

val shortFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yy")
    //SimpleDateFormat("dd/MM/yy")
val longFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy")
fun LocalDate.toShortString(): String {
    return format(shortFormatter)
}

fun LocalDate.toLongString(): String {
    return format(longFormatter)
}
