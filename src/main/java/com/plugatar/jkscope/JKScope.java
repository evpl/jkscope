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
import com.plugatar.jkscope.function.ThDoubleConsumer;
import com.plugatar.jkscope.function.ThDoubleObjToDoubleFunction;
import com.plugatar.jkscope.function.ThDoubleSupplier;
import com.plugatar.jkscope.function.ThDoubleToDoubleFunction;
import com.plugatar.jkscope.function.ThFunction;
import com.plugatar.jkscope.function.ThIntConsumer;
import com.plugatar.jkscope.function.ThIntObjToIntFunction;
import com.plugatar.jkscope.function.ThIntSupplier;
import com.plugatar.jkscope.function.ThIntToIntFunction;
import com.plugatar.jkscope.function.ThLongConsumer;
import com.plugatar.jkscope.function.ThLongObjToLongFunction;
import com.plugatar.jkscope.function.ThLongSupplier;
import com.plugatar.jkscope.function.ThLongToLongFunction;
import com.plugatar.jkscope.function.ThPredicate;
import com.plugatar.jkscope.function.ThRunnable;
import com.plugatar.jkscope.function.ThSupplier;
import com.plugatar.jkscope.function.ThTriConsumer;
import com.plugatar.jkscope.function.ThTriFunction;

import java.util.concurrent.atomic.AtomicReference;

import static com.plugatar.jkscope.Utils.blockArgNotNull;
import static com.plugatar.jkscope.Utils.uncheckedCast;

/**
 * Implement this interface to use these methods:
 * <ul>
 * <li>{@link #also(ThConsumer)}</li>
 * <li>{@link #letIt(ThConsumer)}</li>
 * <li>{@link #letOut(ThFunction)}</li>
 * <li>{@link #letOpt(ThFunction)}</li>
 * <li>{@link #takeIf(ThPredicate)}</li>
 * <li>{@link #takeUnless(ThPredicate)}</li>
 * </ul>
 * Example:
 * <pre>{@code
 * public class MyClass implements JKScope<MyClass> {
 *   private int value = 0;
 *
 *   public void setIntValue(int value) {
 *     this.value = value;
 *   }
 *
 *   public int getIntValue() {
 *     return this.value;
 *   }
 * }
 *
 * new MyClass().also(it -> it.setIntValue(20)).takeIf(it -> it.getIntValue() > 10).letIt(it -> System.out.println("ok"));
 * }</pre>
 * Use static methods anywhere:
 * <ul>
 * <li>{@link #run(ThRunnable)}</li>
 * <li>{@link #runCatching(ThRunnable)}</li>
 * <li>{@link #runRec(ThConsumer)}</li>
 * <li>{@link #with(Object, ThConsumer)}</li>
 * <li>{@link #withInt(int, ThIntConsumer)}</li>
 * <li>{@link #withLong(long, ThLongConsumer)}</li>
 * <li>{@link #withDouble(double, ThDoubleConsumer)}</li>
 * <li>{@link #withResource(AutoCloseable, ThConsumer)}</li>
 * <li>{@link #with(Object, Object, ThBiConsumer)}</li>
 * <li>{@link #with(Object, Object, Object, ThTriConsumer)}</li>
 * <li>{@link #let(Object)}</li>
 * <li>{@link #letNonNull(Object)}</li>
 * <li>{@link #let(ThSupplier)}</li>
 * <li>{@link #letInt(ThIntSupplier)}</li>
 * <li>{@link #letLong(ThLongSupplier)}</li>
 * <li>{@link #letDouble(ThDoubleSupplier)}</li>
 * <li>{@link #let(Object, ThConsumer)}</li>
 * <li>{@link #letInt(int, ThIntConsumer)}</li>
 * <li>{@link #letLong(long, ThLongConsumer)}</li>
 * <li>{@link #letDouble(double, ThDoubleConsumer)}</li>
 * <li>{@link #letRec(Object, ThBiFunction)}</li>
 * <li>{@link #letIntRec(int, ThIntObjToIntFunction)}</li>
 * <li>{@link #letLongRec(long, ThLongObjToLongFunction)}</li>
 * <li>{@link #letDoubleRec(double, ThDoubleObjToDoubleFunction)}</li>
 * <li>{@link #letWith(Object, ThFunction)}</li>
 * <li>{@link #letWithResource(AutoCloseable, ThFunction)}</li>
 * <li>{@link #letWith(Object, Object, ThBiFunction)}</li>
 * <li>{@link #letWith(Object, Object, Object, ThTriFunction)}</li>
 * </ul>
 * Some examples:
 * <pre>{@code
 * Map<String, Integer> map = let(new HashMap<>(), it -> {
 *   it.put("value1", 1);
 *   it.put("value2", 2);
 * };
 *
 * let("value").takeUnless(it -> it.isEmpty()).takeIf(it -> it.length() < 100).letIt(it -> System.out.println(it));
 *
 * int value = letIntRec(10, (n, func) -> {
 *   if (n < 2) {
 *     return n;
 *   }
 *   return func.apply(n - 1) + func.apply(n - 2);
 * });
 *
 * with(new MyObject(), it -> {
 *   System.out.println(it);
 * });
 * }</pre>
 *
 * @param <V> the type of the value
 */
public interface JKScope<V extends JKScope<V>> extends BaseScope<V, V> {

  @Override
  default V also(final ThConsumer<? super V, ?> block) {
    blockArgNotNull(block);
    block.asUnchecked().accept(uncheckedCast(this));
    return uncheckedCast(this);
  }

  @Override
  default V letIt(final ThConsumer<? super V, ?> block) {
    blockArgNotNull(block);
    block.asUnchecked().accept(uncheckedCast(this));
    return uncheckedCast(this);
  }

  @Override
  default <R> R letOut(final ThFunction<? super V, ? extends R, ?> block) {
    blockArgNotNull(block);
    return block.asUnchecked().apply(uncheckedCast(this));
  }

  @Override
  default <R> Opt<R> letOpt(final ThFunction<? super V, ? extends R, ?> block) {
    blockArgNotNull(block);
    return Opt.of(block.asUnchecked().apply(uncheckedCast(this)));
  }

  @Override
  default Opt<V> takeIf(final ThPredicate<? super V, ?> block) {
    blockArgNotNull(block);
    return block.asUnchecked().test(uncheckedCast(this))
      ? Opt.of(uncheckedCast(this))
      : Opt.empty();
  }

  @Override
  default Opt<V> takeUnless(final ThPredicate<? super V, ?> block) {
    blockArgNotNull(block);
    return block.asUnchecked().test(uncheckedCast(this))
      ? Opt.empty()
      : Opt.of(uncheckedCast(this));
  }

  /**
   * Performs given function block.
   * <pre>{@code
   * run(() -> {
   *   System.out.println("Hi");
   * });
   * }</pre>
   *
   * @param block the function block
   * @throws NullPointerException if {@code block} arg is null
   */
  static void run(final ThRunnable<?> block) {
    blockArgNotNull(block);
    block.asUnchecked().run();
  }

  /**
   * Performs given function block and catching any {@link Throwable} exception.
   * <pre>{@code
   * runCatching(() -> {
   *   if(new Random().nextInt(0, 100) == 50) {
   *     throw new Throwable();
   *   }
   * });
   * }</pre>
   *
   * @param block the function block
   * @throws NullPointerException if {@code block} arg is null
   */
  static void runCatching(final ThRunnable<?> block) {
    blockArgNotNull(block);
    try {
      block.asUnchecked().run();
    } catch (final Throwable ignored) { }
  }

  /**
   * Performs given function block recursively.
   * <pre>{@code
   * runRec(func -> {
   *   if(new Random().nextInt(0, 100) == 50) {
   *     func.run();
   *   }
   * });
   * }</pre>
   *
   * @param block the function block
   * @throws NullPointerException if {@code block} arg is null
   */
  static void runRec(final ThConsumer<ThRunnable<Throwable>, ?> block) {
    blockArgNotNull(block);
    final AtomicReference<ThRunnable<Throwable>> selfRef = new AtomicReference<>();
    selfRef.set(() -> block.accept(selfRef.get()));
    block.asUnchecked().accept(selfRef.get());
  }

  /**
   * Performs given function block on given value.
   * <pre>{@code
   * with("Hi", v -> {
   *   System.out.println(v);
   * });
   * }</pre>
   *
   * @param value the value
   * @param block the function block
   * @param <V>   the type of the value
   * @throws NullPointerException if {@code block} arg is null
   */
  static <V> void with(final V value,
                       final ThConsumer<? super V, ?> block) {
    blockArgNotNull(block);
    block.asUnchecked().accept(value);
  }

  /**
   * Performs given function block on given {@code int} value.
   * <pre>{@code
   * withInt(42, v -> {
   *   System.out.println(v);
   * });
   * }</pre>
   *
   * @param value the value
   * @param block the function block
   * @throws NullPointerException if {@code block} arg is null
   */
  static void withInt(final int value,
                      final ThIntConsumer<?> block) {
    blockArgNotNull(block);
    block.asUnchecked().accept(value);
  }

  /**
   * Performs given function block on given {@code long} value.
   * <pre>{@code
   * withLong(42L, v -> {
   *   System.out.println(v);
   * });
   * }</pre>
   *
   * @param value the value
   * @param block the function block
   * @throws NullPointerException if {@code block} arg is null
   */
  static void withLong(final long value,
                       final ThLongConsumer<?> block) {
    blockArgNotNull(block);
    block.asUnchecked().accept(value);
  }

  /**
   * Performs given function block on given {@code double} value.
   * <pre>{@code
   * withDouble(42.0, v -> {
   *   System.out.println(v);
   * });
   * }</pre>
   *
   * @param value the value
   * @param block the function block
   * @throws NullPointerException if {@code block} arg is null
   */
  static void withDouble(final double value,
                         final ThDoubleConsumer<?> block) {
    blockArgNotNull(block);
    block.asUnchecked().accept(value);
  }

  /**
   * Performs given function block on given {@link AutoCloseable} value and close this value.
   * <pre>{@code
   * with(new MyResource(), it -> System.out.println(it.getValue()));
   * }</pre>
   *
   * @param value the value
   * @param block the function block
   * @param <V>   the type of the value
   * @throws NullPointerException if {@code block} arg is null
   */
  static <V extends AutoCloseable> void withResource(final V value,
                                                     final ThConsumer<? super V, ?> block) {
    blockArgNotNull(block);
    ((ThBiConsumer<V, ThConsumer<? super V, ?>, Throwable>) (v, b) -> {
      try (final V resource = v) {
        b.accept(resource);
      }
    }).asUnchecked().accept(value, block);
  }

  /**
   * Performs given function block on given values.
   * <pre>{@code
   * with("Hi", "Mark", (v1, v2) -> {
   *   System.out.println(v1 + " " + v2);
   * });
   * }</pre>
   *
   * @param value1 the first value
   * @param value2 the second value
   * @param block  the function block
   * @param <V1>   the type of the first value
   * @param <V2>   the type of the second value
   * @throws NullPointerException if {@code block} arg is null
   */
  static <V1, V2> void with(final V1 value1,
                            final V2 value2,
                            final ThBiConsumer<? super V1, ? super V2, ?> block) {
    blockArgNotNull(block);
    block.asUnchecked().accept(value1, value2);
  }

  /**
   * Performs given function block on given values.
   * <pre>{@code
   * with("Oh", "Hi", "Mark", (v1, v2, v3) -> {
   *   System.out.println(v1 + " " + v2 + " " + v3);
   * });
   * }</pre>
   *
   * @param value1 the first value
   * @param value2 the second value
   * @param value3 the third value
   * @param block  the function block
   * @param <V1>   the type of the first value
   * @param <V2>   the type of the second value
   * @param <V3>   the type of the third value
   * @throws NullPointerException if {@code block} arg is null
   */
  static <V1, V2, V3> void with(final V1 value1,
                                final V2 value2,
                                final V3 value3,
                                final ThTriConsumer<? super V1, ? super V2, ? super V3, ?> block) {
    blockArgNotNull(block);
    block.asUnchecked().accept(value1, value2, value3);
  }

  /**
   * Returns {@link Opt} instance of given value, {@code null} is also considered as a value.
   * <pre>{@code
   * let(value).takeNonNull().takeUnless(it -> it.isEmpty()).takeIf(it -> it.length() < 100).letIt(it -> System.out.println(it));
   * }</pre>
   *
   * @param value the value
   * @param <V>   the type of the value
   * @return {@link Opt} instance of given value
   */
  static <V> Opt<V> let(final V value) {
    return Opt.of(value);
  }

  /**
   * Returns {@link Opt} instance of given value or empty {@link Opt} instance if given value is null. Equivalent to
   * calling a chain of methods: {@code let(value).takeNonNull()}.
   * <pre>{@code
   * letNonNull(value).takeUnless(it -> it.isEmpty()).takeIf(it -> it.length() < 100).letIt(it -> System.out.println(it));
   * }</pre>
   *
   * @param value the value
   * @param <V>   the type of the value
   * @return {@link Opt} instance of given value or empty {@link Opt} instance if given value is null
   */
  static <V> Opt<V> letNonNull(final V value) {
    return Opt.nonNullOf(value);
  }

  /**
   * Performs given function block and returns result.
   * <pre>{@code
   * String value = let(() -> {
   *   System.out.println("Hi");
   *   return "str";
   * });
   * }</pre>
   *
   * @param block the function block
   * @param <V>   the type of the result
   * @return result
   * @throws NullPointerException if {@code block} arg is null
   */
  static <V> V let(final ThSupplier<? extends V, ?> block) {
    blockArgNotNull(block);
    return block.asUnchecked().get();
  }

  /**
   * Performs given function block and returns {@code int}-valued result.
   * <pre>{@code
   * int value = letInt(() -> {
   *   System.out.println("Hi");
   *   return 42;
   * });
   * }</pre>
   *
   * @param block the function block
   * @return result
   * @throws NullPointerException if {@code block} arg is null
   */
  static int letInt(final ThIntSupplier<?> block) {
    blockArgNotNull(block);
    return block.asUnchecked().get();
  }

  /**
   * Performs given function block and returns {@code long}-valued result.
   * <pre>{@code
   * long value = letLong(() -> {
   *   System.out.println("Hi");
   *   return 42L;
   * });
   * }</pre>
   *
   * @param block the function block
   * @return result
   * @throws NullPointerException if {@code block} arg is null
   */
  static long letLong(final ThLongSupplier<?> block) {
    blockArgNotNull(block);
    return block.asUnchecked().get();
  }

  /**
   * Performs given function block and returns {@code double}-valued result.
   * <pre>{@code
   * double value = letDouble(() -> {
   *   System.out.println("Hi");
   *   return 42.0;
   * });
   * }</pre>
   *
   * @param block the function block
   * @return result
   * @throws NullPointerException if {@code block} arg is null
   */
  static double letDouble(final ThDoubleSupplier<?> block) {
    blockArgNotNull(block);
    return block.asUnchecked().get();
  }

  /**
   * Performs given function block on given value and returns this value.
   * <pre>{@code
   * String value = let("str", it -> {
   *   System.out.println(it);
   * });
   * }</pre>
   *
   * @param value the value
   * @param block the function block
   * @param <V>   the type of the value
   * @return given value
   * @throws NullPointerException if {@code block} arg is null
   */
  static <V> V let(final V value,
                   final ThConsumer<? super V, ?> block) {
    blockArgNotNull(block);
    block.asUnchecked().accept(value);
    return value;
  }

  /**
   * Performs given function block on given {@code int} value and returns this value.
   * <pre>{@code
   * int value = letInt(42, it -> {
   *   System.out.println(it);
   * });
   * }</pre>
   *
   * @param value the value
   * @param block the function block
   * @return given value
   * @throws NullPointerException if {@code block} arg is null
   */
  static int letInt(final int value,
                    final ThIntConsumer<?> block) {
    blockArgNotNull(block);
    block.asUnchecked().accept(value);
    return value;
  }

  /**
   * Performs given function block on given {@code long} value and returns this value.
   * <pre>{@code
   * long value = letLong(42L, it -> {
   *   System.out.println(it);
   * });
   * }</pre>
   *
   * @param value the value
   * @param block the function block
   * @return given value
   * @throws NullPointerException if {@code block} arg is null
   */
  static long letLong(final long value,
                      final ThLongConsumer<?> block) {
    blockArgNotNull(block);
    block.asUnchecked().accept(value);
    return value;
  }

  /**
   * Performs given function block on given {@code double} value and returns this value.
   * <pre>{@code
   * double value = letDouble(42.0, it -> {
   *   System.out.println(it);
   * });
   * }</pre>
   *
   * @param value the value
   * @param block the function block
   * @return given value
   * @throws NullPointerException if {@code block} arg is null
   */
  static double letDouble(final double value,
                          final ThDoubleConsumer<?> block) {
    blockArgNotNull(block);
    block.asUnchecked().accept(value);
    return value;
  }

  /**
   * Performs given function block recursively and returns result.
   * <pre>{@code
   * Integer value = letRec(10, (n, func) -> {
   *   if (n < 2) {
   *     return n;
   *   }
   *   return func.apply(n - 1) + func.apply(n - 2);
   * });
   * }</pre>
   *
   * @param initialValue the initial value
   * @param block        the function block
   * @param <V>          the type of the value
   * @return recursion result
   * @throws NullPointerException if {@code block} arg is null
   */
  static <V> V letRec(final V initialValue,
                      final ThBiFunction<? super V, ThFunction<V, V, Throwable>, ? extends V, ?> block) {
    blockArgNotNull(block);
    final AtomicReference<ThFunction<V, V, Throwable>> selfRef = new AtomicReference<>();
    selfRef.set(v -> block.apply(v, selfRef.get()));
    return block.asUnchecked().apply(initialValue, selfRef.get());
  }

  /**
   * Performs given function block recursively and returns {@code int}-valued result.
   * <pre>{@code
   * int value = letIntRec(10, (n, func) -> {
   *   if (n < 2) {
   *     return n;
   *   }
   *   return func.apply(n - 1) + func.apply(n - 2);
   * });
   * }</pre>
   *
   * @param initialValue the initial value
   * @param block        the function block
   * @return recursion result
   * @throws NullPointerException if {@code block} arg is null
   */
  static int letIntRec(final int initialValue,
                       final ThIntObjToIntFunction<ThIntToIntFunction<Throwable>, ?> block) {
    blockArgNotNull(block);
    final AtomicReference<ThIntToIntFunction<Throwable>> selfRef = new AtomicReference<>();
    selfRef.set(v -> block.apply(v, selfRef.get()));
    return block.asUnchecked().apply(initialValue, selfRef.get());
  }

  /**
   * Performs given function block recursively and returns {@code long}-valued result.
   * <pre>{@code
   * long value = letLongRec(10L, (n, func) -> {
   *   if (n < 2L) {
   *     return n;
   *   }
   *   return func.apply(n - 1L) + func.apply(n - 2L);
   * });
   * }</pre>
   *
   * @param initialValue the initial value
   * @param block        the function block
   * @return recursion result
   * @throws NullPointerException if {@code block} arg is null
   */
  static long letLongRec(final long initialValue,
                         final ThLongObjToLongFunction<ThLongToLongFunction<Throwable>, ?> block) {
    blockArgNotNull(block);
    final AtomicReference<ThLongToLongFunction<Throwable>> selfRef = new AtomicReference<>();
    selfRef.set(v -> block.apply(v, selfRef.get()));
    return block.asUnchecked().apply(initialValue, selfRef.get());
  }

  /**
   * Performs given function block recursively and returns {@code double}-valued result.
   * <pre>{@code
   * double value = letDoubleRec(10.0, (n, func) -> {
   *   if (n < 2.0) {
   *     return n;
   *   }
   *   return func.apply(n - 1.0) + func.apply(n - 2.0);
   * });
   * }</pre>
   *
   * @param initialValue the initial value
   * @param block        the function block
   * @return recursion result
   * @throws NullPointerException if {@code block} arg is null
   */
  static double letDoubleRec(final double initialValue,
                             final ThDoubleObjToDoubleFunction<ThDoubleToDoubleFunction<Throwable>, ?> block) {
    blockArgNotNull(block);
    final AtomicReference<ThDoubleToDoubleFunction<Throwable>> selfRef = new AtomicReference<>();
    selfRef.set(v -> block.apply(v, selfRef.get()));
    return block.asUnchecked().apply(initialValue, selfRef.get());
  }

  /**
   * Performs given function block on given value and returns result.
   * <pre>{@code
   * String value = letWith("Hi", it -> {
   *   System.out.println(it);
   *   return "Oh " + it + " Mark";
   * });
   * }</pre>
   *
   * @param value the value
   * @param block the function block
   * @param <V>   the type of the value
   * @param <R>   the type of the result
   * @return result
   * @throws NullPointerException if {@code block} arg is null
   */
  static <V, R> R letWith(final V value,
                          final ThFunction<? super V, ? extends R, ?> block) {
    blockArgNotNull(block);
    return block.asUnchecked().apply(value);
  }

  /**
   * Performs given function block on {@link AutoCloseable} value, close this value and returns result.
   * <pre>{@code
   * String value = letWith(new MyResource(), it -> it.getValue());
   * }</pre>
   *
   * @param value the value
   * @param block the function block
   * @param <V>   the type of the value
   * @param <R>   the type of the result
   * @return result
   * @throws NullPointerException if {@code block} arg is null
   */
  static <V extends AutoCloseable, R> R letWithResource(final V value,
                                                        final ThFunction<? super V, ? extends R, ?> block) {
    blockArgNotNull(block);
    return ((ThBiFunction<V, ThFunction<? super V, ? extends R, ?>, R, Throwable>) (v, b) -> {
      try (final V resource = v) {
        return b.apply(resource);
      }
    }).asUnchecked().apply(value, block);
  }

  /**
   * Performs given function block on given value and returns result.
   * <pre>{@code
   * String value = letWith("Oh", "Hi", (str1, str2) -> {
   *   System.out.println(str1);
   *   System.out.println(str2);
   *   return str1 + " " + str2 + " Mark";
   * });
   * }</pre>
   *
   * @param value1 the first value
   * @param value2 the second value
   * @param block  the function block
   * @param <V1>   the type of first the value
   * @param <V2>   the type of second the value
   * @param <R>    the type of the result
   * @return result
   * @throws NullPointerException if {@code block} arg is null
   */
  static <V1, V2, R> R letWith(final V1 value1,
                               final V2 value2,
                               final ThBiFunction<? super V1, ? super V2, ? extends R, ?> block) {
    blockArgNotNull(block);
    return block.asUnchecked().apply(value1, value2);
  }

  /**
   * Performs given function block on given value and returns result.
   * <pre>{@code
   * String value = letWith("Oh", "Hi", "Mark", (str1, str2, str3) -> {
   *   System.out.println(str1);
   *   System.out.println(str2);
   *   System.out.println(str3);
   *   return str1 + " " + str2 + " " + str3;
   * });
   * }</pre>
   *
   * @param value1 the first value
   * @param value2 the second value
   * @param value3 the third value
   * @param block  the function block
   * @param <V1>   the type of first the value
   * @param <V2>   the type of second the value
   * @param <V3>   the type of third the value
   * @param <R>    the type of the result
   * @return result
   * @throws NullPointerException if {@code block} arg is null
   */
  static <V1, V2, V3, R> R letWith(final V1 value1,
                                   final V2 value2,
                                   final V3 value3,
                                   final ThTriFunction<? super V1, ? super V2, ? super V3, ? extends R, ?> block) {
    blockArgNotNull(block);
    return block.asUnchecked().apply(value1, value2, value3);
  }
}
