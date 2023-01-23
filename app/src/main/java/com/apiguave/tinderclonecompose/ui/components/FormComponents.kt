package com.apiguave.tinderclonecompose.ui.components

import androidx.annotation.ArrayRes
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.apiguave.tinderclonecompose.R
import com.apiguave.tinderclonecompose.extensions.withLinearGradient
import com.apiguave.tinderclonecompose.ui.theme.Nero
import com.apiguave.tinderclonecompose.ui.theme.Orange
import com.apiguave.tinderclonecompose.ui.theme.Pink
import com.apiguave.tinderclonecompose.ui.theme.SystemGray4
import kotlinx.coroutines.launch

@Composable
fun LoadingView() {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White.copy(alpha = .6f))
            .clickable(enabled = false, onClick = {}),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val infiniteTransition = rememberInfiniteTransition()
        val animatedLogoScale by infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 1.25f,
            animationSpec = infiniteRepeatable(
                animation = tween(1000),
                repeatMode = RepeatMode.Reverse
            )
        )

        Image(
            painter = painterResource(id = R.drawable.tinder_logo),
            contentDescription = null,
            modifier = Modifier
                .width(350.dp)
                .height(IntrinsicSize.Max)
                .graphicsLayer(scaleX = animatedLogoScale, scaleY = animatedLogoScale)
                .withLinearGradient(Pink, Orange)
        )
    }
}

@Composable
fun GradientGoogleButton(enabled: Boolean, onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        contentPadding = PaddingValues(),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(if (enabled) 1f else .12f)
                .background(Brush.horizontalGradient(listOf(Pink, Orange)))
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.google_logo_48),
                contentDescription = null
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(
                stringResource(id = R.string.sign_up_with_google),
                color = Color.White
            )
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        title.uppercase(),
        modifier = Modifier.padding(all = 8.dp),
        color = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun FormDivider() {
    Spacer(
        Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(if (isSystemInDarkTheme()) Color.DarkGray else SystemGray4)
    )
}

@Composable
fun TextRow(title: String, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (isSystemInDarkTheme()) Nero else Color.White)
            .padding(horizontal = 12.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, fontWeight = FontWeight.Bold, color = MaterialTheme.colors.onSurface)
        Spacer(modifier = Modifier.weight(1.0f))
        Text(
            text = text,
            color = MaterialTheme.colors.onSurface
        )
    }
}

@Composable
fun FormTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    placeholder: String,
    onValueChange: (TextFieldValue) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        value = value,
        placeholder = { Text(placeholder) },
        onValueChange = onValueChange
    )
}

@Composable
fun OptionButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    TextButton(
        modifier = modifier,
        onClick = onClick,
        contentPadding = PaddingValues(
            start = 16.dp,
            top = 16.dp,
            end = 16.dp,
            bottom = 16.dp
        )
    ) {
        Text(text, color = MaterialTheme.colors.onSurface.copy(alpha = .2f))
    }
}

@Composable
fun HorizontalPicker(@ArrayRes id: Int, selectedIndex: Int, onOptionClick: (Int) -> Unit) {
    val options = stringArrayResource(id = id)
    val coroutineScope = rememberCoroutineScope()
    val offsetX = remember { Animatable(0f) }

    val density = LocalDensity.current
    var itemWidth by remember { mutableStateOf(0f) }
    var itemHeight by remember { mutableStateOf(0f) }

    Box {
        Row(
            modifier =
            Modifier
                .fillMaxWidth()
                .border(
                    BorderStroke(
                        1.dp,
                        if (isSystemInDarkTheme()) Color.DarkGray else SystemGray4
                    )
                )
                .onGloballyPositioned {
                    itemWidth = density.run { (it.size.width / options.size).toDp().value }
                    itemHeight = density.run { it.size.height.toDp() }.value
                }
        ) {
            options.forEachIndexed { index, s ->
                OptionButton(
                    modifier = Modifier.weight(1.0f),
                    text = s,
                    onClick = {
                        coroutineScope.launch {
                            offsetX.animateTo(targetValue = itemWidth * index)
                        }
                        onOptionClick(index)
                    })
            }
        }

        Surface(
            elevation = 2.dp, modifier = Modifier
                .offset(x = offsetX.value.dp)
                .size(width = itemWidth.dp, height = itemHeight.dp)
                .padding(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(Brush.horizontalGradient(listOf(Pink, Orange)))
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    text = options[selectedIndex],
                )
            }

        }
    }
}
