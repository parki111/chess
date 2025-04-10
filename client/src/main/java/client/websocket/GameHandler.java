package client.websocket;

import chess.ChessGame;
import chess.ChessPiece;

public interface GameHandler {
    void updateGame(ChessGame chessGame, ChessPiece validMoves);
    void printMessage(String message);
}
