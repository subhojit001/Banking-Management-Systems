import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class User {
    private Scanner scanner;
    private Connection connection;


    public User(Connection connection, Scanner sc) {
        this.connection=connection;
        this.scanner=sc;
    }

    public void register() {
        scanner.nextLine();
        System.out.print("Enter your name:");
        String name = scanner.nextLine();
        System.out.print("Adhaar_number:");
        String adhar_no = scanner.nextLine();
        System.out.print("Enter Password:");
        String password = scanner.nextLine();

        if(user_exist(adhar_no)){
            System.out.println("Already registered !!");
            return;
        }
        else{
            String reg_query ="insert into user values(?,?,?);" ;
            try{
                PreparedStatement ps = connection.prepareStatement(reg_query);
                ps.setString(1,name);
                ps.setString(2,adhar_no);
                ps.setString(3,password);

                int affectedrows = ps.executeUpdate();
                if (affectedrows>0){
                    System.out.println("registered successfully");
                }
                else{
                    System.out.println("failed");
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    private boolean user_exist(String adharNo) {
        String exist_query ="SELECT * FROM user WHERE Adhar_no = ? ;" ;
        try{
            PreparedStatement ps = connection.prepareStatement(exist_query);
            ps.setString(1,adharNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return true;
            }
            else{
                return false;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public String login() {

        scanner.nextLine();
        System.out.print("Adhaar_number:");
        String adhar_no = scanner.nextLine();
        System.out.print("Enter Password:");
        String password = scanner.nextLine();

        if (user_exist(adhar_no)) {
            String log_query = "SELECT * FROM User WHERE Adhar_no = ? AND password = ?;";
            try {
                PreparedStatement ps = connection.prepareStatement(log_query);
                ps.setString(1, adhar_no);
                ps.setString(2, password);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    System.out.println("login successfully");
                    return adhar_no;
                } else {
                    System.out.println("failed,invalid password or adhaar number !!");
                    return null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
        else {
            System.out.println("do registration first !! ");
            return null;
        }
    }
}
