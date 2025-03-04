package Request_response;

import Model.GameData;

import java.util.Collection;

public record ListGamesResult (Collection<GameData> gameList){
}
