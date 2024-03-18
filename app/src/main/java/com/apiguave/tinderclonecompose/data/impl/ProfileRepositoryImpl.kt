package com.apiguave.tinderclonecompose.data.impl

import android.content.Context
import com.apiguave.tinderclonecompose.data.auth.AuthRepository
import com.apiguave.tinderclonecompose.data.extension.toUserProfile
import com.apiguave.tinderclonecompose.data.profile.repository.ProfileRepository
import com.apiguave.tinderclonecompose.data.profile.repository.CreateUserProfile
import com.apiguave.tinderclonecompose.data.profile.repository.UserProfile
import com.apiguave.tinderclonecompose.data.profile.repository.Gender
import com.apiguave.tinderclonecompose.data.profile.repository.Orientation
import com.apiguave.tinderclonecompose.data.picture.repository.Picture
import com.apiguave.tinderclonecompose.data.picture.repository.PictureRepository
import com.apiguave.tinderclonecompose.data.user.repository.UserRepository
import com.apiguave.tinderclonecompose.ui.extension.getRandomProfile
import com.apiguave.tinderclonecompose.ui.extension.getRandomUserId

class ProfileRepositoryImpl(
    private val context: Context,
    private val pictureRepository: PictureRepository,
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
): ProfileRepository {

    override suspend fun getProfile(): UserProfile {
        val currentUser = userRepository.getCurrentUser()
        val currentPictures = pictureRepository.getProfilePictures()
        return currentUser.toUserProfile(currentPictures)
    }

    override suspend fun createProfile(profile: CreateUserProfile) {
        val filenames = pictureRepository.uploadProfilePictures(profile.pictures)
        userRepository.createUser(
            authRepository.userId,
            profile.name,
            profile.birthdate,
            profile.bio,
            profile.gender,
            profile.orientation,
            filenames.map { it.filename })
    }

    private suspend fun createRandomProfile() {
        val userId = getRandomUserId()
        val profile = getRandomProfile(context)
        val filenames = pictureRepository.uploadPictures(userId, profile.pictures)
        userRepository.createUser(
            userId,
            profile.name,
            profile.birthdate,
            profile.bio,
            profile.gender,
            profile.orientation,
            filenames.map { it.filename })
    }

    override suspend fun createRandomProfiles(amount: Int) {
        //Profiles are not created concurrently to avoid memory overhead when loading the images
        for(i in 0 until amount) {
            createRandomProfile()
        }
    }

    override suspend fun updateProfile(
        bio: String,
        gender: Gender,
        orientation: Orientation,
        pictures: List<Picture>
    ): UserProfile {
        val currentProfile = getProfile()
        val arePicturesEqual = currentProfile.pictures == pictures

        when {
            arePicturesEqual -> {
                userRepository.updateCurrentUser(bio, gender, orientation, currentProfile.pictures.map { it.filename })
            }
            !arePicturesEqual -> {
                val filenames = pictureRepository.updateProfilePictures(
                    currentProfile.pictures,
                    pictures
                )
                userRepository.updateCurrentUser(bio, gender, orientation, filenames.map { it.filename })
            }
        }

        return getProfile()
    }
}