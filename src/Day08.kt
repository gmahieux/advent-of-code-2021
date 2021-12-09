import ExpectedResultChecker.Companion.verify

fun main() {

    fun Pattern.matches(string: String) = this.length == string.length && this.toList().containsAll(string.toList())
    fun List<Pattern>.getNumberforPattern(pattern: Pattern) = this.indexOfFirst { it.matches(pattern) }

    fun intersection(p1: Pattern, p2: Pattern) = p1.filter { it in p2 }

    fun computePatternOrder(patterns: List<Pattern>): List<Pattern> = MutableList(10) { "" }.apply {
        this[1] = patterns.first { it.length == 2 }
        this[4] = patterns.first { it.length == 4 }
        this[7] = patterns.first { it.length == 3 }
        this[8] = patterns.first { it.length == 7 }

        this[9] = patterns.first { it.length == 6 && it.toList().containsAll(this[4].toList() + this[9].toList()) }
        this[0] = patterns.first { it.length == 6 && it != this[9] && it.toList().containsAll(this[1].toList()) }
        this[6] = patterns.first { it.length == 6 && it != this[9] && it != this[0] }

        this[3] = patterns.first { it.length == 5 && it.toList().containsAll(this[1].toList()) }
        this[5] = patterns.first {
            it.length == 5 && it != this[3] && it.toList().containsAll(intersection(this[4], this[6]).toList())
        }
        this[2] = patterns.first { it.length == 5 && it != this[3] && it != this[5] }
    }

    fun part1(input: List<Pair<List<Pattern>, Output>>): Int =
        input.sumOf { (patterns, output) ->
            computePatternOrder(patterns)
                .filterIndexed { index, _ -> index in listOf(1, 4, 7, 8) }
                .sumOf { pattern ->
                    output.count { pattern.matches(it) }
                }
        }

    fun part2(input: List<Pair<List<Pattern>, Output>>): Int =
        input.map { (patterns, output) -> computePatternOrder(patterns) to output }
            .sumOf { (decodedPatterns, output) ->
                output.map(decodedPatterns::getNumberforPattern)
                    .let { (a, b, c, d) -> a * 1000 + b * 100 + c * 10 + d }
            }

    readInput("Day08_test")
        .parse()
        .also(verify(::part1) returns 26)
        .also(verify(::part2) returns 61229)

    readInput("Day08")
        .parse()
        .also(printResultOf(::part1))
        .also(printResultOf(::part2))

}
typealias Pattern = String
typealias Output = List<String>

private fun List<String>.parse(): List<Pair<List<Pattern>, Output>> = map {
    it.split(" | ")
        .map { it.split(" ") }
        .let { (patterns, output) -> patterns to output }

}


