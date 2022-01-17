package com.github.johnbanq.wiresquid.logic.connection;

import lombok.Value;

import java.util.UUID;

@Value
public class ConnectionIdentifier {
    String displayName;
    UUID uuid;
    String xuid;
}
