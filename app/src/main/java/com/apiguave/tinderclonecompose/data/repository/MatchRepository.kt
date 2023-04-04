package com.apiguave.tinderclonecompose.data.repository

import com.apiguave.tinderclonecompose.data.*
import com.apiguave.tinderclonecompose.data.datasource.AuthDataSource
import com.apiguave.tinderclonecompose.data.datasource.FirestoreDataSource
import com.apiguave.tinderclonecompose.data.datasource.StorageDataSource
import com.apiguave.tinderclonecompose.extensions.*
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

object MatchRepository {
    private val authDataSource = AuthDataSource()
    private val storageDataSource = StorageDataSource()
    private val firestoreDataSource = FirestoreDataSource()

    suspend fun getMatches(): List<Match> {
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
            user.birthDate?.toAge() ?: 99,
            userId,
            user.name,
            picture.uri,
            this.timestamp?.toShortString() ?: "",
            this.lastMessage
        )
    }
}