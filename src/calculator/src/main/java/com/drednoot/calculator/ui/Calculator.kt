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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.drednoot.calculator.Action
import com.drednoot.calculator.Memory
import com.drednoot.calculator.ui.theme.SmartcalcTheme
import com.drednoot.calculator.Model
import com.drednoot.calculator.R

internal const val DEFAULT_FRONT_BUFFER = "0"

class  Calculator {
    private var mem = Memory.default()
    @Preview(showBackground = true, device = "id:pixel_5")
    @Composable
    fun Screen() {
        SmartcalcTheme {
            val frontBuffer = rememberSaveable { mutableStateOf(DEFAULT_FRONT_BUFFER) }

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Column {
                    Text(frontBuffer.value)
                    Grid(frontBuffer)
                }
            }
        }
    }

    @Composable
    private fun Grid(frontBuf: MutableState<String>) {
        Column {
            Row {
                ActionButton(stringResource(R.string.btn_ac), Action.AC, frontBuf)
                ActionButton(stringResource(R.string.btn_erase), Action.ERASE, frontBuf)
                ActionButton(stringResource(R.string.btn_percent), Action.PERCENT, frontBuf)
                ActionButton(stringResource(R.string.btn_div), Action.DIV, frontBuf)
            }
            Row {
                ActionButton(stringResource(R.string.btn_seven), Action.NUM_7, frontBuf)
                ActionButton(stringResource(R.string.btn_eight), Action.NUM_8, frontBuf)
                ActionButton(stringResource(R.string.btn_nine), Action.NUM_9, frontBuf)
                ActionButton(stringResource(R.string.btn_mul), Action.MUL, frontBuf)
            }
            Row {
                ActionButton(stringResource(R.string.btn_four), Action.NUM_4, frontBuf)
                ActionButton(stringResource(R.string.btn_five), Action.NUM_5, frontBuf)
                ActionButton(stringResource(R.string.btn_six), Action.NUM_6, frontBuf)
                ActionButton(stringResource(R.string.btn_sub), Action.SUB, frontBuf)
            }
            Row {
                ActionButton(stringResource(R.string.btn_one), Action.NUM_1, frontBuf)
                ActionButton(stringResource(R.string.btn_two), Action.NUM_2, frontBuf)
                ActionButton(stringResource(R.string.btn_three), Action.NUM_3, frontBuf)
                ActionButton(stringResource(R.string.btn_add), Action.ADD, frontBuf)
            }
            Row {
                ActionButton(stringResource(R.string.btn_zero), Action.NUM_0, frontBuf)
                ActionButton(stringResource(R.string.btn_dot), Action.DOT, frontBuf)
                ActionButton(stringResource(R.string.btn_eq), Action.EQ, frontBuf)
            }
        }
    }

    @Composable
    private fun ActionButton(text: String, action: Action, frontBuf: MutableState<String>) {
        Button(
            onClick = {
                mem = Model.pressAction(action, mem, frontBuf)
            }
        ) {
            Text(text)
        }
    }
}