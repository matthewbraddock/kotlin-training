package codewars

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

val array1 = intArrayOf(1, 2, 3, 4, 3, 2, 1)
val array2 = intArrayOf(1, 100, 50, -51, 1, 1)
val array3 = intArrayOf(1, 2, 3, 4, 5, 6)
val array4 = intArrayOf(20, 10, 30, 10, 10, 15, 35)
val array5 = intArrayOf(-8505, -5130, 1926, -9026)
val array6 = intArrayOf(2824, 1774, -1490, -9084, -9696, 23094)
val array7 = intArrayOf(4, 5, 6, 7, 8, 9, 10, 9, 8, 7, 6, 5, 4)
val zeroEdgeCaseArray = intArrayOf(20, 10, -10)
val emptyArray = intArrayOf()

class EqualSidesOfAnArray : DescribeSpec(
    {
        describe("find equal sides of an array using O(n**2)") {
            it(
                "should return the lowest index N where the side to the left of N is equal to the side to the " +
                    "right of N using the unoptimized solution",
            ) {
                findEvenIndex(array1) shouldBe 3
                findEvenIndex(array2) shouldBe 1
                findEvenIndex(array3) shouldBe -1
                findEvenIndex(array4) shouldBe 3
                findEvenIndex(array5) shouldBe -1
                findEvenIndex(array6) shouldBe 1
                findEvenIndex(array7) shouldBe 6
                findEvenIndex(emptyArray) shouldBe 0
                findEvenIndex(zeroEdgeCaseArray) shouldBe 0
            }
            describe("find equal sides of an array using O(n)") {
                it(
                    "should return the lowest index N where the side to the left of N is equal to the side to the " +
                        "right of N using the optimized solution",
                ) {
                    optimizedFindEvenIndex(array1) shouldBe 3
                    optimizedFindEvenIndex(array2) shouldBe 1
                    optimizedFindEvenIndex(array3) shouldBe -1
                    optimizedFindEvenIndex(array4) shouldBe 3
                    optimizedFindEvenIndex(array5) shouldBe -1
                    optimizedFindEvenIndex(array6) shouldBe 1
                    optimizedFindEvenIndex(array7) shouldBe 6
                    optimizedFindEvenIndex(emptyArray) shouldBe 0
                }
            }
        }
    },
)

/**
 * https://www.codewars.com/kata/5679aa472b8f57fb8c000047/train/kotlin
 *
 * You are going to be given an array of integers. Your job is to take that array and find an index N where the sum of
 * the integers to the left of N is equal to the sum of the integers to the right of N.
 *
 * If there is no index that would make this happen, return -1.
 *
 * For example:
 * Let's say you are given the array {1,2,3,4,3,2,1}:
 * Your function will return the index 3, because the sum of left side of the index ({1,2,3}) and the sum of the right
 * side of the index ({3,2,1}) both equal 6.
 *
 * Let's look at another one.
 * You are given the array {1,100,50,-51,1,1}:
 * Your function will return the index 1, because the sum of left side of the index ({1}) and the sum of the right side
 * of the index ({50,-51,1,1}) both equal 1.
 *
 * Last one:
 * You are given the array {20,10,-80,10,10,15,35}
 * At index 0 the left side is {}
 * The right side is {10,-80,10,10,15,35}
 * They both are equal to 0 when added. (Empty arrays are equal to 0 in this problem)
 * Index 0 is the place where the left side and right side are equal.
 *
 * Note: Please remember that in most languages the index of an array starts at 0.
 *
 * Input
 * An integer array of length 0 < arr < 1000. The numbers in the array can be any integer positive or negative.
 *
 * Output
 * The lowest index N where the side to the left of N is equal to the side to the right of N. If you do not find an
 * index that fits these rules, then you will return -1.
 *
 * Note
 * If you are given an array with multiple answers, return the lowest correct index.
 */

// NOTES:
// First solution was to brute force via looping the array and for each item in the array calculate the value
// on the left and right side value which is Quadratic Time O(n²)
fun findEvenIndex(arr: IntArray): Int {
    if (arr.isEmpty()) {
        return 0
    }

    var leftHandSum = 0
    for (i in 0..arr.indices.last) {
        var rightHandSum = 0

        for (j in i + 1..arr.indices.last) {
            rightHandSum += arr[j]
        }

        if (leftHandSum == rightHandSum) {
            return i
        }

        // Update leftHandSum AFTER the check
        leftHandSum += arr[i]
    }

    return -1
}

// NOTES:
// Second solution: Optimized from O(n²) to Linear Time O(n) by calculating the total sum once,
// then deriving the right sum mathematically instead of recalculating it each time.
//
// Key insight: totalSum = leftSum + arr[i] + rightSum
// Therefore: rightSum = totalSum - leftSum - arr[i]
//
// Special handling for index 0 where leftSum = 0

fun optimizedFindEvenIndex(arr: IntArray): Int {
    if (arr.isEmpty()) {
        return 0
    }

    var leftHandSum = 0

    val arrayTotalSum = arr.sum()

    if (arr.sum() - arr[0] == 0) {
        return 0
    }

    for (i in 1..arr.indices.last) {
        leftHandSum += arr[i - 1]

        val rightHandSum = arrayTotalSum - leftHandSum - arr[i]

        if (leftHandSum == rightHandSum) {
            return i
        }
    }

    return -1
}
