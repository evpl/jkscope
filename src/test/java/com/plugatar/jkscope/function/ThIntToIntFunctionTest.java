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

import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for {@link ThIntToIntFunction}.
 */
final class ThIntToIntFunctionTest {

  @Test
  void uncheckedStaticMethodThrowsNPEForNullArg() {
    final ThIntToIntFunction<Throwable> origin = null;

    assertThatThrownBy(() -> ThIntToIntFunction.unchecked(origin))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void uncheckedStaticMethod() {
    final int value = 100;
    final AtomicInteger valueRef = new AtomicInteger();
    final int result = 999;
    final ThIntToIntFunction<Throwable> origin = arg -> {
      valueRef.set(arg);
      return result;
    };

    final ThIntToIntFunction<RuntimeException> unchecked = ThIntToIntFunction.unchecked(origin);
    assertThat(unchecked.apply(value))
      .isEqualTo(result);
    assertThat(valueRef.get())
      .isEqualTo(value);
  }

  @Test
  void uncheckedStaticMethodThrowsException() {
    final int value = 100;
    final Throwable throwable = new Throwable();
    final ThIntToIntFunction<Throwable> origin = arg -> { throw throwable; };

    final ThIntToIntFunction<RuntimeException> unchecked = ThIntToIntFunction.unchecked(origin);
    assertThatThrownBy(() -> unchecked.apply(value))
      .isSameAs(throwable);
  }
}
