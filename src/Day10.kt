import ExpectedResultChecker.Companion.verify


fun main() {
    val openingChars = listOf('{', '[', '(', '<')

    class CorruptionException(val char: Char) : RuntimeException() {
        fun getScore() = when (char) {
            ')' -> 3
            ']' -> 57
            '}' -> 1197
            '>' -> 25137
            else -> throw IllegalStateException("Unexpected char $char")
        }
    }

    fun Char.closingChar(): Char = when (this) {
        '{' -> '}'
        '[' -> ']'
        '(' -> ')'
        '<' -> '>'
        else -> throw java.lang.IllegalStateException("Unexpected char $this")
    }

    fun Char.isClosingCharOf(c: Char) = this == c.closingChar()

    fun tryGetNonClosedChunks(s: String) = runCatching {
        s.fold(listOf<Char>()) { stack, c ->
            if (c in openingChars) {
                stack + c
            } else if (!c.isClosingCharOf(stack.last())) {
                throw CorruptionException(c)
            } else {
                stack.dropLast(1)
            }
        }
    }

    fun getScoreForLine(s: String): Int = tryGetNonClosedChunks(s).fold(
        onSuccess = { 0 },
        onFailure = {
            when (it) {
                is CorruptionException -> it.getScore()
                else -> throw it
            }
        }
    )

    fun getAutocompleteScore(s: String): Long =
        tryGetNonClosedChunks(s).getOrThrow()
            .reversed()
            .fold(0L) { score, char ->
                when (char) {
                    '(' -> (score * 5) + 1
                    '[' -> (score * 5) + 2
                    '{' -> (score * 5) + 3
                    '<' -> (score * 5) + 4
                    else -> score
                }
            }

    fun part1(input: List<String>): Int =
        input.sumOf(::getScoreForLine)

    fun part2(input: List<String>): Long =
        input.filter { getScoreForLine(it) == 0 }
            .map(::getAutocompleteScore)
            .sorted()
            .let {
                it[it.size / 2]
            }


    readInput("Day10_test")
        .also(verify(::part1) returns 26397)
        .also(verify(::part2) returns 288957)

    readInput("Day10")
        .also(printResultOf(::part1))
        .also(printResultOf(::part2))

}

