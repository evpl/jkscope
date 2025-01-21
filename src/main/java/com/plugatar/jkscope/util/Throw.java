/*
 * Copyright 2025 Evgenii Plugatar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.plugatar.jkscope.util;

/**
 * Utility class. Contains single {@link #unchecked(Throwable)} method.
 */
public final class Throw {

  /**
   * Utility class ctor.
   */
  private Throw() {
  }

  /**
   * Throws given exception.
   *
   * <pre>{@code
   * Throw.unchecked(new Throwable());
   *
   * throw unchecked(new Throwable());
   * }</pre>
   *
   * @param exception the exception
   * @param <E>       the type of the exception
   * @return fake RuntimeException syntax
   * @throws E given exception
   */
  @SuppressWarnings("unchecked")
  public static <E extends Throwable> RuntimeException unchecked(final Throwable exception) throws E {
    if (exception == null) { throw new NullPointerException("exception arg is null"); }
    throw (E) exception;
  }
}
