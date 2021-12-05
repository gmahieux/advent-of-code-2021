fun main() {
    fun part1(numbers: List<Int>, boards: List<Board>): Int =
        boards
            .map { it to it.getNumbersRequiredToWin(numbers) }
            .sortedBy { (_, numbersRequiredToWin) ->numbersRequiredToWin.size }
            .first()
            .let { (board, numbersRequiredToWin) ->
                board.getNumbers()
                    .minus(numbersRequiredToWin)
                    .sum() * numbersRequiredToWin.last()
            }

    fun part2(numbers: List<Int>, boards: List<Board>): Int =
        boards
            .map { it to it.getNumbersRequiredToWin(numbers) }
            .sortedBy { (_, numbersRequiredToWin) ->numbersRequiredToWin.size }
            .last()
            .let { (board, numbersRequiredToWin) ->
                board.getNumbers()
                    .minus(numbersRequiredToWin)
                    .sum() * numbersRequiredToWin.last()
            }

    readInput("Day04_test")
        .parse()
        .also { check(part1(it.first, it.second) == 4512) }
        .also { check(part2(it.first, it.second) == 1924) }

    readInput("Day04")
        .parse()
        .also { println(part1(it.first, it.second)) }
        .also { println(part2(it.first, it.second)) }

}

private fun List<String>.parse(): Pair<List<Int>, List<Board>> =
    this.first().split(",").map { it.toInt() } to
            this.drop(2)
                .windowed(size = 5, step = 6, transform = List<String>::toNumberMatrix)
                .map(::Board)

private class Board(val matrix: List<List<Int>>) {
    val combinations = matrix + matrix.transpose()

    fun getNumbersRequiredToWin(numbers: List<Int>): List<Int> {
        var size = 4
        do {
            size++
        } while (getWinningCombination(numbers.take(size)) == null)
        return numbers.take(size)
    }

    fun getNumbers() = matrix.flatten()

    private fun getWinningCombination(numbers: List<Int>): List<Int>? =
        combinations.firstOrNull { numbers.containsAll(it) }

    override fun toString(): String {
        return matrix.joinToString(separator = "\n") { it.joinToString(separator = " ") } + "\n"
    }
}
