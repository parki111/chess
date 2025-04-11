package client.websocket;

import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

public interface GameHandler {
    boolean observer=false;
    void updateGame(ChessGame chessGame, ChessPosition validMoves);
    void printMessage(String message);
    boolean isObserver();
}
