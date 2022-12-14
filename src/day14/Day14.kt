package day14

import utils.Resources.resourceAsListOfString
typealias Point = Pair<Int,Int>
private val DOWN = Point(0,1)
private val LEFT = Point(-1,1)
private val RIGHT = Point(1,1)

fun main(){
    val rocks = resourceAsListOfString("src/day14/Day14.txt")
        .flatMap { line -> line.split(" -> ")
            .map { Point(it.substringBefore(",").toInt(), it.substringAfter(",").toInt()) }
            .zipWithNext()}.toSet()
    val abyss = rocks.maxOf { it.maxY() }+1
    val floor = rocks.maxOf { it.maxY() }+2
    val start = Point(500,0)
    var self = Point(500,0)
    val sand = mutableSetOf<Point>()

    fun moveDown(self: Point): Boolean{
        return !rocks.contains(self+DOWN) && !sand.contains(self+DOWN)
    }

    fun moveLeft(self: Point): Boolean{
        return !rocks.contains(self+LEFT) && !sand.contains(self+LEFT)
    }

    fun moveRight(self: Point): Boolean{
        return !rocks.contains(self+RIGHT) && !sand.contains(self+RIGHT)
    }

    fun floorReached(self: Point): Boolean{
        return self.second+1 == floor
    }

    fun nextStep(self: Point): String{
        return when{
            floorReached(self) -> "FLOOR"
            moveDown(self) -> "DOWN"
            moveLeft(self) -> "LEFT"
            moveRight(self) -> "RIGHT"
            else -> "BOTTOM"
        }
    }
    fun part1(): Int{
        while(self.second != abyss) {
            when (nextStep(self)) {
                "DOWN" -> self += DOWN
                "LEFT" -> self += LEFT
                "RIGHT" -> self += RIGHT
                else -> {
                    sand.add(self)
                    self = start
                }
            }
        }
        return sand.size
    }
    fun part2(): Int{
        while(!sand.contains(start)) {
            when (nextStep(self)) {
                "DOWN" -> self += DOWN
                "LEFT" -> self += LEFT
                "RIGHT" -> self += RIGHT
                else -> {
                    sand.add(self)
                    self = start
                }
            }
        }
        return sand.size
    }
    //println(part1())
    println(part2())
}
private operator fun Point.plus(that: Point) = Point(
    this.first+that.first, this.second+that.second)
private fun Set<Pair<Point,Point>>.contains(that: Point): Boolean{
    return this.map { it.contains(that) }.contains(true)
}
private operator fun Pair<Point,Point>.contains(that: Point): Boolean {
    val maxX = maxOf(this.first.first, this.second.first)
    val minX = minOf(this.first.first, this.second.first)
    val maxY = maxOf(this.first.second, this.second.second)
    val minY = minOf(this.first.second, this.second.second)
    val xRange = minX..maxX
    val yRange = minY..maxY
    return that.first in xRange && that.second in yRange
}
private fun Pair<Point,Point>.maxX(): Int {
    return maxOf(this.first.first, this.second.first)
}
private fun Pair<Point,Point>.maxY(): Int {
    return maxOf(this.first.second, this.second.second)
}