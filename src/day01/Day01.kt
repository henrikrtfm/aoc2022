package day01

import utils.Resources.resourceAsListOfString

fun main() {
    fun part1(input: List<Int>): Int {
        return input.max()
    }

    fun part2(input: List<Int>): Int {
        return input.sortedDescending().take(3).sum()
    }

    val input = resourceAsListOfString("src/day01/Day01.txt")
    val indexes = input.foldIndexed(listOf(0)){ index, acc, s -> if(s == "") acc+(index) else acc} + input.size
    var caloriesList = mutableListOf<List<Int>>()
    indexes.zipWithNext().forEach {
        caloriesList.add(input.subList(it.first,it.second)
            .filter { s: String -> s != "" }
            .map { it2 -> it2.toInt() })
    }
    val caloriesSummed = caloriesList.map{ it.sum() }
    println(part1(caloriesSummed))
    println(part2(caloriesSummed))
}
