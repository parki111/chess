package server.websocket;

import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class WebSocketSessions {
    public final HashMap<Integer, HashSet<Session>> sessions = new HashMap<>();
    public final HashMap<Session, Integer> sessionsGameiD = new HashMap<>();

    public void addSession(Integer gameID, Session session) {
        try {
            if (!sessions.containsKey(gameID)) {
                sessions.put(gameID, new HashSet<>());

            }
            sessionsGameiD.put(session, gameID);
            sessions.get(gameID).add(session);
        }
        catch(Exception e) {
            System.out.println("Failed to add session");
        }

    }

    public void removeSession(Integer gameID, Session session) {
        try{
            sessions.get(gameID).remove(session);
            sessionsGameiD.remove(session);
        }
        catch(Exception e){
            System.out.println("Failed to leave game, no such game");
        }

    }

    public HashSet<Session> getSessions(Integer gameID){
        return sessions.get(gameID);
    }

    public Integer getGameID(Session session){
        return sessionsGameiD.get(session);
    }
}