package com.light.owl.image;

import java.util.Arrays;
import java.util.Optional;

/**
 * 通过二进制确定图片文件类型
 */
public enum ImageType {
  PNG(new byte[]{
      (byte) 0x89, (byte) 0x50, (byte) 0x4E
  }),
  JPG(new byte[]{
      (byte) 0xFF, (byte) 0xD8, (byte) 0xFF
  }),
  GIF(new byte[]{
      (byte) 0x47, (byte) 0x49, (byte) 0x46, (byte) 0x38
  }),
  BMP(new byte[]{
      (byte) 0x42, (byte) 0x4D
  }),
  ;
  private final byte[] chars;
  private final String hexString;

  ImageType(byte[] chars) {
    this.chars = chars;
    final StringBuilder stringBuilder = new StringBuilder();
    for (byte aChar : chars) {
      stringBuilder.append(Integer.toHexString(aChar & 0xFF));
    }
    this.hexString = stringBuilder.toString();
  }

  public static Optional<ImageType> getType(byte[] bytes) {
    if (bytes == null || bytes.length == 0) {
      return Optional.empty();
    }
    for (ImageType value : values()) {
      if (value.chars.length == bytes.length) {
        if (Arrays.equals(value.chars, bytes)) {
          return Optional.of(value);
        }
      } else if (bytes.length > value.chars.length) {
        if (Arrays.equals(value.chars, Arrays.copyOfRange(bytes, 0, value.chars.length))) {
          return Optional.of(value);
        }
      }
    }
    return Optional.empty();
  }

  public byte[] getChars() {
    return chars;
  }

  public String getHexString() {
    return hexString;
  }
}
