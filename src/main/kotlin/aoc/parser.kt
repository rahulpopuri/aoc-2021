package aoc

import java.io.File
import java.nio.charset.Charset

fun String.containsAll(elements: Collection<Char>): Boolean {
    if (elements.isEmpty()) {
        return false
    }
    for (c in elements) {
        if (!this.contains(c)) {
            return false
        }
    }
    return true
}

fun String.containsOnly(elements: Collection<Char>): Boolean {
    return this.containsAll(elements)
            && this.subtract(elements.toString()).isEmpty()
}

fun String.subtract(input: String): String {
    var result = this
    for (c in input) {
        result = result.replace(c.toString(), "")
    }
    return result
}

fun String.toStringList(): List<String> {
    return File(ClassLoader.getSystemResource(this).file).readLines(Charset.defaultCharset())
}

fun String.toText(): String {
    return File(ClassLoader.getSystemResource(this).file).readText(Charset.defaultCharset())
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

fun String.toIntArray(): IntArray {
    return this.toIntList().toIntArray()
}

fun String.to2dCharArray(): Array<Array<Char>> {
    return this.toStringList().map { it.toCharArray().toTypedArray() }
        .toTypedArray()
}

inline fun <reified INNER> array2d(
    sizeOuter: Int,
    sizeInner: Int,
    noinline innerInit: (Int) -> INNER
): Array<Array<INNER>> = Array(sizeOuter) { Array(sizeInner, innerInit) }

fun array2dOfInt(sizeOuter: Int, sizeInner: Int): Array<IntArray> = Array(sizeOuter) { IntArray(sizeInner) }
fun array2dOfLong(sizeOuter: Int, sizeInner: Int): Array<LongArray> = Array(sizeOuter) { LongArray(sizeInner) }
fun array2dOfByte(sizeOuter: Int, sizeInner: Int): Array<ByteArray> = Array(sizeOuter) { ByteArray(sizeInner) }
fun array2dOfChar(sizeOuter: Int, sizeInner: Int): Array<CharArray> = Array(sizeOuter) { CharArray(sizeInner) }
fun array2dOfBoolean(sizeOuter: Int, sizeInner: Int): Array<BooleanArray> = Array(sizeOuter) { BooleanArray(sizeInner) }

fun Array<IntArray>.print() {
    for (i in this.indices) {
        println(this[i].contentToString())
    }
    println()
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
