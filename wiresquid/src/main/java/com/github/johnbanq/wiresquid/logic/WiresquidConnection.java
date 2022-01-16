package com.github.johnbanq.wiresquid.logic;

import com.github.johnbanq.wiresquid.api.Connection;
import lombok.*;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * holder of {@link Connection} object, and various misc data for presenting
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class WiresquidConnection {

    /**
     * state of the connection
     */
    ConnectionState state;

    /**
     * identifier of the connection, null if we don't have it yet
     */
    @Nullable ConnectionIdentifier identifier;

    /**
     * represents the connection object, null if the connection is disconnected
     */
    @Nullable Connection connection;

    private List<ReceivedPacket> packets;

    public static WiresquidConnection fromNewConnection(Connection connection) {
        return new WiresquidConnection(
                ConnectionState.ACTIVE,
                null,
                connection,
                new ArrayList<>()
        );
    }

    // listeners //

    public synchronized void onPacketReceived(ReceivedPacket packet) {
        packets.add(packet);
    }

    public synchronized void onConnectionClosed() {
        state = ConnectionState.CLOSED;
        connection = null;
    }

}
