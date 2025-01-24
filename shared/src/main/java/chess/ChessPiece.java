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
        PieceType new_piece=board.getPiece(myPosition).pieceType;
        HashSet<ChessMove> hasher=new HashSet<>();
        if (new_piece == PieceType.ROOK)
        {
            Rook rook = new Rook(board,myPosition);
            hasher=rook.pieceMoves(board,myPosition);
        }
        return hasher;
    }

    public HashSet<ChessMove> chessmove_direction(int x_dir, int y_dir, ChessPosition start_pos, ChessBoard board) {         //Queen,bishop,rook
        int row = start_pos.getRow();
        int column = start_pos.getColumn();
        HashSet<ChessMove> piecemoves_1direction = new HashSet();
        ChessPiece piece = board.getPiece(start_pos);
        ChessGame.TeamColor piece_color = piece.getTeamColor();
        int multiplier = 1;
        int i = multiplier * x_dir + row;
        int j = multiplier * y_dir + column;
        while (1 <= i && i <= 8 && 1 <= j && j <= 8) {

            ChessPiece other_piece = board.getPiece(new ChessPosition(i, j));
            if (other_piece != null) {
                if (other_piece.getTeamColor() == piece_color) {
                    break;
                } else {
                    ChessMove move = new ChessMove(start_pos, new ChessPosition(i, j), null);
                    piecemoves_1direction.add(move);
                    break;
                }
            }
            else
            {
                ChessMove move = new ChessMove(start_pos, new ChessPosition(i, j), null);
                piecemoves_1direction.add(move);
                multiplier=multiplier+1;
                i=multiplier * x_dir +row;
                j=multiplier * y_dir +column;
            }

        }
        return piecemoves_1direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
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