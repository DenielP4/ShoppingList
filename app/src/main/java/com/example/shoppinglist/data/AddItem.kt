package com.example.shoppinglist.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "add_item_table")
data class AddItem(
    @PrimaryKey
    val id: Int? = null,
    var name: String,
    val isCheck: Boolean,
    val listId: Int,
    val priority: Boolean,
    val count: Int,
    val price: Int,
    val sum: Int
) {
    fun finalSum(price: Int, count: Int): Int = price*count
}
