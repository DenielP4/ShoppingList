package com.example.shoppinglist.data

import kotlinx.coroutines.flow.Flow

class ReceiptListRepositoryImpl(
    private val dao: ReceiptListDao
) : ReceiptListRepository {

    override suspend fun insertItem(item: ReceiptListItem) {
        dao.insertItem(item)
    }

    override suspend fun deleteItem(item: ReceiptListItem) {
        dao.deleteItem(item)
    }

    override fun getAllItems(): Flow<List<ReceiptListItem>> {
        return dao.getAllItems()
    }

}