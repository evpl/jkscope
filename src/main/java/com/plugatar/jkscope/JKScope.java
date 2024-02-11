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

import com.plugatar.jkscope.function.ThBiConsumer;
import com.plugatar.jkscope.function.ThBiFunction;
import com.plugatar.jkscope.function.ThConsumer;
import com.plugatar.jkscope.function.ThFunction;
import com.plugatar.jkscope.function.ThPredicate;
import com.plugatar.jkscope.function.ThRunnable;
import com.plugatar.jkscope.function.ThSupplier;
import com.plugatar.jkscope.function.ThTriConsumer;
import com.plugatar.jkscope.function.ThTriFunction;

/**
 * This interface contains scope functions.
 * <p>
 * Instance methods:
 * <ul>
 * <li>{@link #let(ThConsumer)}</li>
 * <li>{@link #run(ThFunction)}</li>
 * <li>{@link #also(ThConsumer)}</li>
 * <li>{@link #apply(ThConsumer)}</li>
 * <li>{@link #takeIf(ThPredicate)}</li>
 * <li>{@link #takeUnless(ThPredicate)}</li>
 * </ul>
 * Static methods:
 * <ul>
 * <li>{@link #let(ThRunnable)}</li>
 * <li>{@link #run(ThSupplier)}</li>
 * <li>{@link #with(Object)}</li>
 * <li>{@link #with(Object, ThConsumer)}</li>
 * <li>{@link #with(Object, Object, ThBiConsumer)}</li>
 * <li>{@link #with(Object, Object, Object, ThTriConsumer)}</li>
 * <li>{@link #with(Object, ThFunction)}</li>
 * <li>{@link #with(Object, Object, ThBiFunction)}</li>
 * <li>{@link #with(Object, Object, Object, ThTriFunction)}</li>
 * <li>{@link #withNonNull(Object)}</li>
 * <li>{@link #withNonNull(Object, ThConsumer)}</li>
 * <li>{@link #withNonNull(Object, Object, ThBiConsumer)}</li>
 * <li>{@link #withNonNull(Object, Object, Object, ThTriConsumer)}</li>
 * <li>{@link #withNonNull(Object, ThFunction)}</li>
 * <li>{@link #withNonNull(Object, Object, ThBiFunction)}</li>
 * <li>{@link #withNonNull(Object, Object, Object, ThTriFunction)}</li>
 * </ul>
 *
 * @param <T> the type of the class implementing JKScope
 */
public interface JKScope<T extends JKScope<T>> extends SafeJKScope<T> {

  @Override
  @SuppressWarnings("unchecked")
  default void let(final ThConsumer<? super T, ?> block) {
    if (block == null) { throw new NullPointerException("block arg is null"); }
    block.asUnchecked().accept((T) this);
  }

  /**
   * Performs given function on the value.
   *
   * @param block the function
   * @param <R>   the type of function result
   * @return function result
   * @throws NullPointerException if {@code block} arg is null
   */
  @SuppressWarnings("unchecked")
  default <R> R run(final ThFunction<? super T, ? extends R, ?> block) {
    if (block == null) { throw new NullPointerException("block arg is null"); }
    return block.asUnchecked().apply((T) this);
  }

  /**
   * Performs given consumer on the value. Similar to the {@link #apply(ThConsumer)} method.
   *
   * @param block the consumer
   * @return this
   * @throws NullPointerException if {@code block} arg is null
   */
  @SuppressWarnings("unchecked")
  default T also(final ThConsumer<? super T, ?> block) {
    if (block == null) { throw new NullPointerException("block arg is null"); }
    block.asUnchecked().accept((T) this);
    return (T) this;
  }

  /**
   * Performs given consumer on the value. Similar to the {@link #also(ThConsumer)} method.
   *
   * @param block the consumer
   * @return this
   * @throws NullPointerException if {@code block} arg is null
   */
  @SuppressWarnings("unchecked")
  default T apply(final ThConsumer<? super T, ?> block) {
    if (block == null) { throw new NullPointerException("block arg is null"); }
    block.asUnchecked().accept((T) this);
    return (T) this;
  }

  @Override
  @SuppressWarnings("unchecked")
  default JKScopeOpt<T> takeIf(final ThPredicate<? super T, ?> block) {
    if (block == null) { throw new NullPointerException("block arg is null"); }
    return block.asUnchecked().test((T) this)
      ? JKScopeOpt.of((T) this)
      : JKScopeOpt.empty();
  }

  @Override
  @SuppressWarnings("unchecked")
  default JKScopeOpt<T> takeUnless(final ThPredicate<? super T, ?> block) {
    if (block == null) { throw new NullPointerException("block arg is null"); }
    return block.asUnchecked().test((T) this)
      ? JKScopeOpt.empty()
      : JKScopeOpt.of((T) this);
  }

  /**
   * Performs given runnable.
   *
   * @param block the runnable
   * @throws NullPointerException if {@code block} arg is null
   */
  static void let(final ThRunnable<?> block) {
    if (block == null) { throw new NullPointerException("block arg is null"); }
    block.asUnchecked().run();
  }

  /**
   * Performs given supplier.
   *
   * @param block the supplier
   * @param <R>   the type of supplier result
   * @return supplier result
   * @throws NullPointerException if {@code block} arg is null
   */
  static <R> R run(final ThSupplier<? extends R, ?> block) {
    if (block == null) { throw new NullPointerException("block arg is null"); }
    return block.asUnchecked().get();
  }

  /**
   * Returns JKScopeOpt instance with given value.
   *
   * @param t   the value
   * @param <T> the type of the value
   * @return JKScopeOpt instance with given value
   */
  static <T> JKScopeOpt<T> with(final T t) {
    return JKScopeOpt.of(t);
  }

  /**
   * Performs given consumer on the given value.
   *
   * @param t     the value
   * @param block the consumer
   * @param <T>   type of the value
   * @throws NullPointerException if {@code block} arg is null
   */
  static <T> void with(final T t,
                       final ThConsumer<? super T, ?> block) {
    if (block == null) { throw new NullPointerException("block arg is null"); }
    block.asUnchecked().accept(t);
  }

  /**
   * Performs given consumer on the given values.
   *
   * @param t     the first value
   * @param u     the second value
   * @param block the consumer
   * @param <T>   type of the first value
   * @param <U>   type of the second value
   * @throws NullPointerException if {@code block} arg is null
   */
  static <T, U> void with(final T t,
                          final U u,
                          final ThBiConsumer<? super T, ? super U, ?> block) {
    if (block == null) { throw new NullPointerException("block arg is null"); }
    block.asUnchecked().accept(t, u);
  }

  /**
   * Performs given consumer on the given values.
   *
   * @param t     the first value
   * @param u     the second value
   * @param v     the third value
   * @param block the consumer
   * @param <T>   type of the first value
   * @param <U>   type of the second value
   * @param <V>   type of the third value
   * @throws NullPointerException if {@code block} arg is null
   */
  static <T, U, V> void with(final T t,
                             final U u,
                             final V v,
                             final ThTriConsumer<? super T, ? super U, ? super V, ?> block) {
    if (block == null) { throw new NullPointerException("block arg is null"); }
    block.asUnchecked().accept(t, u, v);
  }

  /**
   * Performs given function on the given value.
   *
   * @param t     the value
   * @param block the function
   * @param <T>   type of the value
   * @param <R>   the type of function result
   * @return function result
   * @throws NullPointerException if {@code block} arg is null
   */
  static <T, R> R with(final T t,
                       final ThFunction<? super T, ? extends R, ?> block) {
    if (block == null) { throw new NullPointerException("block arg is null"); }
    return block.asUnchecked().apply(t);
  }

  /**
   * Performs given function on the given values.
   *
   * @param t     the first value
   * @param u     the second value
   * @param block the function
   * @param <T>   the type of the first value
   * @param <U>   the type of the second value
   * @param <R>   the type of function result
   * @return function result
   * @throws NullPointerException if {@code block} arg is null
   */
  static <T, U, R> R with(final T t,
                          final U u,
                          final ThBiFunction<? super T, ? super U, ? extends R, ?> block) {
    if (block == null) { throw new NullPointerException("block arg is null"); }
    return block.asUnchecked().apply(t, u);
  }

  /**
   * Performs given function on the given values.
   *
   * @param t     the first value
   * @param u     the second value
   * @param v     the third value
   * @param block the function
   * @param <T>   the type of the first value
   * @param <U>   the type of the second value
   * @param <V>   the type of the third value
   * @param <R>   the type of function result
   * @return function result
   * @throws NullPointerException if {@code block} arg is null
   */
  static <T, U, V, R> R with(final T t,
                             final U u,
                             final V v,
                             final ThTriFunction<? super T, ? super U, ? super V, ? extends R, ?> block) {
    if (block == null) { throw new NullPointerException("block arg is null"); }
    return block.asUnchecked().apply(t, u, v);
  }

  /**
   * Returns JKScopeOpt instance with given value or empty JKScopeOpt instance if value is null.
   *
   * @param t   the value
   * @param <T> the type of the value
   * @return JKScopeOpt instance with given value or empty JKScopeOpt instance
   */
  static <T> JKScopeOpt<T> withNonNull(final T t) {
    return t == null
      ? JKScopeOpt.empty()
      : JKScopeOpt.of(t);
  }

  /**
   * Performs given consumer on the given value if value is non-null.
   *
   * @param t     the value
   * @param block the consumer
   * @param <T>   the type of the value
   * @throws NullPointerException if {@code block} arg is null
   */
  static <T> void withNonNull(final T t,
                              final ThConsumer<? super T, ?> block) {
    if (block == null) { throw new NullPointerException("block arg is null"); }
    if (t != null) {
      block.asUnchecked().accept(t);
    }
  }

  /**
   * Performs given consumer on the given values if all values are non-null.
   *
   * @param t     the first value
   * @param u     the second value
   * @param block the consumer
   * @param <T>   the type of the first value
   * @param <U>   the type of the second value
   * @throws NullPointerException if {@code block} arg is null
   */
  static <T, U> void withNonNull(final T t,
                                 final U u,
                                 final ThBiConsumer<? super T, ? super U, ?> block) {
    if (block == null) { throw new NullPointerException("block arg is null"); }
    if (t != null && u != null) {
      block.asUnchecked().accept(t, u);
    }
  }

  /**
   * Performs given consumer on the given values if all values are non-null.
   *
   * @param t     the first value
   * @param u     the second value
   * @param v     the third value
   * @param block the consumer
   * @param <T>   the type of the first value
   * @param <U>   the type of the second value
   * @param <V>   the type of the third value
   * @throws NullPointerException if {@code block} arg is null
   */
  static <T, U, V> void withNonNull(final T t,
                                    final U u,
                                    final V v,
                                    final ThTriConsumer<? super T, ? super U, ? super V, ?> block) {
    if (block == null) { throw new NullPointerException("block arg is null"); }
    if (t != null && u != null && v != null) {
      block.asUnchecked().accept(t, u, v);
    }
  }

  /**
   * Performs given function on the given value if value is non-null.
   *
   * @param t     the value
   * @param block the function
   * @param <T>   the type of the value
   * @param <R>   the type of the function result
   * @return JKScopeOpt instance
   * @throws NullPointerException if {@code block} arg is null
   */
  static <T, R> JKScopeOpt<R> withNonNull(final T t,
                                          final ThFunction<? super T, ? extends R, ?> block) {
    if (block == null) { throw new NullPointerException("block arg is null"); }
    return t != null
      ? JKScopeOpt.of(block.asUnchecked().apply(t))
      : JKScopeOpt.empty();
  }

  /**
   * Performs given function on the given values if all values are non-null.
   *
   * @param t     the first value
   * @param u     the second value
   * @param block the function
   * @param <T>   the type of the first value
   * @param <U>   the type of the second value
   * @param <R>   the type of the function result
   * @return JKScopeOpt instance
   * @throws NullPointerException if {@code block} arg is null
   */
  static <T, U, R> JKScopeOpt<R> withNonNull(final T t,
                                             final U u,
                                             final ThBiFunction<? super T, ? super U, ? extends R, ?> block) {
    if (block == null) { throw new NullPointerException("block arg is null"); }
    return t != null && u != null
      ? JKScopeOpt.of(block.asUnchecked().apply(t, u))
      : JKScopeOpt.empty();
  }

  /**
   * Performs given function on the given values if all values are non-null.
   *
   * @param t     the first value
   * @param u     the second value
   * @param v     the third value
   * @param block the function
   * @param <T>   the type of the first value
   * @param <U>   the type of the second value
   * @param <V>   the type of the third value
   * @param <R>   the type of the function result
   * @return JKScopeOpt instance
   * @throws NullPointerException if {@code block} arg is null
   */
  static <T, U, V, R> JKScopeOpt<R> withNonNull(final T t,
                                                final U u,
                                                final V v,
                                                final ThTriFunction<? super T, ? super U, ? super V, ? extends R, ?> block) {
    if (block == null) { throw new NullPointerException("block arg is null"); }
    return t != null && u != null && v != null
      ? JKScopeOpt.of(block.asUnchecked().apply(t, u, v))
      : JKScopeOpt.empty();
  }
}
