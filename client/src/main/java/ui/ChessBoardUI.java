package ui;

import static ui.EscapeSequences.*;
import static ui.EscapeSequences.RESET_BG_COLOR;

public class ChessBoardUI {
    public void chessBoardWhite(){
        System.out.print(SET_TEXT_BOLD+"\n"+SET_BG_COLOR_LIGHT_GREY + SET_TEXT_COLOR_BLACK + "    a  b  c  d  e  f  g  h    ");
        System.out.print(RESET_BG_COLOR+"\n"+SET_BG_COLOR_LIGHT_GREY+" 8 "+ SET_TEXT_COLOR_BLUE);
        String[] array = new String[]{" ♜ ", " ♞ ", " ♝ ", " ♛ ", " ♚ ", " ♝ ", " ♞ ", " ♜ "};
        for (int i=0; i<array.length;i++){
            if (i%2==0){
                System.out.print(SET_BG_COLOR_WHITE + array[i]);
            }
            else {
                System.out.print(SET_BG_COLOR_BLACK + array[i]);
            }
        }
        System.out.print(SET_BG_COLOR_LIGHT_GREY+SET_TEXT_COLOR_BLACK+" 8 "+RESET_BG_COLOR);
        System.out.print("\n"+SET_BG_COLOR_LIGHT_GREY+SET_TEXT_COLOR_BLACK+" 7 "+SET_TEXT_COLOR_BLUE);
        for (int i=0; i<8;i++){
            if (i%2==0){
                System.out.print(SET_BG_COLOR_BLACK + " ♟ ");
            }
            else {
                System.out.print( SET_BG_COLOR_WHITE+ " ♟ ");
            }
        }
        System.out.print(SET_BG_COLOR_LIGHT_GREY+SET_TEXT_COLOR_BLACK+" 7 "+RESET_BG_COLOR+"\n");
        for (int j=0; j<4;j++){
            System.out.print(SET_BG_COLOR_LIGHT_GREY+SET_TEXT_COLOR_BLACK+" "+(6-j)+" "+RESET_BG_COLOR);
            for (int i=j%2; i<8+j%2;i++){
                if (i%2==0){
                    System.out.print(SET_BG_COLOR_WHITE + "   ");
                }
                else {
                    System.out.print(SET_BG_COLOR_BLACK + "   ");
                }
            }
            System.out.print(SET_BG_COLOR_LIGHT_GREY+SET_TEXT_COLOR_BLACK+" "+(6-j)+" "+RESET_BG_COLOR+"\n");
        }


        System.out.print(SET_BG_COLOR_LIGHT_GREY+SET_TEXT_COLOR_BLACK+" 2 "+SET_TEXT_COLOR_RED);
        for (int i=0; i<8;i++){
            if (i%2==1){
                System.out.print(SET_BG_COLOR_BLACK + " ♟ ");
            }
            else {
                System.out.print( SET_BG_COLOR_WHITE+ " ♟ ");
            }
        }
        System.out.print(SET_BG_COLOR_LIGHT_GREY+SET_TEXT_COLOR_BLACK+" 2 "+RESET_BG_COLOR+"\n");
        System.out.print(RESET_BG_COLOR+SET_BG_COLOR_LIGHT_GREY+" 1 "+ SET_TEXT_COLOR_RED);
        String[] array1 = new String[]{" ♜ ", " ♞ ", " ♝ ", " ♛ ", " ♚ ", " ♝ ", " ♞ ", " ♜ "};
        for (int i=0; i<array.length;i++){
            if (i%2==1){
                System.out.print(SET_BG_COLOR_WHITE + array[i]);
            }
            else {
                System.out.print(SET_BG_COLOR_BLACK + array[i]);
            }
        }
        System.out.print(SET_BG_COLOR_LIGHT_GREY+SET_TEXT_COLOR_BLACK+" 1 "+RESET_BG_COLOR);
        System.out.print("\n"+SET_BG_COLOR_LIGHT_GREY + SET_TEXT_COLOR_BLACK + "    a  b  c  d  e  f  g  h    "+RESET_BG_COLOR+"\n");
    }


    public void chessBoardBlack(){
        System.out.print("\n"+SET_BG_COLOR_LIGHT_GREY + SET_TEXT_COLOR_BLACK + "    h  g  f  e  d  c  b  a    "+RESET_BG_COLOR);

        System.out.print("\n" + RESET_BG_COLOR+SET_BG_COLOR_LIGHT_GREY+" 1 "+ SET_TEXT_COLOR_RED);
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
        System.out.print(SET_BG_COLOR_LIGHT_GREY+SET_TEXT_COLOR_BLACK+" 2 "+RESET_BG_COLOR+SET_TEXT_COLOR_RED);
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
        System.out.print(SET_BG_COLOR_LIGHT_GREY+SET_TEXT_COLOR_BLACK+" 7 "+SET_TEXT_COLOR_BLUE);
        for (int i=0; i<8;i++){
            if (i%2==1){
                System.out.print(SET_BG_COLOR_BLACK + " ♟ ");
            }
            else {
                System.out.print( SET_BG_COLOR_WHITE+ " ♟ ");
            }
        }
        System.out.print(SET_BG_COLOR_LIGHT_GREY+SET_TEXT_COLOR_BLACK+" 7 "+RESET_BG_COLOR+"\n");
        System.out.print(SET_BG_COLOR_LIGHT_GREY+" 8 "+SET_TEXT_COLOR_BLUE);
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
