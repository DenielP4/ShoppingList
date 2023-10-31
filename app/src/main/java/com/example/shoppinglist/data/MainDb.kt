package com.example.shoppinglist.data

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        ShoppingListItem::class,
        AddItem::class,
        NoteItem::class
        // Простая миграция
//    CheckItem::class
    ],
    version = 1
    // Простая миграция
//    autoMigrations = [AutoMigration(from = 1, to = 2, spec = MainDb.RenameShopList::class)],
//    version = 2,
//    exportSchema = true
)
abstract class MainDb : RoomDatabase() {
    // Сложная миграция
    // RenameTable(fromTableName = "shop_list_name", toTableName = "shop_list")
    // class renameShopList: AutoMigrationSpec
    abstract val shoppingListDao: ShoppingListDao
    abstract val noteItemDao: NoteItemDao
    abstract val addItemDao: AddItemDao
}