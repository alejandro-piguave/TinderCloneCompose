package com.apiguave.onboarding_ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apiguave.core_ui.theme.Orange
import com.apiguave.core_ui.theme.Pink
import com.apiguave.core_ui.theme.TinderCloneComposeTheme
import com.apiguave.onboarding_ui.components.AnimatedLogo
import com.apiguave.onboarding_ui.R

@Composable
fun SplashView(state: SplashViewState, onTryAgainClick: () -> Unit = {}) {

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
        Spacer(modifier = Modifier.weight(1f))
        when(state) {
            SplashViewState.Loading -> {
                AnimatedLogo(modifier = Modifier
                    .fillMaxWidth(.4f)
                    .padding(bottom = 8.dp), isAnimating = true
                )
            }

            SplashViewState.Error -> {
                Column(Modifier.padding(horizontal = 12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(stringResource(id = R.string.splash_screen_error_message), textAlign = TextAlign.Center, color = Color.White)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = onTryAgainClick) {
                        Text(stringResource(id = R.string.try_again))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview
@Composable
fun LoginViewPreview() {
    TinderCloneComposeTheme {
        SplashView(SplashViewState.Error)
    }
}