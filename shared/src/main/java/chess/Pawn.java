package chess;

import java.util.HashSet;

public class Pawn implements PieceMovesCalculator{
    public ChessBoard board;
    public ChessPosition position;
    public Pawn(ChessBoard board,ChessPosition position){
        this.position=position;
        this.board=board;
    }
    @Override
    public HashSet<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        int row=position.getRow();
        int column=position.getColumn();
        HashSet <ChessMove> pawnMoves=new HashSet();
        ChessPosition startPos=new ChessPosition(row,column);
        ChessPiece pawn = board.getPiece(startPos);
        if (pawn.getTeamColor()== ChessGame.TeamColor.BLACK)
        {
            pawnMoves.addAll(pawn.chessmoveDirection(-1, 0, startPos, board));
            pawnMoves.addAll(pawn.chessmoveDirection(-1, 1, startPos, board));
            pawnMoves.addAll(pawn.chessmoveDirection(-1, -1, startPos, board));
        }
        else{
            pawnMoves.addAll(pawn.chessmoveDirection(1, 0, startPos, board));
            pawnMoves.addAll(pawn.chessmoveDirection(1, 1, startPos, board));
            pawnMoves.addAll(pawn.chessmoveDirection(1, -1, startPos, board));
        }
        System.out.println(pawnMoves);
        return pawnMoves;
    }
}
