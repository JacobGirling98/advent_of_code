import java.io.File


data class Character(val opener: Char, val closer: Char, val points: Int);


class SyntaxScorer() {

    val characters: List<Character> = listOf(
        Character('(', ')', 1),
        Character('[', ']', 57),
        Character('{', '}', 1197),
        Character('<', '>', 25137)
    )

    fun readLines(fileName: String): List<String> {
        return File(fileName).bufferedReader().readLines();
    }

    fun isOpener(edge: Char): Boolean {
        return characters.filter { it.opener == edge }.isNotEmpty()
    }

    fun getCharacterFromCloser(closer: Char): Character {
        return characters.first { closer == it.closer }
    }

    fun getScore(chunks: String): Int {
        val chunkEdges: MutableList<Char> = mutableListOf();
        for (c in chunks) {
            if (isOpener(c)) {
                chunkEdges.add(c)
            } else {
                val character = getCharacterFromCloser(c)
                if (chunkEdges.last() == character.opener) {
                    chunkEdges.removeLast()
                } else {
                    return character.points
                }
            }
        }
        return 0
    }

    fun totalScore(chunks: List<String>): Int {
        var total = 0
        for (chunk in chunks)
            total += getScore(chunk)
        return total
    }

}


fun main() {
    val syntaxScorer = SyntaxScorer();
    val chunks = syntaxScorer.readLines("src/main/kotlin/syntax.txt")
    println(syntaxScorer.totalScore(chunks))
}