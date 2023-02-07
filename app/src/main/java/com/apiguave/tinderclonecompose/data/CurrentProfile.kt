package com.apiguave.tinderclonecompose.data

data class CurrentProfile(
    val id: String = "",
    val name: String = "",
    val birthDate: String = "",
    val bio: String = "",
    val genderIndex: Int = -1,
    val orientationIndex: Int = -1,
    val pictures: List<FirebasePicture> = emptyList()
){

    fun isDataEqual(newBio: String, newGenderIndex: Int, newOrientationIndex: Int): Boolean {
        return newBio == this.bio && newGenderIndex == this.genderIndex && newOrientationIndex == this.orientationIndex
    }

    fun toModifiedProfile(newBio: String = this.bio,
                          newGenderIndex: Int = this.genderIndex,
                          newOrientationIndex: Int = this.orientationIndex,
                          newPictures: List<FirebasePicture> = this.pictures
    ): CurrentProfile{
        return this.copy(
            bio = if(newBio != this.bio) newBio else this.bio,
            genderIndex = if(newGenderIndex != this.genderIndex) newGenderIndex else this.genderIndex,
            orientationIndex = if(newOrientationIndex != this.orientationIndex) newOrientationIndex else this.orientationIndex,
            pictures = if(newPictures != this.pictures) newPictures else this.pictures
        )
    }

    fun toModifiedData(newBio: String, newGenderIndex: Int, newOrientationIndex: Int): Map<String, Any> {
        val data = mutableMapOf<String, Any>()
        if(newBio != this.bio){
            data[FirestoreUserProperties.bio] = newBio
        }
        if(newGenderIndex != this.genderIndex){
            data[FirestoreUserProperties.isMale] = newGenderIndex == 0
        }
        if(newOrientationIndex != this.orientationIndex){
            data[FirestoreUserProperties.orientation] = Orientation.values()[newOrientationIndex]
        }
        return data
    }
}