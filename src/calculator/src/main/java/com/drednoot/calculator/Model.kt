package com.drednoot.calculator

import android.os.Parcelable
import androidx.compose.runtime.MutableState
import kotlinx.parcelize.Parcelize

enum class Action(val execute: (Double, Double) -> Double) {
    ADD ({ front, back ->
        back + front
    }),
    SUB ({ front, back ->
        back - front
    }),
    MUL ({ front, back ->
        back * front
    }),
    DIV ({ front, back ->
        back / front
    }),
    EQ ({ front, _ ->
        front
    }),
    PERCENT ({ front, _ ->
        front
    }),
    ERASE ({ front, _ ->
        front
    }),
    DOT ({ front, _ ->
        front
    }),
    AC ({ front, _ ->
        front
    }),
}

@Parcelize
data class Memory(
    val bufFront: String = "0",
    val bufBack: String = "0",
    val action: Action = Action.EQ,
    val isActionLast: Boolean = false,
) : Parcelable

object Model {
    private fun String.toDoubleZero(): Double {
        return toDoubleOrNull() ?: 0.0
    }

    private fun Double.toStringFmt(): String {
        if (this <= 1e-6) return "0"
        var ret =  String.format("%.6f", this)
        if (ret.contains('.')) {
            ret = ret.trimEnd('0')
            ret = ret.trimEnd('.')
        }
        return ret
    }

    fun pressNumber(n: String, memState: MutableState<Memory>) {
        val mem = memState.value
        memState.value = if (mem.isActionLast) {
            Memory(
                bufFront = n,
                bufBack = mem.bufFront,
                action = mem.action,
                isActionLast = false,
            )
        } else {
            Memory(
                bufFront = if (mem.bufFront == "0") {
                    n
                } else {
                    mem.bufFront + n
                },
                bufBack = mem.bufBack,
                action = mem.action,
                isActionLast = false,
            )
        }
    }

    fun pressAction(action: Action, memState: MutableState<Memory>) {
        val mem = memState.value
        memState.value =  when (action) {
            Action.ERASE -> Memory(
                bufFront = if (mem.bufFront.length == 1) {
                    "0"
                } else if (mem.bufFront.length > 1) {
                    mem.bufFront.substring(0, mem.bufFront.length - 1)
                } else {
                    mem.bufFront
                },
                bufBack = mem.bufBack,
                action = mem.action,
                isActionLast = false,
            )
            Action.DOT -> Memory(
                bufFront = if (!mem.bufFront.contains('.')) {
                    mem.bufFront + '.'
                } else {
                    mem.bufFront
                },
                bufBack = mem.bufBack,
                action = mem.action,
                isActionLast = false,
            )
            Action.AC -> Memory()
            Action.PERCENT -> Memory(
                bufFront = (mem.bufBack.toDoubleZero() * (mem.bufFront.toDoubleZero() / 100.0)).toStringFmt(),
                bufBack = mem.bufBack,
                action = mem.action,
                isActionLast = false,
            )
            Action.EQ -> Memory(
                bufFront = mem.action.execute(
                    mem.bufFront.toDoubleZero(),
                    mem.bufBack.toDoubleZero(),
                ).toStringFmt(),
                bufBack = mem.bufBack,
                action = mem.action,
                isActionLast = true,
            )

            else -> Memory(
                bufFront = mem.action.execute(
                    mem.bufFront.toDoubleZero(),
                    mem.bufBack.toDoubleZero(),
                ).toStringFmt(),
                bufBack = mem.bufFront,
                action = action,
                isActionLast = true,
            )
        }
    }
}