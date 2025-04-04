import chess.*;
import ui.ChessBoardUI;
import client.Repl;
import java.util.Vector;

import static ui.EscapeSequences.*;

public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);
        Repl repl = new Repl ("http://localhost:8080");
        repl.run();
    }
}