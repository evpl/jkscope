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
 * The {@link java.util.function.Function} specialization that accepts an {@code long}-valued argument and produces an
 * {@code long}-valued result and might throw an exception.
 *
 * @param <E> the type of the throwing exception
 * @see java.util.function.Function
 */
@FunctionalInterface
public interface ThLongToLongFunction<E extends Throwable> {

  /**
   * Applies this function to the given argument.
   *
   * @param value the input argument
   * @return result
   * @throws E if function threw exception
   */
  long apply(long value) throws E;

  /**
   * Returns given function as an unchecked function.
   *
   * @param origin the origin function
   * @return unchecked function
   * @throws NullPointerException if {@code origin} arg is null
   */
  static ThLongToLongFunction<RuntimeException> unchecked(final ThLongToLongFunction<?> origin) {
    originArgNotNull(origin);
    return uncheckedCast(origin);
  }
}
