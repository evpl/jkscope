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
package com.plugatar.jkscope.function;

import com.plugatar.jkscope.util.Cast;

import static com.plugatar.jkscope.function.Utils.consumerArgNotNull;
import static com.plugatar.jkscope.function.Utils.originArgNotNull;

/**
 * The {@link java.util.function.Consumer} specialization with {@code [int,Object->void]} signature that might throw an
 * exception.
 *
 * @param <T> the type of the second input argument
 * @param <E> the type of the throwing exception
 */
@FunctionalInterface
public interface Th2ConsumerIntObj<T, E extends Throwable> {

  /**
   * Performs this operation on the given argument.
   *
   * @param value the first input argument
   * @param t     the second input argument
   * @throws E if consumer threw exception
   */
  void accept(int value, T t) throws E;

  /**
   * Returns given consumer.
   *
   * @param consumer the consumer
   * @param <T>      the type of the second input argument
   * @param <E>      the type of the throwing exception
   * @return consumer
   * @throws NullPointerException if {@code consumer} arg is {@code null}
   */
  static <T, E extends Throwable> Th2ConsumerIntObj<T, E> of(final Th2ConsumerIntObj<? super T, ? extends E> consumer) {
    consumerArgNotNull(consumer);
    return Cast.unsafe(consumer);
  }

  /**
   * Returns given consumer as an unchecked consumer.
   *
   * @param origin the origin consumer
   * @param <T>    the type of the second input argument
   * @return unchecked consumer
   * @throws NullPointerException if {@code origin} arg is {@code null}
   */
  static <T> Th2ConsumerIntObj<T, RuntimeException> unchecked(final Th2ConsumerIntObj<? super T, ?> origin) {
    originArgNotNull(origin);
    return Cast.unsafe(origin);
  }
}
