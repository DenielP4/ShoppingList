package com.example.shoppinglist.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "add_item_table")
data class AddItem(
    @PrimaryKey
    val id: Int? = null,
    var name: String,
    val isCheck: Boolean,
    val listId: Int
)
