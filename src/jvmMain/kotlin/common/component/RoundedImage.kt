package common.component

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

@Composable
fun RoundedImage(
    imagePath:String,
    roundedSize:Dp=30.dp,
    modifier: Modifier = Modifier,
    size: DpSize = DpSize(420.dp, 530.dp)
){
    Card(
        modifier = modifier.size(size),
        shape = RoundedCornerShape(roundedSize),
        elevation = 5.dp
    ) {
        Image(
            painter = painterResource(imagePath),
            contentDescription = "authorization image",
            contentScale = ContentScale.FillBounds,
        )
    }
}

@Preview
@Composable
fun RoundedImagePreview(){
    Box(
        modifier = Modifier.padding(20.dp)
    ) {
        RoundedImage("authorization-image.png")
    }
}