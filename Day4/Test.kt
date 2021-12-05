import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BingoEngineTest {

    @Test
    fun detectBingoAlongRow() {
        val row1: List<BingoNumber> = listOf(BingoNumber(1, true), BingoNumber(2, true), BingoNumber(3, false));
        val row2: List<BingoNumber> = listOf(BingoNumber(4, false), BingoNumber(5, false), BingoNumber(6, true));
        val row3: List<BingoNumber> = listOf(BingoNumber(7, true), BingoNumber(8, true), BingoNumber(9, true));
        val grid = Grid(listOf(row1, row2, row3));

        val bingoEngine = BingoEngine()

        assertTrue { bingoEngine.isBingoAlongRows(grid) }
    }

    @Test
    fun detectNoBingoAlongRow() {
        val row1: List<BingoNumber> = listOf(BingoNumber(1, true), BingoNumber(2, true), BingoNumber(3, false));
        val row2: List<BingoNumber> = listOf(BingoNumber(4, true), BingoNumber(5, false), BingoNumber(6, true));
        val row3: List<BingoNumber> = listOf(BingoNumber(7, false), BingoNumber(8, true), BingoNumber(9, true));
        val grid = Grid(listOf(row1, row2, row3));

        val bingoEngine = BingoEngine()

        assertFalse { bingoEngine.isBingoAlongRows(grid) }
    }

    @Test
    fun detectBingoAlongCol() {
        val row1: List<BingoNumber> = listOf(BingoNumber(1, true), BingoNumber(2, false), BingoNumber(3, true));
        val row2: List<BingoNumber> = listOf(BingoNumber(4, false), BingoNumber(5, false), BingoNumber(6, true));
        val row3: List<BingoNumber> = listOf(BingoNumber(7, false), BingoNumber(8, true), BingoNumber(9, true));
        val grid = Grid(listOf(row1, row2, row3));

        val bingoEngine = BingoEngine()

        assertTrue { bingoEngine.isBingoAlongCols(grid) }
    }

    @Test
    fun detectNoBingoAlongCol() {
        val row1: List<BingoNumber> = listOf(BingoNumber(1, true), BingoNumber(2, false), BingoNumber(3, true));
        val row2: List<BingoNumber> = listOf(BingoNumber(4, false), BingoNumber(5, false), BingoNumber(6, true));
        val row3: List<BingoNumber> = listOf(BingoNumber(7, false), BingoNumber(8, true), BingoNumber(9, false));
        val grid = Grid(listOf(row1, row2, row3));

        val bingoEngine = BingoEngine()

        assertFalse { bingoEngine.isBingoAlongCols(grid) }
    }
}