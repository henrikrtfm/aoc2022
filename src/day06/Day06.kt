package day06

import utils.Resources.resourceAsString

fun main(){

    fun findMarker(windowSize: Int, buffer: String): Int{
        return windowSize + buffer.windowed(windowSize, 1).indexOfFirst { it.allUnique() }
    }

    fun part1(input: String): Int{
        return findMarker(4,input)
    }

    fun part2(input: String): Int{
        return findMarker(14, input)
    }

    val input = resourceAsString("src/day06/Day06.txt")
    println(part1(input))
    println(part2(input))

}

fun String.allUnique(): Boolean = all(hashSetOf<Char>()::add)