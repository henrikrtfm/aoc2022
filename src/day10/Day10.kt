package day10

import utils.Resources.resourceAsListOfString

fun main(){
    val input = resourceAsListOfString("src/day10/Day10.txt")
    val commands = ArrayDeque<Command>().apply { addAll(input.map{parseInput(it)})}
    var register = 1
    var cycle = 0
    var crt = ""
    val intervals = listOf(20,60,100,140,180,220)

    fun execute(commands: ArrayDeque<Command>): Int{
        var signalStrength = 0
        while(commands.isNotEmpty()){
            cycle +=1
            val command = commands.removeFirst()
            if(cycle in intervals){
                signalStrength += (cycle * register)
            }
            when{
                command.instruction == "noop" -> {}
                command.cycles == 0 -> register += command.value
                else -> {
                    command.updateCycle()
                    commands.addFirst(command)
                }
            }

        }
        return signalStrength
    }

    fun executeAndDraw(commands: ArrayDeque<Command>): String{
        while(commands.isNotEmpty()){
            val command = commands.removeFirst()
            crt += when (cycle) {
                in listOf(register-1,register,register+1) -> "#"
                else -> "."
            }
            cycle +=1
            when{
                command.instruction == "noop" -> {}
                command.cycles == 0 -> register += command.value
                else -> {
                    command.updateCycle()
                    commands.addFirst(command)
                }
            }
            when(cycle){
                40 -> cycle = 0
            }
        }
        return crt
    }

    fun part1(commands: ArrayDeque<Command>): Int{
        return execute(commands)
    }

    fun part2(commands: ArrayDeque<Command>): String{
        return executeAndDraw(commands)

    }
    println(part1(commands))
    part2(commands).chunked(40).forEach { println(it) }
}

fun parseInput(line: String): Command {
    val instruction = line.substringBefore(" ")
    val value = line.substringAfter(" ").toIntOrNull() ?: 0
    val cycles = when(instruction){
        "addx" -> 1
        else -> 0
    }
    return Command(instruction, value, cycles)
}

class Command(val instruction: String, val value: Int, var cycles: Int) {

        fun updateCycle(){
            this.cycles -= 1
        }

}

