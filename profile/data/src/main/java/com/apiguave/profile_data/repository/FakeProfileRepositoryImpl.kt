package com.apiguave.profile_data.repository

import com.apiguave.profile_domain.model.Gender
import com.apiguave.profile_domain.model.Orientation
import com.apiguave.profile_domain.model.Profile
import com.apiguave.profile_domain.model.UserProfile
import com.apiguave.profile_domain.repository.ProfileRepository
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.util.UUID
import kotlin.random.Random

class FakeProfileRepositoryImpl: ProfileRepository {
    private val userProfile = UserProfile(
        "mock_user",
        "John Doe",
        LocalDate.of(2000, 1, 1),
        "A lover of libraries, coffee, and perhaps, you.",
        Gender.MALE,
        Orientation.WOMEN,
        listOf("man_1.jpg, man_2.jpg", "man_3.jpg")
    )
    override suspend fun addProfile(
        userId: String,
        name: String,
        birthdate: LocalDate,
        bio: String,
        gender: Gender,
        orientation: Orientation
    ) {
        delay(2000)
    }

    override suspend fun updateProfile(bio: String, gender: Gender, orientation: Orientation) {
        delay(2000)
    }

    override suspend fun updatePictures(pictureNames: List<String>) {
        delay(2000)
    }

    override suspend fun getProfile(): UserProfile {
        return userProfile
    }

    override suspend fun hasProfile(userId: String): Boolean {
        return true
    }

    override suspend fun likeProfile(profile: Profile): String? {
        return null
    }

    override suspend fun passProfile(profile: Profile) { }

    override suspend fun getProfiles(): List<Profile> {
        delay(1000)
        return (0 until 10).map { getRandomProfile(false) }.toList()
    }

    private fun getRandomProfile(isMale: Boolean): Profile {
        val name = getRandomName(isMale)
        val age = getRandomAge()
        val gender = if (isMale) "man" else "woman"
        return Profile(getRandomUserId(), name, age, (0..5).map { "${gender}_${Random.nextInt(1, 13)}.jpg" })
    }

    private fun getRandomUserId(): String = UUID.randomUUID().toString()
    private fun getRandomName(isMale: Boolean): String = if(isMale) maleNames.random() else femaleNames.random()
    private fun getRandomAge(): Int = Random.nextInt(18, 30)


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