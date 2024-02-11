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
package com.plugatar.jkscope;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link JKScopeOpt}.
 */
final class JKScopeOptTest {

  @Test
  void asOptionalMethodForEmpty() {
    final JKScopeOpt<Object> jkScopeOpt = JKScopeOpt.empty();

    assertThat(jkScopeOpt.asOptional())
      .isEmpty();
  }

  @Test
  void asOptionalMethodForNonEmpty() {
    final Object value = new Object();
    final JKScopeOpt<Object> jkScopeOpt = JKScopeOpt.of(value);

    final Optional<Object> optional = jkScopeOpt.asOptional();
    assertThat(optional)
      .isNotEmpty()
      .containsSame(value);
  }
}
