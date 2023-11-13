package com.example.shoppinglist.note_list_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.add_item_screen.AddItemEvent
import com.example.shoppinglist.data.NoteItem
import com.example.shoppinglist.data.NoteItemRepository
import com.example.shoppinglist.datastore.DataStoreManager
import com.example.shoppinglist.datastore.SettingsData
import com.example.shoppinglist.dialog.DialogController
import com.example.shoppinglist.dialog.DialogEvent
import com.example.shoppinglist.utils.Routes
import com.example.shoppinglist.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val repository: NoteItemRepository,
    dataStoreManager: DataStoreManager
): ViewModel(), DialogController {

    private val noteListFlow = repository.getAllItems()
    private var noteItem: NoteItem? = null

    var noteList by mutableStateOf(listOf<NoteItem>())
    var originNoteList = listOf<NoteItem>()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var titleColor = mutableStateOf("#C31E12")

    var searchText by mutableStateOf("")
        private set

    override var dialogTitle = mutableStateOf("Удалить заметку?")
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
    override var showFoodCheckBox = mutableStateOf(false)
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

    init {
        viewModelScope.launch {
            dataStoreManager.getStringPreference(
                DataStoreManager.TITLE_COLOR,
                "#C31E12"
            ).collect{ color ->
                titleColor.value = color
            }
        }

        viewModelScope.launch {
            noteListFlow.collect{ list ->
                noteList = list
                originNoteList = list
            }
        }
    }

    fun onEvent(event: NoteListEvent) {
        when(event){
            is NoteListEvent.OnShowDeleteDialog -> {
                openDialog.value = true
                noteItem = event.item
            }
            is NoteListEvent.OnItemClick -> {
                sendUiEvent(UiEvent.Navigate(event.route))
            }
            is NoteListEvent.UnDoneDeleteItem -> {
                viewModelScope.launch {
                    repository.insertItem(noteItem!!)
                }
            }
            is NoteListEvent.OnTextSearchChange -> {
                searchText = event.text
                noteList = originNoteList.filter { note ->
                    note.title.lowercase().startsWith(searchText.lowercase())
                }
            }
        }
    }

    override fun onDialogEvent(event: DialogEvent) {
        when(event){
            is DialogEvent.OnCancel -> {
                openDialog.value = false
            }
            is DialogEvent.OnConfirm -> {
                viewModelScope.launch {
                    repository.deleteItem(noteItem!!)
                    sendUiEvent(UiEvent.ShowSnackBar("Отменить удаление?"))
                }
                openDialog.value = false
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