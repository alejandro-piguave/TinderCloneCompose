package com.apiguave.tinderclonedata.source.match

import com.apiguave.tinderclonedata.repository.match.MatchRemoteDataSource
import com.apiguave.tinderclonedata.source.api.auth.AuthProvider
import com.apiguave.tinderclonedata.source.api.match.FirestoreMatch
import com.apiguave.tinderclonedata.source.api.match.MatchApi
import com.apiguave.tinderclonedata.source.api.picture.PictureApi
import com.apiguave.tinderclonedata.source.api.user.UserApi
import com.apiguave.tinderclonedata.source.extension.toAge
import com.apiguave.tinderclonedata.source.extension.toShortString
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
        val userId = this.usersMatched.firstOrNull { it != AuthProvider.userId!! } ?: return null
        val user = UserApi.getUser(userId) ?: return null
        val pictures = PictureApi.getPictures(user.id, user.pictures)
        return Match(
            this.id,
            Profile(
                userId,
                user.name,
                user.birthDate?.toDate()?.toAge() ?: 0,
                pictures,
            ),
            this.timestamp?.toShortString() ?: "",
            this.lastMessage
        )
    }

    override suspend fun getMatch(id: String): Match {
        return MatchApi.getMatch(id).toModel()!!
    }

}