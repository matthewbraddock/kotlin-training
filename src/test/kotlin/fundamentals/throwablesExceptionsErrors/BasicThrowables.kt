package fundamentals.throwablesExceptionsErrors

import java.io.IOException

/**
 * Basic Throwable, Exception, and Error Examples
 *
 * This file demonstrates the fundamental concepts of Kotlin's exception hierarchy
 *
 */

fun main() {
    println("=== Throwable Hierarchy Basics ===\n")

    // 1. Understanding the hierarchy
    println("1. Exception Hierarchy:")

    // Everything that can be thrown extends Throwable
    val throwable: Throwable = Exception("I'm a Throwable!")
    val exception: Exception = RuntimeException("I'm an Exception!")
    val error: Error = OutOfMemoryError("I'm an Error!")

    // All have access to Throwable properties
    println("Throwable message: ${throwable.message}")
    println("Exception message: ${exception.message}")
    println("Error message: ${error.message}")

    // 2. Catching different types
    println("\n2. Catching Different Types:")

    fun demonstrateCatching(shouldThrow: String) {
        try {
            when (shouldThrow) {
                "exception" -> throw IllegalArgumentException("Bad argument!")
                "error" -> throw StackOverflowError("Stack overflow!")
                "throwable" -> throw Throwable("Generic throwable!")
                else -> println("No exception thrown")
            }
        } catch (e: IllegalArgumentException) {
            println("Caught specific exception: ${e.message}")
        } catch (e: Exception) {
            // Not caught because throw was already mapped to IllegalArgumentException and caught
            println("Caught general exception: ${e.message}")
        } catch (e: Error) {
            println("Caught error (usually shouldn't do this): ${e.message}")
        } catch (t: Throwable) {
            println("Caught throwable: ${t.message}")
        }
    }

    demonstrateCatching("exception")
    demonstrateCatching("error")
    demonstrateCatching("throwable")
    demonstrateCatching("normal")

    // 3. Exception with cause (chaining)
    println("\n3. Exception Chaining:")

    fun doSomethingRisky() {
        throw NumberFormatException("Cannot parse number")
    }

    fun doSomethingElse() {
        try {
            doSomethingRisky()
        } catch (e: NumberFormatException) {
            // Wrap the original exception as the cause
            throw IllegalStateException("Failed to process data", e)
        }
    }

    try {
        doSomethingElse()
    } catch (e: IllegalStateException) {
        println("Caught: ${e.message}")

        // Walk the entire chain
        var cause = e.cause
        var level = 1
        while (cause != null) {
            println("  Cause level $level: ${cause::class.simpleName} - ${cause.message}")
            cause = cause.cause
            level++
        }
    }

    // 4. Creating custom throwables
    println("\n4. Custom Throwables:")

    // Custom Exception (common)
    class ValidationException(message: String) : Exception(message)

    // Custom Error (rare - only for serious system issues)
    class SystemFailureError(message: String) : Error(message)

    // Using custom exceptions
    fun validateAge(age: Int) {
        if (age < 0) throw ValidationException("Age cannot be negative: $age")
        if (age > 150) throw ValidationException("Age seems unrealistic: $age")
    }

    try {
        validateAge(-5)
    } catch (e: ValidationException) {
        println("Validation failed: ${e.message}")
    }

    // 5. Kotlin's built-in exception functions
    println("\n5. Built-in Exception Functions:")

    // error() throws IllegalStateException
    fun processValue(value: Int?) {
        val nonNull = value ?: error("Value cannot be null")
        println("Processing: $nonNull")
    }

    try {
        processValue(null)
    } catch (e: IllegalStateException) {
        println("error() threw: ${e.message}")
    }

    // check() for state validation
    val isInitialized = false
    try {
        check(isInitialized) { "System must be initialized first" }
    } catch (e: IllegalStateException) {
        println("check() threw: ${e.message}")
    }

    // require() for argument validation
    fun divide(a: Int, b: Int): Int {
        require(b != 0) { "Divisor cannot be zero" }
        return a / b
    }

    try {
        divide(10, 0)
    } catch (e: IllegalArgumentException) {
        println("require() threw: ${e.message}")
    }

    // 6. Demonstrating unchecked exceptions
    println("\n6. All Exceptions are Unchecked in Kotlin:")

    // No need to declare throws - this is valid Kotlin
    fun riskyOperation() {
        if (Math.random() > 0.5) {
            throw IOException("Random failure")
        }
    }

    // Caller is not forced to handle it
    fun caller() {
        // This compiles fine without try-catch
        // riskyOperation()
        println("In Java, this would require throws declaration or try-catch")
    }

    caller()
}
