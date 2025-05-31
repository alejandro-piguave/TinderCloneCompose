package com.apiguave.chat_ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.apiguave.core_ui.modifiers.withLinearGradient
import com.apiguave.chat_ui.R
import com.apiguave.core_ui.theme.Orange
import com.apiguave.core_ui.theme.Pink

@Composable
fun CenterAppBar(
    onArrowBackPressed: () -> Unit,
    content: @Composable () -> Unit){
    Surface(elevation = AppBarDefaults.TopAppBarElevation){
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
            Box(Modifier.weight(1f)){
                IconButton(modifier = Modifier.height(IntrinsicSize.Max),onClick = onArrowBackPressed) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_chevron_left_24),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .withLinearGradient(Pink, Orange)
                            .align(Alignment.Center)
                    )
                }
            }

            content()
            Spacer(Modifier.weight(1f))
        }
    }
}