package dataaccess.memorydataaccess;

import model.GameData;
import chess.ChessGame;
import dataaccess.GameDAO;
import exception.ResponseException;
import java.util.HashMap;
import java.util.Collection;
import java.util.Objects;

public class MemoryGamesData implements GameDAO {
    HashMap<Integer,GameData> games = new HashMap<>();

    public int createGame(String gameName) throws ResponseException{
        GameData game = new GameData(games.size()+1,null,null,gameName,new ChessGame());
        games.put(game.gameID(), game);
        return game.gameID();
    }

    public Collection<GameData> listGames() throws ResponseException{
        return games.values();
        //        new Collection<>
//        for (GameData game:games.values()){
//
//        }
    }

    public GameData findGame(Integer gameID) throws ResponseException{
        if (games.containsKey(gameID)) {
            return games.get(gameID);
        }
        return null;
    }

    public Boolean updateGame(String username, String playerColor, GameData gameData) throws ResponseException{
        GameData  newGame;
        if (Objects.equals(playerColor, "BLACK")){
            if (!Objects.equals(gameData.blackUsername(), null)){
                throw new ResponseException(403,"Error: already taken");
            }
            newGame = new GameData(gameData.gameID(), gameData.whiteUsername(), username, gameData.gameName(), gameData.game());
        }
        else if (Objects.equals(playerColor, "WHITE")){
            if (!Objects.equals(gameData.whiteUsername(), null)){
                throw new ResponseException(403,"Error: already taken");
            }
            newGame = new GameData(gameData.gameID(), username, gameData.blackUsername(), gameData.gameName(), gameData.game());
        }
        else{
            throw new ResponseException(400,"Error: bad request");
        }
        games.put(gameData.gameID(),newGame);
        return Boolean.TRUE;
    }

    public void updateGameWebsocket(GameData gameData){}

    public void clearGames() throws ResponseException{
        games.clear();
    }
}
