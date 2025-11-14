package fundamentals.lambdaFunctions

/**
 * Basic Lambda Syntax and Usage Examples
 */

fun main() {
    // 1. Basic lambda syntax: { parameters -> body }
    val sum = { a: Int, b: Int -> a + b }
    println("Sum of 3 and 5: ${sum(3, 5)}")

    // 2. Lambda with single parameter using 'it'
    val numbers = listOf(1, 2, 3, 4, 5)
    val doubled = numbers.map { it * 2 }
    println("Doubled numbers: $doubled")

    // 3. Lambda with multiple statements
    val printAndDouble = { num: Int ->
        println("Processing: $num")
        num * 2 // Last expression is the return value
    }
    val result = printAndDouble(10)
    println("Result: $result")

    // 4. Lambda with no parameters
    val greet = { println("Hello, World!") }
    greet()

    // 5. Lambda as a variable with explicit type
    val isEven: (Int) -> Boolean = { it % 2 == 0 }
    println("Is 4 even? ${isEven(4)}")

    // 6. Passing lambdas to functions
    val names = listOf("Alice", "Bob", "Charlie", "David")
    val longNames = names.filter { it.length > 4 }
    println("Names longer than 4 chars: $longNames")

    // 7. Trailing lambda syntax
    val sortedByLength = names.sortedBy { it.length }
    println("Sorted by length: $sortedByLength")

    // 8. Lambda with receiver (used in DSLs)
    val buildString = StringBuilder().apply {
        append("Hello")
        append(" ")
        append("Kotlin!")
    }.toString()
    println("Built string: $buildString")

    // 9. Type inference in lambdas
    val multiply = { x: Int, y: Int -> x * y } // Return type inferred as Int
    val divide = { x: Double, y: Double -> x / y } // Return type inferred as Double

    println("10 * 5 = ${multiply(10, 5)}")
    println("10.0 / 3.0 = ${divide(10.0, 3.0)}")
}
