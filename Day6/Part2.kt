import java.io.File

data class LanternfishGroup(var life: Int, var size: Long)


class LanternfishModel() {

    var initialFish: List<Int> = listOf();
    var lanternfish: MutableList<LanternfishGroup> = mutableListOf();

    init {
        for (i in 0..8) {
            lanternfish.add(LanternfishGroup(i, 0))
        }
    }

    fun readFile(fileName: String) {
        initialFish = File(fileName).useLines { it.first().split(",").map { it.toInt() } }
    }

    fun groupFish() {
        val lives: List<Int> = initialFish.distinct().sorted()
        for (life in lives) {
            val index: Int = lanternfish.indexOf(LanternfishGroup(life, 0))
            lanternfish[index].size = initialFish.filter { it == life }.size.toLong()
        }
    }

    fun simulateLanternfish(turns: Int) {
        var births: Long = 0
        for (i in 0 until turns) {
            for (j in lanternfish.indices) {
                if (j == 0) {
                    births = lanternfish[0].size
                } else {
                    lanternfish[j - 1].size = lanternfish[j].size
                }
            }
            lanternfish[6].size += births
            lanternfish[8].size = births
        }
    }

    fun numberOfLanternfish(): Long {
        return lanternfish.map { it.size }.sum()
    }

}

fun main(args: Array<String>) {
    val model = LanternfishModel()
    model.readFile("src/main/kotlin/fish.txt")
    model.groupFish()
    model.simulateLanternfish(256)
    println(model.numberOfLanternfish())
}