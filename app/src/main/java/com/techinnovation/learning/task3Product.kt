package com.techinnovation.learning

import org.jetbrains.annotations.TestOnly

@TestOnly
fun main() {
    //Creating Instance of the Class Product and Add Arguments
    val product: Product = Product("Samsung S23", 15000.00)
    println(
        "Initial product name is =>${product.getProdName()}\n" +
                "and price is => ${product.getProdPrice()}"
    )
    //case 2 Setting Negative price and check
    println("Now after update name and  price are ")
    product.setProdName("Samsung S23 Ultra")
    product.setProdPrice(-200000.0)
    print(
        "Product name is => ${product.getProdName()}\n " +
                "product price is => ${product.getProdPrice()}"
    )
}
class Product(
    private var prodName: String,
    private var prodPrice: Double
) {
    // Function that return the produt name
    fun getProdName(): String {
        return prodName
    }
    //Function that return the Productprice
    fun getProdPrice(): Double {
        return prodPrice
    }
//Function that sets the Product Name
    fun setProdName(name: String) {
        prodName = name
    }
    /*Function that sets the Product Price and only sets
     the price when price is greater than 0 means
    always when price is Positive*/
    fun setProdPrice(price: Double) {
        try {
            if (price>0) {
                prodPrice = price
            }
            else
            {
                println("set price in positive")
            }
        }
        catch (exp:Exception)
        {
            println(exp.message)
        }

    }
}