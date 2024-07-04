package com.techinnovation.learning

fun main() {
    //Here Creating Instance of Class Eployeee and passing arguments
    val employee: Employee =
        Employee(name = "Waleed", position = "Android Intern", salary = 25000.0)
    //case1 Intials name position and salary passed as arguments
    println("Employee name,position and Salary are")
    println(
        "Name is => ${employee.getEmpName()}\n" +
                "Position is => ${employee.getEmpPos()}\n" +
                "Salary is => ${employee.getSalary()}"
    )
    //Case2 Set salary as Negative it will show the previous salary in output

    employee.setEmpName("Ammad")
    employee.setEmpPos("Android Intern")
    employee.setSalary(-35000.0)
    println("Now employee name,position and Salary are")
    println(
        "Name is => ${employee.getEmpName()}\n" +
                "Position is => ${employee.getEmpPos()}\n" +
                "Salary is => ${employee.getSalary()}"
    )
    //case 3
    employee.setEmpName("Usman")
    employee.setEmpPos("Android Intern")
    employee.setSalary(35000.0)
    println(
        "Name is => ${employee.getEmpName()}\n" +
                "Position is => ${employee.getEmpPos()}\n" +
                "Salary is => ${employee.getSalary()}"
    )
}

class Employee(
    private var name: String,
    private var position: String,
    private var salary: Double
) {
    //Function that return Employee Name
    fun getEmpName(): String {
        return name
    }

    //Functon We use to Set EmployeeName with one parameter
    fun setEmpName(empName: String) {
        name = empName
    }
    //Function Use to get Employee Position

    fun getEmpPos(): String {
        return position
    }
    //Function Use to Set position of Employee
    fun setEmpPos(empPos: String) {
        position = empPos
    }
    //Function use to get the Employee Salary
    fun getSalary(): Double {
        return salary
    }
//Function use to Set the salary
    fun setSalary(empSalary: Double) {
        if (empSalary > 0.0) {
            salary = empSalary
        } else {
            println("Salary can always be set Positive")
        }
    }
}