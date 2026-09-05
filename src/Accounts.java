import java.sql.*;
import java.util.Scanner;

public class Accounts {
    private Scanner scanner;
    private Connection connection;
   // double balance;
    public Accounts(Connection connection, Scanner sc) {
        this.connection=connection;
        this.scanner=sc;
    }

    public boolean account_exist(String adhaarNo) {
        try {

            PreparedStatement ps=connection.prepareStatement("SELECT * FROM accounts WHERE Addhar_no = ? ;");
            ps.setString(1,adhaarNo);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }else {
                return false;
            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public void create_account(String adhaar_no) {
        scanner.nextLine();
        System.out.print("Enter Full Name(in capital):");
        String f_name = scanner.nextLine();
        System.out.print("Enter Pin:");
        String pin = scanner.nextLine();
        System.out.print("Initial balance:");
        double balance = scanner.nextDouble();
        try {
            long acc_no = generateAccount_no();
            PreparedStatement ps=connection.prepareStatement("insert into accounts values(?,?,?,?,?);");
            ps.setLong(1,acc_no);
            ps.setString(2,f_name);
            ps.setString(3,adhaar_no);
            ps.setDouble(4,balance);
            ps.setString(5,pin);
            int rowsaffected = ps.executeUpdate();
            if(rowsaffected>0){
                System.out.println("Your Account is created.Your Account no is:"+acc_no);
                System.out.println();


            }else {
                System.out.println("failed");

            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }

    private long generateAccount_no() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT acc_number from accounts ORDER BY acc_number DESC LIMIT 1;");
            if (resultSet.next()) {
                long last_account_number = resultSet.getLong("acc_number");
                return last_account_number+1;
            } else {
                return 10000100;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 10000100;
    }

    public void close_account(long accNo) {
        scanner.nextLine();
        System.out.print("Enter security pin:");
        String pin = scanner.nextLine();
        try{
        PreparedStatement ps=connection.prepareStatement("DELETE FROM accounts WHERE acc_number = ? and security_pin=?;");
        ps.setLong(1,accNo);
        ps.setString(2,pin);
        int rows=ps.executeUpdate();
        if(rows>0){
            System.out.println("account closed");
        }else {
            System.out.println("wrong pin");
        }

    }
        catch (SQLException e){
        e.printStackTrace();
    }

    }

    public long get_accountNo(String adhaarNo) {
        try{
            PreparedStatement ps = connection.prepareStatement("SELECT acc_number from accounts WHERE Addhar_no = ?;");
            ps.setString(1,adhaarNo);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getLong("acc_number");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        throw new RuntimeException();
    }
}
