package dataaccess;

import Model.GameData;
import chess.ChessGame;
import exception.ResponseException;

import java.util.Collection;

public interface DataAccess_Games {
    Collection<ChessGame> listGames() throws ResponseException;
    GameData createGame(String gameName) throws ResponseException;
    GameData findGame(String username, int gameID) throws ResponseException;
    void updateGame(String username, String playerColor, GameData gameData) throws ResponseException;
}
