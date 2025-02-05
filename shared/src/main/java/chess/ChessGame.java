package chess;

import java.util.Collection;
import java.util.HashSet;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    TeamColor whose_turn;
    ChessBoard chessBoard;
    public ChessGame() {
        whose_turn=TeamColor.WHITE;
        chessBoard = new ChessBoard();

    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return whose_turn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        whose_turn=team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece piece= chessBoard.getPiece(startPosition);
        Collection<ChessMove> hasher = piece.pieceMoves(chessBoard,startPosition);
        ChessBoard old_board = chessBoard;
        for (ChessMove move : hasher)
        {
            chessBoard.addPiece(move.getStartPosition(),null);
            chessBoard.addPiece(move.getEndPosition(),piece);
            if (isInCheck(piece.getTeamColor()))
            {
                hasher.remove(move);
            }
            chessBoard=old_board;
        }
        return hasher;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition pos = move.getStartPosition();
        ChessPosition end_pos = move.getEndPosition();
        if (this.validMoves(pos).contains(move))
        {
            this.chessBoard.addPiece(end_pos,chessBoard.getPiece(pos));
            this.chessBoard.addPiece(pos,null);
        }
        else{
            throw new InvalidMoveException("WRONG");
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition king_pos=new ChessPosition(1,1);
        row:
        for (int i=1;i<9;i++)
        {
            for (int j=1;j<9;j++)
            {
                if (chessBoard.getPiece(new ChessPosition(i,j)).getPieceType() == ChessPiece.PieceType.KING
                 && chessBoard.getPiece(new ChessPosition(i,j)).getTeamColor()== teamColor)
                {
                    king_pos = new ChessPosition(i,j);
                    break row;
                }
            }
        };
        for (int i=1;i<9;i++)
        {
            for (int j=1;j<9;j++)
            {
                if (chessBoard.getPiece(new ChessPosition(i,j)).getTeamColor()!=teamColor
                 && chessBoard.getPiece(new ChessPosition(i,j)).getTeamColor()!=null)
                {
                    ChessMove move = new ChessMove(new ChessPosition(i,j),king_pos,null);
                    ChessMove promotion = new ChessMove(new ChessPosition(i,j),king_pos, ChessPiece.PieceType.QUEEN);
                    if (chessBoard.getPiece(new ChessPosition(i,j)).pieceMoves(chessBoard,new ChessPosition(i,j)).contains(move)
                    ||  chessBoard.getPiece(new ChessPosition(i,j)).pieceMoves(chessBoard,new ChessPosition(i,j)).contains(promotion))
                    {
                        return true;
                    }
                }
            }
        }
        return false;

    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.chessBoard=board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return this.chessBoard;
    }
}
