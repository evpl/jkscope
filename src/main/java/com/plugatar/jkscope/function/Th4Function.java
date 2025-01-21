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

import static com.plugatar.jkscope.function.Utils.functionArgNotNull;
import static com.plugatar.jkscope.function.Utils.originArgNotNull;

/**
 * The {@link java.util.function.Function} specialization with {@code [Object,Object,Object,Object->Object]} signature
 * that might throw an exception.
 *
 * @param <T1> the type of the first input argument
 * @param <T2> the type of the second input argument
 * @param <T3> the type of the third input argument
 * @param <T4> the type of the fourth input argument
 * @param <R>  the type of the result
 * @param <E>  the type of the throwing exception
 */
@FunctionalInterface
public interface Th4Function<T1, T2, T3, T4, R, E extends Throwable> {

  /**
   * Applies this function to the given arguments.
   *
   * @param t1 the first input argument
   * @param t2 the second input argument
   * @param t3 the third input argument
   * @param t4 the fourth input argument
   * @return result
   * @throws E if function threw exception
   */
  R apply(T1 t1, T2 t2, T3 t3, T4 t4) throws E;

  /**
   * Returns given function.
   *
   * @param function the function
   * @param <T1>     the type of the first input argument
   * @param <T2>     the type of the second input argument
   * @param <T3>     the type of the third input argument
   * @param <T4>     the type of the fourth input argument
   * @param <R>      the type of the result
   * @param <E>      the type of the throwing exception
   * @return function
   * @throws NullPointerException if {@code function} arg is {@code null}
   */
  static <T1, T2, T3, T4, R, E extends Throwable> Th4Function<T1, T2, T3, T4, R, E> of(final Th4Function<? super T1, ? super T2, ? super T3, ? super T4, ? extends R, ? extends E> function) {
    functionArgNotNull(function);
    return Cast.unsafe(function);
  }

  /**
   * Returns given function as an unchecked function.
   *
   * @param origin the origin function
   * @param <T1>   the type of the first input argument
   * @param <T2>   the type of the second input argument
   * @param <T3>   the type of the third input argument
   * @param <T4>   the type of the fourth input argument
   * @param <R>    the type of the result
   * @return unchecked function
   * @throws NullPointerException if {@code origin} arg is {@code null}
   */
  static <T1, T2, T3, T4, R> Th4Function<T1, T2, T3, T4, R, RuntimeException> unchecked(final Th4Function<? super T1, ? super T2, ? super T3, ? super T4, ? extends R, ?> origin) {
    originArgNotNull(origin);
    return Cast.unsafe(origin);
  }
}
