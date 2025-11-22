package com.diacht.ktest.caffe

import com.diacht.ktest.*

class CafeFactory : FactoryItf() {

    private val storage: CafeStorage = CafeStorage()
    private val machine: CafeMachine = CafeMachine(storage)
    private var cashRegister: Int = 0 // Каса
    private val orderCounts = mutableMapOf<ProductType, Int>()

    override fun resetSimulation() {
        storage.resetSimulation()
        cashRegister = 0
        orderCounts.clear()
    }

    override fun loadProducts(productsFromSupplier: List<Product>) {
        productsFromSupplier.forEach { storage.addProduct(it) }
    }

    override fun order(order: List<Pair<ProductType, Int>>): List<Product> {
        val successfulDrinks = mutableListOf<Product>()

        order.forEach { (drinkType, quantity) ->
            for (i in 1..quantity) {
                try {
                    machine.makeDrink(drinkType)

                    val price = machine.getPrice(drinkType)
                    cashRegister += price

                    orderCounts[drinkType] = orderCounts.getOrDefault(drinkType, 0) + 1
                    successfulDrinks.add(Product(drinkType, 1))

                } catch (e: Exception) {
                    break
                }
            }
        }
        return successfulDrinks
    }

    override fun getLeftovers(): List<Product> = storage.getLeftovers()

    override fun getEarnings(): Int = cashRegister

    override fun getOrderStatistics(): List<Product> {
        return orderCounts.map { (type, count) -> Product(type, count) }
    }

    override fun getPopularDrink(): Product {
        val popular = orderCounts.maxByOrNull { it.value }
        return popular?.let { Product(it.key, it.value) } ?: Product(NONE, 0)
    }

    override fun getUnpopularDrink(): Product {
        val unpopular = orderCounts.minByOrNull { it.value }
        return unpopular?.let { Product(it.key, it.value) } ?: Product(NONE, 0)
    }

    override fun getMostEarnings(): Pair<ProductType, Int> {
        val earningsByDrink = mutableMapOf<ProductType, Int>()
        orderCounts.forEach { (drinkType, count) ->
            val price = machine.getPrice(drinkType)
            earningsByDrink[drinkType] = count * price
        }
        val mostEarningsEntry = earningsByDrink.maxByOrNull { it.value }
        return mostEarningsEntry?.let { Pair(it.key, it.value) } ?: Pair(NONE, 0)
    }
}