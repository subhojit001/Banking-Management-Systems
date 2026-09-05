# 🏦 Banking Management System

A **console-based Banking Management System** developed using **Core Java, JDBC, and MySQL**.
The project focuses on understanding database connectivity, SQL operations, and transaction management using JDBC.

## 🛠️ Technologies Used

* Java
* JDBC
* MySQL
* SQL
* PreparedStatement
* ResultSet

## 🚀 Features

* User Registration & Login
* Bank Account Creation
* Automatic Account Number Generation
* Debit Money
* Credit Money
* Transfer Money
* Check Account Balance
* Close Bank Account
* PIN-based Account Verification

## 🔄 Application Flow

```text
Start
  ↓
Register / Login
  ↓
Create Bank Account
  ↓
Banking Operations
  ├── Debit
  ├── Credit
  ├── Transfer
  ├── Check Balance
  └── Close Account
  ↓
Logout
```

## 💾 Database Integration

The application connects to a **MySQL database using JDBC** and performs database operations using `Connection`, `PreparedStatement`, and `ResultSet`.

Account creation and account management are handled through SQL queries.

## 🔐 Transaction Management

The project uses JDBC transaction management for financial operations.

For example, during a money transfer:

1. Verify the account and PIN
2. Check available balance
3. Debit the sender's account
4. Credit the receiver's account
5. Commit the transaction if both operations succeed
6. Roll back if an operation fails

This is implemented using `setAutoCommit(false)`, `commit()`, and `rollback()`.

## 📚 Learning Objectives

This project helped me understand:

* JDBC database connectivity
* SQL queries from Java
* `PreparedStatement`
* `ResultSet`
* CRUD operations
* Database transactions
* Commit and rollback
* Exception handling
* Connecting Java applications with MySQL

## ▶️ How to Run

### 1. Create the MySQL Database

Create a database named:

```sql
CREATE DATABASE banking_system;
```

Create the required tables according to the SQL structure used by the application.

### 2. Configure Database Credentials

Update the database URL, username, and password in `BankingApp.java`.

**Do not commit your actual password to GitHub.** Use environment variables or a local configuration file instead.

### 3. Add MySQL JDBC Driver

Make sure the **MySQL Connector/J** dependency is available in your project.

### 4. Run the Application

Run:

```text
BankingApp.java
```

The application will start with:

```text
*** WELCOME TO BANKING SYSTEM ***

1. Register
2. Login
3. Exit
```

## 🔮 Future Improvements

* Add transaction history
* Improve input validation
* Add better exception handling
* Store passwords/PINs securely
* Add a GUI or web interface
* Migrate the backend to Spring Boot

## 👨‍💻 Author

**Subhojit Roy**

Core Java | JDBC | MySQL
