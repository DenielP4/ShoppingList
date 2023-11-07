package com.example.shoppinglist.data

class ReceiptListRepositoryImpl(
    private val dao: ReceiptListDao
) : ReceiptListRepository {

    override suspend fun insertItem(item: ReceiptListItem) {
        dao.insertItem(item)
    }

    override suspend fun deleteItem(item: ReceiptListItem) {
        dao.deleteItem(item)
    }

}