package com.example.shoppinglist.settings_screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppinglist.ui.theme.DarkText
import com.example.shoppinglist.ui.theme.GrayLight

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {

    val list = viewModel.colorItemListState.value
    val listTheme = viewModel.colorThemeListState.value

    Column {
        Box {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Цвет заголовка",
                    fontSize = 16.sp
                )
                Text(
                    text = "Выбранный цвет",
                    fontSize = 12.sp,
                    color = DarkText
                )
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                ) {
                    items(list) { item ->
                        UiColorItem(item) { event ->
                            viewModel.onEvent(event)
                        }
                    }
                }
            }
        }
        Box {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Тема",
                    fontSize = 16.sp
                )
                Text(
                    text = "Выбранная тема",
                    fontSize = 12.sp,
                    color = DarkText
                )
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                ) {
                    items(listTheme) { item ->
                        UiThemeItem(item) { event ->
                            viewModel.onEvent(event)
                        }
                    }
                }
            }
        }
    }
}