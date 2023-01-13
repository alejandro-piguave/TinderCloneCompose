package com.apiguave.tinderclonecompose.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apiguave.tinderclonecompose.R
import com.apiguave.tinderclonecompose.ui.theme.Orange
import com.apiguave.tinderclonecompose.ui.theme.Pink

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MatchListView() {
    LazyColumn(Modifier.fillMaxSize()) {
        stickyHeader { MatchListHeader() }
        items(10) {
            MatchView()
        }
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun MatchListHeader() {
    Text(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        text = "Messages",
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        color = Pink,
        style = TextStyle(
            brush = Brush.linearGradient(
                colors = listOf(Pink, Orange)
            )
        ),
        textAlign = TextAlign.Center
    )
}

@Composable
fun MatchView() {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.tinder_logo),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .size(40.dp),
                colorFilter = ColorFilter.tint(Pink)
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