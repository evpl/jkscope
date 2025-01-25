/*
 * Copyright 2024-2025 Evgenii Plugatar
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
 * Utility class. Contains single {@link #unsafe(Object)} method.
 */
public final class Cast {

  /**
   * Utility class ctor.
   */
  private Cast() {
  }

  /**
   * Returns given object of type {@code R}.
   *
   * <pre>{@code
   * Object value = 1;
   * Integer intValue = Cast.unsafe(value);
   * Long longValue = Cast.<Integer>unsafe(value).longValue();
   * }</pre>
   *
   * @param obj the object
   * @param <R> return object type
   * @return given object of type {@code R}
   * @throws ClassCastException if given object is not an instance of type {@code R}
   */
  @SuppressWarnings("unchecked")
  public static <R> R unsafe(final Object obj) {
    return (R) obj;
  }
}
