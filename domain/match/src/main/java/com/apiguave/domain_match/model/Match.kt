package com.apiguave.domain_match.model

import java.time.LocalDate

data class Match(
    val id: String,
    val profile: MatchProfile,
    val creationDate: LocalDate,
    val lastMessage: String?
)

