package com.example.shoppinglist.settings_screen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.datastore.DataStoreManager
import com.example.shoppinglist.datastore.SettingsData
import com.example.shoppinglist.ui.theme.GrayLight
import com.example.shoppinglist.ui.theme.RedLight
import com.example.shoppinglist.ui.theme.White
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    val colorItemListState = mutableStateOf<List<ColorItem>>(emptyList())
    val colorThemeListState = mutableStateOf<List<ThemeItem>>(emptyList())
    val currentSetting = mutableStateOf(
        SettingsData(
            actionButtonColor = RedLight.value.toLong(),
            backroundColor = GrayLight.value.toLong(),
            bottomBarColor = White.value.toLong(),
            bottomBarIconsColor = RedLight.value.toLong()
        )
    )

    init {

        viewModelScope.launch {
            dataStoreManager.getStringPreference(
                DataStoreManager.TITLE_COLOR,
                "#C31E12"
            )
                .collect { selectedColor ->

                    val tempColorItemList = ArrayList<ColorItem>()
                    ColorUtils.colorList.forEach { color ->
                        tempColorItemList.add(
                            ColorItem(
                                color,
                                selectedColor == color
                            )
                        )
                    }
                    colorItemListState.value = tempColorItemList
                }
        }
        viewModelScope.launch {
            dataStoreManager.getSettings(
                SettingsData(
                    actionButtonColor = RedLight.value.toLong(),
                    backroundColor = GrayLight.value.toLong(),
                    bottomBarColor = White.value.toLong(),
                    bottomBarIconsColor = RedLight.value.toLong()
                )
            )
                .collect { selectedTheme ->
                    val tempSettingsItemList = ArrayList<ThemeItem>()
                    Log.d("Theme", "${ColorUtils.themeList.keys}")
                    ColorUtils.themeList.forEach { (color, settings) ->
                        tempSettingsItemList.add(
                            ThemeItem(
                                color,
                                settings,
                                selectedTheme == settings
                            )
                        )
                    }
                    colorThemeListState.value = tempSettingsItemList
                    currentSetting.value = selectedTheme
                    Log.d("Current Set", "${currentSetting.value.backroundColor}")
                }
        }
    }

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.OnItemSelected -> {
                viewModelScope.launch {
                    dataStoreManager.saveStringPreference(
                        event.color,
                        DataStoreManager.TITLE_COLOR
                    )
                }
            }

            is SettingsEvent.OnThemeSelected -> {
                viewModelScope.launch {
                    dataStoreManager.saveSettings(
                        event.settings
                    )
                }
            }
        }
    }
}