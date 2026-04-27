import java.io.Serializable;

public class GameLog implements Serializable{
  private int playerId;
  private String date;
  private int points;
  private int rebounds;
  private int assists;
  private int steals;
  private int blocks;
  private int minutes;

  public GameLog(int playerId, String date, int points, int rebounds, int assists, int blocks, int steals, int minutes){
    this.playerId = playerId;
    this.date = date;
    this.points = points;
    this.rebounds = rebounds;
    this.assists = assists;
    this.blocks = blocks;
    this.steals = steals;
    this.minutes = minutes;
  } // end constructor
  
  public int getPlayerId() {
        return playerId;
    } // end get PlayerID

    public String getDate() {
        return date;
    } // end getDate

    public int getPoints() {
        return points;
    } // end getPoints

    public int getRebounds() {
        return rebounds;
    } // end getRebounds

    public int getAssists() {
        return assists;
    } // end getAssists

    public int getBlocks() {
        return blocks;
    } // end getBlocks

    public int getSteals() {
        return steals;
    } // end getSteals

    public int getMinutes() {
        return minutes;
    } // end getMintes
    
    public String toString() {
        return "Date: " + date +
               " | Points: " + points +
               " | Rebounds: " + rebounds +
               " | Assists: " + assists +
               " | Blocks: " + blocks +
               " | Steals: " + steals +
               " | Minutes: " + minutes;
    } // end toString
} // end GameLog
