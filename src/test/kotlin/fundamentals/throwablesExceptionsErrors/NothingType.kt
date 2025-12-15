package fundamentals.throwablesExceptionsErrors

/**
 * The Nothing Type and Exceptions
 *
 * Nothing is a special type that represents "a value that never exists"
 * It's the return type of functions that never return normally (always throw)
 */

// Functions that return Nothing
fun alwaysFails(): Nothing {
    throw IllegalStateException("This function always throws")
}

fun fail(message: String): Nothing {
    throw RuntimeException(message)
}

fun failWithCode(code: Int, message: String): Nothing {
    throw RuntimeException("[$code] $message")
}

// Sealed class for demonstrating Nothing in when expressions
sealed class NothingResult {
    data class Success(val value: Int) : NothingResult()
    data class Error(val message: String) : NothingResult()
}

fun main() {
    println("=== The Nothing Type ===\n")

    // 1. What is Nothing?
    println("1. Understanding Nothing:")
    println(
        """
        Nothing is a type with NO instances.
        A function returning Nothing will NEVER return normally.
        It either throws an exception or runs forever.
        
        Nothing is a subtype of every other type, which makes it 
        work seamlessly in expressions.
        """.trimIndent(),
    )

    // 2. Built-in functions that return Nothing
    println("\n2. Built-in Nothing Functions:")
    println("TODO() signature: fun TODO(reason: String): Nothing")
    println("error() signature: fun error(message: Any): Nothing")

    try {
        TODO("Implement this later")
    } catch (e: NotImplementedError) {
        println("Caught TODO: ${e.message}")
    }

    try {
        error("Something went wrong")
    } catch (e: IllegalStateException) {
        println("Caught error(): ${e.message}")
    }

    // 3. Nothing as a subtype enables Elvis operator patterns
    println("\n3. Nothing in Elvis Operator:")

    fun getUsername(id: Int): String? {
        return if (id > 0) "User$id" else null
    }

    fun processUser(id: Int): String {
        // throw returns Nothing, which is a subtype of String
        // so this whole expression has type String
        val username = getUsername(id) ?: throw IllegalArgumentException("Invalid user ID")
        return "Processing: $username"
    }

    println(processUser(1))
    try {
        processUser(-1)
    } catch (e: IllegalArgumentException) {
        println("Caught: ${e.message}")
    }

    // 4. Nothing in when expressions
    println("\n4. Nothing in When Expressions:")

    fun handleResult(result: NothingResult): Int {
        return when (result) {
            is NothingResult.Success -> result.value
            is NothingResult.Error -> throw RuntimeException(result.message) // Nothing is subtype of Int
        }
    }

    println("Success: ${handleResult(NothingResult.Success(42))}")
    try {
        handleResult(NothingResult.Error("Failed!"))
    } catch (e: RuntimeException) {
        println("Error case threw: ${e.message}")
    }

    // 5. Custom Nothing functions in practice
    println("\n5. Custom Nothing Functions:")

    fun divide(a: Int, b: Int): Int {
        // fail() returns Nothing, which is a subtype of Int
        return if (b != 0) a / b else fail("Division by zero")
    }

    println("10 / 2 = ${divide(10, 2)}")
    try {
        divide(10, 0)
    } catch (e: RuntimeException) {
        println("Caught: ${e.message}")
    }

    // 6. Nothing? vs Nothing
    println("\n6. Nothing? vs Nothing:")

    // Nothing? can hold exactly one value: null
    val nullOnly: Nothing? = null
    println("Nothing? can be null: $nullOnly")

    // This is why null has type Nothing?
    // It allows null to be assigned to any nullable type
    val str: String? = null // null (type Nothing?) is subtype of String?
    val num: Int? = null // null (type Nothing?) is subtype of Int?

    println("null is typed as Nothing?, making it assignable to any T?")

    // 7. Nothing in generics
    println("\n7. Nothing in Generics:")

    // emptyList() returns List<Nothing>
    // Because Nothing is subtype of everything, List<Nothing> works as List<T> for any T
    val emptyStrings: List<String> = emptyList() // List<Nothing> assigned to List<String>
    val emptyInts: List<Int> = emptyList() // List<Nothing> assigned to List<Int>

    println("emptyList() returns List<Nothing>")
    println("Empty strings: $emptyStrings")
    println("Empty ints: $emptyInts")

    // 8. Why Nothing matters
    println("\n8. Why Nothing Matters:")
    println(
        """
        Nothing enables:
        - Elvis operator with throw: val x = nullable ?: throw Exception()
        - Exhaustive when branches that throw
        - Helper functions like TODO(), error(), fail()
        - Type-safe empty collections
        
        The compiler uses Nothing to understand that certain code paths
        never produce a value, which enables better type inference.
        """.trimIndent(),
    )
}
