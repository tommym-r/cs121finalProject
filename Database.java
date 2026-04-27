import java.util.ArrayList;
import java.io.Serializable;

public class Database implements Serializable{
  private ArrayList<Player> players;
  private ArrayList<GameLog> gameLogs;

  public Database(){
    players = new ArrayList<>();
    gameLogs = new ArrayList<>();
  } // end constructor
  
  public void addPlayer(String name){
    int id = players.size() + 1;
    players.add(new Player(id, name));
  } // end addPlayer

  public void deletePlayer(String name){
    Player target = getPlayer(name);
    if (target != null){
      players.remove(target);
      deletePlayerGameLogs(target.getId());
    } // end if
  } // end deletePlayer

  public void updatePlayer(String oldName, String newName){
    Player target = getPlayer(oldName);
    if (target != null){
      target.setName(newName);
    } // end if
  } // end updatePlayer
  
  public Player getPlayer(String name){
    for (Player p : players){
      if (p.getName().equalsIgnoreCase(name)){
        return p;
      } // end if
    } // end for
    return null;
  } // end getPlayer

  public ArrayList<Player> getAllPlayers(){
    return players;
  } // end getAllPlayers

  public void addGame(GameLog gameLog){
    gameLogs.add(gameLog);
  } // end addGame

  public void deleteGame(int playerId, String date){
    for (int i = 0; i < gameLogs.size(); i++){
      if (gameLogs.get(i).getPlayerId() == playerId && gameLogs.get(i).getDate().equals(date)){
        gameLogs.remove(i);
        return;
      } // end if
    } // end for
  } // end deleteGame

  public void updateGame(GameLog gameLog){
    for (int i = 0; i < gameLogs.size(); i++){
      if (gameLogs.get(i).getPlayerId() == gameLog.getPlayerId() && gameLogs.get(i).getDate().equals(gameLog.getDate())){
        gameLogs.set(i, gameLog);
        return;
      } // end if
    } // end for
  } // end updateGame

  public ArrayList<GameLog> getGameLogs(int playerId){
    ArrayList<GameLog> result = new ArrayList<>();
    for (GameLog g : gameLogs){
      if (g.getPlayerId() == playerId){
        result.add(g);
      } // end if
    } // end for
    return result;
  } // end getGameLogs


  private void deletePlayerGameLogs(int playerId){
    for (int i = gameLogs.size() - 1; i >= 0; i--){
      if (gameLogs.get(i).getPlayerId() == playerId){
        gameLogs.remove(i);
      } // end if
    } // end for
  } // end deletePlayerGameLogs

} // end DataBase
