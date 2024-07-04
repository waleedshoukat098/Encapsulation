package com.techinnovation.learning

import org.jetbrains.annotations.TestOnly

@TestOnly
fun main() {
    // Creating an instance of the Product class and adding arguments
    val product = Product("Samsung S23", 15000.00)

    // Displaying initial product details
    println("Initial product name is => ${product.getProdName()}\n" +
            "and price is => ${product.getProdPrice()}")

    // Updating product name and setting an invalid price
    println("Now after update, name and price are:")
    product.setProdName("Samsung S23 Ultra")
    product.setProdPrice(-200000.0)
    println("Product name is => ${product.getProdName()}\n" +
            "Product price is => ${product.getProdPrice()}")
}

class Product(
    private var prodName: String,
    private var prodPrice: Double
) {
    // Function to retrieve the product name
    fun getProdName(): String {
        return prodName
    }

    // Function to retrieve the product price
    fun getProdPrice(): Double {
        return prodPrice
    }

    // Function to set the product name
    fun setProdName(name: String) {
        prodName = name
    }

    // Function to set the product price, ensuring it is positive
    fun setProdPrice(price: Double) {
        if (price > 0) {
            prodPrice = price
        } else {
            println("Please set a positive price")
        }
    }
}
