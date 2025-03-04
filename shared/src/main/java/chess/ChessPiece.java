package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private ChessGame.TeamColor teamColor;
    private ChessPiece.PieceType pieceType;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.teamColor = pieceColor;
        this.pieceType = type;

    }


    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {

        return teamColor;
    }


    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return pieceType;
    }

    public String toString() {
        char color = teamColor.toString().charAt(0);
        char type = pieceType.toString().charAt(0);
        return ("" + color + type);
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        PieceType newPiece=board.getPiece(myPosition).pieceType;
        HashSet<ChessMove> hasher=new HashSet<>();
        if (newPiece == PieceType.ROOK)
        {
            Rook rook = new Rook(board,myPosition);
            hasher=rook.pieceMoves(board,myPosition);
        }
        if (newPiece == PieceType.BISHOP)
        {
            Bishop bishop = new Bishop(board,myPosition);
            hasher=bishop.pieceMoves(board,myPosition);
        }
        else if (newPiece == PieceType.QUEEN)
        {
            Queen queen = new Queen(board,myPosition);
            hasher=queen.pieceMoves(board,myPosition);
        }
        else if (newPiece == PieceType.KING)
        {
            King king = new King(board,myPosition);
            hasher=king.pieceMoves(board,myPosition);
        }
        else if (newPiece == PieceType.KNIGHT)
        {
            Knight knight = new Knight(board,myPosition);
            hasher=knight.pieceMoves(board,myPosition);
        }
        else if (newPiece == PieceType.PAWN)
        {
            Pawn pawn = new Pawn(board,myPosition);
            hasher=pawn.pieceMoves(board,myPosition);
        }
        return hasher;
    }

    public HashSet<ChessMove> chessmoveDirection(int yDir, int xDir, ChessPosition startPos, ChessBoard board) {         //Queen,bishop,rook
        int row = startPos.getRow();
        int column = startPos.getColumn();
        HashSet<ChessMove> piecemovesOneDirection = new HashSet();
        ChessPiece piece = board.getPiece(startPos);
        ChessGame.TeamColor pieceColor = piece.getTeamColor();
        int multiplier = 1;
        int i = multiplier * yDir + row;
        int j = multiplier * xDir + column;
        int pawnTwoSpaces=0;
        if ((row==7 && piece.getTeamColor() == ChessGame.TeamColor.BLACK) ||
                (row==2 && piece.getTeamColor() == ChessGame.TeamColor.WHITE)){pawnTwoSpaces=1;}
        boolean promotion = ((i==1 && piece.getTeamColor() == ChessGame.TeamColor.BLACK) ||
                (i==8 && piece.getTeamColor() == ChessGame.TeamColor.WHITE));
        while (1 <= i && i <= 8 && 1 <= j && j <= 8) {


            ChessPiece otherPiece = board.getPiece(new ChessPosition(i, j));

            if (otherPiece != null) {
                if (otherPiece.getTeamColor() == pieceColor) {
                    break;
                }
                if (piece.pieceType == PieceType.PAWN && xDir==0){
                    break;
                }
                else if (piece.pieceType ==PieceType.PAWN && promotion)
                {

                }
                else{

                    ChessMove move = new ChessMove(startPos, new ChessPosition(i, j), null);
                    piecemovesOneDirection.add(move);
                    break;
                }
            }
            else if (piece.pieceType == PieceType.PAWN && xDir!=0){
                break;
            }
            else
            {
                if (piece.pieceType!=PieceType.PAWN) {
                    ChessMove move = new ChessMove(startPos, new ChessPosition(i, j), null);
                    piecemovesOneDirection.add(move);
                    multiplier = multiplier + 1;
                    i = multiplier * yDir + row;
                    j = multiplier * xDir + column;
                }
            }
            if (piece.pieceType==PieceType.KING || piece.pieceType==PieceType.KNIGHT){
                break;
            }
            else if(piece.pieceType==PieceType.PAWN){
                ChessMove move = new ChessMove(startPos, new ChessPosition(i, j), null);
                ChessMove move1 = new ChessMove(startPos, new ChessPosition(i, j), PieceType.KNIGHT);
                ChessMove move2 = new ChessMove(startPos, new ChessPosition(i, j), PieceType.ROOK);
                ChessMove move3 = new ChessMove(startPos, new ChessPosition(i, j), PieceType.BISHOP);
                ChessMove move4 = new ChessMove(startPos, new ChessPosition(i, j), PieceType.QUEEN);
                if (promotion==true) {
                    piecemovesOneDirection.add(move1);
                    piecemovesOneDirection.add(move2);
                    piecemovesOneDirection.add(move3);
                    piecemovesOneDirection.add(move4);
                }
                else{
                        piecemovesOneDirection.add(move);
                    }
                if (pawnTwoSpaces==1){
                    multiplier+=1;
                    i = multiplier * yDir + row;
                    j = multiplier * xDir + column;
                    pawnTwoSpaces++;
                    continue;
                }
                break;

            }


        }
        return piecemovesOneDirection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) o;
        return teamColor == that.teamColor && pieceType == that.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamColor, pieceType);
    }
}