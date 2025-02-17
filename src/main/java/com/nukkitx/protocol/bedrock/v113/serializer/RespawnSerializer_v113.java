package com.nukkitx.protocol.bedrock.v113.serializer;

import com.nukkitx.protocol.bedrock.BedrockPacketHelper;
import com.nukkitx.protocol.bedrock.BedrockPacketSerializer;
import com.nukkitx.protocol.bedrock.packet.RespawnPacket;
import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RespawnSerializer_v113 implements BedrockPacketSerializer<RespawnPacket> {
    public static final RespawnSerializer_v113 INSTANCE = new RespawnSerializer_v113();

    @Override
    public void serialize(ByteBuf buffer, BedrockPacketHelper helper, RespawnPacket packet) {
        helper.writeVector3f(buffer, packet.getPosition());
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockPacketHelper helper, RespawnPacket packet) {
        packet.setPosition(helper.readVector3f(buffer));
    }
}
