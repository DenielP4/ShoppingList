package com.example.shoppinglist.receipt_screen

import com.example.shoppinglist.add_item_screen.AddItemEvent
import com.example.shoppinglist.data.AddItem
import com.example.shoppinglist.data.ReceiptListItem
import com.example.shoppinglist.shopping_list_screen.ShoppingListEvent

sealed class ReceiptEvent{
    data class OnDelete(val receipt: ReceiptListItem): ReceiptEvent()
    object UnDoneDelete: ReceiptEvent()
    data class OnItemClick(val receipt: ReceiptListItem): ReceiptEvent()
}
