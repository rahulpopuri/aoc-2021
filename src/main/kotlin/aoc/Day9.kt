package aoc

fun main() {
    val input = loadInput()
    val visited = array2dOfBoolean(input.size, input[0].size)
    solve(input, visited)
}

private fun solve(input: Array<Array<Int>>, visited: Array<BooleanArray>) {
    val lowPoints = mutableListOf<Pair<Int, Int>>()
    while (hasUnvisitedPoints(visited)) {
        findNextUnvisitedPoint(visited)?.let { it ->
            findNextLowPoint(input, visited, it.first, it.second)?.let { i ->
                println("Found low point: [${i.first}][${i.second}]: ${input[i.first][i.second]}")
                lowPoints.add(i)
            }
        }
    }
    println("Part 1: " + lowPoints.sumOf { input[it.first][it.second] + 1 })

    /*
    1. Mark low points in a boolean array
    2. Find basin sizes, set each visited point to false?
    3. Once no more left, solve
     */
}

private fun hasUnvisitedPoints(visited: Array<BooleanArray>): Boolean {
    for (i in visited.indices) {
        for (j in visited[i].indices) {
            if (!visited[i][j]) {
                return true
            }
        }
    }
    return false
}

private fun findNextUnvisitedPoint(visited: Array<BooleanArray>): Pair<Int, Int>? {
    visited.indices.forEach { i ->
        visited[i].indices.forEach { j ->
            if (!visited[i][j]) {
                return Pair(i, j)
            }
        }
    }
    return null
}

private fun findNextLowPoint(input: Array<Array<Int>>, visited: Array<BooleanArray>, i: Int, j: Int): Pair<Int, Int>? {
    if (visited[i][j]) {
        return null
    }
    // println("Visiting [$i][$j]")
    visited[i][j] = true
    val adjacentUnvisitedPoints = getAdjacentUnvisitedPoints(input, i, j)
    if (adjacentUnvisitedPoints.isEmpty()) {
        return null
    } else {
        var minPair = Pair(-1, -1)
        var min = Int.MAX_VALUE
        for (pair in adjacentUnvisitedPoints) {
            if (input[pair.first][pair.second] < min) {
                min = input[pair.first][pair.second]
                minPair = pair
            }
        }
        return if (min > input[i][j]) {
            Pair(i, j)
        } else {
            findNextLowPoint(input, visited, minPair.first, minPair.second)
        }
    }
}

private fun getAdjacentUnvisitedPoints(
    input: Array<Array<Int>>,
    i: Int,
    j: Int
): List<Pair<Int, Int>> {
    val result = mutableListOf<Pair<Int, Int>>()
    if (i > 0) {
        result.add(Pair(i - 1, j))
    }
    if (i < input.size - 1) {
        result.add(Pair(i + 1, j))
    }
    if (j > 0) {
        result.add(Pair(i, j - 1))
    }
    if (j < input[0].size - 1) {
        result.add(Pair(i, j + 1))
    }
    return result
}

private fun loadInput(): Array<Array<Int>> {
    return "input_day9.txt".toStringList()
        .map { it.toCharArray().map { i -> i.toString().toInt() }.toTypedArray() }
        .toTypedArray()
}