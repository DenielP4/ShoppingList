package com.example.shoppinglist.receipt_dialog

sealed class ReceiptDialogEvent{
    object OnCancel: ReceiptDialogEvent()
    object OnConfirm: ReceiptDialogEvent()
    object OnExit: ReceiptDialogEvent()
}
