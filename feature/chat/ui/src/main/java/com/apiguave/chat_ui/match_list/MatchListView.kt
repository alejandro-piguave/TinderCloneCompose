package com.apiguave.chat_ui.match_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.apiguave.core_ui.components.AnimatedGradientLogo
import com.apiguave.core_ui.components.GradientButton
import com.apiguave.core_ui.model.ProfilePictureState
import com.apiguave.core_ui.theme.LightLightGray
import com.apiguave.core_ui.theme.Orange
import com.apiguave.core_ui.theme.Pink
import com.apiguave.core_ui.theme.TinderCloneComposeTheme
import com.apiguave.chat_ui.components.CenterAppBar
import com.apiguave.chat_ui.model.MatchState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.apiguave.chat_ui.R

@OptIn(ExperimentalTextApi::class)
@Composable
fun MatchListView(
    uiState: MatchListViewState,
    onArrowBackPressed: () -> Unit,
    fetchMatches: () -> Unit,
    navigateToMatch: (MatchState) -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAppBar(
                onArrowBackPressed = onArrowBackPressed
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 12.dp),
                    text = stringResource(id = R.string.messages),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        brush = Brush.linearGradient(
                            colors = listOf(Pink, Orange)
                        )
                    )
                )
            }
        }
    ) { padding ->
        when(uiState) {
            is MatchListViewState.Error -> {
                Column(modifier = Modifier.padding(horizontal = 8.dp),horizontalAlignment = Alignment.CenterHorizontally) {
                    val coroutineScope = rememberCoroutineScope()
                    Spacer(Modifier.weight(1f))
                    Text(text = uiState.message, color = Color.Gray, fontSize = 16.sp, textAlign = TextAlign.Center)
                    Spacer(Modifier.height(12.dp))
                    GradientButton(onClick = {
                        coroutineScope.launch {
                            delay(200)
                            fetchMatches()
                        }
                    }) {
                        Text(stringResource(id = R.string.retry))
                    }
                    Spacer(Modifier.weight(1f))
                }
            }
            MatchListViewState.Loading -> {
                Column {
                    Spacer(Modifier.weight(1f))
                    AnimatedGradientLogo(Modifier.fillMaxWidth())
                    Spacer(Modifier.weight(1f))
                }
            }
            is MatchListViewState.Success -> {
                if (uiState.matches.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            textAlign = TextAlign.Center,
                            text = stringResource(id = R.string.no_matches),
                            color = Color.Gray,
                            fontSize = 20.sp
                        )
                    }
                } else {
                    LazyColumn(
                        Modifier
                            .fillMaxSize()
                            .padding(padding)
                    ) {
                        items(uiState.matches.size) {
                            MatchItem(uiState.matches[it]) {
                                navigateToMatch(uiState.matches[it])
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MatchItem(matchState: MatchState, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(Modifier.padding(horizontal = 8.dp).size(60.dp), contentAlignment = Alignment.Center) {
            when(matchState.pictureState) {
                is ProfilePictureState.Loading -> CircularProgressIndicator(Modifier.fillMaxSize(.5f))
                is ProfilePictureState.Remote -> {
                    AsyncImage(
                        model = matchState.pictureState.uri,
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                        modifier = Modifier.clip(CircleShape)
                    )
                }
            }
        }

        Column(
            Modifier
                .fillMaxSize()
                .padding(start = 8.dp)) {
            Spacer(Modifier.height(20.dp))
            Row(Modifier.fillMaxWidth()) {
                Text(matchState.match.profile.name, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.width(10.dp))
                Text(matchState.match.profile.age.toString(), fontSize = 20.sp)
            }
            Text(
                text = matchState.match.lastMessage ?: stringResource(id = R.string.say_something_nice),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Light
            )
            Spacer(Modifier.height(20.dp))
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(LightLightGray))
        }
    }
}

@Preview
@Composable
fun MatchListViewPreview() {
    TinderCloneComposeTheme {
        MatchListView(
            uiState = MatchListViewState.Loading,
            onArrowBackPressed = { },
            fetchMatches = { },
            navigateToMatch = {}
        )
    }
}