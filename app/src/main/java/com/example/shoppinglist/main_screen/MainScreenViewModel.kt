package com.example.shoppinglist.main_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.data.ShoppingListItem
import com.example.shoppinglist.data.ShoppingListRepository
import com.example.shoppinglist.dialog.DialogController
import com.example.shoppinglist.dialog.DialogEvent
import com.example.shoppinglist.shopping_list_screen.ShoppingListEvent
import com.example.shoppinglist.utils.Routes
import com.example.shoppinglist.utils.UiEvent
import com.example.shoppinglist.utils.getCurrentTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val repository: ShoppingListRepository
) : ViewModel(), DialogController {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    override var dialogTitle = mutableStateOf("Название списка")
        private set
    override var editableText = mutableStateOf("")
        private set
    override var openDialog = mutableStateOf(false)
        private set
    override var showEditableText = mutableStateOf(true)
        private set
    override var budgetNumber = mutableStateOf("")
        private set
    override var showBudgetNumber = mutableStateOf(true)
        private set
    override var countNumber = mutableStateOf("0")
        private set
    override var showCountNumber = mutableStateOf(false)
        private set
    override var priceNumber = mutableStateOf("0")
        private set
    override var showPriceNumber = mutableStateOf(false)
        private set

    var showFloatingButton = mutableStateOf(true)
        private set

    fun onEvent(event: MainScreenEvent){
        when(event){
            is MainScreenEvent.OnItemSave -> {
                if (editableText.value.isEmpty() || budgetNumber.value.isEmpty() || budgetNumber.value == "0") return
                viewModelScope.launch {
                    repository.insertItem(
                        ShoppingListItem(
                            null,
                            editableText.value,
                            getCurrentTime(),
                            0,
                            0,
                            budgetNumber.value.toInt()
                        )
                    )
                }
            }
            is MainScreenEvent.OnNewItemClick -> {
                if (event.route == Routes.SHOPPING_LIST){
                    openDialog.value = true
                } else {
                    sendUiEvent(UiEvent.NavigateMain(Routes.NEW_NOTE + "/-1"))
                }
            }
            is MainScreenEvent.Navigate -> {
                sendUiEvent(UiEvent.Navigate(event.route))
                showFloatingButton.value = !(event.route == Routes.RECEIPT_LIST || event.route == Routes.SETTINGS)
            }
            is MainScreenEvent.NavigateMain -> {
                sendUiEvent(UiEvent.NavigateMain(event.route))
            }
        }
    }

    override fun onDialogEvent(event: DialogEvent) {
        when(event){
            is DialogEvent.OnCancel -> {
                openDialog.value = false
                editableText.value = ""
            }
            is DialogEvent.OnConfirm -> {
                onEvent(MainScreenEvent.OnItemSave)
                openDialog.value = false
                editableText.value = ""
                budgetNumber.value = ""
            }
            is DialogEvent.OnTextChange -> {
                editableText.value = event.text
            }
            is DialogEvent.OnBudgetChange -> {
                budgetNumber.value = event.budget
            }
            else -> {}
        }
    }

    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}