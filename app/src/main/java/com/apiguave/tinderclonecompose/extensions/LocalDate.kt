package com.apiguave.tinderclonecompose.extensions

import com.google.firebase.Timestamp
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

fun LocalDate.toTimestamp(): Timestamp{
    return Timestamp(Date.from(this.atStartOfDay(ZoneId.systemDefault()).toInstant()))
}