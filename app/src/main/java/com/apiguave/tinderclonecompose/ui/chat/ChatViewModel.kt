package com.apiguave.tinderclonecompose.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apiguave.tinderclonecompose.data.Match
import com.apiguave.tinderclonecompose.data.repository.FirebaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(ChatUiState(null))
    val uiState = _uiState.asStateFlow()


    fun sendMessage(text: String){
        val matchId = _uiState.value.match!!.id
        viewModelScope.launch {
            try {
                FirebaseRepository.sendMessage(matchId, text)
            }catch (e: Exception){
                //Show the message as unsent?
            }
        }
    }

    fun setMatch(match: Match){
        _uiState.update { it.copy(match = match) }
    }
}

data class ChatUiState(val match: Match?)