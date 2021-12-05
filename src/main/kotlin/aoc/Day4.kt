package aoc

fun main() {
    val bingo = loadData()

    val part1Score = bingo.part1()
    println("Score: $part1Score")

    val part2Score = bingo.part2()
    println("Score: $part2Score")
}

private data class Point(val value: Int, var marked: Boolean)
private data class Board(val id: Int, var data: List<List<Point>>) {
    private var bingo = false

    fun isBingo(): Boolean {
        return bingo
    }

    fun calculateScore(lastNum: Int): Int {
        val unmarkedSum = data.sumOf { points ->
            points.filter { !it.marked }
                .sumOf { it.value }
        }
        return unmarkedSum * lastNum
    }

    fun play(number: Int) {
        data.forEach { points ->
            points.filter { number == it.value }
                .forEach { it.marked = true }
        }
        calculateBingo()
    }

    private fun calculateBingo() {
        // Check rows
        for (row in data) {
            var win = true
            for (point in row) {
                if (!point.marked) {
                    win = false
                    break
                }
            }
            if (win) {
                bingo = true
                return
            }
        }

        // Check cols
        for (j in data[0].indices) {
            var win = true
            for (i in data.indices) {
                if (!data[i][j].marked) {
                    win = false
                    break
                }
            }
            if (win) {
                bingo = true
                return
            }
        }
        bingo = false
    }
}

private data class Bingo(val numbers: List<Int>, var boards: List<Board>) {
    // Find first winning board
    fun part1(): Int? {
        println("Part 1")
        numbers.forEach { number ->
            boards.forEach { board ->
                board.play(number)
                if (board.isBingo()) {
                    println("Board " + board.id + " won!")
                    return board.calculateScore(number)
                }
            }
        }
        println("No winning boards")
        return null
    }

    // Find last winning board
    fun part2(): Int? {
        println("Part 2")
        numbers.forEach { number ->
            var lastBoard: Board? = null
            boards.forEach { board ->
                if (!board.isBingo()) {
                    board.play(number)
                    lastBoard = board
                }
            }
            if (boards.all { it.isBingo() }) {
                println("Last board to win: " + lastBoard?.id)
                return lastBoard?.calculateScore(number)
            }
        }
        println("No winning boards")
        return null
    }
}

private fun loadData(): Bingo {
    val text = "input_day4.txt".toText()
    val sections = text.split("\n\n")
    val numbers = sections[0].split(",").map { it.toInt() }
    val data = sections.drop(1).mapIndexed { index, section ->
        Board(index, section.split("\n")
            .map { row ->
                row.split(" ")
                    .filter { it.isNotBlank() }
                    .map { it.toInt() }
                    .map { Point(it, false) }
            })
    }
    return Bingo(numbers, data)
}