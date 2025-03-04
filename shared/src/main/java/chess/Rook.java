package chess;

import java.util.HashSet;

public class Rook implements PieceMovesCalculator{
    public ChessBoard board;
    public ChessPosition position;
    public Rook(ChessBoard board,ChessPosition position){
        this.position=position;
        this.board=board;
    }
    @Override
    public HashSet<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        int row=position.getRow();
        int column=position.getColumn();
        HashSet <ChessMove> rookMoves=new HashSet();
        ChessPosition startPos=new ChessPosition(row,column);
        ChessPiece rook = board.getPiece(startPos);
        rookMoves.addAll(rook.chessmoveDirection(1, 0, startPos, board));
        rookMoves.addAll(rook.chessmoveDirection(0, 1, startPos, board));
        rookMoves.addAll(rook.chessmoveDirection(-1, 0, startPos, board));
        rookMoves.addAll(rook.chessmoveDirection(0, -1, startPos, board));
        System.out.println(rookMoves);

        return rookMoves;
    }
}
