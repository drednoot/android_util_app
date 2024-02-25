package com.drednoot.calculator.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.drednoot.calculator.Action
import com.drednoot.calculator.Memory
import com.drednoot.calculator.ui.theme.SmartcalcTheme
import com.drednoot.calculator.Model
import com.drednoot.calculator.R

@Preview(showBackground = true, device = "id:pixel_5")
@Composable
fun Calculator() {
    SmartcalcTheme {
        val memory = rememberSaveable { mutableStateOf(Memory()) }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                Text(memory.value.bufFront)
                Grid(memory)
            }
        }
    }
}

@Composable
fun Grid(mem: MutableState<Memory>) {
    Column {
        Row {
            ActionButton(text = stringResource(id = R.string.btn_ac), action = Action.AC, memory = mem)
            ActionButton(text = stringResource(id = R.string.btn_erase), action = Action.ERASE, memory = mem)
            ActionButton(text = stringResource(id = R.string.btn_percent), action = Action.PERCENT, memory = mem)
            ActionButton(text = stringResource(id = R.string.btn_div), action = Action.DIV, memory = mem)
        }
        Row {
            NumberButton(text = stringResource(id = R.string.btn_seven), num = "7", memory = mem)
            NumberButton(text = stringResource(id = R.string.btn_eight), num = "8", memory = mem)
            NumberButton(text = stringResource(id = R.string.btn_nine), num = "9", memory = mem)
            ActionButton(text = stringResource(id = R.string.btn_mul), action = Action.MUL, memory = mem)
        }
        Row {
            NumberButton(text = stringResource(id = R.string.btn_four), num = "4", memory = mem)
            NumberButton(text = stringResource(id = R.string.btn_five), num = "5", memory = mem)
            NumberButton(text = stringResource(id = R.string.btn_six), num = "6", memory = mem)
            ActionButton(text = stringResource(id = R.string.btn_sub), action = Action.SUB, memory = mem)
        }
        Row {
            NumberButton(text = stringResource(id = R.string.btn_one), num = "1", memory = mem)
            NumberButton(text = stringResource(id = R.string.btn_two), num = "2", memory = mem)
            NumberButton(text = stringResource(id = R.string.btn_three), num = "3", memory = mem)
            ActionButton(text = stringResource(id = R.string.btn_add), action = Action.ADD, memory = mem)
        }
        Row {
            NumberButton(text = stringResource(id = R.string.btn_zero), num = "0", memory = mem)
            ActionButton(text = stringResource(id = R.string.btn_dot), action = Action.DOT, memory = mem)
            ActionButton(text = stringResource(id = R.string.btn_eq), action = Action.EQ, memory = mem)
        }
    }
}

@Composable
fun GridButton(text: String, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text(text)
    }
}

@Composable
fun ActionButton(text: String, action: Action, memory: MutableState<Memory>) {
    GridButton(text = text) {
        Model.pressAction(action, memory)
    }
}

@Composable
fun NumberButton(text: String, num: String, memory: MutableState<Memory>) {
    GridButton(text = text) {
        Model.pressNumber(num, memory)
    }
}