package com.apiguave.tinderclonecompose.data.impl

import com.apiguave.tinderclonecompose.data.auth.AuthRepository
import com.apiguave.tinderclonecompose.data.match.datasource.FirestoreMatch
import com.apiguave.tinderclonecompose.data.extension.toAge
import com.apiguave.tinderclonecompose.data.extension.toShortString
import com.apiguave.tinderclonecompose.data.match.datasource.MatchRemoteDataSource
import com.apiguave.tinderclonecompose.data.match.repository.MatchRepository
import com.apiguave.tinderclonecompose.data.match.repository.Match
import com.apiguave.tinderclonecompose.data.picture.repository.PictureRepository
import com.apiguave.tinderclonecompose.data.user.repository.UserRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class MatchRepositoryImpl(
    private val authDataSource: AuthRepository,
    private val pictureRepository: PictureRepository,
    private val userRepository: UserRepository,
    private val matchRemoteDataSource: MatchRemoteDataSource,
): MatchRepository {

    override suspend fun getMatches(): List<Match> {
        val matchModels = matchRemoteDataSource.getFirestoreMatchModels()
        val matches = coroutineScope {
            matchModels.map { async { it.toMatch() } }.awaitAll()
        }
        return matches.filterNotNull()
    }

    private suspend fun FirestoreMatch.toMatch(): Match? {
        val userId = this.usersMatched.firstOrNull { it != authDataSource.userId } ?: return null
        val user = userRepository.getUser(userId)
        val picture = pictureRepository.getPicture(user)
        return Match(
            this.id,
            user.birthDate.toAge(),
            userId,
            user.name,
            picture.uri.toString(),
            this.timestamp?.toShortString() ?: "",
            this.lastMessage
        )
    }
}