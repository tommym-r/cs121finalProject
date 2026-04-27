# cs121finalProject
Basketball Player Database Project

This project functions as a simple database for basketball statistics, allowing for users to view data in different forms or add/manipulate the data.<br>
Users can login as guest without any credentials and have access to view the data, but to edit the database the user must login as an Admin<br>
A simple usecase could be a small Highschool Basketball team doing statkeeping for their team's players<br>

### Admin Login Credentials
username: admin
password: 1111

The program will save any changes made to the database in the 'database.dat' file and then load it when the program is started to allow for persistance between sessions.<br>
Users can view an individual players stats in a variety of manners, or sort a list of all players by their average stats.<br>

## Class Explanations
### GameLog.java
The GameLog objects are the core building block of the program. Each GameLog contains a statline from an individual game of a single player, tied to the player via the playerId variable.<br>
Each statistic is saved as a variable via the constructor and has a corresponding getter method.<br>
Contains an overridden toString() method that allows for simpler printing of the statline<br>

### Player.java
The second building block of the program, the Player class's key feature is the playerId variable that allows for GameLogs to be tied to the correct player.<br>
Both name and id variables have getter methods and name has a setter method in case the admin needs to adjust a name (id shouldn't be changed as that would mess up the data structure)<br>
Contains a similar toString() method as in GameLog.java for similar purposes<br>

### Database.java
Database.java holds all of the Player and GameLog objects in two separate ArrayLists and contains the methods needed to manage them<br>
addPlayer(), deletePlayer(), and updatePlayer() are all self explanatory, where addPlayer atomatically assigns the Player a playerId based on the current length of the players ArrayList<br>
updatePlayer() keeps playerId the same between the old and new names so its ideal for something like a typo in data entry where the GameLogs should still stay attatched to the Player object<br>
deletePlayer() also calls the deletePlayerGameLogs() method which allows it to automatically remove and GameLogs that contain the playerId of the player being deleted<br>
addGame(), deleteGame(), and updateGame() are also self explanitory, interacting directly with the gameLogs ArrayList<br>
updateGame() functions by iterating through the array and checking against playerId and date variables, with the assumption that only 1 game per day is possible. <br>
Additionally this means if a date is intered wrong the GameLog needs to be deleted and re-added<br>
getGameLogs() retrieves every GameLog of a given player from the ArrayList and returns a new ArrayList with just those GameLogs<br>

### StatCalc.java
StatCalc.java holds the methods that allow for data manipulation and visualization<br>
getStat() takes user input (given in Session.java) to decide which GameLog getter method to call and retrieve the correct variable the from the GameLog object<br>
This approach allows for much fewer methods needed to actually compute every statistic (as opposed to a getPointsPerGame(), getReboundsPerGame() etc approach)<br>
getPerGame() returns the average per-game value of a players given statistic<br>
getHigh() returns the highest value a player has achieved in a given statistic<br>
sortBy() and sortPlayersByAvg() return new ArrayLists containing a given players GameLogs sorted by the given statistic (sortBy) or all players sorted by their averages in a given statistic (sortPlayersByAvg)<br>
Both make use of a bubble sort<br>

### HasMenu.java
Interface that specifies the menu() and start() classes<br>

### User.java
Abstract class that is used by Session.java() to treat Admin() and Guest() interchangibly in certain spots<br>

### Guest.java
Guest class is pretty bare bones, only really containing the initial guest menu. Guest users should be able to make use of certain functionalities but not others, so this menu only gives them access to what is needed.<br>

### Admin.java
Admin class is a user that has more permissions than the Guest, shown by the additional options in its menu method.<br>
Contains an overloaded login() method in case the program moved past the hard coded login credentials and allows for easier testing/different implementation<br>
the menu methods for both User classes return strings and have no interactivity with the user, keeping all UI contained in the Session class<br>

### Session.java
Session is the main hub of the program, containing all the methods that interact with the user and using Database.java and StatCalc.java in particular.<br>
Additionally Session.java is where the program interacts with other files as well as saving the database between uses<br>
Session.menu() starts with a buffer menu prompting the user to proceed as either an Admin or Guest<br>
Session.start() prints the menu and is where the actual user interaction starts, moving on to the subsequent menus<br>
All methods from lines 57 to 413 are UI methods printing options for the user and calling on methods from StatCalc, Database, Player, and GameLog<br>
Thsee are all self explainatory are generally named in tandem with the other class' methods they call upon<br>
They mostly follow the same logic featuring switch and if-else statments to move through options and user responses<br>
viewGameLogs(), viewCareerAvgs(), viewCareerHighs(), and sortGameLogsMenu() all display stats based on the user input (though only sortGameLogsMenu() takes user input among these)<br>
These could be considered the endpoint of the "nested" menus<br>
SaveDatabase() and LoadDatabase() save and load the database object to the "database.dat" file to allow for persistence between sessions.<br>
LoadDatabase is called once in the session constructor, while SaveDatabase is called whenever a user exists the program<br>
LoadPlayerFromFile() LoadGamesFromFile() both read correctly formatted csv files and append the data to the respective ArrayList<br>
these methods are currently not in use (commented out in the Session constructor) but were initially used to load "players.csv" and "games.csv" into the database to allow for easy testing<br>
These methods could be used later to allow for a User to upload more data through a file in addition to manually adding gamelogs through the program if the program were to be expanded<br>


### UML Diagram

```mermaid
classDiagram

class HasMenu{
    <<interface>>
    +menu() String
    +start() void
}

class Database{
    <<Serializable>>
    - players ArrayList~Player~
    - gameLogs ArrayList~GameLog~
    +Database()
    +addPlayer(name) void
    +deletePlayer(name) void
    +updatePlayer(oldName, newName) void
    +addGame(gameLog) void
    +deleteGame(playerId, date) void
    +updateGame(gameLog) void
    +getPlayer(name) Player
    +getAllPlayers() ArrayList~Player~
    +getGameLogs(playerId) ArrayList~GameLog~
    -deletePlayerGameLogs(playerId) void
}

class GameLog{
    <<Serializable>>
    - playerId int
    - date String
    - points int
    - rebounds int
    - assists int
    - blocks int
    - steals int
    - minutes int
    +GameLog(playerId, date, points, rebounds, assists, blocks, steals, minutes)
    +getPlayerId() int
    +getDate() String
    +getPoints() int
    +getRebounds() int
    +getAssists() int
    +getBlocks() int
    +getSteals() int
    +getMinutes() int
    +toString() String
}

class Player{
    <<Serializable>>
    - id int
    - name String
    +Player(id, name)
    +getId() int
    +getName() String
    +setName(name) void
    +toString() String
}

class StatCalc {
    -StatCalc()
    +getPerGame(gameLogs, stat) double$
    +getHigh(gameLogs, stat) int$
    +sortBy(gameLogs, stat) ArrayList~GameLog~$
    +sortPlayersByAvg(players, db, stat) ArrayList~Player~$
    -getStat(g, stat) int$
}

class User{
    <<abstract>>
    +menu()* String
    +start()* void
}

class Admin{
    - username String
    - PIN String
    +Admin()
    +login() boolean
    +login(username, PIN) boolean
}

class Guest{
    +Guest()
}

class Session{
    - user User
    - database Database
    - admin Admin
    - input Scanner
    +menu() String
    +start() void
    +main() void
    -adminLogin() void
    -adminMenu() void
    -guestMenu() void
    -addPlayer() void
    -deletePlayer() void
    -updatePlayer() void
    -addGame() void
    -addGame(gameLog) void
    -deleteGame() void
    -updateGame() void
    -viewPlayers() void
    -sortPlayersMenu(players) void
    -playerSubMenu(player) void
    -viewGameLogs(logs) void
    -viewCareerAverages(logs) void
    -viewCareerHighs(logs) void
    -sortGameLogsMenu(logs) void
    -saveDatabase() void
    -loadDatabase() void
    -loadPlayersFromFile() void
    -loadGamesFromFile() void
}

Admin --|> User : extends
Guest --|> User : extends
User --|> HasMenu : implements
Session --|> HasMenu : implements
Session --> Database : uses
Session --> StatCalc : uses
Database --* Player : owns
Database --* GameLog : owns
StatCalc --> GameLog : uses
StatCalc --> Database : uses
```
