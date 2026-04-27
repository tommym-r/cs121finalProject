import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Session implements HasMenu{
  private User user;
  private Database database;
  private Admin admin;
  private Scanner input;

  public Session(){
    database = new Database();
    admin = new Admin();
    input = new Scanner(System.in);
    //loadPlayersFromFile();
    //loadGamesFromFile();
    loadDatabase();
  } // end constructor
  
  public static void main(String[] args){
    Session session = new Session();
    session.start();
  } // end main

  public String menu(){
    return "\n--- Welcome ---\n" +
           "0) Exit\n" +
           "1) Login as Admin\n" +
           "2) Continue as Guest\n" +
           "Enter choice: ";
  } // end menu

  public void start(){
    boolean keepGoing = true;
    while (keepGoing){
      System.out.print(menu());
      String response = input.nextLine();
      switch (response){
        case "0":
          saveDatabase();
          keepGoing = false;
          System.out.println("Goodbye");
          break;
        case "1":
          adminLogin();
          break;
        case "2":
          user = new Guest();
          guestMenu();
          break;
        default:
          System.out.println("Invalid input, please try again.");
      } // end switch
    } // end while
  } // end start

  private void adminLogin(){
    boolean keepGoing = true;
    while (keepGoing){
      if (admin.login()){
        user = admin;
        System.out.println("Login successful!");
        adminMenu();
      } else {
        System.out.println("Incorrect username or password, please try again.");
        System.out.println("0) Exit");
        System.out.println("1) Try again");
        System.out.print("Enter choice: ");
        String response = input.nextLine();
        if (response.equals("0")){
          keepGoing = false;
        } // end if
      } // end if
    } // end while
  } // end adminLogin

  private void adminMenu(){
    boolean keepGoing = true;
    while (keepGoing){
      System.out.print(user.menu());
      String response = input.nextLine();
      switch (response){
        case "0":
          saveDatabase();
          keepGoing = false;
          user = null;
          System.out.println("Logged out successfully.");
          break;
        case "1":
          viewPlayers();
          break;
        case "2":
          addPlayer();
          break;
        case "3":
          deletePlayer();
          break;
        case "4":
          updatePlayer();
          break;
        case "5":
          addGame();
          break;
        case "6":
          deleteGame();
          break;
        case "7":
          updateGame();
          break;
        default:
          System.out.println("Invalid choice, please try again.");
      } // end switch
    } // end while
  } // end adminMenu

  private void guestMenu(){
    boolean keepGoing = true;
    while (keepGoing){
      System.out.print(user.menu());
      String response = input.nextLine();
      switch (response){
        case "0":
          keepGoing = false;
          user = null;
          System.out.println("Goodbye!");
          break;
        case "1":
          viewPlayers();
          break;
        default:
          System.out.println("Invalid choice, please try again.");
      } // end switch
    } // end while
  } // end guestMenu

  private void addPlayer(){
    System.out.print("Enter player name to add: ");
    String name = input.nextLine();
    if (database.getPlayer(name) != null){
      System.out.println("A player with that name already exists.");
    } else {
      database.addPlayer(name);
      System.out.println(name + " added successfully.");
    } // end if
  } // end addPlayer

  private void deletePlayer(){
    System.out.print("Enter player name to delete: ");
    String name = input.nextLine();
    if (database.getPlayer(name) != null){
      System.out.println("Are you sure you want to delete " + name + "? This will delete all of their game logs as well.");
      System.out.println("1) Yes");
      System.out.println("2) No");
      System.out.print("Enter choice: ");
      String response = input.nextLine();
      if (response.equals("1")){
        database.deletePlayer(name);
        System.out.println(name + " deleted successfully.");
      } else {
        System.out.println("Deletion cancelled.");
      } // end if
    } else { 
      System.out.println("Player not found.");
    }// end if
  } // end deletePlayer

  private void updatePlayer(){
    System.out.print("Enter player name to update: ");
    String oldName = input.nextLine();
    if (database.getPlayer(oldName) != null){
      System.out.print("Enter new name: ");
      String newName = input.nextLine();
      if (database.getPlayer(newName) != null){
        System.out.println("A player with that name already exists.");
      } else {
        database.updatePlayer(oldName, newName);
        System.out.println("Player updated successfully.");
      } // end if
    } else {
      System.out.println("Player not found.");
    } // end if
  } // end updatePlayer
  
  private void addGame(){
    System.out.print("Enter player name: ");
    String name = input.nextLine();
    Player player = database.getPlayer(name);
    if (player != null){
      try{
        
        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = input.nextLine();
        System.out.print("Enter points: ");
        int points = Integer.parseInt(input.nextLine());
        System.out.print("Enter rebounds: ");
        int rebounds = Integer.parseInt(input.nextLine());
        System.out.print("Enter assists: ");
        int assists = Integer.parseInt(input.nextLine());
        System.out.print("Enter blocks: ");
        int blocks = Integer.parseInt(input.nextLine());
        System.out.print("Enter steals: ");
        int steals = Integer.parseInt(input.nextLine());
        System.out.print("Enter minutes: ");
        int minutes = Integer.parseInt(input.nextLine());
 
        addGame(new GameLog(player.getId(), date, points, rebounds, assists, blocks, steals, minutes));

      } catch (Exception e) {
        System.out.println("Invalid input, stats must be whole numbers. Game not added.");
      } // end try catch
    } else {
      System.out.println("Player not found.");
    } // end if
  } // end addGame

  private void addGame(GameLog gameLog){
    // overloading to allow for the potential of adding a game by reading a file while keeping the manual add game functionality
    database.addGame(gameLog);
    System.out.println("Game added successfully.");
  } // end addGame
 
  private void deleteGame(){
    System.out.print("Enter player name: ");
    String name = input.nextLine();
    Player player = database.getPlayer(name);
    if (player != null){
      System.out.print("Enter date of game to delete (YYYY-MM-DD): ");
      String date = input.nextLine();
      boolean gameExists = false;
      for (GameLog g : database.getGameLogs(player.getId())){
        if (g.getDate().equals(date)){
          gameExists = true;
          break;
        } // end if
      } // end for
      if (gameExists){
        System.out.println("Are you sure you want to delete this game?");
        System.out.println("1) Yes");
        System.out.println("2) No");
        System.out.print("Enter choice: ");
        String response = input.nextLine();
        if (response.equals("1")){
          database.deleteGame(player.getId(), date);
          System.out.println("Game deleted successfully.");
        } else {
          System.out.println("Deletion cancelled.");
        } // end if
      } else {
        System.out.println("No game found for that date.");
      } // end if
    } else {
      System.out.println("Player not found.");
    } // end if
  } // end deleteGame

  private void updateGame(){
    System.out.print("Enter player name: ");
    String name = input.nextLine();
    Player player = database.getPlayer(name);
    if (player != null){
      System.out.print("Enter date of game to update (YYYY-MM-DD): ");
      String date = input.nextLine();
      boolean gameExists = false;
      for (GameLog g : database.getGameLogs(player.getId())){
        if (g.getDate().equals(date)){
          gameExists = true;
          break;
        } // end if
      } // end for
      if (gameExists){
        try {
          System.out.print("Enter points: ");
          int points = Integer.parseInt(input.nextLine());
          System.out.print("Enter rebounds: ");
          int rebounds = Integer.parseInt(input.nextLine());
          System.out.print("Enter assists: ");
          int assists = Integer.parseInt(input.nextLine());
          System.out.print("Enter blocks: ");
          int blocks = Integer.parseInt(input.nextLine());
          System.out.print("Enter steals: ");
          int steals = Integer.parseInt(input.nextLine());
          System.out.print("Enter minutes: ");
          int minutes = Integer.parseInt(input.nextLine());
          GameLog updatedLog = new GameLog(player.getId(), date, points, rebounds, assists, blocks, steals, minutes);
          database.updateGame(updatedLog);
          System.out.println("Game updated successfully.");
        } catch (Exception e){
          System.out.println("Invalid input, stats must be whole numbers, game not updated");
        } // end try catch
      } else {
        System.out.println("No game found for that date.");
      } // end if
    } else {
      System.out.println("Player not found.");
    } // end if
  } // end updateGame
  
  private void viewPlayers(){
    boolean keepGoing = true;
    while (keepGoing){
      ArrayList<Player> players = database.getAllPlayers();
      if (players.size() == 0){
        System.out.println("No players in database.");
        keepGoing = false;
      } else {
        System.out.println("\n--- Players ---");
        for (int i = 0; i < players.size(); i++){
          System.out.println((i + 1) + ") " + players.get(i).getName());
        } // end for
        System.out.println("---");
        System.out.println("E) Exit");
        System.out.println("S) Sort Players by Average");
        System.out.println("Enter a player's number to view their stats.");
        System.out.print("Enter choice: ");
        String response = input.nextLine();
        if (response.equalsIgnoreCase("E")){
          keepGoing = false;
        } else if (response.equalsIgnoreCase("S")){
          sortPlayersMenu(players);
        } else {
          try {
            int responseInt = Integer.parseInt(response);
            if (responseInt >= 1 && responseInt <= players.size()){
              playerMenu(players.get(responseInt - 1));
            } else { 
              System.out.println("Invalid choice, please try again.");
            } // end if
          } catch (Exception e){
            System.out.println("Invalid choice, please try again.");
          } // end try catch
        } // end if
      } // end if
    } // end while
  } // end viewPlayers

  private void sortPlayersMenu(ArrayList<Player> players){
    System.out.println("\n--- Sort By ---");
    System.out.println("0) Exit");
    System.out.println("1) Points");
    System.out.println("2) Rebounds");
    System.out.println("3) Assists");
    System.out.println("4) Blocks");
    System.out.println("5) Steals");
    System.out.println("6) Minutes");
    System.out.print("Enter choice: ");
    String response = input.nextLine();
    String stat;
    switch (response){
      case "1":
        stat = "points";
        break;
      case "2":
        stat = "rebounds";
        break;
      case "3":
        stat = "assists";
        break;
      case "4":
        stat = "blocks";
        break;
      case "5":
        stat = "steals";
        break;
      case "6":
        stat = "minutes";
        break;
      default:
        return;
    } // end switch
    ArrayList<Player> sorted = StatCalc.sortPlayersByAvg(players, database, stat);
    System.out.println("\n--- Players Sorted by " + stat + " ---");
    for (int i = 0; i < sorted.size(); i++){
      ArrayList<GameLog> logs = database.getGameLogs(sorted.get(i).getId());
      double avg = StatCalc.getPerGame(logs, stat);
      System.out.printf("%d. %-20s %.1f%n", (i + 1), sorted.get(i).getName(), avg);
    } // end for
    System.out.println("\n Press Enter to continue...");
    input.nextLine();
  } // end sortPlayersMenu

  private void playerMenu(Player player){
    boolean keepGoing = true;
    while (keepGoing){
      System.out.println("\n--- " + player.getName() + " ---");
      System.out.println("0) Exit");
      System.out.println("1) View Game Logs");
      System.out.println("2) View Career Averages");
      System.out.println("3) View Career Highs");
      System.out.println("4) Sort Game Logs by Stat");
      System.out.print("Enter choice: ");
      String response = input.nextLine();
      ArrayList<GameLog> logs = database.getGameLogs(player.getId());
      switch (response){
        case "0":
          keepGoing = false;
          break;
        case "1":
          viewGameLogs(logs);
          break;
        case "2":
          viewCareerAvgs(logs);
          break;
        case "3":
          viewCareerHighs(logs);
          break;
        case "4":
          sortGameLogsMenu(logs);
          break;
        default:
          System.out.println("Invalid choice, please try again.");
      } // end swithc
    } // end while
  } // end playerMenu

  private void viewGameLogs(ArrayList<GameLog> logs){
    if (logs.size() == 0){
      System.out.println("No game logs found.");
    } else {
      System.out.println("\n--- Game Logs ---");
      for (GameLog g : logs){
        System.out.println(g.toString());
      } // end for
    } // end if
    System.out.println("\nPress Enter to continue...");
    input.nextLine();
  } // end viewGameLogs
  
  private void viewCareerAvgs(ArrayList<GameLog> logs){
    if (logs.size() == 0){
      System.out.println("No game logs found.");
    } else {
      System.out.println("\n--- Career Averages ---");
      System.out.printf("%-12s %.1f%n", "Points:", StatCalc.getPerGame(logs, "points"));
      System.out.printf("%-12s %.1f%n", "Rebounds:", StatCalc.getPerGame(logs, "rebounds"));
      System.out.printf("%-12s %.1f%n", "Assists:", StatCalc.getPerGame(logs, "assists"));
      System.out.printf("%-12s %.1f%n", "Blocks:", StatCalc.getPerGame(logs, "blocks"));
      System.out.printf("%-12s %.1f%n", "Steals:", StatCalc.getPerGame(logs, "steals"));
      System.out.printf("%-12s %.1f%n", "Minutes:", StatCalc.getPerGame(logs, "minutes"));
    } // end if
    System.out.println("\nPress Enter to continue...");
    input.nextLine();
  } // end viewCareerAverages

  private void viewCareerHighs(ArrayList<GameLog> logs){
    if (logs.size() == 0){
      System.out.println("No game logs found.");
    } else {
      System.out.println("\n--- Career Highs ---");
      System.out.printf("%-12s %d%n", "Points:",   StatCalc.getHigh(logs, "points"));
      System.out.printf("%-12s %d%n", "Rebounds:", StatCalc.getHigh(logs, "rebounds"));
      System.out.printf("%-12s %d%n", "Assists:",  StatCalc.getHigh(logs, "assists"));
      System.out.printf("%-12s %d%n", "Blocks:",   StatCalc.getHigh(logs, "blocks"));
      System.out.printf("%-12s %d%n", "Steals:",   StatCalc.getHigh(logs, "steals"));
      System.out.printf("%-12s %d%n", "Minutes:",  StatCalc.getHigh(logs, "minutes"));
    } // end if
    System.out.println("\nPress Enter to continue...");
    input.nextLine();
  } // end viewCareerHighs

  private void sortGameLogsMenu(ArrayList<GameLog> logs){
    if (logs.size() == 0){
      System.out.println("No game logs found.");
      return;
    } // end if
    System.out.println("\n--- Sort Game Logs By ---");
    System.out.println("0) Exit");
    System.out.println("1) Points");
    System.out.println("2) Rebounds");
    System.out.println("3) Assists");
    System.out.println("4) Blocks");
    System.out.println("5) Steals");
    System.out.println("6) Minutes");
    System.out.print("Enter choice: ");
    String response = input.nextLine().trim();
    String stat;
    switch (response){
      case "1":
        stat = "points";
        break;
      case "2":
        stat = "rebounds";
        break;
      case "3":
        stat = "assists";
        break;
      case "4":
        stat = "blocks";
        break;
      case "5":
        stat = "steals";
        break;
      case "6":
        stat = "minutes";
        break;
      default:
        return;
    } // end switch
    ArrayList<GameLog> sorted = StatCalc.sortBy(logs, stat);
    System.out.println("\n--- Game Logs Sorted by " + stat + " ---");
    for (GameLog g : sorted){
        System.out.println(g.toString());
    } // end for
    System.out.println("\nPress Enter to continue...");
    input.nextLine();
  } // end sortGameLogsMenu

  private void saveDatabase(){
    try{
      FileOutputStream fo = new FileOutputStream("database.dat");
      ObjectOutputStream obOut = new ObjectOutputStream(fo);
      obOut.writeObject(database);
      obOut.close();
      fo.close();
      System.out.println("Database saved successfully.");
    } catch (Exception e){
      System.out.println("Error saving database.");
    } // end try catch
  } // end saveDatabase

  public void loadDatabase(){
    try {
      FileInputStream fi = new FileInputStream("database.dat");
      ObjectInputStream obIn = new ObjectInputStream(fi);
      database = (Database)(obIn.readObject());
      obIn.close();
      fi.close();
    } catch (Exception e){
      System.out.println(e.getMessage());
    } // end try catch
  } // end loadDatabase

  private void loadPlayersFromFile(){
    try{
      File playerFile = new File("players.csv");
      Scanner fileScanner = new Scanner(playerFile);
      while (fileScanner.hasNextLine()){
        String line = fileScanner.nextLine();
        if (!line.isEmpty()){
          database.addPlayer(line);
        } // end if
      } // end while
      fileScanner.close();
      System.out.println("Players loaded successfully.");
    } catch (IOException e){
      System.out.println(e.getMessage());
    } // emd try catch
  } // end loadPlayersFromFile

  private void loadGamesFromFile(){
    try{
      File gamesFile = new File("games.csv");
      Scanner fileScanner = new Scanner(gamesFile);
      while (fileScanner.hasNextLine()){
        String line = fileScanner.nextLine();
        if (!line.isEmpty()){
          String[] loggedGame = line.split(",");
          Player player = database.getPlayer(loggedGame[0]);
          if (player != null){
            String date = loggedGame[1];
            int points = Integer.parseInt(loggedGame[2]);
            int rebounds = Integer.parseInt(loggedGame[3]);
            int assists = Integer.parseInt(loggedGame[4]);
            int blocks = Integer.parseInt(loggedGame[5]);
            int steals = Integer.parseInt(loggedGame[6]);
            int minutes = Integer.parseInt(loggedGame[7]);
            addGame(new GameLog(player.getId(), date, points, rebounds, assists, blocks, steals, minutes));
          } else {
            System.out.println("Player not found: " + loggedGame[0] + ", skipping.");
          } // end if
        } // end if
      } // end while
      fileScanner.close();
      System.out.println("Games loaded successfully.");
    } catch (Exception e){
      System.out.println(e.getMessage());
    } // end try catch
  } // end loadGamesFromFile
} // end Session