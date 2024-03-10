package com.drednoot.circles

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

enum class CircleRelation {
    COINCIDE,
    NOT_INTERSECT,
    TANGENT,
    INTERSECT,
    CONTAIN,
}

data class CircleIntersection(
    val relation: CircleRelation,
    val intersections: List<Point>
)

data class Point(
    val x: Double,
    val y: Double,
)

data class Circle(
    val p: Point,
    val r: Double,
) {
    companion object {
        fun distance(c1: Circle, c2: Circle): Double {
            return sqrt((c2.p.x - c1.p.x)*(c2.p.x - c1.p.x) + (c2.p.y - c1.p.y)*(c2.p.y - c1.p.y))
        }
    }
}

object Circles {
    fun findRelation(
        c1: Circle,
        c2: Circle,
    ): CircleIntersection {
        val distance = Circle.distance(c1, c2)

        return if (distance == 0.0 && c1.r == c2.r) {
            CircleIntersection(CircleRelation.COINCIDE, listOf())
        } else if (distance > c1.r + c2.r) {
            CircleIntersection(CircleRelation.NOT_INTERSECT, listOf())
        } else if (distance == c1.r + c2.r) {
            CircleIntersection(CircleRelation.TANGENT, listOf(intersectionPoints(c1, c2).first))
        } else if (abs(c1.r - c2.r) < distance && distance < c1.r + c2.r) {
            CircleIntersection(CircleRelation.INTERSECT, intersectionPoints(c1, c2).toList())
        } else {
            CircleIntersection(CircleRelation.CONTAIN, listOf())
        }
    }

    private fun intersectionPoints(
        c1: Circle,
        c2: Circle,
    ): Pair<Point, Point> {
        val x1 = c1.p.x
        val y1 = c1.p.y
        val x2 = c2.p.x
        val y2 = c2.p.y
        val r1 = c1.r
        val r2 = c2.r
        val d = Circle.distance(c1, c2)

        val l = (r1 * r1 - r2 * r2 + d * d) / (2.0 * d)
        val h = sqrt(r1 * r1 - l * l)
        val ix1 = l / d * (x2 - x1) + h / d * (y2 - y1) + x1
        val ix2 = l / d * (x2 - x1) - h / d * (y2 - y1) + x1
        val iy1 = l / d * (y2 - y1) - h / d * (x2 - x1) + y1
        val iy2 = l / d * (y2 - y1) + h / d * (x2 - x1) + y1
        return Pair(Point(ix1, iy1), Point(ix2, iy2))
    }

    fun normalizeCircles(circles: List<Circle>): List<Circle> {
        val min = circles.fold(Circle(Point(0.0, 0.0), 0.0)) { acc, c ->
            Circle(Point(min(acc.p.x, c.p.x - c.r), min(acc.p.y, c.p.y - c.r)), 0.0)
        }
        val max = circles.fold(Circle(Point(0.0, 0.0), 0.0)) { acc, c ->
            Circle(Point(max(acc.p.x, c.p.x + c.r), max(acc.p.y, c.p.y + c.r)), 0.0)
        }
        val range = Point(max.p.x - min.p.x, max.p.y - min.p.y)
        val scaleFactor = if (range.x > range.y) range.x else range.y

        return circles.map {
            Circle(
                Point(
                    (it.p.x - min.p.x) / scaleFactor,
                    (it.p.y - min.p.y) / scaleFactor,
                ),
                it.r / scaleFactor,
            )
        }
    }
}

class Model {
}