import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

fun List<String>.toInts(): List<Int> = this.map { it.toInt() }

fun <T, R> printResultOf(partBlock: (List<T>) -> R) = { list: List<T> -> println(partBlock(list)) }

class ExpectedResultChecker<T, R>(val partBlock: (List<T>) -> R) {
    infix fun returns(expectedResult: R) = { list: List<T> ->
        partBlock(list)
            .let { actualResult -> check(actualResult == expectedResult) { "Expected $expectedResult but was $actualResult" } }

    }

    companion object {
        fun <T> verify(partBlock: (List<T>) -> Int) = ExpectedResultChecker(partBlock)
    }
}
