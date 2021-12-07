package aoc

import kotlin.math.abs

fun main() {
    //val input = listOf(16, 1, 2, 0, 4, 2, 7, 1, 2, 14)
    val input = "input_day7.txt".toText().split(",").map { it.toInt() }

    // Brute force
    var minCost = Int.MAX_VALUE
    input.distinct().forEach { i ->
        val curCost = input.sumOf { abs(it - i) }
        minCost = minOf(minCost, curCost)
    }
    println("Part 1: $minCost")

    minCost = Int.MAX_VALUE
    val min = input.minOrNull() ?: 0
    val max = input.maxOrNull() ?: Int.MAX_VALUE
    min.rangeTo(max).forEach { i ->
        val curCost = input.sumOf {
            val n = abs(it - i)
            n * (n + 1) / 2
        }
        minCost = minOf(minCost, curCost)
    }
    println("Part 2: $minCost")
}