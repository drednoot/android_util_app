package com.drednoot.calculator

import android.os.Parcelable
import androidx.compose.runtime.MutableState
import kotlinx.parcelize.Parcelize

enum class Action {
    NOTHING,
    ADD,
    SUB,
    MUL,
    DIV,
    EQ,
    PERCENT,
    NUM_0,
    NUM_1,
    NUM_2,
    NUM_3,
    NUM_4,
    NUM_5,
    NUM_6,
    NUM_7,
    NUM_8,
    NUM_9,
    DOT,
    AC,
    ERASE;

    fun execute(x: Double, y: Double): Double? {
        return when (this) {
            NOTHING -> y
            ADD -> x + y
            SUB -> x - y
            MUL -> x * y
            DIV -> x / y
            else -> null
        }
    }

    fun executeSpecial(f: Double, m: Double = 0.0): Double? {
        return when (this) {
            ADD -> m + f
            SUB -> m - f
            MUL -> f * f
            DIV -> 1 / f
            else -> null
        }
    }
}

@Parcelize
data class Memory(
    val isRefreshing: Boolean,
    val action: Action,
    val actionMemory: Action,
    val back: String,
    val backMemory: String,
) : Parcelable {
    companion object {
        fun default(): Memory {
            return Memory(
                false,
                Action.NOTHING,
                Action.NOTHING,
                "0",
                "0",
            )
        }
    }
}

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

    private fun execute(x: String, y: String, operation: Action): String {
        return operation.execute(
            x.toDoubleZero(),
            y.toDoubleZero(),
        )?.toStringFmt() ?: "0"
    }

    private fun Memory.executeBackActionFront(front: String): String {
        return execute(back, front, action)
    }

    private fun Memory.executeMemActionMemFront(): String {
        return execute(back, backMemory, actionMemory)
    }

    private fun Memory.executeMemActionFront(front: String): String {
        return execute(backMemory, front, action)
    }

    private fun Memory.executeSpecial(front: String): String {
        return action.executeSpecial(
            back.toDoubleZero(),
            front.toDoubleZero(),
        )?.toStringFmt() ?: "0"
    }

    fun pressAction(action: Action, mem: Memory, front: MutableState<String>): Memory {
        return when(action) {
            Action.NOTHING -> mem
            in Action.NUM_0..Action.DOT -> pressAppendable(action, mem, front)
            Action.AC -> {
                front.value = "0"
                Memory.default()
            }
            Action.ERASE -> {
                front.value = if (front.value.length > 1) {
                    front.value.substring(0, front.value.length - 1)
                } else {
                    "0"
                }
                mem
            }
            in Action.ADD..Action.DIV -> pressOperator(action, mem, front)
            Action.EQ -> pressEq(mem, front)
            else -> mem
        }
    }

    private fun pressAppendable(action: Action, mem: Memory, front: MutableState<String>): Memory {
        if (action in Action.NUM_0..Action.NUM_9) {
            val value = when (action) {
                Action.NUM_0 -> "0"
                Action.NUM_1 -> "1"
                Action.NUM_2 -> "2"
                Action.NUM_3 -> "3"
                Action.NUM_4 -> "4"
                Action.NUM_5 -> "5"
                Action.NUM_6 -> "6"
                Action.NUM_7 -> "7"
                Action.NUM_8 -> "8"
                Action.NUM_9 -> "9"
                else -> ""
            }
            if (mem.isRefreshing || front.value == "0") {
                front.value = value
            } else {
                front.value += value
            }
        } else if (action == Action.DOT) {
            if (mem.isRefreshing) {
                front.value = "0."
            } else if (!front.value.contains('.')) {
                front.value += '.'
            }
        }
        return Memory(
            false,
            mem.action,
            mem.actionMemory,
            mem.back,
            mem.backMemory,
        )
    }

    private fun pressOperator(action: Action, mem: Memory, front: MutableState<String>): Memory {
        return if (mem.isRefreshing) {
            Memory(
                true,
                action,
                mem.actionMemory,
                mem.back,
                mem.backMemory,
            )
        } else {
            val execValue = mem.executeBackActionFront(front.value)
            val oldFront = front.value
            front.value = execValue
            Memory(
                true,
                action,
                Action.NOTHING,
                execValue,
                oldFront,
            )
        }
    }

    private fun pressEq(mem: Memory, front: MutableState<String>): Memory {
        return if (mem.actionMemory != Action.NOTHING) {
            front.value = mem.executeMemActionMemFront()
            Memory(
                true,
                Action.NOTHING,
                mem.actionMemory,
                "0",
                mem.backMemory,
            )
        } else if (mem.isRefreshing) {
            val oldFront = front.value
            front.value = mem.executeSpecial(front.value)
            Memory(
                true,
                Action.NOTHING,
                mem.action,
                "0",
                oldFront,
            )
        } else {
            val oldFront = front.value
            front.value = mem.executeMemActionFront(front.value)
            Memory(
                true,
                Action.NOTHING,
                mem.action,
                "0",
                oldFront,
            )
        }
    }
}