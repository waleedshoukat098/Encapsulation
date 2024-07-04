package com.techinnovation.learning

fun main() {
    // Creating an instance of the Student class and passing arguments
    val student = Student("Waleed", "A")
    println("Student name at start is => ${student.getStudentName()}" +
            "\nAnd grade at start is => ${student.getGrade()}")

    // Updating student information
    student.setStudentName("Umer")
    student.setGrade("A")
    println("After Update Student name is => ${student.getStudentName()}\n" +
            "And Grade is => ${student.getGrade()}")

    // Checking grade with invalid input
    println("Check Grade with Setting G")
    student.setStudentName("Ali")
    student.setGrade("G")
    println("Now Student name is => ${student.getStudentName()}\n" +
            "And Grade is => ${student.getGrade()}")
}

class Student(
    private var name: String,
    private var grade: String
) {
    // Function to get the student's name
    fun getStudentName(): String {
        return name
    }

    // Function to set the student's name
    fun setStudentName(studentName: String) {
        name = studentName
    }

    // Function to get the student's grade
    fun getGrade(): String {
        return grade
    }

    // Function to set the student's grade, validating against allowed values
    fun setGrade(studentGrade: String) {
        if (studentGrade in listOf("A", "B", "C", "D", "F")) {
            grade = studentGrade
        } else {
            println("Write the Correct Grade")
        }
    }
}
