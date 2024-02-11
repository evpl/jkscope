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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for {@link ThSupplier}.
 */
final class ThSupplierTest {

  @Test
  void asUncheckedMethodExceptionLambdaResult() {
    final Throwable throwable = new Throwable();
    final ThSupplier<Object, Throwable> originSupplier = () -> { throw throwable; };

    final ThSupplier<Object, RuntimeException> unchecked = originSupplier.asUnchecked();
    assertThatThrownBy(() -> unchecked.get())
      .isSameAs(throwable);
  }

  @Test
  void asUncheckedMethodLambdaResult() {
    final Object supplierResult = new Object();
    final ThSupplier<Object, Throwable> originSupplier = () -> supplierResult;

    final ThSupplier<Object, RuntimeException> unchecked = originSupplier.asUnchecked();
    assertThat(unchecked.get())
      .isSameAs(supplierResult);
  }
}
