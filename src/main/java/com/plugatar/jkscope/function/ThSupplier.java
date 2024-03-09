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
 * The {@link java.util.function.Supplier} specialization that might throw an exception.
 *
 * @param <R> the type of the result
 * @param <E> the type of the throwing exception
 * @see java.util.function.Supplier
 */
@FunctionalInterface
public interface ThSupplier<R, E extends Throwable> {

  /**
   * Gets the result.
   *
   * @return result
   * @throws E if supplier threw exception
   */
  R get() throws E;

  /**
   * Returns given supplier as an unchecked supplier.
   *
   * @param origin the origin supplier
   * @param <R>    the type of the result
   * @return unchecked supplier
   * @throws NullPointerException if {@code origin} arg is null
   */
  static <R> ThSupplier<R, RuntimeException> unchecked(final ThSupplier<? extends R, ?> origin) {
    originArgNotNull(origin);
    return uncheckedCast(origin);
  }
}
