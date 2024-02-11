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
import com.plugatar.jkscope.function.ThRunnable;
import com.plugatar.jkscope.function.ThSupplier;
import com.plugatar.jkscope.function.ThTriConsumer;
import com.plugatar.jkscope.function.ThTriFunction;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static com.plugatar.jkscope.JKScope.withNonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for {@link JKScope}.
 */
final class JKScopeTest {

  //region NPE check for instance methods

  @Test
  void letInstanceMethodThrowsNPEForNullArg() {
    final JKScope<?> jkScope = new JKScopeImpl();
    final ThConsumer<Object, Throwable> block = null;

    assertThatThrownBy(() -> jkScope.let(block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void runInstanceMethodThrowsNPEForNullArg() {
    final JKScope<?> jkScope = new JKScopeImpl();
    final ThFunction<Object, Object, Throwable> block = null;

    assertThatThrownBy(() -> jkScope.run(block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void alsoInstanceMethodThrowsNPEForNullArg() {
    final JKScope<?> jkScope = new JKScopeImpl();
    final ThConsumer<Object, Throwable> block = null;

    assertThatThrownBy(() -> jkScope.also(block))
      .isInstanceOf(NullPointerException.class);
  }

  //endregion

  //region logic check for instance methods

  @Test
  void letInstanceMethod() {
    final JKScope<?> jkScope = new JKScopeImpl();
    final AtomicReference<Object> thisRef = new AtomicReference<>();
    final Throwable throwable = new Throwable();
    final ThConsumer<Object, Throwable> block = arg -> {
      thisRef.set(arg);
      throw throwable;
    };

    assertThatThrownBy(() -> jkScope.let(block))
      .isSameAs(throwable);
    assertThat(thisRef.get())
      .isSameAs(jkScope);
  }

  @Test
  void runInstanceMethodException() {
    final JKScope<?> jkScope = new JKScopeImpl();
    final Throwable throwable = new Throwable();
    final ThFunction<Object, Object, Throwable> block = arg -> { throw throwable; };

    assertThatThrownBy(() -> jkScope.run(block))
      .isSameAs(throwable);
  }

  @Test
  void runInstanceMethodResult() {
    final JKScope<?> jkScope = new JKScopeImpl();
    final AtomicReference<Object> thisRef = new AtomicReference<>();
    final Object blockResult = new Object();
    final ThFunction<Object, Object, Throwable> block = arg -> {
      thisRef.set(arg);
      return blockResult;
    };

    assertThat(jkScope.run(block))
      .isSameAs(blockResult);
    assertThat(thisRef.get())
      .isSameAs(jkScope);
  }

  @Test
  void alsoInstanceMethodException() {
    final JKScope<?> jkScope = new JKScopeImpl();
    final Throwable throwable = new Throwable();
    final ThConsumer<Object, Throwable> block = arg -> { throw throwable; };

    assertThatThrownBy(() -> jkScope.also(block))
      .isSameAs(throwable);
  }

  @Test
  void alsoInstanceMethodResult() {
    final JKScope<?> jkScope = new JKScopeImpl();
    final AtomicReference<Object> thisRef = new AtomicReference<>();
    final ThConsumer<Object, Throwable> block = arg -> thisRef.set(arg);

    assertThat(jkScope.also(block))
      .isSameAs(jkScope);
    assertThat(thisRef.get())
      .isSameAs(jkScope);
  }

  @Test
  void applyInstanceMethodException() {
    final JKScope<?> jkScope = new JKScopeImpl();
    final Throwable throwable = new Throwable();
    final ThConsumer<Object, Throwable> block = arg -> { throw throwable; };

    assertThatThrownBy(() -> jkScope.apply(block))
      .isSameAs(throwable);
  }

  @Test
  void applyInstanceMethodResult() {
    final JKScope<?> jkScope = new JKScopeImpl();
    final AtomicReference<Object> thisRef = new AtomicReference<>();
    final ThConsumer<Object, Throwable> block = arg -> thisRef.set(arg);

    assertThat(jkScope.apply(block))
      .isSameAs(jkScope);
    assertThat(thisRef.get())
      .isSameAs(jkScope);
  }

  //endregion

  //region NPE check for static methods

  @Test
  void applyInstanceMethodThrowsNPEForNullArg() {
    final JKScope<?> jkScope = new JKScopeImpl();
    final ThConsumer<Object, Throwable> block = null;

    assertThatThrownBy(() -> jkScope.apply(block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void letStaticMethodThrowsNPEForNullArg() {
    final ThRunnable<Throwable> block = null;

    assertThatThrownBy(() -> JKScope.let(block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void runStaticMethodThrowsNPEForNullArg() {
    final ThSupplier<Object, Throwable> block = null;

    assertThatThrownBy(() -> JKScope.run(block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void withThConsumerStaticMethodThrowsNPEForNullArg() {
    final ThConsumer<Object, Throwable> block = null;
    final Object arg = new Object();

    assertThatThrownBy(() -> JKScope.with(arg, block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void withThBiConsumerStaticMethodThrowsNPEForNullArg() {
    final ThBiConsumer<Object, Object, Throwable> block = null;
    final Object arg1 = new Object();
    final Object arg2 = new Object();

    assertThatThrownBy(() -> JKScope.with(arg1, arg2, block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void withThTriConsumerStaticMethodThrowsNPEForNullArg() {
    final ThTriConsumer<Object, Object, Object, Throwable> block = null;
    final Object arg1 = new Object();
    final Object arg2 = new Object();
    final Object arg3 = new Object();

    assertThatThrownBy(() -> JKScope.with(arg1, arg2, arg3, block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void withThFunctionStaticMethodThrowsNPEForNullArg() {
    final ThFunction<Object, Object, Throwable> block = null;
    final Object arg = new Object();

    assertThatThrownBy(() -> JKScope.with(arg, block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void withThBiFunctionStaticMethodThrowsNPEForNullArg() {
    final ThBiFunction<Object, Object, Object, Throwable> block = null;
    final Object arg1 = new Object();
    final Object arg2 = new Object();

    assertThatThrownBy(() -> JKScope.with(arg1, arg2, block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void withThTriFunctionStaticMethodThrowsNPEForNullArg() {
    final ThTriFunction<Object, Object, Object, Object, Throwable> block = null;
    final Object arg1 = new Object();
    final Object arg2 = new Object();
    final Object arg3 = new Object();

    assertThatThrownBy(() -> JKScope.with(arg1, arg2, arg3, block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void withNonNullThConsumerStaticMethodThrowsNPEForNullArg() {
    final ThConsumer<Object, Throwable> block = null;
    final Object arg = new Object();

    assertThatThrownBy(() -> withNonNull(arg, block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void withNonNullThBiConsumerStaticMethodThrowsNPEForNullArg() {
    final ThBiConsumer<Object, Object, Throwable> block = null;
    final Object arg1 = new Object();
    final Object arg2 = new Object();

    assertThatThrownBy(() -> withNonNull(arg1, arg2, block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void withNonNullThTriConsumerStaticMethodThrowsNPEForNullArg() {
    final ThTriConsumer<Object, Object, Object, Throwable> block = null;
    final Object arg1 = new Object();
    final Object arg2 = new Object();
    final Object arg3 = new Object();

    assertThatThrownBy(() -> withNonNull(arg1, arg2, arg3, block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void withNonNullThFunctionStaticMethodThrowsNPEForNullArg() {
    final ThFunction<Object, Object, Throwable> block = null;
    final Object arg = new Object();

    assertThatThrownBy(() -> withNonNull(arg, block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void withNonNullThBiFunctionStaticMethodThrowsNPEForNullArg() {
    final ThBiFunction<Object, Object, Object, Throwable> block = null;
    final Object arg1 = new Object();
    final Object arg2 = new Object();

    assertThatThrownBy(() -> withNonNull(arg1, arg2, block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void withNonNullThTriFunctionStaticMethodThrowsNPEForNullArg() {
    final ThTriFunction<Object, Object, Object, Object, Throwable> block = null;
    final Object arg1 = new Object();
    final Object arg2 = new Object();
    final Object arg3 = new Object();

    assertThatThrownBy(() -> withNonNull(arg1, arg2, arg3, block))
      .isInstanceOf(NullPointerException.class);
  }

  //endregion

  //region logic check for static methods

  @Test
  void letStaticMethod() {
    final AtomicBoolean sideEffect = new AtomicBoolean(false);
    final Throwable throwable = new Throwable();
    final ThRunnable<Throwable> block = () -> {
      sideEffect.set(true);
      throw throwable;
    };

    assertThatThrownBy(() -> JKScope.let(block))
      .isSameAs(throwable);
    assertThat(sideEffect.get())
      .isTrue();
  }

  @Test
  void runStaticMethodException() {
    final Throwable throwable = new Throwable();
    final ThSupplier<Object, Throwable> block = () -> { throw throwable; };

    assertThatThrownBy(() -> JKScope.run(block))
      .isSameAs(throwable);
  }

  @Test
  void runStaticMethodResult() {
    final Object supplierResult = new Object();
    final ThSupplier<Object, Throwable> block = () -> supplierResult;

    assertThat(JKScope.run(block))
      .isSameAs(supplierResult);
  }

  //endregion

  private static final class JKScopeImpl implements JKScope<JKScopeImpl> {
  }
}
