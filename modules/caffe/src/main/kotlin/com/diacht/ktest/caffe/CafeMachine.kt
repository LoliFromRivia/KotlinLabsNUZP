package com.diacht.ktest.caffe

import com.diacht.ktest.Machine
import com.diacht.ktest.Product
import com.diacht.ktest.ProductType
import com.diacht.ktest.Receipt
import com.diacht.ktest.Storage

class CafeMachine(storage: Storage) : Machine(storage) {

    private fun getReceipt(drinkType: ProductType): Receipt {
        return CAFE_RECIPES[drinkType]
            ?: throw IllegalArgumentException("Невідомий тип напою: $drinkType")
    }

    fun makeDrink(drinkType: ProductType): Product {
        val receipt = getReceipt(drinkType)
        setReceipt(receipt)
        return executeProcess()
    }

    fun getPrice(drinkType: ProductType): Int {
        val receipt = getReceipt(drinkType)
        return receipt.price
    }
}