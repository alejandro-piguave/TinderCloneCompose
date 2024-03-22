package com.apiguave.tinderclonedata.match.datasource

import com.apiguave.tinderclonedata.api.match.FirestoreMatch
import com.apiguave.tinderclonedata.api.match.MatchApi
import com.apiguave.tinderclonedata.api.picture.PictureApi
import com.apiguave.tinderclonedata.api.user.UserApi
import com.apiguave.tinderclonedata.api.auth.exception.AuthException
import com.apiguave.tinderclonedata.extension.toAge
import com.apiguave.tinderclonedata.extension.toShortString
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import com.apiguave.tinderclonedata.match.repository.Match
import kotlinx.coroutines.awaitAll

class MatchRemoteDataSource(private val matchApi: MatchApi, private val userApi: UserApi, private val pictureApi: PictureApi) {

    private val currentUserId: String
        get() = FirebaseAuth.getInstance().currentUser?.uid ?: throw AuthException("User not logged in")

    suspend fun getMatches(): List<Match> = coroutineScope {
        val apiMatches = matchApi.getMatches(currentUserId)
        val matches = apiMatches.map { async { getMatch(it) }}.awaitAll()
        matches.filterNotNull()
    }

    private suspend fun getMatch(match: FirestoreMatch): Match? {
        val userId = match.usersMatched.firstOrNull { it != currentUserId } ?: return null
        val user = userApi.getUser(userId)
        val picture = pictureApi.getPicture(user.id, user.pictures.first())
        return Match(
            match.id,
            user.birthDate?.toDate()?.toAge() ?: 0,
            userId,
            user.name,
            picture.uri.toString(),
            match.timestamp?.toShortString() ?: "",
            match.lastMessage
        )
    }


}