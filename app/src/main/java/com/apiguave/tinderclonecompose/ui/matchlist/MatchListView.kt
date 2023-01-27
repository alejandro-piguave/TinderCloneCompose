package com.apiguave.tinderclonecompose.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apiguave.tinderclonecompose.ui.components.BlankAppBar
import kotlin.random.Random

@Composable
fun MatchListView(onNavigateToChatView: () -> Unit, onArrowBackPressed: () -> Unit) {
    Scaffold(Modifier.fillMaxSize(), topBar = {
        BlankAppBar(text = "Messages", onArrowBackPressed = onArrowBackPressed)
    }) { padding ->
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(padding)) {
            items(10) {
                MatchView(onNavigateToChatView)
            }
        }
    }

}

@Composable
fun MatchView(onClick: () -> Unit) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable(onClick = onClick)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(randomColor())
            )

            Column(Modifier.fillMaxWidth()) {
                Row(Modifier.fillMaxWidth()) {
                    Text("Jane Doe", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.width(10.dp))
                    Text("20", fontSize = 20.sp)
                }
                Text("Say something nice!", fontWeight = FontWeight.Light)
            }
        }
    }
}

fun randomColor() = Color(Random.nextFloat(), Random.nextFloat(), Random.nextFloat())
