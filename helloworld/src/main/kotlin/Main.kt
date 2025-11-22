package org.example.helloworld
import com.diacht.ktest.FactoryItf
import com.diacht.ktest.Product
import com.diacht.ktest.SUGAR
import com.diacht.ktest.WATER
import com.diacht.ktest.caffe.*
import org.example.helloworld.BuildConfig
import com.diacht.ktest.compose.startTestUi


object SimulationConfig {
    fun seed(): String = "LoliFromRivia"
    fun labNumber() : Int = BuildConfig.LAB_NUMBER

    fun getSimulationObject(): FactoryItf {
        return CafeFactory()
    }
}

suspend fun main(args: Array<String>) {
    println("Лабораторна робота №${SimulationConfig.labNumber()} користувача ${SimulationConfig.seed()}")

    println("--- СИМУЛЯЦІЯ КАВ'ЯРНІ ---")
    println("----------------------------------------------")

    val factory = SimulationConfig.getSimulationObject()
    factory.resetSimulation()

    val initialStock = listOf(
        Product(COFFEE, 200),
        Product(MILK, 500),
        Product(WATER, 1000),
        Product(SUGAR, 300),
        Product(CACAO_POWDER, 50)
    )
    factory.loadProducts(initialStock)
    println("1. Завантажено початкові запаси.")
    println("   Залишки:")
    factory.getLeftovers().forEach { product ->
        println("      - ${product.type.javaClass.simpleName}: ${product.count}")
    }
    println("----------------------------------------------")

    val orderList = listOf(
        Pair(ESPRESSO, 5),
        Pair(CAPPUCCINO, 10),
        Pair(AMERICANO_WI_MILK, 1),
        Pair(LATE, 5)
    )

    println("2. Прийом замовлення:")
    orderList.forEach { (type, count) -> println("   - ${type.javaClass.simpleName}: $count шт.") }

    val successfulDrinks = factory.order(orderList)

    println("\n3. Результати виконання замовлень:")
    println("   Успішно приготовано напоїв: ${successfulDrinks.size}")

    val failedOrderAttempt = orderList.sumOf { it.second } - successfulDrinks.size
    if (failedOrderAttempt > 0) {
        println("   Не виконано через нестачу інгредієнтів: $failedOrderAttempt шт.")
    }
    println("----------------------------------------------")

    println("Загальний заробіток (getEarnings): ${factory.getEarnings()} грн.")

    println("Фінальні залишки (getLeftovers):")
    factory.getLeftovers().forEach { product ->
        println("   - ${product.type.javaClass.simpleName}: ${product.count}")
    }

    println("\nСтатистика замовлень:")
    val popular = factory.getPopularDrink()
    val mostEarnings = factory.getMostEarnings()

    println("   - Найпопулярніший напій: ${popular.type.javaClass.simpleName} (${popular.count} шт.)")
    println("   - Напій з найбільшим заробітком: ${mostEarnings.first.javaClass.simpleName} (${mostEarnings.second} грн.)")
    startTestUi(SimulationConfig.seed(), SimulationConfig.labNumber())
}