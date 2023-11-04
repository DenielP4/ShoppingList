package com.example.shoppinglist.add_item_screen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.data.AddItem
import com.example.shoppinglist.data.AddItemRepository
//import com.example.shoppinglist.data.ReceiptListRepository
import com.example.shoppinglist.data.ShoppingListItem
import com.example.shoppinglist.dialog.DialogController
import com.example.shoppinglist.dialog.DialogEvent
import com.example.shoppinglist.main_screen.MainScreenEvent
import com.example.shoppinglist.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddItemViewModel @Inject constructor(
    private val repository: AddItemRepository,
//    private val repositoryReceipt: ReceiptListRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel(), DialogController {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var itemsList: Flow<List<AddItem>>? = null
    var addItem: AddItem? = null
    var shoppingListItem: ShoppingListItem? = null
    var currentBudget by mutableStateOf(0)
    var currentList by mutableStateOf("")
    var basket by mutableStateOf(0)
    var colorBudget by mutableStateOf(Color.Green)
    var listId: Int = -1

    init {
        listId = savedStateHandle.get<String>("listId")?.toInt()!!
        itemsList = repository.getAllItemsById(listId)
        viewModelScope.launch {
            shoppingListItem = repository.getListItemById(listId)
            currentList = shoppingListItem?.name!!
            currentBudget = shoppingListItem?.budget!!
        }
    }

    var itemText = mutableStateOf("")
        private set
    var isPriority = mutableStateOf(false)
        private set

    override var dialogTitle = mutableStateOf("")
        private set
    override var editableText = mutableStateOf("")
        private set
    override var openDialog = mutableStateOf(false)
        private set
    override var showEditableText = mutableStateOf(true)
        private set
    override var budgetNumber = mutableStateOf("0")
        private set
    override var showBudgetNumber = mutableStateOf(false)
        private set
    override var countNumber = mutableStateOf("0")
        private set
    override var showCountNumber = mutableStateOf(true)
        private set
    override var priceNumber = mutableStateOf("0")
        private set
    override var showPriceNumber = mutableStateOf(true)
        private set

    fun onEvent(event: AddItemEvent) {
        when (event) {
            is AddItemEvent.OnItemSave -> {
                viewModelScope.launch {
                    if (listId == -1) return@launch
                    if (addItem != null) {
                        if (addItem!!.name.isEmpty()) {
                            sendUiEvent(UiEvent.ShowSnackBar("Название не может быть пустым!"))
                            return@launch
                        }
                    } else {
                        if (itemText.value.isEmpty()) {
                            sendUiEvent(UiEvent.ShowSnackBar("Название не может быть пустым!"))
                            return@launch
                        }
                    }
                    repository.insertItem(
                        AddItem(
                            addItem?.id,
                            addItem?.name ?: itemText.value,
                            addItem?.isCheck ?: false,
                            listId,
                            addItem?.priority ?: isPriority.value,
                            addItem?.count ?: 0,
                            addItem?.price ?: 0,
                            addItem?.finalSum(addItem?.price ?: 0, addItem?.count ?: 0) ?: 0
                        )
                    )
                }
                itemText.value = ""
                isPriority.value = false
                addItem = null
                updateShoppingListCount()
            }

            is AddItemEvent.OnShowEditDialog -> {
                addItem = event.item
                openDialog.value = true
                editableText.value = event.item.name
                countNumber.value = event.item.count.toString()
                priceNumber.value = event.item.price.toString()
            }

            is AddItemEvent.OnTextChange -> {
                itemText.value = event.text
            }

            is AddItemEvent.OnDelete -> {
                viewModelScope.launch {
                    repository.deleteItem(event.item)
                }
                updateShoppingListCount()
            }

            is AddItemEvent.OnCheckedChange -> {
                viewModelScope.launch {
                    if (event.item.price !=0)
                        repository.insertItem(event.item)
                    else
                        sendUiEvent(UiEvent.ShowSnackBar("Укажите цену и количество товара <${event.item.name}>"))
                }
                updateBasket()
                updateShoppingListCount()
            }

            is AddItemEvent.OnPriorityChange -> {
                isPriority.value = !isPriority.value
            }

            is AddItemEvent.OnGenerateReceipt -> {
                viewModelScope.launch {
                    itemsList?.collect { list ->
                        val listCheckedItems = list.filter { it.isCheck }
                        Log.d("List", "$listCheckedItems")
                    }
                }
            }
        }
    }

    override fun onDialogEvent(event: DialogEvent) {
        when (event) {
            is DialogEvent.OnCancel -> {
                openDialog.value = false
                editableText.value = ""
            }

            is DialogEvent.OnConfirm -> {
                openDialog.value = false
                addItem = addItem?.copy(
                    name = editableText.value,
                    count = if (countNumber.value.toIntOrNull() != null) countNumber.value.toInt() else {
                        sendUiEvent(UiEvent.ShowSnackBar("В поле <Количетсво> можно писать только числа!"))
                        return
                    },
                    price = if (priceNumber.value.toIntOrNull() != null) priceNumber.value.toInt() else {
                        sendUiEvent(UiEvent.ShowSnackBar("В поле <Цена> можно писать только числа!"))
                        return
                    }
                )
                editableText.value = ""
                countNumber.value = "0"
                priceNumber.value = "0"
                onEvent(AddItemEvent.OnItemSave)
            }

            is DialogEvent.OnTextChange -> {
                editableText.value = event.text
            }

            is DialogEvent.OnPriceChange -> {
                priceNumber.value = event.price
            }

            is DialogEvent.OnCountChange -> {
                countNumber.value = event.count
            }

            else -> {}
        }
    }

    private fun updateBasket() {
        viewModelScope.launch {
            itemsList?.collect { list ->
                val sum = list
                    .filter { it.isCheck }
                    .sumOf { it.sum }
                basket = sum
                checkBasket()
            }
            Log.d("basket =", "$basket")
        }
    }

    private fun checkBasket() {
        colorBudget = if (basket > currentBudget) Color.Red else Color.Green
    }

    private fun updateShoppingListCount() {
        viewModelScope.launch {
            itemsList?.collect { list ->
                var counter = 0
                list.forEach { item ->
                    if (item.isCheck) counter++
                }
                shoppingListItem?.copy(
                    allItemsCount = list.size,
                    selectedItemsCount = counter

                )?.let { shItem ->
                    repository.insertItem(shItem)
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}