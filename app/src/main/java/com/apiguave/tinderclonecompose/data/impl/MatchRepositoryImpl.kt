package com.apiguave.tinderclonecompose.data.impl

import com.apiguave.tinderclonecompose.data.account.AuthRemoteDataSource
import com.apiguave.tinderclonecompose.data.datasource.FirestoreRemoteDataSource
import com.apiguave.tinderclonecompose.data.picture.datasource.PictureRemoteDataSource
import com.apiguave.tinderclonecompose.data.datasource.model.FirestoreMatch
import com.apiguave.tinderclonecompose.data.extension.toAge
import com.apiguave.tinderclonecompose.data.extension.toShortString
import com.apiguave.tinderclonecompose.data.match.MatchRepository
import com.apiguave.tinderclonecompose.data.match.entity.Match
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class MatchRepositoryImpl(
    private val authDataSource: AuthRemoteDataSource,
    private val storageDataSource: PictureRemoteDataSource,
    private val firestoreDataSource: FirestoreRemoteDataSource
): MatchRepository {


    override suspend fun getMatches(): List<Match> {
        val matchModels = firestoreDataSource.getFirestoreMatchModels()
        val matches = coroutineScope {
            matchModels.map { async { it.toMatch() } }.awaitAll()
        }
        return matches.filterNotNull()
    }

    private suspend fun FirestoreMatch.toMatch(): Match? {
        val userId = this.usersMatched.firstOrNull { it != authDataSource.userId } ?: return null
        val user = firestoreDataSource.getFirestoreUserModel(userId)
        val picture = storageDataSource.getPictureFromUser(userId, user.pictures.first())
        return Match(
            this.id,
            user.birthDate?.toDate()?.toAge() ?: 99,
            userId,
            user.name,
            picture.uri.toString(),
            this.timestamp?.toShortString() ?: "",
            this.lastMessage
        )
    }
}