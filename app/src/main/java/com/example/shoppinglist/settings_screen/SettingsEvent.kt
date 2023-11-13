package com.example.shoppinglist.settings_screen

import com.example.shoppinglist.datastore.SettingsData

sealed class SettingsEvent{
    data class OnItemSelected(val color: String): SettingsEvent()
    data class OnThemeSelected(val settings: SettingsData): SettingsEvent()
}
