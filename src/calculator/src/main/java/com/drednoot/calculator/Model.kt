package com.drednoot.calculator

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
    EQ ({ front, _ -> front })
}

data class Memory(
    val bufFront: Double = 0.0,
    val bufBack: Double = 0.0,
    val action: Action = Action.EQ,
    val isActionLast: Boolean = false,
)

object Model {
    fun pressNumber(n: Int, mem: Memory): Memory {
        return if (mem.isActionLast) {
            Memory(
                bufFront = n.toDouble(),
                bufBack = mem.bufFront,
                action = mem.action,
                isActionLast = false,
            )
        } else {
            Memory(
                bufFront = mem.bufFront * 10.0 + n,
                bufBack = mem.bufBack,
                action = mem.action,
                isActionLast = false,
            )
        }
    }

    fun pressAction(action: Action, mem: Memory): Memory {
        return Memory(
            bufFront = mem.action.execute(
                mem.bufFront, mem.bufBack
            ),
            bufBack = mem.bufBack,
            action = action,
            isActionLast = true,
        )
    }
}