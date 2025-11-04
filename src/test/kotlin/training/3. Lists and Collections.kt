package training

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

// Test data
val numbersList = listOf(1, 2, 3, 4, 5)
val emptyList = emptyList<Int>()
val duplicatesList = listOf(1, 2, 2, 3, 3, 3, 4)
val mixedNumbersList = listOf(-5, 10, -3, 7, 0, -1, 8)
val emptyStringList = emptyList<String>()
val stringsList = listOf("apple", "banana", "cherry", "date", "elderberry")
val shortStringsList = listOf("hi", "bye", "cat", "a", "tree")

class ListAndCollections : DescribeSpec(
    {
        describe("sum of list") {
            it("should return the sum of all elements in the list") {
                sumOfList(numbersList) shouldBe 15
                sumOfList(emptyList) shouldBe 0
                sumOfList(mixedNumbersList) shouldBe 16
            }
        }
        describe("filter even numbers") {
            it("should return a list of even numbers from the original list") {
                filterEvenNumbers(numbersList) shouldBe listOf(2, 4)
                filterEvenNumbers(emptyList) shouldBe emptyList()
                filterEvenNumbers(mixedNumbersList) shouldBe listOf(10, 0, 8)
            }
        }
        describe("find strings by length") {
            it("should return a list of strings that are at least the given minimum length") {
                findStringsByLength(emptyStringList, 2) shouldBe emptyList()
                findStringsByLength(stringsList, 4) shouldBe listOf("apple", "banana", "cherry", "date", "elderberry")
                findStringsByLength(shortStringsList, 5) shouldBe emptyList()
            }
        }
        describe("count occurrences") {
            it("should return the count of how many times the target appears in the list") {
                countOccurrences(emptyList, 1) shouldBe 0
                countOccurrences(numbersList, 1) shouldBe 1
                countOccurrences(numbersList, 12) shouldBe 0
                countOccurrences(duplicatesList, 3) shouldBe 3
                countOccurrences(mixedNumbersList, -5) shouldBe 1
            }
        }
        describe("double elements") {
            it("should return a new list with each element doubled") {
                doubleElements(emptyList) shouldBe emptyList()
                doubleElements(numbersList) shouldBe listOf(2, 4, 6, 8, 10)
                doubleElements(duplicatesList) shouldBe listOf(2, 4, 4, 6, 6, 6, 8)
                doubleElements(mixedNumbersList) shouldBe listOf(-10, 20, -6, 14, 0, -2, 16)
            }
        }
        describe("find min and max") {
            it("should return a Pair containing the minimum and maximum values in the list, or null if the list is empty") {
                findMinMax(emptyList) shouldBe null
                findMinMax(numbersList) shouldBe Pair(1, 5)
                findMinMax(duplicatesList) shouldBe Pair(1, 4)
                findMinMax(mixedNumbersList) shouldBe Pair(-5, 10)
            }
        }
        describe("group by first letter") {
            it("should return a Map grouping strings by their first letter") {
                groupByFirstLetter(emptyStringList) shouldBe emptyMap()
                groupByFirstLetter(stringsList) shouldBe mapOf(
                    'a' to listOf("apple"),
                    'b' to listOf("banana"),
                    'c' to listOf("cherry"),
                    'd' to listOf("date"),
                    'e' to listOf("elderberry")
                )
                groupByFirstLetter(shortStringsList) shouldBe mapOf(
                    'h' to listOf("hi"),
                    'b' to listOf("bye"),
                    'c' to listOf("cat"),
                    'a' to listOf("a"),
                    't' to listOf("tree")
                )
            }
        }
        describe("most frequent element") {
            it("should return the element that appears most frequently in the list, or null if the list is empty") {
                mostFrequentElement(emptyList) shouldBe null
                mostFrequentElement(duplicatesList) shouldBe 3
                mostFrequentElement(listOf(1, 1, 2, 2)) shouldBe 1
                mostFrequentElement(mixedNumbersList) shouldBe -5
            }
        }
    },
)

/**
 * Problem 1: Sum all elements in a list
 * Given a list of integers, return the sum of all elements.
 * If the list is empty, return 0.
 *
 * Example:
 * sumOfList([1, 2, 3, 4]) -> 10
 * sumOfList([]) -> 0
 */

// Braddock notes: You can skip checking if the list is empty because we've already set sum to 0, and if the list is
// empty, the loop will run 0 times, leaving sum as 0
fun sumOfList(numbers: List<Int>): Int {
    var sum = 0

    for (number in numbers) {
        sum += number
    }
    return sum
}

/**
 * Problem 2: Filter even numbers
 * Given a list of integers, return a new list containing only the even numbers.
 * Maintain the original order.
 *
 * Example:
 * filterEvenNumbers([1, 2, 3, 4, 5, 6]) -> [2, 4, 6]
 * filterEvenNumbers([1, 3, 5]) -> []
 */
fun filterEvenNumbers(numbers: List<Int>): List<Int> {
    val evenList = mutableListOf<Int>()

    for (number in numbers) {
        if (number % 2 == 0) {
            evenList.add(number)
        }
    }

    return evenList
}

/**
 * Problem 3: Find strings by length
 * Given a list of strings and a minimum length, return all strings
 * that are at least that length.
 *
 * Example:
 * findStringsByLength(["hi", "hello", "hey", "hibernate"], 4) -> ["hello", "hibernate"]
 * findStringsByLength(["a", "bb", "ccc"], 5) -> []
 */
fun findStringsByLength(strings: List<String>, minLength: Int): List<String> {
    val minStrings = mutableListOf<String>()

    for (string in strings) {
        if (string.length >= minLength) {
            minStrings.add(string)
        }
    }

    return minStrings
}
/**
 * Problem 4: Count occurrences
 * Given a list of integers and a target number, count how many times
 * the target appears in the list.
 *
 * Example:
 * countOccurrences([1, 2, 2, 3, 2, 4], 2) -> 3
 * countOccurrences([1, 2, 3], 5) -> 0
 */
fun countOccurrences(numbers: List<Int>, target: Int): Int {
    var answer = 0

    for (number in numbers) {
        if (number == target) {
            answer += 1
        }
    }
    return answer
}

/**
 * Problem 5: Transform list elements
 * Given a list of integers, return a new list where each element
 * is doubled.
 *
 * Example:
 * doubleElements([1, 2, 3]) -> [2, 4, 6]
 * doubleElements([]) -> []
 */
fun doubleElements(numbers: List<Int>): List<Int> {
    val doubledList = mutableListOf<Int>()

    for (number in numbers) {
        doubledList.add(number * 2)
    }

    return doubledList
}

/**
 * Problem 6: Find min and max
 * Given a non-empty list of integers, return a Pair containing
 * the minimum and maximum values.
 * Return null if the list is empty.
 *
 * Example:
 * findMinMax([3, 1, 4, 1, 5]) -> Pair(1, 5)
 * findMinMax([]) -> null
 */
fun findMinMax(numbers: List<Int>): Pair<Int, Int>? {
    if (numbers.isEmpty()) return null

    val sorted = numbers.sorted()
    return Pair(sorted.first(), sorted.last())
}

/**
 * Problem 7: Group by first letter
 * Given a list of strings, group them by their first letter.
 * Return a Map where keys are characters and values are lists of strings.
 *
 * Example:
 * groupByFirstLetter(["apple", "apricot", "banana", "cherry"]) ->
 *   {'a': ["apple", "apricot"], 'b': ["banana"], 'c': ["cherry"]}
 */
fun groupByFirstLetter(words: List<String>): Map<Char, List<String>> {
    val groupedMap = mutableMapOf<Char, MutableList<String>>()

    for (word in words) {
        if (word.isNotEmpty()) {
            val firstChar = word[0]
            if (groupedMap.containsKey(firstChar)) {
                groupedMap[firstChar]?.add(word)
            } else {
                groupedMap[firstChar] = mutableListOf(word)
            }
        }
    }

    return groupedMap
}

/**
 * CAPSTONE Problem: Most frequent element
 * Given a list of integers, find the element that appears most frequently.
 * If there's a tie, return the first one that reached the max frequency.
 * Return null if the list is empty.
 *
 * Example:
 * mostFrequentElement([1, 2, 2, 3, 3, 3, 4]) -> 3
 * mostFrequentElement([1, 1, 2, 2]) -> 1 (first to reach max frequency)
 * mostFrequentElement([]) -> null
 *
 * Hint: You'll need to count occurrences and track the maximum
 */
fun mostFrequentElement(numbers: List<Int>): Int? {
    if (numbers.isEmpty()) return null

    val frequencyMap = mutableMapOf<Int, Int>()
    var maxFrequency = 0
    var mostFrequent: Int? = null

    for (number in numbers) {
        val count = frequencyMap.getOrDefault(number, 0) + 1
        frequencyMap[number] = count

        if (count > maxFrequency) {
            maxFrequency = count
            mostFrequent = number
        }
    }

    return mostFrequent
}