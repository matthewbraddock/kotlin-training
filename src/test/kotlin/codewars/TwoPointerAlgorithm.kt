package codewars

// Write a function to test if the value of `sentence` is a palindrome.
// Ignore any non-alphabetic characters and treat uppercase and lowercase letters as equivalent.

fun isPalindromeKotlinIdiom(data: String): Boolean {
    // Keep only alphabetic characters and convert to lowercase
    val cleaned = data.filter { it.isLetter() }.lowercase()

    // Compare with its reverse
    return cleaned == cleaned.reversed()
}

fun isPalindromeTwoPointer(data: String): Boolean {
    var x = 0
    var y = data.length - 1

    var answer = true

    while (x <= y) {
        // Moving pointer if not alphabetic
        while (!data[x].isLetter()) {
            x++
        }
        while (!data[y].isLetter()) {
            y--
        }

        // Check if letters match
        if (data[x].equals(data[y], ignoreCase = true)) {
            x++
            y--
            continue
        } else {
            answer = false
            break
        }
    }
    return answer
}

fun main() {
    val sentence = "Too hot to hoot."
    val testcase = "...Mans too hot toh oot snam......"
    val notPalindrome = "Braddock is a \"coder\""
    println("Is a palindrome? ${if (isPalindromeKotlinIdiom(sentence)) "YES" else "no :("}")
    println("Is a palindrome? ${if (isPalindromeTwoPointer(testcase)) "YES" else "no :("}")
    println("Is a palindrome? ${if (isPalindromeTwoPointer(notPalindrome)) "YES" else "no :("}")
}
