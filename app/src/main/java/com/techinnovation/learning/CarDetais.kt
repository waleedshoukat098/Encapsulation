package com.techinnovation.learning

fun main() {
    // Creating an instance of the Car class using named arguments
    val car = Car(model = "Civic2012", mileage = 20, make = "Honda")

    // Displaying initial car details
    println("Initial model and mileage are made by:")
    println(
        "Model: ${car.getCarModel()}\n" +
                "Make: ${car.getCarMake()}\n" +
                "Mileage: ${car.getCarMileage()}"
    )

    // Updating car information with invalid mileage (negative)
    car.setCarMake("Toyota")
    car.setCarModel("Toyota Corolla 2013")
    car.setCarMileage(-30)
    println("\nUpdated model, make, and mileage:")
    println(
        "Model: ${car.getCarModel()}\n" +
                "Make: ${car.getCarMake()}\n" +
                "Mileage: ${car.getCarMileage()}"
    )

    // Updating car information with valid mileage
    car.setCarMake("Lamborghini")
    car.setCarModel("Audition 11")
    car.setCarMileage(10)
    println("\nUpdated model, make, and mileage:")
    println(
        "Model: ${car.getCarModel()}\n" +
                "Make: ${car.getCarMake()}\n" +
                "Mileage: ${car.getCarMileage()}"
    )
}

class Car(
    private var make: String,
    private var model: String,
    private var mileage: Int
) {
    // Function to set the car's make
    fun setCarMake(carMake: String) {
        make = carMake
    }

    // Function to get the car's make
    fun getCarMake(): String {
        return make
    }

    // Function to set the car's model
    fun setCarModel(carModel: String) {
        model = carModel
    }

    // Function to get the car's model
    fun getCarModel(): String {
        return model
    }

    // Function to set the car's mileage, ensuring it is positive
    fun setCarMileage(carMileage: Int) {
        if (carMileage > 0) {
            mileage = carMileage
        } else {
            println("Car mileage must be set as a positive number")
        }
    }

    // Function to get the car's mileage
    fun getCarMileage(): Int {
        return mileage
    }
}
