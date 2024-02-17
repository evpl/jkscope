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
 * Tests for {@link ThIntConsumer}.
 */
final class ThIntConsumerTest {

  @Test
  void asUncheckedMethod() {
    final int value = 100;
    final AtomicInteger valueRef = new AtomicInteger();
    final Throwable throwable = new Throwable();
    final ThIntConsumer<Throwable> origin = arg -> {
      valueRef.set(arg);
      throw throwable;
    };

    final ThIntConsumer<RuntimeException> unchecked = origin.asUnchecked();
    assertThatThrownBy(() -> unchecked.accept(value))
      .isSameAs(throwable);
    assertThat(valueRef.get())
      .isEqualTo(value);
  }
}
