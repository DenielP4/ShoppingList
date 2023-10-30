package com.example.shoppinglist.settings_screen

import androidx.compose.ui.graphics.Color
import com.example.shoppinglist.ui.theme.RedLight
import com.example.shoppinglist.ui.theme.Yellow

object ColorUtils {
    val colorList = listOf(
        "#C31E12",
        "#24C312",
        "#487242",
        "#22b9a8",
        "#452e52",
        "#f28f93",
        "#ff00a1",
        "#041cf6",
        "#532a4a",
        "#774084",
        "#09cf6a",
        "#668096"
    )

    fun getProgressColor(progress: Float): Color {
        return when(progress){
            in 0.0..0.339 -> RedLight
            in 0.34..0.669 -> Yellow
            in 0.67..1.0 -> Color.Green
            else -> RedLight
        }
    }
}