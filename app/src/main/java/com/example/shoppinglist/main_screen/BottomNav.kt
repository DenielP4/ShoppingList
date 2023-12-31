package com.example.shoppinglist.main_screen

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.shoppinglist.R
import com.example.shoppinglist.ui.theme.DarkText
import com.example.shoppinglist.ui.theme.GrayLight
import com.example.shoppinglist.ui.theme.LightText
import com.example.shoppinglist.ui.theme.RedLight

@Composable
fun BottomNav(
    currentRoute: String?,
    colorIcons: ULong,
    onNavigate: (String) -> Unit
) {
    val listItem = listOf(
        BottomNavItem.ListItem,
        BottomNavItem.Receipt,
        BottomNavItem.NoteItem,
        BottomNavItem.SettingsItem
    )
    BottomNavigation(backgroundColor = Color.White) {
        listItem.forEach { bottomNavItem ->
            BottomNavigationItem(
                selected = currentRoute == bottomNavItem.route,
                onClick = {
                    onNavigate(bottomNavItem.route)
                },
                icon = {
                    Icon(
                        painter = painterResource(id = bottomNavItem.iconId),
                        contentDescription = "icon"
                    )
                },
                label = {
                    Text(text = bottomNavItem.title)
                },
                selectedContentColor = Color(colorIcons),
                unselectedContentColor = LightText,
                alwaysShowLabel = true
            )
        }
    }
}