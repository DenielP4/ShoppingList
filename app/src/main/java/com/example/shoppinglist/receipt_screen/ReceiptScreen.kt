package com.example.shoppinglist.receipt_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarResult
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppinglist.note_list_screen.NoteListEvent
import com.example.shoppinglist.receipt_dialog.ReceiptDialog
import com.example.shoppinglist.shopping_list_screen.UiShoppingListItem
import com.example.shoppinglist.ui.theme.GrayLight
import com.example.shoppinglist.ui.theme.RedLight
import com.example.shoppinglist.utils.UiEvent

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ReceiptScreen(
    viewModel: ReceiptViewModel = hiltViewModel()
) {
    
    val receiptList = viewModel.receiptList.collectAsState(initial = emptyList())
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.ShowSnackBar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = uiEvent.message,
                        actionLabel = "Отмена"
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(ReceiptEvent.UnDoneDelete)
                    }
                }

                else -> {}
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(hostState = scaffoldState.snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    backgroundColor = RedLight,
                    modifier = Modifier.padding(bottom = 100.dp)
                )
            }
        }
    ){
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(GrayLight),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            items(receiptList.value) { item ->
                UiReceiptItem(item = item) { event ->
                    viewModel.onEvent(event)
                }
            }
        }
    }
    ReceiptDialog(viewModel)
}