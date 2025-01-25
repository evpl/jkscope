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
package com.plugatar.jkscope.function;

/**
 * Utility methods.
 */
final class Utils {

  /**
   * Utility class ctor.
   */
  private Utils() {
  }

  static void originArgNotNull(final Object origin) {
    if (origin == null) { throw new NullPointerException("origin arg is null"); }
  }

  static void consumerArgNotNull(final Object consumer) {
    if (consumer == null) { throw new NullPointerException("consumer arg is null"); }
  }

  static void functionArgNotNull(final Object function) {
    if (function == null) { throw new NullPointerException("function arg is null"); }
  }

  static void runnableArgNotNull(final Object runnable) {
    if (runnable == null) { throw new NullPointerException("runnable arg is null"); }
  }

  static void supplierArgNotNull(final Object supplier) {
    if (supplier == null) { throw new NullPointerException("supplier arg is null"); }
  }
}
