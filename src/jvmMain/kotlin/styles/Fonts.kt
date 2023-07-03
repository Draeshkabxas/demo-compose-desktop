package styles

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.sp


val CairoFontFamily = FontFamily(
    Font("fonts/cairo/Cairo-Black.ttf", FontWeight.Black),
    Font("fonts/cairo/Cairo-Bold.ttf", FontWeight.Bold),
    Font("fonts/cairo/Cairo-ExtraBold.ttf", FontWeight.ExtraBold),
    Font("fonts/cairo/Cairo-ExtraLight.ttf", FontWeight.ExtraLight),
    Font("fonts/cairo/Cairo-Light.ttf", FontWeight.Light),
    Font("fonts/cairo/Cairo-Medium.ttf", FontWeight.Medium),
    Font("fonts/cairo/Cairo-Regular.ttf", FontWeight.Normal),
    Font("fonts/cairo/Cairo-SemiBold.ttf", FontWeight.SemiBold),
)

val CairoTypography = Typography(
    h1 = TextStyle(
        fontFamily = CairoFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp
    ),
    h2 = TextStyle(
        fontFamily = CairoFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp
    ),
    h4 = TextStyle(
        fontFamily = CairoFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp
    ),
    h3 = TextStyle(
        fontFamily = CairoFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ),
    body1 = TextStyle(
        fontFamily = CairoFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    body2 = TextStyle(
        fontFamily = CairoFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    h5 = TextStyle(
        fontFamily = CairoFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp
    )

    // Add more text styles as needed
)
