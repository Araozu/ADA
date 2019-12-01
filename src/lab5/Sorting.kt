package lab5

fun main() {

    val elems = Array(100) { (it + 1) * 10000}

    AlgorithmTester().testAlgorithm(elems)

}
