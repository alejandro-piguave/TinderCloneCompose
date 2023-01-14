package com.apiguave.tinderclonecompose.ui.shared

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable

@Composable
fun BlankAppBar(text: String, onArrowBackPressed: () -> Unit){
    TopAppBar(
        title = {
            Text(text = text)
        },
        backgroundColor = MaterialTheme.colors.surface,
        navigationIcon = {
            IconButton(onClick = onArrowBackPressed) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null
                )
            }
        }
    )
}