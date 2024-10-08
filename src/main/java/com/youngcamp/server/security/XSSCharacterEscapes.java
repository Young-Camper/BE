package com.youngcamp.server.security;

import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SerializedString;
import org.apache.commons.text.StringEscapeUtils;

public class XSSCharacterEscapes extends CharacterEscapes {

  private final int[] asciiEscapes;

  public XSSCharacterEscapes() {
    asciiEscapes = CharacterEscapes.standardAsciiEscapesForJSON();
    asciiEscapes['<'] = CharacterEscapes.ESCAPE_CUSTOM;
    asciiEscapes['>'] = CharacterEscapes.ESCAPE_CUSTOM;
    asciiEscapes['&'] = CharacterEscapes.ESCAPE_CUSTOM;
    asciiEscapes['"'] = CharacterEscapes.ESCAPE_CUSTOM;
    asciiEscapes['('] = CharacterEscapes.ESCAPE_CUSTOM;
    asciiEscapes[')'] = CharacterEscapes.ESCAPE_CUSTOM;
    asciiEscapes['#'] = CharacterEscapes.ESCAPE_CUSTOM;
    asciiEscapes['\''] = CharacterEscapes.ESCAPE_CUSTOM;
  }

  @Override
  public int[] getEscapeCodesForAscii() {
    return asciiEscapes;
  }

  @Override
  public SerializableString getEscapeSequence(int ch) {
    char charAt = (char) ch;
    if (Character.isHighSurrogate(charAt) || Character.isLowSurrogate(charAt)) {
      String sb = "\\u" + String.format("%04x", ch);
      return new SerializedString(sb);
    } else {
      return new SerializedString(StringEscapeUtils.escapeHtml4(Character.toString(charAt)));
    }
  }
}
