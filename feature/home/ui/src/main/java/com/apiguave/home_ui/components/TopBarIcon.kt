package com.apiguave.home_ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import com.apiguave.core_ui.modifiers.withLinearGradient
import com.apiguave.core_ui.theme.Orange
import com.apiguave.core_ui.theme.Pink

@Composable
fun TopBarIcon(painter: Painter, modifier: Modifier, onClick: (() -> Unit )? = null){
    Icon(
        painter = painter,
        modifier = modifier
            .withLinearGradient(Pink, Orange)
            then(if(onClick != null) Modifier.clickable(onClick = onClick!!) else Modifier)
            ,
        contentDescription = null)

}

@Composable
fun TopBarIcon(@DrawableRes resId: Int, modifier: Modifier = Modifier, onClick: (() -> Unit)? = null){
    TopBarIcon(painter = painterResource(id = resId), modifier = modifier, onClick = onClick)
}
@Composable
fun TopBarIcon(imageVector: ImageVector, modifier: Modifier = Modifier, onClick: (() -> Unit)? = null){
    TopBarIcon(painter = rememberVectorPainter(image = imageVector), modifier = modifier, onClick = onClick)
}

