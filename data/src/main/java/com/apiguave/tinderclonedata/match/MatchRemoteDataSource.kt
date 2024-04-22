package com.apiguave.tinderclonedata.match

import com.apiguave.tinderclonedata.api.auth.AuthProvider
import com.apiguave.tinderclonedata.api.match.FirestoreMatch
import com.apiguave.tinderclonedata.api.match.MatchApi
import com.apiguave.tinderclonedata.api.picture.PictureApi
import com.apiguave.tinderclonedata.api.user.UserApi
import com.apiguave.tinderclonedata.extension.toAge
import com.apiguave.tinderclonedata.extension.toShortString
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import com.apiguave.tinderclonedomain.match.Match
import kotlinx.coroutines.awaitAll

class MatchRemoteDataSource(
    private val authProvider: AuthProvider,
    private val matchApi: MatchApi,
    private val userApi: UserApi,
    private val pictureApi: PictureApi) {

    suspend fun getMatches(): List<Match> = coroutineScope {
        val apiMatches = matchApi.getMatches()
        val matches = apiMatches.map { async { it.toModel() }}.awaitAll()
        matches.filterNotNull()
    }

    private suspend fun FirestoreMatch.toModel(): Match? {
        val userId = this.usersMatched.firstOrNull { it != authProvider.userId!! } ?: return null
        val user = userApi.getUser(userId)
        val pictures = pictureApi.getPictures(user.id, user.pictures)
        return Match(
            this.id,
            user.birthDate?.toDate()?.toAge() ?: 0,
            userId,
            user.name,
            pictures,
            this.timestamp?.toShortString() ?: "",
            this.lastMessage
        )
    }

    suspend fun getMatch(id: String): Match {
        return matchApi.getMatch(id).toModel()!!
    }

}