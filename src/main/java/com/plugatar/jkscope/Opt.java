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
import com.plugatar.jkscope.function.ThSupplier;

import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.plugatar.jkscope.Utils.blockArgNotNull;
import static com.plugatar.jkscope.Utils.uncheckedCast;

/**
 * A container object which may or may not contain a value, {@code null} is also considered as a value.
 *
 * @param <V> the type of the value
 */
public interface Opt<V> extends BaseScope<V, Opt<V>> {

  /**
   * Returns true If a value is not present, otherwise false.
   *
   * @return true If a value is not present, otherwise false
   */
  boolean isEmpty();

  /**
   * Returns {@link Opt} instance of this value or empty {@link Opt} instance if given value is null.
   *
   * @return {@link Opt} instance of this value or empty {@link Opt} instance if given value is null
   */
  Opt<V> takeNonNull();

  /**
   * Throws an exception produced by {@code block} function if this {@link Opt} instance is empty.
   *
   * @param block the block function
   * @return value
   * @throws NullPointerException if {@code block} arg is null
   */
  Opt<V> throwIfEmpty(ThSupplier<? extends Throwable, ?> block);

  /**
   * Returns the value if a value is present, otherwise throws {@link NoSuchElementException}.
   *
   * @return the value if a value is present
   * @throws NoSuchElementException if a value is not present
   */
  V get();

  /**
   * Returns the value if a value is present, otherwise {@code other} value.
   *
   * @param other the other value
   * @return the value if a value is present, otherwise {@code other} value
   */
  V orElse(V other);

  /**
   * Returns the value if a value is present, otherwise {@code null} value.
   *
   * @return the value if a value is present, otherwise {@code null} value
   */
  V orNull();

  /**
   * Returns the value if a value is present, otherwise the {@code block} function result.
   *
   * @param block the block function
   * @return the value if a value is present, otherwise the {@code block} function result
   * @throws NullPointerException if {@code block} arg is null
   */
  V orElseGet(ThSupplier<? extends V, ?> block);

  /**
   * Returns the value if a value is present, otherwise throws an exception produced by {@code block} function.
   *
   * @param block the block function
   * @return returns the value if a value is present, otherwise throws an exception produced by {@code block} function
   * @throws NullPointerException if {@code block} arg is null
   */
  V orElseThrow(ThSupplier<? extends Throwable, ?> block);

  /**
   * Returns this {@link Opt} instance as an {@link Optional} instance, {@code null} value means empty
   * {@link Optional}.
   *
   * @return this {@link Opt} instance as an {@link Optional} instance
   */
  Optional<V> asOptional();

  /**
   * Returns an empty {@link Opt} instance.
   *
   * @param <V> the type of the value
   * @return empty {@link Opt} instance
   */
  static <V> Opt<V> empty() {
    return uncheckedCast(Empty.INSTANCE);
  }

  /**
   * Returns {@link Opt} instance of given value, {@code null} is also considered as a value.
   *
   * @param value the value
   * @param <V>   the type of the value
   * @return {@link Opt} instance of given value
   */
  static <V> Opt<V> of(final V value) {
    return value == null
      ? uncheckedCast(Of.NULL)
      : new Of<>(value);
  }

  /**
   * Returns {@link Opt} instance of given value or empty {@link Opt} instance if given value is null. Equivalent to
   * calling a chain of methods: {@code Opt.of(value).takeNonNull()}.
   *
   * @param value the value
   * @param <V>   the type of the value
   * @return {@link Opt} instance of given value or empty {@link Opt} instance if given value is null
   */
  static <V> Opt<V> nonNullOf(final V value) {
    return value == null
      ? empty()
      : of(value);
  }

  /**
   * {@link Opt} implementation of some value.
   *
   * @param <V> the type of the value
   */
  final class Of<V> implements Opt<V>, Serializable {
    private static final long serialVersionUID = 1L;
    private static final Of<?> NULL = new Of<>(null);
    private final V value;

    /**
     * Ctor.
     *
     * @param value the value
     */
    private Of(final V value) {
      this.value = value;
    }

    @Override
    public Opt<V> also(final ThConsumer<? super V, ?> block) {
      blockArgNotNull(block);
      block.asUnchecked().accept(this.value);
      return this;
    }

    @Override
    public Opt<V> letIt(final ThConsumer<? super V, ?> block) {
      blockArgNotNull(block);
      block.asUnchecked().accept(this.value);
      return this;
    }

    @Override
    public <R> R letOut(final ThFunction<? super V, ? extends R, ?> block) {
      blockArgNotNull(block);
      return block.asUnchecked().apply(this.value);
    }

    @Override
    public <R> Opt<R> letOpt(final ThFunction<? super V, ? extends R, ?> block) {
      blockArgNotNull(block);
      return Opt.of(block.asUnchecked().apply(this.value));
    }

    @Override
    public Opt<V> takeIf(final ThPredicate<? super V, ?> block) {
      blockArgNotNull(block);
      return block.asUnchecked().test(this.value)
        ? this
        : Opt.empty();
    }

    @Override
    public Opt<V> takeUnless(final ThPredicate<? super V, ?> block) {
      blockArgNotNull(block);
      return block.asUnchecked().test(this.value)
        ? Opt.empty()
        : this;
    }

    @Override
    public Opt<V> takeNonNull() {
      return this.value == null
        ? Opt.empty()
        : this;
    }

    @Override
    public Opt<V> throwIfEmpty(final ThSupplier<? extends Throwable, ?> block) {
      blockArgNotNull(block);
      return this;
    }

    @Override
    public V get() {
      return this.value;
    }

    @Override
    public boolean isEmpty() {
      return false;
    }

    @Override
    public V orElse(final V other) {
      return this.value;
    }

    @Override
    public V orNull() {
      return this.value;
    }

    @Override
    public V orElseGet(final ThSupplier<? extends V, ?> block) {
      blockArgNotNull(block);
      return this.value;
    }

    @Override
    public V orElseThrow(final ThSupplier<? extends Throwable, ?> block) {
      blockArgNotNull(block);
      return this.value;
    }

    @Override
    public Optional<V> asOptional() {
      return Optional.ofNullable(this.value);
    }

    @Override
    public String toString() {
      return "Opt[" + this.value + "]";
    }
  }

  /**
   * Empty {@link Opt} implementation.
   *
   * @param <V> the type of the value
   */
  final class Empty<V> implements Opt<V>, Serializable {
    private static final long serialVersionUID = 1L;
    private static final Empty<?> INSTANCE = new Empty<>();

    /**
     * Ctor.
     */
    private Empty() {
    }

    @Override
    public Opt<V> also(final ThConsumer<? super V, ?> block) {
      blockArgNotNull(block);
      return this;
    }

    @Override
    public Opt<V> letIt(final ThConsumer<? super V, ?> block) {
      blockArgNotNull(block);
      return this;
    }

    @Override
    public Opt<V> takeIf(final ThPredicate<? super V, ?> block) {
      blockArgNotNull(block);
      return this;
    }

    @Override
    public Opt<V> takeUnless(final ThPredicate<? super V, ?> block) {
      blockArgNotNull(block);
      return this;
    }

    @Override
    public <R> R letOut(final ThFunction<? super V, ? extends R, ?> block) {
      blockArgNotNull(block);
      throw new NoSuchElementException("No value present");
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R> Opt<R> letOpt(final ThFunction<? super V, ? extends R, ?> block) {
      blockArgNotNull(block);
      return (Opt<R>) this;
    }

    @Override
    public Opt<V> takeNonNull() {
      return this;
    }

    @Override
    public Opt<V> throwIfEmpty(final ThSupplier<? extends Throwable, ?> block) {
      blockArgNotNull(block);
      return ((ThFunction<ThSupplier<? extends Throwable, ?>, Opt<V>, ?>) it -> { throw it.get(); })
        .asUnchecked()
        .apply(block);
    }

    @Override
    public V get() {
      throw new NoSuchElementException("No value present");
    }

    @Override
    public boolean isEmpty() {
      return true;
    }

    @Override
    public V orElse(final V other) {
      return other;
    }

    @Override
    public V orNull() {
      return null;
    }

    @Override
    public V orElseGet(final ThSupplier<? extends V, ?> block) {
      blockArgNotNull(block);
      return block.asUnchecked().get();
    }

    @Override
    public V orElseThrow(final ThSupplier<? extends Throwable, ?> block) {
      blockArgNotNull(block);
      return ((ThFunction<ThSupplier<? extends Throwable, ?>, V, ?>) it -> { throw it.get(); })
        .asUnchecked()
        .apply(block);
    }

    @Override
    public Optional<V> asOptional() {
      return Optional.empty();
    }

    @Override
    public String toString() {
      return "Opt.empty";
    }

    /**
     * Returns singleton instance for object serialization.
     *
     * @return singleton instance
     */
    private Object readResolve() {
      return INSTANCE;
    }
  }
}
