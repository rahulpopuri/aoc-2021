package aoc

fun main() {
    val input = loadInput()
    println("Part 1: " + input.sumOf { it.part1() })
    println("Part 2: " + input.sumOf { it.part2() })
}

private data class Pattern(val signals: List<String>, val outputs: List<String>) {
    private val segmentMap = mutableMapOf(
        "abcefg" to 0,
        "cf" to 1,
        "acdeg" to 2,
        "acdfg" to 3,
        "bcdf" to 4,
        "abdfg" to 5,
        "abdefg" to 6,
        "acf" to 7,
        "abcdefg" to 8,
        "abcdfg" to 9,
    )

    fun part1(): Int {
        /*
        1 -> 2 chars
        4 -> 4 chars
        7 -> 3 chars
        8 -> 7 chars
         */
        return outputs.count { it.length == 2 || it.length == 3 || it.length == 4 || it.length == 7 }
    }

    fun part2(): Int {
        /*
                   a
                b     c
                   d
                e     f
                   g

            0 = abcefg   6
            1 = cf       2*
            2 = acdeg    5
            3 = acdfg    5
            4 = bcdf     4*
            5 = abdfg    5
            6 = abdefg   6
            7 = acf      3*
            8 = abcdefg  7*
            9 = abcdfg   6
            gdcfab
         */

        val charMap = mutableMapOf<Char, Char>()
        val oneStr = getSignalsOfLength(2).first()
        val fourStr = getSignalsOfLength(4).first()
        val sevenStr = getSignalsOfLength(3).first()
        val eightStr = getSignalsOfLength(7).first()

        val a = sevenStr.subtract(oneStr)[0]
        charMap[a] = 'a'

        val d = solveForD(fourStr, oneStr)
        charMap[d] = 'd'

        val b = fourStr.subtract(oneStr).replace(d.toString(), "")[0]
        charMap[b] = 'b'

        val c = solveForC(oneStr)
        charMap[c] = 'c'

        val f = oneStr.replace(c.toString(), "")[0]
        charMap[f] = 'f'

        val g = solveForG(charMap)
        charMap[g] = 'g'

        val e = solveForE(eightStr, charMap)
        charMap[e] = 'e'

        return parseOutputs(charMap)
    }

    private fun parseOutputs(charMap: Map<Char, Char>): Int {
        return outputs.map { it.toCharArray() }
            .map {
                val k = String(it.map { c -> charMap.getOrDefault(c, '-') }.toCharArray())
                for (key in segmentMap.keys) {
                    if (key.containsOnly(k.toList())) {
                        return@map segmentMap.getOrDefault(key, 0)
                    }
                }
                return@map 0
            }
            .joinToString("") { it.toString() }
            .toInt()
    }

    private fun solveForD(fourStr: String, oneStr: String): Char {
        val length6Str = getSignalsOfLength(6)
        for (c in fourStr.subtract(oneStr).toCharArray()) {
            for (str6 in length6Str) {
                if (!str6.contains(c)) {
                    return c
                }
            }
        }
        throw IllegalArgumentException("Couldn't solve for d")
    }

    private fun solveForC(oneStr: String): Char {
        val length6Str = getSignalsOfLength(6)
        for (str6 in length6Str) {
            for (c in oneStr) {
                if (!str6.contains(c)) {
                    return c
                }
            }
        }
        throw IllegalArgumentException("Couldn't solve for c")
    }

    private fun solveForG(charMap: Map<Char, Char>): Char {
        val length6Str = getSignalsOfLength(6)
        // Find first that contains all chars in abcdf
        val tmp = mutableListOf<Char>()
        val reversed = charMap.entries.associate { (k, v) -> v to k }
        for (c in listOf('a', 'b', 'c', 'd', 'f')) {
            tmp.add(reversed.getOrDefault(c, '-'))
        }
        for (str6 in length6Str) {
            if (str6.containsAll(tmp)) {
                return str6.subtract(tmp.toString())[0]
            }
        }
        throw IllegalArgumentException("Couldn't solve for g")
    }

    private fun solveForE(eightStr: String, charMap: Map<Char, Char>): Char {
        for (c in eightStr) {
            if (!charMap.containsKey(c)) {
                return c
            }
        }
        throw IllegalArgumentException("Couldn't solve for e")
    }

    private fun getSignalsOfLength(length: Int): List<String> {
        return signals.filter { it.length == length }
    }
}

private fun loadInput(): List<Pattern> {
    return "input_day8.txt".toStringList()
        .map { line ->
            val split = line.split("|")
            val signals = split[0].trim().split(" ").toList()
            val outputs = split[1].trim().split(" ").toList()
            Pattern(signals, outputs)
        }
        .toList()
}