import java.io.File

data class BingoNumber(var value: Int, var isSelected: Boolean = false );
data class Grid(var gridPoints: List<List<BingoNumber>>, var isComplete: Boolean = false);


class BingoEngine() {

    private var rawFileContents: List<String> = listOf();
    private var numbers: IntArray = intArrayOf();
    private var selectedNumbers: MutableList<Int> = mutableListOf();
    private var grids: MutableList<Grid> = mutableListOf();

    fun readFile(fileName: String) {
         rawFileContents = File(fileName).bufferedReader().readLines()
    }

    fun parseRawFileContents() {
        numbers = rawFileContents[0].split(",").map { it.toInt() }.toIntArray();

        var row: List<BingoNumber>
        var gridPoints: MutableList<List<BingoNumber>> = mutableListOf()

        for (i in 2 until rawFileContents.size) {
            if (rawFileContents[i] == "") {
                grids.add(Grid(gridPoints))
                gridPoints = mutableListOf()
            } else {
                row = rawFileContents[i].split(" ").map { it.trim() }.filter { it != "" }.map { BingoNumber(it.toInt()) }
                gridPoints.add(row)
            }
        }
    }

    private fun markNumberInGrid(number: Int, grid: Grid) {
        for (row in grid.gridPoints) {
            for (bingoNumber in row) {
                if (bingoNumber.value == number) bingoNumber.isSelected = true
            }
        }
    }

    fun isBingoAlongRows(grid: Grid): Boolean {
        var bingo: Boolean;
        for (row in grid.gridPoints) {
            bingo = true;
            for (value in row) {
                if (!value.isSelected) {
                    bingo = false;
                    break;
                }
            }
            if (bingo) return true;
        }
        return false;
    }

    fun isBingoAlongCols(grid: Grid): Boolean {
        var bingo: Boolean;
        for (x in grid.gridPoints[0].indices) {
            bingo = true;
            for (y in grid.gridPoints.indices) {
                if (!grid.gridPoints[y][x].isSelected) {
                    bingo = false;
                    break;
                }
            }
            if (bingo) return true;
        }
        return false;
    }

    private fun isBingo(grid: Grid): Boolean {
        return (isBingoAlongRows(grid) || isBingoAlongCols(grid))
    }

    private fun sumBoard(grid: Grid): Int {
        var sum: Int = 0;
        for (row in grid.gridPoints) {
            for (value in row) {
                if (!value.isSelected) {
                    sum += value.value
                }
            }
        }
        return sum;
    }

    private fun getRemainingGrids(): List<Grid> {
        return grids.filter { !it.isComplete };
    }

    fun playBingo(): Int {
        var remainingGrids: List<Grid>;
        for (num in numbers) {
            selectedNumbers.add(num);
            for (grid in grids) {
                if (!grid.isComplete) {
                    markNumberInGrid(num, grid)
                    if(isBingo(grid)) {
                        grid.isComplete = true;
                        remainingGrids = getRemainingGrids();
                        if (remainingGrids.isEmpty()) {
                            return num * sumBoard(grid);
                        }
                    }
                }
            }
        }
        return 0;
    }
}

fun main(args: Array<String>) {
    val bingoEngine = BingoEngine();
    bingoEngine.readFile("src/main/kotlin/bingo.txt")
    bingoEngine.parseRawFileContents()
    println(bingoEngine.playBingo());
}