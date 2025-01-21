/*
 * Copyright 2025 Evgenii Plugatar
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

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for methods {@link Throw}.
 */
final class ThrowTest {

  @Test
  void uncheckedMethodThrowsNPEForNullArg() {
    final Throwable exception = null;

    assertThatThrownBy(() ->
      Throw.unchecked(exception)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void uncheckedMethodThrowsException() {
    final Throwable exception = new Throwable();

    assertThatThrownBy(() ->
      Throw.unchecked(exception)
    ).isSameAs(exception);
  }

  @Test
  void uncheckedMethodCompatibleWithThrowSyntax() {
    final Throwable exception = new Throwable();

    assertThatThrownBy(() -> {
      throw Throw.unchecked(exception);
    }).isSameAs(exception);
  }
}
