package com.example.shoppinglist.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shop_list_item_table")
data class ShoppingListItem(
    @PrimaryKey
    val id: Int? = null,
    val name: String,
    val time: String,
    val allItemsCount: Int,
    val selectedItemsCount: Int,
    val budget: Int
)
