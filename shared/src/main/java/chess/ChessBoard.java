package chess;

import java.util.Arrays;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private Object[][] chess_board;
    public ChessBoard() {
        chess_board=new Object[8][8];
    }

    /**
     * Adds a chess piece to the chessboard
    // *
     * //@param //position where to add the piece to
     * //@param //piece    the piece to add
     **/
    public void initializeChessBoard() {
        chess_board[0][0] = new ChessPiece(ChessGame.TeamColor.WHITE,ChessPiece.PieceType.ROOK);
        chess_board[0][1] = new ChessPiece(ChessGame.TeamColor.WHITE,ChessPiece.PieceType.KNIGHT);
        chess_board[0][2] = new ChessPiece(ChessGame.TeamColor.WHITE,ChessPiece.PieceType.BISHOP);
        chess_board[0][3] = new ChessPiece(ChessGame.TeamColor.WHITE,ChessPiece.PieceType.QUEEN);
        chess_board[0][4] = new ChessPiece(ChessGame.TeamColor.WHITE,ChessPiece.PieceType.KING);
        chess_board[0][5] = new ChessPiece(ChessGame.TeamColor.WHITE,ChessPiece.PieceType.BISHOP);
        chess_board[0][6] = new ChessPiece(ChessGame.TeamColor.WHITE,ChessPiece.PieceType.KNIGHT);
        chess_board[0][7] = new ChessPiece(ChessGame.TeamColor.WHITE,ChessPiece.PieceType.ROOK);
        for (int i = 0; i < 8; i++) {
            chess_board[1][i]= new ChessPiece(ChessGame.TeamColor.WHITE,ChessPiece.PieceType.PAWN);
        }
        chess_board[7][0] = new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.ROOK);
        chess_board[7][1] = new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.KNIGHT);
        chess_board[7][2] = new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.BISHOP);
        chess_board[7][3] = new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.QUEEN);
        chess_board[7][4] = new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.KING);
        chess_board[7][5] = new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.BISHOP);
        chess_board[7][6] = new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.KNIGHT);
        chess_board[7][7] = new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.ROOK);
        for (int i = 0; i < 8; i++) {
            chess_board[6][i]= new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.PAWN);
        }
    }
    public void addPiece(ChessPosition position, ChessPiece piece) {
        chess_board[position.getRow()-1][position.getColumn()-1] = piece;
    }
    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        if (chess_board[position.getRow()-1][position.getColumn()-1]==null){
            return(ChessPiece) null;
        }
        else{
            return (ChessPiece) chess_board[position.getRow()-1][position.getColumn()-1];
        }
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        chess_board=new Object[8][8];
        initializeChessBoard();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
//            System.out.println("expected"+given.chess_board[i][j]);
//                    System.out.println(chess_board[i][j]);
            System.out.println("not true");
            return false;
        }
//        ChessBoard that = (ChessBoard) o;
//        System.out.println(that);
//        System.out.println(this);
//        return Objects.deepEquals(that, this);
        ChessBoard given=(ChessBoard) o;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (given.chess_board[i][j]==null && chess_board[i][j]!=null){
                    return false;
                }
                else if(given.chess_board[i][j]!=null && chess_board[i][j]==null){
                    return false;
                }
                else if (given.chess_board[i][j]==null && chess_board[i][j]==null) {
                }
                else if (!given.chess_board[i][j].toString().equals(chess_board[i][j].toString())){
                    System.out.println("expected"+given.chess_board[i][j]);
                    System.out.println(chess_board[i][j]);
                    System.out.println("false");
                    return false;
                }

                }
            }

        System.out.println("true");
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(chess_board);
    }

    @Override
    public String toString(){
        String board="\n";
        for (int i=0;i<8;i++){
            String line="";
            for (int j=0;j<8;j++){
                if (chess_board[i][j]==null){
                    line=line+"|  |";
                }
                else{
                    line=line+"|"+chess_board[i][j].toString()+"|";
                }

            }
            board=board+line+"\n";
        }
        return board;
    }
}