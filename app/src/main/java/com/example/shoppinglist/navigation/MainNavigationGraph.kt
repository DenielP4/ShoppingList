package com.example.shoppinglist.navigation

import android.annotation.SuppressLint
import android.util.Log
import android.view.animation.OvershootInterpolator
import android.window.SplashScreen
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.shoppinglist.R
import com.example.shoppinglist.about_screen.AboutScreen
import com.example.shoppinglist.add_item_screen.AddItemScreen
import com.example.shoppinglist.datastore.DataStoreManager
import com.example.shoppinglist.datastore.SettingsData
import com.example.shoppinglist.main_screen.MainScreen
import com.example.shoppinglist.new_note_screen.NewNoteScreen
import com.example.shoppinglist.settings_screen.SettingsScreen
import com.example.shoppinglist.shopping_list_screen.ShoppingListScreen
import com.example.shoppinglist.ui.theme.RedLight
import com.example.shoppinglist.ui.theme.White
import com.example.shoppinglist.utils.Routes
import kotlinx.coroutines.delay

@Composable
fun MainNavigationGraph(settings: SettingsData) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.LOADING) {
        composable(Routes.ADD_ITEM + "/{listId}") {
            AddItemScreen()
        }
        composable(Routes.NEW_NOTE + "/{noteId}") {
            NewNoteScreen() {
                navController.popBackStack()
            }
        }
        composable(Routes.MAIN_SCREEN) {
            MainScreen(navController, settings)
        }
        composable(Routes.LOADING) {
            SplashScreen(navController)
        }
    }
}

@SuppressLint("RememberReturnType")
@Composable
fun SplashScreen(
    navController: NavController
) {
    val scale = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.5f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = {
                    OvershootInterpolator(3f).getInterpolation(it)
                }
            )
        )
        delay(3000)
        navController.navigate(Routes.MAIN_SCREEN)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .wrapContentWidth(Alignment.CenterHorizontally)
            .wrapContentHeight(Alignment.CenterVertically)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .scale(scale.value)
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .wrapContentHeight(Alignment.CenterVertically)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(300.dp)
                        .clip(CircleShape)
                        .border(BorderStroke(color = RedLight, width = 3.dp), shape = CircleShape)
                )
            }
            Text(
                text = "Пора за покупками",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator(
                modifier = Modifier.size(48.dp),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}