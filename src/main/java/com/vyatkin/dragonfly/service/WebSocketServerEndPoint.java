package com.vyatkin.dragonfly.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ServerEndpoint(value = "/wss/{nodeId}")
public class WebSocketServerEndPoint {

	private static Logger logger = LoggerFactory.getLogger(WebSocketServerEndPoint.class);

	private static Set<Session> pullConnections = Collections.synchronizedSet(new HashSet<Session>());
	

	/**
	 * @param nodeId
	 *            - nodeId for the session
	 * @param session
	 *            - session object
	 */

	@OnOpen
	public void onOpen(@PathParam(value = "nodeId") String nodeId, final Session session, EndpointConfig ec) {
		logger.info("Server: opened... for Node Id : " + nodeId + " : " + session.getId()); 

		// Add session to the connected sessions set
		pullConnections.add(session);
	}

	@OnMessage
	public void onMessage(@PathParam(value = "nodeId") String nodeId, String message, Session session)
			throws SQLException, IOException {
		logger.info("*** Websocket Message : " + message);
		
		synchronized(pullConnections){
			/** 
			 * Iterate over the connected sessions
			 * and broadcast the received message
			 */
			for(Session client : pullConnections){
				if (!client.equals(session)){
					client.getBasicRemote().sendText(message);
				}
			}
		}
		
		String response = "{\"messageId\": " + nodeId + ",\"statusCode\": 202}"; 
		session.getBasicRemote().sendText(response);
	}

	/**
	 * @param session
	 *            - session object
	 * @param closeReason
	 *            - The reason of close of session
	 */
	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		logger.info("Server: Session " + session.getId() + " closed because of " + closeReason.toString()); 
		
    	// Remove session from the connected sessions set
    	pullConnections.remove(session);
	}

	/**
	 * @param session
	 *            - current session object
	 * @param t
	 *            - Throwable instance containing error info
	 */
	@OnError
	public void onError(Session session, Throwable t) {
		logger.error("Server: Session " + session.getId() + " error " + t.getMessage());
		
    	// Remove session from the connected sessions set
    	pullConnections.remove(session);
	}
}
