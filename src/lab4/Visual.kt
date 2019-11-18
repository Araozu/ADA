package lab4

fun main(args: Array<String>) {

    val elems = try {
        Integer.parseInt(args[0])
    } catch (e: Exception) {
        50
    }

    val stepTime = try {
        Integer.parseInt(args[1])
    } catch (e: Exception) {
        50
    }

    println("Step time is $stepTime, there are $elems elements.")
    Panel(stepTime, elems)
}

