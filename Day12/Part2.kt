import java.io.File

enum class Size {
    SMALL,
    LARGE
}

data class Cave(val name: String, val size: Size, val connections: MutableSet<String>)
data class CaveMapping(val firstCave: String, val secondCave: String)
data class Path(val route: MutableList<String>, var multipleSmallCaves: Boolean = false)

class PassagePathing() {

    private val caves: MutableList<Cave> = mutableListOf()

    private fun sizeOfCave(cave: String): Size {
        return if (cave[0].isUpperCase()) Size.LARGE else Size.SMALL
    }

    private fun getCaveFromName(name: String): Cave {
        return caves.first { it.name == name }
    }

    fun mapCaves(fileName: String) {
        File(fileName).bufferedReader().readLines().forEach { it ->
            val connection = CaveMapping(it.split("-")[0], it.split("-")[1])
            if (connection.firstCave !in caves.map { it.name })
                caves.add(
                    Cave(
                        connection.firstCave,
                        sizeOfCave(connection.firstCave),
                        mutableSetOf(connection.secondCave)
                    )
                )
            if (connection.secondCave !in caves.map { it.name })
                caves.add(
                    Cave(
                        connection.secondCave,
                        sizeOfCave(connection.secondCave),
                        mutableSetOf(connection.firstCave)
                    )
                )
            getCaveFromName(connection.firstCave).connections.add(connection.secondCave)
            getCaveFromName(connection.secondCave).connections.add(connection.firstCave)
        }
    }

    private fun allowedToNavigateTo(caveName: String, path: Path): Boolean {
        return caveName != "start"
                && (
                path.route.none { it == caveName }
                        || !path.multipleSmallCaves
                        || getCaveFromName(caveName).size == Size.LARGE
                )
    }

    private fun areMultipleSmallCaves(path: Path): Boolean {
        val currentSmallCaves = path.route.filter { getCaveFromName(it).size == Size.SMALL }
        return currentSmallCaves.size != currentSmallCaves.toSet().size
    }

    private fun traverseConnections(cave: Cave, path: Path): MutableList<Path> {
        val paths: MutableList<Path> = mutableListOf()
        for (connection in cave.connections) {
            if (allowedToNavigateTo(connection, path)) {
                val newPath = path.copy(
                    route = (path.route + connection).toMutableList(),
                )
                newPath.multipleSmallCaves = areMultipleSmallCaves(newPath)
                if (connection == "end")
                    paths += newPath
                else
                    paths += traverseConnections(getCaveFromName(connection), newPath)
            }
        }
        return paths
    }

    fun navigateCaves(): MutableList<Path> {
        val startingPath = Path(mutableListOf("start"))
        return traverseConnections(getCaveFromName("start"), startingPath)
    }
}


fun main() {
    val pathing = PassagePathing()
    pathing.mapCaves("src/main/kotlin/caves.txt")
    println(pathing.navigateCaves().size)
}