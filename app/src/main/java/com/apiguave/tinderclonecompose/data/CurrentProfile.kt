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
    fun toModifiedData(newBio: String, newGenderIndex: Int, newOrientationIndex: Int): Map<String, Any> {
        val data = mutableMapOf<String, Any>()
        if(newBio != this.bio){
            data[FirestoreUserProperties.bio] = this.bio
        }
        if(newGenderIndex != this.genderIndex){
            data[FirestoreUserProperties.isMale] = genderIndex == 0
        }
        if(newOrientationIndex != this.orientationIndex){
            data[FirestoreUserProperties.orientation] = Orientation.values()[orientationIndex]
        }
        return data
    }
}