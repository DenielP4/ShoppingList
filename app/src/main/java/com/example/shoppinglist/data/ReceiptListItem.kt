package com.example.shoppinglist.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName = "receipt_list_item_table")
data class ReceiptListItem(
    @PrimaryKey
    val receiptId: Int? = null,
    val receiptName: String,
    val receiptTime: String,
    val finalSum: Int,
    val listItem: List<AddItem>?
)
