package com.example.shoppinglist.settings_screen

import com.example.shoppinglist.datastore.SettingsData


data class ThemeItem(
    val color: String,
    val settings: SettingsData,
    val isSelected: Boolean
)
