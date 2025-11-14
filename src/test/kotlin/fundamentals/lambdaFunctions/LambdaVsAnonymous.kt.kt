package fundamentals.lambdaFunctions

/**
 * Comparing Lambda expressions with Anonymous Functions
 */

fun main() {
    println("=== Lambda vs Anonymous Function ===\n")

    // 1. Basic syntax comparison
    println("1. Syntax Comparison:")

    // Lambda
    val lambdaSum = { a: Int, b: Int -> a + b }

    // Anonymous function
    val anonymousSum = fun(a: Int, b: Int): Int = a + b

    // Both work the same way when called
    println("Lambda sum: ${lambdaSum(5, 3)}")
    println("Anonymous sum: ${anonymousSum(5, 3)}")

    // 2. Type inference
    println("\n2. Type Inference:")

    // Lambda - return type always inferred
    val lambdaDouble = { x: Int -> x * 2 }

    // Anonymous function - can specify or infer return type
    val anonymousDoubleInferred = fun(x: Int) = x * 2 // Inferred
    val anonymousDoubleExplicit = fun(x: Int): Int { return x * 2 } // Explicit

    println("All produce the same result: ${lambdaDouble(5)}")

    // 3. Single parameter 'it' syntax
    println("\n3. Single Parameter Syntax:")

    val numbers = listOf(1, 2, 3, 4, 5)

    // Lambda can use 'it'
    val evenLambda = numbers.filter { it % 2 == 0 }

    // Anonymous function cannot use 'it' - must declare parameter
    val evenAnonymous = numbers.filter(fun(num): Boolean = num % 2 == 0)

    println("Even numbers (lambda): $evenLambda")
    println("Even numbers (anonymous): $evenAnonymous")

    // 4. Return behavior (simple example - see ReturnBehavior.kt for details)
    println("\n4. Return Behavior Preview:")

    fun processListLambda(list: List<Int>): String {
        list.forEach {
            if (it > 3) return "Found number > 3" // Returns from processListLambda
        }
        return "No number > 3"
    }

    fun processListAnonymous(list: List<Int>): String {
        list.forEach(fun(num) {
            if (num > 3) return // Returns from anonymous function only
        })
        return "Completed processing"
    }

    println("Lambda return: ${processListLambda(listOf(1, 2, 4))}")
    println("Anonymous return: ${processListAnonymous(listOf(1, 2, 4))}")

    // 5. When to use each
    println("\n5. When to Use Each:")

    // Use lambda for simple, concise operations
    val squared = numbers.map { it * it }
    println("Squared (lambda): $squared")

    // Use anonymous function when you need explicit return type or complex return logic
    val processedNumbers = numbers.map(fun(n: Int): String {
        return when {
            n % 2 == 0 -> "even"
            n % 3 == 0 -> "divisible by 3"
            else -> "other"
        }
    })
    println("Processed (anonymous): $processedNumbers")

    // 6. Both can be assigned to function type variables
    println("\n6. Function Type Variables:")

    val operation1: (Int, Int) -> Int = { a, b -> a + b } // Lambda
    val operation2: (Int, Int) -> Int = fun(a, b) = a + b // Anonymous

    println("Both can be used interchangeably: ${operation1(10, 5)} = ${operation2(10, 5)}")
}
