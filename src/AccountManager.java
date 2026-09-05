import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AccountManager {
    private Scanner scanner;
    private Connection connection;

    public AccountManager(Connection connection, Scanner sc) {
        this.connection=connection;
        this.scanner=sc;
    }

    public void debit(long accNo) throws SQLException {
        connection.setAutoCommit(false);
        scanner.nextLine();
        System.out.print("Enter the amount:");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter pin:");
        String pin= scanner.nextLine();
        try {
            PreparedStatement check_balance = connection.prepareStatement("SELECT balance FROM accounts WHERE acc_number = ? and security_pin=?;");
            check_balance.setLong(1,accNo);
            check_balance.setString(2,pin);
            ResultSet rs = check_balance.executeQuery();
            if(rs.next()) {

                if (amount <= rs.getDouble("balance")) {
                    PreparedStatement debit = connection.prepareStatement("update accounts set balance = balance - ? where acc_number=?;");
                    debit.setDouble(1, amount);
                    debit.setLong(2, accNo);
                    int rows = debit.executeUpdate();
                    if (rows > 0) {
                        connection.commit();
                        System.out.println(amount + " debited successfully");
                    } else {
                        System.out.println("Transaction failed");
                        connection.rollback();
                    }
                } else {
                    System.out.println("insufficient balance");
                }
                balance(accNo);
            }
            else{
                System.out.println("wrong pin !! enter correct pin.");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void credit(long accNo) throws SQLException {
        connection.setAutoCommit(false);
        scanner.nextLine();
        System.out.print("Enter the amount:");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter pin:");
        String pin= scanner.nextLine();
        try {
            PreparedStatement pins = connection.prepareStatement("Select * from accounts where acc_number = ? and security_pin=?;");
            pins.setLong(1,accNo);
            pins.setString(2,pin);
            ResultSet prs = pins.executeQuery();
            if(prs.next()) {

                PreparedStatement debit = connection.prepareStatement("update accounts set balance = balance + ? where acc_number=?;");
                debit.setDouble(1, amount);
                debit.setLong(2, accNo);
                int rows = debit.executeUpdate();
                if (rows > 0) {
                    connection.commit();
                    System.out.println(amount + " debited successfully");
                } else {
                    System.out.println("Transaction failed");
                    connection.rollback();
                }

               balance(accNo);
            }else {
                System.out.println("wrong pin !! enter correct pin.");
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void transfer(long accNo) throws SQLException {
        connection.setAutoCommit(false);
        scanner.nextLine();
        System.out.print("receiver account no:");
        long racc_no = scanner.nextLong();
        System.out.print("Enter the amount:");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter pin:");
        String pin= scanner.nextLine();

        try{
            PreparedStatement check_balance = connection.prepareStatement("SELECT balance FROM accounts WHERE acc_number = ? and security_pin=?;");
            check_balance.setLong(1,accNo);
            check_balance.setString(2,pin);
            ResultSet rs = check_balance.executeQuery();
            if(rs.next()) {

                if (amount <= rs.getDouble("balance")) {
                    PreparedStatement debit = connection.prepareStatement("update accounts set balance = balance - ? where acc_number=?;");
                    debit.setDouble(1, amount);
                    debit.setLong(2, accNo);
                    PreparedStatement credits = connection.prepareStatement("update accounts set balance = balance + ? where acc_number=?;");
                    credits.setDouble(1, amount);
                    credits.setLong(2, racc_no);
                    int rows1 = debit.executeUpdate();
                    int rows2 = credits.executeUpdate();
                    if (rows1 > 0 && rows2 > 0) {
                        connection.commit();
                        System.out.println(amount + " debited successfully");
                    } else {
                        connection.rollback();
                        System.out.println("Transaction failed");
                    }
                } else {
                    System.out.println("insufficient balance");
                }
            }
            else {
                System.out.println("wrong pin !! enter correct pin. ");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void balance(long accNo)  {
        try {
            PreparedStatement updated_balance = connection.prepareStatement("SELECT balance FROM accounts WHERE acc_number = ?;");
            updated_balance.setLong(1, accNo);
            ResultSet urs = updated_balance.executeQuery();
            if (urs.next()) {
                System.out.println("current balance :" + urs.getDouble("balance"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
