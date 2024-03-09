/*
 * Copyright 2024 Evgenii Plugatar
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

import static com.plugatar.jkscope.function.Utils.originArgNotNull;
import static com.plugatar.jkscope.function.Utils.uncheckedCast;

/**
 * The {@link Runnable} specialization that might throw an exception.
 *
 * @param <E> the type of the throwing exception
 * @see Runnable
 */
@FunctionalInterface
public interface ThRunnable<E extends Throwable> {

  /**
   * Performs this operation.
   *
   * @throws E if runnable threw exception
   */
  void run() throws E;

  /**
   * Returns given runnable as an unchecked runnable.
   *
   * @param origin the origin runnable
   * @return unchecked runnable
   * @throws NullPointerException if {@code origin} arg is null
   */
  static ThRunnable<RuntimeException> unchecked(final ThRunnable<?> origin) {
    originArgNotNull(origin);
    return uncheckedCast(origin);
  }
}
