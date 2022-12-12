package day11

import utils.Resources.resourceAsText

fun main(){

    val input = resourceAsText("src/day11/Day11.txt").split("\n\n").map { it.split("\n") }
    val monkeys = input.map { parseInput(it) }

    fun inspectItemsWithWorry(){
        monkeys.forEach { monkey ->
            while(monkey.items.isNotEmpty()){
                val item = monkey.doOperation(monkey.items.removeFirst())/3
                monkey.increaseInspectionCounter()
                val throwTo = monkey.doTest(item)
                monkeys[throwTo].receiveItem(item)
            }
        }
    }

    fun inspectItemsWithoutWorry(trick: Long){
        monkeys.forEach { monkey ->
            while(monkey.items.isNotEmpty()){
                val item = monkey.doOperation(monkey.items.removeFirst()) % trick
                monkey.increaseInspectionCounter()
                val throwTo = monkey.doTest(item)
                monkeys[throwTo].receiveItem(item)
            }
        }
    }

    fun part1(): Int{
        repeat(20){inspectItemsWithWorry()}
        val result = monkeys.map { it.getInspectionCounter() }.sortedDescending().take(2)
        return result.first()*result.last()
    }

    fun part2(): Long{
        val trick = monkeys.map { it.divisibleBy.toLong() }.reduce(Long::times)
        repeat(10000){inspectItemsWithoutWorry(trick)}
        val result = monkeys.map { it.getInspectionCounter() }.sortedDescending().take(2)
        return result.first().toLong()*result.last().toLong()
    }
    println(part1())
    println(part2())
}