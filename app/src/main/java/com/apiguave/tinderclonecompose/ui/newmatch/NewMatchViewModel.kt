package com.apiguave.tinderclonecompose.ui.newmatch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apiguave.tinderclonecompose.data.repository.model.NewMatch
import com.apiguave.tinderclonecompose.data.repository.MessageRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewMatchViewModel(private val messageRepository: MessageRepository): ViewModel() {
    private val _match: MutableStateFlow<NewMatch?> = MutableStateFlow(null)
    val match = _match.asStateFlow()

    fun sendMessage(text: String){
        val matchId = _match.value?.id ?: return
        viewModelScope.launch {
            try {
                messageRepository.sendMessage(matchId, text)
            }catch (e: Exception){
                //Show the message as unsent?
            }
        }
    }

    fun setMatch(match: NewMatch){
        _match.value = match
    }
}