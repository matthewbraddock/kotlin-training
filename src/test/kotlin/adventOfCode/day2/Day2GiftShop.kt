package adventOfCode.day2

import java.io.File
import io.kotest.core.spec.style.DescribeSpec

class Day2GiftShop : DescribeSpec(
    {
        describe("given a list of IDs") {
            it("add up all invalid IDs and return the result") {
                sumOfInvalidIds()
                println()
            }
            it("add up all invalid IDs and return the result for part 2") {

                sumOfInvalidIdsPart2()
                println()
            }
        }
    },
)

/**
You get inside and take the elevator to its only other stop: the gift shop. "Thank you for visiting the North Pole!"
gleefully exclaims a nearby sign. You aren't sure who is even allowed to visit the North Pole, but you know you can
access the lobby through here, and from there you can access the rest of the North Pole base.

As you make your way through the surprisingly extensive selection, one of the clerks recognizes you and asks for your
help.

As it turns out, one of the younger Elves was playing on a gift shop computer and managed to add a whole bunch of
invalid product IDs to their gift shop database! Surely, it would be no trouble for you to identify the invalid product
IDs for them, right?

They've even checked most of the product ID ranges already; they only have a few product ID ranges (your puzzle input)
that you'll need to check. For example:

11-22,95-115,998-1012,1188511880-1188511890,222220-222224,
1698522-1698528,446443-446449,38593856-38593862,565653-565659,
824824821-824824827,2121212118-2121212124
(The ID ranges are wrapped here for legibility; in your input, they appear on a single long line.)

The ranges are separated by commas (,); each range gives its first ID and last ID separated by a dash (-).

Since the young Elf was just doing silly patterns, you can find the invalid IDs by looking for any ID which is made
only of some sequence of digits repeated twice. So, 55 (5 twice), 6464 (64 twice), and 123123 (123 twice) would all be
invalid IDs.

None of the numbers have leading zeroes; 0101 isn't an ID at all. (101 is a valid ID that you would ignore.)

Your job is to find all of the invalid IDs that appear in the given ranges.

What do you get if you add up all of the invalid IDs?

**/

/**
 NOTES:
- Invalid IDs ID are  made only of some sequence of digits repeated twice
 - 11-22 has two invalid IDs, 11 and 22
 - 95-115 has one invalid ID, 99
 - 998-1012 has one invalid ID, 1010.
 - 1188511880-1188511890 has one invalid ID, 1188511885.
 - 222220-222224 has one invalid ID, 222222.
 - 1698522-1698528 contains no invalid IDs.
 - 446443-446449 has one invalid ID, 446446.
 - 38593856-38593862 has one invalid ID, 38593859.
 The rest of the ranges contain no invalid IDs.
 Adding up all the invalid IDs in this example produces 1227775554.

**/

fun sumOfInvalidIds(): Long {
    val inputFile = File("src/test/kotlin/adventOfCode/day2/input.txt")

    try {
        inputFile.readText()
    } catch (e: Exception) {
        println("Error reading file: ${e.message}")
    }

    var answer = 0.toLong()

    inputFile.useLines { lines ->
        lines.forEach { line ->
            // Split the line into an array like 8284583-8497825 , 7171599589-7171806875
            val ranges = line.split(",")
            println(ranges)

            // Actually breaks up 8284583-8497825 into 8284583 and 8497825
            ranges.forEachIndexed { i, rangeStr ->
                val (lowStr, highStr) = rangeStr.split("-")
                val lower = lowStr.toLong()
                val upper = highStr.toLong()

                for (id in lower..upper) {
                    val idStr = id.toString()
                    if (idStr.length % 2 != 0) {
                        answer += 0
                    } else {
                        val halfwayPoint = idStr.length / 2
                        val firstHalf = idStr.substring(0, halfwayPoint)
                        val lastHalf = idStr.substring(halfwayPoint)

                        if (firstHalf == lastHalf) {
                            answer += id
                        }
                    }
                }
            }
        }
    }

    println("The sum of all invalid IDs is: $answer")
    return answer
}

/**
 * Now, an ID is invalid if it is made only of some sequence of digits repeated at least twice.
 * So, 12341234 (1234 two times), 123123123 (123 three times), 1212121212 (12 five times), and
 * 1111111 (1 seven times) are all invalid IDs.
 *
 * From the same example as before:
 *
 * 11-22 still has two invalid IDs, 11 and 22.
 * 95-115 now has two invalid IDs, 99 and 111.
 * 998-1012 now has two invalid IDs, 999 and 1010.
 * 1188511880-1188511890 still has one invalid ID, 1188511885.
 * 222220-222224 still has one invalid ID, 222222.
 * 1698522-1698528 still contains no invalid IDs.
 * 446443-446449 still has one invalid ID, 446446.
 * 38593856-38593862 still has one invalid ID, 38593859.
 * 565653-565659 now has one invalid ID, 565656.
 * 824824821-824824827 now has one invalid ID, 824824824.
 * 2121212118-2121212124 now has one invalid ID, 2121212121.
 *
 * Adding up all the invalid IDs in this example produces 4174379265.
 */
fun sumOfInvalidIdsPart2(): Long {
    val inputFile = File("src/test/kotlin/adventOfCode/day2/input.txt")

    try {
        inputFile.readText()
    } catch (e: Exception) {
        println("Error reading file: ${e.message}")
    }

    var answer = 0.toLong()

    inputFile.useLines { lines ->
        lines.forEach { line ->
            // Split the line into an array like 8284583-8497825 , 7171599589-7171806875
            val ranges = line.split(",")
            println(ranges)

            // Actually breaks up 8284583-8497825 into 8284583 and 8497825
            ranges.forEachIndexed { _, rangeStr ->
                // Break each range e.g. "8284583-8497825" into lower and upper bounds
                val (lowStr, highStr) = rangeStr.split("-")
                val lower = lowStr.toLong()
                val upper = highStr.toLong()

                for (id in lower..upper) {
                    val idStr = id.toString()
                    // Only need to check chunks up to half the string length,
                    // since a sequence must repeat at least twice
                    val halfwayPoint = idStr.length / 2

                    // Try each possible chunk length from 1 up to half the string
                    for (chunk in 1..halfwayPoint) {
                        // Chunk must divide evenly into the total length
                        if (idStr.length % chunk != 0) continue

                        // Rotate the string by moving the first `chunk` chars to the back
                        // e.g. "824824824" with chunk 3 -> "824824" + "824" = "824824824"
                        val newString = idStr.substring(chunk) + idStr.substring(0, chunk)

                        // If the rotated string matches the original, the sequence repeats
                        if (newString == idStr) {
                            answer += id
                            break // No need to check other chunks
                        }
                    }
                }
            }
        }
    }

    println("The sum of all invalid IDs for part 2 is: $answer")
    return answer
}
