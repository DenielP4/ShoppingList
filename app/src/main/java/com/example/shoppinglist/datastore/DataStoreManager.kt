package com.example.shoppinglist.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

const val DATA_STORE_NAME = "preference_storage_name"
private val Context.datastore: DataStore<Preferences> by preferencesDataStore(DATA_STORE_NAME)

class DataStoreManager(
    val context: Context
) {
    suspend fun saveStringPreference(value: String, key: String){
        context.datastore.edit { pref ->
            pref[stringPreferencesKey(key)] = value
        }
    }

    fun getStringPreference(key: String, defValue: String) = context.datastore.data.map { pref ->
        pref[stringPreferencesKey(key)] ?: defValue
    }

    companion object {
        const val TITLE_COLOR = "title_color"
    }

    suspend fun saveSettings(settingsData: SettingsData){
        context.datastore.edit { pref ->
            pref[longPreferencesKey("action_button_color")] = settingsData.actionButtonColor
            pref[longPreferencesKey("background_color")] = settingsData.backroundColor
            pref[longPreferencesKey("bottom_bar_color")] = settingsData.bottomBarColor
            pref[longPreferencesKey("bottom_bar_icons_color")] = settingsData.bottomBarIconsColor
        }
    }

   fun getSettings(defSettings: SettingsData) = context.datastore.data.map { pref ->
       return@map SettingsData(
           pref[longPreferencesKey("action_button_color")] ?: defSettings.actionButtonColor,
           pref[longPreferencesKey("background_color")] ?: defSettings.backroundColor,
           pref[longPreferencesKey("bottom_bar_color")] ?: defSettings.bottomBarColor,
           pref[longPreferencesKey("bottom_bar_icons_color")] ?: defSettings.bottomBarIconsColor
       )
   }


}