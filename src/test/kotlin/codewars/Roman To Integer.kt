package codewars

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

const val roman3 = "III"
const val roman58 = "LVIII"
const val roman777 = "DCCLXXVII"
const val roman1776 = "MDCCLXXVI"
const val roman1994 = "MCMXCIV"

class RomanToInteger : DescribeSpec(
    {
        describe("roman to integer") {
            it("should convert roman numeral to integer") {
                romanToInt(roman3) shouldBe 3
                romanToInt(roman58) shouldBe 58
                romanToInt(roman777) shouldBe 777
                romanToInt(roman1776) shouldBe 1776
                romanToInt(roman1994) shouldBe 1994
            }
        }
    },
)

/**
Roman numerals are represented by seven different symbols: I, V, X, L, C, D and M.
Symbol       Value
I             1
V             5
X             10
L             50
C             100
D             500
M             1000

For example, 2 is written as II in Roman numeral, just two ones added together. 12 is written as XII, which is
simply X + II. The number 27 is written as XXVII, which is XX + V + II.

Roman numerals are usually written largest to smallest from left to right. However, the numeral for four is not IIII.
Instead, the number four is written as IV. Because the one is before the five we subtract it making four. The same
principle applies to the number nine, which is written as IX. There are six instances where subtraction is used:

- I can be placed before V (5) and X (10) to make 4 and 9.
- X can be placed before L (50) and C (100) to make 40 and 90.
- C can be placed before D (500) and M (1000) to make 400 and 900.

Given a roman numeral, convert it to an integer.
 **/
fun romanToInt(s: String): Int {
    var sum = 0

    var previousValue = ' '
    var currentValue: Char

    for (i in s.indices) {
        if (i > 0) {
            previousValue = s.elementAt(i - 1)
        }
        currentValue = s.elementAt(i)

        when {
            previousValue == 'I' && (currentValue == 'V' || currentValue == 'X') -> {
                sum -= 2
            }

            previousValue == 'X' && (currentValue == 'L' || currentValue == 'C') -> {
                sum -= 20
            }
            previousValue == 'C' && (currentValue == 'D' || currentValue == 'M') -> {
                sum -= 200
            }
        }

        sum += when (s.elementAt(i)) {
            'I' -> 1
            'V' -> 5
            'X' -> 10
            'L' -> 50
            'C' -> 100
            'D' -> 500
            'M' -> 1000
            else -> 0
        }
    }
    return sum
}
