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
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for {@link Opt}.
 */
final class OptTest {

  //region NPE check for instance methods

  @Test
  void letMethodThrowsNPEForNullArg() {
    final Opt<Object> nonEmptyOpt = Opt.of(new Object());
    final Opt<Object> emptyOpt = Opt.empty();
    final ThConsumer<Object, Throwable> block = null;

    assertThatThrownBy(() -> nonEmptyOpt.let(block))
      .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> emptyOpt.let(block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void alsoMethodThrowsNPEForNullArg() {
    final Opt<Object> nonEmptyOpt = Opt.of(new Object());
    final Opt<Object> emptyOpt = Opt.empty();
    final ThConsumer<Object, Throwable> block = null;

    assertThatThrownBy(() -> nonEmptyOpt.also(block))
      .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> emptyOpt.also(block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void letOutMethodThrowsNPEForNullArg() {
    final Opt<Object> nonEmptyOpt = Opt.of(new Object());
    final Opt<Object> emptyOpt = Opt.empty();
    final ThFunction<Object, Object, Throwable> block = null;

    assertThatThrownBy(() -> nonEmptyOpt.letOut(block))
      .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> emptyOpt.letOut(block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void letOptMethodThrowsNPEForNullArg() {
    final Opt<Object> nonEmptyOpt = Opt.of(new Object());
    final Opt<Object> emptyOpt = Opt.empty();
    final ThFunction<Object, Object, Throwable> block = null;

    assertThatThrownBy(() -> nonEmptyOpt.letOpt(block))
      .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> emptyOpt.letOpt(block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void takeIfMethodThrowsNPEForNullArg() {
    final Opt<Object> nonEmptyOpt = Opt.of(new Object());
    final Opt<Object> emptyOpt = Opt.empty();
    final ThPredicate<Object, Throwable> block = null;

    assertThatThrownBy(() -> nonEmptyOpt.takeIf(block))
      .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> emptyOpt.takeIf(block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void takeUnlessMethodThrowsNPEForNullArg() {
    final Opt<Object> nonEmptyOpt = Opt.of(new Object());
    final Opt<Object> emptyOpt = Opt.empty();
    final ThPredicate<Object, Throwable> block = null;

    assertThatThrownBy(() -> nonEmptyOpt.takeUnless(block))
      .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> emptyOpt.takeUnless(block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void orElseGetMethodThrowsNPEForNullArg() {
    final Opt<Object> nonEmptyOpt = Opt.of(new Object());
    final Opt<Object> emptyOpt = Opt.empty();
    final ThSupplier<Object, Throwable> block = null;

    assertThatThrownBy(() -> nonEmptyOpt.orElseGet(block))
      .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> emptyOpt.orElseGet(block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void orElseThrowMethodThrowsNPEForNullArg() {
    final Opt<Object> nonEmptyOpt = Opt.of(new Object());
    final Opt<Object> emptyOpt = Opt.empty();
    final ThSupplier<Throwable, Throwable> block = null;

    assertThatThrownBy(() -> nonEmptyOpt.orElseThrow(block))
      .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> emptyOpt.orElseThrow(block))
      .isInstanceOf(NullPointerException.class);
  }

  //endregion

  //region Logic check for instance methods

  @Test
  void letMethod() {
    final Object value = new Object();
    final Opt<Object> opt = Opt.of(value);
    final AtomicReference<Object> valueRef = new AtomicReference<>();
    final ThConsumer<Object, Throwable> block = arg -> valueRef.set(arg);

    assertThat(opt.let(block))
      .isSameAs(opt);
    assertThat(valueRef.get())
      .isSameAs(value);
  }

  @Test
  void letMethodThrowsException() {
    final Object value = new Object();
    final Opt<Object> opt = Opt.of(value);
    final Throwable throwable = new Throwable();
    final ThConsumer<Object, Throwable> block = arg -> { throw throwable; };

    assertThatThrownBy(() -> opt.let(block))
      .isSameAs(throwable);
  }

  @Test
  void alsoMethod() {
    final Object value = new Object();
    final Opt<Object> opt = Opt.of(value);
    final AtomicReference<Object> valueRef = new AtomicReference<>();
    final ThConsumer<Object, Throwable> block = arg -> valueRef.set(arg);

    assertThat(opt.also(block))
      .isSameAs(opt);
    assertThat(valueRef.get())
      .isSameAs(value);
  }

  @Test
  void alsoMethodThrowsException() {
    final Object value = new Object();
    final Opt<Object> opt = Opt.of(value);
    final Throwable throwable = new Throwable();
    final ThConsumer<Object, Throwable> block = arg -> { throw throwable; };

    assertThatThrownBy(() -> opt.also(block))
      .isSameAs(throwable);
  }

  @Test
  void letOutMethod() {
    final Object value = new Object();
    final Opt<Object> opt = Opt.of(value);
    final AtomicReference<Object> valueRef = new AtomicReference<>();
    final Object result = new Object();
    final ThFunction<Object, Object, Throwable> block = arg -> {
      valueRef.set(arg);
      return result;
    };

    assertThat(opt.letOut(block))
      .isSameAs(result);
    assertThat(valueRef.get())
      .isSameAs(value);

    final Opt<Object> emptyOpt = Opt.empty();
    assertThatThrownBy(() -> emptyOpt.letOut(block))
      .isInstanceOf(NoSuchElementException.class);
  }

  @Test
  void letOutMethodThrowsException() {
    final Object value = new Object();
    final Opt<Object> opt = Opt.of(value);
    final Throwable throwable = new Throwable();
    final ThFunction<Object, Object, Throwable> block = arg -> { throw throwable; };

    assertThatThrownBy(() -> opt.letOut(block))
      .isSameAs(throwable);
  }

  @Test
  void letOptMethod() {
    final Object value = new Object();
    final Opt<Object> opt = Opt.of(value);
    final AtomicReference<Object> valueRef = new AtomicReference<>();
    final Object result = new Object();
    final ThFunction<Object, Object, Throwable> block = arg -> {
      valueRef.set(arg);
      return result;
    };

    assertThat(opt.letOpt(block).get())
      .isSameAs(result);
    assertThat(valueRef.get())
      .isSameAs(value);
  }

  @Test
  void letOptMethodThrowsException() {
    final Object value = new Object();
    final Opt<Object> opt = Opt.of(value);
    final Throwable throwable = new Throwable();
    final ThFunction<Object, Object, Throwable> block = arg -> { throw throwable; };

    assertThatThrownBy(() -> opt.letOpt(block))
      .isSameAs(throwable);
  }

  @Test
  void takeIfMethod() {
    final Object value = new Object();
    final Opt<Object> opt = Opt.of(value);
    final AtomicReference<Object> valueRef = new AtomicReference<>();
    final ThPredicate<Object, Throwable> blockTrue = arg -> {
      valueRef.set(arg);
      return true;
    };
    final ThPredicate<Object, Throwable> blockFalse = arg -> false;

    assertThat(opt.takeIf(blockTrue).get())
      .isSameAs(value);
    assertThat(valueRef.get())
      .isSameAs(value);
    assertThat(opt.takeIf(blockFalse).isEmpty())
      .isTrue();
  }

  @Test
  void takeIfMethodThrowsException() {
    final Object value = new Object();
    final Opt<Object> opt = Opt.of(value);
    final Throwable throwable = new Throwable();
    final ThPredicate<Object, Throwable> block = arg -> { throw throwable; };

    assertThatThrownBy(() -> opt.takeIf(block))
      .isSameAs(throwable);
  }

  @Test
  void takeUnlessMethod() {
    final Object value = new Object();
    final Opt<Object> opt = Opt.of(value);
    final AtomicReference<Object> valueRef = new AtomicReference<>();
    final ThPredicate<Object, Throwable> block1 = arg -> {
      valueRef.set(arg);
      return false;
    };
    final ThPredicate<Object, Throwable> block2 = arg -> true;

    assertThat(opt.takeUnless(block1).get())
      .isSameAs(value);
    assertThat(valueRef.get())
      .isSameAs(value);
    assertThat(opt.takeUnless(block2).isEmpty())
      .isTrue();
  }

  @Test
  void takeUnlessMethodThrowsException() {
    final Object value = new Object();
    final Opt<Object> opt = Opt.of(value);
    final Throwable throwable = new Throwable();
    final ThPredicate<Object, Throwable> block = arg -> { throw throwable; };

    assertThatThrownBy(() -> opt.takeUnless(block))
      .isSameAs(throwable);
  }

  @Test
  void isEmptyMethod() {
    final Opt<Object> nonEmptyOpt = Opt.of(new Object());
    final Opt<Object> nonEmptyNullOpt = Opt.of(null);
    final Opt<Object> emptyOpt = Opt.empty();

    assertThat(nonEmptyOpt.isEmpty())
      .isFalse();
    assertThat(nonEmptyNullOpt.isEmpty())
      .isFalse();
    assertThat(emptyOpt.isEmpty())
      .isTrue();
  }

  @Test
  void takeNonNullMethod() {
    final Object value = new Object();
    final Opt<Object> nonNullOpt = Opt.of(value);
    final Opt<Object> nullOpt = Opt.of(null);

    assertThat(nonNullOpt.takeNonNull().isEmpty())
      .isFalse();
    assertThat(nullOpt.takeNonNull().isEmpty())
      .isTrue();
  }

  @Test
  void throwIfEmptyMethod() {
    final Opt<Object> nonEmptyOpt = Opt.of(new Object());
    final Opt<Object> emptyOpt = Opt.empty();
    final Throwable throwable = new Throwable();
    final ThSupplier<Throwable, Throwable> throwableSupplier = () -> throwable;

    assertThat(nonEmptyOpt.throwIfEmpty(throwableSupplier))
      .isSameAs(nonEmptyOpt);
    assertThatThrownBy(() -> emptyOpt.throwIfEmpty(throwableSupplier))
      .isSameAs(throwable);
  }

  @Test
  void getMethod() {
    final Object value = new Object();
    final Opt<Object> nonEmptyOpt = Opt.of(value);
    final Opt<Object> nonEmptyNullOpt = Opt.of(null);
    final Opt<Object> emptyOpt = Opt.empty();

    assertThat(nonEmptyOpt.get())
      .isSameAs(value);
    assertThat(nonEmptyNullOpt.get())
      .isNull();
    assertThatThrownBy(() -> emptyOpt.get())
      .isInstanceOf(NoSuchElementException.class);
  }

  @Test
  void orElseMethod() {
    final Object value = new Object();
    final Opt<Object> nonEmptyOpt = Opt.of(value);
    final Opt<Object> emptyOpt = Opt.empty();
    final Object anotherValue = new Object();

    assertThat(nonEmptyOpt.orElse(anotherValue))
      .isSameAs(value);
    assertThat(emptyOpt.orElse(anotherValue))
      .isSameAs(anotherValue);
  }

  @Test
  void orNullMethod() {
    final Object value = new Object();
    final Opt<Object> nonEmptyOpt = Opt.of(value);
    final Opt<Object> emptyOpt = Opt.empty();

    assertThat(nonEmptyOpt.orNull())
      .isSameAs(value);
    assertThat(emptyOpt.orNull())
      .isNull();
  }

  @Test
  void orElseGetMethod() {
    final Object value = new Object();
    final Opt<Object> nonEmptyOpt = Opt.of(value);
    final Opt<Object> emptyOpt = Opt.empty();
    final Object anotherValue = new Object();
    final ThSupplier<Object, Throwable> anotherValueSupplier = () -> anotherValue;

    assertThat(nonEmptyOpt.orElseGet(anotherValueSupplier))
      .isSameAs(value);
    assertThat(emptyOpt.orElseGet(anotherValueSupplier))
      .isSameAs(anotherValue);
  }

  @Test
  void orElseThrowMethod() {
    final Object value = new Object();
    final Opt<Object> nonEmptyOpt = Opt.of(value);
    final Opt<Object> emptyOpt = Opt.empty();
    final Throwable throwable = new Throwable();
    final ThSupplier<Throwable, Throwable> throwableSupplier = () -> throwable;

    assertThat(nonEmptyOpt.orElseThrow(throwableSupplier))
      .isSameAs(value);
    assertThatThrownBy(() -> emptyOpt.orElseThrow(throwableSupplier))
      .isSameAs(throwable);
  }

  @Test
  void asOptionalMethod() {
    final Object value = new Object();
    final Opt<Object> nonEmptyOpt = Opt.of(value);
    final Opt<Object> emptyOpt = Opt.empty();

    assertThat(nonEmptyOpt.asOptional())
      .isNotEmpty()
      .containsSame(value);
    assertThat(emptyOpt.asOptional())
      .isEmpty();
  }

  //endregion

  //region Logic check for static methods

  @Test
  void emptyStaticMethod() {
    final Opt<Object> emptyOpt1 = Opt.empty();
    final Opt<Object> emptyOpt2 = Opt.empty();

    assertThat(emptyOpt1)
      .isSameAs(emptyOpt2);
    assertThat(emptyOpt1.isEmpty())
      .isTrue();
  }

  @Test
  void ofStaticMethod() {
    final Object value = new Object();
    final Opt<Object> nonNullOpt = Opt.of(value);
    final Opt<Object> nullOpt = Opt.of(null);

    assertThat(nonNullOpt.isEmpty())
      .isFalse();
    assertThat(nonNullOpt.get())
      .isSameAs(value);
    assertThat(nullOpt.isEmpty())
      .isFalse();
    assertThat(nullOpt.get())
      .isNull();
  }

  @Test
  void nonNullOfStaticMethod() {
    final Object value = new Object();
    final Opt<Object> nonNullOpt = Opt.nonNullOf(value);
    final Opt<Object> nullOpt = Opt.nonNullOf(null);

    assertThat(nonNullOpt.isEmpty())
      .isFalse();
    assertThat(nonNullOpt.get())
      .isSameAs(value);
    assertThat(nullOpt.isEmpty())
      .isTrue();
    assertThatThrownBy(() -> nullOpt.get())
      .isInstanceOf(NoSuchElementException.class);
  }

  @Test
  void toStringMethod() {
    final Object value = "abc123";
    final Opt<Object> nonEmptyOpt = Opt.of(value);
    final Opt<Object> emptyOpt = Opt.empty();

    assertThat(nonEmptyOpt)
      .hasToString("Opt[" + value + "]");
    assertThat(emptyOpt)
      .hasToString("Opt.empty");
  }

  //endregion
}
