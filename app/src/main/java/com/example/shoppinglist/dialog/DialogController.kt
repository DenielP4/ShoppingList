package com.example.shoppinglist.dialog

import androidx.compose.runtime.MutableState

interface DialogController {
    val dialogTitle: MutableState<String>
    val editableText: MutableState<String>
    val openDialog: MutableState<Boolean>
    val showEditableText: MutableState<Boolean>
    val budgetNumber: MutableState<String>
    val showBudgetNumber: MutableState<Boolean>
    val countNumber: MutableState<String>
    val showCountNumber: MutableState<Boolean>
    val priceNumber: MutableState<String>
    val showPriceNumber: MutableState<Boolean>
    fun onDialogEvent(event: DialogEvent)
}