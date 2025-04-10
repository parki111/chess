package client.websocket;

import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;
import ui.ChessBoardUI;

import static chess.ChessGame.TeamColor.WHITE;

public class GameUI implements GameHandler {
    private WebsocketFacade wsFacade;

    public void updateGame(ChessGame chessGame, ChessPiece validMoves) {
            if (client.getJoinedColor()==WHITE){
                new ChessBoardUI(ChessGame.TeamColor.WHITE,new ChessGame(),new ChessPosition(2,2)).chessBoardWhite();
            }
            else{
                new ChessBoardUI(ChessGame.TeamColor.BLACK,new ChessGame(),new ChessPosition (2,2)).chessBoardBlack();
            }

        }
    }


    public void printMessage(String message) {

    }

}
