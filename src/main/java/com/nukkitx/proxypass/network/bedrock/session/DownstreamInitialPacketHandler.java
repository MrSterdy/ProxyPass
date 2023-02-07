package com.nukkitx.proxypass.network.bedrock.session;

import com.nukkitx.protocol.bedrock.BedrockClientSession;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import com.nukkitx.protocol.bedrock.packet.ClientToServerHandshakePacket;
import com.nukkitx.protocol.bedrock.packet.ServerToClientHandshakePacket;
import com.nukkitx.protocol.bedrock.util.EncryptionUtils;
import com.nukkitx.proxypass.ProxyPass;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.crypto.SecretKey;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.ECPublicKey;
import java.security.spec.InvalidKeySpecException;

@Log4j2
@RequiredArgsConstructor
public class DownstreamInitialPacketHandler implements BedrockPacketHandler {
    private final BedrockClientSession session;
    private final ProxyPlayerSession player;
    private final ProxyPass proxy;

    public boolean handle(ServerToClientHandshakePacket packet) {
        try {
            ECPublicKey serverKey = EncryptionUtils.generateKey(packet.getPublicKey());
            SecretKey key = EncryptionUtils.getSecretKey(this.player.getProxyKeyPair().getPrivate(), serverKey,
                    packet.getServerToken());
            session.enableEncryption(key);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }

        ClientToServerHandshakePacket clientToServerHandshake = new ClientToServerHandshakePacket();
        session.sendPacketImmediately(clientToServerHandshake);

        this.session.setPacketHandler(new DownstreamPacketHandler(this.session, this.proxy));
        log.debug("Downstream connected");

        return true;
    }
}
