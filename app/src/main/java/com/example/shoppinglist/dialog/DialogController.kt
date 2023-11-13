package com.example.shoppinglist.dialog

import androidx.compose.runtime.MutableState
import com.example.shoppinglist.utils.UiEvent

interface DialogController {
    val dialogTitle: MutableState<String>
    val editableText: MutableState<String>
    val openDialog: MutableState<Boolean>
    val showEditableText: MutableState<Boolean>
    val budgetNumber: MutableState<String>
    val showBudgetNumber: MutableState<Boolean>
    val foodCheckBox: MutableState<Boolean>
    val showFoodCheckBox: MutableState<Boolean>
    val countNumber: MutableState<String>
    val showCountNumber: MutableState<Boolean>
    val priceNumber: MutableState<String>
    val showPriceNumber: MutableState<Boolean>
    val gramNumber: MutableState<String>
    val showGramNumber: MutableState<Boolean>
    val gramWeight: MutableState<Boolean>
    val showGramRadioBox: MutableState<Boolean>
    val kiloWeight: MutableState<Boolean>
    val showKiloRadioBox: MutableState<Boolean>
    val weightNumber: MutableState<String>
    val showWeightNumber: MutableState<Boolean>
    fun onDialogEvent(event: DialogEvent)
}