package com.apiguave.editprofile_ui.extensions

import java.time.LocalDate
import java.time.format.DateTimeFormatter

val longFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy")

fun LocalDate.toLongString(): String {
    return format(longFormatter)
}
