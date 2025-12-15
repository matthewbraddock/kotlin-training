package fundamentals.throwablesExceptionsErrors

/**
 * Creating Custom Exceptions
 *
 * Patterns for building domain-specific exception hierarchies
 */

// Sealed classes must be at top level (or inside another class, but not inside a function)
enum class ErrorCode(val httpStatus: Int) {
    VALIDATION_ERROR(400),
    NOT_FOUND(404),
    CONFLICT(409),
    INTERNAL_ERROR(500),
}

sealed class OrderException(message: String, cause: Throwable? = null) : Exception(message, cause) {

    class OrderNotFound(val orderId: String) : OrderException("Order not found: $orderId")

    class InsufficientStock(
        val productId: String,
        val requested: Int,
        val available: Int,
    ) : OrderException("Insufficient stock for $productId: requested $requested, available $available")

    class PaymentFailed(
        val reason: String,
        cause: Throwable? = null,
    ) : OrderException("Payment failed: $reason", cause)

    class InvalidOrderState(
        val orderId: String,
        val currentState: String,
        val attemptedAction: String,
    ) : OrderException("Cannot $attemptedAction order $orderId in state $currentState")
}

fun main() {
    println("=== Custom Exceptions ===\n")

    // 1. Simple custom exception
    println("1. Simple Custom Exception:")

    class SimpleValidationException(message: String) : Exception(message)

    fun validateSimple(value: Int) {
        if (value < 0) throw SimpleValidationException("Value cannot be negative")
    }

    try {
        validateSimple(-1)
    } catch (e: SimpleValidationException) {
        println("Caught: ${e.message}")
    }

    // 2. Custom exception with additional properties
    println("\n2. Exception with Custom Properties:")

    class ValidationException(
        message: String,
        val field: String,
        val invalidValue: Any?,
        cause: Throwable? = null,
    ) : Exception(message, cause) {

        override fun toString(): String =
            "ValidationException(field='$field', value='$invalidValue', message='$message')"
    }

    fun validateAge(age: Int) {
        if (age < 0) {
            throw ValidationException(
                message = "Age cannot be negative",
                field = "age",
                invalidValue = age,
            )
        }
        if (age > 150) {
            throw ValidationException(
                message = "Age seems unrealistic",
                field = "age",
                invalidValue = age,
            )
        }
    }

    try {
        validateAge(-5)
    } catch (e: ValidationException) {
        println("Field '${e.field}' has invalid value: ${e.invalidValue}")
        println("Reason: ${e.message}")
    }

    // 3. Sealed class exception hierarchy
    println("\n3. Sealed Exception Hierarchy:")

    // Sealed classes let you define a closed set of exception types
    // Great for exhaustive when handling
    // Note: OrderException is defined at top level (sealed classes can't be local)

    fun handleOrderException(e: OrderException) {
        // Compiler knows all possible subtypes - exhaustive when
        when (e) {
            is OrderException.OrderNotFound -> {
                println("Handling: 404 - Order ${e.orderId} does not exist")
            }
            is OrderException.InsufficientStock -> {
                println("Handling: Stock issue - Need ${e.requested} of ${e.productId}, only ${e.available} available")
            }
            is OrderException.PaymentFailed -> {
                println("Handling: Payment error - ${e.reason}, caused by: ${e.cause?.message}")
            }
            is OrderException.InvalidOrderState -> {
                println(
                    "Handling: State error - Can't ${e.attemptedAction} order ${e.orderId} in state ${e.currentState}",
                )
            }
        }
    }

    // Demonstrate each type
    val exceptions = listOf(
        OrderException.OrderNotFound("ORD-123"),
        OrderException.InsufficientStock("SKU-456", requested = 10, available = 3),
        OrderException.PaymentFailed("Card declined", RuntimeException("Insufficient funds")),
        OrderException.InvalidOrderState("ORD-789", "SHIPPED", "cancel"),
    )

    exceptions.forEach { e ->
        handleOrderException(e)
    }

    println() // separator

    // 4. Exception with error codes (useful for APIs)
    println("\n4. Exception with Error Codes:")

    // Note: ErrorCode enum is defined at top level (enums can't be local)

    class ApiException(
        val errorCode: ErrorCode,
        message: String,
        val details: Map<String, Any> = emptyMap(),
        cause: Throwable? = null,
    ) : Exception(message, cause) {

        fun toErrorResponse(): Map<String, Any> = mapOf(
            "error" to errorCode.name,
            "status" to errorCode.httpStatus,
            "message" to (message ?: "Unknown error"),
            "details" to details,
        )
    }

    fun findUser(id: String): String {
        if (id.isBlank()) {
            throw ApiException(
                errorCode = ErrorCode.VALIDATION_ERROR,
                message = "User ID cannot be blank",
                details = mapOf("field" to "id"),
            )
        }
        if (id == "unknown") {
            throw ApiException(
                errorCode = ErrorCode.NOT_FOUND,
                message = "User not found",
                details = mapOf("userId" to id),
            )
        }
        return "User: $id"
    }

    listOf("", "unknown", "alice").forEach { id ->
        try {
            println(findUser(id))
        } catch (e: ApiException) {
            println("API Error: ${e.toErrorResponse()}")
        }
    }

    // 5. Retaining full constructor flexibility
    println("\n5. Full Constructor Flexibility:")

    // Match the flexibility of standard Exception constructors
    class FlexibleException : Exception {
        val errorCode: String

        constructor(errorCode: String) : super() {
            this.errorCode = errorCode
        }

        constructor(errorCode: String, message: String) : super(message) {
            this.errorCode = errorCode
        }

        constructor(errorCode: String, message: String, cause: Throwable) : super(message, cause) {
            this.errorCode = errorCode
        }

        constructor(errorCode: String, cause: Throwable) : super(cause) {
            this.errorCode = errorCode
        }
    }

    val flex1 = FlexibleException("E001")
    val flex2 = FlexibleException("E002", "Something went wrong")
    val flex3 = FlexibleException("E003", "Wrapped error", RuntimeException("Root cause"))

    println("flex1: code=${flex1.errorCode}, message=${flex1.message}")
    println("flex2: code=${flex2.errorCode}, message=${flex2.message}")
    println("flex3: code=${flex3.errorCode}, message=${flex3.message}, cause=${flex3.cause?.message}")

    // 6. When to use RuntimeException vs Exception
    println("\n6. RuntimeException vs Exception:")

    // Extend RuntimeException for programming errors (bugs)
    class ConfigurationException(message: String) : RuntimeException(message)

    // Extend Exception for recoverable situations
    class ResourceUnavailableException(message: String) : Exception(message)

    println("Both are unchecked in Kotlin, but the distinction documents intent:")
    println("- RuntimeException subclass = likely a bug, shouldn't normally happen")
    println("- Exception subclass = expected failure case, caller might want to handle")
}
