package com.apiguave.tinderclonecompose.ui.matchlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.apiguave.tinderclonecompose.R
import com.apiguave.tinderclonecompose.data.Match
import com.apiguave.tinderclonecompose.ui.components.AnimatedGradientLogo
import com.apiguave.tinderclonecompose.ui.components.BlankAppBar
import com.apiguave.tinderclonecompose.ui.components.GradientButton
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun MatchListView(onNavigateToChatView: () -> Unit, onArrowBackPressed: () -> Unit, viewModel: MatchListViewModel = viewModel()) {
    Scaffold(
        topBar = { BlankAppBar(text = stringResource(id = R.string.matches), onArrowBackPressed = onArrowBackPressed) }
    ) { padding ->
        val uiState by viewModel.uiState.collectAsState()
        if(uiState.isLoading){
            Column{
                Spacer(Modifier.weight(1f))
                AnimatedGradientLogo(Modifier.fillMaxWidth())
                Spacer(Modifier.weight(1f))
            }
        } else if(uiState.errorMessage != null) {
            Column {
                Spacer(Modifier.weight(1f))
                Text(text = uiState.errorMessage!!, color = Color.Gray, fontSize = 16.sp)
                Spacer(Modifier.height(12.dp))
                val coroutineScope = rememberCoroutineScope()
                GradientButton(onClick = {
                    coroutineScope.launch {
                        delay(200)
                        viewModel.fetchMatches()
                    }
                }) {
                    Text(stringResource(id = R.string.retry))
                }
                Spacer(Modifier.weight(1f))
            }

        }else if(uiState.matchList.isEmpty()){
            Text(modifier = Modifier.fillMaxSize(), textAlign = TextAlign.Center,text = stringResource(id = R.string.no_matches), color = Color.Gray, fontSize = 20.sp )
        } else {
            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .padding(padding)) {
                items(uiState.matchList.size) {
                    MatchView(uiState.matchList[it], onNavigateToChatView)
                }
            }
        }
    }

}

@Composable
fun MatchView(match: Match, onClick: () -> Unit) {
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
            AsyncImage(
                model = match.picture,
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(randomColor())
            )

            Column(Modifier.fillMaxWidth()) {
                Row(Modifier.fillMaxWidth()) {
                    Text(match.name, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.width(10.dp))
                    Text(match.age.toString(), fontSize = 20.sp)
                }
                Text(match.lastMessage ?: stringResource(id = R.string.say_something_nice), fontWeight = FontWeight.Light)
            }
        }
    }
}

fun randomColor() = Color(Random.nextFloat(), Random.nextFloat(), Random.nextFloat())
