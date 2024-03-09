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
 * The {@link java.util.function.Consumer} specialization that that accepts an {@code double}-valued argument and might
 * throw an exception.
 *
 * @param <E> the type of the throwing exception
 * @see java.util.function.Consumer
 */
@FunctionalInterface
public interface ThDoubleConsumer<E extends Throwable> {

  /**
   * Performs this operation on the given argument.
   *
   * @param value the input argument
   * @throws E if consumer threw exception
   */
  void accept(double value) throws E;

  /**
   * Returns given consumer as an unchecked consumer.
   *
   * @param origin the origin consumer
   * @return unchecked consumer
   * @throws NullPointerException if {@code origin} arg is null
   */
  static ThDoubleConsumer<RuntimeException> unchecked(final ThDoubleConsumer<?> origin) {
    originArgNotNull(origin);
    return uncheckedCast(origin);
  }
}
