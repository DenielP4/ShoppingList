package com.example.shoppinglist.data

import kotlinx.coroutines.flow.Flow

interface ReceiptListRepository {

    suspend fun insertItem(item: ReceiptListItem)
    suspend fun deleteItem(item: ReceiptListItem)
    fun getAllItems(): Flow<List<ReceiptListItem>>
}