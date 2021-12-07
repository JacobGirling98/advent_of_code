import java.io.File
import java.lang.Math.abs


class LaserCrab() {

    var crabs: List<Int> = listOf()
    var median: Int = 0

    fun readFile(fileName: String) {
        crabs = File(fileName).useLines { it.first().split(",").map { it.toInt() }.sorted() }
    }

    fun findMedian() {
        val mid: Int = crabs.size / 2
        if (crabs.size % 2 == 0) {
            median = (crabs[mid] - crabs[mid - 1]) / 2 + crabs[mid - 1]
        } else {
            median = crabs[mid]
        }
    }

    fun calculateLeastFuel(): Int {
        var fuel = 0
        for (crab in crabs) {
            fuel += abs(crab - median)
        }
        return fuel
    }
}

fun main(args: Array<String>) {
    val laser = LaserCrab()
    laser.readFile("src/main/kotlin/crabs.txt")
    println(laser.calculateLeastFuel())


}