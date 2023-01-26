package com.apiguave.tinderclonecompose.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.apiguave.tinderclonecompose.ui.components.eighteenYearsAgo
import java.time.LocalDate
import kotlin.random.Random
import com.apiguave.tinderclonecompose.R
import com.apiguave.tinderclonecompose.data.repository.CreateUserProfile
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.util.UUID


const val allowProfileGeneration = false
const val maxPictureCount = 7
const val minPictureCount = 2
val maxBirthdate: LocalDate = LocalDate.of(1970, 1, 1)

suspend fun getRandomProfile(context: Context): CreateUserProfile{
        val isMale = Random.nextBoolean()
        val pictureCount = getRandomPictureCount()
        val pictures = coroutineScope { (0 until pictureCount).map { async{ getRandomPicture(context, isMale) } }.awaitAll()  }
        val name = getRandomName(isMale)
        val birthdate = getRandomBirthdate()
        val orientation = Orientation.values().random()

        return CreateUserProfile(name, birthdate, "", isMale, orientation, pictures)
}
fun getRandomUserId(): String = UUID.randomUUID().toString()
fun getRandomPictureCount(): Int = minPictureCount + Random.nextInt(maxPictureCount - minPictureCount)
fun getRandomName(isMale: Boolean): String = if(isMale) maleNames.random() else femaleNames.random()
fun getRandomBirthdate(): LocalDate{
        val minDay = maxBirthdate.toEpochDay()
        val randomDay: Long = Random.nextLong(minDay, eighteenYearsAgo.toEpochDay())
        return  LocalDate.ofEpochDay(randomDay)
}
fun getRandomPicture(context: Context, isMale: Boolean): Bitmap {
        return BitmapFactory.decodeResource(context.resources, if (isMale) malePictures.random() else femalePictures.random())
}


val malePictures = listOf(
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
val femalePictures = listOf(
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
val maleNames = listOf(
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

val femaleNames = listOf(
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