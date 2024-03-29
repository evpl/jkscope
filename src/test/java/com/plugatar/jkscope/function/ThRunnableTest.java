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

import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for {@link ThRunnable}.
 */
final class ThRunnableTest {

  @Test
  void uncheckedStaticMethodThrowsNPEForNullArg() {
    final ThRunnable<Throwable> origin = null;

    assertThatThrownBy(() -> ThRunnable.unchecked(origin))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void uncheckedStaticMethod() {
    final AtomicBoolean sideEffect = new AtomicBoolean(false);
    final Throwable throwable = new Throwable();
    final ThRunnable<Throwable> origin = () -> {
      sideEffect.set(true);
      throw throwable;
    };

    final ThRunnable<RuntimeException> unchecked = ThRunnable.unchecked(origin);
    assertThatThrownBy(() -> unchecked.run())
      .isSameAs(throwable);
    assertThat(sideEffect.get())
      .isTrue();
  }
}
