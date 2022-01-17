package com.github.johnbanq.wiresquid.logic;

import lombok.Value;

import javax.annotation.Nullable;

/**
 * value object representing a connection
 */
@Value
public class WiresquidConnection {

    /**
     * id of the connection, unique within the {@link ConnectionDatabase}
     */
    long id;

    /**
     * state of the connection
     */
    ConnectionState state;

    /**
     * identifier of the connection, null if we don't have it yet
     */
    @Nullable ConnectionIdentifier identifier;

    public WiresquidConnection toClosed() {
        return new WiresquidConnection(
                id,
                ConnectionState.CLOSED,
                identifier
        );
    }

}
