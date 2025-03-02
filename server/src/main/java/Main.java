import chess.*;
import server.*;

public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);

        System.out.println("♕ 240 Chess Server: " + piece);
        Server chess_server = new Server();
        chess_server.run(8080);
    }
}