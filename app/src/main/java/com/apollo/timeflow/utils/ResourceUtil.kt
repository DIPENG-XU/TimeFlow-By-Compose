package com.apollo.timeflow.utils

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.apollo.timeflow.R

val imageHash = HashMap<Int, Int>().apply {
    put(0, R.drawable.ic_number0)
    put(1, R.drawable.ic_number1)
    put(2, R.drawable.ic_number2)
    put(3, R.drawable.ic_number3)
    put(4, R.drawable.ic_number4)
    put(5, R.drawable.ic_number5)
    put(6, R.drawable.ic_number6)
    put(7, R.drawable.ic_number7)
    put(8, R.drawable.ic_number8)
    put(9, R.drawable.ic_number9)
}

val defaultFontFamily = FontFamily(
    fonts = listOf(Font(R.font.poppins_bold, FontWeight.Light))
)