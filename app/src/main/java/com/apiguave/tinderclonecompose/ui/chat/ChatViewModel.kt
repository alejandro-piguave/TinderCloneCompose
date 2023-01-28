package com.apiguave.tinderclonecompose.ui.chat

import androidx.lifecycle.ViewModel
import com.apiguave.tinderclonecompose.data.Match
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ChatViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(ChatUiState(null))
    val uiState = _uiState.asStateFlow()

    fun setMatch(match: Match){
        _uiState.update { it.copy(match = match) }
    }
}

data class ChatUiState(val match: Match?)