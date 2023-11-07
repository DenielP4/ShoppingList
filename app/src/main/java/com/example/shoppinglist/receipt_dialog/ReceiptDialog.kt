package com.example.shoppinglist.receipt_dialog

import android.graphics.Paint.Align
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppinglist.ui.theme.DarkText
import com.example.shoppinglist.ui.theme.GrayLight


@Composable
fun ReceiptDialog(
    dialogController: ReceiptDialogController
) {

    val columnName1Weight = .5f
    val columnName2Weight = .5f

    val column1Weight = .5f
    val column2Weight = .3f
    val column3Weight = .3f

    if (dialogController.openReceiptDialog.value) {
        AlertDialog(
            onDismissRequest = {
                dialogController.onReceiptDialogEvent(ReceiptDialogEvent.OnCancel)
            },
            title = null,
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = dialogController.receipt.receiptName,
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            color = DarkText,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    LazyColumn(
                        Modifier
                            .fillMaxWidth()
                            .padding(2.dp)
                    ) {
                        item {
                            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(
                                    text = "НАИМЕНОВАНИЕ",
                                    textAlign = TextAlign.Center,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .weight(columnName1Weight)
                                        .padding(8.dp)
                                )
                                Text(
                                    text = "СТОИМОСТЬ",
                                    textAlign = TextAlign.Center,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .weight(columnName2Weight)
                                        .padding(8.dp)
                                )
                            }
                        }
                    }
                    Divider(
                        color = Color.Black,
                        thickness = 0.5.dp,
                        modifier = Modifier.padding(
                            start = 10.dp,
                            end = 10.dp
                        )
                    )
                    LazyColumn(
                        Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 16.dp,
                                end = 16.dp
                            )
                    ) {
                        items(dialogController.receipt.listItem!!) { item ->
                            Row() {
                                Text(
                                    text = item.name,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Normal,
                                    modifier = Modifier
                                        .weight(column1Weight)
                                        .padding(8.dp)
                                )
                                Row(
                                    horizontalArrangement = Arrangement.SpaceAround,
                                    modifier = Modifier.weight(column1Weight)
                                ) {
                                    Text(
                                        text = "${item.price} * ${item.count}",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Normal,
                                        modifier = Modifier
                                            .padding(8.dp)
                                    )
                                    Text(
                                        text = "= ${item.sum}",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Normal,
                                        modifier = Modifier
                                            .padding(8.dp)
                                    )
                                }

                            }
                        }
                    }
                    Divider(
                        color = Color.Black,
                        modifier = Modifier.padding(
                            start = 10.dp,
                            end = 10.dp
                        )
                    )
                    Box(
                        modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd
                    ) {
                        Text(
                            text = "ИТОГО К ОПЛАТЕ = ${dialogController.receipt.finalSum}",
                            textAlign = TextAlign.Right,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(8.dp)
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        dialogController.onReceiptDialogEvent(ReceiptDialogEvent.OnConfirm)
                    }
                ) {
                    Text(text = "СОХРАНИТЬ")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        dialogController.onReceiptDialogEvent(ReceiptDialogEvent.OnCancel)
                    }
                ) {
                    Text(text = "НЕ СОХРАНЯТЬ")
                }
            }
        )
    }
}