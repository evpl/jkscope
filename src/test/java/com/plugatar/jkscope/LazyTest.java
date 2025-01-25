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
package com.plugatar.jkscope;

import com.plugatar.jkscope.function.ThSupplier;
import org.junit.jupiter.api.Test;

import static com.plugatar.jkscope.JKScope.lazy;
import static com.plugatar.jkscope.JKScope.lazyOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests for methods:
 * <ul>
 * <li>{@link JKScope#lazy(ThSupplier)}</li>
 * <li>{@link JKScope#lazy(Object, ThSupplier)}</li>
 * <li>{@link JKScope#lazy(Lazy.ThreadSafetyMode, ThSupplier)}</li>
 * <li>{@link JKScope#lazyOf(Object)}</li>
 * </ul>
 */
@SuppressWarnings("unchecked")
final class LazyTest {

  @Test
  void lazyMethodWithSupplierThrowsNPEForNullInitializerArg() {
    final ThSupplier<Object, Error> initializer = null;

    assertThatThrownBy(() ->
      lazy(initializer)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void lazyMethodWithSupplier() {
    final Object result = new Object();
    final ThSupplier<Object, Error> initializer = mock(ThSupplier.class);
    doReturn(result).when(initializer).get();
    final Lazy<Object> lazy = lazy(initializer);

    assertThat(
      lazy.get()
    ).isSameAs(result);
    verify(initializer, times(1)).get();
    assertThat(
      lazy.get()
    ).isSameAs(result);
    verify(initializer, times(1)).get();
  }

  @Test
  void lazyMethodWithLockAndSupplierThrowsNPEForNullLockArg() {
    final Object lock = null;
    final ThSupplier<Object, Error> initializer = mock(ThSupplier.class);

    assertThatThrownBy(() ->
      lazy(lock, initializer)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void lazyMethodWithLockAndSupplierThrowsNPEForNullInitializerArg() {
    final Object lock = new Object();
    final ThSupplier<Object, Error> initializer = null;

    assertThatThrownBy(() ->
      lazy(lock, initializer)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void lazyMethodWithLockAndSupplier() {
    final Object lock = new Object();
    final Object result = new Object();
    final ThSupplier<Object, Error> initializer = mock(ThSupplier.class);
    doReturn(result).when(initializer).get();
    final Lazy<Object> lazy = lazy(lock, initializer);

    assertThat(
      lazy.get()
    ).isSameAs(result);
    verify(initializer, times(1)).get();
    assertThat(
      lazy.get()
    ).isSameAs(result);
    verify(initializer, times(1)).get();
  }

  @Test
  void lazyMethodWithThreadSafetyModeAndSupplierThrowsNPEForNullThreadSafetyModeArg() {
    final Lazy.ThreadSafetyMode threadSafetyMode = null;
    final ThSupplier<Object, Error> initializer = mock(ThSupplier.class);

    assertThatThrownBy(() ->
      lazy(threadSafetyMode, initializer)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void lazyMethodWithThreadSafetyModeAndSupplierThrowsNPEForNullInitializerArg() {
    final Lazy.ThreadSafetyMode threadSafetyMode = Lazy.ThreadSafetyMode.NONE;
    final ThSupplier<Object, Error> initializer = null;

    assertThatThrownBy(() ->
      lazy(threadSafetyMode, initializer)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void lazyMethodWithThreadSafetyModeAndSupplierSynchronizedMode() {
    final Object result = new Object();
    final ThSupplier<Object, Error> initializer = mock(ThSupplier.class);
    doReturn(result).when(initializer).get();
    final Lazy<Object> lazy = lazy(Lazy.ThreadSafetyMode.SYNCHRONIZED, initializer);

    assertThat(
      lazy.get()
    ).isSameAs(result);
    verify(initializer, times(1)).get();
    assertThat(
      lazy.get()
    ).isSameAs(result);
    verify(initializer, times(1)).get();
  }

  @Test
  void lazyMethodWithThreadSafetyModeAndSupplierPublicationMode() {
    final Object result = new Object();
    final ThSupplier<Object, Error> initializer = mock(ThSupplier.class);
    doReturn(result).when(initializer).get();
    final Lazy<Object> lazy = lazy(Lazy.ThreadSafetyMode.PUBLICATION, initializer);

    assertThat(
      lazy.get()
    ).isSameAs(result);
    verify(initializer, times(1)).get();
    assertThat(
      lazy.get()
    ).isSameAs(result);
    verify(initializer, times(1)).get();
  }

  @Test
  void lazyMethodWithThreadSafetyModeAndSupplierUnsafeMode() {
    final Object result = new Object();
    final ThSupplier<Object, Error> initializer = mock(ThSupplier.class);
    doReturn(result).when(initializer).get();
    final Lazy<Object> lazy = lazy(Lazy.ThreadSafetyMode.NONE, initializer);

    assertThat(
      lazy.get()
    ).isSameAs(result);
    verify(initializer, times(1)).get();
    assertThat(
      lazy.get()
    ).isSameAs(result);
    verify(initializer, times(1)).get();
  }

  @Test
  void lazyOfMethod() {
    final Object result = new Object();
    final Lazy<Object> lazy = lazyOf(result);

    assertThat(
      lazy.get()
    ).isSameAs(result);
    assertThat(
      lazy.get()
    ).isSameAs(result);
  }
}
