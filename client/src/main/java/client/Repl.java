package client;

import java.util.Scanner;
import client.Client;
import ui.ChessBoardUI;

import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessGame.TeamColor.WHITE;
import static ui.EscapeSequences.*;

public class Repl {
    private final Client client;

    public Repl(String serverUrl) {
        client = new Client(serverUrl);
    }

    public void run() {
        System.out.println("Welcome to Chess! Ready to play? I hope so. Type in a command!");
        System.out.print(client.help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = client.eval(line);
                System.out.print(SET_TEXT_COLOR_BLUE + result);
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
            printBoard(result);
        }
        System.out.println();
    }

    private void printPrompt() {
        System.out.print("\n" + RESET_TEXT_COLOR + ">>> " + SET_TEXT_COLOR_GREEN);
    }

    public void printBoard(String result){
        if (result.equalsIgnoreCase("observegame") || result.equalsIgnoreCase("playgame")){
            if (client.getJoinedColor()==WHITE){
                new ChessBoardUI().chessBoardWhite();
            }
            else{
                new ChessBoardUI().chessBoardBlack();
            }

        }
    }
}