package com.apiguave.tinderclonedata.profile

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import com.apiguave.tinderclonedata.R
import com.apiguave.tinderclonedomain.profile.ProfileGenerator
import com.apiguave.tinderclonedomain.profile.GeneratedProfile
import com.apiguave.tinderclonedomain.profile.Gender
import com.apiguave.tinderclonedomain.profile.LocalPicture
import com.apiguave.tinderclonedomain.profile.Orientation
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.time.LocalDate
import java.util.UUID
import kotlin.random.Random

class ProfileGeneratorImpl(private val context: Context): ProfileGenerator {
    override suspend fun generateProfile(): GeneratedProfile = getRandomProfile(context)

    override suspend fun generateProfiles(amount: Int): List<GeneratedProfile> = (0 until amount).map { generateProfile() }

    private val eighteenYearsAgo: LocalDate = LocalDate.now().minusYears(18L)
    private val maxBirthdate: LocalDate = LocalDate.of(1970, 1, 1)

    private suspend fun getRandomProfile(context: Context): GeneratedProfile {
        val isMale = Random.nextBoolean()
        val pictures = coroutineScope { (0 until 3).map { async { getRandomPicture(context, isMale) } }.awaitAll()  }
        val name = getRandomName(isMale)
        val birthdate = getRandomBirthdate()
        val orientation = Orientation.values().random()

        return GeneratedProfile(getRandomUserId(), name, birthdate, "", if(isMale) Gender.MALE else Gender.FEMALE, orientation, pictures.map { LocalPicture(it.toString()) })
    }

    private fun getRandomUserId(): String = UUID.randomUUID().toString()
    private fun getRandomName(isMale: Boolean): String = if(isMale) maleNames.random() else femaleNames.random()
    private fun getRandomBirthdate(): LocalDate {
        val minDay = maxBirthdate.toEpochDay()
        val randomDay: Long = Random.nextLong(minDay, eighteenYearsAgo.toEpochDay())
        return  LocalDate.ofEpochDay(randomDay)
    }
    private fun getRandomPicture(context: Context, isMale: Boolean): Uri {
        return context.resourceUri(if (isMale) malePictures.random() else femalePictures.random())
    }

    private fun Context.resourceUri(resourceId: Int): Uri = with(resources) {
        Uri.Builder()
            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .authority(getResourcePackageName(resourceId))
            .appendPath(getResourceTypeName(resourceId))
            .appendPath(getResourceEntryName(resourceId))
            .build()
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