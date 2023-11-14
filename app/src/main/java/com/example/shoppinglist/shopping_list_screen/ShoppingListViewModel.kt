package com.example.shoppinglist.shopping_list_screen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.data.ShoppingListItem
import com.example.shoppinglist.data.ShoppingListRepository
import com.example.shoppinglist.dialog.DialogEvent
import com.example.shoppinglist.dialog.DialogController
import com.example.shoppinglist.utils.UiEvent
import com.example.shoppinglist.utils.getCurrentTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingListViewModel @Inject constructor(
    private val repository: ShoppingListRepository
) : ViewModel(), DialogController {

    val list = mutableStateOf<List<ShoppingListItem>>(emptyList())
    val showLoading = mutableStateOf(true)

    init {
        viewModelScope.launch {
            repository.getAllItems().collect { items ->
                list.value = items
                showLoading.value = !list.value.isEmpty()
                Log.d("!!!!!!!", "${showLoading.value}")
            }
        }
    }

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
    override var budgetNumber = mutableStateOf("0")
        private set
    override var showBudgetNumber = mutableStateOf(false)
        private set
    override var foodCheckBox = mutableStateOf(false)
        private set
    override var showFoodCheckBox = mutableStateOf(true)
        private set
    override var countNumber = mutableStateOf("0")
        private set
    override var showCountNumber = mutableStateOf(false)
        private set
    override var priceNumber = mutableStateOf("0")
        private set
    override var showPriceNumber = mutableStateOf(false)
        private set
    override var gramNumber = mutableStateOf("")
        private set
    override var showGramNumber = mutableStateOf(false)
        private set
    override var gramWeight = mutableStateOf(false)
        private set
    override var showGramRadioBox = mutableStateOf(false)
        private set
    override var kiloWeight = mutableStateOf(false)
        private set
    override var showKiloRadioBox = mutableStateOf(false)
        private set
    override var weightNumber = mutableStateOf("")
        private set
    override var showWeightNumber = mutableStateOf(false)
        private set

    fun onEvent(event: ShoppingListEvent){
        when(event){
            is ShoppingListEvent.OnItemSave -> {
                if (editableText.value.isEmpty() || budgetNumber.value.isEmpty() || budgetNumber.value == "0") return
                if (budgetNumber.value.toIntOrNull() == null) {
                    sendUiEvent(UiEvent.ShowSnackBar("В поле <Бюджет> можно писать только числа"))
                    return
                }
                viewModelScope.launch {
                    repository.insertItem(
                        ShoppingListItem(
                            listItem?.id,
                            editableText.value,
                            listItem?.time ?: getCurrentTime(),
                            listItem?.allItemsCount ?: 0,
                            listItem?.selectedItemsCount ?: 0,
                            budgetNumber.value.toInt(),
                            foodCheckBox.value
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
                budgetNumber.value = listItem?.budget.toString() ?: ""
                foodCheckBox.value = listItem?.food ?: false
                dialogTitle.value = "Название списка"
                showEditableText.value = true
                showBudgetNumber.value = true
                showFoodCheckBox.value = true
            }
            is ShoppingListEvent.OnShowDeleteDialog -> {
                listItem = event.item
                openDialog.value = true
                dialogTitle.value = "Удалить список"
                showEditableText.value = false
                showBudgetNumber.value = false
                showFoodCheckBox.value = false
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
            is DialogEvent.OnBudgetChange -> {
                budgetNumber.value = event.budget
            }
            is DialogEvent.OnCheckedChange -> {
                foodCheckBox.value = event.food
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