package com.github.johnbanq.wiresquid.logic;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import lombok.Value;

/**
 * represents an intercepted packet
 */
@Value
public class ReceivedPacket {

    public enum Direction {
        // normal communication //
        CLIENT_TO_SERVER,
        SERVER_TO_CLIENT,
        // when proxy transformed it //
        CLIENT_PROXY_SERVER,
        SERVER_PROXY_CLIENT,
        // sent by proxy //
        PROXY_TO_SERVER,
        PROXY_TO_CLIENT,
    }

    Direction direction;

    BedrockPacket packet;

}
