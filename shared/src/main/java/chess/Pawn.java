package chess;

import java.util.HashSet;

public class Pawn implements PieceMovesCalculator{
    public ChessBoard board;
    public ChessPosition position;
    public Pawn(ChessBoard board,ChessPosition position){
        this.position=position;
        this.board=board;
    }
    @Override
    public HashSet<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        int row=position.getRow();
        int column=position.getColumn();
        HashSet <ChessMove> pawn_moves=new HashSet();
        ChessPosition start_pos=new ChessPosition(row,column);
        ChessPiece pawn = board.getPiece(start_pos);
        pawn_moves.addAll(pawn.chessmove_direction(0, 1, start_pos, board));
        if (row<8 && column<8){
            if (board.getPiece(new ChessPosition(row+1,column+1))!=null){
                if (board.getPiece(new ChessPosition(row+1,column+1)).getTeamColor()!=pawn.getTeamColor()){
                    if (row==7){
                        pawn_moves.add(new ChessMove(start_pos,new ChessPosition(row+1,column+1), ChessPiece.PieceType.QUEEN));
                        pawn_moves.add(new ChessMove(start_pos,new ChessPosition(row+1,column+1), ChessPiece.PieceType.ROOK));
                        pawn_moves.add(new ChessMove(start_pos,new ChessPosition(row+1,column+1), ChessPiece.PieceType.KNIGHT));
                        pawn_moves.add(new ChessMove(start_pos,new ChessPosition(row+1,column+1), ChessPiece.PieceType.BISHOP));
                    }
                    else{
                        pawn_moves.add(new ChessMove(start_pos,new ChessPosition(row+1,column-1),null));
                    }
                }
            }
        }
        if (column>1 && row<8){
            if (board.getPiece(new ChessPosition(row+1,column-1))!=null){
                if (board.getPiece(new ChessPosition(row+1,column-1)).getTeamColor()!=pawn.getTeamColor()) {
                    if (row==7){
                        pawn_moves.add(new ChessMove(start_pos,new ChessPosition(row+1,column-1), ChessPiece.PieceType.QUEEN));
                        pawn_moves.add(new ChessMove(start_pos,new ChessPosition(row+1,column-1), ChessPiece.PieceType.ROOK));
                        pawn_moves.add(new ChessMove(start_pos,new ChessPosition(row+1,column-1), ChessPiece.PieceType.KNIGHT));
                        pawn_moves.add(new ChessMove(start_pos,new ChessPosition(row+1,column-1), ChessPiece.PieceType.BISHOP));
                    }
                    else{
                    pawn_moves.add(new ChessMove(start_pos,new ChessPosition(row+1,column-1),null));
                }}

            }
        }

        System.out.println(pawn_moves);

        return pawn_moves;
    }
}
