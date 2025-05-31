package com.apiguave.match_data.source

import com.apiguave.core_firebase.model.FirestoreMatch
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.awaitAll
import com.apiguave.core_firebase.AuthApi
import com.apiguave.core_firebase.MatchApi
import com.apiguave.core_firebase.UserApi
import com.apiguave.match_data.repository.MatchRemoteDataSource
import com.apiguave.match_data.source.extensions.toAge
import com.apiguave.match_data.source.extensions.toLocalDate
import com.apiguave.match_domain.model.Match
import com.apiguave.match_domain.model.MatchProfile


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