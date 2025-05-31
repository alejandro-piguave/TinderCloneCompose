package com.apiguave.home_ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.apiguave.core_ui.model.ProfilePictureState
import com.apiguave.profile_domain.model.Profile

@Composable
fun ProfileCardView(profile: Profile, pictures: List<ProfilePictureState>, modifier: Modifier = Modifier, contentModifier: Modifier = Modifier){
    var currentIndex by remember{ mutableStateOf(0) }

    val gradient = Brush.verticalGradient(
        colorStops = arrayOf(
            .68f to Color.Transparent,
            .92f to Color.Black
        )
    )
    Card(modifier = modifier
        .fillMaxWidth()
        .aspectRatio(.6f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(Modifier.fillMaxSize()) {
            //Picture
            when(val picture = pictures[currentIndex]) {
                is ProfilePictureState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is ProfilePictureState.Remote -> {
                    AsyncImage(
                        modifier = Modifier.fillMaxSize(),
                        model = picture.uri,
                        contentScale = ContentScale.Crop,
                        contentDescription = null)
                }
            }

            //Gradient
            Spacer(
                Modifier
                    .fillMaxSize()
                    .background(gradient))
            Box(contentModifier.fillMaxSize()){
                //Upper picture index indicator
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(6.dp)) {
                    repeat(pictures.size){ index ->
                        Box(
                            Modifier
                                .weight(1f)
                                .height(3.dp)
                                .padding(horizontal = 4.dp)
                                .alpha(if (index == currentIndex) 1f else .5f)
                                .background(if (index == currentIndex) Color.White else Color.LightGray))
                    }
                }
                //Clickable
                Row(Modifier.fillMaxSize()) {
                    Box(modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .clickable { if (currentIndex > 0) currentIndex-- })
                    Box(modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .clickable { if (currentIndex < pictures.size - 1) currentIndex++ }
                    )
                }
                //Information
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = profile.name, fontWeight = FontWeight.SemiBold, color = Color.White, fontSize = 30.sp)
                    Spacer(Modifier.width(8.dp))
                    Text(text = profile.age.toString(), color = Color.White, fontSize = 28.sp)
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        modifier = Modifier.size(28.dp),
                        imageVector = Icons.Default.Info,
                        tint = Color.White,
                        contentDescription = null)
                }

            }
        }
    }
}
