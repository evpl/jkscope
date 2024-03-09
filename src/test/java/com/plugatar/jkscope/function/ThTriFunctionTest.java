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
package com.plugatar.jkscope.function;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for {@link ThTriFunction}.
 */
final class ThTriFunctionTest {

  @Test
  void uncheckedStaticMethodThrowsNPEForNullArg() {
    final ThTriFunction<Object, Object, Object, Object, Throwable> origin = null;

    assertThatThrownBy(() -> ThTriFunction.unchecked(origin))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void uncheckedStaticMethod() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final AtomicReference<Object> valueRef1 = new AtomicReference<>();
    final AtomicReference<Object> valueRef2 = new AtomicReference<>();
    final AtomicReference<Object> valueRef3 = new AtomicReference<>();
    final Object functionResult = new Object();
    final ThTriFunction<Object, Object, Object, Object, Throwable> origin = (arg1, arg2, arg3) -> {
      valueRef1.set(value1);
      valueRef2.set(value2);
      valueRef3.set(value3);
      return functionResult;
    };

    final ThTriFunction<Object, Object, Object, Object, RuntimeException> unchecked = ThTriFunction.unchecked(origin);
    assertThat(unchecked.apply(value1, value2, value3))
      .isSameAs(functionResult);
    assertThat(valueRef1.get())
      .isSameAs(value1);
    assertThat(valueRef2.get())
      .isSameAs(value2);
    assertThat(valueRef3.get())
      .isSameAs(value3);
  }

  @Test
  void uncheckedStaticMethodThrowsException() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final Throwable throwable = new Throwable();
    final ThTriFunction<Object, Object, Object, Object, Throwable> origin = (arg1, arg2, arg3) -> {
      throw throwable;
    };

    final ThTriFunction<Object, Object, Object, Object, RuntimeException> unchecked = ThTriFunction.unchecked(origin);
    assertThatThrownBy(() -> unchecked.apply(value1, value2, value3))
      .isSameAs(throwable);
  }
}
