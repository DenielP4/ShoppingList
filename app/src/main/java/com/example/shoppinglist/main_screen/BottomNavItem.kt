package com.example.shoppinglist.main_screen

import com.example.shoppinglist.R
import com.example.shoppinglist.utils.Routes

sealed class BottomNavItem(val title: String, val iconId: Int, val route: String){
    object ListItem: BottomNavItem("Список", R.drawable.list_icon, Routes.SHOPPING_LIST)
    object Receipt: BottomNavItem("Чек", R.drawable.receipt_icon, Routes.RECEIPT_LIST)
    object AboutItem: BottomNavItem("About", R.drawable.about_icon, Routes.ABOUT)
    object NoteItem: BottomNavItem("Заметки", R.drawable.note_icon, Routes.NOTE_LIST)
    object SettingsItem: BottomNavItem("Настройки", R.drawable.settings_icon, Routes.SETTINGS)
}
