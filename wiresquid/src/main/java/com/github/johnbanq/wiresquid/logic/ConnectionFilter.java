package com.github.johnbanq.wiresquid.logic;

import lombok.Value;

import javax.annotation.Nullable;

/**
 * filter for choosing connections
 * note: null stands for no limitation, "unknown" for String field means to look for unspecified only
 */
@Value
public class ConnectionFilter {

    public static final String UNKNOWN_FILTER = "unknown";

    @Nullable String playerName;
    @Nullable String playerUUID;
    @Nullable ConnectionState state;

    public ConnectionFilter() {
        this(null, null, null);
    }

    public ConnectionFilter(@Nullable String playerName, @Nullable String playerUUID, @Nullable ConnectionState state) {
        this.playerName = playerName;
        this.playerUUID = playerUUID;
        this.state = state;
    }

    public boolean shouldAccept(WiresquidConnection connection) {
        boolean accept = true;
        if(playerName != null) {
            if(connection.getIdentifier() == null) {
                accept &= playerName.equals(UNKNOWN_FILTER);
            } else {
                accept &= connection.getIdentifier().getDisplayName().contains(playerName);
            }
        }
        if(playerUUID != null) {
            if(connection.getIdentifier() == null) {
                accept &= playerUUID.equals(UNKNOWN_FILTER);
            } else {
                accept &= connection.getIdentifier().getUuid().toString().contains(playerUUID);
            }
        }
        if(state !=null) {
            accept &= connection.getState() == state;
        }
        return accept;
    }

}
