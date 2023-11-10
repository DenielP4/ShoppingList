package com.example.shoppinglist.food_dialog

import com.example.shoppinglist.receipt_dialog.ReceiptDialogEvent

sealed class FoodDialogEvent{
    object OnProductCount: FoodDialogEvent()
    object OnProductWeight: FoodDialogEvent()
    object OnCancel: FoodDialogEvent()
    object OnExit: FoodDialogEvent()
}
