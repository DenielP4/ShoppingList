package com.example.shoppinglist.settings_screen

sealed class SettingsEvent{
    data class OnItemSelected(val color: String): SettingsEvent()
}
