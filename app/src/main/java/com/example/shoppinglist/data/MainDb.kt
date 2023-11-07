package com.example.shoppinglist.data

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        ShoppingListItem::class,
        AddItem::class,
        NoteItem::class,
        ReceiptListItem::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class MainDb : RoomDatabase() {
    abstract val receiptListDao: ReceiptListDao
    abstract val shoppingListDao: ShoppingListDao
    abstract val noteItemDao: NoteItemDao
    abstract val addItemDao: AddItemDao
}