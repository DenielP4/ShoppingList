package com.example.shoppinglist.datastore

import com.example.shoppinglist.ui.theme.RedLight
import com.example.shoppinglist.ui.theme.White

data class SettingsData(
    val actionButtonColor: ULong = RedLight.value,
    val backroundColor: ULong = RedLight.value,
    val bottomBarColor: ULong = White.value,
    val bottomBarIconsColor: ULong = RedLight.value
)
