package com.apiguave.match_data.repository

import com.apiguave.match_domain.model.Match
import com.apiguave.match_domain.model.MatchProfile
import com.apiguave.match_domain.repository.MatchRepository
import kotlinx.coroutines.delay
import java.time.LocalDate

class FakeMatchRepositoryImpl: MatchRepository {
    private val matchList = listOf(
        Match(
            "1",
            MatchProfile("1", "Victoria", 19, listOf("woman_1.jpg")),
            LocalDate.of(22,12,12),
            "Hey!"
        ),
        Match(
            "2",
            MatchProfile("2", "Marta", 19, listOf("woman_2.jpg")),
            LocalDate.of(23,12,12),
            "Hey!"
        ),
        Match(
            "3",
            MatchProfile("3", "Juliette", 23, listOf("woman_3.jpg")),
            LocalDate.of(22,12,12),
            "Hey!"
        ),
        Match(
            "4",
            MatchProfile("4", "María", 22, listOf("woman_4.jpg")),
            LocalDate.of(22,12,12),
            "Hey!"
        ),
        Match(
            "5",
            MatchProfile("5", "Julia", 18, listOf("woman_5.jpg")),
            LocalDate.of(22,12,12),
            "Hey!"
        ),
        Match(
            "6",
            MatchProfile("6", "Lily", 20, listOf("woman_6.jpg")),
            LocalDate.of(22,12,12),
            "Hey!"
        ),
        Match(
            "7",
            MatchProfile("7", "Christina", 20, listOf("woman_7.jpg")),
            LocalDate.of(22,12,12),
            "Hey!"
        ),
        Match(
            "8",
            MatchProfile("8", "Jana", 20, listOf("woman_8.jpg")),
            LocalDate.of(22,12,12),
            "Hey!"
        ),
        Match(
            "9",
            MatchProfile("9", "Greta", 20, listOf("woman_9.jpg")),
            LocalDate.of(22,12,12),
            "Hey!"
        ),
        Match(
            "10",
            MatchProfile("10", "Violeta", 23, listOf("woman_10.jpg")),
            LocalDate.of(22,12,12),
            "Hey!"
        ),
        Match(
            "11",
            MatchProfile("11", "Daniela", 19, listOf("woman_11.jpg")),
            LocalDate.of(22,12,12),
            "Hey!"
        )
    )
    override suspend fun getMatches(): List<Match> {
        delay(1000)
        return matchList
    }

    override suspend fun getMatch(id: String): Match {
        delay(1000)
        return matchList.first()
    }
}