package com.example.shoppinglist.add_item_screen

import com.example.shoppinglist.data.AddItem

sealed class AddItemEvent {
    data class OnDelete(val item: AddItem): AddItemEvent()
    data class OnShowEditDialog(val item: AddItem): AddItemEvent()
    data class OnTextChange(val text: String): AddItemEvent()
    data class OnPriorityChange(val priority: Boolean): AddItemEvent()
    data class OnCheckedChange(val item: AddItem): AddItemEvent()
    object OnItemSave: AddItemEvent()
    object OnGenerateReceipt: AddItemEvent()
}
