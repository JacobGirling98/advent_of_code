import java.io.File


data class Character(val opener: Char, val closer: Char, val points: Int);


class SyntaxScorer() {

    val characters: List<Character> = listOf(
        Character('(', ')', 1),
        Character('[', ']', 2),
        Character('{', '}', 3),
        Character('<', '>', 4)
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

    fun getCharacterFromOpener(opener: Char): Character {
        return characters.first { opener == it.opener }
    }

    fun completeLine(chunks: String): Long {
        val chunkEdges: MutableList<Char> = mutableListOf();
        var character: Character;
        for (c in chunks) {
            if (isOpener(c)) {
                chunkEdges.add(c)
            } else {
                character = getCharacterFromCloser(c)
                if (chunkEdges.last() == character.opener) {
                    chunkEdges.removeLast()
                } else {
                    return 0
                }
            }
        }
        var score: Long = 0;
        while (chunkEdges.size > 0) {
            score *= 5;
            character = getCharacterFromOpener(chunkEdges.last())
            score += character.points.toLong()
            chunkEdges.removeLast()
        }
        return score
    }

    fun totalScore(chunks: List<String>): Long {
        val scores = chunks.map { completeLine(it) }.filter { it != 0L }.sorted()
        return scores[scores.size / 2];
    }

}


fun main() {
    val syntaxScorer = SyntaxScorer();
    val chunks = syntaxScorer.readLines("src/main/kotlin/syntax.txt")
    println(syntaxScorer.totalScore(chunks))
}