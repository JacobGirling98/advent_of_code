import java.io.File


class LaserCrab() {

    private var crabs: List<Int> = listOf()
    private var median: Int = 0

    fun readFile(fileName: String) {
        crabs = File(fileName).useLines { it.first().split(",").map { it.toInt() }.sorted() }
    }

    fun findMedian() {
        val mid: Int = crabs.size / 2
        median = if (crabs.size % 2 == 0) {
            (crabs[mid] - crabs[mid - 1]) / 2 + crabs[mid - 1]
        } else {
            crabs[mid]
        }
    }

    fun calculateLeastFuel(): Int {
        return crabs.sumOf { kotlin.math.abs(it - median) }
    }
}

fun main() {
    val laser = LaserCrab()
    laser.readFile("src/main/kotlin/crabs.txt")
    laser.findMedian()
    println(laser.calculateLeastFuel())
}