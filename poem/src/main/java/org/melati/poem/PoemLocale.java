package org.melati.util;

import java.util.*;
import java.text.*;

public class MelatiLocale {

  public static final MelatiLocale here = new MelatiLocale(Locale.UK);

  private final Locale locale;

  private final DateFormatSymbols dateFormatSymbols;
  private final String[] months, shortMonths;
  private final DateFormat[] dateFormats;

  public MelatiLocale(Locale locale) {
    if (locale == null)
      throw new NullPointerException();
    this.locale = locale;

    dateFormatSymbols = new DateFormatSymbols(locale);
    months = dateFormatSymbols.getMonths();
    shortMonths = dateFormatSymbols.getShortMonths();

    dateFormats = new DateFormat[4]; // don't tell me this will break
    dateFormats[DateFormat.FULL] =
        DateFormat.getDateInstance(DateFormat.FULL, locale);
    dateFormats[DateFormat.LONG] =
        DateFormat.getDateInstance(DateFormat.LONG, locale);
    dateFormats[DateFormat.MEDIUM] =
        DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
    dateFormats[DateFormat.SHORT] =
        DateFormat.getDateInstance(DateFormat.SHORT, locale);
  }

  public final Locale locale() {
    return locale;
  }

  public String monthName(int monthNum) {
    return months[monthNum - 1];
  }

  public String shortMonthName(int monthNum) {
    return shortMonths[monthNum - 1];
  }

  public DateFormat dateFormat(int style) {
    return dateFormats[style];
  }
}
