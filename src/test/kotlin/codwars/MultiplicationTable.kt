package codwars

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

class MultiplicationTable : DescribeSpec(
    {
        describe("multiplication table") {
            it("should create 2-dimensional array correctly") {
                val result = table(3)

                result shouldHaveSize 3
                result[0] shouldHaveSize 3
                result shouldBe arrayOf(
                    intArrayOf(1, 2, 3),
                    intArrayOf(2, 4, 6),
                    intArrayOf(3, 6, 9)
                )
            }
        }
    },
)

fun table(size: Int): Array<IntArray>{
    val table: Array<IntArray> = Array(size = size) {
        IntArray(size = size) { rowIndex ->
            (it + 1) * (rowIndex + 1)
        }
    }
 return  table
}