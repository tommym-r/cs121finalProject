# cs121finalProject
Basketball Player Database Project

```mermaid
classDiagram

class HasMenu{
    <<interface>>
    +menu() String
    +start() void
}


class SQLiteDB{
    +addPlayer(name) void
    +deletePlayer(name) void
    +addGame(entry) void
    +updateGame(entry) void
    +getPlayer(name) Player
    +getAllPlayers() ArrayList~Player~ 

}

class Player{
    -id int
    -name String
    -points int
    -rebounds int
    -assists int
    -games int
    -minutes int
    -blocks int
    -steals int
    +Player(id, name, points, rebounds, assists, blocks, steals, games, minutes)
    +getName() String
    +getPoints() int
    +getRebounds() int
    +getAssists() int
    +getGames() int
    +getMinutes() int
    +getBlocks() int
    +getSteals() int
    +toString() String
}

class StatCalc {
    +StatCalc()
    +getPointsPerGame(player) double
    +getReboundsPerGame(player) double
    +getAssistsPerGame(player) double
    +getMinutesPerGame(player) double
    +getBlocksPerGame(player) double
    +getStealsPerGame(player) double
    +getHighPoints(playerList) int
    +getHighRebounds(playerList) int
    +getHighAssists(playerList) int
    +getHighBlocks(playerList) int
    +getHighSteals(playerList) int
    +getHighMinutes(playerList) int
    +sortPlayersPoints(playerList) ArrayList~Player~
    +sortPlayersRebounds(playerList) ArrayList~Player~
    +sortPlayersAssists(playerList) ArrayList~Player~
    +sortPlayersGames(playerList) ArrayList~Player~
    +sortPlayersMinutes(playerList) ArrayList~Player~
    +sortPlayersSteals(playerList) ArrayList~Player~
    +sortPlayersBlocks(playerList) ArrayList~Player~
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
    - entry String array
    - playerList ArrayList~Player~
    +menu() String
    +start() void
    +main() void
}

Admin --|> User : extends
Guest --|> User : extends
User --|> HasMenu : implements
Session --|> HasMenu : implements
Session --> SQLiteDB : uses
Session --> StatCalc : uses
SQLiteDB --> Player : creates
StatCalc --> Player : uses
```
