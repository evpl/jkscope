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
import com.plugatar.jkscope.function.ThFunction;
import com.plugatar.jkscope.function.ThPredicate;

import java.util.NoSuchElementException;

/**
 * Base scope interface.
 *
 * @param <V> the type of the value
 * @param <T> the type of the container object implementing {@link BaseScope}
 */
interface BaseScope<V, T extends BaseScope<V, T>> {

  /**
   * Performs given function on the value if value is present and returns this value. Similar to
   * {@link #letIt(ThConsumer)} method.
   * <pre>{@code
   * MyObj obj = new MyObj().also(it -> System.out.println(it.getString()));
   * }</pre>
   *
   * @param block the function block
   * @return value
   * @throws NullPointerException if {@code block} arg is null
   */
  T also(ThConsumer<? super V, ?> block);

  /**
   * Performs given function on the value if value is present and returns this value. Similar to
   * {@link #also(ThConsumer)} method.
   * <pre>{@code
   * MyObj obj = new MyObj().letIt(it -> System.out.println(it.getString()));
   * }</pre>
   *
   * @param block the function block
   * @return value
   * @throws NullPointerException if {@code block} arg is null
   */
  T letIt(ThConsumer<? super V, ?> block);

  /**
   * Performs given function block if value is present on this value and returns result.
   * <pre>{@code
   * String str = new MyObj().letOut(it -> it.getString());
   * }</pre>
   *
   * @param block the function block
   * @param <R>   the type of the result
   * @return result
   * @throws NullPointerException   if {@code block} arg is null
   * @throws NoSuchElementException if value is not present
   */
  <R> R letOut(ThFunction<? super V, ? extends R, ?> block);

  /**
   * Performs given function block if value is present on this value and returns {@link Opt} instance of result.
   * <pre>{@code
   * new MyObj().letOpt(it -> it.getString()).takeIf(it -> !it.isEmpty()).letIt(it -> System.out.println(it));
   * }</pre>
   *
   * @param block the function block
   * @param <R>   the type of the result
   * @return {@link Opt} instance of result
   * @throws NullPointerException if {@code block} arg is null
   */
  <R> Opt<R> letOpt(ThFunction<? super V, ? extends R, ?> block);

  /**
   * Performs given function on the value if value is present and returns {@link Opt} instance of value if the condition
   * is met or empty {@link Opt} instance if the condition is not met.
   * <pre>{@code
   * new MyObj().takeIf(it -> !it.getString().isEmpty()).letIt(it -> System.out.println(it.getString()));
   * }</pre>
   *
   * @param block the function block
   * @return {@link Opt} instance of value if the condition is met or empty {@link Opt} instance if the condition is not
   * met
   * @throws NullPointerException if {@code block} arg is null
   */
  Opt<V> takeIf(ThPredicate<? super V, ?> block);

  /**
   * Performs given function on the value if value is present and returns {@link Opt} instance of value if the condition
   * is not met or empty {@link Opt} instance if the condition is met.
   * <pre>{@code
   * new MyObj().takeUnless(it -> it.getString().isEmpty()).letIt(it -> System.out.println(it.getString()));
   * }</pre>
   *
   * @param block the function block
   * @return {@link Opt} instance of value if the condition is not met or empty {@link Opt} instance if the condition is
   * met
   * @throws NullPointerException if {@code block} arg is null
   */
  Opt<V> takeUnless(ThPredicate<? super V, ?> block);
}
