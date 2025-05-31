package com.apiguave.chat_ui.chat

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apiguave.core_ui.model.ProfilePictureState
import com.apiguave.chat_ui.model.MatchState
import com.apiguave.message_domain.usecases.GetMessagesUseCase
import com.apiguave.message_domain.usecases.SendMessageUseCase
import com.apiguave.picture_domain.usecases.GetPictureUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatViewModel(
    private val getMessagesUseCase: GetMessagesUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val getPictureUseCase: GetPictureUseCase
): ViewModel() {
    private val _viewState = MutableStateFlow<MatchState?>(null)
    val viewState = _viewState.asStateFlow()

    fun getMessages(matchId: String) = getMessagesUseCase(matchId)

    fun sendMessage(text: String){
        val matchId = _viewState.value?.match?.id ?: return
        viewModelScope.launch {
            sendMessageUseCase(matchId, text)
        }
    }

    fun setMatchState(matchState: MatchState){
        _viewState.value = matchState
        if(matchState.pictureState is ProfilePictureState.Loading) {
            viewModelScope.launch {
                getPictureUseCase(matchState.match.profile.id, matchState.match.profile.pictureNames.first()).onSuccess { pictureUrl ->
                    _viewState.update { it?.copy(pictureState = ProfilePictureState.Remote(Uri.parse(pictureUrl))) }
                }
            }
        }
    }
}

