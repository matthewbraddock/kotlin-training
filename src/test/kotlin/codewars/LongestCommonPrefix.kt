package codewars

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class LongestCommonPrefix : DescribeSpec(
    {
        describe("longest common prefix") {
            it("should find the longest common prefix among an array of strings") {
                longestCommonPrefix(arrayOf("flower", "flow", "flight")) shouldBe "fl"
                longestCommonPrefix(arrayOf("dog", "racecar", "car")) shouldBe ""
                longestCommonPrefix(arrayOf("interspecies", "interstellar", "interstate")) shouldBe "inters"
                longestCommonPrefix(arrayOf("throne", "dungeon")) shouldBe ""
                longestCommonPrefix(arrayOf("throne", "throne")) shouldBe "throne"
            }
        }
    },
)

/**
 * Write a function to find the longest common prefix string amongst an array of strings.
 * If there is no common prefix, return an empty string "".
 *
 * Example 1:
 *
 * Input: strs = ["flower","flow","flight"]
 * Output: "fl"
 * Example 2:
 *
 * Input: strs = ["dog","racecar","car"]
 * Output: ""
 * Explanation: There is no common prefix amongst the input strings.
 **/

fun longestCommonPrefix(strs: Array<String>): String {
    var commonPrefix = ""

    var wordSize = Int.MAX_VALUE

    // Gives us the smallest word size minus 1 and updates wordSize to that value
    for (word in strs) {
        if (wordSize > word.length) {
            wordSize = (word.length - 1)
        }
    }

    for (wordIndex in 0..wordSize) {
        val currentLetter = strs[0][wordIndex]

        for (word in strs) {
            if (word[wordIndex] != currentLetter) {
                return commonPrefix
            }
        }
        commonPrefix += currentLetter
    }
    return commonPrefix
}