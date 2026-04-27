import java.util.Scanner;

public class Admin extends User{
  private String username;
  private String PIN;

  public Admin(){
    this.username = "admin";
    this.PIN = "1111";
  } // end constructor
  
  public Admin(String username, String PIN){
    this.username = username;
    this.PIN = PIN;
  } // end constructor

  public boolean login(){
    boolean success = false;
    Scanner input = new Scanner(System.in);
    System.out.print("Username: ");
    String username = input.nextLine();
    System.out.print("PIN: ");
    String PIN = input.nextLine();
    if (username.equals(this.username)){
      if (PIN.equals(this.PIN)){
        success = true;
      } // end if
    } // end if
    return success;
  } // end login

  public boolean login(String username, String PIN){
    boolean success = false;
    if (username.equals(this.username)){
      if (PIN.equals(this.PIN)){
        success = true;
      } // end if
    } // end if
    return success;
  } // end login

  public String menu(){
    return "\n--- Admin Menu ---\n" +
               "0) Logout\n" +
	             "1) View Players\n" +
               "2) Add Player\n" +
               "3) Delete Player\n" +
               "4) Update Player\n" +
               "5) Add Game\n" +
               "6) Delete Game\n" +
               "7) Update Game\n" +
               "Enter choice: ";
  } // end menu

  public void start(){}

} // end Admin

