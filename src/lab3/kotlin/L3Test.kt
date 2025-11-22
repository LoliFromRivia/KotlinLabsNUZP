import com.diacht.ktest.*
import com.diacht.ktest.caffe.AMERICANO
import com.diacht.ktest.caffe.AMERICANO_WI_MILK
import com.diacht.ktest.caffe.CACAO_DRINK
import com.diacht.ktest.caffe.CACAO_POWDER
import com.diacht.ktest.caffe.CAPPUCCINO
import com.diacht.ktest.caffe.COFFEE
import com.diacht.ktest.caffe.ESPRESSO
import com.diacht.ktest.caffe.LATE
import com.diacht.ktest.caffe.MILK
import com.diacht.ktest.domain.usecases.GenerateL3TaskUseCase
import com.diacht.ktest.juicefactory.APPLE
import com.diacht.ktest.juicefactory.APPLE_CARROT_JUICE
import com.diacht.ktest.juicefactory.APPLE_JUICE
import com.diacht.ktest.juicefactory.CARROT
import com.diacht.ktest.juicefactory.ORANGE
import com.diacht.ktest.juicefactory.ORANGE_JUICE
import com.diacht.ktest.juicefactory.SALT
import com.diacht.ktest.juicefactory.TOMATO
import com.diacht.ktest.juicefactory.TOMATO_CARROT_JUICE
import com.diacht.ktest.juicefactory.TOMATO_JUICE
import org.example.helloworld.SimulationConfig.getSimulationObject
import org.example.helloworld.SimulationConfig.seed
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue


internal class L3Test {
    fun juiceProducts(k: Int) = listOf(
        Product(ORANGE, 13000 * k),
        Product(APPLE, 15000 * k),
        Product(CARROT, 8000 * k),
        Product(TOMATO, 12000 * k),
        Product(WATER, 8000 * k),
        Product(SALT, 1000 * k),
        Product(SUGAR, 3000 * k),
    )

    fun caffeeProducts(k: Int) = listOf(
        Product(COFFEE, 750 * k),
        Product(MILK, 2500 * k),
        Product(WATER, 4000 * k),
        Product(SUGAR, 1000 * k),
        Product(CACAO_POWDER, 500 * k),
    )

    @Test
    fun task1Test() {
        val generator = GenerateL3TaskUseCase()
        val seed = seed()
        val task = generator(seed)
        val simulationObject = getSimulationObject()

        simulationObject.resetSimulation()
        assertTrue(simulationObject.getLeftovers().isEmpty(), "Склад має бути порожнім після сбросу симуляції.")

        when (task.theme) {
            L3Theme.JUICE_FABRIC -> testJuiceFabric1(simulationObject)
            L3Theme.CAFFE -> testCafee1(simulationObject)
        }
        simulationObject.resetSimulation()
        assertTrue(simulationObject.getLeftovers().isEmpty(), "Склад має бути порожнім після сбросу симуляції.")
    }

    private fun testJuiceFabric1(simulationObject: FactoryItf) {
        simulationObject.loadProducts(juiceProducts(2))
        assertTrue(
            simulationObject.getLeftovers().isNotEmpty(),
            "Склад не має бути порожнім після завантаження продуктів."
        )

        val listOrder1 = simulationObject.order(listOf(Pair(APPLE_JUICE, 1)))
        assertTrue(listOrder1.size == 1, "Має бути лише один сік")
        assertTrue(listOrder1[0].type == APPLE_JUICE, "Має бути APPLE_JUICE")
        assertEquals(simulationObject.getLeftovers().findLast { it.type == APPLE }?.count, 28500, "APPLE leftover")

        for (i in 0..1) {
            val res = simulationObject.order(
                listOf(
                    Pair(ORANGE_JUICE, 5),
                    Pair(APPLE_JUICE, 4),
                    Pair(APPLE_CARROT_JUICE, 3),
                    Pair(TOMATO_CARROT_JUICE, 2),
                    Pair(TOMATO_JUICE, 1),
                )
            )
            var count = 0.0
            res.filter { it.type == TOMATO_JUICE }.forEach { count += it.count }
            assertEquals(1.0, count, "TOMATO_JUICE")
            count = 0.0
            res.filter { it.type == TOMATO_CARROT_JUICE }.forEach { count += it.count }
            assertEquals(2.0, count, "TOMATO_CARROT_JUICE")
            count = 0.0
            res.filter { it.type == APPLE_CARROT_JUICE }.forEach { count += it.count }
            assertEquals(3.0, count, "APPLE_CARROT_JUICE")
            count = 0.0
            res.filter { it.type == APPLE_JUICE }.forEach { count += it.count }
            assertEquals(4.0, count, "APPLE_JUICE")
            count = 0.0
            res.filter { it.type == ORANGE_JUICE }.forEach { count += it.count }
            assertEquals(5.0, count, "ORANGE_JUICE")
        }
        // apple 8400
        // orange 6000
        // carrot 2900
        // tomato 3300
        // water 4370
        // sugar 385
        // salt 22
        val leftovers = simulationObject.getLeftovers()
        assertEquals(11700, leftovers.findLast { it.type == APPLE }?.count, "APPLE leftover")
        assertEquals(14000, leftovers.findLast { it.type == ORANGE }?.count, "ORANGE leftover")
        assertEquals(10200, leftovers.findLast { it.type == CARROT }?.count, "CARROT leftover")
        assertEquals(17400, leftovers.findLast { it.type == TOMATO }?.count, "TOMATO leftover")
        assertEquals(6910, leftovers.findLast { it.type == WATER }?.count, "WATER leftover")
        assertEquals(5195, leftovers.findLast { it.type == SUGAR }?.count, "SUGAR leftover")
        assertEquals(1956, leftovers.findLast { it.type == SALT }?.count, "SALT leftover")
    }

    private fun testCafee1(simulationObject: FactoryItf) {
        simulationObject.loadProducts(caffeeProducts(3))
        assertTrue(
            simulationObject.getLeftovers().isNotEmpty(),
            "Склад не має бути порожнім після завантаження продуктів."
        )

        val listOrder1 = simulationObject.order(listOf(Pair(CACAO_DRINK, 1)))
        assertTrue(listOrder1.size == 1, "Має бути лише один CACAO_DRINK")
        assertTrue(listOrder1[0].type == CACAO_DRINK, "Має бути CACAO_DRINK")
        assertEquals(
            simulationObject.getLeftovers().findLast { it.type == CACAO_POWDER }?.count,
            1487,
            "CACAO_POWDER leftover"
        )

        for (i in 0..1) {
            val res = simulationObject.order(
                listOf(
                    Pair(CACAO_DRINK, 5),
                    Pair(LATE, 4),
                    Pair(AMERICANO_WI_MILK, 3),
                    Pair(CAPPUCCINO, 2),
                    Pair(AMERICANO, 1),
                )
            )
            var count = 0.0
            res.filter { it.type == AMERICANO }.forEach { count += it.count }
            assertEquals(1.0, count, "AMERICANO")
            count = 0.0
            res.filter { it.type == CAPPUCCINO }.forEach { count += it.count }
            assertEquals(2.0, count, "CAPPUCCINO")
            count = 0.0
            res.filter { it.type == AMERICANO_WI_MILK }.forEach { count += it.count }
            assertEquals(3.0, count, "AMERICANO_WI_MILK")
            count = 0.0
            res.filter { it.type == LATE }.forEach { count += it.count }
            assertEquals(4.0, count, "LATE")
            count = 0.0
            res.filter { it.type == CACAO_DRINK }.forEach { count += it.count }
            assertEquals(5.0, count, "CACAO_DRINK")
        }
        val leftovers = simulationObject.getLeftovers()
        assertEquals(null, leftovers.findLast { it.type == APPLE }?.count, "APPLE leftover")
        assertEquals(2078, leftovers.findLast { it.type == COFFEE }?.count, "COFFEE leftover")
        assertEquals(3940, leftovers.findLast { it.type == MILK }?.count, "MILK leftover")
        assertEquals(10050, leftovers.findLast { it.type == WATER }?.count, "WATER leftover")
        assertEquals(1357, leftovers.findLast { it.type == CACAO_POWDER }?.count, "CACAO_POWDER leftover")
        assertEquals(2487, leftovers.findLast { it.type == SUGAR }?.count, "SUGAR leftover")
    }

    @Test
    fun test2() {
        val generator = GenerateL3TaskUseCase()
        val seed = seed()
        val task = generator(seed)
        val simulationObject = getSimulationObject()

        simulationObject.resetSimulation()
        assertTrue(simulationObject.getLeftovers().isEmpty(), "Склад має бути порожнім після сбросу симуляції.")

        when (task.theme) {
            L3Theme.JUICE_FABRIC -> testJuiceFabric2(simulationObject, task)
            L3Theme.CAFFE -> testCafee2(simulationObject, task)
        }
        simulationObject.resetSimulation()
        assertTrue(simulationObject.getLeftovers().isEmpty(), "Склад має бути порожнім після сбросу симуляції.")
    }

    fun testCafee2(simulationObject: FactoryItf, task: TaskL3) {
        simulationObject.resetSimulation()
        assertTrue(simulationObject.getLeftovers().isEmpty(), "Склад має бути порожнім після сбросу симуляції.")
        simulationObject.loadProducts(caffeeProducts(4))

        assertTrue(
            simulationObject.getLeftovers().isNotEmpty(),
            "Склад не має бути порожнім після завантаження продуктів."
        )

        simulationObject.order(
            listOf(
                Pair(CACAO_DRINK, 5),
                Pair(LATE, 4),
                Pair(AMERICANO_WI_MILK, 3),
                Pair(CAPPUCCINO, 2),
                Pair(AMERICANO, 1),
            )
        )
        simulationObject.order(
            listOf(
                Pair(CACAO_DRINK, 5),
            )
        )
//        assertEquals(
//                755, simulationObject.getEarnings(),
//                "сума грошей в касі не правильна"
//            )
//        simulationObject.resetSimulation()
//        assertEquals(
//            0, simulationObject.getEarnings(),
//            "сума грошей в касі не правильна"
//        )

        when (task.sub) {
            L3TaskSub.CASH_CHECK -> assertEquals(
                755, simulationObject.getEarnings(),
                "сума грошей в касі не правильна"
            )

            L3TaskSub.ORDER_STATISTICS -> {
                assertEquals(
                    simulationObject.getOrderStatistics().findLast { it.type == CACAO_DRINK }?.count,
                    10, "статистика замовлень не правильна, помилка у CACAO_DRINK"
                )
                assertEquals(
                    simulationObject.getOrderStatistics().findLast { it.type == ESPRESSO }?.count,
                    0, "статистика замовлень не правильна, помилка у ESPRESSO"
                )
            }

            L3TaskSub.POPULAR_DRINK -> assertEquals(
                CACAO_DRINK,
                simulationObject.getPopularDrink().type, "найпопопулярніший напій не правильний"
            )

            L3TaskSub.UNPOPULAR_DRINK -> assertEquals(
                ESPRESSO,
                simulationObject.getUnpopularDrink().type, "непопулярний напій не правильний"
            )

            L3TaskSub.MOST_EARNINGS -> assertEquals(
                CACAO_DRINK,
                simulationObject.getMostEarnings().first, "найприбутковий напій не правильний"
            )
        }
        simulationObject.resetSimulation()
        when (task.sub) {
            L3TaskSub.CASH_CHECK -> assertEquals(
                0, simulationObject.getEarnings(),
                "сума грошей в касі не правильна після перезапуску"
            )

            L3TaskSub.ORDER_STATISTICS -> {
                assertEquals(
                    simulationObject.getOrderStatistics().findLast { it.type == CACAO_DRINK }?.count,
                    0, "статистика замовлень не правильна після перезапуску"
                )
                assertEquals(
                    simulationObject.getOrderStatistics().findLast { it.type == LATE }?.count,
                    0, "статистика замовлень не правильна після перезапуску"
                )
            }

            L3TaskSub.POPULAR_DRINK -> assertEquals(
                NONE,
                simulationObject.getPopularDrink().type, "найпопопулярніший напій не правильний після перезапуску"
            )

            L3TaskSub.UNPOPULAR_DRINK -> assertEquals(
                NONE,
                simulationObject.getUnpopularDrink().type, "непопулярний напій не правильний після перезапуску"
            )

            L3TaskSub.MOST_EARNINGS -> assertEquals(
                NONE,
                simulationObject.getMostEarnings().first, "найприбутковий напій не правильний після перезапуску"
            )
        }
    }

    fun testJuiceFabric2(simulationObject: FactoryItf, task: TaskL3) {
        simulationObject.resetSimulation()
        assertTrue(simulationObject.getLeftovers().isEmpty(), "Склад має бути порожнім після сбросу симуляції.")
        simulationObject.loadProducts(juiceProducts(2))
        assertTrue(
            simulationObject.getLeftovers().isNotEmpty(),
            "Склад не має бути порожнім після завантаження продуктів."
        )

          simulationObject.order(
                listOf(
                    Pair(ORANGE_JUICE, 5),
                    Pair(APPLE_JUICE, 4),
                    Pair(APPLE_CARROT_JUICE, 3),
                    Pair(TOMATO_CARROT_JUICE, 2),
                    Pair(TOMATO_JUICE, 1),
                )
            )

        simulationObject.order(
            listOf(
                Pair(TOMATO_JUICE, 5),
            )
        )

        when (task.sub) {
            L3TaskSub.CASH_CHECK -> assertEquals(
                800, simulationObject.getEarnings(),
                "сума грошей в касі не правильна"
            )

            L3TaskSub.ORDER_STATISTICS -> {
                assertEquals(
                    simulationObject.getOrderStatistics().findLast { it.type == TOMATO_JUICE }?.count,
                    6, "статистика замовлень не правильна, помилка у TOMATO_JUICE"
                )
                assertEquals(
                    simulationObject.getOrderStatistics().findLast { it.type == APPLE_CARROT_JUICE }?.count,
                    3, "статистика замовлень не правильна, помилка у APPLE_CARROT_JUICE"
                )
            }

            L3TaskSub.POPULAR_DRINK -> assertEquals(
                TOMATO_JUICE,
                simulationObject.getPopularDrink().type, "найпопопулярніший напій не правильний"
            )

            L3TaskSub.UNPOPULAR_DRINK -> assertEquals(
                TOMATO_CARROT_JUICE,
                simulationObject.getUnpopularDrink().type, "непопулярний напій не правильний"
            )

            L3TaskSub.MOST_EARNINGS -> assertEquals(
                ORANGE_JUICE,
                simulationObject.getMostEarnings().first, "найприбутковий напій не правильний"
            )
        }
        simulationObject.resetSimulation()
        when (task.sub) {
            L3TaskSub.CASH_CHECK -> assertEquals(
                0, simulationObject.getEarnings(),
                "сума грошей в касі не правильна після перезапуску"
            )

            L3TaskSub.ORDER_STATISTICS -> {
                assertEquals(
                    simulationObject.getOrderStatistics().findLast { it.type == ORANGE_JUICE }?.count,
                    0, "статистика замовлень не правильна після перезапуску"
                )
                assertEquals(
                    simulationObject.getOrderStatistics().findLast { it.type == TOMATO_JUICE }?.count,
                    0, "статистика замовлень не правильна після перезапуску"
                )
            }

            L3TaskSub.POPULAR_DRINK -> assertEquals(
                NONE,
                simulationObject.getPopularDrink().type, "найпопопулярніший напій не правильний після перезапуску"
            )

            L3TaskSub.UNPOPULAR_DRINK -> assertEquals(
                NONE,
                simulationObject.getUnpopularDrink().type, "непопулярний напій не правильний після перезапуску"
            )

            L3TaskSub.MOST_EARNINGS -> assertEquals(
                NONE,
                simulationObject.getMostEarnings().first, "найприбутковий напій не правильний після перезапуску"
            )
        }
    }
}