package aoc

fun main() {
    val input = "input_day3.txt".to2dCharArray()
    part1(input)
    part2(input)
}

fun part1(input: Array<Array<Char>>) {
    val zeros = IntArray(input[0].size)
    val ones = IntArray(input[0].size)

    input.indices.forEach { i ->
        for (j in 0 until input[i].size) {
            if (input[i][j] == '0') {
                zeros[j]++
            } else {
                ones[j]++
            }
        }
    }
    val gammaData = CharArray(input[0].size)
    val epsilonData = CharArray(input[0].size)
    for (i in zeros.indices) {
        if (zeros[i] > ones[i]) {
            gammaData[i] = '0'
            epsilonData[i] = '1'
        } else {
            gammaData[i] = '1'
            epsilonData[i] = '0'
        }
    }
    val gamma = String(gammaData).toInt(2)
    val epsilon = String(epsilonData).toInt(2)
    println("Part 1: " + gamma * epsilon)
}

fun part2(input: Array<Array<Char>>) {
    // Oxygen generator -> most common
    var data = input.copy()
    for (i in input.indices) {
        val counts = Counts(data)
        data = counts.mostCommon(i)
        if (data.size == 1) break
    }
    val o2Rating = String(data[0].toCharArray()).toInt(2)

    // CO2 scrubber -> least common
    data = input.copy()
    for (i in input.indices) {
        val counts = Counts(data)
        data = counts.leastCommon(i)
        if (data.size == 1) break
    }
    val co2Scrubber = String(data[0].toCharArray()).toInt(2)
    println("Part 2: " + o2Rating * co2Scrubber)
}

private data class Counts(val input: Array<Array<Char>>) {
    private var zeros = 0
    private var ones = 0
    private var zeroArray = mutableListOf<Array<Char>>()
    private var oneArray = mutableListOf<Array<Char>>()

    private fun parse(index: Int) {
        for (i in input.indices) {
            if (input[i][index] == '0') {
                zeros++
                zeroArray.add(input[i])
            } else {
                ones++
                oneArray.add(input[i])
            }
        }
    }

    fun mostCommon(index: Int): Array<Array<Char>> {
        parse(index)
        return if (zeros > ones) zeroArray.toTypedArray() else oneArray.toTypedArray()
    }

    fun leastCommon(index: Int): Array<Array<Char>> {
        parse(index)
        return if (ones < zeros) oneArray.toTypedArray() else zeroArray.toTypedArray()
    }
}