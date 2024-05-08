package com.apiguave.tinderclonedata.source.mock

import com.apiguave.tinderclonedata.repository.match.MatchRemoteDataSource
import com.apiguave.tinderclonedomain.match.Match
import com.apiguave.tinderclonedomain.profile.Profile
import kotlinx.coroutines.delay
import java.time.LocalDate

class MatchRemoteDataSourceMockImpl : MatchRemoteDataSource {
    private val matchList = listOf(
        Match(
            "1",
            Profile("1", "Victoria", 19, listOf("woman_1.jpg")),
            LocalDate.of(22,12,12),
            "Hey!"
        ),
        Match(
            "2",
            Profile("2", "Marta", 19, listOf("woman_2.jpg")),
            LocalDate.of(23,12,12),
            "Hey!"
        ),
        Match(
            "3",
            Profile("3", "Juliette", 23, listOf("woman_3.jpg")),
            LocalDate.of(22,12,12),
            "Hey!"
        ),
        Match(
            "4",
            Profile("4", "María", 22, listOf("woman_4.jpg")),
            LocalDate.of(22,12,12),
            "Hey!"
        ),
        Match(
            "5",
            Profile("5", "Julia", 18, listOf("woman_5.jpg")),
            LocalDate.of(22,12,12),
            "Hey!"
        ),
        Match(
            "6",
            Profile("6", "Lily", 20, listOf("woman_6.jpg")),
            LocalDate.of(22,12,12),
            "Hey!"
        ),
        Match(
            "7",
            Profile("7", "Christina", 20, listOf("woman_7.jpg")),
            LocalDate.of(22,12,12),
            "Hey!"
        ),
        Match(
            "8",
            Profile("8", "Jana", 20, listOf("woman_8.jpg")),
            LocalDate.of(22,12,12),
            "Hey!"
        ),
        Match(
            "9",
            Profile("9", "Greta", 20, listOf("woman_9.jpg")),
            LocalDate.of(22,12,12),
            "Hey!"
        ),
        Match(
            "10",
            Profile("10", "Violeta", 23, listOf("woman_10.jpg")),
            LocalDate.of(22,12,12),
            "Hey!"
        ),
        Match(
            "11",
            Profile("11", "Daniela", 19, listOf("woman_11.jpg")),
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