package fundamentals.lambdaFunctions

/**
 * Understanding Lambda Return Behavior
 *
 * Key concept: Non-labeled returns in lambdas return from the enclosing function!
 */

// Inline function must be at top level (not inside another function)
inline fun inlineForEach(list: List<Int>, action: (Int) -> Unit) {
    for (item in list) {
        action(item)
    }
}

inline fun crossinlineExample(crossinline action: () -> Unit) {
    val runnable = Runnable {
        action() // crossinline prevents non-local returns here
    }
    runnable.run()
}

fun main() {
    println("=== Lambda Return Behavior ===\n")

    // 1. Basic return behavior difference
    println("1. Basic Return Behavior:")

    fun findFirstNegativeLambda(numbers: List<Int>): String {
        numbers.forEach {
            if (it < 0) return "Found negative: $it" // Returns from findFirstNegativeLambda
        }
        return "No negatives found"
    }

    fun findFirstNegativeAnonymous(numbers: List<Int>): String {
        numbers.forEach(fun(num) {
            if (num < 0) return // Returns from anonymous function only, forEach continues
        })
        return "Finished checking all numbers"
    }

    println("Lambda: ${findFirstNegativeLambda(listOf(1, 2, -3, 4))}")
    println("Anonymous: ${findFirstNegativeAnonymous(listOf(1, 2, -3, 4))}")

    // 2. Labeled returns in lambdas
    println("\n2. Labeled Returns:")

    fun processWithLabel(numbers: List<Int>) {
        numbers.forEach label@{
            print("Checking $it: ")
            if (it == 0) {
                println("skipping zero")
                return@label // Returns from lambda only, not from processWithLabel
            }
            println("processed")
        }
        println("Done with all numbers")
    }

    processWithLabel(listOf(1, 0, 2))

    // 3. Implicit labels (function name as label)
    println("\n3. Implicit Labels:")

    fun processWithImplicitLabel(numbers: List<Int>) {
        numbers.forEach {
            if (it == 0) return@forEach // Using function name as label
            println("Processing: $it")
        }
        println("Finished processing")
    }

    processWithImplicitLabel(listOf(1, 0, 2, 3))

    // 4. Multiple nested lambdas
    println("\n4. Nested Lambdas:")

    fun nestedExample() {
        listOf(1, 2, 3).forEach outer@{ i ->
            listOf("a", "b", "c").forEach inner@{ s ->
                if (i == 2 && s == "b") {
                    println("Skipping $i-$s")
                    return@outer // Skip rest of inner loop for i=2
                }
                println("$i-$s")
            }
        }
        println("Nested loops complete")
    }

    nestedExample()

    // 5. Return from lambda assigned to variable
    println("\n5. Returning from Lambda Variables:")

    fun demonstrateLambdaVariable(): String {
        val myLambda = lambda@{ x: Int ->
            if (x > 5) return@lambda "greater" // Must use label
            "not greater"
        }

        println("Result: ${myLambda(3)}")
        println("Result: ${myLambda(7)}")
        return "Function completed"
    }

    println(demonstrateLambdaVariable())

    // 6. Inline functions and return behavior
    println("\n6. Inline Functions:")

    fun testInlineReturn(): String {
        inlineForEach(listOf(1, 2, 3, 4, 5)) {
            if (it == 3) return "Found 3!" // Can return from testInlineReturn
            println("Processing: $it")
        }
        return "Completed without finding 3"
    }

    println("Inline result: ${testInlineReturn()}")

    // 7. Crossinline - preventing non-local returns
    println("\n7. Crossinline Example:")

    fun testCrossinline() {
        crossinlineExample {
            // return "Can't do this"  // Would cause compilation error
            println("Must use local return or no return")
        }
    }

    testCrossinline()
}
