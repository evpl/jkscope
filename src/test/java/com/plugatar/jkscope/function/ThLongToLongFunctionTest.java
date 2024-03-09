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

import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for {@link ThLongToLongFunction}.
 */
final class ThLongToLongFunctionTest {

  @Test
  void uncheckedStaticMethodThrowsNPEForNullArg() {
    final ThLongToLongFunction<Throwable> origin = null;

    assertThatThrownBy(() -> ThLongToLongFunction.unchecked(origin))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void uncheckedStaticMethod() {
    final long value = 100;
    final AtomicLong valueRef = new AtomicLong();
    final long result = 999;
    final ThLongToLongFunction<Throwable> origin = arg -> {
      valueRef.set(arg);
      return result;
    };

    final ThLongToLongFunction<RuntimeException> unchecked = ThLongToLongFunction.unchecked(origin);
    assertThat(unchecked.apply(value))
      .isEqualTo(result);
    assertThat(valueRef.get())
      .isEqualTo(value);
  }

  @Test
  void uncheckedStaticMethodThrowsException() {
    final long value = 100;
    final Throwable throwable = new Throwable();
    final ThLongToLongFunction<Throwable> origin = arg -> { throw throwable; };

    final ThLongToLongFunction<RuntimeException> unchecked = ThLongToLongFunction.unchecked(origin);
    assertThatThrownBy(() -> unchecked.apply(value))
      .isSameAs(throwable);
  }
}
