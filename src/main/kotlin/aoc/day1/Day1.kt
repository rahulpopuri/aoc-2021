package aoc.day1

import aoc.toIntList

fun main() {
    val input = "input_day1.txt".toIntList()
    var count = input
        .windowed(2)
        .count { it.last() > it.first() }
    println("Part 1: $count")

    count = input
        .windowed(4)
        .count { it.last() > it.first() }
    println("Part 2: $count")
}
