package day12

typealias Point = Pair<Int, Int>

class Heightmap(val topography: Array<CharArray>, val start: Point, val end: Point) {

    val lookupvalue = (('a'..'z')+'E')
        .mapIndexed{ index, value -> value to index + 1}
        .toMap()

    fun lookupValue(point: Point): Int{
        return lookupvalue[topography[point.first][point.second]] ?: 0
    }

    operator fun contains(point: Point): Boolean =
        point.first in this.topography.indices && point.second in this.topography.first().indices

}

fun parseInput(lines: List<String>): Heightmap{
    val topography = lines.map {line -> line.chunked(1) }
        .map { it -> it.map { it -> it[0] }.toCharArray()}
        .toTypedArray()
    val start = topography.flatMapIndexed{indexrow, row -> row.mapIndexed{indexcol, _ -> when(topography[indexrow][indexcol]){
        'S' -> Point(indexrow,indexcol)
        else -> null
    } }}.filterNotNull().first()
    val end = topography.flatMapIndexed{indexrow, row -> row.mapIndexed{indexcol, _ -> when(topography[indexrow][indexcol]){
        'E' -> Point(indexrow,indexcol)
        else -> null
    } }}.filterNotNull().first()
    return Heightmap(topography,start,end)
}