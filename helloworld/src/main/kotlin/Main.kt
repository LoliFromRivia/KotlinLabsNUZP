import com.diacht.ktest.compose.startTestUi
import org.example.helloworld.BuildConfig
import kotlin.math.*

fun seed(): String = "LoliFromRivia"

fun labNumber() : Int = BuildConfig.LAB_NUMBER

fun iCalculate(x0: Int = 6, x1: Int = 10, x2: Int = 40): Double {
    return tan(maxOf(x0, x1, x2).toDouble())
}

fun dCalculate(x0: Double = 23.46, x1: Double = 78.12, x2: Double = -90.72, x3: Double = -1.9): Double {
    return ln(abs(x0) + abs(x1) + abs(x2) + abs(x3))
}

fun strCalculate(x0: String, x1: String): Int {
    var diff = 0
    val half = x0.length / 2
    for (i in x0.indices) {
        val c0 = x0[i]
        val c1 = x1[i]
        if (c0 == 'A' || c0 == 'C') {
            if (c0 != c1) {
                diff += if (i < half) 2 else 1
            }
        }
    }
    return diff
}

suspend fun main(args: Array<String>) {
    println("Лабораторна робота №${labNumber()} користувача ${seed()}")
    println("iCalculate() = ${iCalculate()}")
    println("dCalculate() = ${dCalculate()}")
    println("strCalculate(AAACCC, ATGCJC) = ${strCalculate("AAACCC", "ATGCJC")}")
    startTestUi(seed(), labNumber())
}