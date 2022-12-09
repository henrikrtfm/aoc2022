package day09

import utils.Resources.resourceAsListOfString

typealias Point = Pair<Int, Int>
fun main(){

    val tailNeighbors = sequenceOf(-1 to 0, 0 to -1, 0 to 1, 1 to 0, -1 to 1, -1 to -1, 1 to 1, 1 to -1)
    val headNeighbours = sequenceOf(-1 to 0, 0 to -1, 0 to 1, 1 to 0)
    val diagonals = sequenceOf(-1 to 1, -1 to -1, 1 to -1, 1 to 1)
    val input = resourceAsListOfString("src/day09/Day09_test.txt").map { Move.parse(it) }
    val visited: MutableSet<Point> = mutableSetOf()
    var head = Point(0,0)
    var tail = Point(0,0)
    val knots = MutableList(9) {Point(0,0)}

    fun simulateOneKnot(move: Move){
        move.steps.forEach {
            head += move.direction
            when((tailNeighbors.map { it + tail }.toList()+tail).contains(head)){
                true -> {}
                false -> {
                    tail = (tailNeighbors.map { it+tail }.toSet() intersect headNeighbours.map { it2 -> it2 + head }.toSet()).first()
                }
            }
            visited.add(tail)
        }
    }

    fun simulateTenKnots(move: Move){
        move.steps.forEach {
            head += move.direction
            for((index, knot) in knots.withIndex()){
                val myHead = knots.getOrNull(index-1) ?: head
                when((tailNeighbors.map { it + knot }.toList()+knot).contains(myHead)){
                    true -> break
                    false -> {
                        knots[index] = (tailNeighbors.map { it+knot }.toSet() intersect headNeighbours.map { it2 -> it2 + myHead }.toSet()).firstOrNull()
                            ?:  (diagonals.map { self -> self+knot }.toSet() intersect diagonals.map { head -> head + myHead }.toSet()).first()
                    }
                }
                if(index==knots.lastIndex){
                    visited.add(knot)
                }
            }
        }
    }


    fun part1(input: List<Move>): Int{
        input.forEach { simulateOneKnot(it) }
        return visited.size
    }

    fun part2(input: List<Move>): Int {
        input.forEach { simulateTenKnots(it) }
        return visited.size + 1
    }
    println(part1(input))
    //println(part2(input))


}
private operator fun Point.plus(that: Point): Point =
    Point(this.first + that.first, this.second + that.second)

data class Move(
    val direction: Point,
    val steps: IntRange
) {
    companion object {
        fun parse(input: String): Move {

            val direction = when (input.substringBefore(" ")) {
                "U" -> Point(1, 0)
                "R" -> Point(0, 1)
                "L" -> Point(0, -1)
                "D" -> Point(-1, 0)
                else -> Point(0,0)
            }
            val steps = 1..input.substringAfter(" ").toInt()
            return Move(direction, steps)
        }
    }
}

