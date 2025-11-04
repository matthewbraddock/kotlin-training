package training

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Strings : DescribeSpec(
    {
        describe("find character in string") {
            it("should find character correctly") {
                findCharacter(string = "Braddock", character = 'B') shouldBe true
                findCharacter(string = "Braddock", character = 'l') shouldBe false
                findCharacter(string = "Braddock", character = 'b') shouldBe false
            }
        }
        describe("string matching") {
            it("should match strings correctly") {
                stringMatching("Braddock", "Braddock") shouldBe true
                stringMatching("Braddock", "Lisa") shouldBe false
                stringMatching("Braddock", "") shouldBe false
                stringMatching("Braddock", "braddock") shouldBe true
            }
        }
        describe("concatenate two strings") {
            it("should concatenate strings correctly") {
                concatenateTwoString("braddock", "knows how to code?") shouldBe "braddock knows how to code?!"
                concatenateTwoString("Empty String", "") shouldBe "Empty String !"
            }
        }
        describe("reverse string") {
            it("should reverse strings correctly") {
                reverseString("lisa") shouldBe "asil"
                reverseString("Kotlin") shouldBe "niltoK"
            }
        }
        describe("palindrome string") {
            it("should identify palindromes correctly") {
                palindromeString("aba") shouldBe true
                palindromeString("NotApalindrome") shouldBe false
            }
        }
    },
)

fun findCharacter(string: String, character: Char): Boolean {
    for (char in string) {
        if (char == character) {
            return true
        }
    }
    return false
}

fun stringMatching(string1: String, string2: String): Boolean {
    // Don't forget the basics that this by default returns a boolean
    return (string1.equals(string2, ignoreCase = true))
}

fun concatenateTwoString(string1: String, string2: String): String {
    return ("$string1 $string2!")
}

fun reverseString(string: String): String {
    var endString = ""

    for (i in string.length - 1 downTo 0) {
        endString += string[i]
    }
    return endString
}

fun palindromeString(string: String): Boolean {
    var reverseString = ""

    for (i in string.length - 1 downTo 0) {
        reverseString += string[i]
    }

    return (string == reverseString)
}
