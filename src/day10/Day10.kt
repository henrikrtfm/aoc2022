package day10

import day10.Command.Companion.updateCycle
import utils.Resources.resourceAsListOfString

fun main(){
    val input = resourceAsListOfString("src/day10/Day10.txt")
    val commands = input.map { Command.parse(it) }

    fun execute(commands: List<Command>): Int{
        var register = 1
        var cycle = 0
        var signalStrength = 0
        val commandStack = ArrayDeque<Command>().apply { addAll(commands)}
        while(commandStack.isNotEmpty()){
            cycle +=1
            val command = commandStack.removeFirst()
            if(cycle in listOf(20,60,100,140,180,220)){
                signalStrength += (cycle * register)
            }
            when{
                command.instruction == "noop" -> {}
                command.cycles == 0 -> register += command.value
                else -> {
                    command.updateCycle()
                    commandStack.addFirst(command)
                }
            }

        }
        return signalStrength
    }

    fun executeAndDraw(commands: List<Command>): String{
        var register = 1
        var cycle = 0
        var crt = ""
        val commandStack = ArrayDeque<Command>().apply { addAll(commands)}
        while(commandStack.isNotEmpty()){
            val command = commandStack.removeFirst()
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
                    commandStack.addFirst(command)
                }
            }
            when(cycle){
                40 -> cycle = 0
            }
        }
        return crt
    }

    fun part1(commands: List<Command>): Int{
        return execute(commands)
    }

    fun part2(commands: List<Command>): String{
        return executeAndDraw(commands)

    }
    println(part1(commands))
    part2(commands).chunked(40).forEach { println(it) }
}

data class Command(
    val instruction: String,
    val value: Int,
    var cycles: Int
) {
    companion object {
        fun parse(line: String): Command {
            val instruction = line.substringBefore(" ")
            val value = line.substringAfter(" ").toIntOrNull() ?: 0
            val cycles = when(instruction){
                "addx" -> 1
                else -> 0
            }
            return Command(instruction, value, cycles)
        }

        fun Command.updateCycle(){
            this.cycles -= 1
        }

    }
}

