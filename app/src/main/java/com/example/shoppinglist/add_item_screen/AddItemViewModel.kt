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
import com.example.shoppinglist.data.ReceiptListItem
import com.example.shoppinglist.data.ShoppingListItem
import com.example.shoppinglist.dialog.DialogController
import com.example.shoppinglist.dialog.DialogEvent
import com.example.shoppinglist.food_dialog.FoodDialogController
import com.example.shoppinglist.food_dialog.FoodDialogEvent
import com.example.shoppinglist.main_screen.MainScreenEvent
import com.example.shoppinglist.receipt_dialog.ReceiptDialogController
import com.example.shoppinglist.receipt_dialog.ReceiptDialogEvent
import com.example.shoppinglist.ui.theme.DarkText
import com.example.shoppinglist.ui.theme.GrayLight
import com.example.shoppinglist.ui.theme.LightText
import com.example.shoppinglist.utils.UiEvent
import com.example.shoppinglist.utils.getCurrentTime
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
    savedStateHandle: SavedStateHandle
) : ViewModel(), DialogController, ReceiptDialogController, FoodDialogController {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var itemsList: Flow<List<AddItem>>? = null
    var addItem: AddItem? = null

    var receiptItem: ReceiptListItem? = null
    var shoppingListItem: ShoppingListItem? = null
    var currentBudget by mutableStateOf(0)
    var currentList by mutableStateOf("")
    var basket by mutableStateOf(0)
    var listCheckedItems by mutableStateOf(emptyList<AddItem>())
    var colorBudget by mutableStateOf(Color.Green)
    var colorReceipt by mutableStateOf(GrayLight)
    var colorReceiptText by mutableStateOf(DarkText)
    var listId: Int = -1

    var weightCheck by mutableStateOf(false)
        private set

    init {
        listId = savedStateHandle.get<String>("listId")?.toInt()!!
        itemsList = repository.getAllItemsById(listId)
        viewModelScope.launch {
            shoppingListItem = repository.getListItemById(listId)
            currentList = shoppingListItem?.name!!
            currentBudget = shoppingListItem?.budget!!
        }
        updateListChekedItem()
        updateBasket()
    }

    var itemText = mutableStateOf("")
        private set
    var isPriority = mutableStateOf(false)
        private set

    override var dialogTitle = mutableStateOf("Харктеристика товара")
        private set
    override var editableText = mutableStateOf("")
        private set
    override var openDialog = mutableStateOf(false)
        private set
    override var showEditableText = mutableStateOf(false)
        private set
    override var budgetNumber = mutableStateOf("")
        private set
    override var showBudgetNumber = mutableStateOf(false)
        private set
    override var foodCheckBox = mutableStateOf(false)
        private set
    override var showFoodCheckBox = mutableStateOf(false)
        private set
    override var countNumber = mutableStateOf("")
        private set
    override var showCountNumber = mutableStateOf(false)
        private set
    override var priceNumber = mutableStateOf("")
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

    override var dialogTitleReceipt = mutableStateOf("")
        private set
    override var openReceiptDialog = mutableStateOf(false)
        private set
    override var receipt: ReceiptListItem = ReceiptListItem(null, "", "", 0, emptyList())
        private set
    override var showSaveButton = mutableStateOf(true)
        private set
    override var showDontSaveButton = mutableStateOf(true)
        private set


    override var dialogTitleFood = mutableStateOf("Какой товар?")
        private set
    override var openFoodDialog = mutableStateOf(false)
        private set
    override var showProductCount = mutableStateOf(true)
        private set
    override var showProductWeight = mutableStateOf(true)
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
                            addItem?.gram ?: 0,
                            addItem?.weight ?:0,
                            addItem?.price ?: 0,
                            if (weightCheck)
                                addItem?.sumOfProductWeight(addItem?.gram ?: 0, addItem?.weight ?: 0, addItem?.price ?: 0) ?: 0
                            else
                                addItem?.finalSum(addItem?.price ?: 0, addItem?.count ?: 0) ?: 0
                        )
                    )
                }
                itemText.value = ""
                weightCheck = false
                isPriority.value = false
                addItem = null
                updateShoppingListCount()
            }

            is AddItemEvent.OnShowEditDialog -> {
                addItem = event.item
                if (shoppingListItem!!.food) {
                    if (addItem!!.sum > 0){
                        if (addItem!!.weight > 0){
                            openDialog.value = true
                            dialogTitle.value = "Весовой товар"
                            weightCheck = true
                            editableText.value = event.item.name
                            showEditableText.value = true
                            priceNumber.value = if (event.item.price == 0) "" else event.item.price.toString()
                            showPriceNumber.value = true
                            gramNumber.value = if (event.item.gram == 0) "" else event.item.gram.toString()
                            showGramNumber.value = true
                            weightNumber.value = if (event.item.weight == 0) "" else event.item.weight.toString()
                            showWeightNumber.value = true
                            showWeightNumber.value = true
                            gramWeight.value = true
                            showGramRadioBox.value = true
                            kiloWeight.value = false
                            showKiloRadioBox.value = true
                        } else {
                            openDialog.value = true
                            dialogTitle.value = "Штучный товар"
                            weightCheck = false
                            editableText.value = event.item.name
                            showEditableText.value = true
                            countNumber.value = if (event.item.count == 0) "" else event.item.count.toString()
                            showCountNumber.value = true
                            priceNumber.value = if (event.item.price == 0) "" else event.item.price.toString()
                            showPriceNumber.value = true
                        }
                    } else {
                        openFoodDialog.value = true
                    }
                } else {
                    openDialog.value = true
                    dialogTitle.value = "Штучный товар"
                    weightCheck = false
                    editableText.value = event.item.name
                    showEditableText.value = true
                    countNumber.value = if (event.item.count == 0) "" else event.item.count.toString()
                    showCountNumber.value = true
                    priceNumber.value = if (event.item.price == 0) "" else event.item.price.toString()
                    showPriceNumber.value = true
                }
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
                    if (event.item.price != 0)
                        repository.insertItem(event.item)
                    else{
                        onEvent(AddItemEvent.OnShowEditDialog(event.item))
                    }
                }
                updateBasket()
                updateShoppingListCount()
            }

            is AddItemEvent.OnPriorityChange -> {
                isPriority.value = !isPriority.value
            }

            is AddItemEvent.OnGenerateReceipt -> {
                if (basket == 0)
                    sendUiEvent(UiEvent.ShowSnackBar("Сначала добавьте товар в корзину!"))
                else {
                    viewModelScope.launch {
                        receiptItem = ReceiptListItem(
                            receiptItem?.receiptId,
                            shoppingListItem!!.name,
                            getCurrentTime(),
                            basket,
                            listCheckedItems
                        )
                        Log.d("Receipt", "$receiptItem")
                    }
                    openReceiptDialog.value = true
                    receipt = receiptItem!!
                }

            }
        }
    }

    override fun onDialogEvent(event: DialogEvent) {
        when (event) {
            is DialogEvent.OnCancel -> {
                clearAllInput()
            }

            is DialogEvent.OnConfirm -> {
                if (weightCheck){
                    val price = priceNumber.value
                    val gram = gramNumber.value
                    val weight = weightNumber.value
                    if (gram.toIntOrNull() == null || price.toIntOrNull() == null || weight.toIntOrNull() == null){
                        sendUiEvent(UiEvent.ShowSnackBar("В числовые поля можно писать только числа!"))
                        clearAllInput()
                        return
                    }
                    addItem = addItem?.copy(
                        name = editableText.value,
                        price = price.toInt(),
                        gram = if (kiloWeight.value) gram.toInt()*1000 else gram.toInt(),
                        weight = weight.toInt()
                    )
                    clearAllInput()

                } else {
                    val count = countNumber.value
                    val price = priceNumber.value
                    if (count.toIntOrNull() == null || price.toIntOrNull() == null){
                        sendUiEvent(UiEvent.ShowSnackBar("В числовые поля можно писать только числа!"))
                        clearAllInput()
                        return
                    }
                    addItem = addItem?.copy(
                        name = editableText.value,
                        count = count.toInt(),
                        price = price.toInt()
                    )
                    clearAllInput()
                }
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

            is DialogEvent.OnGramChange -> {
                gramNumber.value = event.gram
            }

            is DialogEvent.OnGramRadioBoxChange -> {
                if (gramWeight.value) gramWeight.value = true
                else {
                    gramWeight.value = true
                    kiloWeight.value = false
                }
            }

            is DialogEvent.OnKiloRadioBoxChange -> {
                if (kiloWeight.value) kiloWeight.value = true
                else {
                    kiloWeight.value = true
                    gramWeight.value = false
                }
            }

            is DialogEvent.OnWeightChange -> {
                weightNumber.value = event.weight
            }
            else -> {}
        }
    }

    private fun clearAllInput() {
        openDialog.value = false
        editableText.value = ""
        countNumber.value = ""
        priceNumber.value = ""
        showEditableText.value = false
        showCountNumber.value = false
        showPriceNumber.value = false
        gramNumber.value = ""
        showGramNumber.value = false
        weightNumber.value = ""
        showWeightNumber.value = false
        gramWeight.value = false
        showGramRadioBox.value = false
        kiloWeight.value = false
        showKiloRadioBox.value = false
    }

    private fun updateListChekedItem() {
        viewModelScope.launch {
            itemsList?.collect { list ->
                listCheckedItems = list.filter { it.isCheck }
            }
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
        colorReceipt = if (basket == 0) GrayLight else Color.Green
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


    override fun onReceiptDialogEvent(event: ReceiptDialogEvent) {
        when (event) {
            ReceiptDialogEvent.OnCancel -> {
                openReceiptDialog.value = false
                receipt = ReceiptListItem(null, "", "", 0, emptyList())
                viewModelScope.launch {
                    listCheckedItems.forEach {  chItem ->
                        repository.deleteItem(chItem)
                    }
                }
                updateShoppingListCount()
                sendUiEvent(UiEvent.ShowSnackBar("Ваш чек не был сохранён!"))
            }
            ReceiptDialogEvent.OnConfirm -> {
                openReceiptDialog.value = false
                viewModelScope.launch {
                    repository.insertReceipt(receiptItem!!)
                    listCheckedItems.forEach {  chItem ->
                        repository.deleteItem(chItem)
                    }
                }
                updateShoppingListCount()
                sendUiEvent(UiEvent.ShowSnackBar("Ваш чек был сохранён!"))
            }
            ReceiptDialogEvent.OnExit -> {
                openReceiptDialog.value = false
                receipt = ReceiptListItem(null, "", "", 0, emptyList())
            }
        }
    }

    override fun onFoodDialogEvent(event: FoodDialogEvent) {
        when(event){
            FoodDialogEvent.OnCancel -> {
                openFoodDialog.value = false
            }
            FoodDialogEvent.OnExit -> {
                openFoodDialog.value = false
            }
            FoodDialogEvent.OnProductCount -> {
                weightCheck = false
                openFoodDialog.value = false
                openDialog.value = true
                dialogTitle.value = "Штучный товар"
                editableText.value = addItem!!.name
                showEditableText.value = true
                countNumber.value = ""
                showCountNumber.value = true
                priceNumber.value = ""
                showPriceNumber.value = true
            }
            FoodDialogEvent.OnProductWeight -> {
                weightCheck = true
                openFoodDialog.value = false
                openDialog.value = true
                dialogTitle.value = "Весовой товар"
                editableText.value = addItem!!.name
                showEditableText.value = true
                priceNumber.value = ""
                showPriceNumber.value = true
                gramNumber.value = ""
                showGramNumber.value = true
                weightNumber.value = ""
                showWeightNumber.value = true
                gramWeight.value = true
                showGramRadioBox.value = true
                kiloWeight.value = false
                showKiloRadioBox.value = true
            }
        }
    }

}