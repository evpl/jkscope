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
package com.plugatar.jkscope;

import com.plugatar.jkscope.function.ThConsumer;
import com.plugatar.jkscope.function.ThPredicate;

/**
 * This interface contains instance methods:
 * <ul>
 * <li>{@link #let(ThConsumer)}</li>
 * <li>{@link #takeIf(ThPredicate)}</li>
 * <li>{@link #takeUnless(ThPredicate)}</li>
 * </ul>
 *
 * @param <T> the type of the value
 */
public interface SafeJKScope<T> {

  /**
   * Performs given consumer on the value.
   *
   * @param block the consumer
   * @throws NullPointerException if {@code block} arg is null
   */
  void let(ThConsumer<? super T, ?> block);

  /**
   * Filters the value using given predicate.
   *
   * @param block the predicate
   * @return filtered value
   * @throws NullPointerException if {@code block} arg is null
   */
  SafeJKScope<T> takeIf(ThPredicate<? super T, ?> block);

  /**
   * Filters the value using given predicate.
   *
   * @param block the predicate
   * @return filtered value
   * @throws NullPointerException if {@code block} arg is null
   */
  SafeJKScope<T> takeUnless(ThPredicate<? super T, ?> block);
}
