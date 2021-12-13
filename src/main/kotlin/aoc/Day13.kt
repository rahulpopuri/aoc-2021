package aoc

fun main() {
    val folds = loadInput()
    println("Part 1: ${folds.part1()}")
    folds.part2()
}

private data class Folds(val points: List<Pair<Int, Int>>, val instructions: List<FoldInstruction>) {
    private var world: Array<IntArray>

    init {
        val xMax = points.maxOf { it.first }
        val yMax = points.maxOf { it.second }
        world = array2dOfInt(yMax + 1, xMax + 1)
        for (point in points) {
            world[point.second][point.first] = 1
        }
    }

    fun part1(): Int {
        var data = world.clone()
        val instruction = instructions.first()
        data = if (instruction.direction == "x") {
            foldLeft(data, instruction.value)
        } else {
            foldUp(data, instruction.value)
        }
        return data.sumOf { row -> row.count { it == 1 } }
    }

    fun part2() {
        var data = world.clone()
        for (instruction in instructions) {
            data = if (instruction.direction == "x") {
                foldLeft(data, instruction.value)
            } else {
                foldUp(data, instruction.value)
            }
        }
        data.print()
    }

    private fun foldLeft(data: Array<IntArray>, num: Int): Array<IntArray> {
        val tmp = array2dOfInt(data.size, num)
        for (i in data.indices) {
            for (j in 0 until num) {
                tmp[i][j] = data[i][j]
                if (data[i][data[i].size - j - 1] == 1) {
                    tmp[i][j] = 1
                }
            }
        }
        return tmp
    }

    private fun foldUp(data: Array<IntArray>, num: Int): Array<IntArray> {
        val tmp = array2dOfInt(num, data[0].size)
        for (i in 0 until num) {
            tmp[i] = data[i]
            for (j in data[data.size - i - 1].indices) {
                if (data[data.size - i - 1][j] == 1) {
                    tmp[i][j] = 1
                }
            }
        }
        return tmp
    }
}

private data class FoldInstruction(val direction: String, val value: Int)

private fun loadInput(): Folds {
    val split = "input_day13.txt".toText().split("\n\n")
    val points = split[0]
        .split("\n")
        .map { it.split(",") }
        .map { p -> Pair(p[0].toInt(), p[1].toInt()) }
    val regex = "fold along ([a-z])=(\\d+)".toRegex()
    val instructions = split[1].split("\n")
        .map {
            regex.matchEntire(it)
                ?.destructured
                ?.let { (d, n) ->
                    FoldInstruction(d, n.toInt())
                }
                ?: throw IllegalArgumentException("Bad input '$it'")
        }
    return Folds(points, instructions)
}