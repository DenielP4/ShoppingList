package com.example.shoppinglist.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ReceiptListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ReceiptListItem)

    @Delete
    suspend fun deleteItem(item: ReceiptListItem)

    @Query("SELECT * FROM receipt_list_item_table")
    fun getAllItems(): Flow<List<ReceiptListItem>>


}