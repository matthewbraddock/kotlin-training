package training

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

val array = arrayOf(1, 2, 3, 4)
val reversedArray = arrayOf(4, 2, 3, 1)
val emptyIntArray = emptyArray<Int>()
val duplicateIntsArray = arrayOf(1, 1, 2, 3, 1)
val bigNumbersArray = arrayOf(100, 20, 50, 1050, 9)
val duplicateNumbersArray = arrayOf(10, 10, 10, 10)

val missingNumberArray = intArrayOf(3, 0, 1)
val missingNumberArrayToNine = intArrayOf(9, 6, 4, 2, 3, 5, 7, 0, 1)
val missingNumberArrayTwo = intArrayOf(0, 1)

class ArraysHowDoTheyWork : DescribeSpec(
    {
        describe("every other element of int array") {
            it("should return every other element of int array as string") {
                everyOtherElementOfIntArray(array) shouldBe "[2, 4]"
                everyOtherElementOfIntArray(reversedArray) shouldBe "[1, 2]"
                everyOtherElementOfIntArray(emptyIntArray) shouldBe "[]"
            }
        }
        describe("find element at index") {
            it("should find element at given index in int array") {
                findElementAtIndex(array, 0) shouldBe "1"
                findElementAtIndex(array, 1) shouldBe "2"
                findElementAtIndex(emptyIntArray, 1) shouldBe "Array cannot be empty"
                findElementAtIndex(reversedArray, 7) shouldBe "Array cannot be empty"
            }
        }
        describe("search array for int") {
            it("should return indices of all occurrences of given int in array") {
                searchArrayForInt(duplicateIntsArray, 1) shouldBe "[0, 1, 4]"
                searchArrayForInt(reversedArray, 7) shouldBe "[]"
            }
        }
        describe("largest number in array") {
            it("should return the largest number in the array") {
                largestNumberInArray(bigNumbersArray) shouldBe "1050"
                largestNumberInArray(duplicateNumbersArray) shouldBe "10"
                largestNumberInArray(emptyIntArray) shouldBe "Array cannot be empty"
            }
        }
        describe("unique array") {
            it("should return array with unique elements only") {
                uniqueArray(duplicateIntsArray) shouldBe "[1, 2, 3]"
                uniqueArray(duplicateNumbersArray) shouldBe "[10]"
                uniqueArray(emptyIntArray) shouldBe "[]"
            }
        }
        describe("missing number") {
            it("should return the missing number in the array") {
                missingNumber(missingNumberArray) shouldBe 2
                missingNumber(missingNumberArrayToNine) shouldBe 8
                missingNumber(missingNumberArrayTwo) shouldBe 2
            }
        }
    },
)

fun everyOtherElementOfIntArray(userArray: Array<Int>): String {
    var everyOtherIntArray = emptyArray<Int>()

    for (i in userArray) {
        if (i % 2 == 1) {
            everyOtherIntArray += userArray[i]
        }
    }
    return everyOtherIntArray.contentToString()
}

fun findElementAtIndex(userArray: Array<Int>, index: Int): String {
    if (userArray.isEmpty() || index > userArray.size) {
        return "Array cannot be empty"
    }

    return userArray[index].toString()
}

fun searchArrayForInt(userArray: Array<Int>, userInt: Int): String {
    var intArray = emptyArray<Int>()

    for (i in userArray.indices) {
        if (userArray[i] == userInt) {
            intArray += i
        }
    }
    return intArray.contentToString()
}

fun largestNumberInArray(userArray: Array<Int>): String {
    var largestNumber = 0

    if (userArray.isEmpty()) {
        return "Array cannot be empty"
    }

    for (i in userArray.indices) {
        if (largestNumber < userArray[i]) {
            largestNumber = userArray[i]
        }
    }
    return largestNumber.toString()
}

fun uniqueArray(userArray: Array<Int>): String {
    var distinctArray = emptyArray<Int>()

    for (i in userArray.indices) {
        var isDuplicate = false

        for (j in distinctArray.indices) {
            if (distinctArray[j] == userArray[i]) {
                isDuplicate = true
                break
            }
        }

        if (!isDuplicate) {
            distinctArray += userArray[i]
        }
    }
    return distinctArray.contentToString()
}

fun missingNumber(nums: IntArray): Int {
    nums.sort()

    for (i in nums.indices) {
        if (i != nums[i]) {
            return i
        }
    }
    return nums.size
}
