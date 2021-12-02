package aoc

import aoc.toStringList

fun main() {
    val input = loadInput()
    println("Part 1: " + part1(input))
    println("Part 2: " + part2(input))
}

fun part1(input: Array<Instruction>): Int {
    var x = 0
    var y = 0
    for (instruction in input) {
        when (instruction.command) {
            "forward" -> x += instruction.value
            "down" -> y += instruction.value
            "up" -> y -= instruction.value
        }
    }
    return x * y
}

fun part2(input: Array<Instruction>): Int {
    var x = 0
    var y = 0
    var aim = 0
    for (instruction in input) {
        when (instruction.command) {
            "forward" -> {
                x += instruction.value
                y += aim * instruction.value
            }
            "down" -> aim += instruction.value
            "up" -> aim -= instruction.value
        }
    }
    return x * y
}

data class Instruction(val command: String, val value: Int)

fun loadInput(): Array<Instruction> {
    return "input_day2.txt".toStringList()
        .map { Instruction(it.split(" ")[0], it.split(" ")[1].toInt()) }
        .toTypedArray()
}