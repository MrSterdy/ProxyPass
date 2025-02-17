package com.nukkitx.protocol.bedrock.data.skin;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Objects;

@Getter
@EqualsAndHashCode(doNotUseGetters = true)
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ImageData {
    public static final ImageData EMPTY = new ImageData(0, 0, new byte[0]);

    private static final int PIXEL_SIZE = 4;

    private static final int SINGLE_SKIN_SIZE = 64 * 32 * PIXEL_SIZE;
    private static final int DOUBLE_SKIN_SIZE = 64 * 64 * PIXEL_SIZE;

    private final int width;
    private final int height;
    private final byte[] image;

    public static ImageData of(int width, int height, byte[] image) {
        Objects.requireNonNull(image, "image");
        return new ImageData(width, height, image);
    }

    public static ImageData of(byte[] image) {
        Objects.requireNonNull(image, "image");
        switch (image.length) {
            case 0:
                return EMPTY;
            case SINGLE_SKIN_SIZE:
                return new ImageData(64, 32, image);
            case DOUBLE_SKIN_SIZE:
                return new ImageData(64, 64, image);
            default:
                throw new IllegalArgumentException("Invalid legacy skin");
        }
    }

    public void checkLegacySkinSize() {
        switch (image.length) {
            case SINGLE_SKIN_SIZE:
            case DOUBLE_SKIN_SIZE:
                return;
            default:
                throw new IllegalArgumentException("Invalid legacy skin");
        }
    }

    public static ImageData from(BufferedImage image) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color color = new Color(image.getRGB(x, y), true);
                outputStream.write(color.getRed());
                outputStream.write(color.getGreen());
                outputStream.write(color.getBlue());
                outputStream.write(color.getAlpha());
            }
        }
        image.flush();
        return new ImageData(image.getWidth(), image.getHeight(), outputStream.toByteArray());
    }
}
