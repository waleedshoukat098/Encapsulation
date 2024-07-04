package com.techinnovation.learning

fun main() {
    //Named Arguments
    //instance
    val car:Car=Car(model="Civic2012", mileage = 20,make="Honda")
    //case1 Intial
    println("Initial model and miages are make by")
    println("Model is =>${car.getCarModel()}\n" +
            "Make by =>${car.getcarMake()}\n" +
            "Milage is =>${car.getCarMileage()}")
    //case 2 Checking with setting milage negative
    car.setCarMake("Toyota")
    car.setCarModel("Toyota Corolla 2013")
    car.setCarMilage(-30)
    println("Model is =>${car.getCarModel()}\n" +
            "Make by =>${car.getcarMake()}\n" +
            "Milage is =>${car.getCarMileage()}")
    ///case 3
    car.setCarMake("Lamborghini")
    car.setCarModel("Audition 11")
    car.setCarMilage(10)
    println("Model is =>${car.getCarModel()}\n" +
            "Make by =>${car.getcarMake()}\n" +
            "Milage is =>${car.getCarMileage()}")

}

class Car(
    private var make: String,
    private var model: String,
    private var mileage: Int
) {
    //function that sets the car make
    fun setCarMake(carmake: String) {
        make = carmake
    }
    //function that returns the carMake

    fun getcarMake(): String {
        return make
    }
///Function for Set the CarModel
    fun setCarModel(carmodel: String) {
        model = carmodel
    }
//function that return the Carmodel
    fun getCarModel(): String {
        return model
    }
//here in this function we set that milage can only be set in positive numbers
    fun setCarMilage(milage: Int) {
        if (milage > 0) {
            mileage = milage
        } else {
            println("car milage can be Set Negative")
        }
    }
    //function that returns the milage
    fun getCarMileage(): Int {
        return mileage
    }
}