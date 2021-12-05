import java.io.File
import kotlin.math.abs

data class Coordinate(val x: Int, val y: Int)


class VentDetector {

    var ventCoordinates: List<List<Coordinate>> = listOf();
    var grid: MutableList<MutableList<Int>> = mutableListOf();

    fun readFile(fileName: String) {
        ventCoordinates = File(fileName).bufferedReader().readLines().map { it.split("->").map {
            val rawCoordinate = it.split(",").map { it.trim() };
            Coordinate(rawCoordinate[0].toInt(), rawCoordinate[1].toInt())
        } }
    }

    fun instantiateGrid() {
        var maxX = 0;
        var maxY = 0;
        for (lineCoords in ventCoordinates) {
            for (end in lineCoords) {
                if (end.x > maxX) maxX = end.x
                if (end.y > maxY) maxY = end.y
            }
        }
        for (y in 0..maxY) {
            grid.add(MutableList(maxX + 1) { 0 })
        }
    }

    fun drawHorizontalLine(start: Coordinate, end: Coordinate) {
        val lower: Int;
        val upper: Int;
        if (end.x > start.x) {
            lower = start.x;
            upper = end.x;
        } else {
            lower = end.x;
            upper = start.x;
        }
        for (x in lower..upper) {
            grid[start.y][x]++;
        }
    }

    fun drawVerticalLine(start: Coordinate, end: Coordinate) {
        val lower: Int;
        val upper: Int;
        if (end.y > start.y) {
            lower = start.y;
            upper = end.y;
        } else {
            lower = end.y;
            upper = start.y;
        }
        for (y in lower..upper) {
            grid[y][start.x]++;
        }
    }

    fun drawDiagonalLine(start: Coordinate, end: Coordinate) {
        var x: Int = start.x;
        var y: Int = start.y;
        val deltaX: Int = if(end.x > start.x) 1 else -1;
        val deltaY: Int = if(end.y > start.y) 1 else -1;
        while(x != end.x && y != end.y) {
            grid[y][x]++;
            x += deltaX;
            y += deltaY;
        }
        grid[y][x]++;
    }

    fun drawLine(start: Coordinate, end: Coordinate) {
        if (start.x == end.x) {
            drawVerticalLine(start, end)
        } else if (start.y == end.y) {
            drawHorizontalLine(start, end)
        } else if (abs(start.x - end.x) == abs(start.y - end.y)) {
            drawDiagonalLine(start, end)
        }
    }

    fun drawLines() {
        for (vent in ventCoordinates) {
            drawLine(vent[0], vent[1])
        }
    }

    fun findNumberOfIntersections(): Int {
        var sum = 0;
        for (row in grid) {
            for (value in row) {
                if (value > 1) sum++
            }
        }
        return sum;
    }
}

fun main(args: Array<String>) {
    val ventDetector = VentDetector()
    ventDetector.readFile("src/main/kotlin/lines.txt")
    ventDetector.instantiateGrid()
    ventDetector.drawLines()
    println(ventDetector.findNumberOfIntersections());
}