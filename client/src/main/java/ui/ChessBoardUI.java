package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static ui.EscapeSequences.*;
import static ui.EscapeSequences.RESET_BG_COLOR;

public class ChessBoardUI {
    private ChessGame.TeamColor boardColor;
    private ChessBoard chessBoard;
    private HashMap<ChessPiece.PieceType,String> pieceCharacters = null;

    public ChessBoardUI(ChessGame.TeamColor color, ChessBoard chessBoard){
        boardColor=color;
        this.chessBoard=chessBoard;
        pieceCharacters= new HashMap<>();
        constructHashmap();

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
        List<String> rows = new ArrayList<>(Arrays.asList("  ", " 8 ", " 7 "," 6 "," 5 "," 4 "," 3 "," 2 "," 1 ","  "));
        List<String> firstRow = new ArrayList<>(Arrays.asList(" a ", " b "," c "," d "," e "," f "," g "," h "));
        String piece;
        for (int i=0;i<10;i++){
            piece = SET_BG_COLOR_DARK_GREY+SET_TEXT_COLOR_BLACK+rows.get(i);
            orderedList.add(piece);
            if (i==0 || i==9){
                for (int j=0;j<8;j++){
                    piece = SET_TEXT_COLOR_BLACK+firstRow.get(j);
                    orderedList.add(piece);
                }
            }
            for (int j=0;j<8;j++){
                String colorBG;
                if (j%2==0){
                    colorBG=SET_BG_COLOR_WHITE;
                }
                else{
                    colorBG=SET_BG_COLOR_BLACK;
                }

                if (chessBoard.getPiece(new ChessPosition(i+1,j+1)).getTeamColor()== ChessGame.TeamColor.WHITE){
                    piece = colorBG+SET_TEXT_COLOR_LIGHT_GREY;
                }
                else{
                    piece = colorBG+SET_TEXT_COLOR_DARK_GREY;
                }
                piece=piece+pieceCharacters.get(chessBoard.getPiece(new ChessPosition(i,j)).getPieceType());
                orderedList.add(piece);
            }
            piece = SET_BG_COLOR_DARK_GREY+SET_TEXT_COLOR_BLACK+rows.get(i)+RESET_BG_COLOR+"\n";
            orderedList.add(piece);
        }
        return orderedList;
    }


    public void ChessBoardWhite(){
        List<String> whiteBoard=constructStringBoard();
        for (String i:whiteBoard){
            System.out.print(i);
        }
    }

//    public void chessBoardWhite(){
//        System.out.print(SET_TEXT_BOLD+"\n"+SET_BG_COLOR_LIGHT_GREY + SET_TEXT_COLOR_BLACK + "    a  b  c  d  e  f  g  h    ");
//        System.out.print(RESET_BG_COLOR+"\n"+SET_BG_COLOR_LIGHT_GREY+" 8 "+ SET_TEXT_COLOR_RED);
//        String[] array = new String[]{" ♜ ", " ♞ ", " ♝ ", " ♛ ", " ♚ ", " ♝ ", " ♞ ", " ♜ "};
//        for (int i=0; i<array.length;i++){
//            if (i%2==0){
//                System.out.print(SET_BG_COLOR_WHITE + array[i]);
//            }
//            else {
//                System.out.print(SET_BG_COLOR_BLACK + array[i]);
//            }
//        }
//        System.out.print(SET_BG_COLOR_LIGHT_GREY+SET_TEXT_COLOR_BLACK+" 8 "+RESET_BG_COLOR);
//        System.out.print("\n"+SET_BG_COLOR_LIGHT_GREY+SET_TEXT_COLOR_BLACK+" 7 "+SET_TEXT_COLOR_RED);
//        for (int i=0; i<8;i++){
//            if (i%2==0){
//                System.out.print(SET_BG_COLOR_BLACK + " ♟ ");
//            }
//            else {
//                System.out.print( SET_BG_COLOR_WHITE+ " ♟ ");
//            }
//        }
//        System.out.print(SET_BG_COLOR_LIGHT_GREY+SET_TEXT_COLOR_BLACK+" 7 "+RESET_BG_COLOR+"\n");
//        for (int j=0; j<4;j++){
//            System.out.print(SET_BG_COLOR_LIGHT_GREY+SET_TEXT_COLOR_BLACK+" "+(6-j)+" "+RESET_BG_COLOR);
//            for (int i=j%2; i<8+j%2;i++){
//                if (i%2==0){
//                    System.out.print(SET_BG_COLOR_WHITE + "   ");
//                }
//                else {
//                    System.out.print(SET_BG_COLOR_BLACK + "   ");
//                }
//            }
//            System.out.print(SET_BG_COLOR_LIGHT_GREY+SET_TEXT_COLOR_BLACK+" "+(6-j)+" "+RESET_BG_COLOR+"\n");
//        }
//
//
//        System.out.print(SET_BG_COLOR_LIGHT_GREY+SET_TEXT_COLOR_BLACK+" 2 "+SET_TEXT_COLOR_BLUE);
//        for (int i=0; i<8;i++){
//            if (i%2==1){
//                System.out.print(SET_BG_COLOR_BLACK + " ♟ ");
//            }
//            else {
//                System.out.print( SET_BG_COLOR_WHITE+ " ♟ ");
//            }
//        }
//        System.out.print(SET_BG_COLOR_LIGHT_GREY+SET_TEXT_COLOR_BLACK+" 2 "+RESET_BG_COLOR+"\n");
//        System.out.print(RESET_BG_COLOR+SET_BG_COLOR_LIGHT_GREY+" 1 "+ SET_TEXT_COLOR_BLUE);
//        String[] array1 = new String[]{" ♜ ", " ♞ ", " ♝ ", " ♛ ", " ♚ ", " ♝ ", " ♞ ", " ♜ "};
//        for (int i=0; i<array.length;i++){
//            if (i%2==1){
//                System.out.print(SET_BG_COLOR_WHITE + array[i]);
//            }
//            else {
//                System.out.print(SET_BG_COLOR_BLACK + array[i]);
//            }
//        }
//        System.out.print(SET_BG_COLOR_LIGHT_GREY+SET_TEXT_COLOR_BLACK+" 1 "+RESET_BG_COLOR);
//        System.out.print("\n"+SET_BG_COLOR_LIGHT_GREY + SET_TEXT_COLOR_BLACK + "    a  b  c  d  e  f  g  h    "+RESET_BG_COLOR+"\n");
//    }


    public void chessBoardBlack(){
        System.out.print("\n"+SET_BG_COLOR_LIGHT_GREY + SET_TEXT_COLOR_BLACK + "    h  g  f  e  d  c  b  a    "+RESET_BG_COLOR);

        System.out.print("\n" + RESET_BG_COLOR+SET_BG_COLOR_LIGHT_GREY+" 1 "+ SET_TEXT_COLOR_BLUE);
        String[] array2 = new String[]{" ♜ ", " ♞ ", " ♝ ", " ♚ ", " ♛ ", " ♝ ", " ♞ ", " ♜ "};
        for (int i=0; i<array2.length;i++){
            if (i%2==0){
                System.out.print(SET_BG_COLOR_WHITE + array2[i]);
            }
            else {
                System.out.print(SET_BG_COLOR_BLACK + array2[i]);
            }
        }
        System.out.print(SET_BG_COLOR_LIGHT_GREY+SET_TEXT_COLOR_BLACK+" 1 "+RESET_BG_COLOR+"\n");
        System.out.print(SET_BG_COLOR_LIGHT_GREY+SET_TEXT_COLOR_BLACK+" 2 "+RESET_BG_COLOR+SET_TEXT_COLOR_BLUE);
        for (int i=0; i<8;i++){
            if (i%2==0){
                System.out.print(SET_BG_COLOR_BLACK + " ♟ ");
            }
            else {
                System.out.print( SET_BG_COLOR_WHITE+ " ♟ ");
            }
        }
        System.out.print(SET_BG_COLOR_LIGHT_GREY+SET_TEXT_COLOR_BLACK+" 2 "+RESET_BG_COLOR+"\n");
        for (int j=0; j<4;j++){
            System.out.print(SET_BG_COLOR_LIGHT_GREY+SET_TEXT_COLOR_BLACK+" "+(j+3)+" "+RESET_BG_COLOR);
            for (int i=j%2; i<8+j%2;i++){
                if (i%2==0){
                    System.out.print(SET_BG_COLOR_WHITE + "   ");
                }
                else {
                    System.out.print(SET_BG_COLOR_BLACK + "   ");
                }
            }
            System.out.print(SET_BG_COLOR_LIGHT_GREY+SET_TEXT_COLOR_BLACK+" "+(j+3)+" "+RESET_BG_COLOR+"\n");
        }
        System.out.print(SET_BG_COLOR_LIGHT_GREY+SET_TEXT_COLOR_BLACK+" 7 "+SET_TEXT_COLOR_RED);
        for (int i=0; i<8;i++){
            if (i%2==1){
                System.out.print(SET_BG_COLOR_BLACK + " ♟ ");
            }
            else {
                System.out.print( SET_BG_COLOR_WHITE+ " ♟ ");
            }
        }
        System.out.print(SET_BG_COLOR_LIGHT_GREY+SET_TEXT_COLOR_BLACK+" 7 "+RESET_BG_COLOR+"\n");
        System.out.print(SET_BG_COLOR_LIGHT_GREY+" 8 "+SET_TEXT_COLOR_RED);
        for (int i=0; i<array2.length;i++){
            if (i%2==1){
                System.out.print(SET_BG_COLOR_WHITE + array2[i]);
            }
            else {
                System.out.print(SET_BG_COLOR_BLACK + array2[i]);
            }
        }
        System.out.print(SET_BG_COLOR_LIGHT_GREY+SET_TEXT_COLOR_BLACK+" 8 "+RESET_BG_COLOR+"\n");
        System.out.print(SET_BG_COLOR_LIGHT_GREY + SET_TEXT_COLOR_BLACK + "    h  g  f  e  d  c  b  a    "+RESET_BG_COLOR+"\n");
    }
}
