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
        HashSet <ChessMove> queen_moves=new HashSet();
        ChessPosition start_pos=new ChessPosition(row,column);
        ChessPiece queen = board.getPiece(start_pos);
        queen_moves.addAll(queen.chessmove_direction(1, 1, start_pos, board));
        queen_moves.addAll(queen.chessmove_direction(-1, 1, start_pos, board));
        queen_moves.addAll(queen.chessmove_direction(1, -1, start_pos, board));
        queen_moves.addAll(queen.chessmove_direction(-1, -1, start_pos, board));
        queen_moves.addAll(queen.chessmove_direction(1, 0, start_pos, board));
        queen_moves.addAll(queen.chessmove_direction(0, 1, start_pos, board));
        queen_moves.addAll(queen.chessmove_direction(-1, 0, start_pos, board));
        queen_moves.addAll(queen.chessmove_direction(0, -1, start_pos, board));
        for (ChessMove move : queen_moves) {
            System.out.println("("+move.getEndPosition().getRow()+","+move.getEndPosition().getColumn()+")");
        }


        return queen_moves;
    }
}