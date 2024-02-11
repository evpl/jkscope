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
 * Tests for {@link ThConsumer}.
 */
final class ThConsumerTest {

  @Test
  void asUncheckedMethod() {
    final Object consumerArg = new Object();
    final AtomicReference<Object> argRef = new AtomicReference<>();
    final Throwable throwable = new Throwable();
    final ThConsumer<Object, Throwable> originConsumer = arg -> {
      argRef.set(arg);
      throw throwable;
    };

    final ThConsumer<Object, RuntimeException> unchecked = originConsumer.asUnchecked();
    assertThatThrownBy(() -> unchecked.accept(consumerArg))
      .isSameAs(throwable);
    assertThat(argRef.get())
      .isSameAs(consumerArg);
  }
}
