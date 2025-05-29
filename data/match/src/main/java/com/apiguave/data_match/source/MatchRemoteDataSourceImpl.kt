package com.apiguave.data_match.source

import com.apiguave.core_network.model.FirestoreMatch
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.awaitAll
import com.apiguave.core_network.AuthApi
import com.apiguave.core_network.MatchApi
import com.apiguave.core_network.UserApi
import com.apiguave.data_match.repository.MatchRemoteDataSource
import com.apiguave.data_match.source.extensions.toAge
import com.apiguave.data_match.source.extensions.toLocalDate
import com.apiguave.domain_match.model.Match
import com.apiguave.domain_match.model.MatchProfile


class MatchRemoteDataSourceImpl: MatchRemoteDataSource {

    override suspend fun getMatches(): List<Match> = coroutineScope {
        val apiMatches = MatchApi.getMatches()
        val matches = apiMatches.map { async { it.toModel() }}.awaitAll()
        matches.filterNotNull()
    }

    private suspend fun FirestoreMatch.toModel(): Match? {
        val userId = this.usersMatched.firstOrNull { it != AuthApi.userId!! } ?: return null
        val user = UserApi.getUser(userId) ?: return null
        return Match(
            this.id,
            MatchProfile(
                userId,
                user.name,
                user.birthDate!!.toAge(),
                user.pictures,
            ),
            this.timestamp!!.toLocalDate(),
            this.lastMessage
        )
    }

    override suspend fun getMatch(id: String): Match {
        return MatchApi.getMatch(id).toModel()!!
    }

}