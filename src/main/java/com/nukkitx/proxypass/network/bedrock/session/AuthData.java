package com.nukkitx.proxypass.network.bedrock.session;

import lombok.Value;

import java.util.UUID;

@Value
public class AuthData {
    String displayName;
    UUID identity;
}
