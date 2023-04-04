package com.apiguave.tinderclonecompose.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apiguave.tinderclonecompose.data.repository.model.Match
import com.apiguave.tinderclonecompose.data.repository.MessageRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel: ViewModel() {
    private val _match = MutableStateFlow<Match?>(null)
    val match = _match.asStateFlow()

    fun getMessages(matchId: String) = MessageRepository.getMessages(matchId)

    fun sendMessage(text: String){
        val matchId = _match.value?.id ?: return
        viewModelScope.launch {
            try {
                MessageRepository.sendMessage(matchId, text)
            }catch (e: Exception){
                //Show the message as unsent?
            }
        }
    }

    fun setMatch(match: Match){
        _match.value = match
    }
}
