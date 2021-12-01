fun main() {
    fun part1(input: List<Int>): Int = input.windowed(2).count { (prev, cur) -> cur > prev }

    fun part2(input: List<Int>): Int = input
        .windowed(size = 3) { it.sum() }
        .windowed(2).count { (prev, cur) -> cur > prev }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput.toInts()) == 7)
    check(part2(testInput.toInts()) == 5)

    val input = readInput("Day01")
    println(part1(input.toInts()))
    println(part2(input.toInts()))
}
