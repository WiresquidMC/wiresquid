package com.github.johnbanq.wiresquid.logic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.johnbanq.wiresquid.logic.connection.ConnectionIdentifier;
import com.nimbusds.jose.JWSObject;
import com.nukkitx.protocol.bedrock.packet.LoginPacket;
import lombok.SneakyThrows;

import java.util.UUID;

/**
 * logic used to parse identifier from packets
 */
public class IdentifierUtils {

    public static final ObjectMapper MAPPER = new ObjectMapper();

    @SneakyThrows
    public static ConnectionIdentifier parseIdentifier(LoginPacket packet) {
        JsonNode root = MAPPER.readTree(packet.getChainData().toByteArray());
        if(!root.has("chain") || root.get("chain") instanceof ArrayNode) {
            throw new IllegalArgumentException("invalid login chainData");
        }

        ArrayNode chainNode = (ArrayNode) root.get("chain");
        String jwtText = chainNode.get(chainNode.size() - 1).asText();
        JsonNode payload = MAPPER.readTree(JWSObject.parse(jwtText).getParsedString());

        JsonNode extraData = payload.get("extraData");
        if (!(extraData instanceof ObjectNode)) {
            throw new IllegalStateException("Invalid 'extraData'");
        }
        String displayName = extraData.get("displayName").asText();
        String xuid = extraData.get("XUID").asText();
        String uuid = extraData.get("UUID").asText();

        return new ConnectionIdentifier(
            displayName, UUID.fromString(uuid), xuid
        );
    }

}
