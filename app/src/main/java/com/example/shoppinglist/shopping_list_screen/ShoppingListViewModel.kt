package com.example.shoppinglist.shopping_list_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.data.ShoppingListItem
import com.example.shoppinglist.data.ShoppingListRepository
import com.example.shoppinglist.dialog.DialogEvent
import com.example.shoppinglist.dialog.DialogController
import com.example.shoppinglist.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingListViewModel @Inject constructor(
    private val repository: ShoppingListRepository
) : ViewModel(), DialogController {

    val list = repository.getAllItems()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var listItem: ShoppingListItem? = null
    override var dialogTitle = mutableStateOf("")
        private set
    override var editableText = mutableStateOf("")
        private set
    override var openDialog = mutableStateOf(false)
        private set
    override var showEditableText = mutableStateOf(false)
        private set

    fun onEvent(event: ShoppingListEvent){
        when(event){
            is ShoppingListEvent.OnItemSave -> {
                if (editableText.value.isEmpty()) return
                viewModelScope.launch {
                    repository.insertItem(
                        ShoppingListItem(
                            listItem?.id,
                            editableText.value,
                            "12/12/2023 13:00",
                            listItem?.allItemsCount ?: 0,
                            listItem?.selectedItemsCount ?: 0
                        )
                    )
                }
            }
            is ShoppingListEvent.OnItemClick -> {
                sendUiEvent(UiEvent.Navigate(event.route))
            }
            is ShoppingListEvent.OnShowEditDialog -> {
                listItem = event.item
                openDialog.value = true
                editableText.value = listItem?.name ?: ""
                dialogTitle.value = "Название списка"
                showEditableText.value = true
            }
            is ShoppingListEvent.OnShowDeleteDialog -> {
                listItem = event.item
                openDialog.value = true
                dialogTitle.value = "Удалить список"
                showEditableText.value = false
            }
        }
    }

    override fun onDialogEvent(event: DialogEvent){
        when(event){
            is DialogEvent.OnCancel -> {
                openDialog.value = false
            }
            is DialogEvent.OnConfirm -> {
                if (showEditableText.value) {
                    onEvent(ShoppingListEvent.OnItemSave)
                } else {
                    viewModelScope.launch {
                        listItem?.let { repository.deleteItem(it) }
                    }
                }
                openDialog.value = false
            }
            is DialogEvent.OnTextChange -> {
                editableText.value = event.text
            }
        }
    }

    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}