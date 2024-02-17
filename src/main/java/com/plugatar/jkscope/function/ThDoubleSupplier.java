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
 * The {@link java.util.function.Supplier} specialization that produces an {@code double}-valued result and might throw
 * an exception.
 *
 * @param <E> the type of the throwing exception
 * @see java.util.function.Supplier
 */
@FunctionalInterface
public interface ThDoubleSupplier<E extends Throwable> {

  /**
   * Gets the result.
   *
   * @return result
   * @throws E if supplier threw exception
   */
  double get() throws E;

  /**
   * Returns this supplier as an unchecked supplier.
   *
   * @return unchecked runnable
   */
  @SuppressWarnings("unchecked")
  default ThDoubleSupplier<RuntimeException> asUnchecked() {
    return (ThDoubleSupplier<RuntimeException>) this;
  }
}
