package com.apiguave.feature_chat.extensions

import java.time.LocalDate
import java.time.format.DateTimeFormatter

val shortFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yy")
    //SimpleDateFormat("dd/MM/yy")
fun LocalDate.toShortString(): String {
    return format(shortFormatter)
}

