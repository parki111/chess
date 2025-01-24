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
        HashSet <ChessMove> king_moves=new HashSet();
        ChessPosition start_pos=new ChessPosition(row,column);
        ChessPiece king = board.getPiece(start_pos);
        king_moves.addAll(king.chessmove_direction(1, 1, start_pos, board));
        king_moves.addAll(king.chessmove_direction(-1, 1, start_pos, board));
        king_moves.addAll(king.chessmove_direction(1, -1, start_pos, board));
        king_moves.addAll(king.chessmove_direction(-1, -1, start_pos, board));
        king_moves.addAll(king.chessmove_direction(1, 0, start_pos, board));
        king_moves.addAll(king.chessmove_direction(0, 1, start_pos, board));
        king_moves.addAll(king.chessmove_direction(-1, 0, start_pos, board));
        king_moves.addAll(king.chessmove_direction(0, -1, start_pos, board));
        for (ChessMove move : king_moves) {
            System.out.println("("+move.getEndPosition().getRow()+","+move.getEndPosition().getColumn()+")");
        }


        return king_moves;
    }
}