import chess.*;
import exception.ResponseException;
import ui.ChessBoardUI;
import client.Repl;
import java.util.Vector;

import static ui.EscapeSequences.*;

public class Main {
    public static void main(String[] args) throws ResponseException {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);
        ChessGame chessGame = new ChessGame();
        chessGame.getBoard().addPiece(new ChessPosition(5,5),new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN));
        new ChessBoardUI(ChessGame.TeamColor.WHITE,chessGame,new ChessPosition(5,3)).chessBoardWhite();
        Repl repl = new Repl ("http://localhost:8080");
        repl.run();
    }
}