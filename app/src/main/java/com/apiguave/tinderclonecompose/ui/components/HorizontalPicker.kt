package com.apiguave.tinderclonecompose.ui.components

import androidx.annotation.ArrayRes
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.apiguave.tinderclonecompose.ui.theme.Orange
import com.apiguave.tinderclonecompose.ui.theme.Pink
import com.apiguave.tinderclonecompose.ui.theme.SystemGray4
import kotlinx.coroutines.launch

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
                    val firstRender = itemWidth == 0f
                    itemWidth = density.run { (it.size.width / options.size).toDp().value }
                    itemHeight = density.run { it.size.height.toDp() }.value

                    if (firstRender && selectedIndex > 0){
                        coroutineScope.launch {
                            offsetX.snapTo(itemWidth * selectedIndex)
                        }
                    }
                }
        ) {
            options.forEachIndexed { index, s ->
                OptionButton(
                    modifier = Modifier.weight(1.0f),
                    text = s,
                    onClick = {
                        coroutineScope.launch {
                            if(selectedIndex < 0){
                                offsetX.snapTo(itemWidth * index)
                            } else {
                                offsetX.animateTo(itemWidth * index)
                            }
                        }
                        onOptionClick(index)
                    })
            }
        }

        if(selectedIndex >= 0){
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
}