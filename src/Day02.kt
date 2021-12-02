import ExpectedResultChecker.Companion.verify

fun main() {

    data class State(val aim: Int = 0, val horizontalPos: Int = 0, val depth: Int = 0)

    fun getSumOfUnitsForEachCommand(input: List<Pair<String, Int>>): Map<String, Int> =
        input.groupBy({ it.first }, { it.second })
            .mapValues { it.value.sum() }
            .withDefault { 0 }

    fun part1(input: List<Pair<String, Int>>): Int =
        input.let(::getSumOfUnitsForEachCommand)
            .let { it.getValue("forward") * (it.getValue("down") - it.getValue("up")) }

    fun part2(input: List<Pair<String, Int>>): Int =
        input.fold(State()) { state, (strCmd, units) ->
            when (strCmd) {
                "up" -> state.copy(aim = state.aim - units)
                "down" -> state.copy(aim = state.aim + units)
                "forward" -> state.copy(
                    horizontalPos = state.horizontalPos + units,
                    depth = state.depth + (state.aim * units)
                )
                else -> throw IllegalArgumentException("Unknown command $strCmd")
            }
        }.let { (_, horizontalPos, depth) -> horizontalPos * depth }

    readInput("Day02_test")
        .map(::asCommandAndUnit)
        .also(verify(::part1) returns 150)
        .also(verify(::part2) returns 900)

    readInput("Day02")
        .map(::asCommandAndUnit)
        .also(printResultOf(::part1))
        .also(printResultOf(::part2))

}

private fun asCommandAndUnit(it: String) = it.split(" ").let { it[0] to it[1].toInt() }


