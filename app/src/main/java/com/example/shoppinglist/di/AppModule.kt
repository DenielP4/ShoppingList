package com.example.shoppinglist.di

import android.app.Application
import androidx.room.Room
import com.example.shoppinglist.data.AddItemRepository
import com.example.shoppinglist.data.AddItemRepositoryImpl
import com.example.shoppinglist.data.MainDb
import com.example.shoppinglist.data.NoteItemRepository
import com.example.shoppinglist.data.NoteItemRepositoryImpl

import com.example.shoppinglist.data.ShoppingListDao
import com.example.shoppinglist.data.ShoppingListRepository
import com.example.shoppinglist.data.ShoppingListRepositoryImpl
import com.example.shoppinglist.datastore.DataStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideMainDb(app: Application): MainDb{
        return Room.databaseBuilder(
            app,
            MainDb::class.java,
            "shop_list_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideShoppingRepository(db: MainDb): ShoppingListRepository{
        return ShoppingListRepositoryImpl(db.shoppingListDao)
    }

    @Provides
    @Singleton
    fun provideAddItemRepository(db: MainDb): AddItemRepository{
        return AddItemRepositoryImpl(db.addItemDao)
    }

    @Provides
    @Singleton
    fun provideNoteItemRepository(db: MainDb): NoteItemRepository{
        return NoteItemRepositoryImpl(db.noteItemDao)
    }

//    @Provides
//    @Singleton
//    fun provideReceiptItemRepository(db: MainDb): ReceiptListRepository{
//        return ReceiptListRepositoryImpl(db.receiptListDao)
//    }

    @Provides
    @Singleton
    fun provideDataStoreManager(app: Application): DataStoreManager{
        return DataStoreManager(app)
    }
}

