package com.example.shoppinglist.receipt_dialog

import androidx.compose.runtime.MutableState
import com.example.shoppinglist.data.ReceiptListItem

interface ReceiptDialogController {
    val dialogTitleReceipt: MutableState<String>
    val openReceiptDialog: MutableState<Boolean>
    val receipt: ReceiptListItem
    val showSaveButton: MutableState<Boolean>
    val showDontSaveButton: MutableState<Boolean>
    fun onReceiptDialogEvent(event: ReceiptDialogEvent)
}