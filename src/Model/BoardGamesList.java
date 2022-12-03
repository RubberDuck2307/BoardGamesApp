package Model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class BoardGamesList
{
  private ArrayList<BoardGame> boardGamesList;

  public BoardGamesList()
  {
    boardGamesList = new ArrayList<>();
  }

  public BoardGamesList(ArrayList<BoardGame> boardGames) //This is composition
  {
    this.boardGamesList = new ArrayList<>();
    for (BoardGame boardGame : boardGames)
    {
      BoardGame newBoardGame = boardGame.copy();
      this.addBoardGame(newBoardGame);
    }
  }

  public void addBoardGame(BoardGame boardGame)
  {
    if (boardGame.getID() == -1)
    {
      if (boardGamesList.size() != 0)
      {
        boardGame.setID(
            boardGamesList.get(boardGamesList.size() - 1).getID() + 1);
      }
      else
      {
        boardGame.setID(0);
      }
    }
    boardGamesList.add(boardGame);
  }

  public void setBoardGame(BoardGame boardgame, int ID)
  {
    for (int i = 0; i < boardGamesList.size(); i++)
    {
      if (boardGamesList.get(i).getID() == ID)
      {
        boardGamesList.set(i, boardgame);
        break;
      }
      else
      {
        boardGamesList.add(boardgame);
      }
    }
  }

  public void deleteByID(int ID)
  {
    for (int i = 0; i < boardGamesList.size(); i++)
    {
      if (boardGamesList.get(i).getID() == ID)
      {
        boardGamesList.remove(i);
        break;
      }
    }
  }

  public BoardGamesList getBoardGameListByStatus(String status)
  {
    BoardGamesList newBoardGameList = new BoardGamesList();
    for (int i = 0; i < size(); i++)
    {
      if (Objects.equals(boardGamesList.get(i).getAvailabilityStatus(), status))
      {
        newBoardGameList.addBoardGame(boardGamesList.get(i));
      }
    }
    return newBoardGameList;
  }

  public BoardGamesList getBoardGameListByGenre(String genre)
  {
    BoardGamesList newBoardGameList = new BoardGamesList();
    for (int i = 0; i < size(); i++)
    {
      if (Objects.equals(boardGamesList.get(i).getType(), genre))
      {
        newBoardGameList.addBoardGame(boardGamesList.get(i));
      }
    }
    return newBoardGameList;
  }

  public BoardGamesList getBoardGameListByNumberOfPlayers(int numberOfPlayers)
  {
    BoardGamesList newBoardGameList = new BoardGamesList();
    for (int i = 0; i < size(); i++)
    {
      if (boardGamesList.get(i).getNumberOfPlayersMin() <= numberOfPlayers
          && numberOfPlayers <= boardGamesList.get(i).getNumberOfPlayersMax())
      {
        newBoardGameList.addBoardGame(boardGamesList.get(i));
      }
    }
    return newBoardGameList;
  }


  public BoardGame getBoardGame(int index)
  {
    return boardGamesList.get(index);
  }

  public BoardGamesList filterBoardGameList(String charSequence)
  {
    BoardGamesList newBoardGameList = new BoardGamesList();
    for (int i = 0; i < size(); i++)
    {
      if (boardGamesList.get(i).getName().contains(charSequence))
      {
        newBoardGameList.addBoardGame(boardGamesList.get(i));
      }
    }
    return newBoardGameList;
  }

  public String getNameByID(int ID)
  {
    for (int i = 0; i < boardGamesList.size(); i++)
    {
      if (boardGamesList.get(i).getID() == ID)
      {
        return boardGamesList.get(i).getName();
      }
    }
    return null;
  }

  public int size()
  {
    return boardGamesList.size();
  }

  public BoardGamesList getConsideredToBeBoughtGames()
  {  //Returns all games with the "Considered to be bought status" sorted by number of Votes
    BoardGamesList newBoardGamesList = new BoardGamesList();
    for (int i = 0; i < size(); i++)
    {

      if (boardGamesList.get(i).getAvailabilityStatus()
          .equals(BoardGame.CONSIDERED_TO_BE_BOUGHT_STATUS))
      {
        newBoardGamesList.addBoardGame(boardGamesList.get(i));
      }
    }

    ArrayList<BoardGame> arrayList = newBoardGamesList.getBoardGamesListAsArrayList();
    arrayList.sort(Comparator.comparing(BoardGame::getNumberOfVotes));
    return new BoardGamesList(arrayList);
  }

  public ArrayList<BoardGame> getBoardGamesListAsArrayList() //This is composition
  {
    ArrayList<BoardGame> newBoardGamesList = new ArrayList<>();

    for (BoardGame boardGame : boardGamesList)
    {
      newBoardGamesList.add(boardGame.copy());
    }
    return newBoardGamesList;
  }

  public BoardGame getBoardGameByID(int ID)
  {
    for (int i = 0; i < size(); i++)
    {
      if (getBoardGame(i).getID() == ID)
      {
        return getBoardGame(i);
      }
    }
    return new BoardGame("Board Game not Found","Error",0,0,BoardGame.UNAVAILABLE_STATUS,"",0,0);
  }

  public BoardGame getBoardGameWithMostVotes()
  {
    BoardGame max = boardGamesList.get(0);
    for (int i = 1; i < size(); i++)
    {
      if (boardGamesList.get(i).getNumberOfVotes() > max.getNumberOfVotes())
      {
        max = boardGamesList.get(i);
      }
    }
    return max;
  }

  public void setAllVotesTo0()
  {
    for (int i = 0; i < size(); i++)
    {
      boardGamesList.get(i).setNumberOfVotes(0);
    }

  }

  public BoardGamesList findByOwnership(int ID)
  {
    System.out.println(ID);
    BoardGamesList newBoardGamesList = new BoardGamesList();
    for (int i = 0; i < size(); i++)
    {
      if (getBoardGame(i).getOwnerID() == ID)
      {
        newBoardGamesList.addBoardGame(getBoardGame(i));
      }
    }
    return newBoardGamesList;
  }

  @Override public String toString()
  {
    return "Model.BoardGamesList{" + "boardGamesList=" + boardGamesList + '}';
  }
}
