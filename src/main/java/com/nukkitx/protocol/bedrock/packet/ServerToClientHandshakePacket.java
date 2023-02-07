package com.nukkitx.protocol.bedrock.packet;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockPacketType;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(doNotUseGetters = true, callSuper = false)
public class ServerToClientHandshakePacket extends BedrockPacket {
    private String publicKey;

    private byte[] serverToken;

    @Override
    public final boolean handle(BedrockPacketHandler handler) {
        return handler.handle(this);
    }

    public BedrockPacketType getPacketType() {
        return BedrockPacketType.SERVER_TO_CLIENT_HANDSHAKE;
    }
}
