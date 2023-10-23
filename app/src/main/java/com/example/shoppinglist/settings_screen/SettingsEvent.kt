package com.example.shoppinglist.settings_screen

sealed class SettingsEvent{
    data class OnItemselected(val color: String): SettingsEvent()
}
