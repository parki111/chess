package chess;

import java.util.HashSet;

public class Bishop implements PieceMovesCalculator{
    public ChessBoard board;
    public ChessPosition position;
    public Bishop(ChessBoard board,ChessPosition position){
        this.position=position;
        this.board=board;
    }
    @Override
    public HashSet<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        int row=position.getRow();
        int column=position.getColumn();
        HashSet <ChessMove> bishopMoves=new HashSet();
        ChessPosition startPos=new ChessPosition(row,column);
        ChessPiece bishop = board.getPiece(startPos);
        bishopMoves.addAll(bishop.chessmoveDirection(1, 1, startPos, board));
        bishopMoves.addAll(bishop.chessmoveDirection(-1, 1, startPos, board));
        bishopMoves.addAll(bishop.chessmoveDirection(1, -1, startPos, board));
        bishopMoves.addAll(bishop.chessmoveDirection(-1, -1, startPos, board));
        for (ChessMove move : bishopMoves) {
            System.out.println("("+move.getEndPosition().getRow()+","+move.getEndPosition().getColumn()+")");
        }


        return bishopMoves;
    }
}