package com.example.shoppinglist.main_screen

import com.example.shoppinglist.data.ShoppingListItem
import com.example.shoppinglist.shopping_list_screen.ShoppingListEvent

sealed class MainScreenEvent {
    object OnShowEditDialog: MainScreenEvent()
    object OnItemSave: MainScreenEvent()
}
