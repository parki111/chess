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
        HashSet <ChessMove> bishop_moves=new HashSet();
        ChessPosition start_pos=new ChessPosition(row,column);
        ChessPiece bishop = board.getPiece(start_pos);
        bishop_moves.addAll(bishop.chessmove_direction(1, 1, start_pos, board));
        bishop_moves.addAll(bishop.chessmove_direction(-1, 1, start_pos, board));
        bishop_moves.addAll(bishop.chessmove_direction(1, -1, start_pos, board));
        bishop_moves.addAll(bishop.chessmove_direction(-1, -1, start_pos, board));
        for (ChessMove move : bishop_moves) {
            System.out.println("("+move.getEndPosition().getRow()+","+move.getEndPosition().getColumn()+")");
        }


        return bishop_moves;
    }
}