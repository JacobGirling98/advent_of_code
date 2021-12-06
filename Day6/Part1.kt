import java.io.File

data class Lanternfish(var life: Int)


class LanternfishModel {

    var lanternfish: MutableList<Lanternfish> = mutableListOf();

    fun readFile(fileName: String) {
        lanternfish = File(fileName).useLines { it.first().split(",").map { Lanternfish(it.toInt()) }.toMutableList() }
    }

    fun simulateLanternfish(turns: Int) {
        var fishToAdd: MutableList<Lanternfish>
        for (i in 0 until turns) {
            fishToAdd = mutableListOf()
            for (fish in lanternfish) {
                if (fish.life == 0) {
                    fishToAdd.add(Lanternfish(8))
                    fish.life = 6;
                } else {
                    fish.life--
                }
            }
            lanternfish += fishToAdd
        }
    }

    fun numberOfLanternfish(): Int {
        return lanternfish.size
    }

}

fun main(args: Array<String>) {
    val model = LanternfishModel()
    model.readFile("src/main/kotlin/fish.txt")
    model.simulateLanternfish(80)
    println(model.numberOfLanternfish())
}