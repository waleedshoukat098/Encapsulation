package com.techinnovation.learning

fun main() {
    // Creating an instance of the BankAccount class with an initial balance of 200.0
    val bankAccount = BankAccount(200.0)

    // Initial Balance
    println("Initial balance at start => ${bankAccount.getBalance()}")

    // Deposit
    bankAccount.deposit(500.0)

    // Balance After Deposit
    println("Balance After Deposit => ${bankAccount.getBalance()}")

    // Withdraw
    bankAccount.withdraw(100.0)

    // Balance After Withdraw
    println("Balance After Withdraw => ${bankAccount.getBalance()}")
}

class BankAccount(
    private var balance: Double // Private property to hold the balance
) {

    // Method to retrieve the current balance
    fun getBalance(): Double {
        return balance
    }

    // Method to deposit money into the account
    fun deposit(amount: Double) {
        if (amount > 0) {
            balance += amount
        }
    }

    // Method to withdraw money from the account
    fun withdraw(amount: Double) {
        if (amount > 0 && amount <= balance) {
            balance -= amount
        }
    }
}
