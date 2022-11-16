import java.util.ArrayList;

public class PlayerList
{
  private ArrayList<Player> playerList;

  PlayerList()
  {
    playerList = new ArrayList<>();
  }

  PlayerList(ArrayList<Player> playerList)
  {
    this.playerList = playerList;
  }

  public Player getPlayer(int index)
  {
    return playerList.get(index);
  }

  public int size()
  {
    return playerList.size();
  }

  public void addPlayer(Player player)
  {
    if (player.getID() == -1)
    {
      if (playerList.size() == 0)
      {
        player.setID(0);
        playerList.add(player);
      }
      else
      {
        int lastID = playerList.get(playerList.size() - 1).getID();
        player.setID(lastID + 1);
        playerList.add(player);
      }
    }
    else
    {
      playerList.add(player);
    }
  }

  @Override public String toString()
  {
    return "PlayerList{" + "playerList=" + playerList + '}';
  }
}
