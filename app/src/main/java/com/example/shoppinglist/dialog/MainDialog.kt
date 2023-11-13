package com.example.shoppinglist.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Checkbox
import androidx.compose.material.RadioButton
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppinglist.add_item_screen.AddItemEvent
import com.example.shoppinglist.add_item_screen.AddItemViewModel
import com.example.shoppinglist.ui.theme.DarkText
import com.example.shoppinglist.ui.theme.GrayLight

@Composable
fun MainDialog(
    dialogController: DialogController
) {
    if (dialogController.openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                dialogController.onDialogEvent(DialogEvent.OnCancel)
            },
            title = null,
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = dialogController.dialogTitle.value,
                        style = TextStyle(
                            color = DarkText,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    )
                    if (dialogController.showEditableText.value) {
                        TextFieldInput(
                            value = dialogController.editableText.value,
                            label = "Название списка",
                            keyboardOptions = false,
                            onEvent = { text ->
                                dialogController.onDialogEvent(DialogEvent.OnTextChange(text))
                            }
                        )
                    }
                    if (dialogController.showPriceNumber.value) {
                        TextFieldInput(
                            value = dialogController.priceNumber.value,
                            label = if (dialogController.showGramNumber.value) "Цена за вес" else "Цена",
                            keyboardOptions = true,
                            onEvent = { price ->
                                dialogController.onDialogEvent(DialogEvent.OnPriceChange(price))
                            }
                        )
                    }
                    if (dialogController.showGramNumber.value) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TextField(
                                modifier = Modifier
                                    .weight(1f),
                                value = dialogController.gramNumber.value,
                                onValueChange = { gram ->
                                    dialogController.onDialogEvent(DialogEvent.OnGramChange(gram))
                                },
                                label = {
                                    Text(text = "Вес")
                                },
                                singleLine = true,
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = GrayLight,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent
                                ),
                                shape = RoundedCornerShape(9.dp),
                                textStyle = TextStyle(
                                    color = DarkText,
                                    fontSize = 16.sp
                                ),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                Column(
                                    modifier = Modifier
                                        .weight(1f),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = "ГР",
                                        style = TextStyle(
                                            fontWeight = FontWeight.Bold
                                        ),
                                        textAlign = TextAlign.Center
                                    )
                                    RadioButton(
                                        modifier = Modifier.size(25.dp),
                                        selected = dialogController.gramWeight.value,
                                        onClick = {
                                            dialogController.onDialogEvent(DialogEvent.OnGramRadioBoxChange)
                                        }
                                    )
                                }
                                Column(
                                    modifier = Modifier
                                        .weight(1f),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = "КГ",
                                        style = TextStyle(
                                            fontWeight = FontWeight.Bold
                                        ),
                                        textAlign = TextAlign.Center
                                    )
                                    RadioButton(
                                        modifier = Modifier.size(25.dp),
                                        selected = dialogController.kiloWeight.value,
                                        onClick = {
                                            dialogController.onDialogEvent(DialogEvent.OnKiloRadioBoxChange)
                                        }
                                    )
                                }
                            }
                        }
                    }
                    if (dialogController.showWeightNumber.value) {
                        TextFieldInput(
                            value = dialogController.weightNumber.value,
                            label = "Вес на упаковке",
                            keyboardOptions = true,
                            onEvent = { weight ->
                                dialogController.onDialogEvent(DialogEvent.OnWeightChange(weight))
                            }
                        )
                    }
                    if (dialogController.showBudgetNumber.value) {
                        TextFieldInput(
                            value = dialogController.budgetNumber.value,
                            label = "Бюджет",
                            keyboardOptions = true,
                            onEvent = { budget ->
                                dialogController.onDialogEvent(DialogEvent.OnBudgetChange(budget))
                            }
                        )
                    }
                    if (dialogController.showFoodCheckBox.value) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.padding(start = 10.dp),
                                text = "Будут продукты",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Checkbox(
                                checked = dialogController.foodCheckBox.value,
                                onCheckedChange = { isChecked ->
                                    dialogController.onDialogEvent(
                                        DialogEvent.OnCheckedChange(
                                            isChecked
                                        )
                                    )
                                }
                            )
                        }
                    }
                    if (dialogController.showCountNumber.value) {
                        TextFieldInput(
                            value = dialogController.countNumber.value,
                            label = "Количество",
                            keyboardOptions = true,
                            onEvent = { count ->
                                dialogController.onDialogEvent(DialogEvent.OnCountChange(count))
                            }
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        dialogController.onDialogEvent(DialogEvent.OnConfirm)
                    }
                ) {
                    Text(text = "OKAY")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        dialogController.onDialogEvent(DialogEvent.OnCancel)
                    }
                ) {
                    Text(text = "CANCEL")
                }
            }
        )
    }
}

@Composable
fun TextFieldInput(
    value: String,
    label: String,
    keyboardOptions: Boolean,
    onEvent: (String) -> Unit
) {
    Spacer(modifier = Modifier.height(10.dp))
    Row {
        TextField(
            value = value,
            onValueChange = onEvent,
            label = {
                Text(text = label)
            },
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = GrayLight,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(9.dp),
            textStyle = TextStyle(
                color = DarkText,
                fontSize = 16.sp
            ),
            keyboardOptions = if (keyboardOptions) KeyboardOptions(keyboardType = KeyboardType.Number) else KeyboardOptions(
                keyboardType = KeyboardType.Text
            )
        )
    }

}