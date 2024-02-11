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
 * The {@link java.util.function.Consumer} specialization that might throw an exception.
 *
 * @param <T> the type of the input argument
 * @param <E> the type of the throwing exception
 * @see java.util.function.Consumer
 */
@FunctionalInterface
public interface ThConsumer<T, E extends Throwable> {

  /**
   * Performs this operation on the given argument.
   *
   * @param t the input argument
   * @throws E if consumer threw exception
   */
  void accept(T t) throws E;

  /**
   * Returns this consumer as an unchecked consumer.
   *
   * @return unchecked consumer
   */
  @SuppressWarnings("unchecked")
  default ThConsumer<T, RuntimeException> asUnchecked() {
    return (ThConsumer<T, RuntimeException>) this;
  }
}
