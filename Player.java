import java.io.Serializable;

public class Player implements Serializable{
  private int id;
  private String name;

  public Player(int id, String name){
    this.id = id;
    this.name = name;
  } // end constructor

  public int getId(){
    return id;
  } // end getID

  public String getName(){
    return name;
  } // end getName

  public void setName(String name){
    this.name = name;
  } // end setName

  public String toString(){
    return "ID: " + id + " | Name: " + name;
  } // end toString
} // end Player
