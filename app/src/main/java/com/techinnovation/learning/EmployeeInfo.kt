package com.techinnovation.learning

fun main() {
    // Creating an instance of the Employee class and passing arguments
    val employee = Employee(name = "Waleed", position = "Android Intern", salary = 25000.0)

    // Displaying initial employee details
    println("Employee name, position, and Salary are:")
    println(
        "Name: ${employee.getEmpName()}\n" +
                "Position: ${employee.getEmpPos()}\n" +
                "Salary: ${employee.getSalary()}"
    )

    // Updating employee information with invalid salary (negative)
    employee.setEmpName("Ammad")
    employee.setEmpPos("Android Intern")
    employee.setSalary(-35000.0)
    println("\nNow employee name, position, and Salary are:")
    println(
        "Name: ${employee.getEmpName()}\n" +
                "Position: ${employee.getEmpPos()}\n" +
                "Salary: ${employee.getSalary()}"
    )

    // Updating employee information with valid salary
    employee.setEmpName("Usman")
    employee.setEmpPos("Android Intern")
    employee.setSalary(35000.0)
    println("\nUpdated employee details:")
    println(
        "Name: ${employee.getEmpName()}\n" +
                "Position: ${employee.getEmpPos()}\n" +
                "Salary: ${employee.getSalary()}"
    )
}

class Employee(
    private var name: String,
    private var position: String,
    private var salary: Double
) {
    // Function to retrieve the employee's name
    fun getEmpName(): String {
        return name
    }

    // Function to set the employee's name
    fun setEmpName(empName: String) {
        name = empName
    }

    // Function to retrieve the employee's position
    fun getEmpPos(): String {
        return position
    }

    // Function to set the employee's position
    fun setEmpPos(empPos: String) {
        position = empPos
    }

    // Function to retrieve the employee's salary
    fun getSalary(): Double {
        return salary
    }

    // Function to set the employee's salary, ensuring it is positive
    fun setSalary(empSalary: Double) {
        if (empSalary > 0.0) {
            salary = empSalary
        } else {
            println("Salary must be a positive value")
        }
    }
}
