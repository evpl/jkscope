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
 * Tests for {@link ThToIntFunction}.
 */
final class ThToIntFunctionTest {

  @Test
  void uncheckedStaticMethodThrowsNPEForNullArg() {
    final ThToIntFunction<Object, Throwable> origin = null;

    assertThatThrownBy(() -> ThToIntFunction.unchecked(origin))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void uncheckedStaticMethod() {
    final Object value = new Object();
    final AtomicReference<Object> valueRef = new AtomicReference<>();
    final int result = 111;
    final ThToIntFunction<Object, Throwable> origin = arg -> {
      valueRef.set(arg);
      return result;
    };

    final ThToIntFunction<Object, RuntimeException> unchecked = ThToIntFunction.unchecked(origin);
    assertThat(unchecked.apply(value))
      .isEqualTo(result);
    assertThat(valueRef.get())
      .isSameAs(value);
  }

  @Test
  void uncheckedStaticMethodThrowsException() {
    final Object value = new Object();
    final Throwable throwable = new Throwable();
    final ThToIntFunction<Object, Throwable> origin = arg -> { throw throwable; };

    final ThToIntFunction<Object, RuntimeException> unchecked = ThToIntFunction.unchecked(origin);
    assertThatThrownBy(() -> unchecked.apply(value))
      .isSameAs(throwable);
  }
}
