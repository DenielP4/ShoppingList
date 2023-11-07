package com.example.shoppinglist.data

interface ReceiptListRepository {

    suspend fun insertItem(item: ReceiptListItem)
    suspend fun deleteItem(item: ReceiptListItem)
}