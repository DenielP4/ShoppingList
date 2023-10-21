package com.example.shoppinglist.main_screen

import com.example.shoppinglist.data.ShoppingListItem
import com.example.shoppinglist.shopping_list_screen.ShoppingListEvent

sealed class MainScreenEvent {
    object OnItemSave: MainScreenEvent()
    data class Navigate(val route: String): MainScreenEvent()
    data class NavigateMain(val route: String): MainScreenEvent()
    data class OnNewItemClick(val route: String): MainScreenEvent()
}
