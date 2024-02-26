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
import com.plugatar.jkscope.function.ThToDoubleFunction;
import com.plugatar.jkscope.function.ThToIntFunction;
import com.plugatar.jkscope.function.ThToLongFunction;
import com.plugatar.jkscope.function.ThTriConsumer;
import com.plugatar.jkscope.function.ThTriFunction;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for {@link JKScope}.
 */
final class JKScopeTest {

  //region NPE check for instance methods

  @Test
  void alsoInstanceMethodThrowsNPEForNullArg() {
    final JKScope<?> jkScope = new Impl();
    final ThConsumer<Object, Throwable> block = null;

    assertThatThrownBy(() -> jkScope.also(block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void letItInstanceMethodThrowsNPEForNullArg() {
    final JKScope<?> jkScope = new Impl();
    final ThConsumer<Object, Throwable> block = null;

    assertThatThrownBy(() -> jkScope.letIt(block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void letOutInstanceMethodThrowsNPEForNullArg() {
    final JKScope<?> jkScope = new Impl();
    final ThFunction<Object, Object, Throwable> block = null;

    assertThatThrownBy(() -> jkScope.letOut(block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void letOptInstanceMethodThrowsNPEForNullArg() {
    final JKScope<?> jkScope = new Impl();
    final ThFunction<Object, Object, Throwable> block = null;

    assertThatThrownBy(() -> jkScope.letOpt(block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void takeIfInstanceMethodThrowsNPEForNullArg() {
    final JKScope<?> jkScope = new Impl();
    final ThPredicate<Object, Throwable> block = null;

    assertThatThrownBy(() -> jkScope.takeIf(block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void takeUnlessInstanceMethodThrowsNPEForNullArg() {
    final JKScope<?> jkScope = new Impl();
    final ThPredicate<Object, Throwable> block = null;

    assertThatThrownBy(() -> jkScope.takeUnless(block))
      .isInstanceOf(NullPointerException.class);
  }

  //endregion

  //region Logic check for instance methods

  @Test
  void alsoInstanceMethod() {
    final JKScope<?> jkScope = new Impl();
    final AtomicReference<Object> thisRef = new AtomicReference<>();
    final ThConsumer<Object, Throwable> block = arg -> thisRef.set(arg);

    assertThat(jkScope.also(block))
      .isSameAs(jkScope);
    assertThat(thisRef.get())
      .isSameAs(jkScope);
  }

  @Test
  void alsoInstanceMethodThrowsException() {
    final JKScope<?> jkScope = new Impl();
    final Throwable throwable = new Throwable();
    final ThConsumer<Object, Throwable> block = arg -> { throw throwable; };

    assertThatThrownBy(() -> jkScope.also(block))
      .isSameAs(throwable);
  }

  @Test
  void letItInstanceMethod() {
    final JKScope<?> jkScope = new Impl();
    final AtomicReference<Object> thisRef = new AtomicReference<>();
    final ThConsumer<Object, Throwable> block = arg -> thisRef.set(arg);

    assertThat(jkScope.letIt(block))
      .isSameAs(jkScope);
    assertThat(thisRef.get())
      .isSameAs(jkScope);
  }

  @Test
  void letItInstanceMethodThrowsException() {
    final JKScope<?> jkScope = new Impl();
    final Throwable throwable = new Throwable();
    final ThConsumer<Object, Throwable> block = arg -> { throw throwable; };

    assertThatThrownBy(() -> jkScope.letIt(block))
      .isSameAs(throwable);
  }

  @Test
  void letOutInstanceMethod() {
    final JKScope<?> jkScope = new Impl();
    final AtomicReference<Object> thisRef = new AtomicReference<>();
    final Object result = new Object();
    final ThFunction<Object, Object, Throwable> block = arg -> {
      thisRef.set(arg);
      return result;
    };

    assertThat(jkScope.letOut(block))
      .isSameAs(result);
    assertThat(thisRef.get())
      .isSameAs(jkScope);
  }

  @Test
  void letOutInstanceMethodThrowsException() {
    final JKScope<?> jkScope = new Impl();
    final Throwable throwable = new Throwable();
    final ThFunction<Object, Object, Throwable> block = arg -> { throw throwable; };

    assertThatThrownBy(() -> jkScope.letOut(block))
      .isSameAs(throwable);
  }

  @Test
  void letOptInstanceMethod() {
    final JKScope<?> jkScope = new Impl();
    final AtomicReference<Object> thisRef = new AtomicReference<>();
    final Object result = new Object();
    final ThFunction<Object, Object, Throwable> block = arg -> {
      thisRef.set(arg);
      return result;
    };

    assertThat(jkScope.letOpt(block).get())
      .isSameAs(result);
    assertThat(thisRef.get())
      .isSameAs(jkScope);
  }

  @Test
  void letOptInstanceMethodThrowsException() {
    final JKScope<?> jkScope = new Impl();
    final Throwable throwable = new Throwable();
    final ThFunction<Object, Object, Throwable> block = arg -> { throw throwable; };

    assertThatThrownBy(() -> jkScope.letOpt(block))
      .isSameAs(throwable);
  }

  @Test
  void takeIfInstanceMethod() {
    final JKScope<?> jkScope = new Impl();
    final AtomicReference<Object> thisRef = new AtomicReference<>();
    final ThPredicate<Object, Throwable> block1 = arg -> {
      thisRef.set(arg);
      return true;
    };
    final ThPredicate<Object, Throwable> block2 = arg -> false;

    assertThat(jkScope.takeIf(block1).get())
      .isSameAs(jkScope);
    assertThat(thisRef.get())
      .isSameAs(jkScope);
    assertThat(jkScope.takeIf(block2).isEmpty())
      .isTrue();
  }

  @Test
  void takeIfInstanceMethodThrowsException() {
    final JKScope<?> jkScope = new Impl();
    final Throwable throwable = new Throwable();
    final ThPredicate<Object, Throwable> block = arg -> { throw throwable; };

    assertThatThrownBy(() -> jkScope.takeIf(block))
      .isSameAs(throwable);
  }

  @Test
  void takeUnlessInstanceMethod() {
    final JKScope<?> jkScope = new Impl();
    final AtomicReference<Object> thisRef = new AtomicReference<>();
    final ThPredicate<Object, Throwable> block1 = arg -> {
      thisRef.set(arg);
      return false;
    };
    final ThPredicate<Object, Throwable> block2 = arg -> true;

    assertThat(jkScope.takeUnless(block1).get())
      .isSameAs(jkScope);
    assertThat(thisRef.get())
      .isSameAs(jkScope);
    assertThat(jkScope.takeUnless(block2).isEmpty())
      .isTrue();
  }

  @Test
  void takeUnlessInstanceMethodThrowsException() {
    final JKScope<?> jkScope = new Impl();
    final Throwable throwable = new Throwable();
    final ThPredicate<Object, Throwable> block = arg -> { throw throwable; };

    assertThatThrownBy(() -> jkScope.takeUnless(block))
      .isSameAs(throwable);
  }

  //endregion

  //region NPE check for static methods

  @Test
  void runStaticMethodThrowsNPEForNullArg() {
    final ThRunnable<Throwable> block = null;

    assertThatThrownBy(() -> JKScope.run(block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void runCatchingStaticMethodThrowsNPEForNullArg() {
    final ThRunnable<Throwable> block = null;

    assertThatThrownBy(() -> JKScope.runCatching(block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void runRecStaticMethodThrowsNPEForNullArg() {
    final ThConsumer<ThRunnable<Throwable>, Throwable> block = null;

    assertThatThrownBy(() -> JKScope.runRec(block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void withStaticMethodThrowsNPEForNullArg() {
    final Object value = new Object();
    final ThConsumer<Object, Throwable> block = null;

    assertThatThrownBy(() -> JKScope.with(value, block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void withIntStaticMethodThrowsNPEForNullArg() {
    final int value = 0;
    final ThIntConsumer<Throwable> block = null;

    assertThatThrownBy(() -> JKScope.withInt(value, block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void withLongStaticMethodThrowsNPEForNullArg() {
    final long value = 0L;
    final ThLongConsumer<Throwable> block = null;

    assertThatThrownBy(() -> JKScope.withLong(value, block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void withDoubleStaticMethodThrowsNPEForNullArg() {
    final double value = 0.0;
    final ThDoubleConsumer<Throwable> block = null;

    assertThatThrownBy(() -> JKScope.withDouble(value, block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void withResourceMethodThrowsNPEForNullArg() {
    final AutoCloseable value = new AutoCloseableImpl();
    final ThConsumer<Object, Throwable> block = null;

    assertThatThrownBy(() -> JKScope.withResource(value, block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void with2ArgsStaticMethodThrowsNPEForNullArg() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final ThBiConsumer<Object, Object, Throwable> block = null;

    assertThatThrownBy(() -> JKScope.with(value1, value2, block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void with3ArgsStaticMethodThrowsNPEForNullArg() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final ThTriConsumer<Object, Object, Object, Throwable> block = null;

    assertThatThrownBy(() -> JKScope.with(value1, value2, value3, block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void letSupplierStaticMethodThrowsNPEForNullArg() {
    final ThSupplier<Object, Throwable> block = null;

    assertThatThrownBy(() -> JKScope.let(block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void letIntSupplierStaticMethodThrowsNPEForNullArg() {
    final ThIntSupplier<Throwable> block = null;

    assertThatThrownBy(() -> JKScope.letInt(block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void letLongSupplierStaticMethodThrowsNPEForNullArg() {
    final ThLongSupplier<Throwable> block = null;

    assertThatThrownBy(() -> JKScope.letLong(block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void letDoubleSupplierStaticMethodThrowsNPEForNullArg() {
    final ThDoubleSupplier<Throwable> block = null;

    assertThatThrownBy(() -> JKScope.letDouble(block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void letConsumerStaticMethodThrowsNPEForNullArg() {
    final Object value = new Object();
    final ThConsumer<Object, Throwable> block = null;

    assertThatThrownBy(() -> JKScope.let(value, block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void letIntConsumerStaticMethodThrowsNPEForNullArg() {
    final int value = 0;
    final ThIntConsumer<Throwable> block = null;

    assertThatThrownBy(() -> JKScope.letInt(value, block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void letLongConsumerStaticMethodThrowsNPEForNullArg() {
    final long value = 0L;
    final ThLongConsumer<Throwable> block = null;

    assertThatThrownBy(() -> JKScope.letLong(value, block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void letDoubleConsumerStaticMethodThrowsNPEForNullArg() {
    final double value = 0.0;
    final ThDoubleConsumer<Throwable> block = null;

    assertThatThrownBy(() -> JKScope.letDouble(value, block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void letRecStaticMethodThrowsNPEForNullArg() {
    final Object value = new Object();
    final ThBiFunction<Object, ThFunction<Object, Object, Throwable>, Object, Throwable> block = null;

    assertThatThrownBy(() -> JKScope.letRec(value, block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void letIntRecStaticMethodThrowsNPEForNullArg() {
    final int value = 0;
    final ThIntObjToIntFunction<ThIntToIntFunction<Throwable>, Throwable> block = null;

    assertThatThrownBy(() -> JKScope.letIntRec(value, block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void letLongRecStaticMethodThrowsNPEForNullArg() {
    final long value = 0L;
    final ThLongObjToLongFunction<ThLongToLongFunction<Throwable>, Throwable> block = null;

    assertThatThrownBy(() -> JKScope.letLongRec(value, block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void letDoubleRecStaticMethodThrowsNPEForNullArg() {
    final double value = 0L;
    final ThDoubleObjToDoubleFunction<ThDoubleToDoubleFunction<Throwable>, Throwable> block = null;

    assertThatThrownBy(() -> JKScope.letDoubleRec(value, block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void letWithStaticMethodThrowsNPEForNullArg() {
    final Object value = new Object();
    final ThFunction<Object, Object, Throwable> block = null;

    assertThatThrownBy(() -> JKScope.letWith(value, block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void letIntWithStaticMethodThrowsNPEForNullArg() {
    final Object value = new Object();
    final ThToIntFunction<Object, Throwable> block = null;

    assertThatThrownBy(() -> JKScope.letIntWith(value, block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void letLongWithStaticMethodThrowsNPEForNullArg() {
    final Object value = new Object();
    final ThToLongFunction<Object, Throwable> block = null;

    assertThatThrownBy(() -> JKScope.letLongWith(value, block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void letDoubleWithStaticMethodThrowsNPEForNullArg() {
    final Object value = new Object();
    final ThToDoubleFunction<Object, Throwable> block = null;

    assertThatThrownBy(() -> JKScope.letDoubleWith(value, block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void letWithResourceMethodThrowsNPEForNullArg() {
    final AutoCloseable value = new AutoCloseableImpl();
    final ThFunction<Object, Object, Throwable> block = null;

    assertThatThrownBy(() -> JKScope.letWithResource(value, block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void letWith2ArgsStaticMethodThrowsNPEForNullArg() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final ThBiFunction<Object, Object, Object, Throwable> block = null;

    assertThatThrownBy(() -> JKScope.letWith(value1, value2, block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void letWith3ArgsStaticMethodThrowsNPEForNullArg() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final ThTriFunction<Object, Object, Object, Object, Throwable> block = null;

    assertThatThrownBy(() -> JKScope.letWith(value1, value2, value3, block))
      .isInstanceOf(NullPointerException.class);
  }

  //endregion

  //region Logic check for static methods

  @Test
  void runStaticMethod() {
    final AtomicBoolean sideEffect = new AtomicBoolean(false);
    final ThRunnable<Throwable> block = () -> sideEffect.set(true);

    JKScope.run(block);
    assertThat(sideEffect.get())
      .isTrue();
  }

  @Test
  void runStaticMethodThrowsException() {
    final Throwable throwable = new Throwable();
    final ThRunnable<Throwable> block = () -> { throw throwable; };

    assertThatThrownBy(() -> JKScope.run(block))
      .isSameAs(throwable);
  }

  @Test
  void runCatchingStaticMethod() {
    final AtomicBoolean sideEffect = new AtomicBoolean(false);
    final ThRunnable<Throwable> block = () -> sideEffect.set(true);

    JKScope.runCatching(block);
    assertThat(sideEffect.get())
      .isTrue();
  }

  @Test
  void runCatchingStaticMethodThrowsException() {
    final AtomicBoolean sideEffect = new AtomicBoolean(false);
    final ThRunnable<Throwable> block = () -> {
      sideEffect.set(true);
      throw new Throwable();
    };

    JKScope.runCatching(block);
    assertThat(sideEffect.get())
      .isTrue();
  }

  @Test
  void runRecStaticMethodThrowsException() {
    final Throwable throwable = new Throwable();
    final ThConsumer<ThRunnable<Throwable>, Throwable> block = arg -> { throw throwable; };

    assertThatThrownBy(() -> JKScope.runRec(block))
      .isSameAs(throwable);
  }

  @Test
  void runRecStaticMethod() throws Throwable {
    final AtomicReference<ThRunnable<Throwable>> funcRef = new AtomicReference<>();
    final ThConsumer<ThRunnable<Throwable>, Throwable> block = arg -> funcRef.set(arg);

    JKScope.runRec(block);
    assertThat(funcRef.get())
      .isNotNull();

    final ThRunnable<Throwable> func = funcRef.get();
    funcRef.set(null);

    func.run();
    assertThat(funcRef.get())
      .isSameAs(func);
  }

  @Test
  void withStaticMethod() {
    final Object value = new Object();
    final AtomicReference<Object> valueRef = new AtomicReference<>();
    final ThConsumer<Object, Throwable> block = arg -> valueRef.set(arg);

    JKScope.with(value, block);
    assertThat(valueRef.get())
      .isSameAs(value);
  }

  @Test
  void withStaticMethodThrowsException() {
    final Object value = new Object();
    final Throwable throwable = new Throwable();
    final ThConsumer<Object, Throwable> block = arg -> { throw throwable; };

    assertThatThrownBy(() -> JKScope.with(value, block))
      .isSameAs(throwable);
  }

  @Test
  void withIntStaticMethod() {
    final int value = 100;
    final AtomicInteger valueRef = new AtomicInteger();
    final ThIntConsumer<Throwable> block = arg -> valueRef.set(arg);

    JKScope.withInt(value, block);
    assertThat(valueRef.get())
      .isEqualTo(value);
  }

  @Test
  void withIntStaticMethodThrowsException() {
    final int value = 100;
    final Throwable throwable = new Throwable();
    final ThIntConsumer<Throwable> block = arg -> { throw throwable; };

    assertThatThrownBy(() -> JKScope.withInt(value, block))
      .isSameAs(throwable);
  }

  @Test
  void withLongStaticMethod() {
    final long value = 100L;
    final AtomicLong valueRef = new AtomicLong();
    final ThLongConsumer<Throwable> block = arg -> valueRef.set(arg);

    JKScope.withLong(value, block);
    assertThat(valueRef.get())
      .isSameAs(value);
  }

  @Test
  void withLongStaticMethodThrowsException() {
    final long value = 100L;
    final Throwable throwable = new Throwable();
    final ThLongConsumer<Throwable> block = arg -> { throw throwable; };

    assertThatThrownBy(() -> JKScope.withLong(value, block))
      .isSameAs(throwable);
  }

  @Test
  void withDoubleStaticMethod() {
    final double value = 100.0;
    final AtomicReference<Double> valueRef = new AtomicReference<>();
    final ThDoubleConsumer<Throwable> block = arg -> valueRef.set(arg);

    JKScope.withDouble(value, block);
    assertThat(valueRef.get())
      .isEqualTo(value);
  }

  @Test
  void withDoubleStaticMethodThrowsException() {
    final double value = 100.0;
    final Throwable throwable = new Throwable();
    final ThDoubleConsumer<Throwable> block = arg -> { throw throwable; };

    assertThatThrownBy(() -> JKScope.withDouble(value, block))
      .isSameAs(throwable);
  }

  @Test
  void withResourceMethod() {
    final AutoCloseableImpl value = new AutoCloseableImpl();
    final AtomicReference<Object> valueRef = new AtomicReference<>();
    final ThConsumer<Object, Throwable> block = arg -> valueRef.set(arg);

    JKScope.withResource(value, block);
    assertThat(valueRef.get())
      .isSameAs(value);
    assertThat(value.isClosed())
      .isTrue();
  }

  @Test
  void withResourceMethodThrowsException() {
    final AutoCloseableImpl value = new AutoCloseableImpl();
    final Throwable throwable = new Throwable();
    final ThConsumer<Object, Throwable> block = arg -> { throw throwable; };

    assertThatThrownBy(() -> JKScope.withResource(value, block))
      .isSameAs(throwable);
    assertThat(value.isClosed())
      .isTrue();
  }

  @Test
  void with2ArgsStaticMethod() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final AtomicReference<Object> value1Ref = new AtomicReference<>();
    final AtomicReference<Object> value2Ref = new AtomicReference<>();
    final ThBiConsumer<Object, Object, Throwable> block = (arg1, arg2) -> {
      value1Ref.set(arg1);
      value2Ref.set(arg2);
    };

    JKScope.with(value1, value2, block);
    assertThat(value1Ref.get())
      .isSameAs(value1);
    assertThat(value2Ref.get())
      .isSameAs(value2);
  }

  @Test
  void with2ArgsStaticMethodThrowsException() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Throwable throwable = new Throwable();
    final ThBiConsumer<Object, Object, Throwable> block = (arg1, arg2) -> { throw throwable; };

    assertThatThrownBy(() -> JKScope.with(value1, value2, block))
      .isSameAs(throwable);
  }

  @Test
  void with3ArgsStaticMethod() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final AtomicReference<Object> value1Ref = new AtomicReference<>();
    final AtomicReference<Object> value2Ref = new AtomicReference<>();
    final AtomicReference<Object> value3Ref = new AtomicReference<>();
    final ThTriConsumer<Object, Object, Object, Throwable> block = (arg1, arg2, arg3) -> {
      value1Ref.set(arg1);
      value2Ref.set(arg2);
      value3Ref.set(arg3);
    };

    JKScope.with(value1, value2, value3, block);
    assertThat(value1Ref.get())
      .isSameAs(value1);
    assertThat(value2Ref.get())
      .isSameAs(value2);
    assertThat(value3Ref.get())
      .isSameAs(value3);
  }

  @Test
  void with3ArgsStaticMethodThrowsException() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final Throwable throwable = new Throwable();
    final ThTriConsumer<Object, Object, Object, Throwable> block = (arg1, arg2, arg3) -> { throw throwable; };

    assertThatThrownBy(() -> JKScope.with(value1, value2, value3, block))
      .isSameAs(throwable);
  }

  @Test
  void letStaticMethod() {
    final Object nonNullValue = new Object();
    assertThat(JKScope.let(nonNullValue).get())
      .isSameAs(nonNullValue);

    final Object nullValue = null;
    assertThat(JKScope.let(nullValue).get())
      .isNull();
  }

  @Test
  void letNonNullStaticMethod() {
    final Object nonNullValue = new Object();
    assertThat(JKScope.letNonNull(nonNullValue).get())
      .isSameAs(nonNullValue);

    final Object nullValue = null;
    assertThat(JKScope.letNonNull(nullValue).isEmpty())
      .isTrue();
  }

  @Test
  void letSupplierStaticMethod() {
    final Object result = new Object();
    final ThSupplier<Object, Throwable> block = () -> result;

    assertThat(JKScope.let(block))
      .isSameAs(result);
  }

  @Test
  void letSupplierStaticMethodThrowsException() {
    final Throwable throwable = new Throwable();
    final ThSupplier<Object, Throwable> block = () -> { throw throwable; };

    assertThatThrownBy(() -> JKScope.let(block))
      .isSameAs(throwable);
  }

  @Test
  void letIntSupplierStaticMethod() {
    final int result = 100;
    final ThIntSupplier<Throwable> block = () -> result;

    assertThat(JKScope.letInt(block))
      .isSameAs(result);
  }

  @Test
  void letIntSupplierStaticMethodThrowsException() {
    final Throwable throwable = new Throwable();
    final ThIntSupplier<Throwable> block = () -> { throw throwable; };

    assertThatThrownBy(() -> JKScope.letInt(block))
      .isSameAs(throwable);
  }

  @Test
  void letLongSupplierStaticMethod() {
    final long result = 100L;
    final ThLongSupplier<Throwable> block = () -> result;

    assertThat(JKScope.letLong(block))
      .isSameAs(result);
  }

  @Test
  void letLongSupplierStaticMethodThrowsException() {
    final Throwable throwable = new Throwable();
    final ThLongSupplier<Throwable> block = () -> { throw throwable; };

    assertThatThrownBy(() -> JKScope.letLong(block))
      .isSameAs(throwable);
  }

  @Test
  void letDoubleSupplierStaticMethod() {
    final double result = 100.0;
    final ThDoubleSupplier<Throwable> block = () -> result;

    assertThat(JKScope.letDouble(block))
      .isEqualTo(result);
  }

  @Test
  void letDoubleSupplierStaticMethodThrowsException() {
    final Throwable throwable = new Throwable();
    final ThDoubleSupplier<Throwable> block = () -> { throw throwable; };

    assertThatThrownBy(() -> JKScope.letDouble(block))
      .isSameAs(throwable);
  }

  @Test
  void letConsumerStaticMethod() {
    final Object value = new Object();
    final AtomicReference<Object> valueRef = new AtomicReference<>();
    final ThConsumer<Object, Throwable> block = arg -> valueRef.set(arg);

    assertThat(JKScope.let(value, block))
      .isSameAs(value);
    assertThat(valueRef.get())
      .isSameAs(value);
  }

  @Test
  void letConsumerStaticMethodThrowsException() {
    final Object value = new Object();
    final Throwable throwable = new Throwable();
    final ThConsumer<Object, Throwable> block = arg -> { throw throwable; };

    assertThatThrownBy(() -> JKScope.let(value, block))
      .isSameAs(throwable);
  }

  @Test
  void letIntConsumerStaticMethod() {
    final int value = 100;
    final AtomicInteger valueRef = new AtomicInteger();
    final ThIntConsumer<Throwable> block = arg -> valueRef.set(arg);

    assertThat(JKScope.letInt(value, block))
      .isEqualTo(value);
    assertThat(valueRef.get())
      .isEqualTo(value);
  }

  @Test
  void letIntConsumerStaticMethodThrowsException() {
    final int value = 100;
    final Throwable throwable = new Throwable();
    final ThIntConsumer<Throwable> block = arg -> { throw throwable; };

    assertThatThrownBy(() -> JKScope.letInt(value, block))
      .isSameAs(throwable);
  }

  @Test
  void letLongConsumerStaticMethod() {
    final long value = 100L;
    final AtomicLong valueRef = new AtomicLong();
    final ThLongConsumer<Throwable> block = arg -> valueRef.set(arg);

    assertThat(JKScope.letLong(value, block))
      .isSameAs(value);
    assertThat(valueRef.get())
      .isSameAs(value);
  }

  @Test
  void letLongConsumerStaticMethodThrowsException() {
    final long value = 100L;
    final Throwable throwable = new Throwable();
    final ThLongConsumer<Throwable> block = arg -> { throw throwable; };

    assertThatThrownBy(() -> JKScope.letLong(value, block))
      .isSameAs(throwable);
  }

  @Test
  void letDoubleConsumerStaticMethod() {
    final double value = 100.0;
    final AtomicReference<Double> valueRef = new AtomicReference<>();
    final ThDoubleConsumer<Throwable> block = arg -> valueRef.set(arg);

    assertThat(JKScope.letDouble(value, block))
      .isEqualTo(value);
    assertThat(valueRef.get())
      .isEqualTo(value);
  }

  @Test
  void letDoubleConsumerStaticMethodThrowsException() {
    final double value = 100.0;
    final Throwable throwable = new Throwable();
    final ThDoubleConsumer<Throwable> block = arg -> { throw throwable; };

    assertThatThrownBy(() -> JKScope.letDouble(value, block))
      .isSameAs(throwable);
  }

  @Test
  void letRecStaticMethod() throws Throwable {
    final Object value = new Object();
    final Object result = new Object();
    final AtomicReference<Object> valueRef = new AtomicReference<>();
    final AtomicReference<ThFunction<Object, Object, Throwable>> funcRef = new AtomicReference<>();
    final ThBiFunction<Object, ThFunction<Object, Object, Throwable>, Object, Throwable> block = (arg1, arg2) -> {
      valueRef.set(arg1);
      funcRef.set(arg2);
      return result;
    };

    assertThat(JKScope.letRec(value, block))
      .isSameAs(result);
    assertThat(valueRef.get())
      .isSameAs(value);

    final ThFunction<Object, Object, Throwable> func = funcRef.get();
    valueRef.set(null);
    funcRef.set(null);

    assertThat(func.apply(value))
      .isSameAs(result);
    assertThat(valueRef.get())
      .isSameAs(value);
  }

  @Test
  void letRecStaticMethodThrowsException() {
    final Object value = new Object();
    final Throwable throwable = new Throwable();
    final ThBiFunction<Object, ThFunction<Object, Object, Throwable>, Object, Throwable> block =
      (arg1, arg2) -> { throw throwable; };

    assertThatThrownBy(() -> JKScope.letRec(value, block))
      .isSameAs(throwable);
  }

  @Test
  void letIntRecStaticMethod() throws Throwable {
    final int value = 100;
    final int result = 999;
    final AtomicInteger valueRef = new AtomicInteger();
    final AtomicReference<ThIntToIntFunction<Throwable>> funcRef = new AtomicReference<>();
    final ThIntObjToIntFunction<ThIntToIntFunction<Throwable>, Throwable> block = (arg1, arg2) -> {
      valueRef.set(arg1);
      funcRef.set(arg2);
      return result;
    };

    assertThat(JKScope.letIntRec(value, block))
      .isEqualTo(result);
    assertThat(valueRef.get())
      .isEqualTo(value);

    final ThIntToIntFunction<Throwable> func = funcRef.get();
    valueRef.set(0);
    funcRef.set(null);

    assertThat(func.apply(value))
      .isEqualTo(result);
    assertThat(valueRef.get())
      .isEqualTo(value);
  }

  @Test
  void letIntRecStaticMethodThrowsException() {
    final int value = 100;
    final Throwable throwable = new Throwable();
    final ThIntObjToIntFunction<ThIntToIntFunction<Throwable>, Throwable> block =
      (arg1, arg2) -> { throw throwable; };

    assertThatThrownBy(() -> JKScope.letIntRec(value, block))
      .isSameAs(throwable);
  }

  @Test
  void letLongRecStaticMethod() throws Throwable {
    final long value = 100L;
    final long result = 999L;
    final AtomicLong valueRef = new AtomicLong();
    final AtomicReference<ThLongToLongFunction<Throwable>> funcRef = new AtomicReference<>();
    final ThLongObjToLongFunction<ThLongToLongFunction<Throwable>, Throwable> block = (arg1, arg2) -> {
      valueRef.set(arg1);
      funcRef.set(arg2);
      return result;
    };

    assertThat(JKScope.letLongRec(value, block))
      .isEqualTo(result);
    assertThat(valueRef.get())
      .isEqualTo(value);

    final ThLongToLongFunction<Throwable> func = funcRef.get();
    valueRef.set(0L);
    funcRef.set(null);

    assertThat(func.apply(value))
      .isEqualTo(result);
    assertThat(valueRef.get())
      .isEqualTo(value);
  }

  @Test
  void letLongRecStaticMethodThrowsException() {
    final long value = 100L;
    final Throwable throwable = new Throwable();
    final ThLongObjToLongFunction<ThLongToLongFunction<Throwable>, Throwable> block =
      (arg1, arg2) -> { throw throwable; };

    assertThatThrownBy(() -> JKScope.letLongRec(value, block))
      .isSameAs(throwable);
  }

  @Test
  void letDoubleRecStaticMethod() throws Throwable {
    final double value = 100.0;
    final double result = 999.0;
    final AtomicReference<Double> valueRef = new AtomicReference<>();
    final AtomicReference<ThDoubleToDoubleFunction<Throwable>> funcRef = new AtomicReference<>();
    final ThDoubleObjToDoubleFunction<ThDoubleToDoubleFunction<Throwable>, Throwable> block = (arg1, arg2) -> {
      valueRef.set(arg1);
      funcRef.set(arg2);
      return result;
    };

    assertThat(JKScope.letDoubleRec(value, block))
      .isEqualTo(result);
    assertThat(valueRef.get())
      .isEqualTo(value);

    final ThDoubleToDoubleFunction<Throwable> func = funcRef.get();
    valueRef.set(null);
    funcRef.set(null);

    assertThat(func.apply(value))
      .isEqualTo(result);
    assertThat(valueRef.get())
      .isEqualTo(value);
  }

  @Test
  void letDoubleRecStaticMethodThrowsException() {
    final double value = 100.0;
    final Throwable throwable = new Throwable();
    final ThDoubleObjToDoubleFunction<ThDoubleToDoubleFunction<Throwable>, Throwable> block =
      (arg1, arg2) -> { throw throwable; };

    assertThatThrownBy(() -> JKScope.letDoubleRec(value, block))
      .isSameAs(throwable);
  }

  @Test
  void letWithStaticMethod() {
    final Object value = new Object();
    final Object result = new Object();
    final AtomicReference<Object> valueRef = new AtomicReference<>();
    final ThFunction<Object, Object, Throwable> block = arg -> {
      valueRef.set(arg);
      return result;
    };

    assertThat(JKScope.letWith(value, block))
      .isSameAs(result);
    assertThat(valueRef.get())
      .isSameAs(value);
  }

  @Test
  void letWithStaticMethodThrowsException() {
    final Object value = new Object();
    final Throwable throwable = new Throwable();
    final ThFunction<Object, Object, Throwable> block = arg -> { throw throwable; };

    assertThatThrownBy(() -> JKScope.letWith(value, block))
      .isSameAs(throwable);
  }

  @Test
  void letIntWithStaticMethod() {
    final Object value = new Object();
    final int result = 111;
    final AtomicReference<Object> valueRef = new AtomicReference<>();
    final ThToIntFunction<Object, Throwable> block = arg -> {
      valueRef.set(arg);
      return result;
    };

    assertThat(JKScope.letIntWith(value, block))
      .isEqualTo(result);
    assertThat(valueRef.get())
      .isSameAs(value);
  }

  @Test
  void letIntWithStaticMethodThrowsException() {
    final Object value = new Object();
    final Throwable throwable = new Throwable();
    final ThToIntFunction<Object, Throwable> block = arg -> { throw throwable; };

    assertThatThrownBy(() -> JKScope.letIntWith(value, block))
      .isSameAs(throwable);
  }

  @Test
  void letLongWithStaticMethod() {
    final Object value = new Object();
    final long result = 111L;
    final AtomicReference<Object> valueRef = new AtomicReference<>();
    final ThToLongFunction<Object, Throwable> block = arg -> {
      valueRef.set(arg);
      return result;
    };

    assertThat(JKScope.letLongWith(value, block))
      .isEqualTo(result);
    assertThat(valueRef.get())
      .isSameAs(value);
  }

  @Test
  void letLongWithStaticMethodThrowsException() {
    final Object value = new Object();
    final Throwable throwable = new Throwable();
    final ThToLongFunction<Object, Throwable> block = arg -> { throw throwable; };

    assertThatThrownBy(() -> JKScope.letLongWith(value, block))
      .isSameAs(throwable);
  }

  @Test
  void letDoubleWithStaticMethod() {
    final Object value = new Object();
    final double result = 111.0;
    final AtomicReference<Object> valueRef = new AtomicReference<>();
    final ThToDoubleFunction<Object, Throwable> block = arg -> {
      valueRef.set(arg);
      return result;
    };

    assertThat(JKScope.letDoubleWith(value, block))
      .isEqualTo(result);
    assertThat(valueRef.get())
      .isSameAs(value);
  }

  @Test
  void letDoubleWithStaticMethodThrowsException() {
    final Object value = new Object();
    final Throwable throwable = new Throwable();
    final ThToDoubleFunction<Object, Throwable> block = arg -> { throw throwable; };

    assertThatThrownBy(() -> JKScope.letDoubleWith(value, block))
      .isSameAs(throwable);
  }

  @Test
  void letWithResourceMethod() {
    final AutoCloseableImpl value = new AutoCloseableImpl();
    final Object result = new Object();
    final AtomicReference<Object> valueRef = new AtomicReference<>();
    final ThFunction<Object, Object, Throwable> block = arg -> {
      valueRef.set(arg);
      return result;
    };

    assertThat(JKScope.letWithResource(value, block))
      .isSameAs(result);
    assertThat(valueRef.get())
      .isSameAs(value);
    assertThat(value.isClosed())
      .isTrue();
  }

  @Test
  void letWithResourceMethodThrowsException() {
    final AutoCloseableImpl value = new AutoCloseableImpl();
    final Throwable throwable = new Throwable();
    final ThFunction<Object, Object, Throwable> block = arg -> { throw throwable; };

    assertThatThrownBy(() -> JKScope.letWithResource(value, block))
      .isSameAs(throwable);
    assertThat(value.isClosed())
      .isTrue();
  }

  @Test
  void letWith2ArgsStaticMethod() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object result = new Object();
    final AtomicReference<Object> value1Ref = new AtomicReference<>();
    final AtomicReference<Object> value2Ref = new AtomicReference<>();
    final ThBiFunction<Object, Object, Object, Throwable> block = (arg1, arg2) -> {
      value1Ref.set(arg1);
      value2Ref.set(arg2);
      return result;
    };

    assertThat(JKScope.letWith(value1, value2, block))
      .isSameAs(result);
    assertThat(value1Ref.get())
      .isSameAs(value1);
    assertThat(value2Ref.get())
      .isSameAs(value2);
  }

  @Test
  void letWith2ArgsStaticMethodThrowsException() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Throwable throwable = new Throwable();
    final ThBiFunction<Object, Object, Object, Throwable> block = (arg1, arg2) -> { throw throwable; };

    assertThatThrownBy(() -> JKScope.letWith(value1, value2, block))
      .isSameAs(throwable);
  }

  @Test
  void letWith3ArgsStaticMethod() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final Object result = new Object();
    final AtomicReference<Object> value1Ref = new AtomicReference<>();
    final AtomicReference<Object> value2Ref = new AtomicReference<>();
    final AtomicReference<Object> value3Ref = new AtomicReference<>();
    final ThTriFunction<Object, Object, Object, Object, Throwable> block = (arg1, arg2, arg3) -> {
      value1Ref.set(arg1);
      value2Ref.set(arg2);
      value3Ref.set(arg3);
      return result;
    };

    assertThat(JKScope.letWith(value1, value2, value3, block))
      .isSameAs(result);
    assertThat(value1Ref.get())
      .isSameAs(value1);
    assertThat(value2Ref.get())
      .isSameAs(value2);
    assertThat(value3Ref.get())
      .isSameAs(value3);
  }

  @Test
  void letWith3ArgsStaticMethodThrowsException() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final Throwable throwable = new Throwable();
    final ThTriFunction<Object, Object, Object, Object, Throwable> block = (arg1, arg2, arg3) -> { throw throwable; };

    assertThatThrownBy(() -> JKScope.letWith(value1, value2, value3, block))
      .isSameAs(throwable);
  }

  //endregion

  private static final class Impl implements JKScope<Impl> {
  }

  private static final class AutoCloseableImpl implements AutoCloseable {
    private boolean isClosed = false;

    @Override
    public void close() throws Exception {
      this.isClosed = true;
    }

    public boolean isClosed() {
      return this.isClosed;
    }
  }
}
