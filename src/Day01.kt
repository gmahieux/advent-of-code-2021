import ExpectedResultChecker.Companion.verify

fun main() {
    fun part1(input: List<Int>): Int = input.windowed(2).count { (prev, cur) -> cur > prev }

    fun part2(input: List<Int>): Int = input
        .windowed(size = 3) { it.sum() }
        .let(::part1)

     readInput("Day01_test")
        .toInts()
        .also(verify(::part1) returns 7)
        .also(verify(::part2) returns 5)

    readInput("Day01")
        .toInts()
        .also(printResultOf(::part1))
        .also(printResultOf(::part2))
}