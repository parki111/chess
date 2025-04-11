package ui;

import chess.*;

import java.util.*;

import static ui.EscapeSequences.*;
import static ui.EscapeSequences.RESET_BG_COLOR;

public class ChessBoardUI {
    private ChessGame.TeamColor boardColor;
    private ChessBoard chessBoard;
    private ChessGame chessGame;
    private HashMap<ChessPiece.PieceType,String> pieceCharacters = null;
    private ChessPosition validMovesPos;
    private HashSet<ChessPosition> validMoves;

    public ChessBoardUI(ChessGame.TeamColor color, ChessGame chessGame,ChessPosition validMovesPosition){
        boardColor=(color==ChessGame.TeamColor.WHITE)? ChessGame.TeamColor.BLACK : ChessGame.TeamColor.WHITE;

        validMovesPos=validMovesPosition;
        this.chessGame = chessGame;
        chessBoard=chessGame.getBoard();
        pieceCharacters= new HashMap<>();
        validMoves=new HashSet<>();
        constructHashmap();
        constructValidMoves();

    }

    private void constructValidMoves(){
        for (ChessMove i:chessGame.validMoves(validMovesPos)){
            validMoves.add(i.getEndPosition());
        }
    }

    private void constructHashmap(){
        pieceCharacters.put(ChessPiece.PieceType.KING," ♚ ");
        pieceCharacters.put(ChessPiece.PieceType.BISHOP," ♝ ");
        pieceCharacters.put(ChessPiece.PieceType.KNIGHT," ♞ ");
        pieceCharacters.put(ChessPiece.PieceType.QUEEN," ♛ ");
        pieceCharacters.put(ChessPiece.PieceType.ROOK," ♜ ");
        pieceCharacters.put(ChessPiece.PieceType.PAWN," ♟ ");
        pieceCharacters.put(null," ");
    }

    public List<String> constructStringBoard(){
        List<String> orderedList = new ArrayList<>();
        List<String> rows = new ArrayList<>(Arrays.asList("   ", " 8 ", " 7 "," 6 "," 5 "," 4 "," 3 "," 2 "," 1 ","   "));
        List<String> firstRow = new ArrayList<>(Arrays.asList(" a ", " b "," c "," d "," e "," f "," g "," h "));
        String piece;
        for (int i=0;i<10;i++){
            piece = SET_TEXT_COLOR_BLACK+rows.get(i);
            orderedList.add(piece);
            if (i==0 || i==9){
                for (int j=0;j<8;j++){
                    piece = SET_TEXT_COLOR_BLACK+firstRow.get(j);
                    orderedList.add(piece);
                }
            }else{
                for (int j=0;j<8;j++){
                    String colorBG;
                    if ((j+i+1)%2==0){

//                        colorBG=
                        colorBG= (validMovesPos!=null && validMoves.contains(new ChessPosition(i,j+1)))?
                                SET_BG_COLOR_GREEN:SET_BG_COLOR_WHITE;

                    }
                    else{
                        colorBG= (validMovesPos!=null && validMoves.contains(new ChessPosition(i,j+1)))?
                                SET_BG_COLOR_DARK_GREEN:SET_BG_COLOR_BLACK;

                    }
                    if (validMovesPos!=null && validMovesPos.equals(new ChessPosition(i, j + 1))){
                        colorBG=SET_BG_COLOR_YELLOW;
                    }
                    if(chessBoard.getPiece(new ChessPosition(i,j+1))!=null){
                        piece = (chessBoard.getPiece(new ChessPosition(i,j+1)).getTeamColor()== ChessGame.TeamColor.WHITE)?
                                colorBG+SET_TEXT_COLOR_LIGHT_GREY:colorBG+SET_TEXT_COLOR_MAGENTA;

                        piece=piece+pieceCharacters.get(chessBoard.getPiece(new ChessPosition(i,j+1)).getPieceType());
                    }
                    else{
                        piece=colorBG+"   ";
                    }
                    orderedList.add(piece);
                }
            }
            piece = SET_TEXT_COLOR_BLACK+rows.get(i);
            orderedList.add(piece);

        }
        return orderedList;
    }

    public void chessBoardBlack(){
        List<String> whiteBoard=constructStringBoard();

        for (int i = 0; i < 10; i++) {

            rotateBoard(whiteBoard, i);

        }
        whiteBoard.set(0,"\n"+whiteBoard.get(0));
        for (String i:whiteBoard){
            System.out.print(i);
        }
    }

    public void chessBoardWhite(){
        List<String> whiteBoard = constructStringBoard();
        Collections.reverse(whiteBoard);
        for (int i = 0; i < 10; i++) {
            rotateBoard(whiteBoard, i);

        }
        whiteBoard.set(0, "\n" + whiteBoard.get(0));
            for (String i : whiteBoard) {
                System.out.print(i);
            }

    }

    private void rotateBoard(List<String> whiteBoard, int i) {
        if (i != 9) {
            whiteBoard.set(10 * i, SET_BG_COLOR_DARK_GREY + whiteBoard.get(10 * i));
            whiteBoard.set(i, SET_BG_COLOR_DARK_GREY + whiteBoard.get(i));
            whiteBoard.set(90 + i, SET_BG_COLOR_DARK_GREY + whiteBoard.get(90 + i));
        }
        whiteBoard.set(9 + 10 * i, SET_BG_COLOR_DARK_GREY + whiteBoard.get(9 + 10 * i) + RESET_BG_COLOR + "\n");
    }


}
