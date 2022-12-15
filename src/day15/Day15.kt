package day15

import utils.Resources.resourceAsListOfString
import kotlin.math.absoluteValue

typealias Point = Pair<Int,Int>
typealias SensorBeacon = Pair<Point,Point>

fun main(){
    val input = resourceAsListOfString("src/day15/Day15.txt").map { parsePair(it) }

    fun part1(row: Int): Int{
        return input.filter { validSensor(row,it) }.flatMap { it.calculatePlaces(row) }.toSet().size-1
    }

    println(part1(2000000))

}
fun getSensorY(item: SensorBeacon): Int =  item.first.second

fun SensorBeacon.getSensor(): Point = this.first

fun manhattanDistance(item: SensorBeacon): Int{
    return (item.first.first-item.second.first).absoluteValue+(item.first.second-item.second.second).absoluteValue
}

fun SensorBeacon.calculatePlaces(row: Int): Set<Point>{
    return when (row > getSensorY(this)) {
        true -> {
            val offsetY = row - getSensorY(this)
            val offsetX = manhattanDistance(this) - offsetY
            val counter = offsetX * 2 + 1
            addToResultSet(this.getSensor(), offsetX, offsetY, counter)
        }

        else -> {
            val offsetY = (getSensorY(this) - row)
            val offsetX = (manhattanDistance(this) - offsetY)
            val counter = offsetX * 2 + 1
            addToResultSet(this.getSensor(), offsetX, -offsetY, counter)
        }
    }
}
fun addToResultSet(sensor: Point, offsetX: Int, offsetY: Int, counter: Int): Set<Point>{
    val resultSet = mutableSetOf<Point>()
    for(x in 0 until counter){
        resultSet.add(sensor+Point(-offsetX+x,offsetY))

    }
    return resultSet
}
fun validSensor(row: Int, item: SensorBeacon): Boolean {
    val md = manhattanDistance(item)
    val range = getSensorY(item) - md..getSensorY(item) + md
    return row in range
}
fun parsePair(line: String):SensorBeacon {
    val sensorX =  line.substringAfter("x=").substringBefore(",").toInt()
    val sensorY = line.substringAfter("y=").substringBefore(":").toInt()
    val beaconX = line.substringAfterLast("x=").substringBeforeLast(",").toInt()
    val beaconY = line.substringAfterLast("y=").trim().toInt()
    return Pair(Point(sensorX,sensorY), Point(beaconX,beaconY))
}
private operator fun Point.plus(that: Point): Point =
    Point(this.first + that.first, this.second + that.second)
