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
 * Tests for {@link ThTriConsumer}.
 */
final class ThTriConsumerTest {

  @Test
  void asUncheckedMethod() {
    final Object consumerArg1 = new Object();
    final Object consumerArg2 = new Object();
    final Object consumerArg3 = new Object();
    final AtomicReference<Object> argRef1 = new AtomicReference<>();
    final AtomicReference<Object> argRef2 = new AtomicReference<>();
    final AtomicReference<Object> argRef3 = new AtomicReference<>();
    final Throwable throwable = new Throwable();
    final ThTriConsumer<Object, Object, Object, Throwable> originConsumer = (arg1, arg2, arg3) -> {
      argRef1.set(arg1);
      argRef2.set(arg2);
      argRef3.set(arg3);
      throw throwable;
    };

    final ThTriConsumer<Object, Object, Object, RuntimeException> unchecked = originConsumer.asUnchecked();
    assertThatThrownBy(() -> unchecked.accept(consumerArg1, consumerArg2, consumerArg3))
      .isSameAs(throwable);
    assertThat(argRef1.get())
      .isSameAs(consumerArg1);
    assertThat(argRef2.get())
      .isSameAs(consumerArg2);
    assertThat(argRef3.get())
      .isSameAs(consumerArg3);
  }
}
