package com.apiguave.tinderclonecompose.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.apiguave.tinderclonecompose.R
import com.apiguave.tinderclonecompose.data.Match
import com.apiguave.tinderclonecompose.data.Message
import com.apiguave.tinderclonecompose.extensions.withLinearGradient
import com.apiguave.tinderclonecompose.ui.theme.*

@Composable
fun ChatView(onArrowBackPressed: () -> Unit, viewModel: ChatViewModel = viewModel()) {
    val match by viewModel.match.collectAsState()
    match?.let { match: Match ->
        val messages by viewModel.getMessages(match.id).collectAsState(listOf())
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                ChatAppBar(match = match, onArrowBackPressed = onArrowBackPressed)
            },
            bottomBar = { ChatFooter(
                onSendClicked = {text ->
                    viewModel.sendMessage(text)
                }
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
                        text = stringResource(id = R.string.you_matched_with_on, match.userName, match.formattedDate).uppercase())
                }
                items(messages.size){ index ->
                    MessageItem(match = match,message = messages[index])
                }
            }
        }

    } ?: run{
        Text(modifier = Modifier.fillMaxSize(), textAlign = TextAlign.Center, text = stringResource(id = R.string.no_match_value_passed))
    }

}

@Composable
fun ChatAppBar(match: Match, onArrowBackPressed: () -> Unit){
    Surface(elevation = AppBarDefaults.TopAppBarElevation){
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
            Box(Modifier.weight(1f)){
                IconButton(modifier = Modifier.height(IntrinsicSize.Max),onClick = onArrowBackPressed) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_chevron_left_24),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .withLinearGradient(Pink, Orange)
                            .align(Alignment.TopCenter)
                    )
                }
            }

            Column(Modifier.padding(vertical = 8.dp), horizontalAlignment = Alignment.CenterHorizontally){
                AsyncImage(
                    model = match.userPicture,
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .size(40.dp)
                        .clip(CircleShape)
                )
                Text(text = match.userName, fontSize = 13.sp,fontWeight = FontWeight.Light, color = Color.Gray,textAlign = TextAlign.Center)
            }
            Spacer(Modifier.weight(1f))
        }
    }
}

@Composable
fun MessageItem(match: Match, message: Message) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = if(message.isFromSender) Arrangement.End else Arrangement.Start) {

        if(message.isFromSender){
            Spacer(Modifier.fillMaxWidth(.25f))
        } else {
            AsyncImage(
                model = match.userPicture,
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(40.dp)
                    .clip(CircleShape)
            )
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
fun ChatFooter(onSendClicked: (String) -> Unit) {
    var inputValue by remember { mutableStateOf("") } // 2
    Box(Modifier.padding(8.dp)){
        Row(
            modifier = Modifier.border(1.dp, LightLightGray, CircleShape),
            verticalAlignment = Alignment.CenterVertically) {
            BasicTextField(
                // 4
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 20.dp),
                value = inputValue,
                onValueChange = { inputValue = it },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions {
                    onSendClicked(inputValue)
                    inputValue = ""  },
            )
            TextButton(
                // 5
                onClick = {
                    onSendClicked(inputValue)
                    inputValue = ""
                },
                enabled = inputValue.isNotBlank(),
                colors = ButtonDefaults.textButtonColors(contentColor = UltramarineBlue)
            ) {
                Text(stringResource(id = R.string.send))
            }
        }

    }
}