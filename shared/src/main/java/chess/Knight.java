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
        HashSet <ChessMove> Knight_moves=new HashSet();
        ChessPosition start_pos=new ChessPosition(row,column);
        ChessPiece Knight = board.getPiece(start_pos);
        Knight_moves.addAll(Knight.chessmove_direction(2, -1, start_pos, board));
        Knight_moves.addAll(Knight.chessmove_direction(1, -2, start_pos, board));
        Knight_moves.addAll(Knight.chessmove_direction(-1, -2, start_pos, board));
        Knight_moves.addAll(Knight.chessmove_direction(-2, -1, start_pos, board));
        Knight_moves.addAll(Knight.chessmove_direction(-2, 1, start_pos, board));
        Knight_moves.addAll(Knight.chessmove_direction(-1, 2, start_pos, board));
        Knight_moves.addAll(Knight.chessmove_direction(1, 2, start_pos, board));
        Knight_moves.addAll(Knight.chessmove_direction(2, 1, start_pos, board));
        System.out.println(Knight_moves);

        return Knight_moves;
    }
}