package com.drednoot.circles.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.drednoot.circles.ui.theme.SmartcalcTheme
import com.drednoot.circles.Model
import com.drednoot.circles.Circle
import com.drednoot.circles.Circles.normalizeCircles
import com.drednoot.circles.Point

class Circles {
    @Composable
    fun Screen() {
        var c1: Circle = remember { Circle(Point(-1.0, 0.0), 0.5) }
        var c2: Circle = remember { Circle(Point(1.0, 0.0), 1.0) }
        var intersections: List<Point> = remember { listOf() }

        val normalizedCircles = normalizeCircles(listOf(c1, c2))

        SmartcalcTheme {
            Surface(modifier = Modifier.fillMaxSize()) {
                Column {
                    CircleCanvas(normalizedCircles, intersections)
                    Text("test")
                }
            }
        }
    }

    @Composable
    fun CircleCanvas(
        circles: List<Circle>,
        intersections: List<Point>,
    ) {
        val strokeColor = MaterialTheme.colorScheme.primary
        Canvas(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.6.toFloat())
        ) {
            val stroke = Stroke(3.0.dp.toPx())
            for (c in circles) {
                drawCircle(
                    strokeColor,
                    radius = (c.r * size.width).toFloat(),
                    center = Offset(
                        (c.p.x * size.width).toFloat(),
                        (c.p.y * size.height).toFloat(),
                    ),
                    style = stroke
                )
            }
        }
    }

}
