package com.apiguave.feature_chat.model

import androidx.compose.runtime.Immutable
import com.apiguave.core_ui.model.ProfilePictureState
import com.apiguave.domain_match.model.Match

@Immutable
data class MatchState(val match: Match, val pictureState: ProfilePictureState)