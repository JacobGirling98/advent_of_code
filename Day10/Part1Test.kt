import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SyntaxScorerTest {

    val scorer = SyntaxScorer()

    @Test
    fun isOpenerReturnsTrue() {
        assertTrue(scorer.isOpener('('));
        assertTrue(scorer.isOpener('['));
        assertTrue(scorer.isOpener('{'));
        assertTrue(scorer.isOpener('<'));
    }

    @Test
    fun canGetCharacterFromCloser() {
        assertEquals(Character('(', ')', 3), scorer.getCharacterFromCloser(')'));
        assertEquals(Character('[', ']', 57), scorer.getCharacterFromCloser(']'));
        assertEquals(Character('{', '}', 1197), scorer.getCharacterFromCloser('}'));
        assertEquals(Character('<', '>', 25137), scorer.getCharacterFromCloser('>'));
    }

    @Test
    fun canGetScore() {
        assertEquals(3, scorer.getScore("{[(){})"))
        assertEquals(57, scorer.getScore("{[[[[()]]]]]"))
    }

}