package com.example.shoppinglist.note_list_screen

import com.example.shoppinglist.data.NoteItem

sealed class NoteListEvent{
    data class OnShowDeleteDialog(val item: NoteItem): NoteListEvent()
    data class OnItemClick(val route: String): NoteListEvent()
    data class OnTextSearchChange(val text: String): NoteListEvent()
    object UnDoneDeleteItem: NoteListEvent()
}
