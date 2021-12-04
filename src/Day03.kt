import ExpectedResultChecker.Companion.verify

fun main() {
    fun bitsToInt(string: String) = Integer.parseInt(string, 2)
    fun bitsToInt(lessCommonBits: List<Char>) = bitsToInt(String(lessCommonBits.toCharArray()))

    fun part1(input: List<String>): Int =
        input
            .toBitsLists()
            .let { transposedList ->
                transposedList.map(::mostCommonBit) to transposedList.map(::lessCommonBit)
            }.let { (mostCommonBits, lessCommonBits) ->
                bitsToInt(mostCommonBits) to bitsToInt(lessCommonBits)
            }.let { (gammaRate, epsilonRate) -> gammaRate * epsilonRate }


    fun part2(input: List<String>): Int {
        val oxygenNumber = input.filterOnEachBit(::mostCommonBit).first()
        val co2Number = input.filterOnEachBit(::lessCommonBit).first()
        return bitsToInt(oxygenNumber) * bitsToInt(co2Number)
    }

    readInput("Day03_test")
        .also(verify(::part1) returns 198)
        .also(verify(::part2) returns 230)

    readInput("Day03")
        .also(printResultOf(::part1))
        .also(printResultOf(::part2))

}

private fun List<String>.filterOnEachBit(filterFunction: (List<Char>) -> Char, index: Int = 0): List<String> =
    if (this.size > 1) {
        val bitsLists = this.bitsListForIndex(index);
        this.filter { it[index] == filterFunction(bitsLists) }.filterOnEachBit(filterFunction, index + 1)
    } else {
        this
    }

private fun List<String>.bitsListForIndex(index: Int): List<Char> = this.map { it[index] }


private fun List<String>.toBitsLists(): List<List<Char>> = List(this[0].length) { mutableListOf<Char>() }
    .apply {
        this@toBitsLists.forEach {
            it.forEachIndexed { index, char -> this[index].add(char) }
        }
    }

private fun mostCommonBit(bits: List<Char>): Char = if (bits.count { it == '0' } > bits.size / 2) '0' else '1'
private fun lessCommonBit(bits: List<Char>): Char = if (bits.count { it == '0' } > bits.size / 2) '1' else '0'
