package org.melati.poem;

public class OutsideRangePoemException extends PoemException {
  public Object low, limit, value;

  public OutsideRangePoemException(Object low, Object limit, Object value) {
    this.low = low;
    this.limit = limit;
    this.value = value;
  }

  public String getMessage() {
    return "The value " + value +
           " is outside the range [" + low + ", " + limit + ")";
  }
}
