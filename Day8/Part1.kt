import java.io.File

data class SegmentMapping(val segments: List<String>, val output: List<String>)


class Digit() {

    fun readFile(fileName: String): List<SegmentMapping> {
        return File(fileName).bufferedReader().readLines().map { it.split("|").map { it.trim().split(" ") } }
            .map { SegmentMapping(it[0], it[1]) }
    }

    fun countEasyDigits(outputs: List<List<String>>): Int {
        return outputs.sumOf { it.filter { it.length == 2 || it.length == 3 || it.length == 4 || it.length == 7 }.size }
    }
}

fun main() {
    val digit = Digit()
    val mappings = digit.readFile("src/main/kotlin/digits.txt")
    print(digit.countEasyDigits(mappings.map { it.output }))
}