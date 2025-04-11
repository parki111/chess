package chess;

import java.util.HashSet;

public class Knight implements PieceMovesCalculator{
    public ChessBoard board;
    public ChessPosition position;
    public Knight(ChessBoard board,ChessPosition position){
        this.position=position;
        this.board=board;
    }
    @Override
    public HashSet<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        int row=position.getRow();
        int column=position.getColumn();
        HashSet <ChessMove> knightMoves=new HashSet();
        ChessPosition startPos=new ChessPosition(row,column);
        ChessPiece knight = board.getPiece(startPos);
        knightMoves.addAll(knight.chessmoveDirection(2, -1, startPos, board));
        knightMoves.addAll(knight.chessmoveDirection(1, -2, startPos, board));
        knightMoves.addAll(knight.chessmoveDirection(-1, -2, startPos, board));
        knightMoves.addAll(knight.chessmoveDirection(-2, -1, startPos, board));
        knightMoves.addAll(knight.chessmoveDirection(-2, 1, startPos, board));
        knightMoves.addAll(knight.chessmoveDirection(-1, 2, startPos, board));
        knightMoves.addAll(knight.chessmoveDirection(1, 2, startPos, board));
        knightMoves.addAll(knight.chessmoveDirection(2, 1, startPos, board));
//        System.out.println(knightMoves);

        return knightMoves;
    }
}