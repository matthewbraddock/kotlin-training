package codewars

import java.util.HashMap
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

val set = emptySet<Int>()

val arrayDuplicate1 = intArrayOf(1, 2, 3, 1)
val noDuplicateArray = intArrayOf(1, 2, 3, 4)
val multipleDuplicateArray = intArrayOf(1, 1, 1, 3, 3, 4, 3, 2, 4, 2)

class ContainsDuplicate : DescribeSpec(
    {
        describe("contains duplicate") {
            it("should return true if any value appears at least twice in the array") {
                containsDuplicateCheat(arrayDuplicate1) shouldBe true
                containsDuplicateCheat(noDuplicateArray) shouldBe false
                containsDuplicateCheat(multipleDuplicateArray) shouldBe true

                containsDuplicate(arrayDuplicate1) shouldBe true
                containsDuplicate(noDuplicateArray) shouldBe false
                containsDuplicate(multipleDuplicateArray) shouldBe true

                containsDuplicateHashMap(arrayDuplicate1) shouldBe true
                containsDuplicateHashMap(noDuplicateArray) shouldBe false
                containsDuplicateHashMap(multipleDuplicateArray) shouldBe true
            }
        }
    },
)

/**
 * Given an integer array nums, return true if any value appears at least twice in the array, and return false if every
 * element is distinct.
 */

fun containsDuplicateCheat(nums: IntArray): Boolean {
    return nums.size != nums.distinct().size
}

fun containsDuplicate(nums: IntArray): Boolean {
    return nums.size != nums.toSet().size
}

fun containsDuplicateHashMap(nums: IntArray): Boolean {
    val hashMap = HashMap<Int, Unit>()

    for (num in nums) {
        if (!hashMap.containsKey(num)) {
            hashMap[num] = Unit
        } else {
            return true
        }
    }
    return false
}
