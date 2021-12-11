import java.io.File


data class Octopus(var energy: Int, var hasExploded: Boolean)


class DumboOctopi() {

    var octopi: MutableList<MutableList<Octopus>> = mutableListOf()
    var flashes: Int = 0;

    fun getOctopi(fileName: String) {
        octopi = File(fileName).bufferedReader().readLines()
            .map { it.map { Octopus(Character.getNumericValue(it), false) }.toMutableList() }
            .toMutableList()
    }

    fun aboveEnergyThreshold(energy: Int): Boolean {
        return energy > 9;
    }

    fun increaseEnergy(y: Int, x: Int) {
        if (!octopi[y][x].hasExploded) octopi[y][x].energy++
    }

    fun yInGrid(y: Int): Boolean {
        return 0 <= y && y < octopi.size
    }

    fun xInGrid(x: Int): Boolean {
        return 0 <= x && x < octopi[0].size
    }

    fun cycleRadially(y: Int, x: Int, method: (y: Int, x: Int) -> Unit) {
        if (yInGrid(y - 1) && xInGrid(x - 1))
            method(y - 1, x - 1);
        if (yInGrid(y - 1))
            method(y - 1, x)
        if (yInGrid(y - 1) && xInGrid(x + 1))
            method(y - 1, x + 1)
        if (xInGrid(x + 1))
            method(y, x + 1)
        if (yInGrid(y + 1) && xInGrid(x + 1))
            method(y + 1, x + 1)
        if (yInGrid(y + 1))
            method(y + 1, x)
        if (yInGrid(y + 1) && xInGrid(x - 1))
            method(y + 1, x - 1)
        if (xInGrid(x - 1))
            method(y, x - 1)
    }

    fun energyFlash(y: Int, x: Int) {
        octopi[y][x].energy = 0
        octopi[y][x].hasExploded = true
        flashes++
        cycleRadially(y, x, ::increaseEnergy)
        cycleRadially(y, x, ::triggerFlashes)
    }

    fun triggerFlashes(y: Int, x: Int) {
        if (aboveEnergyThreshold(octopi[y][x].energy)) {
            energyFlash(y, x)
        }
    }

    fun resetHasExploded() {
        octopi.forEach { it.forEach { it.hasExploded = false } }
    }

    fun allOctopiFlashed(): Boolean {
        return octopi.all { it.all { it.hasExploded } }
    }

    fun simulateOctopi(): Int {
        var steps = 0
        while (true) {
            resetHasExploded()
            for (y in octopi.indices) {
                for (x in octopi[0].indices) {
                    increaseEnergy(y, x)
                    triggerFlashes(y, x)
                }
            }
            steps++
            if (allOctopiFlashed()) return steps
        }
    }
}


fun main() {
    val dumboOctopi = DumboOctopi()
    dumboOctopi.getOctopi("src/main/kotlin/octopi.txt")
    println(dumboOctopi.simulateOctopi())
    println(dumboOctopi.flashes)
}