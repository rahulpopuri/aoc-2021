package aoc

import kotlin.math.max
import kotlin.math.min

fun main() {
    val vents = loadInput()
    val size = vents.map { it.start }.maxOf { it.first }
    val world1 = array2dOfInt(size + 1, size + 1)
    val world2 = array2dOfInt(size + 1, size + 1)
    for (vent in vents) {
        vent.apply(world1, false)
        vent.apply(world2, true)
    }
    val sum1 = world1.sumOf { row ->
        row.count { it > 1 }
    }
    val sum2 = world2.sumOf { row ->
        row.count { it > 1 }
    }
    println("Part 1 score: $sum1")
    println("Part 2 score: $sum2")
}

private data class Vent(val start: Pair<Int, Int>, val end: Pair<Int, Int>) {
    private fun slope(): Float {
        if (start.first == end.first) return Float.MAX_VALUE
        return (end.second - start.second).toFloat() / (end.first - start.first)
    }

    private fun isVertical(): Boolean {
        return start.first == end.first
    }

    private fun isHorizontal(): Boolean {
        return start.second == end.second
    }

    fun apply(map: Array<IntArray>, includeDiagonals: Boolean) {
        if (isVertical()) {
            var i = min(start.second, end.second)
            while (i <= max(start.second, end.second)) {
                map[i][start.first]++
                i++
            }
        } else if (isHorizontal()) {
            var i = min(start.first, end.first)
            while (i <= max(start.first, end.first)) {
                map[start.second][i]++
                i++
            }
        } else if (includeDiagonals) {
            val a: Pair<Int, Int>
            val b: Pair<Int, Int>
            if (start.first < end.first) {
                a = start
                b = end
            } else {
                a = end
                b = start
            }
            var i = a.first
            var j = a.second
            val slope = slope()
            while (i != b.first || j != b.second) {
                map[j][i]++
                i++
                if (slope > 0) {
                    j++
                } else {
                    j--
                }
            }
            map[j][i]++
        }
    }
}

private fun loadInput(): List<Vent> {
    val regex = "(\\d+),(\\d+) -> (\\d+),(\\d+)".toRegex()
    val result = mutableListOf<Vent>()
    "input_day5.txt".toStringList()
        .forEach { line ->
            run {
                regex.matchEntire(line)
                    ?.destructured
                    ?.let { (x1, y1, x2, y2) ->
                        result.add(Vent(Pair(x1.toInt(), y1.toInt()), Pair(x2.toInt(), y2.toInt())))
                    }
                    ?: throw IllegalArgumentException("Bad input '$line'")
            }
        }
    return result
}