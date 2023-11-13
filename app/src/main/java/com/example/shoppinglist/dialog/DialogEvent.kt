package com.example.shoppinglist.dialog

import com.example.shoppinglist.add_item_screen.AddItemEvent
import com.example.shoppinglist.data.AddItem

sealed class DialogEvent{
    data class OnTextChange(val text: String): DialogEvent()
    data class OnBudgetChange(val budget: String): DialogEvent()
    data class OnCountChange(val count: String): DialogEvent()
    data class OnPriceChange(val price: String): DialogEvent()
    data class OnGramChange(val gram: String): DialogEvent()
    object OnGramRadioBoxChange: DialogEvent()
    object OnKiloRadioBoxChange: DialogEvent()
    data class OnWeightChange(val weight: String): DialogEvent()
    data class OnCheckedChange(val food: Boolean): DialogEvent()
    object OnCancel: DialogEvent()
    object OnConfirm: DialogEvent()
}
