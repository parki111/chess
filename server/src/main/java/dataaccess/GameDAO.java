package dataaccess;

import model.GameData;
import exception.ResponseException;

import java.util.Collection;

public interface GameDAO {
     int createGame(String gameName) throws ResponseException;
    Collection<GameData> listGames() throws ResponseException;
    GameData findGame(Integer gameID) throws ResponseException;
    Boolean updateGame(String username, String playerColor, GameData gameData) throws ResponseException;
    void updateGameWebsocket(GameData gameData) throws ResponseException;
//    Boolean deleteGame(String username, String playerColor, GameData gameData) throws ResponseException;
    void clearGames() throws ResponseException;
}
