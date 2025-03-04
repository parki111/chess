package dataaccess.memorydataaccess;

import Model.GameData;
import chess.ChessGame;
import dataaccess.GameDAO;
import exception.ResponseException;
import java.util.HashMap;
import java.util.Collection;

public class MemoryGamesData implements GameDAO {
    HashMap<Integer,GameData> games = new HashMap<>();

    public Boolean createGame(String gameName) throws ResponseException{
        GameData game = new GameData(games.size(),null,null,gameName,new ChessGame());
        games.put(game.gameID(), game);
        return Boolean.TRUE;
    }

    public Collection<GameData> listGames() throws ResponseException{
        return games.values();
    }

    public GameData findGame(int gameID) throws ResponseException{
        if (games.containsKey(gameID)) {
            return games.get(gameID);
        }
        throw new ResponseException(400,"Error: bad request");
    }

    public Boolean updateGame(String username, String playerColor, GameData gameData) throws ResponseException{
        GameData  new_game;
        if (playerColor=="BLACK"){
            if (gameData.blackUsername()!=null){
                throw new ResponseException(403,"Error: already taken");
            }
            new_game = new GameData(gameData.gameID(), gameData.whiteUsername(), username, gameData.gameName(), gameData.game());
        }
        else if (playerColor=="WHITE"){
            if (gameData.whiteUsername()!=null){
                throw new ResponseException(403,"Error: already taken");
            }
            new_game = new GameData(gameData.gameID(), username, gameData.blackUsername(), gameData.gameName(), gameData.game());
        }
        else{
            throw new ResponseException(400,"Error: bad request");
        }
        games.put(gameData.gameID(),new_game);
        return Boolean.TRUE;
    }


    public void clearGames() throws ResponseException{
        games.clear();
    }
}
