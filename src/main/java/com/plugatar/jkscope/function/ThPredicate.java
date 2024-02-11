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
 * The {@link java.util.function.Predicate} specialization that might throw an exception.
 *
 * @param <T> the type of the input argument
 * @param <E> the type of the throwing exception
 * @see java.util.function.Predicate
 */
@FunctionalInterface
public interface ThPredicate<T, E extends Throwable> {

  /**
   * Applies this predicate to the given argument.
   *
   * @param t the input argument
   * @return result
   * @throws E if function threw exception
   */
  boolean test(T t) throws E;

  /**
   * Returns this predicate as an unchecked predicate.
   *
   * @return unchecked predicate
   */
  @SuppressWarnings("unchecked")
  default ThPredicate<T, RuntimeException> asUnchecked() {
    return (ThPredicate<T, RuntimeException>) this;
  }
}
