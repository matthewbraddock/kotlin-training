# Lambdas in Kotlin

## What is a Lambda?

Lambda literals are one of two types of function literals in Kotlin (the other being anonymous functions). They define a
function with no name using a concise syntax that's distinct from regular function declarations.

## Lambda Syntax
```kotlin
{ parameters -> body }
```

- Parameters come before the `->` arrow
- The body comes after the arrow
- The entire expression is wrapped in curly braces `{}`

## Key Characteristics

### Lambda-Specific Features

1. **Cannot have vararg parameters** (unlike anonymous functions)
2. **Can use destructuring in parameters**: `{ (a, b) -> a + b }`
3. **Special `it` parameter**: When a lambda has a single parameter, you can omit the parameter declaration and use `it`
    ```kotlin
       list.filter { it > 5 }  // instead of { num -> num > 5 }
    ```
4. **No explicit parameter list vs zero parameters**:
    ```kotlin
       { println("No parameters") }    // Zero parameters (has ->)
       { -> println("Zero params") }   // Explicitly zero parameters
    ```

### Return Behavior (Important!)

Lambda literals have unique return behavior:
- Non-labeled `return` inside a lambda returns from the **enclosing function**, not the lambda itself
- To return from the lambda, use labeled returns:
    ```kotlin
      list.forEach label@{ 
        if (it == 0) return@label  // Returns from lambda
        println(it)
      }
    ```

### Trailing Lambda Syntax

When a function's last parameter is a lambda, move it outside the parentheses:
    ```kotlin
    listOf(1, 2, 3).forEach { println(it) }
    ```

## Lambda vs Anonymous Function

While both are function literals, they differ in:
- **Syntax**: Lambdas use `{}`, anonymous functions use `fun`
- **Return behavior**: Lambdas return from enclosing function, anonymous functions return from themselves
- **Parameter restrictions**: Lambdas cannot have vararg parameters

## Common Use Cases

1. Collection operations: `filter`, `map`, `forEach`
2. Callbacks and event handlers
3. DSL building blocks

## Examples

See the following files:
- `BasicLambdas.kt` - Lambda syntax and usage
- `LambdaVsAnonymous.kt` - Comparing lambdas with anonymous functions
- `ReturnBehavior.kt` - Understanding lambda return statements
- `DestructuringLambdas.kt` - Using destructuring in lambda parameters

## References
- [Kotlin Language Specification - Function Literals](https://kotlinlang.org/spec/expressions.html#function-literals)
- [Kotlin Documentation - Lambda Expressions](https://kotlinlang.org/docs/lambdas.html)