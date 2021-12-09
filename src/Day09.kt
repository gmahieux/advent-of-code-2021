fun main() {

    fun isSurrounding(cell: Cell) : (Cell)-> Boolean = { (x: Int,y: Int) ->  (cell.first == x && (cell.second == y + 1 || cell.second == y - 1)) ||
            (cell.second == y && (cell.first == x + 1 || cell.first == x - 1))}

    fun Set<Cell>.getBasinForCell(cell: Cell): Basin =
        this
            .filter(isSurrounding(cell))
            .toSet()
            .let { result ->
                result.fold(result + cell) { currentBasin, cell ->
                    currentBasin + this.minus(currentBasin).getBasinForCell(cell)
                }
            }

    fun Matrix.getSurroundingCellsHeight(x: Int, y: Int) = listOfNotNull(
        this[x - 1 to y],
        this[x + 1 to y],
        this[x to y - 1],
        this[x to y + 1],
        )

    fun Matrix.heightAt(x: Int, y: Int) = this[x to y] ?: throw IllegalArgumentException("Invalid coords : $x $y")
    fun Matrix.isLowPoint(x: Int, y: Int) =
        getSurroundingCellsHeight(x, y).all {
            heightAt(x, y) < it
        }

    fun Set<Cell>.getNextBasin(): Basin =
        if (this.isNotEmpty()) {
            this.getBasinForCell(this.first())
        } else {
            emptySet()
        }

    fun Set<Cell>.getBasins(): List<Basin> =
        if (this.isNotEmpty()) {
            val basin = this.getNextBasin()
            listOf(basin) + this.minus(basin).getBasins()
        } else {
            emptyList()
        }

    fun part1(input: Matrix): Int =
        input.map { (coords, height) ->
            if (input.isLowPoint(coords.first, coords.second)) {
                height + 1
            } else {
                0
            }
        }.sum()

    fun part2(input: Matrix): Int =
        input.filterNot { (_, height) -> height == 9 }
            .keys
            .getBasins()
            .map { it.size }
            .sortedDescending()
            .take(3)
            .let { (a, b, c) -> a * b * c }


    readInput("Day09_test")
        .parse()
        .also { check(part1(it) == 15) }
        .also { check(part2(it) == 1134) }

    readInput("Day09")
        .parse()
        .also { println(part1(it)) }
        .also { println(part2(it)) }
}



typealias Cell = Pair<Int, Int>
typealias Matrix = Map<Cell, Int>
typealias Basin = Set<Cell>

private fun List<String>.parse(): Matrix = flatMapIndexed { rowIndex, row ->
    row.mapIndexed { colIndex, col -> (colIndex to rowIndex) to col.toString().toInt() }
}.toMap()


