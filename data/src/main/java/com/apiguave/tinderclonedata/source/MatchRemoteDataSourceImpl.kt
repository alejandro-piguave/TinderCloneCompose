package com.apiguave.tinderclonedata.source

import com.apiguave.tinderclonedata.repository.match.MatchRemoteDataSource
import com.apiguave.tinderclonedata.source.firebase.AuthApi
import com.apiguave.tinderclonedata.source.firebase.model.FirestoreMatch
import com.apiguave.tinderclonedata.source.firebase.MatchApi
import com.apiguave.tinderclonedata.source.firebase.UserApi
import com.apiguave.tinderclonedata.source.extension.toAge
import com.apiguave.tinderclonedata.source.extension.toLocalDate
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import com.apiguave.tinderclonedomain.match.Match
import com.apiguave.tinderclonedomain.profile.Profile
import kotlinx.coroutines.awaitAll

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
            Profile(
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