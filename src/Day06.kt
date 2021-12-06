import ExpectedResultChecker.Companion.verify

fun main() {
    fun processDays(input: List<Long>, daysLeft: Int): List<Long> =
        if (daysLeft == 0) {
            input
        } else {
            input.mapIndexed { index, _ ->
                when (index) {
                    8 -> input[0]
                    6 -> input[0] + input[7]
                    else -> input[index + 1]
                }
            }
                .let { processDays(it, daysLeft - 1) }
        }


    fun part1(input: List<Long>): Long = processDays(input, 80).sum()

    fun part2(input: List<Long>): Long = processDays(input, 256).sum()

    readInput("Day06_test")
        .parse()
        .also(verify(::part1) returns 5934)
        .also(verify(::part2) returns 26984457539L)

    readInput("Day06")
        .parse()
        .also(printResultOf(::part1))
        .also(printResultOf(::part2))

}

private fun List<String>.parse() = MutableList(9) { 0L }.apply {
    this@parse.first()
        .split(",")
        .toInts()
        .forEach { this[it] += 1L }
}

