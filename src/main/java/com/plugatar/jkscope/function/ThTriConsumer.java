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
 * The {@link java.util.function.Consumer} specialization for 3 input arguments that might throw an exception.
 *
 * @param <T> the type of the first input argument
 * @param <U> the type of the second input argument
 * @param <V> the type of the third input argument
 * @param <E> the type of the throwing exception
 */
@FunctionalInterface
public interface ThTriConsumer<T, U, V, E extends Throwable> {

  /**
   * Performs this operation on the given arguments.
   *
   * @param t the first input argument
   * @param u the second input argument
   * @param v the third input argument
   * @throws E if consumer threw exception
   */
  void accept(T t, U u, V v) throws E;

  /**
   * Returns given consumer as an unchecked consumer.
   *
   * @param origin the origin consumer
   * @param <T>    the type of the first input argument
   * @param <U>    the type of the second input argument
   * @param <V>    the type of the third input argument
   * @return unchecked consumer
   * @throws NullPointerException if {@code origin} arg is null
   */
  static <T, U, V> ThTriConsumer<T, U, V, RuntimeException> unchecked(final ThTriConsumer<? super T, ? super U, ? super V, ?> origin) {
    originArgNotNull(origin);
    return uncheckedCast(origin);
  }
}
