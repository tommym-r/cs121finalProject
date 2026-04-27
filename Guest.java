public class Guest extends User{
  public Guest(){}

  public String menu(){
    return "\n--- Guest Menu ---\n" +
               "0) Exit\n" +    
               "1) View Player Stats\n" +
               "Enter choice: ";
  } // end menu

  public void start(){}

} // end Guest
