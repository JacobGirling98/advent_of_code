import java.io.File

data class Command(val direction: String, val units: Int)


class Submarine {
    var position: Int = 0;
    var depth: Int = 0;
    var aim: Int = 0;

    private fun moveForward(units: Int) {
        position += units;
        depth += aim * units;
    }

    private fun moveDown(units: Int) {
        aim += units;
    }

    private fun moveUp(units: Int) {
        aim -= units;
    }

    private fun parseInstruction(instruction: String): Command {
        val separatedInstruction: List<String> = instruction.split(" ")
        return Command(separatedInstruction[0], separatedInstruction[1].toInt())
    }

    private fun executeCommand(command: Command) {
        if (command.direction == "forward") {
            moveForward(command.units);
        } else if (command.direction == "down") {
            moveDown(command.units);
        } else if (command.direction == "up") {
            moveUp(command.units);
        }
    }

    fun moveSubmarine(fileName: String) {
        File(fileName).forEachLine {
            val command: Command = parseInstruction(it);
            executeCommand(command)
        }
    }

    fun finalAnswer(): Int {
        return position * depth;
    }
}


fun main(args: Array<String>) {
    val sub: Submarine = Submarine()
    sub.moveSubmarine("src/main/kotlin/instructions.txt");
    println(sub.finalAnswer());
}