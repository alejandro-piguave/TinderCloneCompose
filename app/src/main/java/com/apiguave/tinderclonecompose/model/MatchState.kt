package com.apiguave.tinderclonecompose.model

import androidx.compose.runtime.Immutable
import com.apiguave.core_ui.model.ProfilePictureState
import com.apiguave.tinderclonedomain.match.Match

@Immutable
data class MatchState(val match: Match, val pictureState: ProfilePictureState)