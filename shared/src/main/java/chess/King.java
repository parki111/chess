package chess;

import java.util.HashSet;

public class King implements PieceMovesCalculator{
    public ChessBoard board;
    public ChessPosition position;
    public King(ChessBoard board,ChessPosition position){
        this.position=position;
        this.board=board;
    }
    @Override
    public HashSet<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        int row=position.getRow();
        int column=position.getColumn();
        HashSet <ChessMove> kingMoves=new HashSet();
        ChessPosition startPos=new ChessPosition(row,column);
        ChessPiece king = board.getPiece(startPos);
        kingMoves.addAll(king.chessmoveDirection(1, 1, startPos, board));
        kingMoves.addAll(king.chessmoveDirection(-1, 1, startPos, board));
        kingMoves.addAll(king.chessmoveDirection(1, -1, startPos, board));
        kingMoves.addAll(king.chessmoveDirection(-1, -1, startPos, board));
        kingMoves.addAll(king.chessmoveDirection(1, 0, startPos, board));
        kingMoves.addAll(king.chessmoveDirection(0, 1, startPos, board));
        kingMoves.addAll(king.chessmoveDirection(-1, 0, startPos, board));
        kingMoves.addAll(king.chessmoveDirection(0, -1, startPos, board));
        for (ChessMove move : kingMoves) {
            System.out.println("("+move.getEndPosition().getRow()+","+move.getEndPosition().getColumn()+")");
        }


        return kingMoves;
    }
}