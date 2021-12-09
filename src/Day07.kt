import ExpectedResultChecker.Companion.verify
import kotlin.math.abs
import kotlin.math.floor

fun main() {

    fun getMedian(input: List<Int>) : Int = input.sorted()[input.size/2]
    fun getAvg(input: List<Int>) : Int = floor(input.average()).toInt()


    fun part1(input: List<Int>): Int = getMedian(input).let { median ->
        input.sumOf { abs(it - median) }
    }

    fun part2(input: List<Int>): Int = getAvg(input).let { avg ->
        minOf(
            input.sumOf { abs(it - avg).let { n->(n*(n+1)/2) } },
            input.sumOf { abs(it - avg - 1).let { n->(n*(n+1)/2) } }
        )
    }

    readInput("Day07_test")
        .parse()
        .also(verify(::part1) returns 37)
        .also(verify(::part2) returns 168)

    readInput("Day07")
        .parse()
        .also(printResultOf(::part1))
        .also(printResultOf(::part2))

}

private fun List<String>.parse() = first().split(",").toInts()


