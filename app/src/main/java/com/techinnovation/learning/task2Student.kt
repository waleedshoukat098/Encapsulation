package com.techinnovation.learning

fun main()
{
    //Creating Instance of the Student Class and passing Arguments
val student:Student= Student("Waleed","A")
    println("Student name at start is => ${student.getStudentName()}" +
            "\nAnd grade at start is => ${student.getGrade()}")
    student.setStudentName("Umer")
    student.setGrade("A")
    println("After Update Student name is => ${student.getStudentName()}\n" +
            "And Grade is => ${student.getGrade()}")
    //Check Grade with Setting G
    println("Check Grade with Setting G")
    student.setStudentName("Ali")
    student.setGrade("G")
    println("Now  Student name is => ${student.getStudentName()}\n" +
            "And Grade is => ${student.getGrade()}")
}
class  Student(
    private var name:String,
    private var grade:String
){
///Function returns the Student Name
 fun getStudentName():String
 {
     return name
 }
    //Function that set the Student name
    fun setStudentName(stdName:String)
    {
        name=stdName
    }
    // function that return studentGrade

    fun getGrade():String{
        return grade
    }
    /*Here functoion set the grade only when grades are in between
    "A","B","C","D","F"*/
    fun setGrade(stdGrade:String)
    {
        if (stdGrade in listOf("A","B","C","D","F")) {
            grade = stdGrade
        }
        else
        {
            println("Write the Correct Grade")
    }
    }

}
