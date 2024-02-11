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
  void asUncheckedMethodExceptionLambdaResult() {
    final Object functionArg1 = new Object();
    final Object functionArg2 = new Object();
    final Object functionArg3 = new Object();
    final Throwable throwable = new Throwable();
    final ThTriFunction<Object, Object, Object, Object, Throwable> originFunction = (arg1, arg2, arg3) -> {
      throw throwable;
    };

    final ThTriFunction<Object, Object, Object, Object, RuntimeException> unchecked = originFunction.asUnchecked();
    assertThatThrownBy(() -> unchecked.apply(functionArg1, functionArg2, functionArg3))
      .isSameAs(throwable);
  }

  @Test
  void asUncheckedMethodLambdaResult() {
    final Object functionArg1 = new Object();
    final Object functionArg2 = new Object();
    final Object functionArg3 = new Object();
    final AtomicReference<Object> argRef1 = new AtomicReference<>();
    final AtomicReference<Object> argRef2 = new AtomicReference<>();
    final AtomicReference<Object> argRef3 = new AtomicReference<>();
    final Object functionResult = new Object();
    final ThTriFunction<Object, Object, Object, Object, Throwable> originFunction = (arg1, arg2, arg3) -> {
      argRef1.set(functionArg1);
      argRef2.set(functionArg2);
      argRef3.set(functionArg3);
      return functionResult;
    };

    final ThTriFunction<Object, Object, Object, Object, RuntimeException> unchecked = originFunction.asUnchecked();
    assertThat(unchecked.apply(functionArg1, functionArg2, functionArg3))
      .isSameAs(functionResult);
    assertThat(argRef1.get())
      .isSameAs(functionArg1);
    assertThat(argRef2.get())
      .isSameAs(functionArg2);
    assertThat(argRef3.get())
      .isSameAs(functionArg3);
  }
}
