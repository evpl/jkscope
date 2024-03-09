package com.plugatar.jkscope.function;

/**
 * Utility methods.
 */
final class Utils {

  /**
   * Ctor.
   */
  private Utils() {
  }

  static void originArgNotNull(final Object origin) {
    if (origin == null) {
      throw new NullPointerException("origin arg is null");
    }
  }

  @SuppressWarnings("unchecked")
  static <T> T uncheckedCast(final Object obj) {
    return (T) obj;
  }
}
