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
    private Object[][] chessBoard;
    public ChessBoard() {
        chessBoard=new Object[8][8];
    }
    /**
     * Adds a chess piece to the chessboard
    // *
     * //@param //position where to add the piece to
     * //@param //piece    the piece to add
     **/
    public void initializeChessBoard() {
        chessBoard[0][0] = new ChessPiece(ChessGame.TeamColor.WHITE,ChessPiece.PieceType.ROOK);
        chessBoard[0][1] = new ChessPiece(ChessGame.TeamColor.WHITE,ChessPiece.PieceType.KNIGHT);
        chessBoard[0][2] = new ChessPiece(ChessGame.TeamColor.WHITE,ChessPiece.PieceType.BISHOP);
        chessBoard[0][3] = new ChessPiece(ChessGame.TeamColor.WHITE,ChessPiece.PieceType.QUEEN);
        chessBoard[0][4] = new ChessPiece(ChessGame.TeamColor.WHITE,ChessPiece.PieceType.KING);
        chessBoard[0][5] = new ChessPiece(ChessGame.TeamColor.WHITE,ChessPiece.PieceType.BISHOP);
        chessBoard[0][6] = new ChessPiece(ChessGame.TeamColor.WHITE,ChessPiece.PieceType.KNIGHT);
        chessBoard[0][7] = new ChessPiece(ChessGame.TeamColor.WHITE,ChessPiece.PieceType.ROOK);
        for (int i = 0; i < 8; i++) {
            chessBoard[1][i]= new ChessPiece(ChessGame.TeamColor.WHITE,ChessPiece.PieceType.PAWN);
        }
        chessBoard[7][0] = new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.ROOK);
        chessBoard[7][1] = new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.KNIGHT);
        chessBoard[7][2] = new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.BISHOP);
        chessBoard[7][3] = new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.QUEEN);
        chessBoard[7][4] = new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.KING);
        chessBoard[7][5] = new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.BISHOP);
        chessBoard[7][6] = new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.KNIGHT);
        chessBoard[7][7] = new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.ROOK);
        for (int i = 0; i < 8; i++) {
            chessBoard[6][i]= new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.PAWN);
        }
    }
    public void addPiece(ChessPosition position, ChessPiece piece) {
        chessBoard[position.getRow()-1][position.getColumn()-1] = piece;
        if (piece!=null){
            System.out.println(this);
            System.out.println("Added piece!");
        }

    }
    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        if (chessBoard[position.getRow()-1][position.getColumn()-1]==null){
            return null;
        }
        else{
            //System.out.println(this);
            return (ChessPiece) chessBoard[position.getRow()-1][position.getColumn()-1];
        }
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        chessBoard=new Object[8][8];
        initializeChessBoard();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
//            System.out.println("expected"+given.chessBoard[i][j]);
//                    System.out.println(chessBoard[i][j]);
            System.out.println(this);
            System.out.println(o);
            System.out.println("not true");
            return false;
        }
        ChessBoard given=(ChessBoard) o;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (given.chessBoard[i][j]==null && chessBoard[i][j]!=null){
                    return false;
                }
                else if(given.chessBoard[i][j]!=null && chessBoard[i][j]==null){
                    return false;
                }
                else if (given.chessBoard[i][j]==null && chessBoard[i][j]==null) {
                }
                else if (!given.chessBoard[i][j].toString().equals(chessBoard[i][j].toString())){
                    System.out.println("expected"+given.chessBoard[i][j]);
                    System.out.println(chessBoard[i][j]);
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
        return Arrays.deepHashCode(chessBoard);
    }

    @Override
    public String toString(){
        String board="\n";
        for (int i=0;i<8;i++){
            String line="";
            for (int j=0;j<8;j++){
                if (chessBoard[i][j]==null){
                    line=line+"|  |";
                }
                else{
                    line=line+"|"+chessBoard[i][j].toString()+"|";
                }

            }
            board=board+line+"\n";
        }
        return board;
    }
}