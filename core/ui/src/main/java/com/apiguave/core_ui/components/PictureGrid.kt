package com.apiguave.core_ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.apiguave.core_ui.model.PictureState
import com.apiguave.core_ui.theme.*

//Picture Grid components

const val ColumnCount = 3
const val GridItemCount = 9
const val RowCount = 1 + (GridItemCount -1) / ColumnCount

@Composable
fun PictureGridRow(rowIndex: Int, pictures: List<PictureState>, onAddPicture: () -> Unit, onAddedPictureClicked: (Int) -> Unit){
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)){
        repeat(ColumnCount) { columnIndex ->
            val cellIndex = rowIndex * ColumnCount + columnIndex

            if(cellIndex < pictures.size){
                SelectedPictureItem(
                    imageState = pictures[cellIndex],
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(.6f),
                    onClick = { onAddedPictureClicked(cellIndex) }
                )
            } else {
                EmptyPictureItem(modifier = Modifier
                    .weight(1f)
                    .aspectRatio(.6f), onClick = onAddPicture )
            }
        }
    }
}
@Composable
fun EmptyPictureItem(modifier: Modifier = Modifier, onClick: () -> Unit){
    Box(modifier = modifier.clickable(onClick = onClick)) {
        Card(
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.6f)
                .padding(all = 8.dp),
            backgroundColor = if(isSystemInDarkTheme()) NightRider else LightLightGray, content = {
                val borderColor = if(isSystemInDarkTheme()) Color.DarkGray else SystemGray4
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawRoundRect(color = borderColor, style = Stroke(width = 4.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(5.dp.toPx(), 5.dp.toPx()), 0f))
                    )
                }
            }
        )

        Icon(
            Icons.Filled.Add,
            tint = Color.White,
            modifier = Modifier
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Pink,
                            Orange,
                        )
                    ), CircleShape
                )
                .padding(2.dp)
                .align(Alignment.BottomEnd),
            contentDescription = null
        )
    }

}

@Composable
fun SelectedPictureItem(imageState: PictureState,
                        modifier: Modifier = Modifier,
                        onClick: () -> Unit){
    Box(modifier = modifier.clickable(onClick = onClick)) {
        Card(
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.6f)
                .padding(all = 8.dp),
            content = {
                when(imageState) {
                    is PictureState.Loading -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }
                    is PictureState.Local -> {
                        AsyncImage(
                            model = imageState.uri,
                            modifier = Modifier.fillMaxSize(),
                            onState = {

                            },
                            contentScale = ContentScale.Crop,
                            contentDescription = null
                        )
                    }
                    is PictureState.Remote -> {
                        AsyncImage(
                            model = imageState.uri,
                            modifier = Modifier.fillMaxSize(),
                            onState = {

                            },
                            contentScale = ContentScale.Crop,
                            contentDescription = null
                        )
                    }
                }

            }
        )

        Icon(
            Icons.Filled.Close,
            tint = Orange,
            modifier = Modifier
                .background(color = Color.White, shape = CircleShape)
                .padding(2.dp)
                .align(Alignment.BottomEnd),
            contentDescription = null
        )
    }

}