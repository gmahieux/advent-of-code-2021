import ExpectedResultChecker.Companion.verify

fun main() {

    fun part1(input: List<Pair<Coord, Coord>>): Int =
        input
            .filter { (coord1, coord2) -> coord1.first == coord2.first || coord1.second == coord2.second }
            .flatmapToPoints()
            .groupBy { it }
            .count { (_, v) -> v.size > 1 }

    fun part2(input: List<Pair<Coord, Coord>>): Int =
        input
            .flatmapToPoints()
            .groupBy { it }
            .count { (_, v) -> v.size > 1 }

    readInput("Day05_test")
        .parseCoords()
        .also(verify(::part1) returns 5)
        .also(verify(::part2) returns 12)

    readInput("Day05")
        .parseCoords()
        .also(printResultOf(::part1))
        .also(printResultOf(::part2))

}

typealias Coord = Pair<Int, Int>

private fun List<String>.parseCoords(): List<Pair<Coord, Coord>> =
    this.map {
        it.split(" -> ")
            .map { it.split(",").map(String::toInt).let { (x, y) -> Coord(x, y) } }
            .let { (first, last) -> first to last }

    }

private fun rangeFrom(first: Int, second: Int): IntProgression =
    if (first < second)
        (first..second)
    else
        (first downTo second)

private fun List<Pair<Coord, Coord>>.flatmapToPoints() =
    this.map { (coord1, coord2) ->
        when {
            coord1.first == coord2.first -> rangeFrom(coord1.second, coord2.second)
                .map { Coord(coord1.first, it) }
            coord1.second == coord2.second -> rangeFrom(coord1.first, coord2.first)
                .map { Coord(it, coord1.second) }
            else -> rangeFrom(coord1.first, coord2.first)
                .zip(rangeFrom(coord1.second, coord2.second))
        }
    }.flatten()