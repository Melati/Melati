package org.melati.util;

public class StringUtils {
  public static String[] split(String s, char c) {
    int n = 0;
    for (int i = s.indexOf(c); i >= 0; i = s.indexOf(c, i + 1))
      ++n;

    String[] them = new String[n + 1];

    for (int i = 0, m = 0;; ++m) {
      int j = s.indexOf(c, i);
      if (j == -1) {
        them[m] = s.substring(i);
        break;
      }
      else {
        them[m] = s.substring(i, j);
        i = j + 1;
      }
    }

    return them;
  }

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

  public static String capitalised(String name) {
    char suffix[] = name.toCharArray();
    suffix[0] = Character.toUpperCase(suffix[0]);
    return new String(suffix);
  }

  public static String tr(String s, String from, String to) {
    StringBuffer sNew = null;

    for (int i = 0; i < s.length(); ++i) {
      int t = from.indexOf(s.charAt(i));
      if (t != -1) {
	if (sNew == null) sNew = new StringBuffer(s);
	sNew.setCharAt(i, to.charAt(t));
      }
    }

    return sNew == null ? s : sNew.toString();
  }

  public static String tr(String s, char from, char to) {
    StringBuffer sNew = null;

    for (int i = 0; i < s.length(); ++i) {
      if (s.charAt(i) == from) {
	if (sNew == null) sNew = new StringBuffer(s);
	sNew.setCharAt(i, to);
      }
    }

    return sNew == null ? s : sNew.toString();
  }

  public static void main(String[] args) {
    System.out.println(tr(args[0], "abc", "123"));
    System.out.println(tr(args[0], "a", "1"));
  }
}
