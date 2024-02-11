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

import java.util.Optional;

/**
 * This interface contains instance methods:
 * <ul>
 * <li>{@link #let(ThConsumer)}</li>
 * <li>{@link #takeIf(ThPredicate)}</li>
 * <li>{@link #takeUnless(ThPredicate)}</li>
 * <li>{@link #takeNonNull()}</li>
 * <li>{@link #asOptional()}</li>
 * </ul>
 * And static methods:
 * <ul>
 * <li>{@link #empty()}</li>
 * <li>{@link #of(Object)}</li>
 * </ul>
 *
 * @param <T> the type of the value
 */
public interface JKScopeOpt<T> extends SafeJKScope<T> {

  @Override
  JKScopeOpt<T> takeIf(ThPredicate<? super T, ?> block);

  @Override
  JKScopeOpt<T> takeUnless(ThPredicate<? super T, ?> block);

  /**
   * Filters the value.
   *
   * @return filtered value
   */
  JKScopeOpt<T> takeNonNull();

  /**
   * Returns the value as an Optional instance.
   *
   * @return Optional instance
   */
  Optional<T> asOptional();

  /**
   * Returns an empty JKScopeOpt instance.
   *
   * @param <T> the type of the value
   * @return empty JKScopeOpt instance
   */
  @SuppressWarnings("unchecked")
  static <T> JKScopeOpt<T> empty() {
    return (JKScopeOpt<T>) Empty.INSTANCE;
  }

  /**
   * Returns a non-empty JKScopeOpt instance (null value considered a value too).
   *
   * @param value the value
   * @param <T>   the type of value
   * @return non-empty JKScopeOpt instance
   */
  static <T> JKScopeOpt<T> of(final T value) {
    return new OfValue<>(value);
  }

  /**
   * Non-empty JKScopeOpt implementation.
   *
   * @param <T> the type of the value
   */
  final class OfValue<T> implements JKScopeOpt<T> {
    private final T value;

    /**
     * Ctor
     *
     * @param value the value
     */
    private OfValue(final T value) {
      this.value = value;
    }

    @Override
    public void let(final ThConsumer<? super T, ?> block) {
      if (block == null) { throw new NullPointerException("block arg is null"); }
      block.asUnchecked().accept(this.value);
    }

    @Override
    public JKScopeOpt<T> takeIf(final ThPredicate<? super T, ?> block) {
      if (block == null) { throw new NullPointerException("block arg is null"); }
      return block.asUnchecked().test(this.value)
        ? this
        : JKScopeOpt.empty();
    }

    @Override
    public JKScopeOpt<T> takeUnless(final ThPredicate<? super T, ?> block) {
      if (block == null) { throw new NullPointerException("block arg is null"); }
      return block.asUnchecked().test(this.value)
        ? JKScopeOpt.empty()
        : this;
    }

    @Override
    public JKScopeOpt<T> takeNonNull() {
      return this.value == null
        ? JKScopeOpt.empty()
        : this;
    }

    @Override
    public Optional<T> asOptional() {
      return Optional.ofNullable(this.value);
    }
  }

  /**
   * Empty JKScopeOpt implementation.
   *
   * @param <T> the type of the value
   */
  final class Empty<T> implements JKScopeOpt<T> {
    private static final Empty<?> INSTANCE = new Empty<>();

    /**
     * Ctor.
     */
    private Empty() {
    }

    @Override
    public void let(final ThConsumer<? super T, ?> block) {
      if (block == null) { throw new NullPointerException("block arg is null"); }
    }

    @Override
    public JKScopeOpt<T> takeIf(final ThPredicate<? super T, ?> block) {
      if (block == null) { throw new NullPointerException("block arg is null"); }
      return this;
    }

    @Override
    public JKScopeOpt<T> takeUnless(final ThPredicate<? super T, ?> block) {
      if (block == null) { throw new NullPointerException("block arg is null"); }
      return this;
    }

    @Override
    public JKScopeOpt<T> takeNonNull() {
      return this;
    }

    @Override
    public Optional<T> asOptional() {
      return Optional.empty();
    }
  }
}
