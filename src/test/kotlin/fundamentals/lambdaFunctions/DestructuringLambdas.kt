package fundamentals.lambdaFunctions

/**
 * Using Destructuring in Lambda Parameters
 *
 * Lambdas can destructure their parameters, which is especially useful with pairs, data classes, and maps
 */

fun main() {
    println("=== Destructuring in Lambdas ===\n")

    // 1. Destructuring Pairs
    println("1. Destructuring Pairs:")

    val pairs = listOf(
        1 to "one",
        2 to "two",
        3 to "three",
    )

    // Without destructuring
    pairs.forEach { pair ->
        println("${pair.first} is written as ${pair.second}")
    }

    println()

    // With destructuring
    pairs.forEach { (number, word) ->
        println("$number is written as $word")
    }

    // 2. Destructuring with Map entries
    println("\n2. Destructuring Map Entries:")

    val scores = mapOf(
        "Alice" to 95,
        "Bob" to 87,
        "Charlie" to 92,
    )

    scores.forEach { (name, score) ->
        println("$name scored $score")
    }

    // Finding highest scorer
    val topScorer = scores.maxByOrNull { (_, score) -> score }
    println("Top scorer: $topScorer")

    // 3. Destructuring data classes
    println("\n3. Destructuring Data Classes:")

    data class Person(val name: String, val age: Int, val city: String)

    val people = listOf(
        Person("Alice", 30, "New York"),
        Person("Bob", 25, "London"),
        Person("Charlie", 35, "Tokyo"),
    )

    // Destructure all components
    people.forEach { (name, age, city) ->
        println("$name is $age years old and lives in $city")
    }

    // Destructure only what you need (use _ for unused)
    println("\nAdults only:")
    people.filter { (_, age, _) -> age >= 30 }
        .forEach { (name, _, city) ->
            println("$name from $city")
        }

    // 4. Destructuring in higher-order functions
    println("\n4. With Higher-Order Functions:")

    val coordinates = listOf(
        Triple(1, 2, 3),
        Triple(4, 5, 6),
        Triple(7, 8, 9),
    )

    val sum = coordinates.map { (x, y, z) -> x + y + z }
    println("Sums of coordinates: $sum")

    // 5. Nested destructuring
    println("\n5. Nested Destructuring:")

    val nestedPairs = listOf(
        "Group A" to (1 to 2),
        "Group B" to (3 to 4),
        "Group C" to (5 to 6),
    )

    nestedPairs.forEach { (group, pair) ->
        val (first, second) = pair
        println("$group contains $first and $second")
    }

    // 6. Destructuring with indices
    println("\n6. WithIndex Destructuring:")

    val fruits = listOf("apple", "banana", "cherry")

    fruits.withIndex().forEach { (index, fruit) ->
        println("${index + 1}. $fruit")
    }

    // 7. Custom destructuring with component functions
    println("\n7. Custom Destructuring:")

    class Point(val x: Int, val y: Int) {
        operator fun component1() = x
        operator fun component2() = y
    }

    val points = listOf(Point(10, 20), Point(30, 40))
    points.forEach { (x, y) ->
        println("Point at ($x, $y)")
    }

    // 8. Practical example: Processing CSV-like data
    println("\n8. Practical Example - Processing Data:")

    val csvData = listOf(
        "Alice,30,Engineer",
        "Bob,25,Designer",
        "Charlie,35,Manager",
    )

    data class Employee(val name: String, val age: Int, val role: String)

    val employees = csvData.map { line ->
        val (name, age, role) = line.split(",")
        Employee(name, age.toInt(), role)
    }

    println("Employees over 30:")
    employees.filter { (_, age, _) -> age > 30 }
        .forEach { (name, _, role) ->
            println("- $name ($role)")
        }
}
