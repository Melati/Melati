package org.melati.util;

public class StringUtils {
  public static void appendEscaped(StringBuffer b, String s, char e) {
    int l = s.length();
    for (int i = 0; i < l; ++i) {
      char c = s.charAt(i);
      if (c == '\\' || c == e) {
        // damn, found one; catch up to here ...

        for (int j = 0; j < i; ++j)
          b.append(s.charAt(j));
        b.append('\\');
        b.append(c);

        // ... and continue

        for (++i; i < l; ++i) {
          c = s.charAt(i);
          if (c == '\\' || c == e)
            b.append('\\');
          b.append(c);
        }

        return;
      }
    }

    b.append(s);
  }

  public static void appendQuoted(StringBuffer b, String s, char q) {
    b.append(q);
    appendEscaped(b, s, q);
    b.append(q);
  }

  public static String quoted(String i, char q) {
    StringBuffer b = new StringBuffer();
    appendQuoted(b, i, q);
    return b.toString();
  }

  public static String escaped(String s, char e) {
    int l = s.length();
    for (int i = 0; i < l; ++i) {
      char c = s.charAt(i);
      if (c == '\\' || c == e) {
        // damn, found one; catch up to here ...

        StringBuffer t = new StringBuffer(l + 2);

        for (int j = 0; j < i; ++j)
          t.append(s.charAt(j));
        t.append('\\');
        t.append(c);

        // ... and continue

        for (++i; i < l; ++i) {
          c = s.charAt(i);
          if (c == '\\' || c == e)
            t.append('\\');
          t.append(c);
        }

        return t.toString();
      }
    }

    return s;
  }
}
