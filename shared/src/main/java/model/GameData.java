package model;
import chess.ChessGame;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public record GameData(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game) {
    @Override
    public String toString(){
//        Map<String, Object> gameData = new HashMap<>();
//        gameData.put("gameID", this.gameID);
//        gameData.put("whiteUsername", this.whiteUsername);
//        gameData.put("blackUsername", this.blackUsername);
//        gameData.put("gameName", this.gameName);
//        return new Gson().toJson(gameData);
        return new Gson().toJson(this);
    }
}
