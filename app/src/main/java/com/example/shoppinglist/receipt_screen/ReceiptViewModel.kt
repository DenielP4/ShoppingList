package com.example.shoppinglist.receipt_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.data.ReceiptListItem
import com.example.shoppinglist.data.ReceiptListRepository
import com.example.shoppinglist.receipt_dialog.ReceiptDialogController
import com.example.shoppinglist.receipt_dialog.ReceiptDialogEvent
import com.example.shoppinglist.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReceiptViewModel @Inject constructor(
    private val repository: ReceiptListRepository
) : ViewModel(), ReceiptDialogController {


    override var dialogTitleReceipt = mutableStateOf("")
        private set
    override var openReceiptDialog = mutableStateOf(false)
        private set
    override var receipt: ReceiptListItem = ReceiptListItem(null, "", "", 0, emptyList())
        private set
    override var showSaveButton = mutableStateOf(false)
        private set
    override var showDontSaveButton = mutableStateOf(false)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var receiptItem: ReceiptListItem? = null
    var receiptList = repository.getAllItems()


    fun onEvent(event: ReceiptEvent){
        when(event){
            is ReceiptEvent.OnItemClick -> {
                dialogTitleReceipt.value = event.receipt.receiptName
                openReceiptDialog.value = true
                receipt = event.receipt
            }
            is ReceiptEvent.OnDelete -> {
                receiptItem = event.receipt
                viewModelScope.launch {
                    repository.deleteItem(receiptItem!!)
                    sendUiEvent(UiEvent.ShowSnackBar("Отменить удаление?"))
                }
            }
            is ReceiptEvent.UnDoneDelete -> {
                viewModelScope.launch {
                    repository.insertItem(receiptItem!!)
                }
            }
        }
    }

    override fun onReceiptDialogEvent(event: ReceiptDialogEvent) {
        when(event){
            is ReceiptDialogEvent.OnExit -> {
                openReceiptDialog.value = false
                receipt = ReceiptListItem(null, "", "", 0, emptyList())
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