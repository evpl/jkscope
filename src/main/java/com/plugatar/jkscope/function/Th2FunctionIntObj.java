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

import static com.plugatar.jkscope.function.Utils.functionArgNotNull;
import static com.plugatar.jkscope.function.Utils.originArgNotNull;

/**
 * The {@link java.util.function.Function} specialization with {@code [int,Object->Object]} signature that might throw
 * an exception.
 *
 * @param <T> the type of the second input argument
 * @param <R> the type of the result
 * @param <E> the type of the throwing exception
 */
@FunctionalInterface
public interface Th2FunctionIntObj<T, R, E extends Throwable> {

  /**
   * Applies this function to the given arguments.
   *
   * @param value the first input argument
   * @param t     the second input argument
   * @return result
   * @throws E if function threw exception
   */
  R apply(int value, T t) throws E;

  /**
   * Returns given function.
   *
   * @param function the function
   * @param <T>      the type of the second input argument
   * @param <R>      the type of the result
   * @param <E>      the type of the throwing exception
   * @return function
   * @throws NullPointerException if {@code function} arg is {@code null}
   */
  static <T, R, E extends Throwable> Th2FunctionIntObj<T, R, E> of(final Th2FunctionIntObj<? super T, ? extends R, ? extends E> function) {
    functionArgNotNull(function);
    return Cast.unsafe(function);
  }

  /**
   * Returns given function as an unchecked function.
   *
   * @param origin the origin function
   * @param <T>    the type of the second input argument
   * @param <R>    the type of the result
   * @return unchecked function
   * @throws NullPointerException if {@code origin} arg is {@code null}
   */
  static <T, R> Th2FunctionIntObj<T, R, RuntimeException> unchecked(final Th2FunctionIntObj<? super T, ? extends R, ?> origin) {
    originArgNotNull(origin);
    return Cast.unsafe(origin);
  }
}
