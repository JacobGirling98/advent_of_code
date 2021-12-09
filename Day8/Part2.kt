import java.io.File

data class Instructions(val segments: List<String>, val output: List<String>)
data class Pairing(val chars: List<Char>, val segments: List<Int>)
data class DigitMapping(val digit: Int, val mapping: List<Int>)
data class SegmentMapping(val segment: String, val digit: Int)


class Digit() {

    var sixSegmentDigits: List<DigitMapping> = listOf(
        DigitMapping(0, listOf(1, 1, 1, 0, 1, 1, 1)),
        DigitMapping(6, listOf(1, 1, 0, 1, 1, 1, 1)),
        DigitMapping(9, listOf(1, 1, 1, 1, 0, 1, 1))
    )
    var fiveSegmentDigits: List<DigitMapping> = listOf(
        DigitMapping(2, listOf(1, 0, 1, 1, 1, 0, 1)),
        DigitMapping(3, listOf(1, 0, 1, 1, 0, 1, 1)),
        DigitMapping(5, listOf(1, 1, 0, 1, 0, 1, 1)),
    )

    fun readFile(fileName: String): List<Instructions> {
        return File(fileName).bufferedReader().readLines().map { it.split("|").map { it.trim().split(" ") } }
            .map { Instructions(it[0], it[1]) }
    }

    fun charInPairing(character: Char, pairing: Pairing): Boolean {
        return character in pairing.chars;
    }

    fun pairingInSegment(pairing: Pairing, segment: String): Boolean {
        return pairing.chars.all { it in segment }
    }

    fun pairingPartiallyInSegment(pairing: Pairing, segment: String): Boolean {
        return pairing.chars.any { it in segment }
    }

    // not proud of this, but was behind!
    fun decodeOutput(mappings: List<Instructions>): Int {
        var sumOfOutputs: Int = 0
        for (mapping in mappings) {
            val solution: MutableList<Any> = (0..6).map { 0 }.toMutableList();
            val segments: List<String> = mapping.segments.sortedBy { it.length }
            var pairings: MutableList<Pairing> = mutableListOf()

            // get first pairing
            pairings.add(Pairing(segments[0].map { it }, listOf(2, 5)))
            solution[2] = pairings[0]
            solution[5] = pairings[0]

            // get confirmed segment
            val firstSegment: Char = segments[1].filter { !charInPairing(it, pairings[0]) }[0]
            solution[0] = firstSegment

            // get second pairing
            pairings.add(Pairing(segments[2].filter { !charInPairing(it, pairings[0]) }.map { it }, listOf(1, 3)))
            solution[1] = pairings[1]
            solution[3] = pairings[1]

            // get final pairing
            pairings.add(Pairing(segments[9].filter {
                !charInPairing(it, pairings[0]) && !charInPairing(
                    it,
                    pairings[1]
                ) && it != firstSegment
            }.map { it }, listOf(4, 6)))
            solution[4] = pairings[2]
            solution[6] = pairings[2]

            // get 6 segment digit and construct
            var sixSegmentDigit: String = segments.first { it.length == 6 }.filter { it != firstSegment }
            val constructedSixSegmentDigit: MutableList<Any> = mutableListOf(firstSegment, 0, 0, 0, 0, 0, 0)

            // find left-over segment - sixSegmentDigit will be a single char
            for (pairing in pairings) {
                if (pairingInSegment(pairing, sixSegmentDigit)) {
                    constructedSixSegmentDigit[pairing.segments[0]] = pairing
                    constructedSixSegmentDigit[pairing.segments[1]] = pairing
                    sixSegmentDigit = sixSegmentDigit.filter { !charInPairing(it, pairing) }
                }
            }

            // determine the correct digit
            var correctDigit: MutableList<DigitMapping> = mutableListOf()
            for (d in sixSegmentDigits) {
                var isValid = true
                for (i in 0..6) {
                    if (d.mapping[i] == 0 && constructedSixSegmentDigit[i] != 0) isValid = false
                }
                if (isValid) correctDigit.add(d)
            }

            // determine segment which is missing
            val missingSegment =
                (0..6).first { correctDigit[0].mapping[it] != 0 && constructedSixSegmentDigit[it] == 0 }

            // sixDigitSegment is the letter, missingSegment is the index
            solution[missingSegment] = sixSegmentDigit.single();
            var pairingToUpdate = pairings.first { missingSegment in it.segments }
            solution[pairingToUpdate.segments.first { it != missingSegment }] =
                pairingToUpdate.chars.first { it != sixSegmentDigit.single() }


            // get 5 segment digit and construct
            var fiveSegmentDigit: String = segments.filter {
                it.length == 5
                        && pairingInSegment(pairingToUpdate, it)
                        && pairingPartiallyInSegment(pairings[0], it)
                        && pairingPartiallyInSegment(pairings[1], it)
                        && pairingPartiallyInSegment(pairings[2], it)
            }.first()
            val constructedFiveSegmentDigit: MutableList<Any> = mutableListOf(firstSegment, 0, 0, 0, 0, 0, 0)
            for (i in 0..6) {
                if (solution[i] != 0 && solution[i] is Char) constructedFiveSegmentDigit[i] = solution[i]
            }

            // determine the correct digit
            correctDigit = mutableListOf()
            for (d in fiveSegmentDigits) {
                var isValid = true
                for (i in 0..6) {
                    if (d.mapping[i] == 0 && constructedFiveSegmentDigit[i] != 0) isValid = false
                }
                if (isValid) correctDigit.add(d)
            }

            // determine segments which are missing
            val missingSegments = (0..6).filter { correctDigit[0].mapping[it] != 0 && constructedFiveSegmentDigit[it] == 0 }

            // deduce all mappings
            for (m in missingSegments) {
                val foundPairing = pairings.first { m in it.segments }
                solution[m] = foundPairing.chars.first { it in fiveSegmentDigit }
                solution[foundPairing.segments.first { it != m }] =  foundPairing.chars.first { it != solution[m] }
            }

            // put into segment mappings
            val zero = listOf(solution[0] as Char, solution[1] as Char, solution[2] as Char, solution[4] as Char, solution[5] as Char, solution[6] as Char)
            val one = listOf(solution[2] as Char, solution[5] as Char)
            val two = listOf(solution[0] as Char, solution[2] as Char, solution[3] as Char, solution[4] as Char, solution[6] as Char)
            val three = listOf(solution[0] as Char, solution[2] as Char, solution[3] as Char, solution[5] as Char, solution[6] as Char)
            val four = listOf(solution[1] as Char, solution[2] as Char, solution[3] as Char, solution[5] as Char)
            val five = listOf(solution[0] as Char, solution[1] as Char, solution[3] as Char, solution[5] as Char, solution[6] as Char)
            val six = listOf(solution[0] as Char, solution[1] as Char, solution[3] as Char, solution[4] as Char, solution[5] as Char, solution[6] as Char)
            val seven = listOf(solution[0] as Char, solution[2] as Char, solution[5] as Char)
            val eight = listOf(solution[0] as Char, solution[1] as Char, solution[2] as Char, solution[3] as Char, solution[4] as Char, solution[5] as Char, solution[6] as Char)
            val nine = listOf(solution[0] as Char, solution[1] as Char, solution[2] as Char, solution[3] as Char, solution[5] as Char, solution[6] as Char)
            val segmentToDigits= listOf(
                zero.sorted(), one.sorted(), two.sorted(), three.sorted(), four.sorted(), five.sorted(), six.sorted(), seven.sorted(), eight.sorted(), nine.sorted()
            )

            // get output number
            var output: String = ""
            for (digit in mapping.output) {
                val segmentDigitMapping = digit.map { it }.sorted()
                var actualDigit: Int = -1
                for (i in 0..9) {
                    if (segmentDigitMapping == segmentToDigits[i]) {
                        actualDigit = i
                    }
                }
                output += actualDigit.toString()
            }
            sumOfOutputs += output.toInt()
        }
        return sumOfOutputs
    }
}

fun main() {
    val digit = Digit()
    val mappings = digit.readFile("src/main/kotlin/digits.txt")
    println(digit.decodeOutput(mappings))
}