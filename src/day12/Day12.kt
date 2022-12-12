package day12

import utils.Resources.resourceAsListOfString

fun main(){
    val neighbors = sequenceOf(-1 to 0, 0 to -1, 0 to 1, 1 to 0)
    val input = resourceAsListOfString("src/day12/Day12.txt")
    val heightmap = parseInput(input)

    fun returnNeighbors(point: Point): List<Point>{
        return neighbors.map { it + point }.filter { it in heightmap }.toList()
    }

    fun traverse(start: Point): Int{
        val visited = mutableSetOf(start)
        val queue = mutableListOf(Pair(start, 0))
        while(queue.isNotEmpty()){
            val current = queue.removeFirst()
            when (current.first) {
                heightmap.end -> return current.second
                else -> {
                    val neighborPoints = returnNeighbors(current.first)
                        .filter { it !in visited }
                        .filter { heightmap.lookupValue(it) <= heightmap.lookupValue(current.first) +1}
                    visited.addAll(neighborPoints)
                    neighborPoints.forEach { next -> queue.add(Pair(next,current.second+1))}
                }
            }
        }
        error("Can't find path to highest point!")
    }

    fun traverseBackwards(end: Point): Int{
        val visited = mutableSetOf(end)
        val queue = mutableListOf(Pair(end, 0))
        while(queue.isNotEmpty()){
            val current = queue.removeFirst()
            when (heightmap.topography[current.first.first][current.first.second]) {
                'a' -> return current.second
                else -> {
                    val neighborPoints = returnNeighbors(current.first)
                        .filter { it !in visited }
                        .filter { heightmap.lookupValue(it) >= heightmap.lookupValue(current.first) -1}
                    visited.addAll(neighborPoints)
                    neighborPoints.forEach { next -> queue.add(Pair(next,current.second+1))}
                }
            }
        }
        error("Can't find path to first lowest point!")
    }
    fun part1(){
        println(traverse(heightmap.start))

    }
    fun part2(){
        println(traverseBackwards(heightmap.end))
    }
    part1()
    part2()
}

private operator fun Point.plus(that: Point): Point =
    Point(this.first + that.first, this.second + that.second)