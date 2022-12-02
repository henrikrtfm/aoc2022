package day01

import utils.Resources.resourceAsText

fun main() {
    fun part1(input: List<Int>): Int {
        return input.max()
    }

    fun part2(input: List<Int>): Int {
        return input.sortedDescending().take(3).sum()
    }

    val input = resourceAsText("src/day01/Day01.txt").split("\n\n").map { it.split("\n") }
    val caloriesSummed = input.map { it.sumOf(String::toInt) }
    println(part1(caloriesSummed))
    println(part2(caloriesSummed))
}
