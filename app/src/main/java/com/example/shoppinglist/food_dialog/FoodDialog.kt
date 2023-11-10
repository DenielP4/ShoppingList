package com.example.shoppinglist.food_dialog

import android.graphics.drawable.Icon
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.shoppinglist.R
import com.example.shoppinglist.dialog.DialogController
import com.example.shoppinglist.dialog.DialogEvent
import com.example.shoppinglist.receipt_dialog.ReceiptDialogEvent
import com.example.shoppinglist.ui.theme.DarkText
import com.example.shoppinglist.ui.theme.GrayLight
import com.example.shoppinglist.ui.theme.RedLight


@Composable
fun FoodDialog(
    dialogController: FoodDialogController
) {
    if (!dialogController.openFoodDialog.value) return
    AlertDialog(
        onDismissRequest = {
            dialogController.onFoodDialogEvent(FoodDialogEvent.OnCancel)
        },
        title = null,
        text = {
            ConstraintLayout(
                modifier = Modifier.padding(
                    start = 3.dp, top = 18.dp, end = 3.dp
                )
            ) {
                val (dialog, exit) = createRefs()
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(dialog) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    Text(
                        text = dialogController.dialogTitleFood.value,
                        style = TextStyle(
                            color = DarkText,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CardProduct(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(horizontal = 5.dp)
                                .clickable {
                                    dialogController.onFoodDialogEvent(FoodDialogEvent.OnProductCount)
                                },
                            title = "Штучный",
                            painter = R.drawable.count_product
                        )
                        CardProduct(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(horizontal = 5.dp)
                                .clickable {
                                    dialogController.onFoodDialogEvent(FoodDialogEvent.OnProductWeight)
                                },
                            title = "Весовой",
                            painter = R.drawable.weight_product
                        )
                    }
                }
                IconButton(
                    onClick = {
                        dialogController.onFoodDialogEvent(FoodDialogEvent.OnExit)
                    },
                    modifier = Modifier
                        .constrainAs(exit) {
                            top.linkTo(dialog.top)
                            bottom.linkTo(dialog.top)
                            start.linkTo(dialog.end)
                            end.linkTo(dialog.end)
                        }
                        .size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Exit",
                        modifier = Modifier
                            .padding(5.dp)
                    )
                }
            }
        },
        confirmButton = {},
        dismissButton = {}
    )
}


@Composable
fun CardProduct(
    modifier: Modifier,
    title: String,
    painter: Int
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(2.dp, GrayLight)
    ) {
        Column(
            modifier = Modifier.padding(10.dp).fillMaxWidth()
        ) {
            Image(
                alignment = Alignment.Center,
                painter = painterResource(id = painter),
                contentDescription = title,
                modifier = Modifier.size(150.dp)
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                style = TextStyle(
                    color = DarkText,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center
                )
            )
        }

    }
}