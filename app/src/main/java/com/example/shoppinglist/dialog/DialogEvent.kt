package com.example.shoppinglist.dialog

sealed class DialogEvent{
    data class OnTextChange(val text: String): DialogEvent()
    data class OnBudgetChange(val budget: String): DialogEvent()
    data class OnCountChange(val count: String): DialogEvent()
    data class OnPriceChange(val price: String): DialogEvent()
    object OnCancel: DialogEvent()
    object OnConfirm: DialogEvent()
}
