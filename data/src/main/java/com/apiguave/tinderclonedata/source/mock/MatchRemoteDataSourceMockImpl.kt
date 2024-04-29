package com.apiguave.tinderclonedata.source.mock

import android.content.Context
import com.apiguave.tinderclonedata.R
import com.apiguave.tinderclonedata.repository.match.MatchRemoteDataSource
import com.apiguave.tinderclonedata.source.mock.extension.resourceUri
import com.apiguave.tinderclonedomain.match.Match
import com.apiguave.tinderclonedomain.profile.Profile
import kotlinx.coroutines.delay

class MatchRemoteDataSourceMockImpl(context: Context) : MatchRemoteDataSource {
    private val matchList = listOf(
        Match(
            "1",
            Profile(
                "1",
                "Victoria",
                19,
                listOf(context.resourceUri(R.drawable.woman_1).toString())
            ),
            "12/12/22",
            "Hey!"
        ),
        Match(
            "2",
            Profile("2", "Marta", 19, listOf(context.resourceUri(R.drawable.woman_2).toString())),
            "12/12/23",
            "Hey!"
        ),
        Match(
            "3",
            Profile(
                "3",
                "Juliette",
                23,
                listOf(context.resourceUri(R.drawable.woman_3).toString())
            ),
            "12/12/22",
            "Hey!"
        ),
        Match(
            "4",
            Profile("4", "María", 22, listOf(context.resourceUri(R.drawable.woman_4).toString())),
            "12/12/22",
            "Hey!"
        ),
        Match(
            "5",
            Profile("5", "Julia", 18, listOf(context.resourceUri(R.drawable.woman_5).toString())),
            "12/12/22",
            "Hey!"
        ),
        Match(
            "6",
            Profile("6", "Lily", 20, listOf(context.resourceUri(R.drawable.woman_6).toString())),
            "12/12/22",
            "Hey!"
        ),
        Match(
            "7",
            Profile(
                "7",
                "Christina",
                20,
                listOf(context.resourceUri(R.drawable.woman_7).toString())
            ),
            "12/12/22",
            "Hey!"
        ),
        Match(
            "8",
            Profile("8", "Jana", 20, listOf(context.resourceUri(R.drawable.woman_8).toString())),
            "12/12/22",
            "Hey!"
        ),
        Match(
            "9",
            Profile("9", "Greta", 20, listOf(context.resourceUri(R.drawable.woman_9).toString())),
            "12/12/22",
            "Hey!"
        ),
        Match(
            "10",
            Profile(
                "10",
                "Violeta",
                23,
                listOf(context.resourceUri(R.drawable.woman_10).toString())
            ),
            "12/12/22",
            "Hey!"
        ),
        Match(
            "11",
            Profile(
                "11",
                "Daniela",
                19,
                listOf(context.resourceUri(R.drawable.woman_11).toString())
            ),
            "12/12/22",
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