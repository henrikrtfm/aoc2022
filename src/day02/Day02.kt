package day02

import utils.Resources.resourceAsListOfString

fun main(){

    fun part1(turns: List<Turn>): Int {
        val results = mapOf(
            Turn("A","Y") to 6,
            Turn("A", "X") to 3,
            Turn("B", "Z") to 6,
            Turn("B", "Y") to 3,
            Turn("C", "X") to 6,
            Turn("C", "Z") to 3)
        val shapes = mapOf("X" to 1, "Y" to 2, "Z" to 3)
        var score = 0

        turns.forEach {
            score += shapes.getOrDefault(it.you, 0)+results.getOrDefault(it, 0)
        }
        return score
    }

    fun part2(turns: List<Turn>): Int {
        val results = mapOf(
            Turn("A","Y") to 4,
            Turn("A", "Z") to 8,
            Turn("A", "X") to 3,
            Turn("B", "Z") to 9,
            Turn("B", "Y") to 5,
            Turn("B", "X") to 1,
            Turn("C", "Y") to 6,
            Turn("C", "Z") to 7,
            Turn("C", "X") to 2)
        var score = 0

        turns.forEach {
            score += results.getOrDefault(it, 0)
        }
        return score
    }

    val input = resourceAsListOfString("src/day02/Day02.txt")
    val turns = input.map(Turn.Companion::parse)
    println(part1(turns))
    println(part2(turns))


}
data class Turn(
    val opponent: String,
    val you: String
){
    companion object{
        fun parse(line: String) = Turn(
            opponent = line.substringBefore(" "),
            you = line.substringAfter(" ")
        )
    }
}