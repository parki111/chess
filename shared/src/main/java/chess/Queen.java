package chess;

import java.util.HashSet;

public class Queen implements PieceMovesCalculator{
    public ChessBoard board;
    public ChessPosition position;
    public Queen(ChessBoard board,ChessPosition position){
        this.position=position;
        this.board=board;
    }
    @Override
    public HashSet<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        int row=position.getRow();
        int column=position.getColumn();
        HashSet <ChessMove> queenMoves=new HashSet();
        ChessPosition startPos=new ChessPosition(row,column);
        ChessPiece queen = board.getPiece(startPos);
        queenMoves.addAll(queen.chessmoveDirection(1, 1, startPos, board));
        queenMoves.addAll(queen.chessmoveDirection(-1, 1, startPos, board));
        queenMoves.addAll(queen.chessmoveDirection(1, -1, startPos, board));
        queenMoves.addAll(queen.chessmoveDirection(-1, -1, startPos, board));
        queenMoves.addAll(queen.chessmoveDirection(1, 0, startPos, board));
        queenMoves.addAll(queen.chessmoveDirection(0, 1, startPos, board));
        queenMoves.addAll(queen.chessmoveDirection(-1, 0, startPos, board));
        queenMoves.addAll(queen.chessmoveDirection(0, -1, startPos, board));
//        for (ChessMove move : queenMoves) {
//            System.out.println("("+move.getEndPosition().getRow()+","+move.getEndPosition().getColumn()+")");



        return queenMoves;
    }
}