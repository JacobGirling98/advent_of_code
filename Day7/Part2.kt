import java.io.File
import kotlin.math.roundToInt


class LaserCrab() {

    var crabs: List<Int> = listOf()
    var mean: Int = 0

    fun readFile(fileName: String) {
        crabs = File(fileName).useLines { it.first().split(",").map { it.toInt() }.sorted() }
    }

    fun findMean() {
        mean = (crabs.sum().toFloat() / crabs.size).roundToInt()
    }

    fun calculateLeastFuel(): Int {
        var fuel = 0
        var distance: Int
        for (crab in crabs) {
            distance = kotlin.math.abs(crab - mean)
            for (delta in 1..distance) {
                fuel += delta
            }
        }
        return fuel
    }

    fun plusMinusMean(delta: Int, initialValue: Int, initialMean: Int): Int {
        var minimumFuel: Int = initialValue;
        var newFuel = 0;
        for (m in (initialMean - delta)..(initialMean + delta)) {
            mean = m
            newFuel = calculateLeastFuel();
            if (newFuel < minimumFuel) {
                minimumFuel = newFuel
            }
        }
        return minimumFuel
    }
}

fun main() {
    val crabArmy = LaserCrab()
    crabArmy.readFile("src/main/kotlin/crabs.txt")
    crabArmy.findMean()
    val initialMin = crabArmy.calculateLeastFuel()
    // hate that I have to brute force this - the actual min value isn't the mean!!!
    println(crabArmy.plusMinusMean(5, initialMin, crabArmy.mean))
}