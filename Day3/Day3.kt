import java.io.File

data class BinaryRates(val oxygen: String, val co2: String)
data class IntRates(val oxygen: Int, val co2: Int)

class Rates() {

    fun readFile(fileName: String): List<String> {
        return File(fileName).useLines { it.toList() }
    }

    fun generateRates(binaries: List<String>): BinaryRates{
        val oneBinaries: List<String> = binaries.filter { it[0] == '1' }
        val zeroBinaries: List<String> = binaries.filter { it[0] == '0' }
        val oxygen: String;
        val co2: String;
        if (oneBinaries.size >= zeroBinaries.size) {
            oxygen = reduceBinaries(oneBinaries, true)
            co2 = reduceBinaries(zeroBinaries, false)
        } else {
            oxygen = reduceBinaries(zeroBinaries, true)
            co2 = reduceBinaries(oneBinaries, false);
        }
        return BinaryRates(oxygen, co2);
    }

    private fun reduceBinaries(binaries: List<String>, mostCommon: Boolean): String {
        var oneBinaries: List<String>;
        var zeroBinaries: List<String>;
        var retainedBinaries = binaries;
        var colIndex = 1;
        while (retainedBinaries.size > 1) {
            oneBinaries = retainedBinaries.filter { it[colIndex] == '1' }
            zeroBinaries = retainedBinaries.filter { it[colIndex] == '0' }
            retainedBinaries = if (mostCommon) {
                if (oneBinaries.size >= zeroBinaries.size) oneBinaries else zeroBinaries
            } else {
                if (oneBinaries.size < zeroBinaries.size) oneBinaries else zeroBinaries
            }
            colIndex++;
        }
        return retainedBinaries[0];
    }

    private fun binaryToInt(startingBinary: String): Int {
        var binary = startingBinary.toLong();
        var answer = 0;
        var counter = 0;
        var remainder: Long;
        while (binary > 0) {
            remainder = binary % 10;
            binary /= 10;
            answer += (remainder * Math.pow(2.0, counter.toDouble())).toInt()
            counter++
        }
        return answer;
    }

    fun convertBinaryRates(binary: BinaryRates): IntRates = IntRates(binaryToInt(binary.oxygen), binaryToInt(binary.co2));

    fun finalAnswer(rates: IntRates) {
        println(rates.oxygen * rates.co2);
    }
}

fun main(args: Array<String>) {
    val rateGenerator = Rates();
    val binaries = rateGenerator.readFile("src/main/kotlin/binaries.txt");
    val binaryRates = rateGenerator.generateRates(binaries);
    val intRates = rateGenerator.convertBinaryRates(binaryRates);
    rateGenerator.finalAnswer(intRates)
}