package client;

import java.util.Objects;
import java.util.Scanner;

import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;
import client.Client;
import client.websocket.GameHandler;
import exception.ResponseException;
import ui.ChessBoardUI;

import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessGame.TeamColor.WHITE;
import static ui.EscapeSequences.*;

public class Repl {
    private final Client client;

    public Repl(String serverUrl) throws ResponseException {
        client = new Client(Objects.requireNonNullElse(serverUrl,"http://localhost:8081"));
    }

    public void run() {
        System.out.println("Welcome to Chess! Ready to play? I hope so. Type in a command!");
        System.out.print(client.help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            client.printBoard=false;
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = client.eval(line);
                System.out.print(SET_TEXT_COLOR_BLUE + result);
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }

        }
        System.out.println();
    }

    private void printPrompt() {
        System.out.print("\n" + RESET_TEXT_COLOR + ">>> " + SET_TEXT_COLOR_GREEN);
    }




}