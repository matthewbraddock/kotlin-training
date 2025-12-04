package adventOfCode.day1

import java.io.File
import io.kotest.core.spec.style.DescribeSpec

class Day1SecretEntrance : DescribeSpec(
    {
        describe("decodes the code for the entrance door") {
            it("should return a int with the correct answer to the secret entrance") {
                secretCodeDecoderPart1()
                secretCodeDecoderPart2()
            }
        }
    },
)

/**
The attached document (your puzzle input) contains a sequence of rotations, one per line, which tell you how to open the
safe. A rotation starts with an L or R which indicates whether the rotation should be to the left (toward lower numbers)
or to the right (toward higher numbers). Then, the rotation has a distance value which indicates how many clicks the dial
should be rotated in that direction.

So, if the dial were pointing at 11, a rotation of R8 would cause the dial to point at 19. After that, a rotation of L19
would cause it to point at 0.

Because the dial is a circle, turning the dial left from 0 one click makes it point at 99. Similarly, turning the dial
right from 99 one click makes it point at 0.

So, if the dial were pointing at 5, a rotation of L10 would cause it to point at 95. After that, a rotation of R5 could
cause it to point at 0.

The dial starts by pointing at 50.
You could follow the instructions, but your recent required official North Pole secret entrance security training
seminar taught you that the safe is actually a decoy. The actual password is the number of times the dial is left
pointing at 0 after any rotation in the sequence.

For example, suppose the attached document contained the following rotations:

L68
L30
R48
L5
R60
L55
L1
L99
R14
L82

Following these rotations would cause the dial to move as follows:

The dial starts by pointing at 50.
The dial is rotated L68 to point at 82.
The dial is rotated L30 to point at 52.
The dial is rotated R48 to point at `0`.
The dial is rotated L5 to point at 95.
The dial is rotated R60 to point at 55.
The dial is rotated L55 to point at `0`.
The dial is rotated L1 to point at 99.
The dial is rotated L99 to point at `0`.
The dial is rotated R14 to point at 14.
The dial is rotated L82 to point at 32.
Because the dial points at `0` a total of `three` times during this process, the password in this example is `3`.

**/

/**
 NOTES:
- Start at 50
- Need to handle left and right
 - Left would be -
 - Right would be +
- Need to handle the range is from 0-99 and 0 goes to
- Once 0 is hit need to increment the value we are going to return as the answer

**/

fun secretCodeDecoderPart1(): Int {
    val inputFile = File("src/test/kotlin/adventOfCode/day1/input.txt")

    try {
        inputFile.readText()
    } catch (e: Exception) {
        println("Error reading file: ${e.message}")
    }

    var currentDailNumber = 50
    var answer = 0

    inputFile.useLines { lines ->
        lines.forEach { line ->
            if (line[0] == 'L') {
                val change = line.drop(1).toInt()

                currentDailNumber = (currentDailNumber - change).mod(100)
                if (currentDailNumber == 0) {
                    answer += 1
                }
            } else {
                val change = line.drop(1).toInt()
                currentDailNumber = (currentDailNumber + change).mod(100)
                if (currentDailNumber == 0) {
                    answer += 1
                }
            }
        }
    }
    println("The part one answer is: $answer")
    return answer
}

// Now we need to increment the answer any time we go past 0 not just if it lands on it
fun secretCodeDecoderPart2(): Int {
    val inputFile = File("src/test/kotlin/adventOfCode/day1/input.txt")

    try {
        inputFile.readText()
    } catch (e: Exception) {
        println("Error reading file: ${e.message}")
    }

    var currentDailNumber = 50
    var answer = 0

    inputFile.useLines { lines ->
        lines.forEach { line ->
            if (line[0] == 'L') {
                val change = line.drop(1).toInt()

                answer += ((100 - currentDailNumber).mod(100) + change).div(100)

                currentDailNumber = (currentDailNumber - change).mod(100)
            } else {
                val change = line.drop(1).toInt()

                answer += (currentDailNumber + change).div(100)

                currentDailNumber = (currentDailNumber + change).mod(100)
            }
        }
    }
    println("The part two answer is: $answer")
    return answer
}
