package com.example.shoppinglist.food_dialog

import androidx.compose.runtime.MutableState
import com.example.shoppinglist.receipt_dialog.ReceiptDialogEvent

interface FoodDialogController {
    val dialogTitleFood: MutableState<String>
    val openFoodDialog: MutableState<Boolean>
    val showProductCount: MutableState<Boolean>
    val showProductWeight: MutableState<Boolean>
    fun onFoodDialogEvent(event: FoodDialogEvent)
}