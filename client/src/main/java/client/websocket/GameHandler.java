package client.websocket;

import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

public interface GameHandler {
    void updateGame(ChessGame chessGame, ChessPosition validMoves);
    void printMessage(String message);
}
