package com.apiguave.tinderclonecompose.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apiguave.tinderclonedomain.match.Match
import com.apiguave.tinderclonedomain.usecase.GetMessagesUseCase
import com.apiguave.tinderclonedomain.usecase.SendMessageUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel(
    private val getMessagesUseCase: GetMessagesUseCase,
    private val sendMessageUseCase: SendMessageUseCase): ViewModel() {
    private val _match = MutableStateFlow<Match?>(null)
    val match = _match.asStateFlow()

    fun getMessages(matchId: String) = getMessagesUseCase(matchId)

    fun sendMessage(text: String){
        val matchId = _match.value?.id ?: return
        viewModelScope.launch {
            sendMessageUseCase(matchId, text)
        }
    }

    fun setMatch(match: Match){
        _match.value = match
    }
}
