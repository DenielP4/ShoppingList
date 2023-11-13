package com.example.shoppinglist.receipt_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.shoppinglist.R
import com.example.shoppinglist.data.ReceiptListItem
import com.example.shoppinglist.ui.theme.DarkText
import com.example.shoppinglist.ui.theme.DeleteColor
import com.example.shoppinglist.ui.theme.LightText

@Composable
fun UiReceiptItem(
    item: ReceiptListItem,
    onEvent: (ReceiptEvent) -> Unit
) {

    ConstraintLayout(
        modifier = Modifier.padding(
            start = 3.dp, top = 18.dp, end = 3.dp
        )
    ) {
        val (card, deleteButton) = createRefs()
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(card) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .clickable {
                    onEvent(ReceiptEvent.OnItemClick(item))
                }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = item.receiptName,
                    style = TextStyle(
                        color = DarkText,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box() {
                        Row {
                            Icon(
                                modifier = Modifier
                                    .size(20.dp),
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "Календарь",
                                tint = Color.Gray
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = item.receiptTime,
                                style = TextStyle(
                                    color = LightText,
                                    fontSize = 15.sp
                                )
                            )
                        }
                    }
                    Text(
                        text = "Итог: ${item.finalSum}",
                        style = TextStyle(
                            color = LightText,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

            }
        }
        IconButton(
            onClick = {
                onEvent(ReceiptEvent.OnDelete(item))
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
    }
}
