import java.util.ArrayList;

public class StatCalc{
  
  private StatCalc(){}

  private static int getStat(GameLog g, String stat){
    switch (stat){
      case "points":
        return g.getPoints();
      case "rebounds":
        return g.getRebounds();
      case "assists":
        return g.getAssists();
      case "steals":
        return g.getSteals();
      case "blocks":
        return g.getBlocks();
      case "minutes":
        return g.getMinutes();
      default:
        return 0;
    } // end switch
  } // end getStat

  public static double getPerGame(ArrayList<GameLog> gameLogs, String stat){
    int total = 0;
    for (GameLog g : gameLogs){
      total += getStat(g, stat);
    } // end for
    return (double) total / gameLogs.size();
  } // end getPerGame
    
  public static int getHigh(ArrayList<GameLog> gameLogs, String stat){
    int high = 0;
    for (GameLog g : gameLogs){
      int value = getStat(g, stat);
      if (value > high){
        high = value;
      } // end if
    } // end for
    return high;
  } // end getHigh

  public static ArrayList<GameLog> sortBy(ArrayList<GameLog> gameLogs, String stat){
    ArrayList<GameLog> sorted = new ArrayList<>(gameLogs);
    for (int i = 0; i < sorted.size() - 1; i++){
      for (int j = 0; j < sorted.size() - 1; j++){
        int a = getStat(sorted.get(j), stat);
        int b = getStat(sorted.get(j + 1), stat);
        if (a < b){
          GameLog temp = sorted.get(j);
          sorted.set(j, sorted.get(j+1));
          sorted.set(j + 1, temp);
        } // end if
      } // end for
    } // end for
    return sorted;
  } // end sortBy

  public static ArrayList<Player> sortPlayersByAvg(ArrayList<Player> players, Database db, String stat){
    ArrayList<Player> sorted = new ArrayList<>(players);
    for (int i = 0; i < sorted.size() - 1; i++){
      for (int j = 0; j < sorted.size() - 1; j++){
        double a = getPerGame(db.getGameLogs(sorted.get(j).getId()), stat);
        double b = getPerGame(db.getGameLogs(sorted.get(j + 1).getId()), stat);
        if (a < b){
          Player  temp = sorted.get(j);
          sorted.set(j, sorted.get(j + 1));
          sorted.set(j + 1, temp);
        } // end if
      } // end for
    } // end for
    return sorted;
  } // end sortBy

} // end StatCalc

