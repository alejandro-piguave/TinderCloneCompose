package com.apiguave.onboarding_ui.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apiguave.onboarding_ui.components.AnimatedLogo
import com.apiguave.core_ui.theme.Orange
import com.apiguave.core_ui.theme.Pink
import com.apiguave.core_ui.theme.TinderCloneComposeTheme
import com.apiguave.onboarding_ui.R

@Composable
fun LoginView(uiState: LoginViewState,
              onSignInClicked: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Pink,
                        Orange
                    )
                )
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Spacer(modifier = Modifier.weight(1.0f))
        AnimatedLogo(modifier = Modifier
            .fillMaxWidth(.4f)
            .padding(bottom = 8.dp), isAnimating = uiState is LoginViewState.Loading
        )
        Column(modifier = Modifier.weight(1.0f)){
            Spacer(modifier = Modifier.weight(1f))


            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .alpha(if (uiState is LoginViewState.Loading) 0f else 1f),
                onClick = onSignInClicked,
                contentPadding = PaddingValues(
                    start = 20.dp,
                    top = 12.dp,
                    end = 20.dp,
                    bottom = 12.dp
                ),
                border = BorderStroke(2.dp, Color.White),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Transparent)
            ) {
                Box(Modifier.fillMaxWidth()){
                   Image(
                       modifier = Modifier.align(Alignment.CenterStart),
                       painter = painterResource(id = R.drawable.google_icon),
                       contentDescription = null
                   )
                   Text(modifier = Modifier.align(Alignment.Center), textAlign = TextAlign.Center,text = stringResource(id = R.string.sign_in_with_google).uppercase(), color = Color.White)
                }
            }
            Spacer(modifier = Modifier.height(44.dp))
        }
    }
}

@Preview
@Composable
fun LoginViewPreview() {
    TinderCloneComposeTheme {
        LoginView(
            uiState = LoginViewState.Ready,
            onSignInClicked = {}
        )
    }
}