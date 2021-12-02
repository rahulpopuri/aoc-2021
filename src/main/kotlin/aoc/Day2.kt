package aoc

fun main() {
    val input = loadInput()
    println("Part 1: " + part1(input))
    println("Part 2: " + part2(input))
}

fun part1(input: List<Instruction>): Int {
    var x = 0
    var y = 0
    input.forEach { instruction ->
        when (instruction.command) {
            "forward" -> x += instruction.value
            "down" -> y += instruction.value
            "up" -> y -= instruction.value
        }
    }
    return x * y
}

fun part2(input: List<Instruction>): Int {
    var x = 0
    var y = 0
    var aim = 0
    input.forEach { instruction ->
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

fun loadInput(): List<Instruction> {
    return "input_day2.txt".toStringList()
        .map { val split = it.split(" "); Instruction(split[0], split[1].toInt()) }
        .toList()
}