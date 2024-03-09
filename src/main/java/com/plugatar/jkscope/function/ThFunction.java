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
 * The {@link java.util.function.Function} specialization that might throw an exception.
 *
 * @param <T> the type of the input argument
 * @param <R> the type of the result
 * @param <E> the type of the throwing exception
 * @see java.util.function.Function
 */
@FunctionalInterface
public interface ThFunction<T, R, E extends Throwable> {

  /**
   * Applies this function to the given argument.
   *
   * @param t the input argument
   * @return result
   * @throws E if function threw exception
   */
  R apply(T t) throws E;

  /**
   * Returns given function as an unchecked function.
   *
   * @param origin the origin function
   * @param <T>    the type of the input argument
   * @param <R>    the type of the result
   * @return unchecked function
   * @throws NullPointerException if {@code origin} arg is null
   */
  static <T, R> ThFunction<T, R, RuntimeException> unchecked(final ThFunction<? super T, ? extends R, ?> origin) {
    originArgNotNull(origin);
    return uncheckedCast(origin);
  }
}
