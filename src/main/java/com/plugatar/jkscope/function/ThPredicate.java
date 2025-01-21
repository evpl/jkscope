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
 * The {@link java.util.function.Predicate} specialization with {@code [Object->boolean]} signature that might throw an
 * exception.
 *
 * @param <T> the type of the input argument
 * @param <E> the type of the throwing exception
 */
@FunctionalInterface
public interface ThPredicate<T, E extends Throwable> {

  /**
   * Evaluates this predicate on the given argument.
   *
   * @param t the input argument
   * @throws E if predicate threw exception
   */
  boolean test(T t) throws E;

  /**
   * Returns given predicate.
   *
   * @param consumer the predicate
   * @param <T>      the type of the input argument
   * @param <E>      the type of the throwing exception
   * @return unchecked predicate
   * @throws NullPointerException if {@code consumer} arg is {@code null}
   */
  static <T, E extends Throwable> ThPredicate<T, E> of(final ThPredicate<? super T, ? extends E> consumer) {
    consumerArgNotNull(consumer);
    return Cast.unsafe(consumer);
  }

  /**
   * Returns given consumer as an unchecked predicate.
   *
   * @param origin the origin predicate
   * @param <T>    the type of the input argument
   * @return unchecked predicate
   * @throws NullPointerException if {@code origin} arg is {@code null}
   */
  static <T> ThPredicate<T, RuntimeException> unchecked(final ThPredicate<? super T, ?> origin) {
    originArgNotNull(origin);
    return Cast.unsafe(origin);
  }
}
