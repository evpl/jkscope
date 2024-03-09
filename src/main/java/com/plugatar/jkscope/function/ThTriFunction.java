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
 * The {@link java.util.function.Function} specialization for 3 input arguments that might throw an exception.
 *
 * @param <T> the type of the first input argument
 * @param <U> the type of the second input argument
 * @param <V> the type of the third input argument
 * @param <R> the type of the result
 * @param <E> the type of the throwing exception
 */
@FunctionalInterface
public interface ThTriFunction<T, U, V, R, E extends Throwable> {

  /**
   * Applies this function to the given arguments.
   *
   * @param t the first input argument
   * @param u the second input argument
   * @param v the third input argument
   * @return result
   * @throws E if function threw exception
   */
  R apply(T t, U u, V v) throws E;

  /**
   * Returns given function as an unchecked function.
   *
   * @param origin the origin function
   * @param <T>    the type of the first input argument
   * @param <U>    the type of the second input argument
   * @param <V>    the type of the third input argument
   * @param <R>    the type of the result
   * @return unchecked function
   * @throws NullPointerException if {@code origin} arg is null
   */
  static <T, U, V, R> ThTriFunction<T, U, V, R, RuntimeException> unchecked(final ThTriFunction<? super T, ? super U, ? super V, ? extends R, ?> origin) {
    originArgNotNull(origin);
    return uncheckedCast(origin);
  }
}
