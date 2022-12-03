package day03

import utils.Resources.resourceAsListOfString

fun main() {

    fun parseInput(line: String): Rucksack{
        val firstCompartment = mutableSetOf<Char>()
        val secondCompartment = mutableSetOf<Char>()
        line.chunked(line.length/2)
            .first()
            .forEach {
                firstCompartment.add(it)
            }
        line.chunked(line.length/2)
            .last()
            .forEach {
                secondCompartment.add(it)
            }
        return Rucksack(firstCompartment,secondCompartment)
    }

    fun part1(rucksacks: List<Rucksack>): Int {
        return rucksacks
            .flatMap { it.firstCompartment.intersect(it.secondCompartment) }
            .sumOf { Rucksack.priority(it) }
    }

    fun part2(rucksacks: List<Set<Char>>): Int {
        return rucksacks
            .windowed(3,3)
            .flatMap { rucksacksGrouped ->
                rucksacksGrouped
                    .reduce(){first, next -> first.intersect(next)} }
            .sumOf { Rucksack.priority(it) }
    }

    val input = resourceAsListOfString("src/day03/Day03.txt")
    val rucksacks = input.map { parseInput(it) }
    val rucksacks2 = input.map { it.toSet() }
    println(part1(rucksacks))
    println(part2(rucksacks2))


}
data class Rucksack(
    val firstCompartment: Set<Char>,
    val secondCompartment : Set<Char>
){
    companion object{
        private val priorities = ('a'..'z').union('A'..'Z')
            .mapIndexed{ index, value -> value to index + 1}
            .toMap()

        fun priority(type: Char): Int{
            return priorities.getOrDefault(type, 0)
        }
    }
}