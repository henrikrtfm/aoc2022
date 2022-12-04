package day04

import utils.Resources.resourceAsListOfString

fun main(){

    fun part1(assignments: List<Assignment>): Int{
        return assignments.sumOf { Assignment.overlapTotal(it) }
    }

    fun part2(assignments: List<Assignment>): Int{
        return assignments.sumOf { Assignment.overlapAny(it) }
    }

    val input =  resourceAsListOfString("src/day04/Day04.txt")
    val assignments = input.map { Assignment.parse(it) }
    println(part1(assignments))
    println(part2(assignments))
}

data class Assignment(
    val sectionsOne:  Set<Int>,
    val sectionsTwo: Set<Int>
){
    companion object{
        fun parse(line: String) = Assignment(
            sectionsOne = IntRange(line.substringBefore(",").substringBefore("-").toInt(), line.substringBefore(",").substringAfter("-").toInt()).toSet(),
            sectionsTwo = IntRange(line.substringAfter(",").substringBefore("-").toInt(), line.substringAfter(",").substringAfter("-").toInt()).toSet()
        )

        fun overlapTotal(assignment: Assignment): Int{
            return when{
                assignment.sectionsOne.containsAll(assignment.sectionsTwo) or assignment.sectionsTwo.containsAll(assignment.sectionsOne) -> 1
                else -> 0
            }
        }

        fun overlapAny(assignment: Assignment): Int{
            return when{
                assignment.sectionsOne.intersect(assignment.sectionsTwo).isEmpty() -> 0
                else -> 1
            }
        }
    }
}