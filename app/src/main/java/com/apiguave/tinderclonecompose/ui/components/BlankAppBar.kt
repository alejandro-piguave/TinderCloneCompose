package com.apiguave.tinderclonecompose.ui.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.apiguave.tinderclonecompose.ui.extension.withLinearGradient
import com.apiguave.tinderclonecompose.ui.theme.Orange
import com.apiguave.tinderclonecompose.ui.theme.Pink

@OptIn(ExperimentalTextApi::class)
@Composable
fun BlankAppBar(text: String, onArrowBackPressed: () -> Unit){
    TopAppBar(
        title = {
            Text(
                text = text,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                style = TextStyle(
                    brush = Brush.linearGradient(
                        colors = listOf(Pink, Orange)
                    )
                )
            )
        },
        backgroundColor = MaterialTheme.colors.surface,
        navigationIcon = {
            IconButton(onClick = onArrowBackPressed) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier.withLinearGradient(Pink, Orange)
                )
            }
        }
    )
}