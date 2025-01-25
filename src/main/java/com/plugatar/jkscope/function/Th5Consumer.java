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

import com.plugatar.jkscope.util.Cast;

import static com.plugatar.jkscope.function.Utils.consumerArgNotNull;
import static com.plugatar.jkscope.function.Utils.originArgNotNull;

/**
 * The {@link java.util.function.Consumer} specialization with {@code [Object,Object,Object,Object,Object->void]}
 * signature that might throw an exception.
 *
 * @param <T1> the type of the first input argument
 * @param <T2> the type of the second input argument
 * @param <T3> the type of the third input argument
 * @param <T4> the type of the fourth input argument
 * @param <T5> the type of the fifth input argument
 * @param <E>  the type of the throwing exception
 */
@FunctionalInterface
public interface Th5Consumer<T1, T2, T3, T4, T5, E extends Throwable> {

  /**
   * Performs this operation on the given arguments.
   *
   * @param t1 the first input argument
   * @param t2 the second input argument
   * @param t3 the third input argument
   * @param t4 the fourth input argument
   * @param t5 the fifth input argument
   * @throws E if consumer threw exception
   */
  void accept(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5) throws E;

  /**
   * Returns given consumer.
   *
   * @param consumer the consumer
   * @param <T1>     the type of the first input argument
   * @param <T2>     the type of the second input argument
   * @param <T3>     the type of the third input argument
   * @param <T4>     the type of the fourth input argument
   * @param <T5>     the type of the fifth input argument
   * @param <E>      the type of the throwing exception
   * @return consumer
   * @throws NullPointerException if {@code consumer} arg is {@code null}
   */
  static <T1, T2, T3, T4, T5, E extends Throwable> Th5Consumer<T1, T2, T3, T4, T5, E> of(final Th5Consumer<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends E> consumer) {
    consumerArgNotNull(consumer);
    return Cast.unsafe(consumer);
  }

  /**
   * Returns given consumer as an unchecked consumer.
   *
   * @param origin the origin consumer
   * @param <T1>   the type of the first input argument
   * @param <T2>   the type of the second input argument
   * @param <T3>   the type of the third input argument
   * @param <T4>   the type of the fourth input argument
   * @param <T5>   the type of the fifth input argument
   * @return unchecked consumer
   * @throws NullPointerException if {@code origin} arg is {@code null}
   */
  static <T1, T2, T3, T4, T5> Th5Consumer<T1, T2, T3, T4, T5, RuntimeException> unchecked(final Th5Consumer<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ?> origin) {
    originArgNotNull(origin);
    return Cast.unsafe(origin);
  }
}
