package com.apiguave.match_domain.model

import java.time.LocalDate

data class Match(
    val id: String,
    val profile: MatchProfile,
    val creationDate: LocalDate,
    val lastMessage: String?
)

