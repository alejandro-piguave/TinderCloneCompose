package com.apiguave.tinderclonecompose.ui.login

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.apiguave.tinderclonecompose.R
import com.apiguave.tinderclonecompose.extensions.conditional
import com.apiguave.tinderclonecompose.ui.theme.Orange
import com.apiguave.tinderclonecompose.ui.theme.Pink
import com.google.android.gms.auth.api.signin.GoogleSignInClient

@Composable
fun LoginView(signInClient: GoogleSignInClient, onNavigateToSignUp: () -> Unit, onNavigateToHome: () -> Unit, loginViewModel: LoginViewModel = viewModel()) {
    val startForResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
            loginViewModel.signIn(it)
        }
    )

    val uiState by loginViewModel.uiState.collectAsState()

    LaunchedEffect(key1 = uiState, block = {
        if(uiState.isUserSignedIn){
            onNavigateToHome()
        }
    })

    val infiniteTransition = rememberInfiniteTransition()
    val animatedLogoScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.25f,
        animationSpec = infiniteRepeatable(animation = tween(1000), repeatMode = RepeatMode.Reverse)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Orange,
                        Pink
                    )
                )
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Spacer(modifier = Modifier.weight(1.0f))
        Image(
            painter = painterResource(id = R.drawable.tinder_logo),
            contentDescription = null,
            modifier = Modifier
                .padding(all = 28.dp)
                .width(400.dp)
                .height(IntrinsicSize.Max)
                .conditional(uiState.isLoading) {
                    graphicsLayer(scaleX = animatedLogoScale, scaleY = animatedLogoScale)
                }
        )
        Button(
            modifier = Modifier.alpha(if (uiState.isLoading) 0f else 1f),
            onClick = {
                startForResult.launch(signInClient.signInIntent)
            },
            contentPadding = PaddingValues(
                start = 20.dp,
                top = 12.dp,
                end = 20.dp,
                bottom = 12.dp
            ),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                Image(
                    painter = painterResource(id = R.drawable.google_logo_48),
                    contentDescription = null
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(stringResource(id = R.string.sign_in_with_google), color = Color.Gray)
            }

        }
        Spacer(modifier = Modifier.weight(1.0f))
        TextButton(
            modifier = Modifier.alpha(if (uiState.isLoading) 0f else 1f),
            onClick = onNavigateToSignUp
        ) {
            Text(stringResource(id = R.string.create_account), color = Color.White)
        }
    }
}