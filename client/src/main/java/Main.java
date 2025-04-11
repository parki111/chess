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
//        ChessGame chessGame = new ChessGame();
//        chessGame.getBoard().addPiece(new ChessPosition(6,4),new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN));
//        chessGame.getBoard().addPiece(new ChessPosition(4,4),new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT));
//        new ChessBoardUI(ChessGame.TeamColor.WHITE,chessGame,new ChessPosition(4,4)).chessBoardBlack();
        Repl repl = new Repl (null);
        repl.run();
    }
}