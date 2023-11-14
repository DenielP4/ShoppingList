package com.example.shoppinglist.settings_screen

import androidx.compose.ui.graphics.Color
import com.example.shoppinglist.datastore.SettingsData
import com.example.shoppinglist.ui.theme.BlueLight
import com.example.shoppinglist.ui.theme.GrayLight
import com.example.shoppinglist.ui.theme.GreenLight
import com.example.shoppinglist.ui.theme.RedLight
import com.example.shoppinglist.ui.theme.White
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

    val themeList: Map<String, SettingsData> = mapOf(
        "#C31E12" to SettingsData(
            actionButtonColor = RedLight.value,
            backroundColor = RedLight.value,
            bottomBarColor = White.value,
            bottomBarIconsColor = RedLight.value
        ),
        "#041cf6" to SettingsData(
            actionButtonColor = BlueLight.value,
            backroundColor = BlueLight.value,
            bottomBarColor = White.value,
            bottomBarIconsColor = BlueLight.value
        ),
        "#24C312" to SettingsData(
            actionButtonColor = GreenLight.value,
            backroundColor = GreenLight.value,
            bottomBarColor = White.value,
            bottomBarIconsColor = GreenLight.value
        )
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