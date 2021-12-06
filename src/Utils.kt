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

fun <T> printResultOf(partBlock: (List<T>) -> Any) = { list: List<T> -> println(partBlock(list)) }

class ExpectedResultChecker<T, R>(val partBlock: (List<T>) -> R) {
    infix fun returns(expectedResult: R) = { list: List<T> ->
        partBlock(list)
            .let { actualResult -> check(actualResult == expectedResult) { "Expected $expectedResult but was $actualResult" } }

    }

    companion object {
        fun <T,R> verify(partBlock: (List<T>) -> R) = ExpectedResultChecker(partBlock)
    }
}

fun List<String>.toNumberMatrix() : List<List<Int>> = this.map { it.windowed(size=2, step = 3) {it.toString().trim().toInt()} }
fun List<List<Int>>.transpose() : List<List<Int>> =List(this[0].size) { mutableListOf<Int>() }
    .apply {
        this@transpose.forEach {
            it.forEachIndexed { index, int -> this[index].add(int) }
        }
    }