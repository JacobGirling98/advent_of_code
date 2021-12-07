import java.io.File
import kotlin.math.roundToInt


class LaserCrab() {

    private var crabs: List<Int> = listOf()

    fun readFile(fileName: String) {
        crabs = File(fileName).useLines { it.first().split(",").map { it.toInt() }.sorted() }
    }

    fun findMean(): Int {
        return (crabs.sum().toFloat() / crabs.size).roundToInt()
    }

    private fun calculateLeastFuel(mean: Int): Int {
        return crabs.sumOf { (1..kotlin.math.abs(it - mean)).sum() }
    }

    // not sure why min may give a null here
    fun searchLeastFuel(delta: Int, initialMean: Int): Int? {
        return ((initialMean - delta)..(initialMean + delta)).map { calculateLeastFuel(it) }.minOrNull()
    }
}

fun main() {
    val crabArmy = LaserCrab()
    crabArmy.readFile("src/main/kotlin/crabs.txt")
    val initialMean = crabArmy.findMean();
    // hate that I have to brute force this - the actual min value isn't the mean!!!
    println(crabArmy.searchLeastFuel(5, initialMean))
}