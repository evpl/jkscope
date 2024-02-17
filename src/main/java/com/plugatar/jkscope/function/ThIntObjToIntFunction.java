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

/**
 * The {@link java.util.function.Function} specialization that accepts an object-valued and a {@code int}-valued
 * argument and produces an {@code int}-valued result and might throw an exception.
 *
 * @param <T> the type of the second input argument
 * @param <E> the type of the throwing exception
 * @see java.util.function.Function
 */
@FunctionalInterface
public interface ThIntObjToIntFunction<T, E extends Throwable> {

  /**
   * Applies this function to the given argument.
   *
   * @param value the first input argument
   * @param t     the second input argument
   * @return result
   * @throws E if function threw exception
   */
  int apply(int value, T t) throws E;

  /**
   * Returns this functions as an unchecked functions.
   *
   * @return unchecked function
   */
  @SuppressWarnings("unchecked")
  default ThIntObjToIntFunction<T, RuntimeException> asUnchecked() {
    return (ThIntObjToIntFunction<T, RuntimeException>) this;
  }
}
