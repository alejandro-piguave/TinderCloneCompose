package com.apiguave.chat_ui.model

import androidx.compose.runtime.Immutable
import com.apiguave.core_ui.model.ProfilePictureState
import com.apiguave.match_domain.model.Match

@Immutable
data class MatchState(val match: Match, val pictureState: ProfilePictureState)