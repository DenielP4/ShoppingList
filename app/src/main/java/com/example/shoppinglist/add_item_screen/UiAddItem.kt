package com.example.shoppinglist.add_item_screen


import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppinglist.R
import com.example.shoppinglist.data.AddItem

@Composable
fun UiAddItem(
    item: AddItem,
    onEvent: (AddItemEvent) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 3.dp)
            .clickable {
                onEvent(AddItemEvent.OnShowEditDialog(item))
            }
            .drawWithContent {
                drawContent()
                drawRect(
                    color = if (item.priority) Color.Red else Color.Gray,
                    topLeft = Offset(0f, size.height - 4.dp.toPx()),
                    size = Size(size.width, 4.dp.toPx())
                )
            }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp),
                text = item.name,
                fontSize = 12.sp,
            )
            if (item.sum > 0){
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp),
                    text = item.count.toString(),
                    fontSize = 12.sp,
                )
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp),
                    text = item.price.toString(),
                    fontSize = 12.sp,
                )
            }
            Checkbox(
                checked = item.isCheck,
                onCheckedChange = { isChecked ->
                    onEvent(AddItemEvent.OnCheckedChange(item.copy(isCheck = isChecked)))
                }
            )
            IconButton(
                onClick = {
                    onEvent(AddItemEvent.OnDelete(item))
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.delete_icon),
                    contentDescription = "Delete"
                )
            }
        }
    }
}