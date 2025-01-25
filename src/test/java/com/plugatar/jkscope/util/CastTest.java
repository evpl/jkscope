/*
 * Copyright 2024-2025 Evgenii Plugatar
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
package com.plugatar.jkscope.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for methods {@link Cast}.
 */
final class CastTest {

  @Test
  void unsafeMethodDoesNotThrowsNPEForNullArg() {
    final Object value = null;

    assertThatCode(() ->
      Cast.unsafe(value)
    ).doesNotThrowAnyException();
  }

  @Test
  void unsafeMethodCastIncompatibleClasses() {
    final Integer value = 1;

    assertThatThrownBy(() -> {
      final String result = Cast.unsafe(value);
    }).isInstanceOf(ClassCastException.class);
  }

  @Test
  void unsafeMethodDowncast() {
    final Number value = 1;

    assertThatCode(() -> {
      Integer result = Cast.unsafe(value);
    }).doesNotThrowAnyException();
  }

  @Test
  void unsafeMethodUpcast() {
    final Integer value = 1;

    assertThatCode(() -> {
      Number result = Cast.unsafe(value);
    }).doesNotThrowAnyException();
  }
}
