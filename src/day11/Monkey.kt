package day11

class Monkey(var items: ArrayDeque<Long>, var operation: String, val divisibleBy: Int, val trueMonkeyId: Int, val falseMonkeyId: Int) {

    private var inspectionCounter = 0

    fun doOperation(level: Long): Long{
        val operator = operation.substringBefore(" ")
        val value = operation.substringAfter(" ").toLongOrNull() ?: level
        return when(operator){
            "*" -> (level * value)
            else -> (level + value)
        }
    }

    fun doTest(level: Long): Int{
        return when(level % divisibleBy){
            0L -> trueMonkeyId
            else -> falseMonkeyId
        }
    }

    fun receiveItem(item: Long){
        items.add(item)
    }

    fun increaseInspectionCounter(){
        inspectionCounter+=1
    }

    fun getInspectionCounter():Int{
        return inspectionCounter
    }
}

fun parseInput(lines: List<String>): Monkey{
    var items = ArrayDeque<Long>()
    var operation = ""
    var divisibleBy = 0
    var trueMonkeyId = 0
    var falseMonkeyId = 0
    lines.forEach { line ->
        when{
            line.trim().startsWith("Starting") -> items = items.apply { addAll(line.substringAfter(": ").split(", ").map { it.toLong() }) }
            line.trim().startsWith("Operation") -> operation = line.substringAfter("old ")
            line.trim().startsWith("Test") -> divisibleBy = line.substringAfter("by ").toInt()
            line.trim().startsWith("If true") -> trueMonkeyId = line.substringAfter("monkey ").toInt()
            line.trim().startsWith("If false") -> falseMonkeyId = line.substringAfter("monkey ").toInt()
        }
    }
    return Monkey(items, operation, divisibleBy, trueMonkeyId, falseMonkeyId)
}