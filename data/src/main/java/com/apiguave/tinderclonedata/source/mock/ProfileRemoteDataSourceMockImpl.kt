package com.apiguave.tinderclonedata.source.mock

import android.content.Context
import android.net.Uri
import com.apiguave.tinderclonedata.R
import com.apiguave.tinderclonedata.repository.profile.ProfileRemoteDataSource
import com.apiguave.tinderclonedata.source.mock.extension.resourceUri
import com.apiguave.tinderclonedomain.profile.Gender
import com.apiguave.tinderclonedomain.profile.LocalPicture
import com.apiguave.tinderclonedomain.profile.Orientation
import com.apiguave.tinderclonedomain.profile.Picture
import com.apiguave.tinderclonedomain.profile.Profile
import com.apiguave.tinderclonedomain.profile.RemotePicture
import com.apiguave.tinderclonedomain.profile.UserProfile
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.util.UUID
import kotlin.random.Random

class ProfileRemoteDataSourceMockImpl(private val context: Context) : ProfileRemoteDataSource {
    private val userProfile = UserProfile(
        "mock_user",
        "John Doe",
        "01/01/2000",
        "A lover of libraries, coffee, and perhaps, you.",
        Gender.MALE,
        Orientation.WOMEN,
        emptyList(),
        emptyList(),
        listOf(
            RemotePicture(context.resourceUri(R.drawable.man_1).toString(), "picture1.jpg"),
            RemotePicture(context.resourceUri(R.drawable.man_2).toString(), "picture2.jpg"),
            RemotePicture(context.resourceUri(R.drawable.man_3).toString(), "picture3.jpg")
        )
    )
    override suspend fun getUserProfile(): UserProfile = userProfile

    override suspend fun createProfile(
        userId: String,
        name: String,
        birthdate: LocalDate,
        bio: String,
        gender: Gender,
        orientation: Orientation,
        pictures: List<LocalPicture>
    ) {
        delay(2000)
    }

    override suspend fun updateProfile(
        bio: String,
        gender: Gender,
        orientation: Orientation,
        pictures: List<Picture>
    ): UserProfile {
        delay(2000)
        return userProfile
    }

    override suspend fun getProfiles(): List<Profile> {
        delay(3000)
        return (0 until 10).map { getRandomProfile(false) }.toList()
    }

    override suspend fun passProfile(profile: Profile) {}

    override suspend fun likeProfile(profile: Profile): String? = null

    private suspend fun getRandomProfile(isMale: Boolean): Profile {
        val pictures = coroutineScope { (0 until 3).map { async { getRandomPicture(isMale) } }.awaitAll()  }
        val name = getRandomName(isMale)
        val age = getRandomAge()
        return Profile(getRandomUserId(), name, age, pictures.map { it.toString() })
    }

    private fun getRandomUserId(): String = UUID.randomUUID().toString()
    private fun getRandomName(isMale: Boolean): String = if(isMale) maleNames.random() else femaleNames.random()
    private fun getRandomAge(): Int = Random.nextInt(18, 30)
    private fun getRandomPicture(isMale: Boolean): Uri {
        return context.resourceUri(if (isMale) malePictures.random() else femalePictures.random())
    }

    private val malePictures = listOf(
        R.drawable.man_1,
        R.drawable.man_2,
        R.drawable.man_3,
        R.drawable.man_4,
        R.drawable.man_5,
        R.drawable.man_6,
        R.drawable.man_7,
        R.drawable.man_8,
        R.drawable.man_9,
        R.drawable.man_10,
        R.drawable.man_11,
        R.drawable.man_12,
    )
    private val femalePictures = listOf(
        R.drawable.woman_1,
        R.drawable.woman_2,
        R.drawable.woman_3,
        R.drawable.woman_4,
        R.drawable.woman_5,
        R.drawable.woman_6,
        R.drawable.woman_7,
        R.drawable.woman_8,
        R.drawable.woman_9,
        R.drawable.woman_10,
        R.drawable.woman_11,
        R.drawable.woman_12,
    )
    private val maleNames = listOf(
        "Ethan" ,
        "Reagan" ,
        "Bryson" ,
        "Blake" ,
        "Edgar" ,
        "Clark" ,
        "Dane" ,
        "Heath" ,
        "Charlie" ,
        "Elian" ,
        "Allen" ,
        "Walker" ,
        "Jadon" ,
        "Fernando" ,
        "Ellis" ,
        "Mohammed" ,
        "Kadin" ,
        "Joey" ,
        "Octavio" ,
        "Wyatt" ,
        "Aryan" ,
        "Cayden" ,
        "Jamari" ,
        "Donald" ,
        "Josue" ,
        "Kendrick" ,
        "Emmanuel" ,
        "Dustin" ,
        "Korbin" ,
        "Jasper" ,
        "Cameron" ,
        "Isiah" ,
        "Jeremy" ,
        "Alexzander" ,
        "Jared" ,
        "Bentley" ,
        "Oscar" ,
        "Ramon" ,
        "Jermaine" ,
        "John" ,
        "Tristian" ,
        "Jacob" ,
        "Yahir" ,
        "Giovanni" ,
        "Jaylon" ,
        "Marcus" ,
        "Javier" ,
        "Mathew" ,
        "Rayan" ,
        "Prince" ,
        "Jay" ,
        "Sincere" ,
        "Jesus" ,
        "Brayden" ,
        "Kayden" ,
        "Rhys" ,
        "Brodie" ,
        "Drake" ,
        "Landin" ,
        "Demetrius" ,
        "Mohamed" ,
        "Cason" ,
        "Calvin" ,
        "Maxwell" ,
        "Matias" ,
        "Anthony" ,
        "Liam" ,
        "Rodney" ,
        "Orion" ,
        "Ray" ,
        "August" ,
        "Matthew" ,
        "Jabari" ,
        "Joaquin" ,
        "Kole" ,
        "Brandon" ,
        "Konnor" ,
        "Rigoberto" ,
        "Jack" ,
        "Spencer" ,
        "Devan" ,
        "Aron" ,
        "Leo" ,
        "Marco" ,
        "Stephen" ,
        "Haiden" ,
        "Ian" ,
        "Coleman" ,
        "Levi" ,
        "Jayvion" ,
        "Keyon" ,
        "Brenton" ,
        "Payton" ,
        "Malachi" ,
        "Milton" ,
        "Tyrone" ,
        "Deegan" ,
        "Immanuel" ,
        "Eugene" ,
        "Harrison"
    )

    private val femaleNames = listOf(
        "Dominique" ,
        "Nyla" ,
        "Paulina" ,
        "Theresa" ,
        "Paula" ,
        "June" ,
        "Taniyah" ,
        "Zaniyah" ,
        "Kenna" ,
        "Lorelei" ,
        "Adeline" ,
        "Leyla" ,
        "Kennedy" ,
        "Fatima" ,
        "Emily" ,
        "Paityn" ,
        "Cadence" ,
        "Naima" ,
        "Khloe" ,
        "Justice" ,
        "Jaylyn" ,
        "Aleena" ,
        "Kaylie" ,
        "Nicole" ,
        "Brittany" ,
        "Sarai" ,
        "Bryanna" ,
        "Breanna" ,
        "Alejandra" ,
        "Imani" ,
        "Sophie" ,
        "Irene" ,
        "Alena" ,
        "Sharon" ,
        "Summer" ,
        "Danika" ,
        "Kiera" ,
        "Jocelynn" ,
        "Gabriella" ,
        "Jadyn" ,
        "Frances" ,
        "Ashtyn" ,
        "Skye" ,
        "Kaliyah" ,
        "Aliza" ,
        "Penelope" ,
        "Phoebe" ,
        "Addisyn" ,
        "Audrina" ,
        "Anya" ,
        "Aubrey" ,
        "Tessa" ,
        "Dayana" ,
        "Angie" ,
        "Hanna" ,
        "Iyana" ,
        "Carmen" ,
        "Leila" ,
        "Jaylah" ,
        "Meghan" ,
        "Aimee" ,
        "Madelynn" ,
        "Noemi" ,
        "Kamila" ,
        "Janiya" ,
        "Kaia" ,
        "Kenzie" ,
        "Camilla" ,
        "Nancy" ,
        "Ariana" ,
        "Magdalena" ,
        "Jamie" ,
        "Abagail" ,
        "Barbara" ,
        "Nayeli" ,
        "Raelynn" ,
        "Precious" ,
        "Lilia" ,
        "Jaycee" ,
        "Alaina" ,
        "Isabelle" ,
        "Adrianna" ,
        "Sanai" ,
        "Charlie" ,
        "Kelly" ,
        "Edith" ,
        "Gemma" ,
        "Raina" ,
        "Marisa" ,
        "Kierra" ,
        "Nola" ,
        "Rayna" ,
        "Kamora" ,
        "Dahlia" ,
        "Alondra" ,
        "Cassandra" ,
        "Whitney" ,
        "Adison" ,
        "Arely" ,
        "Carolina"
    )
}