package com.github.johnbanq.wiresquid.logic;

import com.github.johnbanq.wiresquid.api.Connection;
import com.github.johnbanq.wiresquid.api.ConnectionListener;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * core object of storing and managing connections and packets within each
 */
@Slf4j
public class ConnectionDatabase implements ConnectionListener {

    private final Map<Connection, WiresquidConnection> activeConnections = new HashMap<>();

    private final List<WiresquidConnection> connections = Collections.synchronizedList(new LinkedList<>());

    // queries //

    /**
     * returns list of active connections
     * note: the connections list will be concurrently updated as new connection comes in,
     *       only new connections will be appended to the end of the list
     */
    public List<WiresquidConnection> getConnections() {
        return connections;
    }

    // listener logic //

    @Override
    public synchronized void onNewConnection(Connection connection) {
        WiresquidConnection conn = WiresquidConnection.fromNewConnection(connection);
        if(activeConnections.put(connection, conn)==null) {
            connections.add(conn);
        } else {
            log.warn("onNewConnection() was called twice on connection {}, ignoring the second one!", connection);
        }
    }

    @Override
    public synchronized void onPacketFromClient(Connection connection, BedrockPacket packet) {
        WiresquidConnection conn = activeConnections.get(connection);
        if(conn!=null) {
            conn.onPacketReceived(new ReceivedPacket(ReceivedPacket.Direction.CLIENT_TO_SERVER, packet));
        } else {
            log.warn("onPacketFromClient() was called twice on non-active connection {}, ignoring!", connection);
        }
    }

    @Override
    public synchronized void onPacketFromServer(Connection connection, BedrockPacket packet) {
        WiresquidConnection conn = activeConnections.get(connection);
        if(conn!=null) {
            conn.onPacketReceived(new ReceivedPacket(ReceivedPacket.Direction.SERVER_TO_CLIENT, packet));
        } else {
            log.warn("onPacketFromServer() was called twice on non-active connection {}, ignoring!", connection);
        }
    }

    @Override
    public synchronized void onConnectionClosed(Connection connection) {
        WiresquidConnection conn = activeConnections.remove(connection);
        if(conn != null) {
            conn.onConnectionClosed();
        }
    }

}
