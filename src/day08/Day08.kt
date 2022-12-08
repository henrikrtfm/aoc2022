package day08

import utils.Resources.resourceAsListOfString

typealias Treemap = Array<IntArray>
typealias Point = Pair<Int, Int>

fun main() {

    val input = resourceAsListOfString("src/day08/Day08.txt")
    val treemap: Treemap = input
        .map { it.chunked(1) }
        .map { it -> it.map { it -> it.toInt() }.toIntArray() }
        .toTypedArray()
    val maxX = treemap.first().size-1
    val maxY = treemap.last().size-1

    fun returnLineOfSight(point: Point): Lineofsight{
        val lineOfSight = Lineofsight()
        lineOfSight.up = (0 until point.first).map{Point(it,point.second)}
        lineOfSight.down = (point.first+1..maxX).map{Point(it,point.second)}
        lineOfSight.left = (0 until point.second).map{Point(point.first,it)}
        lineOfSight.right = (point.second+1..maxY).map{Point(point.first,it)}
        return  lineOfSight
    }

    fun isVisible(point: Point): Point?{
        val lineofsight = returnLineOfSight(point)
        val self = treemap[point.first][point.second]
        val up = lineofsight.up.map { treemap[it.first][it.second] } + self
        val down = lineofsight.down.map { treemap[it.first][it.second] } + self
        val left = lineofsight.left.map { treemap[it.first][it.second] } + self
        val right = lineofsight.right.map { treemap[it.first][it.second] } + self
        return if((up.maxOrNull() == self && up.groupingBy { it }.eachCount()[self] ==1)
            || (down.maxOrNull() == self && down.groupingBy { it }.eachCount()[self] ==1)
            || (left.maxOrNull() == self && left.groupingBy { it }.eachCount()[self] ==1)
            || (right.maxOrNull() == self && right.groupingBy { it }.eachCount()[self] ==1)){
            point
        }else
            null

    }
    fun scenicScore(point:Point): Int{
        val lineofsight = returnLineOfSight(point)
        val self = treemap[point.first][point.second]

        val viewUp = lineofsight.up.map { treemap[it.first][it.second] }.reversed().countUntil{it >= self}
        val viewLeft = lineofsight.left.map { treemap[it.first][it.second] }.reversed().countUntil{it >= self}
        val viewRight = lineofsight.right.map { treemap[it.first][it.second] }.countUntil{it >= self}
        val viewDown = lineofsight.down.map { treemap[it.first][it.second] }.countUntil { it >= self }

        return viewUp*viewLeft*viewRight*viewDown
    }
    fun part1(): Int{
        return treemap
            .flatMapIndexed{indexrow, row -> row.mapIndexed{indexcol, _ -> isVisible(Point(indexrow,indexcol))}}
            .filterNotNull()
            .count()
    }

    fun part2(): Int{
        return treemap
            .flatMapIndexed{indexrow, row -> row.mapIndexed{indexcol, _ -> scenicScore(Point(indexrow,indexcol))}}
            .max()
    }
    println(part1())
    println(part2())
}

data class Lineofsight(
    var up: List<Point> = mutableListOf(),
    var down: List<Point> = mutableListOf(),
    var left: List<Point> = mutableListOf(),
    var right: List<Point> = mutableListOf()
)

private fun List<Int>.countUntil(pred: (Int) -> Boolean): Int{
    var res = 0
    for (element in this) {
        res++
        if (pred(element))
            return res
    }
    return res
}
