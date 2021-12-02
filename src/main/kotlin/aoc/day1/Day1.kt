package aoc.day1

import aoc.toIntArray

fun main() {
    val input = toIntArray("input_day1.txt")
    var count = input.toList()
        .windowed(2)
        .count { it.last() > it.first() }
    println("Part 1: $count")

    count = input.toList()
        .windowed(4)
        .count { it.last() > it.first() }
    println("Part 2: $count")
}
