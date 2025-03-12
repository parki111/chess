package dataaccess.sqldataaccess;

import chess.ChessGame;
import exception.ResponseException;
import model.GameData;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class SqlDataAccessGamesData {
    public int createGame(String gameName) throws ResponseException{return 1;};
    public Collection<GameData> listGames() throws ResponseException{return new HashSet<GameData>();};
    public GameData findGame(int gameID) throws ResponseException {return new GameData(5,"","","",new ChessGame());};
    public Boolean updateGame(String username, String playerColor, GameData gameData) throws ResponseException{return true;};
    //    Boolean deleteGame(String username, String playerColor, GameData gameData) throws ResponseException;
    public void clearGames() throws ResponseException{};
}
