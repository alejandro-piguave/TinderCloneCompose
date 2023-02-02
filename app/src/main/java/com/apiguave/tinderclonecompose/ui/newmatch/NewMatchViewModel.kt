package com.apiguave.tinderclonecompose.ui.newmatch

import androidx.lifecycle.ViewModel
import com.apiguave.tinderclonecompose.data.NewMatch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NewMatchViewModel: ViewModel() {
    private val _match: MutableStateFlow<NewMatch?> = MutableStateFlow(null)
    val match = _match.asStateFlow()

    fun setMatch(match: NewMatch){
        _match.value = match
    }
}