Session.class: Session.java HasMenu.class Player.class GameLog.class Database.class StatCalc.class User.class Admin.class Guest.class
	javac -g Session.java

Admin.class: Admin.java User.class
	javac -g Admin.java

Guest.class: Guest.java User.class
	javac -g Guest.java

User.class: User.java HasMenu.class
	javac -g User.java

Database.class: Database.java Player.class GameLog.class
	javac -g Database.java

StatCalc.class: StatCalc.java GameLog.class Database.class Player.class
	javac -g StatCalc.java

Player.class: Player.java
	javac -g Player.java

GameLog.class: GameLog.java
	javac -g GameLog.java

HasMenu.class: HasMenu.java
	javac -g HasMenu.java

testPlayer: Player.class
	java Player

testGameLog: GameLog.class
	java GameLog

testDatabase: Database.class
	java Database

testStatCalc: StatCalc.class
	java StatCalc

clean:
	rm *.class

run: Session.class
	java Session

debug: Session.class
	jdb Session
