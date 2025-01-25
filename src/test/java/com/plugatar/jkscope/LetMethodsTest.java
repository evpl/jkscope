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

import com.plugatar.jkscope.function.Th2Function;
import com.plugatar.jkscope.function.Th3Function;
import com.plugatar.jkscope.function.Th4Function;
import com.plugatar.jkscope.function.Th5Function;
import com.plugatar.jkscope.function.ThFunction;
import com.plugatar.jkscope.function.ThSupplier;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static com.plugatar.jkscope.JKScope.let;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests for methods:
 * <ul>
 * <li>{@link JKScope#let(ThSupplier)}</li>
 * <li>{@link JKScope#let(Object, ThFunction)}</li>
 * <li>{@link JKScope#let(Object, Object, Th2Function)}</li>
 * <li>{@link JKScope#let(Object, Object, Object, Th3Function)}</li>
 * <li>{@link JKScope#let(Object, Object, Object, Object, Th4Function)}</li>
 * <li>{@link JKScope#let(Object, Object, Object, Object, Object, Th5Function)}</li>
 * </ul>
 */
@SuppressWarnings("unchecked")
final class LetMethodsTest {

  @Test
  void letMethod0ValThrowsNPEForNullBlockArg() {
    final ThSupplier<Object, Error> block = null;

    assertThatThrownBy(() ->
      let(block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void letMethod0Val() {
    final Object result = new Object();
    final ThSupplier<Object, Error> block = mock(ThSupplier.class);
    doReturn(result).when(block).get();

    assertThat(
      let(block)
    ).isSameAs(result);
    verify(block, times(1)).get();
  }

  @Test
  void letMethod1ValThrowsNPEForNullBlockArg() {
    final Object value = new Object();
    final ThFunction<Object, Object, Error> block = null;

    assertThatThrownBy(() ->
      let(value, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void letMethod1Val() {
    final Object value = new Object();
    final ArgumentCaptor<Object> valueCaptor = ArgumentCaptor.forClass(Object.class);
    final Object result = new Object();
    final ThFunction<Object, Object, Error> block = mock(ThFunction.class);
    doReturn(result).when(block).apply(any());

    assertThat(
      let(value, block)
    ).isSameAs(result);
    verify(block, times(1)).apply(valueCaptor.capture());
    assertThat(valueCaptor.getValue()).isSameAs(value);
  }

  @Test
  void letMethod2ValThrowsNPEForNullBlockArg() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Th2Function<Object, Object, Object, Error> block = null;

    assertThatThrownBy(() ->
      let(value1, value2, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void letMethod2Val() {
    final Object value1 = new Object();
    final ArgumentCaptor<Object> valueCaptor1 = ArgumentCaptor.forClass(Object.class);
    final Object value2 = new Object();
    final ArgumentCaptor<Object> valueCaptor2 = ArgumentCaptor.forClass(Object.class);
    final Object result = new Object();
    final Th2Function<Object, Object, Object, Error> block = mock(Th2Function.class);
    doReturn(result).when(block).apply(any(), any());

    assertThat(
      let(value1, value2, block)
    ).isSameAs(result);
    verify(block, times(1)).apply(valueCaptor1.capture(), valueCaptor2.capture());
    assertThat(valueCaptor1.getValue()).isSameAs(value1);
    assertThat(valueCaptor2.getValue()).isSameAs(value2);
  }

  @Test
  void letMethod3ValThrowsNPEForNullBlockArg() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final Th3Function<Object, Object, Object, Object, Error> block = null;

    assertThatThrownBy(() ->
      let(value1, value2, value3, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void letMethod3Val() {
    final Object value1 = new Object();
    final ArgumentCaptor<Object> valueCaptor1 = ArgumentCaptor.forClass(Object.class);
    final Object value2 = new Object();
    final ArgumentCaptor<Object> valueCaptor2 = ArgumentCaptor.forClass(Object.class);
    final Object value3 = new Object();
    final ArgumentCaptor<Object> valueCaptor3 = ArgumentCaptor.forClass(Object.class);
    final Object result = new Object();
    final Th3Function<Object, Object, Object, Object, Error> block = mock(Th3Function.class);
    doReturn(result).when(block).apply(any(), any(), any());

    assertThat(
      let(value1, value2, value3, block)
    ).isSameAs(result);
    verify(block, times(1)).apply(valueCaptor1.capture(), valueCaptor2.capture(), valueCaptor3.capture());
    assertThat(valueCaptor1.getValue()).isSameAs(value1);
    assertThat(valueCaptor2.getValue()).isSameAs(value2);
    assertThat(valueCaptor3.getValue()).isSameAs(value3);
  }

  @Test
  void letMethod4ValThrowsNPEForNullBlockArg() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final Object value4 = new Object();
    final Th4Function<Object, Object, Object, Object, Object, Error> block = null;

    assertThatThrownBy(() ->
      let(value1, value2, value3, value4, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void letMethod4Val() {
    final Object value1 = new Object();
    final ArgumentCaptor<Object> valueCaptor1 = ArgumentCaptor.forClass(Object.class);
    final Object value2 = new Object();
    final ArgumentCaptor<Object> valueCaptor2 = ArgumentCaptor.forClass(Object.class);
    final Object value3 = new Object();
    final ArgumentCaptor<Object> valueCaptor3 = ArgumentCaptor.forClass(Object.class);
    final Object value4 = new Object();
    final ArgumentCaptor<Object> valueCaptor4 = ArgumentCaptor.forClass(Object.class);
    final Object result = new Object();
    final Th4Function<Object, Object, Object, Object, Object, Error> block = mock(Th4Function.class);
    doReturn(result).when(block).apply(any(), any(), any(), any());

    assertThat(
      let(value1, value2, value3, value4, block)
    ).isSameAs(result);
    verify(block, times(1)).apply(valueCaptor1.capture(), valueCaptor2.capture(), valueCaptor3.capture(),
      valueCaptor4.capture());
    assertThat(valueCaptor1.getValue()).isSameAs(value1);
    assertThat(valueCaptor2.getValue()).isSameAs(value2);
    assertThat(valueCaptor3.getValue()).isSameAs(value3);
    assertThat(valueCaptor4.getValue()).isSameAs(value4);
  }

  @Test
  void letMethod5ValThrowsNPEForNullBlockArg() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final Object value4 = new Object();
    final Object value5 = new Object();
    final Th5Function<Object, Object, Object, Object, Object, Object, Error> block = null;

    assertThatThrownBy(() ->
      let(value1, value2, value3, value4, value5, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void letMethod5Val() {
    final Object value1 = new Object();
    final ArgumentCaptor<Object> valueCaptor1 = ArgumentCaptor.forClass(Object.class);
    final Object value2 = new Object();
    final ArgumentCaptor<Object> valueCaptor2 = ArgumentCaptor.forClass(Object.class);
    final Object value3 = new Object();
    final ArgumentCaptor<Object> valueCaptor3 = ArgumentCaptor.forClass(Object.class);
    final Object value4 = new Object();
    final ArgumentCaptor<Object> valueCaptor4 = ArgumentCaptor.forClass(Object.class);
    final Object value5 = new Object();
    final ArgumentCaptor<Object> valueCaptor5 = ArgumentCaptor.forClass(Object.class);
    final Object result = new Object();
    final Th5Function<Object, Object, Object, Object, Object, Object, Error> block = mock(Th5Function.class);
    doReturn(result).when(block).apply(any(), any(), any(), any(), any());

    assertThat(
      let(value1, value2, value3, value4, value5, block)
    ).isSameAs(result);
    verify(block, times(1)).apply(valueCaptor1.capture(), valueCaptor2.capture(), valueCaptor3.capture(),
      valueCaptor4.capture(), valueCaptor5.capture());
    assertThat(valueCaptor1.getValue()).isSameAs(value1);
    assertThat(valueCaptor2.getValue()).isSameAs(value2);
    assertThat(valueCaptor3.getValue()).isSameAs(value3);
    assertThat(valueCaptor4.getValue()).isSameAs(value4);
    assertThat(valueCaptor5.getValue()).isSameAs(value5);
  }
}
