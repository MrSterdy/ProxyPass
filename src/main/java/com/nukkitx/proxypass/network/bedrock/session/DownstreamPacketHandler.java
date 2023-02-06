package com.nukkitx.proxypass.network.bedrock.session;

import com.nukkitx.protocol.bedrock.BedrockClientSession;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import com.nukkitx.protocol.bedrock.packet.*;
import com.nukkitx.proxypass.ProxyPass;
import com.nukkitx.proxypass.network.bedrock.util.RecipeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class DownstreamPacketHandler implements BedrockPacketHandler {
    private final BedrockClientSession session;
    private final ProxyPass proxy;

    @Override
    public boolean handle(CraftingDataPacket packet) {
        RecipeUtils.writeRecipes(packet, this.proxy);
        return false;
    }

    @Override
    public boolean handle(DisconnectPacket packet) {
        this.session.disconnect();
        // Let the client see the reason too.
        return false;
    }
}
