/*
 * Copyright 2024-2025 Evgenii Plugatar
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

import com.plugatar.jkscope.function.Th2Consumer;
import com.plugatar.jkscope.function.Th2ConsumerIntObj;
import com.plugatar.jkscope.function.Th2Function;
import com.plugatar.jkscope.function.Th3Consumer;
import com.plugatar.jkscope.function.Th3ConsumerIntObj2;
import com.plugatar.jkscope.function.Th3Function;
import com.plugatar.jkscope.function.Th4Consumer;
import com.plugatar.jkscope.function.Th4ConsumerIntObj3;
import com.plugatar.jkscope.function.Th4Function;
import com.plugatar.jkscope.function.Th5Consumer;
import com.plugatar.jkscope.function.Th5ConsumerIntObj4;
import com.plugatar.jkscope.function.Th5Function;
import com.plugatar.jkscope.function.Th6Consumer;
import com.plugatar.jkscope.function.Th6ConsumerIntObj5;
import com.plugatar.jkscope.function.ThConsumer;
import com.plugatar.jkscope.function.ThConsumerInt;
import com.plugatar.jkscope.function.ThFunction;
import com.plugatar.jkscope.function.ThRunnable;
import com.plugatar.jkscope.function.ThSupplier;
import com.plugatar.jkscope.util.Cast;
import com.plugatar.jkscope.util.Throw;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * JKScope utility methods.
 * <p>
 * {@code with} methods:
 * <ul>
 * <li>{@link #with(ThRunnable)}</li>
 * <li>{@link #with(Object, ThConsumer)}</li>
 * <li>{@link #with(Object, Object, Th2Consumer)}</li>
 * <li>{@link #with(Object, Object, Object, Th3Consumer)}</li>
 * <li>{@link #with(Object, Object, Object, Object, Th4Consumer)}</li>
 * <li>{@link #with(Object, Object, Object, Object, Object, Th5Consumer)}</li>
 * </ul>
 * <p>
 * {@code let} methods:
 * <ul>
 * <li>{@link #let(ThSupplier)}</li>
 * <li>{@link #let(Object, ThFunction)}</li>
 * <li>{@link #let(Object, Object, Th2Function)}</li>
 * <li>{@link #let(Object, Object, Object, Th3Function)}</li>
 * <li>{@link #let(Object, Object, Object, Object, Th4Function)}</li>
 * <li>{@link #let(Object, Object, Object, Object, Object, Th5Function)}</li>
 * </ul>
 * <p>
 * {@code it} methods:
 * <ul>
 * <li>{@link #it(ThSupplier)}</li>
 * <li>{@link #it(Object, ThConsumer)}</li>
 * <li>{@link #it(Object, Object, Th2Consumer)}</li>
 * <li>{@link #it(Object, Object, Object, Th3Consumer)}</li>
 * <li>{@link #it(Object, Object, Object, Object, Th4Consumer)}</li>
 * <li>{@link #it(Object, Object, Object, Object, Object, Th5Consumer)}</li>
 * </ul>
 * <p>
 * {@code use} methods:
 * <ul>
 * <li>{@link #use(ThConsumer)}</li>
 * <li>{@link #use(AutoCloseable, ThConsumer)}</li>
 * <li>{@link #use(AutoCloseable, Th2Consumer)}</li>
 * <li>{@link #use(AutoCloseable, AutoCloseable, Th2Consumer)}</li>
 * <li>{@link #use(AutoCloseable, AutoCloseable, Th3Consumer)}</li>
 * <li>{@link #use(AutoCloseable, AutoCloseable, AutoCloseable, Th3Consumer)}</li>
 * <li>{@link #use(AutoCloseable, AutoCloseable, AutoCloseable, Th4Consumer)}</li>
 * <li>{@link #use(ThFunction)}</li>
 * <li>{@link #use(AutoCloseable, ThFunction)}</li>
 * <li>{@link #use(AutoCloseable, Th2Function)}</li>
 * <li>{@link #use(AutoCloseable, AutoCloseable, Th2Function)}</li>
 * <li>{@link #use(AutoCloseable, AutoCloseable, Th3Function)}</li>
 * <li>{@link #use(AutoCloseable, AutoCloseable, AutoCloseable, Th3Function)}</li>
 * <li>{@link #use(AutoCloseable, AutoCloseable, AutoCloseable, Th4Function)}</li>
 * </ul>
 * <p>
 * {@code repeat} methods:
 * <ul>
 * <li>{@link #repeat(int, ThRunnable)}</li>
 * <li>{@link #repeat(int, ThConsumerInt)}</li>
 * <li>{@link #repeat(int, Object, ThConsumer)}</li>
 * <li>{@link #repeat(int, Object, Th2ConsumerIntObj)}</li>
 * <li>{@link #repeat1(int, Object, ThConsumer)}</li>
 * <li>{@link #repeat1(int, Object, Th2ConsumerIntObj)}</li>
 * <li>{@link #repeat1(int, Object, Object, Th2Consumer)}</li>
 * <li>{@link #repeat1(int, Object, Object, Th3ConsumerIntObj2)}</li>
 * <li>{@link #repeat2(int, Object, Object, Th2Consumer)}</li>
 * <li>{@link #repeat2(int, Object, Object, Th3ConsumerIntObj2)}</li>
 * <li>{@link #repeat2(int, Object, Object, Object, Th3Consumer)}</li>
 * <li>{@link #repeat2(int, Object, Object, Object, Th4ConsumerIntObj3)}</li>
 * <li>{@link #repeat3(int, Object, Object, Object, Th3Consumer)}</li>
 * <li>{@link #repeat3(int, Object, Object, Object, Th4ConsumerIntObj3)}</li>
 * <li>{@link #repeat3(int, Object, Object, Object, Object, Th4Consumer)}</li>
 * <li>{@link #repeat3(int, Object, Object, Object, Object, Th5ConsumerIntObj4)}</li>
 * </ul>
 * <p>
 * {@code iterateOver} methods:
 * <ul>
 * <li>{@link #iterateOver(Object[], ThConsumer)}</li>
 * <li>{@link #iterateOver(Object[], Th2ConsumerIntObj)}</li>
 * <li>{@link #iterateOver(Object[], Object, Th2Consumer)}</li>
 * <li>{@link #iterateOver(Object[], Object, Th3ConsumerIntObj2)}</li>
 * <li>{@link #iterateOver(Iterable, ThConsumer)}</li>
 * <li>{@link #iterateOver(Iterable, Th2ConsumerIntObj)}</li>
 * <li>{@link #iterateOver(Iterable, Object, Th2Consumer)}</li>
 * <li>{@link #iterateOver(Iterable, Object, Th3ConsumerIntObj2)}</li>
 * <li>{@link #iterateOver(Iterator, ThConsumer)}</li>
 * <li>{@link #iterateOver(Iterator, Th2ConsumerIntObj)}</li>
 * <li>{@link #iterateOver(Iterator, Object, Th2Consumer)}</li>
 * <li>{@link #iterateOver(Iterator, Object, Th3ConsumerIntObj2)}</li>
 * <li>{@link #iterateOver(Map, Th2Consumer)}</li>
 * <li>{@link #iterateOver(Map, Th3ConsumerIntObj2)}</li>
 * <li>{@link #iterateOver(Map, Object, Th3Consumer)}</li>
 * <li>{@link #iterateOver(Map, Object, Th4ConsumerIntObj3)}</li>
 * </ul>
 * <p>
 * {@code iterate} methods:
 * <ul>
 * <li>{@link #iterate1(Object, Th2Consumer)}</li>
 * <li>{@link #iterate1(Object, Th3ConsumerIntObj2)}</li>
 * <li>{@link #iterate1(Object, Object, Th3Consumer)}</li>
 * <li>{@link #iterate1(Object, Object, Th4ConsumerIntObj3)}</li>
 * <li>{@link #iterate2(Object, Object, Th3Consumer)}</li>
 * <li>{@link #iterate2(Object, Object, Th4ConsumerIntObj3)}</li>
 * <li>{@link #iterate2(Object, Object, Object, Th4Consumer)}</li>
 * <li>{@link #iterate2(Object, Object, Object, Th5ConsumerIntObj4)}</li>
 * <li>{@link #iterate3(Object, Object, Object, Th4Consumer)}</li>
 * <li>{@link #iterate3(Object, Object, Object, Th5ConsumerIntObj4)}</li>
 * <li>{@link #iterate3(Object, Object, Object, Object, Th5Consumer)}</li>
 * <li>{@link #iterate3(Object, Object, Object, Object, Th6ConsumerIntObj5)}</li>
 * </ul>
 * <p>
 * {@code recur} methods:
 * <ul>
 * <li>{@link #recur(ThConsumer)}</li>
 * <li>{@link #recur(Th2Consumer)}</li>
 * <li>{@link #recur(Object, Th2Consumer)}</li>
 * <li>{@link #recur(Object, Th3Consumer)}</li>
 * <li>{@link #recur1(Object, Th2Consumer)}</li>
 * <li>{@link #recur1(Object, Th3Consumer)}</li>
 * <li>{@link #recur1(Object, Object, Th3Consumer)}</li>
 * <li>{@link #recur1(Object, Object, Th4Consumer)}</li>
 * <li>{@link #recur2(Object, Object, Th3Consumer)}</li>
 * <li>{@link #recur2(Object, Object, Th4Consumer)}</li>
 * <li>{@link #recur2(Object, Object, Object, Th4Consumer)}</li>
 * <li>{@link #recur2(Object, Object, Object, Th5Consumer)}</li>
 * <li>{@link #recur3(Object, Object, Object, Th4Consumer)}</li>
 * <li>{@link #recur3(Object, Object, Object, Th5Consumer)}</li>
 * <li>{@link #recur3(Object, Object, Object, Object, Th5Consumer)}</li>
 * <li>{@link #recur3(Object, Object, Object, Object, Th6Consumer)}</li>
 * <li>{@link #recur(ThFunction)}</li>
 * <li>{@link #recur(Th2Function)}</li>
 * <li>{@link #recur1(Object, Th2Function)}</li>
 * <li>{@link #recur1(Object, Th3Function)}</li>
 * <li>{@link #recur2(Object, Object, Th3Function)}</li>
 * <li>{@link #recur2(Object, Object, Th4Function)}</li>
 * <li>{@link #recur3(Object, Object, Object, Th4Function)}</li>
 * <li>{@link #recur3(Object, Object, Object, Th5Function)}</li>
 * </ul>
 * <p>
 * {@code lazy} methods:
 * <ul>
 * <li>{@link #lazy(ThSupplier)}</li>
 * <li>{@link #lazy(Object, ThSupplier)}</li>
 * <li>{@link #lazy(Lazy.ThreadSafetyMode, ThSupplier)}</li>
 * <li>{@link #lazyOf(Object)}</li>
 * </ul>
 */
public final class JKScope {

  /**
   * Utility class ctor.
   */
  private JKScope() {
  }

  /**
   * Performs given function block.
   *
   * <pre>{@code
   * with(() -> {
   *   System.out.println("Hi");
   * });
   * }</pre>
   *
   * @param block the function block
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static void with(final ThRunnable<?> block) {
    blockArgNotNull(block);
    ThRunnable.unchecked(block).run();
  }

  /**
   * Performs given function block on given value.
   *
   * <pre>{@code
   * with("Hello", v -> {
   *   System.out.println(v);
   * });
   * }</pre>
   *
   * @param value the value
   * @param block the function block
   * @param <V>   the type of the value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V> void with(final V value,
                              final ThConsumer<? super V, ?> block) {
    blockArgNotNull(block);
    ThConsumer.unchecked(block).accept(value);
  }

  /**
   * Performs given function block on given values.
   *
   * <p>Same as the {@link #with(Object, ThConsumer)} method but for two values.</p>
   *
   * @param value1 the first value
   * @param value2 the second value
   * @param block  the function block
   * @param <V1>   the type of the first value
   * @param <V2>   the type of the second value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V1, V2> void with(final V1 value1,
                                   final V2 value2,
                                   final Th2Consumer<? super V1, ? super V2, ?> block) {
    blockArgNotNull(block);
    Th2Consumer.unchecked(block).accept(value1, value2);
  }

  /**
   * Performs given function block on given values.
   *
   * <p>Same as the {@link #with(Object, ThConsumer)} method but for three values.</p>
   *
   * @param value1 the first value
   * @param value2 the second value
   * @param value3 the third value
   * @param block  the function block
   * @param <V1>   the type of the first value
   * @param <V2>   the type of the second value
   * @param <V3>   the type of the third value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V1, V2, V3> void with(final V1 value1,
                                       final V2 value2,
                                       final V3 value3,
                                       final Th3Consumer<? super V1, ? super V2, ? super V3, ?> block) {
    blockArgNotNull(block);
    Th3Consumer.unchecked(block).accept(value1, value2, value3);
  }

  /**
   * Performs given function block on given values.
   *
   * <p>Same as the {@link #with(Object, ThConsumer)} method but for four values.</p>
   *
   * @param value1 the first value
   * @param value2 the second value
   * @param value3 the third value
   * @param value4 the fourth value
   * @param block  the function block
   * @param <V1>   the type of the first value
   * @param <V2>   the type of the second value
   * @param <V3>   the type of the third value
   * @param <V4>   the type of the fourth value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V1, V2, V3, V4> void with(final V1 value1,
                                           final V2 value2,
                                           final V3 value3,
                                           final V4 value4,
                                           final Th4Consumer<? super V1, ? super V2, ? super V3, ? super V4, ?> block) {
    blockArgNotNull(block);
    Th4Consumer.unchecked(block).accept(value1, value2, value3, value4);
  }

  /**
   * Performs given function block on given values.
   *
   * <p>Same as the {@link #with(Object, ThConsumer)} method but for five values.</p>
   *
   * @param value1 the first value
   * @param value2 the second value
   * @param value3 the third value
   * @param value4 the fourth value
   * @param value5 the fifth value
   * @param block  the function block
   * @param <V1>   the type of the first value
   * @param <V2>   the type of the second value
   * @param <V3>   the type of the third value
   * @param <V4>   the type of the fourth value
   * @param <V5>   the type of the fifth value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V1, V2, V3, V4, V5> void with(final V1 value1,
                                               final V2 value2,
                                               final V3 value3,
                                               final V4 value4,
                                               final V5 value5,
                                               final Th5Consumer<? super V1, ? super V2, ? super V3, ? super V4, ? super V5, ?> block) {
    blockArgNotNull(block);
    Th5Consumer.unchecked(block).accept(value1, value2, value3, value4, value5);
  }

  /**
   * Performs given function block and returns result.
   *
   * <pre>{@code
   * let(() -> {
   *   return "Hello";
   * });
   * }</pre>
   *
   * @param block the function block
   * @param <R>   the type of the result
   * @return result
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <R> R let(final ThSupplier<? extends R, ?> block) {
    blockArgNotNull(block);
    return ThSupplier.unchecked(block).get();
  }

  /**
   * Performs given function block on given value and returns result.
   *
   * <pre>{@code
   * String result = let("Hello", v -> {
   *   return v + " world";
   * });
   * }</pre>
   *
   * @param value the value
   * @param block the function block
   * @param <V>   the type of the value
   * @param <R>   the type of the result
   * @return result
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V, R> R let(final V value,
                             final ThFunction<? super V, ? extends R, ?> block) {
    blockArgNotNull(block);
    return ThFunction.unchecked(block).apply(value);
  }

  /**
   * Performs given function block on given values and returns result.
   *
   * <p>Same as the {@link #let(Object, ThFunction)} method but for two values.</p>
   *
   * @param value1 the first value
   * @param value2 the second value
   * @param block  the function block
   * @param <V1>   the type of first the value
   * @param <V2>   the type of second the value
   * @param <R>    the type of the result
   * @return result
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V1, V2, R> R let(final V1 value1,
                                  final V2 value2,
                                  final Th2Function<? super V1, ? super V2, ? extends R, ?> block) {
    blockArgNotNull(block);
    return Th2Function.unchecked(block).apply(value1, value2);
  }

  /**
   * Performs given function block on given values and returns result.
   *
   * <p>Same as the {@link #let(Object, ThFunction)} method but for three values.</p>
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
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V1, V2, V3, R> R let(final V1 value1,
                                      final V2 value2,
                                      final V3 value3,
                                      final Th3Function<? super V1, ? super V2, ? super V3, ? extends R, ?> block) {
    blockArgNotNull(block);
    return Th3Function.unchecked(block).apply(value1, value2, value3);
  }

  /**
   * Performs given function block on given values and returns result.
   *
   * <p>Same as the {@link #let(Object, ThFunction)} method but for four values.</p>
   *
   * @param value1 the first value
   * @param value2 the second value
   * @param value3 the third value
   * @param value4 the fourth value
   * @param block  the function block
   * @param <V1>   the type of first the value
   * @param <V2>   the type of second the value
   * @param <V3>   the type of third the value
   * @param <V4>   the type of fourth the value
   * @param <R>    the type of the result
   * @return result
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V1, V2, V3, V4, R> R let(final V1 value1,
                                          final V2 value2,
                                          final V3 value3,
                                          final V4 value4,
                                          final Th4Function<? super V1, ? super V2, ? super V3, ? super V4, ? extends R, ?> block) {
    blockArgNotNull(block);
    return Th4Function.unchecked(block).apply(value1, value2, value3, value4);
  }

  /**
   * Performs given function block on given values and returns result.
   *
   * <p>Same as the {@link #let(Object, ThFunction)} method but for five values.</p>
   *
   * @param value1 the first value
   * @param value2 the second value
   * @param value3 the third value
   * @param value4 the fourth value
   * @param value5 the fifth value
   * @param block  the function block
   * @param <V1>   the type of first the value
   * @param <V2>   the type of second the value
   * @param <V3>   the type of third the value
   * @param <V4>   the type of fourth the value
   * @param <V5>   the type of fifth the value
   * @param <R>    the type of the result
   * @return result
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V1, V2, V3, V4, V5, R> R let(final V1 value1,
                                              final V2 value2,
                                              final V3 value3,
                                              final V4 value4,
                                              final V5 value5,
                                              final Th5Function<? super V1, ? super V2, ? super V3, ? super V4, ? super V5, ? extends R, ?> block) {
    blockArgNotNull(block);
    return Th5Function.unchecked(block).apply(value1, value2, value3, value4, value5);
  }

  /**
   * Performs given function block and returns result.
   *
   * <pre>{@code
   * String value = it(() -> {
   *   return "Hello";
   * });
   * }</pre>
   *
   * @param block the function block
   * @param <V>   the type of the result
   * @return result
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V> V it(final ThSupplier<? extends V, ?> block) {
    blockArgNotNull(block);
    return ThSupplier.unchecked(block).get();
  }

  /**
   * Performs given function block on given value and returns this value.
   *
   * <pre>{@code
   * String value = it("Hello", v -> {
   *   System.out.println(v);
   * });
   * }</pre>
   *
   * @param value the value
   * @param block the function block
   * @param <V>   the type of the value
   * @return given value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V> V it(final V value,
                         final ThConsumer<? super V, ?> block) {
    blockArgNotNull(block);
    ThConsumer.unchecked(block).accept(value);
    return value;
  }

  /**
   * Performs given function block on given values and returns first value.
   *
   * <pre>{@code
   * String value = it("Hello", 100, (v, a) -> {
   *   System.out.println(v);
   *   System.out.println(a);
   * });
   * }</pre>
   *
   * @param value           the value
   * @param additionalValue the additional value
   * @param block           the function block
   * @param <V>             the type of the value
   * @param <A>             the type of the additional value
   * @return given first value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V, A> V it(final V value,
                            final A additionalValue,
                            final Th2Consumer<? super V, ? super A, ?> block) {
    blockArgNotNull(block);
    Th2Consumer.unchecked(block).accept(value, additionalValue);
    return value;
  }

  /**
   * Performs given function block on given values and returns first value.
   *
   * <p>Same as the {@link #it(Object, Object, Th2Consumer)} method but for two additional values.</p>
   *
   * @param value            the value
   * @param additionalValue1 the first additional value
   * @param additionalValue2 the second additional value
   * @param block            the function block
   * @param <V>              the type of the value
   * @param <A1>             the type of the first additional value
   * @param <A2>             the type of the second additional value
   * @return given first value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V, A1, A2> V it(final V value,
                                 final A1 additionalValue1,
                                 final A2 additionalValue2,
                                 final Th3Consumer<? super V, ? super A1, ? super A2, ?> block) {
    blockArgNotNull(block);
    Th3Consumer.unchecked(block).accept(value, additionalValue1, additionalValue2);
    return value;
  }

  /**
   * Performs given function block on given values and returns first value.
   *
   * <p>Same as the {@link #it(Object, Object, Th2Consumer)} method but for three additional values.</p>
   *
   * @param value            the value
   * @param additionalValue1 the first additional value
   * @param additionalValue2 the second additional value
   * @param additionalValue3 the third additional value
   * @param block            the function block
   * @param <V>              the type of the value
   * @param <A1>             the type of the first additional value
   * @param <A2>             the type of the second additional value
   * @param <A3>             the type of the third additional value
   * @return given first value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V, A1, A2, A3> V it(final V value,
                                     final A1 additionalValue1,
                                     final A2 additionalValue2,
                                     final A3 additionalValue3,
                                     final Th4Consumer<? super V, ? super A1, ? super A2, ? super A3, ?> block) {
    blockArgNotNull(block);
    Th4Consumer.unchecked(block).accept(value, additionalValue1, additionalValue2, additionalValue3);
    return value;
  }

  /**
   * Performs given function block on given values and returns first value.
   *
   * <p>Same as the {@link #it(Object, Object, Th2Consumer)} method but for four additional values.</p>
   *
   * @param value            the value
   * @param additionalValue1 the first additional value
   * @param additionalValue2 the second additional value
   * @param additionalValue3 the third additional value
   * @param additionalValue4 the fourth additional value
   * @param block            the function block
   * @param <V>              the type of the value
   * @param <A1>             the type of the first additional value
   * @param <A2>             the type of the second additional value
   * @param <A3>             the type of the third additional value
   * @param <A4>             the type of the fourth additional value
   * @return given first value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V, A1, A2, A3, A4> V it(final V value,
                                         final A1 additionalValue1,
                                         final A2 additionalValue2,
                                         final A3 additionalValue3,
                                         final A4 additionalValue4,
                                         final Th5Consumer<? super V, ? super A1, ? super A2, ? super A3, ? super A4, ?> block) {
    blockArgNotNull(block);
    Th5Consumer.unchecked(block).accept(value, additionalValue1, additionalValue2, additionalValue3, additionalValue4);
    return value;
  }

  /**
   * Performs given function block and then closes all resources in ResourceDeque.
   *
   * <pre>{@code
   * use(resources -> {
   *   AutoCloseable ac = resources.push(new AutoCloseableImpl());
   *   ...
   * });
   * }</pre>
   *
   * @param block the function block
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static void use(final ThConsumer<? super ResourceDeque, ?> block) {
    blockArgNotNull(block);
    final ResourceDequeImpl deque = new ResourceDequeImpl();
    Throwable mainEx = null;
    try {
      block.accept(deque);
    } catch (final Throwable ex) {
      mainEx = ex;
    } finally {
      deque.close(mainEx);
    }
  }

  /**
   * Performs given function block on given {@link AutoCloseable} resource and then closes this resource.
   *
   * <pre>{@code
   * use(new AutoCloseableImpl(), ac -> {
   *   ...
   * });
   * }</pre>
   *
   * @param resource the resource
   * @param block    the function block
   * @param <V>      the type of the resource
   * @throws NullPointerException if {@code resource} arg is {@code null} or {@code block} arg is {@code null}
   */
  public static <V extends AutoCloseable> void use(final V resource,
                                                   final ThConsumer<? super V, ?> block) {
    resourceArgNotNull(resource);
    blockArgNotNull(block);
    Throwable mainEx = null;
    try {
      block.accept(resource);
    } catch (final Throwable ex) {
      mainEx = ex;
    } finally {
      mainEx = closeResource(resource, mainEx);
      if (mainEx != null) {
        Throw.unchecked(mainEx);
      }
    }
  }

  /**
   * Performs given function block on given {@link AutoCloseable} resource, adds this resource to ResourceDeque and then
   * closes all resources in ResourceDeque.
   *
   * <pre>{@code
   * use(new AutoCloseableImpl(), (ac1, resources) -> {
   *   AutoCloseable ac2 = resources.push(new AutoCloseableImpl());
   *   ...
   * });
   * }</pre>
   *
   * @param resource the resource
   * @param block    the function block
   * @param <V>      the type of the resource
   * @throws NullPointerException if {@code resource} arg is {@code null} or {@code block} arg is {@code null}
   */
  public static <V extends AutoCloseable> void use(final V resource,
                                                   final Th2Consumer<? super V, ? super ResourceDeque, ?> block) {
    resourceArgNotNull(resource);
    blockArgNotNull(block);
    final ResourceDequeImpl deque = new ResourceDequeImpl();
    deque.push(resource);
    Throwable mainEx = null;
    try {
      block.accept(resource, deque);
    } catch (final Throwable ex) {
      mainEx = ex;
    } finally {
      deque.close(mainEx);
    }
  }

  /**
   * Performs given function block on given {@link AutoCloseable} resources and then closes these resources in order:
   * {@code resource2}, {@code resource1}.
   *
   * <p>Same as the {@link #use(AutoCloseable, ThConsumer)} method but for two resources.</p>
   *
   * @param resource1 the first resource
   * @param resource2 the second resource
   * @param block     the function block
   * @param <V1>      the type of the first resource
   * @param <V2>      the type of the second resource
   * @throws NullPointerException if {@code resource1} arg is {@code null} or {@code resource2} arg is {@code null} or
   *                              {@code block} arg is {@code null}
   */
  public static <V1 extends AutoCloseable, V2 extends AutoCloseable> void use(final V1 resource1,
                                                                              final V2 resource2,
                                                                              final Th2Consumer<? super V1, ? super V2, ?> block) {
    resource1ArgNotNull(resource1);
    resource2ArgNotNull(resource2);
    blockArgNotNull(block);
    Throwable mainEx = null;
    try {
      block.accept(resource1, resource2);
    } catch (final Throwable ex) {
      mainEx = ex;
    } finally {
      mainEx = closeResource(resource1, closeResource(resource2, mainEx));
      if (mainEx != null) {
        Throw.unchecked(mainEx);
      }
    }
  }

  /**
   * Performs given function block on given {@link AutoCloseable}, adds these resources to ResourceDeque in order:
   * {@code resource2}, {@code resource1}, and then closes all resources in ResourceDeque.
   *
   * <p>Same as the {@link #use(AutoCloseable, Th2Consumer)} method but for two resources.</p>
   *
   * @param resource1 the first resource
   * @param resource2 the second resource
   * @param block     the function block
   * @param <V1>      the type of the first resource
   * @param <V2>      the type of the second resource
   * @throws NullPointerException if {@code resource1} arg is {@code null} or {@code resource2} arg is {@code null} or
   *                              {@code block} arg is {@code null}
   */
  public static <V1 extends AutoCloseable, V2 extends AutoCloseable> void use(final V1 resource1,
                                                                              final V2 resource2,
                                                                              final Th3Consumer<? super V1, ? super V2, ? super ResourceDeque, ?> block) {
    resource1ArgNotNull(resource1);
    resource2ArgNotNull(resource2);
    blockArgNotNull(block);
    final ResourceDequeImpl deque = new ResourceDequeImpl();
    deque.push(resource1);
    deque.push(resource2);
    Throwable mainEx = null;
    try {
      block.accept(resource1, resource2, deque);
    } catch (final Throwable ex) {
      mainEx = ex;
    } finally {
      deque.close(mainEx);
    }
  }

  /**
   * Performs given function block on given {@link AutoCloseable} resources and then closes these resources in order:
   * {@code resource3}, {@code resource2}, {@code resource1}.
   *
   * <p>Same as the {@link #use(AutoCloseable, ThConsumer)} method but for three resources.</p>
   *
   * @param resource1 the first resource
   * @param resource2 the second resource
   * @param resource3 the third resource
   * @param block     the function block
   * @param <V1>      the type of the first resource
   * @param <V2>      the type of the second resource
   * @param <V3>      the type of the third resource
   * @throws NullPointerException if {@code resource1} arg is {@code null} or {@code resource2} arg is {@code null} or
   *                              {@code resource3} arg is {@code null} or {@code block} arg is {@code null}
   */
  public static <V1 extends AutoCloseable, V2 extends AutoCloseable, V3 extends AutoCloseable> void use(final V1 resource1,
                                                                                                        final V2 resource2,
                                                                                                        final V3 resource3,
                                                                                                        final Th3Consumer<? super V1, ? super V2, ? super V3, ?> block) {
    resource1ArgNotNull(resource1);
    resource2ArgNotNull(resource2);
    resource3ArgNotNull(resource3);
    blockArgNotNull(block);
    Throwable mainEx = null;
    try {
      block.accept(resource1, resource2, resource3);
    } catch (final Throwable ex) {
      mainEx = ex;
    } finally {
      mainEx = closeResource(resource1, closeResource(resource2, closeResource(resource3, mainEx)));
      if (mainEx != null) {
        Throw.unchecked(mainEx);
      }
    }
  }

  /**
   * Performs given function block on given {@link AutoCloseable}, adds these resources to ResourceDeque in order:
   * {@code resource3}, {@code resource2}, {@code resource1}, and then closes all resources in ResourceDeque.
   *
   * <p>Same as the {@link #use(AutoCloseable, Th2Consumer)} method but for three resources.</p>
   *
   * @param resource1 the first resource
   * @param resource2 the second resource
   * @param resource3 the third resource
   * @param block     the function block
   * @param <V1>      the type of the first resource
   * @param <V2>      the type of the second resource
   * @param <V3>      the type of the third resource
   * @throws NullPointerException if {@code resource1} arg is {@code null} or {@code resource2} arg is {@code null} or
   *                              {@code resource3} arg is {@code null} or {@code block} arg is {@code null}
   */
  public static <V1 extends AutoCloseable, V2 extends AutoCloseable, V3 extends AutoCloseable> void use(final V1 resource1,
                                                                                                        final V2 resource2,
                                                                                                        final V3 resource3,
                                                                                                        final Th4Consumer<? super V1, ? super V2, ? super V3, ? super ResourceDeque, ?> block) {
    resource1ArgNotNull(resource1);
    resource2ArgNotNull(resource2);
    resource3ArgNotNull(resource3);
    blockArgNotNull(block);
    final ResourceDequeImpl deque = new ResourceDequeImpl();
    deque.push(resource1);
    deque.push(resource2);
    deque.push(resource3);
    Throwable mainEx = null;
    try {
      block.accept(resource1, resource2, resource3, deque);
    } catch (final Throwable ex) {
      mainEx = ex;
    } finally {
      deque.close(mainEx);
    }
  }

  /**
   * Performs given function block and then closes all resources in ResourceDeque and returns result.
   *
   * <pre>{@code
   * String result = use(resources -> {
   *   AutoCloseable ac = resources.push(new AutoCloseableImpl());
   *   ...
   *   return "value";
   * });
   * }</pre>
   *
   * @param block the function block
   * @param <R>   the type of the result
   * @return result
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <R> R use(final ThFunction<? super ResourceDeque, ? extends R, ?> block) {
    blockArgNotNull(block);
    final ResourceDequeImpl deque = new ResourceDequeImpl();
    Throwable mainEx = null;
    try {
      return block.apply(deque);
    } catch (final Throwable ex) {
      mainEx = ex;
    } finally {
      deque.close(mainEx);
    }
    throw new Error(); /* unreachable */
  }

  /**
   * Performs given function block on given {@link AutoCloseable} resource and then closes this resource and returns
   * result.
   *
   * <pre>{@code
   * String result = use(new AutoCloseableImpl(), ac -> {
   *   ...
   *   return "value";
   * });
   * }</pre>
   *
   * @param resource the resource
   * @param block    the function block
   * @param <V>      the type of the resource
   * @param <R>      the type of the result
   * @return result
   * @throws NullPointerException if {@code resource} arg is {@code null} or {@code block} arg is {@code null}
   */
  public static <V extends AutoCloseable, R> R use(final V resource,
                                                   final ThFunction<? super V, ? extends R, ?> block) {
    resourceArgNotNull(resource);
    blockArgNotNull(block);
    Throwable mainEx = null;
    try {
      return block.apply(resource);
    } catch (final Throwable ex) {
      mainEx = ex;
    } finally {
      mainEx = closeResource(resource, mainEx);
      if (mainEx != null) {
        Throw.unchecked(mainEx);
      }
    }
    throw new Error(); /* unreachable */
  }

  /**
   * Performs given function block on given {@link AutoCloseable} resource, adds this resource to ResourceDeque and then
   * closes all resources in ResourceDeque and returns result.
   *
   * <pre>{@code
   * String result = use(new AutoCloseableImpl(), (ac1, resources) -> {
   *   AutoCloseable ac2 = resources.push(new AutoCloseableImpl());
   *   ...
   *   return "value";
   * });
   * }</pre>
   *
   * @param resource the resource
   * @param block    the function block
   * @param <V>      the type of the resource
   * @param <R>      the type of the result
   * @return result
   * @throws NullPointerException if {@code resource} arg is {@code null} or {@code block} arg is {@code null}
   */
  public static <V extends AutoCloseable, R> R use(final V resource,
                                                   final Th2Function<? super V, ? super ResourceDeque, ? extends R, ?> block) {
    resourceArgNotNull(resource);
    blockArgNotNull(block);
    final ResourceDequeImpl deque = new ResourceDequeImpl();
    deque.push(resource);
    Throwable mainEx = null;
    try {
      return block.apply(resource, deque);
    } catch (final Throwable ex) {
      mainEx = ex;
    } finally {
      deque.close(mainEx);
    }
    throw new Error(); /* unreachable */
  }

  /**
   * Performs given function block on given {@link AutoCloseable} resources and then closes these resources in order:
   * {@code resource2}, {@code resource1}, and returns result.
   *
   * <p>Same as the {@link #use(AutoCloseable, ThFunction)} method but for two resources.</p>
   *
   * @param resource1 the first resource
   * @param resource2 the second resource
   * @param block     the function block
   * @param <V1>      the type of the first resource
   * @param <V2>      the type of the second resource
   * @param <R>       the type of the result
   * @return result
   * @throws NullPointerException if {@code resource1} arg is {@code null} or {@code resource2} arg is {@code null} or
   *                              {@code block} arg is {@code null}
   */
  public static <V1 extends AutoCloseable, V2 extends AutoCloseable, R> R use(final V1 resource1,
                                                                              final V2 resource2,
                                                                              final Th2Function<? super V1, ? super V2, ? extends R, ?> block) {
    resource1ArgNotNull(resource1);
    resource2ArgNotNull(resource2);
    blockArgNotNull(block);
    Throwable mainEx = null;
    try {
      return block.apply(resource1, resource2);
    } catch (final Throwable ex) {
      mainEx = ex;
    } finally {
      mainEx = closeResource(resource1, closeResource(resource2, mainEx));
      if (mainEx != null) {
        Throw.unchecked(mainEx);
      }
    }
    throw new Error(); /* unreachable */
  }

  /**
   * Performs given function block on given {@link AutoCloseable}, adds these resources to ResourceDeque in order:
   * {@code resource2}, {@code resource1}, and then closes all resources in ResourceDeque and returns result.
   *
   * <p>Same as the {@link #use(AutoCloseable, Th2Function)} method but for two resources.</p>
   *
   * @param resource1 the first resource
   * @param resource2 the second resource
   * @param block     the function block
   * @param <V1>      the type of the first resource
   * @param <V2>      the type of the second resource
   * @param <R>       the type of the result
   * @return result
   * @throws NullPointerException if {@code resource1} arg is {@code null} or {@code resource2} arg is {@code null} or
   *                              {@code block} arg is {@code null}
   */
  public static <V1 extends AutoCloseable, V2 extends AutoCloseable, R> R use(final V1 resource1,
                                                                              final V2 resource2,
                                                                              final Th3Function<? super V1, ? super V2, ? super ResourceDeque, ? extends R, ?> block) {
    resource1ArgNotNull(resource1);
    resource2ArgNotNull(resource2);
    blockArgNotNull(block);
    final ResourceDequeImpl deque = new ResourceDequeImpl();
    deque.push(resource1);
    deque.push(resource2);
    Throwable mainEx = null;
    try {
      return block.apply(resource1, resource2, deque);
    } catch (final Throwable ex) {
      mainEx = ex;
    } finally {
      deque.close(mainEx);
    }
    throw new Error(); /* unreachable */
  }

  /**
   * Performs given function block on given {@link AutoCloseable} resources and then closes these resources in order:
   * {@code resource3}, {@code resource2}, {@code resource1}, and returns result.
   *
   * <p>Same as the {@link #use(AutoCloseable, ThFunction)} method but for three resources.</p>
   *
   * @param resource1 the first resource
   * @param resource2 the second resource
   * @param resource3 the third resource
   * @param block     the function block
   * @param <V1>      the type of the first resource
   * @param <V2>      the type of the second resource
   * @param <V3>      the type of the third resource
   * @param <R>       the type of the result
   * @return result
   * @throws NullPointerException if {@code resource1} arg is {@code null} or {@code resource2} arg is {@code null} or
   *                              {@code resource3} arg is {@code null} or {@code block} arg is {@code null}
   */
  public static <V1 extends AutoCloseable, V2 extends AutoCloseable, V3 extends AutoCloseable, R> R use(final V1 resource1,
                                                                                                        final V2 resource2,
                                                                                                        final V3 resource3,
                                                                                                        final Th3Function<? super V1, ? super V2, ? super V3, ? extends R, ?> block) {
    resource1ArgNotNull(resource1);
    resource2ArgNotNull(resource2);
    resource3ArgNotNull(resource3);
    blockArgNotNull(block);
    Throwable mainEx = null;
    try {
      return block.apply(resource1, resource2, resource3);
    } catch (final Throwable ex) {
      mainEx = ex;
    } finally {
      mainEx = closeResource(resource1, closeResource(resource2, closeResource(resource3, mainEx)));
      if (mainEx != null) {
        Throw.unchecked(mainEx);
      }
    }
    throw new Error(); /* unreachable */
  }

  /**
   * Performs given function block on given {@link AutoCloseable}, adds these resources to ResourceDeque in order:
   * {@code resource3}, {@code resource2}, {@code resource1}, and then closes all resources in ResourceDeque and returns
   * result.
   *
   * <p>Same as the {@link #use(AutoCloseable, Th2Function)} method but for three resources.</p>
   *
   * @param resource1 the first resource
   * @param resource2 the second resource
   * @param resource3 the third resource
   * @param block     the function block
   * @param <V1>      the type of the first resource
   * @param <V2>      the type of the second resource
   * @param <V3>      the type of the third resource
   * @param <R>       the type of the result
   * @return result
   * @throws NullPointerException if {@code resource1} arg is {@code null} or {@code resource2} arg is {@code null} or
   *                              {@code resource3} arg is {@code null} or {@code block} arg is {@code null}
   */
  public static <V1 extends AutoCloseable, V2 extends AutoCloseable, V3 extends AutoCloseable, R> R use(final V1 resource1,
                                                                                                        final V2 resource2,
                                                                                                        final V3 resource3,
                                                                                                        final Th4Function<? super V1, ? super V2, ? super V3, ? super ResourceDeque, ? extends R, ?> block) {
    resource1ArgNotNull(resource1);
    resource2ArgNotNull(resource2);
    resource3ArgNotNull(resource3);
    blockArgNotNull(block);
    final ResourceDequeImpl deque = new ResourceDequeImpl();
    deque.push(resource1);
    deque.push(resource2);
    deque.push(resource3);
    Throwable mainEx = null;
    try {
      return block.apply(resource1, resource2, resource3, deque);
    } catch (final Throwable ex) {
      mainEx = ex;
    } finally {
      deque.close(mainEx);
    }
    throw new Error(); /* unreachable */
  }

  /**
   * Performs given function block specified number of times.
   *
   * <pre>{@code
   * repeat(10, () -> {
   *   System.out.println("Hi");
   * });
   * }</pre>
   *
   * @param times the number of times
   * @param block the function block
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static void repeat(final int times,
                            final ThRunnable<?> block) {
    blockArgNotNull(block);
    final ThRunnable<RuntimeException> unchecked = ThRunnable.unchecked(block);
    for (int idx = 0; idx < times; idx++) {
      unchecked.run();
    }
  }

  /**
   * Performs given function block specified number of times.
   *
   * <pre>{@code
   * repeat(10, idx -> {
   *   System.out.println("iteration: " + idx);
   * });
   * }</pre>
   *
   * @param times the number of times
   * @param block the function block
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static void repeat(final int times,
                            final ThConsumerInt<?> block) {
    blockArgNotNull(block);
    final ThConsumerInt<RuntimeException> unchecked = ThConsumerInt.unchecked(block);
    for (int idx = 0; idx < times; idx++) {
      unchecked.accept(idx);
    }
  }

  /**
   * Performs given function block specified number of times and returns accumulator value.
   *
   * <pre>{@code
   * List<String> result = repeat(10, new ArrayList<>(), acc -> {
   *   acc.add("value");
   * });
   * }</pre>
   *
   * @param times    the number of times
   * @param accValue the accumulator value
   * @param block    the function block
   * @param <A>      the type of the accumulator value
   * @return given accumulator value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <A> A repeat(final int times,
                             final A accValue,
                             final ThConsumer<? super A, ?> block) {
    blockArgNotNull(block);
    final ThConsumer<A, RuntimeException> unchecked = ThConsumer.unchecked(block);
    for (int idx = 0; idx < times; idx++) {
      unchecked.accept(accValue);
    }
    return accValue;
  }

  /**
   * Performs given function block specified number of times and returns accumulator value.
   *
   * <pre>{@code
   * List<String> result = repeat(10, new ArrayList<>(), (idx, acc) -> {
   *   acc.add("value " + idx);
   * });
   * }</pre>
   *
   * @param times    the number of times
   * @param accValue the accumulator value
   * @param block    the function block
   * @param <A>      the type of the accumulator value
   * @return given accumulator value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <A> A repeat(final int times,
                             final A accValue,
                             final Th2ConsumerIntObj<? super A, ?> block) {
    blockArgNotNull(block);
    final Th2ConsumerIntObj<A, RuntimeException> unchecked = Th2ConsumerIntObj.unchecked(block);
    for (int idx = 0; idx < times; idx++) {
      unchecked.accept(idx, accValue);
    }
    return accValue;
  }

  /**
   * Performs given function block specified number of times on value.
   *
   * <pre>{@code
   * repeat1(10, "value", value -> {
   *   System.out.println(value);
   * });
   * }</pre>
   *
   * @param times the number of times
   * @param value the value
   * @param block the function block
   * @param <V>   the type of the value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V> void repeat1(final int times,
                                 final V value,
                                 final ThConsumer<? super V, ?> block) {
    blockArgNotNull(block);
    final ThConsumer<V, RuntimeException> unchecked = ThConsumer.unchecked(block);
    for (int idx = 0; idx < times; idx++) {
      unchecked.accept(value);
    }
  }

  /**
   * Performs given function block specified number of times on value.
   *
   * <pre>{@code
   * repeat1(10, "value", (idx, value) -> {
   *   System.out.println(value);
   *   System.out.println("iteration: " + idx);
   * });
   * }</pre>
   *
   * @param times the number of times
   * @param value the value
   * @param block the function block
   * @param <V>   the type of the value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V> void repeat1(final int times,
                                 final V value,
                                 final Th2ConsumerIntObj<? super V, ?> block) {
    blockArgNotNull(block);
    final Th2ConsumerIntObj<V, RuntimeException> unchecked = Th2ConsumerIntObj.unchecked(block);
    for (int idx = 0; idx < times; idx++) {
      unchecked.accept(idx, value);
    }
  }

  /**
   * Performs given function block specified number of times on value and returns accumulator value.
   *
   * <pre>{@code
   * List<String> result = repeat1(10, "value", new ArrayList<>(), (value, acc) -> {
   *   acc.add(value);
   * });
   * }</pre>
   *
   * @param times    the number of times
   * @param value    the value
   * @param accValue the accumulator value
   * @param block    the function block
   * @param <V>      the type of the value
   * @param <A>      the type of the accumulator value
   * @return given accumulator value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V, A> A repeat1(final int times,
                                 final V value,
                                 final A accValue,
                                 final Th2Consumer<? super V, ? super A, ?> block) {
    blockArgNotNull(block);
    final Th2Consumer<V, A, RuntimeException> unchecked = Th2Consumer.unchecked(block);
    for (int idx = 0; idx < times; idx++) {
      unchecked.accept(value, accValue);
    }
    return accValue;
  }

  /**
   * Performs given function block specified number of times on value and returns accumulator value.
   *
   * <pre>{@code
   * List<String> result = repeat1(10, "value", new ArrayList<>(), (idx, value, acc) -> {
   *   acc.add(value + " " + idx);
   * });
   * }</pre>
   *
   * @param times    the number of times
   * @param value    the value
   * @param accValue the accumulator value
   * @param block    the function block
   * @param <V>      the type of the value
   * @param <A>      the type of the accumulator value
   * @return given accumulator value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V, A> A repeat1(final int times,
                                 final V value,
                                 final A accValue,
                                 final Th3ConsumerIntObj2<? super V, ? super A, ?> block) {
    blockArgNotNull(block);
    final Th3ConsumerIntObj2<V, A, RuntimeException> unchecked = Th3ConsumerIntObj2.unchecked(block);
    for (int idx = 0; idx < times; idx++) {
      unchecked.accept(idx, value, accValue);
    }
    return accValue;
  }

  /**
   * Performs given function block specified number of times on values.
   *
   * <p>Same as the {@link #repeat1(int, Object, ThConsumer)} method but for two values.</p>
   *
   * @param times  the number of times
   * @param value1 the first value
   * @param value2 the second value
   * @param block  the function block
   * @param <V1>   the type of the first value
   * @param <V2>   the type of the second value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V1, V2> void repeat2(final int times,
                                      final V1 value1,
                                      final V2 value2,
                                      final Th2Consumer<? super V1, ? super V2, ?> block) {
    blockArgNotNull(block);
    final Th2Consumer<V1, V2, RuntimeException> unchecked = Th2Consumer.unchecked(block);
    for (int idx = 0; idx < times; idx++) {
      unchecked.accept(value1, value2);
    }
  }

  /**
   * Performs given function block specified number of times on values.
   *
   * <p>Same as the {@link #repeat1(int, Object, Th2ConsumerIntObj)} method but for two values.</p>
   *
   * @param times  the number of times
   * @param value1 the first value
   * @param value2 the second value
   * @param block  the function block
   * @param <V1>   the type of the first value
   * @param <V2>   the type of the second value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V1, V2> void repeat2(final int times,
                                      final V1 value1,
                                      final V2 value2,
                                      final Th3ConsumerIntObj2<? super V1, ? super V2, ?> block) {
    blockArgNotNull(block);
    final Th3ConsumerIntObj2<V1, V2, RuntimeException> unchecked = Th3ConsumerIntObj2.unchecked(block);
    for (int idx = 0; idx < times; idx++) {
      unchecked.accept(idx, value1, value2);
    }
  }

  /**
   * Performs given function block specified number of times on values and returns accumulator value.
   *
   * <p>Same as the {@link #repeat1(int, Object, Object, Th2Consumer)} method but for two values.</p>
   *
   * @param times    the number of times
   * @param value1   the first value
   * @param value2   the second value
   * @param accValue the accumulator value
   * @param block    the function block
   * @param <V1>     the type of the first value
   * @param <V2>     the type of the second value
   * @param <A>      the type of the accumulator value
   * @return given accumulator value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V1, V2, A> A repeat2(final int times,
                                      final V1 value1,
                                      final V2 value2,
                                      final A accValue,
                                      final Th3Consumer<? super V1, ? super V2, ? super A, ?> block) {
    blockArgNotNull(block);
    final Th3Consumer<V1, V2, A, RuntimeException> unchecked = Th3Consumer.unchecked(block);
    for (int idx = 0; idx < times; idx++) {
      unchecked.accept(value1, value2, accValue);
    }
    return accValue;
  }

  /**
   * Performs given function block specified number of times on values and returns accumulator value.
   *
   * <p>Same as the {@link #repeat1(int, Object, Object, Th3ConsumerIntObj2)} method but for two values.</p>
   *
   * @param times    the number of times
   * @param value1   the first value
   * @param value2   the second value
   * @param accValue the accumulator value
   * @param block    the function block
   * @param <V1>     the type of the first value
   * @param <V2>     the type of the second value
   * @param <A>      the type of the accumulator value
   * @return given accumulator value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V1, V2, A> A repeat2(final int times,
                                      final V1 value1,
                                      final V2 value2,
                                      final A accValue,
                                      final Th4ConsumerIntObj3<? super V1, ? super V2, ? super A, ?> block) {
    blockArgNotNull(block);
    final Th4ConsumerIntObj3<V1, V2, A, RuntimeException> unchecked = Th4ConsumerIntObj3.unchecked(block);
    for (int idx = 0; idx < times; idx++) {
      unchecked.accept(idx, value1, value2, accValue);
    }
    return accValue;
  }

  /**
   * Performs given function block specified number of times on values.
   *
   * <p>Same as the {@link #repeat1(int, Object, ThConsumer)} method but for three values.</p>
   *
   * @param times  the number of times
   * @param value1 the first value
   * @param value2 the second value
   * @param value3 the third value
   * @param block  the function block
   * @param <V1>   the type of the first value
   * @param <V2>   the type of the second value
   * @param <V3>   the type of the third value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V1, V2, V3> void repeat3(final int times,
                                          final V1 value1,
                                          final V2 value2,
                                          final V3 value3,
                                          final Th3Consumer<? super V1, ? super V2, ? super V3, ?> block) {
    blockArgNotNull(block);
    final Th3Consumer<V1, V2, V3, RuntimeException> unchecked = Th3Consumer.unchecked(block);
    for (int idx = 0; idx < times; idx++) {
      unchecked.accept(value1, value2, value3);
    }
  }

  /**
   * Performs given function block specified number of times on values.
   *
   * <p>Same as the {@link #repeat1(int, Object, Th2ConsumerIntObj)} method but for three values.</p>
   *
   * @param times  the number of times
   * @param value1 the first value
   * @param value2 the second value
   * @param value3 the third value
   * @param block  the function block
   * @param <V1>   the type of the first value
   * @param <V2>   the type of the second value
   * @param <V3>   the type of the third value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V1, V2, V3> void repeat3(final int times,
                                          final V1 value1,
                                          final V2 value2,
                                          final V3 value3,
                                          final Th4ConsumerIntObj3<? super V1, ? super V2, ? super V3, ?> block) {
    blockArgNotNull(block);
    final Th4ConsumerIntObj3<V1, V2, V3, RuntimeException> unchecked = Th4ConsumerIntObj3.unchecked(block);
    for (int idx = 0; idx < times; idx++) {
      unchecked.accept(idx, value1, value2, value3);
    }
  }

  /**
   * Performs given function block specified number of times on values and returns accumulator value.
   *
   * <p>Same as the {@link #repeat1(int, Object, Object, Th2Consumer)} method but for three values.</p>
   *
   * @param times    the number of times
   * @param value1   the first value
   * @param value2   the second value
   * @param value3   the third value
   * @param accValue the accumulator value
   * @param block    the function block
   * @param <V1>     the type of the first value
   * @param <V2>     the type of the second value
   * @param <V3>     the type of the third value
   * @param <A>      the type of the accumulator value
   * @return given accumulator value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V1, V2, V3, A> A repeat3(final int times,
                                          final V1 value1,
                                          final V2 value2,
                                          final V3 value3,
                                          final A accValue,
                                          final Th4Consumer<? super V1, ? super V2, ? super V3, ? super A, ?> block) {
    blockArgNotNull(block);
    final Th4Consumer<V1, V2, V3, A, RuntimeException> unchecked = Th4Consumer.unchecked(block);
    for (int idx = 0; idx < times; idx++) {
      unchecked.accept(value1, value2, value3, accValue);
    }
    return accValue;
  }

  /**
   * Performs given function block specified number of times on values and returns accumulator value.
   *
   * <p>Same as the {@link #repeat1(int, Object, Object, Th3ConsumerIntObj2)} method but for three
   * values.</p>
   *
   * @param times    the number of times
   * @param value1   the first value
   * @param value2   the second value
   * @param value3   the third value
   * @param accValue the accumulator value
   * @param block    the function block
   * @param <V1>     the type of the first value
   * @param <V2>     the type of the second value
   * @param <V3>     the type of the third value
   * @param <A>      the type of the accumulator value
   * @return given accumulator value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V1, V2, V3, A> A repeat3(final int times,
                                          final V1 value1,
                                          final V2 value2,
                                          final V3 value3,
                                          final A accValue,
                                          final Th5ConsumerIntObj4<? super V1, ? super V2, ? super V3, ? super A, ?> block) {
    blockArgNotNull(block);
    final Th5ConsumerIntObj4<V1, V2, V3, A, RuntimeException> unchecked = Th5ConsumerIntObj4.unchecked(block);
    for (int idx = 0; idx < times; idx++) {
      unchecked.accept(idx, value1, value2, value3, accValue);
    }
    return accValue;
  }

  /**
   * Iterate over elements of given array.
   *
   * <pre>{@code
   * String[] array = {"a", "b", "c"};
   * iterateOver(array, element -> {
   *   System.out.println("element: " + element);
   * });
   * }</pre>
   *
   * @param array the array
   * @param block the function block
   * @param <V>   the type of the array elements
   * @throws NullPointerException if {@code array} arg is {@code null} or {@code block} arg is {@code null}
   */
  public static <V> void iterateOver(final V[] array,
                                     final ThConsumer<? super V, ?> block) {
    arrayArgNotNull(array);
    blockArgNotNull(block);
    final ThConsumer<V, RuntimeException> unchecked = ThConsumer.unchecked(block);
    for (final V value : array) {
      unchecked.accept(value);
    }
  }

  /**
   * Iterate over elements and indices of given array.
   *
   * <pre>{@code
   * String[] array = {"a", "b", "c"};
   * iterateOver(array, (idx, element) -> {
   *   System.out.println("element " + idx + ": " + element);
   * });
   * }</pre>
   *
   * @param array the array
   * @param block the function block
   * @param <V>   the type of the array elements
   * @throws NullPointerException if {@code array} arg is {@code null} or {@code block} arg is {@code null}
   */
  public static <V> void iterateOver(final V[] array,
                                     final Th2ConsumerIntObj<? super V, ?> block) {
    arrayArgNotNull(array);
    blockArgNotNull(block);
    final Th2ConsumerIntObj<V, RuntimeException> unchecked = Th2ConsumerIntObj.unchecked(block);
    for (int idx = 0; idx < array.length; idx++) {
      unchecked.accept(idx, array[idx]);
    }
  }

  /**
   * Iterate over elements of given array and returns accumulator value.
   *
   * <pre>{@code
   * String[] array = {"a", "b", "c"};
   * List<String> result = iterateOver(array, new ArrayList<>(), (element, acc) -> {
   *   acc.add(element);
   * });
   * }</pre>
   *
   * @param array    the array
   * @param accValue the accumulator value
   * @param block    the function block
   * @param <V>      the type of the array elements
   * @param <A>      the type of the accumulator value
   * @return given accumulator value
   * @throws NullPointerException if {@code array} arg is {@code null} or {@code block} arg is {@code null}
   */
  public static <V, A> A iterateOver(final V[] array,
                                     final A accValue,
                                     final Th2Consumer<? super V, ? super A, ?> block) {
    arrayArgNotNull(array);
    blockArgNotNull(block);
    final Th2Consumer<V, A, RuntimeException> unchecked = Th2Consumer.unchecked(block);
    for (final V value : array) {
      unchecked.accept(value, accValue);
    }
    return accValue;
  }

  /**
   * Iterate over elements and indices of given array and returns accumulator value.
   *
   * <pre>{@code
   * String[] array = {"a", "b", "c"};
   * List<String> result = iterateOver(array, new ArrayList<>(), (idx, element, acc) -> {
   *   acc.add(idx + " " + element);
   * });
   * }</pre>
   *
   * @param array    the array
   * @param accValue the accumulator value
   * @param block    the function block
   * @param <V>      the type of the array elements
   * @param <A>      the type of the accumulator value
   * @return given accumulator value
   * @throws NullPointerException if {@code array} arg is {@code null} or {@code block} arg is {@code null}
   */
  public static <V, A> A iterateOver(final V[] array,
                                     final A accValue,
                                     final Th3ConsumerIntObj2<? super V, ? super A, ?> block) {
    arrayArgNotNull(array);
    blockArgNotNull(block);
    final Th3ConsumerIntObj2<V, A, RuntimeException> unchecked = Th3ConsumerIntObj2.unchecked(block);
    for (int idx = 0; idx < array.length; idx++) {
      unchecked.accept(idx, array[idx], accValue);
    }
    return accValue;
  }

  /**
   * Iterate over elements of given iterable.
   *
   * <pre>{@code
   * List<String> iterable = List.of("a", "b", "c");
   * iterateOver(iterable, element -> {
   *   System.out.println("element: " + element);
   * });
   * }</pre>
   *
   * @param iterable the iterable
   * @param block    the function block
   * @param <V>      the type of the iterable elements
   * @throws NullPointerException if {@code iterable} arg is {@code null} or {@code block} arg is {@code null}
   */
  public static <V> void iterateOver(final Iterable<? extends V> iterable,
                                     final ThConsumer<? super V, ?> block) {
    iterableArgNotNull(iterable);
    iterateOver(iterable.iterator(), block);
  }

  /**
   * Iterate over elements and indices of given iterable.
   *
   * <pre>{@code
   * List<String> iterable = List.of("a", "b", "c");
   * iterateOver(iterable, (idx, element) -> {
   *   System.out.println("element " + idx + ": " + element);
   * });
   * }</pre>
   *
   * @param iterable the iterable
   * @param block    the function block
   * @param <V>      the type of the iterable elements
   * @throws NullPointerException if {@code iterable} arg is {@code null} or {@code block} arg is {@code null}
   */
  public static <V> void iterateOver(final Iterable<? extends V> iterable,
                                     final Th2ConsumerIntObj<? super V, ?> block) {
    iterableArgNotNull(iterable);
    iterateOver(iterable.iterator(), block);
  }

  /**
   * Iterate over elements of given iterable and returns accumulator value.
   *
   * <pre>{@code
   * List<String> iterable = List.of("a", "b", "c");
   * List<String> result = iterateOver(iterable, new ArrayList<>(), (element, acc) -> {
   *   acc.add(element);
   * });
   * }</pre>
   *
   * @param iterable the iterable
   * @param accValue the accumulator value
   * @param block    the function block
   * @param <V>      the type of the iterable elements
   * @param <A>      the type of the accumulator value
   * @return given accumulator value
   * @throws NullPointerException if {@code iterable} arg is {@code null} or {@code block} arg is {@code null}
   */
  public static <V, A> A iterateOver(final Iterable<? extends V> iterable,
                                     final A accValue,
                                     final Th2Consumer<? super V, ? super A, ?> block) {
    iterableArgNotNull(iterable);
    return iterateOver(iterable.iterator(), accValue, block);
  }

  /**
   * Iterate over elements and indices of given iterable and returns accumulator value.
   *
   * <pre>{@code
   * List<String> iterable = List.of("a", "b", "c");
   * List<String> result = iterateOver(iterable, new ArrayList<>(), (idx, element, acc) -> {
   *   acc.add(idx + " " + element);
   * });
   * }</pre>
   *
   * @param iterable the iterable
   * @param accValue the accumulator value
   * @param block    the function block
   * @param <V>      the type of the iterable elements
   * @param <A>      the type of the accumulator value
   * @return given accumulator value
   * @throws NullPointerException if {@code iterable} arg is {@code null} or {@code block} arg is {@code null}
   */
  public static <V, A> A iterateOver(final Iterable<? extends V> iterable,
                                     final A accValue,
                                     final Th3ConsumerIntObj2<? super V, ? super A, ?> block) {
    iterableArgNotNull(iterable);
    return iterateOver(iterable.iterator(), accValue, block);
  }

  /**
   * Iterate over elements of given iterator.
   *
   * <pre>{@code
   * Iterator<String> iterator = List.of("a", "b", "c").iterator();
   * iterateOver(iterator, element -> {
   *   System.out.println("element: " + element);
   * });
   * }</pre>
   *
   * @param iterator the iterator
   * @param block    the function block
   * @param <V>      the type of the iterator elements
   * @throws NullPointerException if {@code iterator} arg is {@code null} or {@code block} arg is {@code null}
   */
  public static <V> void iterateOver(final Iterator<? extends V> iterator,
                                     final ThConsumer<? super V, ?> block) {
    iteratorArgNotNull(iterator);
    blockArgNotNull(block);
    final ThConsumer<V, RuntimeException> unchecked = ThConsumer.unchecked(block);
    while (iterator.hasNext()) {
      unchecked.accept(iterator.next());
    }
  }

  /**
   * Iterate over elements and indices of given iterator.
   *
   * <pre>{@code
   * Iterator<String> iterator = List.of("a", "b", "c").iterator();
   * iterateOver(iterator, (idx, element) -> {
   *   System.out.println("element " + idx + ": " + element);
   * });
   * }</pre>
   *
   * @param iterator the iterator
   * @param block    the function block
   * @param <V>      the type of the iterator elements
   * @throws NullPointerException if {@code iterator} arg is {@code null} or {@code block} arg is {@code null}
   */
  public static <V> void iterateOver(final Iterator<? extends V> iterator,
                                     final Th2ConsumerIntObj<? super V, ?> block) {
    iteratorArgNotNull(iterator);
    blockArgNotNull(block);
    final Th2ConsumerIntObj<V, RuntimeException> unchecked = Th2ConsumerIntObj.unchecked(block);
    for (int idx = 0; iterator.hasNext(); idx++) {
      unchecked.accept(idx, iterator.next());
    }
  }

  /**
   * Iterate over elements of given iterator and returns accumulator value.
   *
   * <pre>{@code
   * Iterator<String> iterator = List.of("a", "b", "c").iterator();
   * List<String> result = iterateOver(iterator, new ArrayList<>(), (element, acc) -> {
   *   acc.add(element);
   * });
   * }</pre>
   *
   * @param iterator the iterator
   * @param accValue the accumulator value
   * @param block    the function block
   * @param <V>      the type of the iterator elements
   * @param <A>      the type of the accumulator value
   * @return given accumulator value
   * @throws NullPointerException if {@code iterable} arg is {@code null} or {@code block} arg is {@code null}
   */
  public static <V, A> A iterateOver(final Iterator<? extends V> iterator,
                                     final A accValue,
                                     final Th2Consumer<? super V, ? super A, ?> block) {
    iteratorArgNotNull(iterator);
    blockArgNotNull(block);
    final Th2Consumer<V, A, RuntimeException> unchecked = Th2Consumer.unchecked(block);
    while (iterator.hasNext()) {
      unchecked.accept(iterator.next(), accValue);
    }
    return accValue;
  }

  /**
   * Iterate over elements and indices of given iterator and returns accumulator value.
   *
   * <pre>{@code
   * Iterator<String> iterator = List.of("a", "b", "c").iterator();
   * List<String> result = iterateOver(iterator, new ArrayList<>(), (idx, element, acc) -> {
   *   acc.add(idx + " " + element);
   * });
   * }</pre>
   *
   * @param iterator the iterator
   * @param accValue the accumulator value
   * @param block    the function block
   * @param <V>      the type of the iterator elements
   * @param <A>      the type of the accumulator value
   * @return given accumulator value
   * @throws NullPointerException if {@code iterable} arg is {@code null} or {@code block} arg is {@code null}
   */
  public static <V, A> A iterateOver(final Iterator<? extends V> iterator,
                                     final A accValue,
                                     final Th3ConsumerIntObj2<? super V, ? super A, ?> block) {
    iteratorArgNotNull(iterator);
    blockArgNotNull(block);
    final Th3ConsumerIntObj2<V, A, RuntimeException> unchecked = Th3ConsumerIntObj2.unchecked(block);
    for (int idx = 0; iterator.hasNext(); idx++) {
      unchecked.accept(idx, iterator.next(), accValue);
    }
    return accValue;
  }

  /**
   * Iterate over keys and values of given map.
   *
   * <pre>{@code
   * Map<Integer, String> map = Map.of(1, "a", 2, "b", 3, "c");
   * iterateOver(map, (key, value) -> {
   *   System.out.println("key: " + key + ", value: " + value);
   * });
   * }</pre>
   *
   * @param map   the map
   * @param block the function block
   * @param <K>   the type of the map keys
   * @param <V>   the type of the map values
   * @throws NullPointerException if {@code map} arg is {@code null} or {@code block} arg is {@code null}
   */
  public static <K, V> void iterateOver(final Map<? extends K, ? extends V> map,
                                        final Th2Consumer<? super K, ? super V, ?> block) {
    mapArgNotNull(map);
    blockArgNotNull(block);
    final Th2Consumer<K, V, RuntimeException> unchecked = Th2Consumer.unchecked(block);
    for (final Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
      unchecked.accept(entry.getKey(), entry.getValue());
    }
  }

  /**
   * Iterate over keys, values and indices of given map.
   *
   * <pre>{@code
   * Map<Integer, String> map = Map.of(1, "a", 2, "b", 3, "c");
   * iterateOver(map, (idx, key, value) -> {
   *   System.out.println("idx: " + idx + ", key: " + key + ", value: " + value);
   * });
   * }</pre>
   *
   * @param map   the map
   * @param block the function block
   * @param <K>   the type of the map keys
   * @param <V>   the type of the map values
   * @throws NullPointerException if {@code map} arg is {@code null} or {@code block} arg is {@code null}
   */
  public static <K, V> void iterateOver(final Map<? extends K, ? extends V> map,
                                        final Th3ConsumerIntObj2<? super K, ? super V, ?> block) {
    mapArgNotNull(map);
    blockArgNotNull(block);
    final Th3ConsumerIntObj2<K, V, RuntimeException> unchecked = Th3ConsumerIntObj2.unchecked(block);
    int idx = 0;
    for (final Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
      unchecked.accept(idx++, entry.getKey(), entry.getValue());
    }
  }

  /**
   * Iterate over keys and values of given map and returns accumulator value.
   *
   * <pre>{@code
   * Map<Integer, String> map = Map.of(1, "a", 2, "b", 3, "c");
   * List<String> result = iterateOver(map, new ArrayList<>(), (key, value, acc) -> {
   *   acc.add("key: " + key + ", value: " + value);
   * });
   * }</pre>
   *
   * @param map      the map
   * @param accValue the accumulator value
   * @param block    the function block
   * @param <K>      the type of the map keys
   * @param <V>      the type of the map values
   * @param <A>      the type of the accumulator value
   * @return given accumulator value
   * @throws NullPointerException if {@code map} arg is {@code null} or {@code block} arg is {@code null}
   */
  public static <K, V, A> A iterateOver(final Map<? extends K, ? extends V> map,
                                        final A accValue,
                                        final Th3Consumer<? super K, ? super V, ? super A, ?> block) {
    mapArgNotNull(map);
    blockArgNotNull(block);
    final Th3Consumer<K, V, A, RuntimeException> unchecked = Th3Consumer.unchecked(block);
    for (final Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
      unchecked.accept(entry.getKey(), entry.getValue(), accValue);
    }
    return accValue;
  }

  /**
   * Iterate over keys, values and indices of given map and returns accumulator value.
   *
   * <pre>{@code
   * Map<Integer, String> map = Map.of(1, "a", 2, "b", 3, "c");
   * List<String> result = iterateOver(map, new ArrayList<>(), (idx, key, value, acc) -> {
   *   acc.add("idx: " + idx + ", key: " + key + ", value: " + value);
   * });
   * }</pre>
   *
   * @param map      the map
   * @param accValue the accumulator value
   * @param block    the function block
   * @param <K>      the type of the map keys
   * @param <V>      the type of the map values
   * @param <A>      the type of the accumulator value
   * @return given accumulator value
   * @throws NullPointerException if {@code map} arg is {@code null} or {@code block} arg is {@code null}
   */
  public static <K, V, A> A iterateOver(final Map<? extends K, ? extends V> map,
                                        final A accValue,
                                        final Th4ConsumerIntObj3<? super K, ? super V, ? super A, ?> block) {
    mapArgNotNull(map);
    blockArgNotNull(block);
    final Th4ConsumerIntObj3<K, V, A, RuntimeException> unchecked = Th4ConsumerIntObj3.unchecked(block);
    int idx = 0;
    for (final Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
      unchecked.accept(idx++, entry.getKey(), entry.getValue(), accValue);
    }
    return accValue;
  }

  /**
   * Iterate over values starting from the initial value.
   *
   * <pre>{@code
   * iterate1(5, (value, nextValues) -> {
   *   if (value < 10) {
   *     System.out.println(value);
   *     nextValues.push(value + 1);
   *   }
   * });
   * }</pre>
   *
   * @param initValue the initial value
   * @param block     the function block
   * @param <V>       the type of the initial value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V> void iterate1(final V initValue,
                                  final Th2Consumer<? super V, ? super ItrDeque<V>, ?> block) {
    blockArgNotNull(block);
    final Th2Consumer<V, ItrDeque<V>, RuntimeException> unchecked = Th2Consumer.unchecked(block);
    final ItrDequeImpl<V> itrDeque = new ItrDequeImpl<>();
    unchecked.accept(initValue, itrDeque);
    while (!itrDeque.isEmpty()) {
      unchecked.accept(unwrapNull(itrDeque.deque.poll()), itrDeque);
    }
  }

  /**
   * Iterate over values and indices starting from the initial value.
   *
   * <pre>{@code
   * iterate1(5, (idx, value, nextValues) -> {
   *   if (idx < 100 && value < 10) {
   *     System.out.println(value);
   *     nextValues.push(value + 1);
   *   }
   * });
   * }</pre>
   *
   * @param initValue the initial value
   * @param block     the function block
   * @param <V>       the type of the initial value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V> void iterate1(final V initValue,
                                  final Th3ConsumerIntObj2<? super V, ? super ItrDeque<V>, ?> block) {
    blockArgNotNull(block);
    final Th3ConsumerIntObj2<V, ItrDeque<V>, RuntimeException> unchecked = Th3ConsumerIntObj2.unchecked(block);
    final ItrDequeImpl<V> itrDeque = new ItrDequeImpl<>();
    int idx = 0;
    unchecked.accept(idx++, initValue, itrDeque);
    while (!itrDeque.isEmpty()) {
      unchecked.accept(idx++, unwrapNull(itrDeque.deque.poll()), itrDeque);
    }
  }

  /**
   * Iterate over values starting from the initial value and returns given accumulator value.
   *
   * <pre>{@code
   * List<Integer> result = iterate1(5, new ArrayList<>(), (value, acc, nextValues) -> {
   *   if (value < 10) {
   *     acc.add(value);
   *     nextValues.push(value + 1);
   *   }
   * });
   * }</pre>
   *
   * @param initValue the initial value
   * @param accValue  the accumulator value
   * @param block     the function block
   * @param <V>       the type of the initial value
   * @param <A>       the type of the accumulator value
   * @return given accumulator value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V, A> A iterate1(final V initValue,
                                  final A accValue,
                                  final Th3Consumer<? super V, ? super A, ? super ItrDeque<V>, ?> block) {
    blockArgNotNull(block);
    final Th3Consumer<V, A, ItrDeque<V>, RuntimeException> unchecked = Th3Consumer.unchecked(block);
    final ItrDequeImpl<V> itrDeque = new ItrDequeImpl<>();
    unchecked.accept(initValue, accValue, itrDeque);
    while (!itrDeque.isEmpty()) {
      unchecked.accept(unwrapNull(itrDeque.deque.poll()), accValue, itrDeque);
    }
    return accValue;
  }

  /**
   * Iterate over values and indices starting from the initial value and returns given accumulator value.
   *
   * <pre>{@code
   * List<Integer> result = iterate1(5, new ArrayList<>(), (idx, value, acc, nextValues) -> {
   *   if (idx < 100 && value < 10) {
   *     acc.add(value);
   *     nextValues.push(value + 1);
   *   }
   * });
   * }</pre>
   *
   * @param initValue the initial value
   * @param accValue  the accumulator value
   * @param block     the function block
   * @param <V>       the type of the initial value
   * @param <A>       the type of the accumulator value
   * @return given accumulator value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V, A> A iterate1(final V initValue,
                                  final A accValue,
                                  final Th4ConsumerIntObj3<? super V, ? super A, ? super ItrDeque<V>, ?> block) {
    blockArgNotNull(block);
    final Th4ConsumerIntObj3<V, A, ItrDeque<V>, RuntimeException> unchecked = Th4ConsumerIntObj3.unchecked(block);
    final ItrDequeImpl<V> itrDeque = new ItrDequeImpl<>();
    int idx = 0;
    unchecked.accept(idx++, initValue, accValue, itrDeque);
    while (!itrDeque.isEmpty()) {
      unchecked.accept(idx++, unwrapNull(itrDeque.deque.poll()), accValue, itrDeque);
    }
    return accValue;
  }

  /**
   * Iterate over values starting from two initial values.
   *
   * <p>Same as the {@link #iterate1(Object, Th2Consumer)} method but for two initial values.</p>
   *
   * @param initValue1 the first initial value
   * @param initValue2 the second initial value
   * @param block      the function block
   * @param <V1>       the type of the first initial value
   * @param <V2>       the type of the second initial value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V1, V2> void iterate2(final V1 initValue1,
                                       final V2 initValue2,
                                       final Th3Consumer<? super V1, ? super V2, ? super ItrDeque2<V1, V2>, ?> block) {
    blockArgNotNull(block);
    final Th3Consumer<V1, V2, ItrDeque2<V1, V2>, RuntimeException> unchecked = Th3Consumer.unchecked(block);
    final ItrDeque2Impl<V1, V2> itrDeque = new ItrDeque2Impl<>();
    unchecked.accept(initValue1, initValue2, itrDeque);
    while (!itrDeque.isEmpty()) {
      unchecked.accept(unwrapNull(itrDeque.deque1.poll()), unwrapNull(itrDeque.deque2.poll()), itrDeque);
    }
  }

  /**
   * Iterate over values and indices starting from two initial values.
   *
   * <p>Same as the {@link #iterate1(Object, Th3ConsumerIntObj2)} method but for two initial values.</p>
   *
   * @param initValue1 the first initial value
   * @param initValue2 the second initial value
   * @param block      the function block
   * @param <V1>       the type of the first initial value
   * @param <V2>       the type of the second initial value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V1, V2> void iterate2(final V1 initValue1,
                                       final V2 initValue2,
                                       final Th4ConsumerIntObj3<? super V1, ? super V2, ? super ItrDeque2<V1, V2>, ?> block) {
    blockArgNotNull(block);
    final Th4ConsumerIntObj3<V1, V2, ItrDeque2<V1, V2>, RuntimeException> unchecked =
      Th4ConsumerIntObj3.unchecked(block);
    final ItrDeque2Impl<V1, V2> itrDeque = new ItrDeque2Impl<>();
    int idx = 0;
    unchecked.accept(idx++, initValue1, initValue2, itrDeque);
    while (!itrDeque.isEmpty()) {
      unchecked.accept(
        idx++,
        unwrapNull(itrDeque.deque1.poll()),
        unwrapNull(itrDeque.deque2.poll()),
        itrDeque
      );
    }
  }

  /**
   * Iterate over values starting from two initial values and returns given accumulator value.
   *
   * <p>Same as the {@link #iterate1(Object, Object, Th3Consumer)} method but for two initial values.</p>
   *
   * @param initValue1 the first initial value
   * @param initValue2 the second initial value
   * @param accValue   the accumulator value
   * @param block      the function block
   * @param <V1>       the type of the first initial value
   * @param <V2>       the type of the second initial value
   * @param <A>        the type of the accumulator value
   * @return given accumulator value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V1, V2, A> A iterate2(final V1 initValue1,
                                       final V2 initValue2,
                                       final A accValue,
                                       final Th4Consumer<? super V1, ? super V2, ? super A, ? super ItrDeque2<V1, V2>, ?> block) {
    blockArgNotNull(block);
    final Th4Consumer<V1, V2, A, ItrDeque2<V1, V2>, RuntimeException> unchecked = Th4Consumer.unchecked(block);
    final ItrDeque2Impl<V1, V2> itrDeque = new ItrDeque2Impl<>();
    unchecked.accept(initValue1, initValue2, accValue, itrDeque);
    while (!itrDeque.isEmpty()) {
      unchecked.accept(
        unwrapNull(itrDeque.deque1.poll()),
        unwrapNull(itrDeque.deque2.poll()),
        accValue,
        itrDeque
      );
    }
    return accValue;
  }

  /**
   * Iterate over values and indices starting from two initial values and returns given accumulator value.
   *
   * <p>Same as the {@link #iterate1(Object, Object, Th4ConsumerIntObj3)} method but for two initial values.</p>
   *
   * @param initValue1 the first initial value
   * @param initValue2 the second initial value
   * @param accValue   the accumulator value
   * @param block      the function block
   * @param <V1>       the type of the first initial value
   * @param <V2>       the type of the second initial value
   * @param <A>        the type of the accumulator value
   * @return given accumulator value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V1, V2, A> A iterate2(final V1 initValue1,
                                       final V2 initValue2,
                                       final A accValue,
                                       final Th5ConsumerIntObj4<? super V1, ? super V2, ? super A, ? super ItrDeque2<V1, V2>, ?> block) {
    blockArgNotNull(block);
    final Th5ConsumerIntObj4<V1, V2, A, ItrDeque2<V1, V2>, RuntimeException> unchecked =
      Th5ConsumerIntObj4.unchecked(block);
    final ItrDeque2Impl<V1, V2> itrDeque = new ItrDeque2Impl<>();
    int idx = 0;
    unchecked.accept(idx++, initValue1, initValue2, accValue, itrDeque);
    while (!itrDeque.isEmpty()) {
      unchecked.accept(
        idx++,
        unwrapNull(itrDeque.deque1.poll()),
        unwrapNull(itrDeque.deque2.poll()),
        accValue,
        itrDeque
      );
    }
    return accValue;
  }

  /**
   * Iterate over values starting from two initial values.
   *
   * <p>Same as the {@link #iterate1(Object, Th2Consumer)} method but for three initial values.</p>
   *
   * @param initValue1 the first initial value
   * @param initValue2 the second initial value
   * @param initValue3 the third initial value
   * @param block      the function block
   * @param <V1>       the type of the first initial value
   * @param <V2>       the type of the second initial value
   * @param <V3>       the type of the third initial value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V1, V2, V3> void iterate3(final V1 initValue1,
                                           final V2 initValue2,
                                           final V3 initValue3,
                                           final Th4Consumer<? super V1, ? super V2, ? super V3, ? super ItrDeque3<V1, V2, V3>, ?> block) {
    blockArgNotNull(block);
    final Th4Consumer<V1, V2, V3, ItrDeque3<V1, V2, V3>, RuntimeException> unchecked = Th4Consumer.unchecked(block);
    final ItrDeque3Impl<V1, V2, V3> itrDeque = new ItrDeque3Impl<>();
    unchecked.accept(initValue1, initValue2, initValue3, itrDeque);
    while (!itrDeque.isEmpty()) {
      unchecked.accept(
        unwrapNull(itrDeque.deque1.poll()),
        unwrapNull(itrDeque.deque2.poll()),
        unwrapNull(itrDeque.deque3.poll()),
        itrDeque
      );
    }
  }

  /**
   * Iterate over values and indices starting from two initial values.
   *
   * <p>Same as the {@link #iterate1(Object, Th3ConsumerIntObj2)} method but for three initial values.</p>
   *
   * @param initValue1 the first initial value
   * @param initValue2 the second initial value
   * @param initValue3 the third initial value
   * @param block      the function block
   * @param <V1>       the type of the first initial value
   * @param <V2>       the type of the second initial value
   * @param <V3>       the type of the third initial value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V1, V2, V3> void iterate3(final V1 initValue1,
                                           final V2 initValue2,
                                           final V3 initValue3,
                                           final Th5ConsumerIntObj4<? super V1, ? super V2, ? super V3, ? super ItrDeque3<V1, V2, V3>, ?> block) {
    blockArgNotNull(block);
    final Th5ConsumerIntObj4<V1, V2, V3, ItrDeque3<V1, V2, V3>, RuntimeException> unchecked =
      Th5ConsumerIntObj4.unchecked(block);
    final ItrDeque3Impl<V1, V2, V3> itrDeque = new ItrDeque3Impl<>();
    int idx = 0;
    unchecked.accept(idx++, initValue1, initValue2, initValue3, itrDeque);
    while (!itrDeque.isEmpty()) {
      unchecked.accept(
        idx++,
        unwrapNull(itrDeque.deque1.poll()),
        unwrapNull(itrDeque.deque2.poll()),
        unwrapNull(itrDeque.deque3.poll()),
        itrDeque
      );
    }
  }

  /**
   * Iterate over values starting from two initial values and returns given accumulator value.
   *
   * <p>Same as the {@link #iterate1(Object, Object, Th3Consumer)} method but for three initial values.</p>
   *
   * @param initValue1 the first initial value
   * @param initValue2 the second initial value
   * @param initValue3 the third initial value
   * @param accValue   the accumulator value
   * @param block      the function block
   * @param <V1>       the type of the first initial value
   * @param <V2>       the type of the second initial value
   * @param <V3>       the type of the third initial value
   * @param <A>        the type of the accumulator value
   * @return given accumulator value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V1, V2, V3, A> A iterate3(final V1 initValue1,
                                           final V2 initValue2,
                                           final V3 initValue3,
                                           final A accValue,
                                           final Th5Consumer<? super V1, ? super V2, ? super V3, ? super A, ? super ItrDeque3<V1, V2, V3>, ?> block) {
    blockArgNotNull(block);
    final Th5Consumer<V1, V2, V3, A, ItrDeque3<V1, V2, V3>, RuntimeException> unchecked =
      Th5Consumer.unchecked(block);
    final ItrDeque3Impl<V1, V2, V3> itrDeque = new ItrDeque3Impl<>();
    unchecked.accept(initValue1, initValue2, initValue3, accValue, itrDeque);
    while (!itrDeque.isEmpty()) {
      unchecked.accept(
        unwrapNull(itrDeque.deque1.poll()),
        unwrapNull(itrDeque.deque2.poll()),
        unwrapNull(itrDeque.deque3.poll()),
        accValue,
        itrDeque
      );
    }
    return accValue;
  }

  /**
   * Iterate over values and indices starting from two initial values and returns given accumulator value.
   *
   * <p>Same as the {@link #iterate1(Object, Object, Th4ConsumerIntObj3)} method but for three initial values.</p>
   *
   * @param initValue1 the first initial value
   * @param initValue2 the second initial value
   * @param initValue3 the third initial value
   * @param accValue   the accumulator value
   * @param block      the function block
   * @param <V1>       the type of the first initial value
   * @param <V2>       the type of the second initial value
   * @param <V3>       the type of the third initial value
   * @param <A>        the type of the accumulator value
   * @return given accumulator value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V1, V2, V3, A> A iterate3(final V1 initValue1,
                                           final V2 initValue2,
                                           final V3 initValue3,
                                           final A accValue,
                                           final Th6ConsumerIntObj5<? super V1, ? super V2, ? super V3, ? super A, ? super ItrDeque3<V1, V2, V3>, ?> block) {
    blockArgNotNull(block);
    final Th6ConsumerIntObj5<V1, V2, V3, A, ItrDeque3<V1, V2, V3>, RuntimeException> unchecked =
      Th6ConsumerIntObj5.unchecked(block);
    final ItrDeque3Impl<V1, V2, V3> itrDeque = new ItrDeque3Impl<>();
    int idx = 0;
    unchecked.accept(idx++, initValue1, initValue2, initValue3, accValue, itrDeque);
    while (!itrDeque.isEmpty()) {
      unchecked.accept(
        idx++,
        unwrapNull(itrDeque.deque1.poll()),
        unwrapNull(itrDeque.deque2.poll()),
        unwrapNull(itrDeque.deque3.poll()),
        accValue,
        itrDeque
      );
    }
    return accValue;
  }

  /**
   * Performs given function block recursively.
   *
   * <pre>{@code
   * recur(self -> {
   *   self.run();
   * });
   * }</pre>
   *
   * @param block the function block
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static void recur(final ThConsumer<? super ThRunnable<? extends Throwable>, ?> block) {
    blockArgNotNull(block);
    final AtomicReference<ThRunnable<?>> selfRef = new AtomicReference<>();
    selfRef.set(() -> block.accept(selfRef.get()));
    ThConsumer.unchecked(block).accept(selfRef.get());
  }

  /**
   * Performs given function block recursively.
   *
   * <pre>{@code
   * recur((depth, self) -> {
   *   if (depth.current() < 50) {
   *     self.run();
   *   }
   *   throw new IllegalStateException("don't wait for the StackOverflowError");
   * });
   * }</pre>
   *
   * @param block the function block
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static void recur(final Th2Consumer<? super RecurDepth, ? super ThRunnable<?>, ?> block) {
    blockArgNotNull(block);
    final AtomicReference<ThRunnable<?>> selfRef = new AtomicReference<>();
    final RecurDepthImpl recurDepth = new RecurDepthImpl();
    selfRef.set(() -> {
      try {
        recurDepth.current++;
        block.accept(recurDepth, selfRef.get());
      } finally {
        recurDepth.current--;
      }
    });
    Th2Consumer.unchecked(block).accept(recurDepth, selfRef.get());
  }

  /**
   * Performs given function block recursively and returns accumulator value.
   *
   * <pre>{@code
   * List<String> result = recur(new ArrayList<>(), (acc, self) -> {
   *   if (acc.size() < 10) {
   *     acc.add("value");
   *     self.run();
   *   }
   * });
   * }</pre>
   *
   * @param accValue the accumulator value
   * @param block    the function block
   * @param <A>      the type of the accumulator value
   * @return given accumulator value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <A> A recur(final A accValue,
                            final Th2Consumer<? super A, ? super ThRunnable<?>, ?> block) {
    blockArgNotNull(block);
    final AtomicReference<ThRunnable<?>> selfRef = new AtomicReference<>();
    selfRef.set(() -> block.accept(accValue, selfRef.get()));
    Th2Consumer.unchecked(block).accept(accValue, selfRef.get());
    return accValue;
  }

  /**
   * Performs given function block recursively and returns accumulator value.
   *
   * <pre>{@code
   * List<String> result = recur(new ArrayList<>(), (depth, acc, self) -> {
   *   if (depth.current() < 50 && acc.size() < 10) {
   *     acc.add("value");
   *     self.run();
   *   }
   * });
   * }</pre>
   *
   * @param accValue the accumulator value
   * @param block    the function block
   * @param <A>      the type of the accumulator value
   * @return given accumulator value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <A> A recur(final A accValue,
                            final Th3Consumer<? super RecurDepth, ? super A, ? super ThRunnable<?>, ?> block) {
    blockArgNotNull(block);
    final AtomicReference<ThRunnable<?>> selfRef = new AtomicReference<>();
    final RecurDepthImpl recurDepth = new RecurDepthImpl();
    selfRef.set(() -> {
      try {
        recurDepth.current++;
        block.accept(recurDepth, accValue, selfRef.get());
      } finally {
        recurDepth.current--;
      }
    });
    Th3Consumer.unchecked(block).accept(recurDepth, accValue, selfRef.get());
    return accValue;
  }

  /**
   * Performs given function block recursively.
   *
   * <pre>{@code
   * recur1(5, (value, self) -> {
   *   if (value < 10) {
   *     System.out.println(value);
   *     self.accept(value + 1);
   *   }
   * });
   * }</pre>
   *
   * @param initValue the initial value
   * @param block     the function block
   * @param <V>       the type of the initial value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V> void recur1(final V initValue,
                                final Th2Consumer<? super V, ? super ThConsumer<V, ?>, ?> block) {
    blockArgNotNull(block);
    final AtomicReference<ThConsumer<V, ?>> selfRef = new AtomicReference<>();
    selfRef.set(v -> block.accept(v, selfRef.get()));
    Th2Consumer.unchecked(block).accept(initValue, selfRef.get());
  }

  /**
   * Performs given function block recursively.
   *
   * <pre>{@code
   * recur1(5, (depth, value, self) -> {
   *   if (depth.current() < 100 && value < 10) {
   *     System.out.println(value);
   *     self.accept(value + 1);
   *   }
   * });
   * }</pre>
   *
   * @param initValue the initial value
   * @param block     the function block
   * @param <V>       the type of the initial value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V> void recur1(final V initValue,
                                final Th3Consumer<? super RecurDepth, ? super V, ? super ThConsumer<V, ?>, ?> block) {
    blockArgNotNull(block);
    final AtomicReference<ThConsumer<V, ?>> selfRef = new AtomicReference<>();
    final RecurDepthImpl recurDepth = new RecurDepthImpl();
    selfRef.set(v -> {
      try {
        recurDepth.current++;
        block.accept(recurDepth, v, selfRef.get());
      } finally {
        recurDepth.current--;
      }
    });
    Th3Consumer.unchecked(block).accept(recurDepth, initValue, selfRef.get());
  }

  /**
   * Performs given function block recursively and returns accumulator value.
   *
   * <pre>{@code
   * List<Integer> result = recur1(5, new ArrayList<>(), (value, acc, self) -> {
   *   if (value < 10) {
   *     acc.add(value);
   *     self.accept(value + 1);
   *   }
   * });
   * }</pre>
   *
   * @param initValue the initial value
   * @param accValue  the accumulator value
   * @param block     the function block
   * @param <V>       the type of the initial value
   * @param <A>       the type of the accumulator value
   * @return given accumulator value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V, A> A recur1(final V initValue,
                                final A accValue,
                                final Th3Consumer<? super V, ? super A, ? super ThConsumer<V, ?>, ?> block) {
    blockArgNotNull(block);
    final AtomicReference<ThConsumer<V, ?>> selfRef = new AtomicReference<>();
    selfRef.set(v -> block.accept(v, accValue, selfRef.get()));
    Th3Consumer.unchecked(block).accept(initValue, accValue, selfRef.get());
    return accValue;
  }

  /**
   * Performs given function block recursively and returns accumulator value.
   *
   * <pre>{@code
   * List<Integer> result = recur1(5, new ArrayList<>(), (depth, value, acc, self) -> {
   *   if (depth.current() < 50 && value < 10) {
   *     acc.add(value);
   *     self.accept(value + 1);
   *   }
   * });
   * }</pre>
   *
   * @param initValue the initial value
   * @param accValue  the accumulator value
   * @param block     the function block
   * @param <V>       the type of the initial value
   * @param <A>       the type of the accumulator value
   * @return given accumulator value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V, A> A recur1(final V initValue,
                                final A accValue,
                                final Th4Consumer<? super RecurDepth, ? super V, ? super A, ? super ThConsumer<V, ?>, ?> block) {
    blockArgNotNull(block);
    final AtomicReference<ThConsumer<V, ?>> selfRef = new AtomicReference<>();
    final RecurDepthImpl recurDepth = new RecurDepthImpl();
    selfRef.set(v -> {
      try {
        recurDepth.current++;
        block.accept(recurDepth, v, accValue, selfRef.get());
      } finally {
        recurDepth.current--;
      }
    });
    Th4Consumer.unchecked(block).accept(recurDepth, initValue, accValue, selfRef.get());
    return accValue;
  }

  /**
   * Performs given function block recursively.
   *
   * <p>Same as the {@link #recur1(Object, Th2Consumer)} method but for two initial values.</p>
   *
   * @param initValue1 the first initial value
   * @param initValue2 the second initial value
   * @param block      the function block
   * @param <V1>       the type of the first initial value
   * @param <V2>       the type of the second initial value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V1, V2> void recur2(final V1 initValue1,
                                     final V2 initValue2,
                                     final Th3Consumer<? super V1, ? super V2, ? super Th2Consumer<V1, V2, ?>, ?> block) {
    blockArgNotNull(block);
    final AtomicReference<Th2Consumer<V1, V2, ?>> selfRef = new AtomicReference<>();
    selfRef.set((v1, v2) -> block.accept(v1, v2, selfRef.get()));
    Th3Consumer.unchecked(block).accept(initValue1, initValue2, selfRef.get());
  }

  /**
   * Performs given function block recursively.
   *
   * <p>Same as the {@link #recur1(Object, Th3Consumer)} method but for two initial values.</p>
   *
   * @param initValue1 the first initial value
   * @param initValue2 the second initial value
   * @param block      the function block
   * @param <V1>       the type of the first initial value
   * @param <V2>       the type of the second initial value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V1, V2> void recur2(final V1 initValue1,
                                     final V2 initValue2,
                                     final Th4Consumer<? super RecurDepth, ? super V1, ? super V2, ? super Th2Consumer<V1, V2, ?>, ?> block) {
    blockArgNotNull(block);
    final AtomicReference<Th2Consumer<V1, V2, ?>> selfRef = new AtomicReference<>();
    final RecurDepthImpl recurDepth = new RecurDepthImpl();
    selfRef.set((v1, v2) -> {
      try {
        recurDepth.current++;
        block.accept(recurDepth, v1, v2, selfRef.get());
      } finally {
        recurDepth.current--;
      }
    });
    Th4Consumer.unchecked(block).accept(recurDepth, initValue1, initValue2, selfRef.get());
  }

  /**
   * Performs given function block recursively and returns accumulator value.
   *
   * <p>Same as the {@link #recur1(Object, Object, Th3Consumer)} method but for two initial values.</p>
   *
   * @param initValue1 the first initial value
   * @param initValue2 the second initial value
   * @param accValue   the accumulator value
   * @param block      the function block
   * @param <V1>       the type of the first initial value
   * @param <V2>       the type of the second initial value
   * @param <A>        the type of the accumulator value
   * @return given accumulator value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V1, V2, A> A recur2(final V1 initValue1,
                                     final V2 initValue2,
                                     final A accValue,
                                     final Th4Consumer<? super V1, ? super V2, ? super A, ? super Th2Consumer<V1, V2, ?>, ?> block) {
    blockArgNotNull(block);
    final AtomicReference<Th2Consumer<V1, V2, ?>> selfRef = new AtomicReference<>();
    selfRef.set((v1, v2) -> block.accept(v1, v2, accValue, selfRef.get()));
    Th4Consumer.unchecked(block).accept(initValue1, initValue2, accValue, selfRef.get());
    return accValue;
  }

  /**
   * Performs given function block recursively and returns accumulator value.
   *
   * <p>Same as the {@link #recur1(Object, Object, Th4Consumer)} method but for two initial values.</p>
   *
   * @param initValue1 the first initial value
   * @param initValue2 the second initial value
   * @param accValue   the accumulator value
   * @param block      the function block
   * @param <V1>       the type of the first initial value
   * @param <V2>       the type of the second initial value
   * @param <A>        the type of the accumulator value
   * @return given accumulator value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V1, V2, A> A recur2(final V1 initValue1,
                                     final V2 initValue2,
                                     final A accValue,
                                     final Th5Consumer<? super RecurDepth, ? super V1, ? super V2, ? super A, ? super Th2Consumer<V1, V2, ?>, ?> block) {
    blockArgNotNull(block);
    final AtomicReference<Th2Consumer<V1, V2, ?>> selfRef = new AtomicReference<>();
    final RecurDepthImpl recurDepth = new RecurDepthImpl();
    selfRef.set((v1, v2) -> {
      try {
        recurDepth.current++;
        block.accept(recurDepth, v1, v2, accValue, selfRef.get());
      } finally {
        recurDepth.current--;
      }
    });
    Th5Consumer.unchecked(block).accept(recurDepth, initValue1, initValue2, accValue, selfRef.get());
    return accValue;
  }

  /**
   * Performs given function block recursively.
   *
   * <p>Same as the {@link #recur1(Object, Th2Consumer)} method but for three initial values.</p>
   *
   * @param initValue1 the first initial value
   * @param initValue2 the second initial value
   * @param initValue3 the third initial value
   * @param block      the function block
   * @param <V1>       the type of the first initial value
   * @param <V2>       the type of the second initial value
   * @param <V3>       the type of the third initial value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V1, V2, V3> void recur3(final V1 initValue1,
                                         final V2 initValue2,
                                         final V3 initValue3,
                                         final Th4Consumer<? super V1, ? super V2, ? super V3, ? super Th3Consumer<V1, V2, V3, ?>, ?> block) {
    blockArgNotNull(block);
    final AtomicReference<Th3Consumer<V1, V2, V3, ?>> selfRef = new AtomicReference<>();
    selfRef.set((v1, v2, v3) -> block.accept(v1, v2, v3, selfRef.get()));
    Th4Consumer.unchecked(block).accept(initValue1, initValue2, initValue3, selfRef.get());
  }

  /**
   * Performs given function block recursively.
   *
   * <p>Same as the {@link #recur1(Object, Th3Consumer)} method but for three initial values.</p>
   *
   * @param initValue1 the first initial value
   * @param initValue2 the second initial value
   * @param initValue3 the third initial value
   * @param block      the function block
   * @param <V1>       the type of the first initial value
   * @param <V2>       the type of the second initial value
   * @param <V3>       the type of the third initial value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V1, V2, V3> void recur3(final V1 initValue1,
                                         final V2 initValue2,
                                         final V3 initValue3,
                                         final Th5Consumer<? super RecurDepth, ? super V1, ? super V2, ? super V3, ? super Th3Consumer<V1, V2, V3, ?>, ?> block) {
    blockArgNotNull(block);
    final AtomicReference<Th3Consumer<V1, V2, V3, ?>> selfRef = new AtomicReference<>();
    final RecurDepthImpl recurDepth = new RecurDepthImpl();
    selfRef.set((v1, v2, v3) -> {
      try {
        recurDepth.current++;
        block.accept(recurDepth, v1, v2, v3, selfRef.get());
      } finally {
        recurDepth.current--;
      }
    });
    Th5Consumer.unchecked(block).accept(recurDepth, initValue1, initValue2, initValue3, selfRef.get());
  }

  /**
   * Performs given function block recursively and returns accumulator value.
   *
   * <p>Same as the {@link #recur1(Object, Object, Th3Consumer)} method but for three initial values.</p>
   *
   * @param initValue1 the first initial value
   * @param initValue2 the second initial value
   * @param initValue3 the third initial value
   * @param accValue   the accumulator value
   * @param block      the function block
   * @param <V1>       the type of the first initial value
   * @param <V2>       the type of the second initial value
   * @param <V3>       the type of the third initial value
   * @param <A>        the type of the accumulator value
   * @return given accumulator value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V1, V2, V3, A> A recur3(final V1 initValue1,
                                         final V2 initValue2,
                                         final V3 initValue3,
                                         final A accValue,
                                         final Th5Consumer<? super V1, ? super V2, ? super V3, ? super A, ? super Th3Consumer<V1, V2, V3, ?>, ?> block) {
    blockArgNotNull(block);
    final AtomicReference<Th3Consumer<V1, V2, V3, ?>> selfRef = new AtomicReference<>();
    selfRef.set((v1, v2, v3) -> block.accept(v1, v2, v3, accValue, selfRef.get()));
    Th5Consumer.unchecked(block).accept(initValue1, initValue2, initValue3, accValue, selfRef.get());
    return accValue;
  }

  /**
   * Performs given function block recursively and returns accumulator value.
   *
   * <p>Same as the {@link #recur1(Object, Object, Th4Consumer)} method but for two initial values.</p>
   *
   * @param initValue1 the first initial value
   * @param initValue2 the second initial value
   * @param initValue3 the third initial value
   * @param accValue   the accumulator value
   * @param block      the function block
   * @param <V1>       the type of the first initial value
   * @param <V2>       the type of the second initial value
   * @param <V3>       the type of the third initial value
   * @param <A>        the type of the accumulator value
   * @return given accumulator value
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V1, V2, V3, A> A recur3(final V1 initValue1,
                                         final V2 initValue2,
                                         final V3 initValue3,
                                         final A accValue,
                                         final Th6Consumer<? super RecurDepth, ? super V1, ? super V2, ? super V3, ? super A, ? super Th3Consumer<V1, V2, V3, ?>, ?> block) {
    blockArgNotNull(block);
    final AtomicReference<Th3Consumer<V1, V2, V3, ?>> selfRef = new AtomicReference<>();
    final RecurDepthImpl recurDepth = new RecurDepthImpl();
    selfRef.set((v1, v2, v3) -> {
      try {
        recurDepth.current++;
        block.accept(recurDepth, v1, v2, v3, accValue, selfRef.get());
      } finally {
        recurDepth.current--;
      }
    });
    Th6Consumer.unchecked(block).accept(recurDepth, initValue1, initValue2, initValue3, accValue, selfRef.get());
    return accValue;
  }

  /**
   * Performs given function block recursively and returns result.
   *
   * <pre>{@code
   * recur(self -> {
   *   return self.get();
   * });
   * }</pre>
   *
   * @param block the function block
   * @param <R>   the type of the result
   * @return result
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <R> R recur(final ThFunction<? super ThSupplier<R, ?>, ? extends R, ?> block) {
    blockArgNotNull(block);
    final AtomicReference<ThSupplier<R, ?>> selfRef = new AtomicReference<>();
    selfRef.set(() -> block.apply(selfRef.get()));
    return ThFunction.unchecked(block).apply(selfRef.get());
  }

  /**
   * Performs given function block recursively and returns result.
   *
   * <pre>{@code
   * recur((depth, self) -> {
   *   if (depth.current() < 50) {
   *     return self.get();
   *   }
   *   throw new IllegalStateException("don't wait for the StackOverflowError");
   * });
   * }</pre>
   *
   * @param block the function block
   * @param <R>   the type of the result
   * @return result
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <R> R recur(final Th2Function<? super RecurDepth, ? super ThSupplier<R, ?>, ? extends R, ?> block) {
    blockArgNotNull(block);
    final AtomicReference<ThSupplier<R, ?>> selfRef = new AtomicReference<>();
    final RecurDepthImpl recurDepth = new RecurDepthImpl();
    selfRef.set(() -> {
      try {
        recurDepth.current++;
        return block.apply(recurDepth, selfRef.get());
      } finally {
        recurDepth.current--;
      }
    });
    return Th2Function.unchecked(block).apply(recurDepth, selfRef.get());
  }

  /**
   * Performs given function block recursively and returns result.
   *
   * <pre>{@code
   * Integer result = recur1(5, (value, self) -> {
   *   if (value < 10) {
   *     return self.apply(value + 1);
   *   }
   *   return value;
   * });
   * }</pre>
   *
   * @param initValue the initial value
   * @param block     the function block
   * @param <V>       the type of the value
   * @param <R>       the type of the result
   * @return result
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V, R> R recur1(final V initValue,
                                final Th2Function<? super V, ? super ThFunction<V, R, ?>, ? extends R, ?> block) {
    blockArgNotNull(block);
    final AtomicReference<ThFunction<V, R, ?>> selfRef = new AtomicReference<>();
    selfRef.set(v -> block.apply(v, selfRef.get()));
    return Th2Function.unchecked(block).apply(initValue, selfRef.get());
  }

  /**
   * Performs given function block recursively and returns result.
   *
   * <pre>{@code
   * Integer result = recur1(5, (depth, value, self) -> {
   *   if (depth.current() < 100 && value < 10) {
   *     return self.apply(value + 1);
   *   }
   *   return value;
   * });
   * }</pre>
   *
   * @param initValue the initial value
   * @param block     the function block
   * @param <V>       the type of the value
   * @param <R>       the type of the result
   * @return result
   * @throws NullPointerException if {@code block} arg is {@code null}
   */
  public static <V, R> R recur1(final V initValue,
                                final Th3Function<? super RecurDepth, ? super V, ? super ThFunction<V, R, ?>, ? extends R, ?> block) {
    blockArgNotNull(block);
    final AtomicReference<ThFunction<V, R, ?>> selfRef = new AtomicReference<>();
    final RecurDepthImpl recurDepth = new RecurDepthImpl();
    selfRef.set(v -> {
      try {
        recurDepth.current++;
        return block.apply(recurDepth, v, selfRef.get());
      } finally {
        recurDepth.current--;
      }
    });
    return Th3Function.unchecked(block).apply(recurDepth, initValue, selfRef.get());
  }

  /**
   * Performs given function block recursively and returns result.
   *
   * <p>Same as the {@link #recur1(Object, Th2Function)} method but for two initial values.</p>
   *
   * @param initValue1 the first initial value
   * @param initValue2 the second initial value
   * @param block      the function block
   * @param <V1>       the type of the first initial value
   * @param <V2>       the type of the second initial value
   * @param <R>        the type of the result
   * @return result
   */
  public static <V1, V2, R> R recur2(final V1 initValue1,
                                     final V2 initValue2,
                                     final Th3Function<? super V1, ? super V2, ? super Th2Function<V1, V2, R, ?>, ? extends R, ?> block) {
    blockArgNotNull(block);
    final AtomicReference<Th2Function<V1, V2, R, ?>> selfRef = new AtomicReference<>();
    selfRef.set((v1, v2) -> block.apply(v1, v2, selfRef.get()));
    return Th3Function.unchecked(block).apply(initValue1, initValue2, selfRef.get());
  }

  /**
   * Performs given function block recursively and returns result.
   *
   * <p>Same as the {@link #recur1(Object, Th3Function)} method but for two initial values.</p>
   *
   * @param initValue1 the first initial value
   * @param initValue2 the second initial value
   * @param block      the function block
   * @param <V1>       the type of the first initial value
   * @param <V2>       the type of the second initial value
   * @param <R>        the type of the result
   * @return result
   */
  public static <V1, V2, R> R recur2(final V1 initValue1,
                                     final V2 initValue2,
                                     final Th4Function<? super RecurDepth, ? super V1, ? super V2, ? super Th2Function<V1, V2, R, ?>, ? extends R, ?> block) {
    blockArgNotNull(block);
    final AtomicReference<Th2Function<V1, V2, R, ?>> selfRef = new AtomicReference<>();
    final RecurDepthImpl recurDepth = new RecurDepthImpl();
    selfRef.set((v1, v2) -> {
      try {
        recurDepth.current++;
        return block.apply(recurDepth, v1, v2, selfRef.get());
      } finally {
        recurDepth.current--;
      }
    });
    return Th4Function.unchecked(block).apply(recurDepth, initValue1, initValue2, selfRef.get());
  }

  /**
   * Performs given function block recursively and returns result.
   *
   * <p>Same as the {@link #recur1(Object, Th2Function)} method but for three initial values.</p>
   *
   * @param initValue1 the first initial value
   * @param initValue2 the second initial value
   * @param initValue3 the third initial value
   * @param block      the function block
   * @param <V1>       the type of the first initial value
   * @param <V2>       the type of the second initial value
   * @param <V3>       the type of the third initial value
   * @param <R>        the type of the result
   * @return result
   */
  public static <V1, V2, V3, R> R recur3(final V1 initValue1,
                                         final V2 initValue2,
                                         final V3 initValue3,
                                         final Th4Function<? super V1, ? super V2, ? super V3, ? super Th3Function<V1, V2, V3, R, ?>, ? extends R, ?> block) {
    blockArgNotNull(block);
    final AtomicReference<Th3Function<V1, V2, V3, R, ?>> selfRef = new AtomicReference<>();
    selfRef.set((v1, v2, v3) -> block.apply(v1, v2, v3, selfRef.get()));
    return Th4Function.unchecked(block).apply(initValue1, initValue2, initValue3, selfRef.get());
  }

  /**
   * Performs given function block recursively and returns result.
   *
   * <p>Same as the {@link #recur1(Object, Th3Function)} method but for three initial values.</p>
   *
   * @param initValue1 the first initial value
   * @param initValue2 the second initial value
   * @param initValue3 the third initial value
   * @param block      the function block
   * @param <V1>       the type of the first initial value
   * @param <V2>       the type of the second initial value
   * @param <V3>       the type of the third initial value
   * @param <R>        the type of the result
   * @return result
   */
  public static <V1, V2, V3, R> R recur3(final V1 initValue1,
                                         final V2 initValue2,
                                         final V3 initValue3,
                                         final Th5Function<? super RecurDepth, ? super V1, ? super V2, ? super V3, ? super Th3Function<V1, V2, V3, R, ?>, ? extends R, ?> block) {
    blockArgNotNull(block);
    final AtomicReference<Th3Function<V1, V2, V3, R, ?>> selfRef = new AtomicReference<>();
    final RecurDepthImpl recurDepth = new RecurDepthImpl();
    selfRef.set((v1, v2, v3) -> {
      try {
        recurDepth.current++;
        return block.apply(recurDepth, v1, v2, v3, selfRef.get());
      } finally {
        recurDepth.current--;
      }
    });
    return Th5Function.unchecked(block).apply(recurDepth, initValue1, initValue2, initValue3, selfRef.get());
  }

  /**
   * Returns a new {@link Lazy} instance that uses the specified initialization function and the
   * {@link Lazy.ThreadSafetyMode#SYNCHRONIZED} thread-safety mode. The returned instance uses itself to synchronize
   * on.
   *
   * <pre>{@code
   * Lazy<String> lazyValue = lazy(() -> {
   *   //...
   *   return "abc";
   * });
   * }</pre>
   *
   * @param initializer the value initializer
   * @param <V>         the type of the value
   * @return new {@link Lazy} instance
   * @throws NullPointerException if {@code initializer} arg is null
   */
  public static <V> Lazy<V> lazy(final ThSupplier<? extends V, ?> initializer) {
    initializerArgNotNull(initializer);
    return new SynchronizedLazy<>(initializer);
  }

  /**
   * Returns a new {@link Lazy} instance that uses the specified initialization function and the
   * {@link Lazy.ThreadSafetyMode#SYNCHRONIZED} thread-safety mode. The returned instance uses the specified lock object
   * to synchronize on.
   *
   * <pre>{@code
   * Object externalLock = new Object();
   * Lazy<String> lazyValue = lazy(externalLock, () -> {
   *   //...
   *   return "abc";
   * });
   * }</pre>
   *
   * @param lock        the lock object
   * @param initializer the value initializer
   * @param <V>         the type of the value
   * @return new {@link Lazy} instance
   * @throws NullPointerException if {@code lock} arg is null or {@code initializer} arg is null
   */
  public static <V> Lazy<V> lazy(final Object lock,
                                 final ThSupplier<? extends V, ?> initializer) {
    lockArgNotNull(lock);
    initializerArgNotNull(initializer);
    return new SynchronizedLazy<>(lock, initializer);
  }

  /**
   * Returns a new {@link Lazy} instance that uses the specified initialization function and thread-safety mode. For
   * {@link Lazy.ThreadSafetyMode#SYNCHRONIZED} the returned instance uses itself to synchronize on.
   *
   * <pre>{@code
   * Lazy<String> lazyValue = lazy(ThreadSafetyMode.PUBLICATION, () -> {
   *   //...
   *   return "abc";
   * });
   * }</pre>
   *
   * @param threadSafetyMode the thread safety mode
   * @param initializer      the value initializer
   * @param <V>              the type of the value
   * @return new {@link Lazy} instance
   * @throws NullPointerException if {@code threadSafetyMode} arg is null or {@code initializer} arg is null
   */
  public static <V> Lazy<V> lazy(final Lazy.ThreadSafetyMode threadSafetyMode,
                                 final ThSupplier<? extends V, ?> initializer) {
    threadSafetyModeArgNotNull(threadSafetyMode);
    initializerArgNotNull(initializer);
    switch (threadSafetyMode) {
      case SYNCHRONIZED:
        return new SynchronizedLazy<>(initializer);
      case PUBLICATION:
        return new SafePublicationLazy<>(initializer);
      case NONE:
        return new UnsafeLazy<>(initializer);
      default:
        throw new Error(); /* unreachable */
    }
  }

  /**
   * Returns a new {@link Lazy} instance that is already initialized with the specified value.
   *
   * <pre>{@code
   * Lazy<String> lazyValue4 = lazyOf("abc");
   * }</pre>
   *
   * @param value the value
   * @param <V>   the type of the value
   * @return new {@link Lazy} instance
   */
  public static <V> Lazy<V> lazyOf(final V value) {
    return value == null
      ? Cast.unsafe(InitializedLazyOfNull.INSTANCE)
      : new InitializedLazy<>(value);
  }

  /**
   * Recursion depth.
   */
  public interface RecurDepth {

    /**
     * Returns the current recursion depth.
     *
     * @return current recursion depth
     */
    int current();
  }

  /**
   * Deque.
   *
   * <p>Used in {@code iterate1} methods.</p>
   *
   * @param <E> the type of the deque elements
   */
  public interface ItrDeque<E> {

    /**
     * Inserts the specified element at the end of this deque.
     *
     * @param element the element
     * @return given element
     */
    E add(E element);

    /**
     * Inserts the specified element at the end of this deque. Alias for the {@link #add(Object)} method.
     *
     * @param element the element
     * @return given element
     */
    E addLast(E element);

    /**
     * Inserts the specified element at the front of this deque.
     *
     * @param element the element
     * @return given element
     */
    E push(E element);

    /**
     * Inserts the specified element at the front of this deque. Alias for the {@link #push(Object)} method.
     *
     * @param element the element
     * @return given element
     */
    E addFirst(E element);

    /**
     * Returns {@code true} if this deque contains no elements.
     *
     * @return {@code true} if this deque contains no elements
     */
    boolean isEmpty();

    /**
     * Returns the number of elements in this deque.
     *
     * @return number of elements in this deque
     */
    int size();
  }

  /**
   * Deque of element pairs.
   *
   * <p>Used in {@code iterate2} methods.</p>
   *
   * @param <E1> the type of the first element in pair
   * @param <E2> the type of the second element in pair
   */
  public interface ItrDeque2<E1, E2> {

    /**
     * Inserts the specified pair of elements at the end of this deque.
     *
     * @param element1 the first element in pair
     * @param element2 the second element in pair
     */
    void add(E1 element1,
             E2 element2);

    /**
     * Inserts the specified pair of elements at the end of this deque. Alias for the {@link #add(Object, Object)}
     * method.
     *
     * @param element1 the first element in pair
     * @param element2 the second element in pair
     */
    void addLast(E1 element1,
                 E2 element2);

    /**
     * Inserts the specified pair of elements at the front of this deque.
     *
     * @param element1 the first element in pair
     * @param element2 the second element in pair
     */
    void push(E1 element1,
              E2 element2);

    /**
     * Inserts the specified pair of elements at the front of this deque. Alias for the {@link #push(Object, Object)}
     * method.
     *
     * @param element1 the first element in pair
     * @param element2 the second element in pair
     */
    void addFirst(E1 element1,
                  E2 element2);

    /**
     * Returns {@code true} if this deque contains no elements.
     *
     * @return {@code true} if this deque contains no elements
     */
    boolean isEmpty();

    /**
     * Returns the number of element pairs in this deque.
     *
     * @return number of element pairs in this deque
     */
    int size();
  }

  /**
   * Deque of element triples.
   *
   * <p>Used in {@code iterate3} methods.</p>
   *
   * @param <E1> the type of the first element in triple
   * @param <E2> the type of the second element in triple
   * @param <E3> the type of the third element in triple
   */
  public interface ItrDeque3<E1, E2, E3> {

    /**
     * Inserts the specified triple of elements at the end of this deque.
     *
     * @param element1 the first element in triple
     * @param element2 the second element in triple
     * @param element3 the third element in triple
     */
    void add(E1 element1,
             E2 element2,
             E3 element3);

    /**
     * Inserts the specified triple of elements at the end of this deque. Alias for the
     * {@link #add(Object, Object, Object)} method.
     *
     * @param element1 the first element in triple
     * @param element2 the second element in triple
     * @param element3 the third element in triple
     */
    void addLast(E1 element1,
                 E2 element2,
                 E3 element3);

    /**
     * Inserts the specified triple of elements at the front of this deque.
     *
     * @param element1 the first element in triple
     * @param element2 the second element in triple
     * @param element3 the third element in triple
     */
    void push(E1 element1,
              E2 element2,
              E3 element3);

    /**
     * Inserts the specified triple of elements at the front of this deque. Alias for the
     * {@link #push(Object, Object, Object)} method.
     *
     * @param element1 the first element in triple
     * @param element2 the second element in triple
     * @param element3 the third element in triple
     */
    void addFirst(E1 element1,
                  E2 element2,
                  E3 element3);

    /**
     * Returns {@code true} if this deque contains no elements.
     *
     * @return {@code true} if this deque contains no elements
     */
    boolean isEmpty();

    /**
     * Returns the number of element triples in this deque.
     *
     * @return number of element triples in this deque
     */
    int size();
  }

  /**
   * Deque that stores {@link AutoCloseable} resources.
   *
   * <p>Used in {@code use} methods.</p>
   */
  public interface ResourceDeque {

    /**
     * Inserts the specified resource at the end of this deque.
     *
     * @param resource the {@link AutoCloseable} resource
     * @param <R>      the type of the resource
     * @return given resource
     * @throws NullPointerException if {@code resource} arg is {@code null}
     */
    <R extends AutoCloseable> R add(R resource);

    /**
     * Inserts the specified resource at the end of this deque. Alias for the {@link #add(AutoCloseable)} method.
     *
     * @param resource the {@link AutoCloseable} resource
     * @param <R>      the type of the resource
     * @return given resource
     * @throws NullPointerException if {@code resource} arg is {@code null}
     */
    <R extends AutoCloseable> R addLast(R resource);

    /**
     * Inserts the specified resource at the front of this deque.
     *
     * @param resource the {@link AutoCloseable} resource
     * @param <R>      the type of the resource
     * @return given resource
     * @throws NullPointerException if {@code resource} arg is {@code null}
     */
    <R extends AutoCloseable> R push(R resource);

    /**
     * Inserts the specified resource at the front of this deque. Alias for the {@link #push(AutoCloseable)} method.
     *
     * @param resource the {@link AutoCloseable} resource
     * @param <R>      the type of the resource
     * @return given resource
     * @throws NullPointerException if {@code resource} arg is {@code null}
     */
    <R extends AutoCloseable> R addFirst(R resource);

    /**
     * Returns {@code true} if this deque contains no elements.
     *
     * @return {@code true} if this deque contains no elements
     */
    boolean isEmpty();

    /**
     * Returns the number of elements in this deque.
     *
     * @return number of elements in this deque
     */
    int size();
  }

  /* private area */

  private static final Object UNINITIALIZED_VALUE = new Object();
  private static final Object NULL_REPLACEMENT = new Object();

  private static Object wrapNull(final Object obj) {
    return obj == null ? NULL_REPLACEMENT : obj;
  }

  @SuppressWarnings("unchecked")
  private static <T> T unwrapNull(final Object obj) {
    return obj == NULL_REPLACEMENT ? null : (T) obj;
  }

  private static Throwable closeResource(final AutoCloseable resource,
                                         Throwable mainEx) {
    try {
      resource.close();
    } catch (final Throwable ex) {
      if (mainEx == null) {
        mainEx = ex;
      } else {
        mainEx.addSuppressed(ex);
      }
    }
    return mainEx;
  }

  private static void blockArgNotNull(final Object block) {
    if (block == null) { throw new NullPointerException("block arg is null"); }
  }

  private static void resourceArgNotNull(final Object resource) {
    if (resource == null) { throw new NullPointerException("resource arg is null"); }
  }

  private static void resource1ArgNotNull(final Object resource1) {
    if (resource1 == null) { throw new NullPointerException("resource1 arg is null"); }
  }

  private static void resource2ArgNotNull(final Object resource2) {
    if (resource2 == null) { throw new NullPointerException("resource2 arg is null"); }
  }

  private static void resource3ArgNotNull(final Object resource3) {
    if (resource3 == null) { throw new NullPointerException("resource3 arg is null"); }
  }

  private static void arrayArgNotNull(final Object array) {
    if (array == null) { throw new NullPointerException("array arg is null"); }
  }

  private static void iterableArgNotNull(final Object iterable) {
    if (iterable == null) { throw new NullPointerException("iterable arg is null"); }
  }

  private static void iteratorArgNotNull(final Object iterator) {
    if (iterator == null) { throw new NullPointerException("iterator arg is null"); }
  }

  private static void mapArgNotNull(final Object map) {
    if (map == null) { throw new NullPointerException("map arg is null"); }
  }

  private static void initializerArgNotNull(final Object initializer) {
    if (initializer == null) { throw new NullPointerException("initializer arg is null"); }
  }

  private static void threadSafetyModeArgNotNull(final Object threadSafetyMode) {
    if (threadSafetyMode == null) { throw new NullPointerException("threadSafetyMode arg is null"); }
  }

  private static void lockArgNotNull(final Object lock) {
    if (lock == null) { throw new NullPointerException("lock arg is null"); }
  }

  private static final class RecurDepthImpl implements RecurDepth {
    private int current = 0;

    private RecurDepthImpl() {
    }

    @Override
    public int current() {
      return this.current;
    }
  }

  private static final class ItrDequeImpl<E> implements ItrDeque<E> {
    private final Deque<Object> deque = new ArrayDeque<>();

    private ItrDequeImpl() {
    }

    @Override
    public E add(final E element) {
      this.deque.add(wrapNull(element));
      return element;
    }

    @Override
    public E addLast(final E element) {
      this.deque.addLast(wrapNull(element));
      return element;
    }

    @Override
    public E push(final E element) {
      this.deque.push(wrapNull(element));
      return element;
    }

    @Override
    public E addFirst(final E element) {
      this.deque.addFirst(wrapNull(element));
      return element;
    }

    @Override
    public boolean isEmpty() {
      return this.deque.isEmpty();
    }

    @Override
    public int size() {
      return this.deque.size();
    }
  }

  private static final class ItrDeque2Impl<E1, E2> implements ItrDeque2<E1, E2> {
    private final Deque<Object> deque1 = new ArrayDeque<>();
    private final Deque<Object> deque2 = new ArrayDeque<>();

    private ItrDeque2Impl() {
    }

    @Override
    public void add(final E1 element1,
                    final E2 element2) {
      this.deque1.add(wrapNull(element1));
      this.deque2.add(wrapNull(element2));
    }

    @Override
    public void addLast(final E1 element1,
                        final E2 element2) {
      this.deque1.addLast(wrapNull(element1));
      this.deque2.addLast(wrapNull(element2));
    }

    @Override
    public void push(final E1 element1,
                     final E2 element2) {
      this.deque1.push(wrapNull(element1));
      this.deque2.push(wrapNull(element2));
    }

    @Override
    public void addFirst(final E1 element1,
                         final E2 element2) {
      this.deque1.addFirst(wrapNull(element1));
      this.deque2.addFirst(wrapNull(element2));
    }

    @Override
    public boolean isEmpty() {
      return this.deque1.isEmpty();
    }

    @Override
    public int size() {
      return this.deque1.size();
    }
  }

  private static final class ItrDeque3Impl<E1, E2, E3> implements ItrDeque3<E1, E2, E3> {
    private final Deque<Object> deque1 = new ArrayDeque<>();
    private final Deque<Object> deque2 = new ArrayDeque<>();
    private final Deque<Object> deque3 = new ArrayDeque<>();

    private ItrDeque3Impl() {
    }

    @Override
    public void add(final E1 element1,
                    final E2 element2,
                    final E3 element3) {
      this.deque1.add(wrapNull(element1));
      this.deque2.add(wrapNull(element2));
      this.deque3.add(wrapNull(element3));
    }

    @Override
    public void addLast(final E1 element1,
                        final E2 element2,
                        final E3 element3) {
      this.deque1.addLast(wrapNull(element1));
      this.deque2.addLast(wrapNull(element2));
      this.deque3.addLast(wrapNull(element3));
    }

    @Override
    public void push(final E1 element1,
                     final E2 element2,
                     final E3 element3) {
      this.deque1.push(wrapNull(element1));
      this.deque2.push(wrapNull(element2));
      this.deque3.push(wrapNull(element3));
    }

    @Override
    public void addFirst(final E1 element1,
                         final E2 element2,
                         final E3 element3) {
      this.deque1.addFirst(wrapNull(element1));
      this.deque2.addFirst(wrapNull(element2));
      this.deque3.addFirst(wrapNull(element3));
    }

    @Override
    public boolean isEmpty() {
      return this.deque1.isEmpty();
    }

    @Override
    public int size() {
      return this.deque1.size();
    }
  }

  private static final class ResourceDequeImpl implements ResourceDeque {
    private final Deque<AutoCloseable> deque = new ArrayDeque<>();

    private ResourceDequeImpl() {
    }

    @Override
    public <R extends AutoCloseable> R add(final R resource) {
      resourceArgNotNull(resource);
      this.deque.add(resource);
      return resource;
    }

    @Override
    public <R extends AutoCloseable> R addLast(final R resource) {
      resourceArgNotNull(resource);
      this.deque.addLast(resource);
      return resource;
    }

    @Override
    public <R extends AutoCloseable> R push(final R resource) {
      resourceArgNotNull(resource);
      this.deque.push(resource);
      return resource;
    }

    @Override
    public <R extends AutoCloseable> R addFirst(final R resource) {
      resourceArgNotNull(resource);
      this.deque.addFirst(resource);
      return resource;
    }

    @Override
    public boolean isEmpty() {
      return this.deque.isEmpty();
    }

    @Override
    public int size() {
      return this.deque.size();
    }

    private void close(Throwable mainEx) {
      for (final AutoCloseable resource : this.deque) {
        try {
          resource.close();
        } catch (final Throwable ex) {
          if (mainEx == null) {
            mainEx = ex;
          } else {
            mainEx.addSuppressed(ex);
          }
        }
      }
      if (mainEx != null) {
        Throw.unchecked(mainEx);
      }
    }
  }

  private static final class InitializedLazy<V> implements Lazy<V>, Serializable {
    private static final long serialVersionUID = 1L;
    private final V value;

    private InitializedLazy(final V value) {
      this.value = value;
    }

    @Override
    public boolean isInitialized() {
      return true;
    }

    @Override
    public V get() {
      return this.value;
    }

    @Override
    public String toString() {
      return "Lazy[" + this.value + "]";
    }
  }

  private static final class InitializedLazyOfNull<V> implements Lazy<V>, Serializable {
    private static final long serialVersionUID = 1L;
    private static final InitializedLazyOfNull<?> INSTANCE = new InitializedLazyOfNull<>();

    private InitializedLazyOfNull() {
    }

    @Override
    public boolean isInitialized() {
      return true;
    }

    @Override
    public V get() {
      return null;
    }

    @Override
    public String toString() {
      return "Lazy[null]";
    }

    private Object readResolve() {
      return INSTANCE;
    }
  }

  private static final class UnsafeLazy<V> implements Lazy<V>, Serializable {
    private static final long serialVersionUID = 1L;
    private ThSupplier<? extends V, ?> initializer;
    private Object value;

    private UnsafeLazy(final ThSupplier<? extends V, ?> initializer) {
      this.initializer = initializer;
      this.value = UNINITIALIZED_VALUE;
    }

    @Override
    public boolean isInitialized() {
      return this.value != UNINITIALIZED_VALUE;
    }

    @Override
    public V get() {
      final Object v = this.value;
      if (v != UNINITIALIZED_VALUE) {
        return Cast.unsafe(v);
      }
      final ThSupplier<? extends V, ?> init = this.initializer;
      if (init != null) {
        final V newValue = ThSupplier.unchecked(init).get();
        this.value = newValue;
        this.initializer = null;
        return newValue;
      }
      return Cast.unsafe(this.value);
    }

    @Override
    public String toString() {
      return this.isInitialized()
        ? "Lazy[" + this.value + "]"
        : "Lazy value not initialized yet";
    }

    private Object writeReplace() {
      return lazyOf(this.get());
    }
  }

  private static final class SafePublicationLazy<V> implements Lazy<V>, Serializable {
    private static final long serialVersionUID = 1L;
    private static final AtomicReferenceFieldUpdater<SafePublicationLazy<?>, Object> VALUE_FIELD_UPDATER =
      Cast.unsafe(AtomicReferenceFieldUpdater.newUpdater(SafePublicationLazy.class, Object.class, "value"));
    private volatile ThSupplier<? extends V, ?> initializer;
    private volatile Object value;

    private SafePublicationLazy(final ThSupplier<? extends V, ?> initializer) {
      this.initializer = initializer;
      this.value = UNINITIALIZED_VALUE;
    }

    @Override
    public boolean isInitialized() {
      return this.value != UNINITIALIZED_VALUE;
    }

    @Override
    public V get() {
      final Object v = this.value;
      if (v != UNINITIALIZED_VALUE) {
        return Cast.unsafe(v);
      }
      final ThSupplier<? extends V, ?> init = this.initializer;
      if (init != null) {
        final V newValue = ThSupplier.unchecked(init).get();
        if (VALUE_FIELD_UPDATER.compareAndSet(this, UNINITIALIZED_VALUE, newValue)) {
          this.initializer = null;
          return newValue;
        }
      }
      return Cast.unsafe(this.value);
    }

    @Override
    public String toString() {
      return this.isInitialized()
        ? "Lazy[" + this.value + "]"
        : "Lazy value not initialized yet";
    }

    private Object writeReplace() {
      return lazyOf(this.get());
    }
  }

  private static final class SynchronizedLazy<V> implements Lazy<V>, Serializable {
    private static final long serialVersionUID = 1L;
    private final Object lock;
    private ThSupplier<? extends V, ?> initializer;
    private volatile Object value;

    private SynchronizedLazy(final ThSupplier<? extends V, ?> initializer) {
      this.lock = this;
      this.initializer = initializer;
      this.value = UNINITIALIZED_VALUE;
    }

    private SynchronizedLazy(final Object lock,
                             final ThSupplier<? extends V, ?> initializer) {
      this.lock = lock;
      this.initializer = initializer;
      this.value = UNINITIALIZED_VALUE;
    }

    @Override
    public boolean isInitialized() {
      return this.value != UNINITIALIZED_VALUE;
    }

    @Override
    public V get() {
      final Object v1 = this.value;
      if (v1 != UNINITIALIZED_VALUE) {
        return Cast.unsafe(v1);
      }
      synchronized (this.lock) {
        final Object v2 = this.value;
        if (v2 != UNINITIALIZED_VALUE) {
          return Cast.unsafe(v2);
        }
        final V newValue = ThSupplier.unchecked(this.initializer).get();
        this.value = newValue;
        this.initializer = null;
        return newValue;
      }
    }

    @Override
    public String toString() {
      return this.isInitialized()
        ? "Lazy[" + this.value + "]"
        : "Lazy value not initialized yet";
    }

    private Object writeReplace() {
      return lazyOf(this.get());
    }
  }
}
