package server.websocket;

import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class WebSocketSessions {
    public final HashMap<Integer, HashSet<Session>> sessions = new HashMap<>();

    public void addSession(Integer gameID, Session session) {
        if (!sessions.containsKey(gameID)){
            sessions.put(gameID,new HashSet<>());
        }
        sessions.get(gameID).add(session);
    }

    public void removeSession(Integer gameID, Session session) {
        sessions.get(gameID).remove(session);
    }

    public HashSet<Session> getSessions(Integer gameID){
        return sessions.get(gameID);
    }
}