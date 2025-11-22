package com.diacht.ktest.caffe

import com.diacht.ktest.*
import java.util.concurrent.TimeUnit

val CAFE_RECIPES: Map<ProductType, Receipt> = mapOf(
    ESPRESSO to Receipt(
        products = listOf(Product(COFFEE, 7), Product(WATER, 25)),
        time = 5L, timeUnit = TimeUnit.SECONDS,
        outProductType = ESPRESSO, price = 25
    ),
    AMERICANO to Receipt(
        products = listOf(Product(COFFEE, 7), Product(SUGAR, 7), Product(WATER, 120)),
        time = 6L, timeUnit = TimeUnit.SECONDS,
        outProductType = AMERICANO, price = 30
    ),
    CAPPUCCINO to Receipt(
        products = listOf(Product(MILK, 50), Product(COFFEE, 9), Product(SUGAR, 7), Product(WATER, 110)),
        time = 10L, timeUnit = TimeUnit.SECONDS,
        outProductType = CAPPUCCINO, price = 30
    ),
    AMERICANO_WI_MILK to Receipt(
        products = listOf(Product(MILK, 30), Product(COFFEE, 7), Product(SUGAR, 14), Product(WATER, 90)),
        time = 8L, timeUnit = TimeUnit.SECONDS,
        outProductType = AMERICANO_WI_MILK, price = 35
    ),
    LATE to Receipt(
        products = listOf(Product(MILK, 150), Product(COFFEE, 10), Product(SUGAR, 14), Product(WATER, 50)),
        time = 10L, timeUnit = TimeUnit.SECONDS,
        outProductType = LATE, price = 40
    ),
    CACAO_DRINK to Receipt(
        products = listOf(Product(MILK, 180), Product(SUGAR, 25), Product(WATER, 30), Product(CACAO_POWDER, 13)),
        time = 9L, timeUnit = TimeUnit.SECONDS,
        outProductType = CACAO_DRINK, price = 40
    )
)
