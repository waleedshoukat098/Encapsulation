package com.techinnovation.learning

fun main() {
    /*here we create the Instance of the BnkAccount class
      bankAccount   specifies the name of variable
      BankAccount   specifies it holds the instance of the BankAccount class
      BankAccount(200.0)    instance of the class  with one argument*/
    val bankAccount: BankAccount = BankAccount(200.0)
    //intial Balance
    println("Initial balance at start => ${bankAccount.getBalance()}")
    //Deposit
    bankAccount.deposit(500.0)
    //Balance After Deposit
    println("Balance After Deposit => ${bankAccount.getBalance()}")
    bankAccount.withdraw(100.0)
    //Balance After withdraw
    println("Balance After Widthraw => ${bankAccount.getBalance()}")

}

class BankAccount(
    private var balance: Double
) {

    /*Method for the balance which returns
    updated balance if user widthraw or deposit then it shows
    the updated balance
     */
    fun getBalance(): Double {
        return balance
    }

    /*Deposit function is used for deposting money to the account
         we write here logic for deposit when we have
         to deposit money ito the account
          */
    fun deposit(amount: Double) {
        if (amount > 0) {
            balance += amount
        }
    }

    /*withdraw
    Withdraw method is used to withdraw the balance from the account
    Here we write Logic for Withdraw the balance*/

    fun withdraw(amount: Double) {

        if (amount > 0 && amount <= balance) {
            balance -= amount
        }
    }
}