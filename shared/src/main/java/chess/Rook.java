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
        HashSet <ChessMove> rook_moves=new HashSet();
        ChessPosition start_pos=new ChessPosition(row,column);
        ChessPiece rook = board.getPiece(start_pos);
        rook_moves.addAll(rook.chessmove_direction(1, 0, start_pos, board));
        rook_moves.addAll(rook.chessmove_direction(0, 1, start_pos, board));
        rook_moves.addAll(rook.chessmove_direction(-1, 0, start_pos, board));
        rook_moves.addAll(rook.chessmove_direction(0, -1, start_pos, board));
        System.out.println(rook_moves);

        return rook_moves;
    }
}
//
//        ChessGame.TeamColor rook_color=rook.getTeamColor();
//
//        for (int i=column+1; i<9; i++) {
//            ChessPiece piece=board.getPiece(new ChessPosition(row,i));
//            if (piece!=null)
//            {
//                if (piece.getTeamColor()==rook_color)
//                {
//                    break;
//                }
//
//                else
//                {
//                    ChessMove move = new ChessMove(start_pos,new ChessPosition(row,i),null);
//                    rook_moves.add(move);
//                    break;
//                }
//            }
//
//            ChessPosition end_pos = new ChessPosition(row,i);
//            ChessMove move = new ChessMove(start_pos,end_pos,null);
//            rook_moves.add(move);
//        }
//
//        return null;

