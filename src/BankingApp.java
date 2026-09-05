import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class BankingApp {
    private static final String url = "jdbc:mysql://localhost:3306/banking_system";
    private static final String username = "root";
    private static final String password = "xxxxxxx";

    public static void main(String[] args) throws FileNotFoundException , SQLException {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Scanner sc = new Scanner(System.in);
            User user = new User(connection,sc);
            Accounts accounts = new Accounts(connection,sc);
            AccountManager accountManager = new AccountManager(connection,sc);

            String Adhaar_no ;
            long acc_no = 0;

            while (true){
                System.out.println("*** WELCOME TO BANKING SYSTEM ***");
                System.out.println();
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print ("Enter your choice: ");
                int choice1 = sc.nextInt();
                switch (choice1){
                    case 1: user.register();
                        break;
                    case 2:
                        Adhaar_no = user.login();
                        if(Adhaar_no!=null) {
                            if (!(accounts.account_exist(Adhaar_no))) {
                                System.out.println();
                                System.out.println("Create your account first");
                                accounts.create_account(Adhaar_no);
                            }

                            acc_no = accounts.get_accountNo(Adhaar_no);
                            int choice2 = 0;
                            while (choice2 != 6) {
                                System.out.println();
                                System.out.println("1. Debit Money");
                                System.out.println("2. Credit Money");
                                System.out.println("3. Transfer Money");
                                System.out.println("4. Check Balance");
                                System.out.println("5. Close account");
                                System.out.println("6. Log Out");
                                System.out.print("Enter your choice: ");
                                choice2 = sc.nextInt();
                                switch (choice2) {
                                    case 1:
                                        accountManager.debit(acc_no);
                                        break;
                                    case 2:
                                        accountManager.credit(acc_no);
                                        break;
                                    case 3:
                                        accountManager.transfer(acc_no);
                                        break;
                                    case 4:
                                        accountManager.balance(acc_no);
                                        break;
                                    case 5:
                                        accounts.close_account(acc_no);
                                        break;
                                    case 6:
                                        break;
                                    default:
                                        System.out.println("enter valid choice");
                                        break;
                                }
                            }
                        }else{
                            continue;
                        }
                    case 3:
                        return;
                    default:
                        break;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
