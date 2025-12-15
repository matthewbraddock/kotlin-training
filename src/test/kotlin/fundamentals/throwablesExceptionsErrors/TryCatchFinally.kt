package fundamentals.throwablesExceptionsErrors

import java.io.FileNotFoundException

/**
 * Try-Catch-Finally Mechanics
 *
 * Key concept: try-catch-finally is an EXPRESSION in Kotlin, meaning it returns a value
 */

fun main() {
    println("=== Try-Catch-Finally Mechanics ===\n")

    // 1. Try as an expression (returns a value)
    println("1. Try as an Expression:")

    fun parseNumber(str: String): Int {
        return try {
            str.toInt()
        } catch (e: NumberFormatException) {
            0 // Default value when parsing fails
        }
    }

    println("Result for '42': ${parseNumber("42")}")
    println("Result for 'abc': ${parseNumber("abc")}")

    // Can assign directly to a variable
    val result = try {
        "100".toInt() * 2
    } catch (e: NumberFormatException) {
        -1
    }
    println("Direct assignment result: $result")

    // 2. Multiple catch blocks (order matters - specific first)
    println("\n2. Multiple Catch Blocks:")

    fun processInput(input: Any?) {
        try {
            requireNotNull(input) { "Input cannot be null" }
            val number = (input as String).toInt()
            require(number > 0) { "Number must be positive" }
            println("Processed: $number")
        } catch (e: NumberFormatException) {
            println("Could not parse number: ${e.message}")
        } catch (e: ClassCastException) {
            println("Wrong type provided: ${e.message}")
        } catch (e: IllegalArgumentException) {
            // Catches both require() and requireNotNull() failures
            println("Validation failed: ${e.message}")
        }
    }

    processInput(null)
    processInput("-5")
    processInput(42) // Not a String
    processInput("abc")
    processInput("10")

    // 3. Finally block guarantees
    println("\n3. Finally Block Guarantees:")

    fun demonstrateFinally(shouldThrow: Boolean): String {
        try {
            println("  In try block")
            if (shouldThrow) throw RuntimeException("Oops!")
            return "Returned from try"
        } catch (e: RuntimeException) {
            println("  In catch block")
            return "Returned from catch"
        } finally {
            // This ALWAYS runs, even when returning from try or catch
            println("  In finally block (always executes)")
        }
    }

    println("No exception: ${demonstrateFinally(false)}")
    println()
    println("With exception: ${demonstrateFinally(true)}")

    // 4. Finally runs even with uncaught exceptions
    println("\n4. Finally with Uncaught Exceptions:")

    fun finallyWithUncaught() {
        try {
            try {
                throw IllegalStateException("Inner exception")
            } finally {
                println("  Inner finally runs before exception propagates")
            }
        } catch (e: IllegalStateException) {
            println("  Outer catch got: ${e.message}")
        }
    }

    finallyWithUncaught()

    // 5. Resource cleanup pattern (before use() was available)
    println("\n5. Resource Cleanup Pattern:")

    class FakeResource(val name: String) {
        fun open() = println("  Opening $name")
        fun read() = "Data from $name"
        fun close() = println("  Closing $name")
    }

    fun readResource(shouldFail: Boolean): String {
        val resource = FakeResource("myfile.txt")
        return try {
            resource.open()
            if (shouldFail) throw FileNotFoundException("File not found")
            resource.read()
        } finally {
            resource.close() // Always closes, even if exception thrown
        }
    }

    println("Success case:")
    println("  Result: ${readResource(false)}")

    println("\nFailure case:")
    try {
        readResource(true)
    } catch (e: FileNotFoundException) {
        println("  Caught: ${e.message}")
    }

    // 6. Try-finally without catch
    println("\n6. Try-Finally (No Catch):")

    fun mustCleanup() {
        try {
            println("  Doing work...")
            // Exception would propagate up, but finally still runs
        } finally {
            println("  Cleanup happens regardless")
        }
    }

    mustCleanup()

    // 7. Return value precedence
    println("\n7. Return Value Precedence (tricky!):")

    fun trickyReturn(): String {
        try {
            return "from try"
        } finally {
            // This runs, but doesn't override the return value
            println("  Finally executed")
            // Uncommenting this would override: return "from finally"
        }
    }

    println("Result: ${trickyReturn()}")

    // 8. Nested try-catch
    println("\n8. Nested Try-Catch:")

    fun nestedExample(outer: String, inner: String) {
        try {
            println("  Outer try with: $outer")
            try {
                println("  Inner try with: $inner")
                if (inner == "fail") throw IllegalArgumentException("Inner failed")
            } catch (e: IllegalArgumentException) {
                println("  Inner catch: ${e.message}")
                if (outer == "rethrow") throw IllegalStateException("Rethrowing", e)
            }
            if (outer == "fail") throw RuntimeException("Outer failed")
        } catch (e: Exception) {
            println("  Outer catch: ${e::class.simpleName} - ${e.message}")
        }
    }

    nestedExample("ok", "ok")
    println()
    nestedExample("ok", "fail")
    println()
    nestedExample("rethrow", "fail")
}
