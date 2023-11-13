package com.example.shoppinglist.shopping_list_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.shoppinglist.R
import com.example.shoppinglist.data.ShoppingListItem
import com.example.shoppinglist.settings_screen.ColorUtils
import com.example.shoppinglist.ui.theme.DarkText
import com.example.shoppinglist.ui.theme.DeleteColor
import com.example.shoppinglist.ui.theme.EditColor
import com.example.shoppinglist.ui.theme.GrayLight
import com.example.shoppinglist.ui.theme.LightText
import com.example.shoppinglist.ui.theme.RedLight
import com.example.shoppinglist.utils.ProgressHelper
import com.example.shoppinglist.utils.Routes

@Composable
fun UiShoppingListItem(
    item: ShoppingListItem,
    onEvent: (ShoppingListEvent) -> Unit
) {

    var progress = ProgressHelper.getProgress(item.allItemsCount, item.selectedItemsCount)

    ConstraintLayout(
        modifier = Modifier.padding(
            start = 3.dp, top = 18.dp, end = 3.dp
        )
    ) {
        val (card, deleteButton, editButton, counter) = createRefs()
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(card) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .clickable {
                    onEvent(
                        ShoppingListEvent.OnItemClick(
                            Routes.ADD_ITEM + "/${item.id}"
                        )
                    )
                }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.name,
                        style = TextStyle(
                            color = DarkText,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    if (item.food) {
                        Icon(
                            modifier = Modifier
                                .size(25.dp),
                            painter = painterResource(id = R.drawable.shopping_basket_icon),
                            contentDescription = "Корзина",
                            tint = LightText
                        )
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .size(20.dp),
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Календарь",
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = item.time,
                        style = TextStyle(
                            color = LightText,
                            fontSize = 15.sp
                        )
                    )
                }
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp),
                    progress = progress,
                    color = ColorUtils.getProgressColor(progress)
                )
            }
        }
        IconButton(
            onClick = {
                onEvent(ShoppingListEvent.OnShowDeleteDialog(item))
            },
            modifier = Modifier
                .constrainAs(deleteButton) {
                    top.linkTo(card.top)
                    bottom.linkTo(card.top)
                    end.linkTo(card.end)
                }
                .padding(end = 5.dp)
                .size(30.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.delete_icon),
                contentDescription = "Delete",
                modifier = Modifier
                    .clip(CircleShape)
                    .background(DeleteColor)
                    .padding(5.dp)
            )
        }
        IconButton(
            onClick = {
                onEvent(ShoppingListEvent.OnShowEditDialog(item))
            },
            modifier = Modifier
                .constrainAs(editButton) {
                    top.linkTo(card.top)
                    bottom.linkTo(card.top)
                    end.linkTo(deleteButton.start)
                }
                .padding(end = 5.dp)
                .size(30.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.edit_icon),
                contentDescription = "Edit",
                modifier = Modifier
                    .clip(CircleShape)
                    .background(EditColor)
                    .padding(5.dp)
            )
        }
        Card(
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier
                .constrainAs(counter) {
                    top.linkTo(card.top)
                    bottom.linkTo(card.top)
                    end.linkTo(editButton.start)
                }
                .padding(end = 5.dp),
            backgroundColor = Color.Yellow
        ) {
            Text(
                text = "${item.allItemsCount}/${item.selectedItemsCount}",
                modifier = Modifier
                    .padding(
                        top = 3.dp,
                        bottom = 3.dp,
                        start = 5.dp,
                        end = 5.dp
                    ),
                color = Color.Black
            )
        }
    }
}
