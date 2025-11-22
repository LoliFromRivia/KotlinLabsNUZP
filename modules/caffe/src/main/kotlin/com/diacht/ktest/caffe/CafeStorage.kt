package com.diacht.ktest.caffe

import com.diacht.ktest.Product
import com.diacht.ktest.ProductType
import com.diacht.ktest.Storage

class CafeStorage : Storage {
    private val productCounts = mutableMapOf<ProductType, Int>()

    override fun addProduct(product: Product) {
        val currentCount = productCounts.getOrDefault(product.type, 0)
        productCounts[product.type] = currentCount + product.count
    }

    override fun checkProductCount(type: ProductType): Int = productCounts.getOrDefault(type, 0)

    override fun getProduct(productType: ProductType, count: Int): Product {
        val currentCount = checkProductCount(productType)
        if (currentCount < count) {
            throw IllegalStateException("Продукту $productType не вистачає. Потрібно: $count, В наявності: $currentCount")
        }
        productCounts[productType] = currentCount - count
        return Product(productType, count)
    }

    override fun getLeftovers(): List<Product> {
        return productCounts.filter { it.value > 0 }.map { (type, count) -> Product(type, count) }
    }

    override fun resetSimulation() {
        productCounts.clear()
    }
}