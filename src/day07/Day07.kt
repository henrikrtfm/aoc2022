package day07

import utils.Resources.resourceAsListOfString

fun main(){

    fun totalSize(directory: Directory): Long{
        return directory.size + directory.children.sumOf { totalSize(it) }
    }

    fun part1(filesystem: List<Directory>): Long{
        return filesystem
            .map { totalSize(it) }
            .filter { it <= 100000 }
            .sum()
    }

    fun part2(filesystem: List<Directory>): Long{
        val neededSpace = 30000000 - (70000000 - filesystem.map { totalSize(it) }.first())
        return filesystem
            .map{totalSize(it)}
            .sorted()
            .first { it >= neededSpace }
    }

    val input = resourceAsListOfString("src/day07/Day07.txt")
    val filesystem = Directory.parse(input)
    println(part1(filesystem))
    println(part2(filesystem))

}

data class Directory(
    val parent: Directory?,
    var size: Long = 0L,
    val children: MutableList<Directory> = mutableListOf()
){

    companion object{
        private val root: Directory = Directory(parent = null)

        fun parse(input: List<String>): List<Directory>{
            var current = root
            val directories = mutableListOf(root)
            input.forEach { line ->
                when{
                    line.startsWith("$ cd ..") -> current = current.parent!!

                    line.startsWith("$ cd ") -> {
                        val directory = Directory(parent = current)
                        current.children.add(directory)
                        directories.add(directory)
                        current = directory

                    }

                    line.first().isDigit() -> {
                        current.size += line.substringBefore(" ").toLong()
                    }
                }
            }
            return directories
        }
    }
}