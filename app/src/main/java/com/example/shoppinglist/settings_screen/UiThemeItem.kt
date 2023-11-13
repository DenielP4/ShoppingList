package com.example.shoppinglist.settings_screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.R
import com.example.shoppinglist.datastore.SettingsData

@Composable
fun UiThemeItem(
    item: ThemeItem,
    onEvent: (SettingsEvent) -> Unit
) {
    IconButton(
        modifier = Modifier
            .padding(start = 10.dp)
            .clip(CircleShape)
            .size(35.dp)
            .background(
                color = Color(
                    android.graphics.Color.parseColor(item.color)
                )
            ),
        onClick = {
            Log.d("Current Settings", "${item.settings}")
            onEvent(SettingsEvent.OnThemeSelected(item.settings))
        }
    ) {
        if (item.isSelected) {
            Icon(
                painter = painterResource(id = R.drawable.check_icon),
                contentDescription = "Check",
                tint = Color.White
            )
        }
    }
}