package com.apiguave.tinderclonecompose.ui.newmatch

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun NewMatchView(viewModel: NewMatchViewModel = viewModel()){
    Text(modifier = Modifier
        .fillMaxSize()
        .background(Color.Red), textAlign = TextAlign.Center,text = "It's a match!")
}