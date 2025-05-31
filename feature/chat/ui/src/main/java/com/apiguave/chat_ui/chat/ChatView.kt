package com.apiguave.chat_ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.apiguave.core_ui.components.ChatFooter
import com.apiguave.core_ui.model.ProfilePictureState
import com.apiguave.core_ui.theme.AntiFlashWhite
import com.apiguave.core_ui.theme.TinderCloneComposeTheme
import com.apiguave.core_ui.theme.UltramarineBlue
import com.apiguave.match_domain.model.Match
import com.apiguave.match_domain.model.MatchProfile
import com.apiguave.message_domain.model.Message
import com.apiguave.chat_ui.R
import com.apiguave.chat_ui.components.CenterAppBar
import com.apiguave.chat_ui.extensions.toShortString
import com.apiguave.chat_ui.model.MatchState
import java.time.LocalDate

@Composable
fun ChatView(
    state: MatchState,
    onArrowBackPressed: () -> Unit,
    sendMessage: (String) -> Unit,
    messages: List<Message>
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            ChatAppBar(match = state.match, pictureState = state.pictureState, onArrowBackPressed = onArrowBackPressed)
        },
        bottomBar = { ChatFooter(
            onSendClicked = sendMessage
        ) }
    ) { padding ->
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    textAlign = TextAlign.Center,
                    color = Color.Gray,
                    fontSize = 12.sp,
                    text = stringResource(id = R.string.you_matched_with_on, state.match.profile.name, state.match.creationDate.toShortString()).uppercase())
            }
            items(messages.size){ index ->
                MessageItem(pictureState = state.pictureState, message = messages[index])
            }
        }
    }

}

@Composable
fun ChatAppBar(match: Match, pictureState: ProfilePictureState, onArrowBackPressed: () -> Unit){
    CenterAppBar(onArrowBackPressed = onArrowBackPressed) {
        Column(Modifier.padding(vertical = 8.dp), horizontalAlignment = Alignment.CenterHorizontally){
            when(pictureState) {
                is ProfilePictureState.Loading -> CircularProgressIndicator()
                is ProfilePictureState.Remote -> {
                    AsyncImage(
                        model = pictureState.uri,
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .size(40.dp)
                            .clip(CircleShape)
                    )
                }
            }

            Text(text = match.profile.name, fontSize = 13.sp,fontWeight = FontWeight.Light, color = Color.Gray,textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun MessageItem(pictureState: ProfilePictureState, message: Message) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = if(message.isFromSender) Arrangement.End else Arrangement.Start) {

        if(message.isFromSender){
            Spacer(Modifier.fillMaxWidth(.25f))
        } else {
            when(pictureState) {
                is ProfilePictureState.Loading -> CircularProgressIndicator()
                is ProfilePictureState.Remote -> {
                    AsyncImage(
                        model = pictureState.uri,
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(40.dp)
                            .clip(CircleShape)
                    )
                }
            }
        }

        Text(
            modifier = Modifier
                .background(
                    if (message.isFromSender) UltramarineBlue else AntiFlashWhite,
                    RoundedCornerShape(4.dp)
                )
                .padding(6.dp)
                .weight(4f, false)
            ,
            text = message.text,
            color = if(message.isFromSender) Color.White else Color.Black,
        )

        if(!message.isFromSender){
            Spacer(
                Modifier
                    .height(4.dp)
                    .weight(1f, false)
                    .background(Color.Red))
        }

    }
}

@Composable
@Preview
fun ChatViewPreview() {
    TinderCloneComposeTheme {
        ChatView(
            state = MatchState(
                Match("", MatchProfile("", "Alice", 20, emptyList()), LocalDate.of(2024, 2, 24), "Hey, how are you doing?"),
                ProfilePictureState.Loading("picture1.png")
            ),
            onArrowBackPressed = { },
            sendMessage = { },
            messages = listOf(
                Message("Whats up!", true),
                Message("Hey, how are you doing?", false),
            )
        )
    }
}
