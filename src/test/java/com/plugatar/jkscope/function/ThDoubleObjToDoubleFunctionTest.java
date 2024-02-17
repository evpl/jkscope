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
 * Tests for {@link ThDoubleObjToDoubleFunction}.
 */
final class ThDoubleObjToDoubleFunctionTest {

  @Test
  void asUncheckedMethod() {
    final double value1 = 100.0;
    final Object value2 = new Object();
    final AtomicReference<Double> value1Ref = new AtomicReference();
    final AtomicReference<Object> value2Ref = new AtomicReference<>();
    final double result = 999.0;
    final ThDoubleObjToDoubleFunction<Object, Throwable> origin = (arg1, arg2) -> {
      value1Ref.set(arg1);
      value2Ref.set(arg2);
      return result;
    };

    final ThDoubleObjToDoubleFunction<Object, RuntimeException> unchecked = origin.asUnchecked();
    assertThat(unchecked.apply(value1, value2))
      .isEqualTo(result);
    assertThat(value1Ref.get())
      .isEqualTo(value1);
    assertThat(value2Ref.get())
      .isSameAs(value2);
  }

  @Test
  void asUncheckedMethodThrowsException() {
    final double value1 = 100.0;
    final Object value2 = new Object();
    final Throwable throwable = new Throwable();
    final ThDoubleObjToDoubleFunction<Object, Throwable> origin = (arg1, arg2) -> { throw throwable; };

    final ThDoubleObjToDoubleFunction<Object, RuntimeException> unchecked = origin.asUnchecked();
    assertThatThrownBy(() -> unchecked.apply(value1, value2))
      .isSameAs(throwable);
  }
}
