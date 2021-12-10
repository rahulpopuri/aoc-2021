package aoc

import java.util.*

fun main() {
    val input = "input_day10.txt".toStringList().map { Brackets(it) }
    println("Part 1: " + input.sumOf { it.calculateScore() })
    val scores = input.map { it.autocomplete() }.filter { it != 0L }.sorted()
    println("Part 2: " + scores[scores.size / 2])
}

private data class Brackets(val input: String) {
    private val matches = mapOf('(' to ')', '{' to '}', '[' to ']', '<' to '>')
    fun autocomplete(): Long {
        if (calculateScore() != 0) {
            return 0
        }
        val stack = Stack<Char>()
        for (c in input.toCharArray()) {
            if (isOpenBracket(c)) {
                stack.push(c)
            } else {
                if (stack.isNotEmpty() && matchingBracket(stack.peek(), c)) {
                    stack.pop()
                } else {
                    stack.push(c)
                }
            }
        }
        val autocomplete = mutableListOf<Char>()
        while (stack.isNotEmpty()) {
            autocomplete.add(matches.getOrDefault(stack.pop(), '-'))
        }
        var score = 0L
        for (c in autocomplete) {
            score *= 5
            score += autocompleteScore(c)
        }
        return score
    }

    fun calculateScore(): Int {
        val stack = Stack<Char>()
        for (c in input.toCharArray()) {
            if (isOpenBracket(c)) {
                stack.push(c)
            } else {
                if (stack.isNotEmpty()) {
                    if (matchingBracket(stack.peek(), c)) {
                        stack.pop()
                    } else {
                        return errorScore(c)
                    }
                } else {
                    stack.push(c)
                }
            }
        }
        return 0
    }

    private fun autocompleteScore(c: Char): Int {
        return when (c) {
            ')' -> 1
            ']' -> 2
            '}' -> 3
            '>' -> 4
            else -> 0
        }
    }

    private fun errorScore(c: Char): Int {
        return when (c) {
            ')' -> 3
            ']' -> 57
            '}' -> 1197
            '>' -> 25137
            else -> 0
        }
    }

    private fun isOpenBracket(c: Char): Boolean {
        return c in listOf('(', '[', '{', '<')
    }

    private fun matchingBracket(b1: Char, b2: Char): Boolean {
        return b2 == matches.getOrDefault(b1, '-')
                || b1 == matches.getOrDefault(b2, '-')
    }
}
