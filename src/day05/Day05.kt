package day05

import utils.Resources.resourceAsText

fun main(){

    fun part1(crates: List<MutableList<Char>>, procedures: List<Procedure>): String {
        procedures.forEach {
            val move = it.move
            val from = it.from-1
            val to = it.to-1
            crates[to].addAll(crates[from].takeLast(move).reversed())
            repeat(move) {crates[from].removeLast()}
        }
        return String(crates.flatMap{it.takeLast(1)}.toCharArray())
    }

    fun part2(crates: List<MutableList<Char>>, procedures: List<Procedure>): String {
        procedures.forEach {
            val move = it.move
            val from = it.from-1
            val to = it.to-1
            crates[to].addAll(crates[from].takeLast(move))
            repeat(move) {crates[from].removeLast()}
        }
        return String(crates.flatMap{it.takeLast(1)}.toCharArray())
    }

    val crates = listOf(mutableListOf('T', 'P', 'Z', 'C', 'S', 'L', 'Q', 'N'),
                                            mutableListOf('L', 'P', 'T', 'V', 'H', 'C', 'G'),
                                            mutableListOf('D', 'C', 'Z', 'F'),
                                            mutableListOf('G', 'W', 'T', 'D', 'L', 'M', 'V', 'C'),
                                            mutableListOf('P', 'W', 'C'),
                                            mutableListOf('P', 'F', 'J', 'D', 'C', 'T', 'S', 'Z'),
                                            mutableListOf('V', 'W', 'G', 'B', 'D'),
                                            mutableListOf('N', 'J', 'S', 'Q', 'H', 'W'),
                                            mutableListOf('R', 'C', 'Q', 'F', 'S', 'L', 'V'))

    val procedures = resourceAsText("src/day05/Day05.txt")
        .substringAfter("\n\n")
        .lines()
        .map { Procedure.parse(it) }

    println(part1(crates, procedures))
    println(part2(crates, procedures))

}

data class Procedure(
    val move: Int,
    val from: Int,
    val to: Int
){
    companion object{
        fun parse(line: String) = Procedure(
            move = line.substringAfter("move ").substringBefore(" from").toInt(),
            from = line.substringAfter("from ").substringBefore(" to").toInt(),
            to = line.substringAfter("to ").toInt()
        )
    }
}