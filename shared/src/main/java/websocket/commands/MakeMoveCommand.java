package websocket.commands;

import chess.ChessMove;

public class MakeMoveCommand extends UserGameCommand{
    ChessMove chessMove;
    public MakeMoveCommand(ChessMove chessMove, String authToken, Integer gameID){
        super(CommandType.MAKE_MOVE, authToken, gameID);
        this.chessMove=chessMove;
    }

    public ChessMove getMove() {
        return chessMove;
    }
}
