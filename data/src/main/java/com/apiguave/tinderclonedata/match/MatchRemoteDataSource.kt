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
        val matches = apiMatches.map { async { getMatch(it) }}.awaitAll()
        matches.filterNotNull()
    }

    private suspend fun getMatch(match: FirestoreMatch): Match? {
        val userId = match.usersMatched.firstOrNull { it != authProvider.userId!! } ?: return null
        val user = userApi.getUser(userId)
        val picture = pictureApi.getPicture(user.id, user.pictures.first())
        return Match(
            match.id,
            user.birthDate?.toDate()?.toAge() ?: 0,
            userId,
            user.name,
            picture.uri,
            match.timestamp?.toShortString() ?: "",
            match.lastMessage
        )
    }


}