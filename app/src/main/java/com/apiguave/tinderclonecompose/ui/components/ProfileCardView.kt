package com.apiguave.tinderclonecompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.apiguave.tinderclonecompose.data.Profile

@Composable
fun ProfileCardView(profile: Profile, modifier: Modifier = Modifier){
    var currentIndex by remember{ mutableStateOf(0) }

    Card(modifier = modifier
        .fillMaxWidth()
        .aspectRatio(.6f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(Modifier.fillMaxSize()) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = profile.pictures[currentIndex],
                contentScale = ContentScale.Crop,
                contentDescription = null)
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(6.dp)) {
                repeat(profile.pictures.size){ index ->
                    Box(
                        Modifier
                            .weight(1f)
                            .height(3.dp)
                            .padding(horizontal = 4.dp)
                            .alpha(if (index == currentIndex) 1f else .5f)
                            .background(if (index == currentIndex) Color.White else Color.LightGray))
                }
            }
            Row(
                Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .padding(12.dp)) {
                Text(text = profile.name, fontWeight = FontWeight.Bold, color = Color.White, fontSize = 30.sp)
                Spacer(Modifier.width(8.dp))
                Text(text = profile.age.toString(), color = Color.White, fontSize = 30.sp)
            }
            Row(Modifier.fillMaxSize()) {
                Box(modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .clickable { if (currentIndex > 0) currentIndex-- })
                Box(modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .clickable { if (currentIndex < profile.pictures.size - 1) currentIndex++ }
                )
            }

        }

    }
}
