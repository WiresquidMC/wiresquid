package com.github.johnbanq.wiresquid.logic;

import com.github.johnbanq.wiresquid.api.Connection;
import com.github.johnbanq.wiresquid.api.ConnectionListener;
import com.github.johnbanq.wiresquid.logic.connection.ConnectionIdentifier;
import com.github.johnbanq.wiresquid.logic.connection.ConnectionState;
import com.github.johnbanq.wiresquid.logic.connection.ReceivedPacket;
import com.github.johnbanq.wiresquid.logic.connection.WiresquidConnection;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.LoginPacket;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * core object for storing and managing connections and packets within each
 */
@Slf4j
public class ConnectionDatabase implements ConnectionListener {

    // connection states //

    private long connectionId = 1;

    private io.vavr.collection.SortedMap<Long, WiresquidConnection> connections = io.vavr.collection.TreeMap.empty();

    private final BiMap<Long, Connection> id2connections = HashBiMap.create();

    private final Map<Long, io.vavr.collection.List<ReceivedPacket>> id2packets = new HashMap<>();

    // subscription states //

    private final Set<ConnectionSubscription> subscriptions = new HashSet<>();

    // subscriptions //

    public synchronized ConnectionSubscription subscribeConnections(ConnectionFilter filter) {
        ConnectionSubscription sub = new ConnectionSubscription(filter, this::stopSubscription);
        sub.onInit(connections.values());
        subscriptions.add(sub);
        return sub;
    }

    private synchronized void stopSubscription(ConnectionSubscription subscription) {
        subscriptions.remove(subscription);
    }

    // listener logic //

    @Override
    public synchronized void onNewConnection(Connection connection) {
        if(id2connections.inverse().get(connection)==null) {
            WiresquidConnection conn = new WiresquidConnection(connectionId, ConnectionState.ACTIVE, null);
            connectionId++;

            connections = connections.put(conn.getId(), conn);
            id2connections.put(conn.getId(), connection);
            id2packets.put(conn.getId(), io.vavr.collection.List.empty());

            subscriptions.forEach(s->s.onConnectionCreated(conn));
        } else {
            log.warn("onNewConnection() was called twice on connection {}, ignoring the second one!", connection);
        }
    }

    @Override
    public synchronized void onPacketFromClient(Connection connection, BedrockPacket packet) {
        onPacketReceived(
                connection,
                new ReceivedPacket(ReceivedPacket.Direction.CLIENT_TO_SERVER, packet)
        );
    }

    @Override
    public synchronized void onPacketFromServer(Connection connection, BedrockPacket packet) {
        onPacketReceived(
                connection,
                new ReceivedPacket(ReceivedPacket.Direction.SERVER_TO_CLIENT, packet)
        );
    }

    private synchronized void onPacketReceived(Connection connection, ReceivedPacket packet) {
        Long id = id2connections.inverse().get(connection);
        if(id!=null) {
            // log packets
            this.id2packets.computeIfPresent(id, (i,packets)->packets.append(packet));
            // update identifier on LoginPacket
            if(packet.getPacket() instanceof LoginPacket) {
                this.connections.computeIfPresent(id, (i,c)-> {
                    ConnectionIdentifier ident;

                    try {
                        ident = IdentifierUtils.parseIdentifier((LoginPacket) packet.getPacket());
                    } catch(Exception e) {
                        log.error(
                                "error while parsing identifier information for connection {}, falling through!",
                                connection,
                                e
                        );
                        return c;
                    }

                    WiresquidConnection newC = c.withIdentifier(ident);
                    subscriptions.forEach(s->s.onConnectionStateChange(c, newC));

                    return newC;
                });
            }
        } else {
            log.warn("onPacketFrom*() was called on non-active connection {}, ignoring!", connection);
        }
    }

    @Override
    public synchronized void onConnectionClosed(Connection connection) {
        Long id = id2connections.inverse().remove(connection);
        if(id != null) {
            connections = connections.computeIfPresent(id, (i,p)-> {
                WiresquidConnection np = p.toClosed();
                subscriptions.forEach(s->s.onConnectionStateChange(p, np));
                return np;
            })._2;
        }
    }

}
