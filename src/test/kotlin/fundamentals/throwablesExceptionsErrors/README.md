# Throwables, Exceptions, and Errors

## What is a Throwable?
**Throwable** is the built-in classifier type that is the base type of all exception types. Any value that is used in a
throw expression must have a static type that is a subtype of **Throwable**. Any type that is used in a catch part of
the try expression must be a subtype of (or equal to) **throwable**.

It provides at least the following properties:
- `public val message: String?`
    -  An optional message depicting the cause of the throw.
- `public val cause: Throwable?`
    -  An optional other value of type **throwable** allowing for nested throwables to be constructed.

No subtype of **throwable** is allowed to have type parameters. Declaring such a type is a compile-time error.

### In My Own Words



## What is an Exception?
Exceptions help your code run more predictably, even when runtime errors occur that could disrupt program execution.
Kotlin treats all exceptions as **unchecked** by default. Unchecked exceptions simplify the exception handling process:
you can catch exceptions, but you don't need to explicitly handle or declare them.

Working with exceptions consists of two primary actions:

- **Throwing exceptions**: indicate when a problem occurs.
- **Catching exceptions**: handle the unexpected exception manually by resolving the issue or notifying the developer or
  application user.

An exception type declaration is any type declaration that meets the following criteria:
- It is a class or object declaration;
- It has **_kotlin.Throwable_** as one of its supertypes (either explicitly or implicitly);
- It has no type parameters.

Any object of an exception type may be thrown or caught.

Exceptions are represented by subclasses of the Exception class, which is a subclass of the Throwable class. Since 
Exception is an open class, you can create custom exceptions to suit your application's specific needs.

## What is an Error in Kotlin?
The Error class in Kotlin is a type alias for the Java Error class:

```kotlin
@SinceKotlin("1.1") public actual typealias Error = java.lang.Error
```

An Error is a subclass of Throwable that indicates serious problems that a reasonable application should not try to
catch. Most such errors are abnormal conditions.

A method is not required to declare in its throws clause any subclasses of Error that might be thrown during the
execution of the method but not caught, since these errors are abnormal conditions that should never occur. That is,
Error and its subclasses are regarded as unchecked exceptions for the purposes of compile-time checking of exceptions.

## Putting It All Together

In Kotlin's exception handling system, everything starts with **Throwable** - it's the root of the entire hierarchy.
We can think of it as the grandfather class that defines what can be "thrown" in our program. Every error or exception
in Kotlin must inherit from Throwable, which provides two key properties: 
 - an optional message explaining what went wrong
 - and an optional cause for chaining exceptions together

The Throwable family tree branches into two main categories:

1. **Exceptions** - These are for problems our application that  might reasonably recover from. When we write `try-catch`
    blocks, we are usually catching these. All exceptions in Kotlin are unchecked, meaning we are never forced to catch
    them (unlike Java). We can create our own custom exceptions by extending the Exception class, which makes them part
    of the Throwable family automatically.
2. **Errors** - These represent serious JVM-level problems that typically mean something has gone catastrophically wrong.
    Things like running out of memory (`OutOfMemoryError`) or stack overflow (`StackOverflowError`). We generally
    shouldn't try to catch these because they indicate problems beyond our application's control. In Kotlin, `Error` is
    actually just an alias for Java's Error class.

Here is a hierarchy visualization:
```
Throwable (the root - everything that can be thrown)
├── Exception (recoverable application problems)
│   ├── RuntimeException
│   └── Our custom exceptions
└── Error (serious JVM problems - don't catch these)
    ├── OutOfMemoryError
    └── StackOverflowError
```

One important rule: we **cannot** create generic exception classes (like `MyException<T>`). This restriction exists
because the JVM's exception handling mechanism needs concrete types at runtime, and generics are erased.

Practical takeaway: When writing Kotlin code, we'll mostly work with Exception and its subclasses for handling
recoverable problems. Errors will rarely appear in our code unless something is seriously wrong with the runtime
environment. In Kotlin, all exceptions are unchecked, so we have the freedom to handle them when it makes sense, not
when the compiler forces us to.

## References
- [Kotlin Documentation - Exceptions](https://kotlinlang.org/docs/exceptions.html)
- [Kotlin Spec - Throwable](https://kotlinlang.org/spec/kotlin-spec.html#kotlin.throwable)
- [Java Documentation - Error](https://docs.oracle.com/javase/8/docs/api/java/lang/Error.html)