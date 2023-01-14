package com.apiguave.tinderclonecompose.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apiguave.tinderclonecompose.R
import com.apiguave.tinderclonecompose.ui.shared.RoundGradientButton
import com.apiguave.tinderclonecompose.ui.shared.rememberSwipeableCardState
import com.apiguave.tinderclonecompose.ui.shared.swipableCard
import com.apiguave.tinderclonecompose.ui.theme.Green1
import com.apiguave.tinderclonecompose.ui.theme.Green2
import com.apiguave.tinderclonecompose.ui.theme.Orange
import com.apiguave.tinderclonecompose.ui.theme.Pink
import kotlin.random.Random

@Composable
fun HomeView(onNavigateToEditProfile: () -> Unit, onNavigateToMatchList: () -> Unit){
    val colorArray = remember{ (0 until 10).map { (0 until 6).map { randomColor() } }.toMutableStateList() }

    Column(Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TopBarIcon(imageVector = Icons.Filled.AccountCircle, onClick = onNavigateToEditProfile)
            Spacer(Modifier.weight(1f))
            TopBarIcon(resId = R.drawable.tinder_logo, modifier = Modifier.size(32.dp))
            Spacer(Modifier.weight(1f))
            TopBarIcon(resId = R.drawable.ic_baseline_message_24, onClick = onNavigateToMatchList)
        }

        Spacer(Modifier.weight(1f))
        Box(Modifier.padding(horizontal = 20.dp)){
            colorArray.forEach { colors  ->
                val state = rememberSwipeableCardState()
                ProfileCardView(colors, Modifier.swipableCard(
                        state = state,
                        onSwiped = { direction ->
                            println("The card was swiped to $direction")
                            colorArray.removeLast()
                        }
                    )
                )
            }
        }

        Spacer(Modifier.weight(1f))

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Spacer(Modifier.weight(1f))
            RoundGradientButton(Icons.Filled.Close, Pink, Orange) { }
            Spacer(Modifier.weight(.5f))
            RoundGradientButton(R.drawable.ic_baseline_favorite_border_44, Green1, Green2) { }
            Spacer(Modifier.weight(1f))
        }
        Spacer(Modifier.height(24.dp))
    }
}

private fun randomColor() = Color(Random.nextFloat(), Random.nextFloat(), Random.nextFloat())

@Composable
fun TopBarIcon(painter: Painter, modifier: Modifier, onClick: (() -> Unit )? = null){
    Icon(
        painter = painter,
        modifier = modifier
            .withLinearGradient(Pink, Orange)
            .conditional(onClick != null) { clickable(onClick = onClick!!) },
        contentDescription = null)

}

@Composable
fun ProfileCardView(colors: List<Color>, modifier: Modifier = Modifier){
    var currentIndex by remember{ mutableStateOf(0) }

    Card(modifier = modifier
        .fillMaxWidth()
        .aspectRatio(.7f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .background(colors[currentIndex])) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(6.dp)) {
                repeat(colors.size){ index ->
                    Box(
                        Modifier
                            .weight(1f)
                            .height(3.dp)
                            .padding(horizontal = 4.dp)
                            .alpha(if (index == currentIndex) 1f else .5f)
                            .background(if (index == currentIndex) Color.White else Color.LightGray))
                }
            }
            Row(
                Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth().padding(12.dp)) {
                Text(text = "John Doe", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 30.sp)
                Spacer(Modifier.width(8.dp))
                Text(text = "20", color = Color.White, fontSize = 30.sp)
            }
            Row(Modifier.fillMaxSize()) {
                Box(modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .clickable { if (currentIndex > 0) currentIndex-- })
                Box(modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .clickable { if (currentIndex < colors.size - 1) currentIndex++ }
                )
            }

        }

    }
}

@Composable
fun TopBarIcon(@DrawableRes resId: Int, modifier: Modifier = Modifier, onClick: (() -> Unit)? = null){
    TopBarIcon(painter = painterResource(id = resId), modifier = modifier, onClick = onClick)
}
@Composable
fun TopBarIcon(imageVector: ImageVector, modifier: Modifier = Modifier, onClick: (() -> Unit)? = null){
    TopBarIcon(painter = rememberVectorPainter(image = imageVector), modifier = modifier, onClick = onClick)
}

fun Modifier.conditional(condition : Boolean, modifier : Modifier.() -> Modifier) : Modifier {
    return if (condition) {
        then(modifier(Modifier))
    } else {
        this
    }
}

fun Modifier.withLinearGradient(color1: Color, color2: Color): Modifier{
    return this
        .graphicsLayer(alpha = 0.99f)
        .drawWithCache {
            onDrawWithContent {
                drawContent()
                drawRect(
                    Brush.linearGradient(
                        colors = listOf(
                            color1,
                            color2,
                        )
                    ), blendMode = BlendMode.SrcAtop
                )
            }
        }
}