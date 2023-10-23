package com.example.shoppinglist.new_note_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.data.NoteItem
import com.example.shoppinglist.data.NoteItemRepository
import com.example.shoppinglist.datastore.DataStoreManager
import com.example.shoppinglist.utils.UiEvent
import com.example.shoppinglist.utils.getCurrentTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewNoteViewModel @Inject constructor(
    private val repository: NoteItemRepository,
    savedStateHandle: SavedStateHandle,
    dataStoreManager: DataStoreManager
): ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var titleColor = mutableStateOf("#C31E12")

    private var noteId = -1
    private var noteItem: NoteItem? = null

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    init {
        noteId = savedStateHandle.get<String>("noteId")?.toInt() ?: -1
        if (noteId != -1){
            viewModelScope.launch {
//                repository.getNoteItemById(noteId).let {  noteItem ->
//                    title = noteItem.title
//                    description = noteItem.description
//                    this@NewNoteViewModel.noteItem = noteItem
//                }
                this@NewNoteViewModel.noteItem = repository.getNoteItemById(noteId)
                title = noteItem!!.title
                description = noteItem!!.description
                dataStoreManager.getStringPreference(
                    DataStoreManager.TITLE_COLOR,
                    "#C31E12"
                ).collect{ color ->
                    titleColor.value = color
                }
            }
        }
    }

    fun onEvent(event: NewNoteEvent){
        when(event){
            is NewNoteEvent.OnSave -> {
                viewModelScope.launch {
                    if (title.isEmpty()){
                        sendUiEvent(UiEvent.ShowSnackBar("Название не может быть пустым!"))
                        return@launch
                    }
                    repository.insertItem(
                        NoteItem(
                            noteItem?.id,
                            title,
                            description,
                            noteItem?.time ?: getCurrentTime()
                        )
                    )
                    sendUiEvent(UiEvent.PopBackStack)
                }
            }
            is NewNoteEvent.OnTitleChange -> {
                title = event.title
            }
            is NewNoteEvent.OnDescriptionChange -> {
                description = event.description
            }
        }
    }

    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}