package aoc

import java.io.File
import java.io.InputStream
import java.nio.charset.Charset

fun String.toStringList(): List<String> {
    return File(ClassLoader.getSystemResource(this).file).readLines(Charset.defaultCharset())
}

fun String.toIntList(): List<Int> {
    return this.toStringList().map { it.toInt() }
}

fun String.toLongList(): List<Long> {
    return this.toStringList().map { it.toLong() }
}

fun String.to2dCharList(): List<List<Char>> {
    return this.toStringList().map { it.toCharArray().toList() }
}

fun String.toIntArray() : IntArray {
    return this.toIntList().toIntArray()
}

fun String.to2dCharArray(): Array<Array<Char>> {
    return this.toStringList().map { it.toCharArray().toTypedArray() }
        .toTypedArray()
}

fun Array<Array<Int>>.print() {
    for (i in this.indices) {
        println(this[i].contentToString())
    }
    println()
}

fun Array<Array<Char>>.print() {
    for (i in this.indices) {
        println(this[i].contentToString())
    }
    println()
}

fun Array<Array<Char>>.copy() = Array(size) { get(it).clone() }

fun Array<Array<Char>>.flip(): Array<Array<Char>> {
    val result = this.copy()
    for (i in 0 until result.size / 2) {
        val tmp = result[result.size - i - 1]
        result[result.size - i - 1] = result[i]
        result[i] = tmp
    }
    return result
}

fun Array<Array<Char>>.rotate(): Array<Array<Char>> {
    val result = this.copy()
    val width = this.size
    for (i in result.indices) {
        for (j in result[i].indices) {
            result[i][j] = this[width - 1 - j][i]
        }
    }
    return result
}

fun MutableMap<Int, Set<String>>.prune() {
    while (this.values.any { it.size > 1 }) {
        for (allergen in this.entries) {
            if (allergen.value.size == 1) {
                // remove from other entries
                this.entries
                    .filter { it.key != allergen.key }
                    .filter { it.value.contains(allergen.value.elementAt(0)) }
                    .forEach { e ->
                        val set = HashSet(e.value)
                        set.remove(allergen.value.elementAt(0))
                        this[e.key] = set
                    }
            }
        }
    }
}

// Figure out how to combine with above
fun MutableMap<String, Set<String>>.prune2() {
    while (this.values.any { it.size > 1 }) {
        for (allergen in this.entries) {
            if (allergen.value.size == 1) {
                // remove from other entries
                this.entries
                    .filter { it.key != allergen.key }
                    .filter { it.value.contains(allergen.value.elementAt(0)) }
                    .forEach { e ->
                        val set = HashSet(e.value)
                        set.remove(allergen.value.elementAt(0))
                        this[e.key] = set
                    }
            }
        }
    }
}