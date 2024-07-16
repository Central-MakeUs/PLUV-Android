package com.cmc15th.pluv.core.designsystem.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.cmc15th.pluv.R

val pretendardFamily = FontFamily(
    Font(R.font.pretendard_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.pretendard_semi_bold, FontWeight.SemiBold, FontStyle.Normal),
    Font(R.font.pretendard_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.pretendard_regular, FontWeight.Normal, FontStyle.Normal),
)

val Title1 = TextStyle(
    fontFamily = pretendardFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 24.sp
)

val Title2 = TextStyle(
    fontFamily = pretendardFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 22.sp
)

val Title3 = TextStyle(
    fontFamily = pretendardFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 20.sp
)

val Title4 = TextStyle(
    fontFamily = pretendardFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 18.sp
)

val Content1 = TextStyle(
    fontFamily = pretendardFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp
)

val Content2 = TextStyle(
    fontFamily = pretendardFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp
)

val Content3 = TextStyle(
    fontFamily = pretendardFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp
)

val MigrateAppName = TextStyle(
    fontFamily = pretendardFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    color = Color.Blue
)

val SelectedAppName = TextStyle(
    fontFamily = pretendardFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    color = Color(0xFF5C5C5C)
)

