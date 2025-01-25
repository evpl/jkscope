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

import com.plugatar.jkscope.function.Th2Consumer;
import com.plugatar.jkscope.function.Th3Consumer;
import com.plugatar.jkscope.function.Th4Consumer;
import com.plugatar.jkscope.function.Th5Consumer;
import com.plugatar.jkscope.function.ThConsumer;
import com.plugatar.jkscope.function.ThSupplier;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static com.plugatar.jkscope.JKScope.it;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests for methods:
 * <ul>
 * <li>{@link JKScope#it(ThSupplier)}</li>
 * <li>{@link JKScope#it(Object, ThConsumer)}</li>
 * <li>{@link JKScope#it(Object, Object, Th2Consumer)}</li>
 * <li>{@link JKScope#it(Object, Object, Object, Th3Consumer)}</li>
 * <li>{@link JKScope#it(Object, Object, Object, Object, Th4Consumer)}</li>
 * <li>{@link JKScope#it(Object, Object, Object, Object, Object, Th5Consumer)}</li>
 * </ul>
 */
@SuppressWarnings("unchecked")
final class ItMethodsTest {

  @Test
  void itMethod0ValThrowsNPEForNullBlockArg() {
    final ThSupplier<Object, Error> block = null;

    assertThatThrownBy(() ->
      it(block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void itMethod0Val() {
    final Object result = new Object();
    final ThSupplier<Object, Error> block = mock(ThSupplier.class);
    doReturn(result).when(block).get();

    assertThat(
      it(block)
    ).isSameAs(result);
    verify(block, times(1)).get();
  }

  @Test
  void itMethod1ValThrowsNPEForNullBlockArg() {
    final Object value = new Object();
    final ThConsumer<Object, Error> block = null;

    assertThatThrownBy(() ->
      it(value, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void itMethod1Val() {
    final Object value = new Object();
    final ArgumentCaptor<Object> valueCaptor = ArgumentCaptor.forClass(Object.class);
    final ThConsumer<Object, Error> block = mock(ThConsumer.class);

    assertThat(
      it(value, block)
    ).isSameAs(value);
    verify(block, times(1)).accept(valueCaptor.capture());
    assertThat(valueCaptor.getValue()).isSameAs(value);
  }

  @Test
  void itMethod2ValThrowsNPEForNullBlockArg() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Th2Consumer<Object, Object, Error> block = null;

    assertThatThrownBy(() ->
      it(value1, value2, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void itMethod2Val() {
    final Object value1 = new Object();
    final ArgumentCaptor<Object> valueCaptor1 = ArgumentCaptor.forClass(Object.class);
    final Object value2 = new Object();
    final ArgumentCaptor<Object> valueCaptor2 = ArgumentCaptor.forClass(Object.class);
    final Th2Consumer<Object, Object, Error> block = mock(Th2Consumer.class);

    assertThat(
      it(value1, value2, block)
    ).isSameAs(value1);
    verify(block, times(1)).accept(valueCaptor1.capture(), valueCaptor2.capture());
    assertThat(valueCaptor1.getValue()).isSameAs(value1);
    assertThat(valueCaptor2.getValue()).isSameAs(value2);
  }

  @Test
  void itMethod3ValThrowsNPEForNullBlockArg() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final Th3Consumer<Object, Object, Object, Error> block = null;

    assertThatThrownBy(() ->
      it(value1, value2, value3, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void itMethod3Val() {
    final Object value1 = new Object();
    final ArgumentCaptor<Object> valueCaptor1 = ArgumentCaptor.forClass(Object.class);
    final Object value2 = new Object();
    final ArgumentCaptor<Object> valueCaptor2 = ArgumentCaptor.forClass(Object.class);
    final Object value3 = new Object();
    final ArgumentCaptor<Object> valueCaptor3 = ArgumentCaptor.forClass(Object.class);
    final Th3Consumer<Object, Object, Object, Error> block = mock(Th3Consumer.class);

    assertThat(
      it(value1, value2, value3, block)
    ).isSameAs(value1);
    verify(block, times(1)).accept(valueCaptor1.capture(), valueCaptor2.capture(), valueCaptor3.capture());
    assertThat(valueCaptor1.getValue()).isSameAs(value1);
    assertThat(valueCaptor2.getValue()).isSameAs(value2);
    assertThat(valueCaptor3.getValue()).isSameAs(value3);
  }

  @Test
  void itMethod4ValThrowsNPEForNullBlockArg() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final Object value4 = new Object();
    final Th4Consumer<Object, Object, Object, Object, Error> block = null;

    assertThatThrownBy(() ->
      it(value1, value2, value3, value4, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void itMethod4Val() {
    final Object value1 = new Object();
    final ArgumentCaptor<Object> valueCaptor1 = ArgumentCaptor.forClass(Object.class);
    final Object value2 = new Object();
    final ArgumentCaptor<Object> valueCaptor2 = ArgumentCaptor.forClass(Object.class);
    final Object value3 = new Object();
    final ArgumentCaptor<Object> valueCaptor3 = ArgumentCaptor.forClass(Object.class);
    final Object value4 = new Object();
    final ArgumentCaptor<Object> valueCaptor4 = ArgumentCaptor.forClass(Object.class);
    final Th4Consumer<Object, Object, Object, Object, Error> block = mock(Th4Consumer.class);

    assertThat(
      it(value1, value2, value3, value4, block)
    ).isSameAs(value1);
    verify(block, times(1)).accept(valueCaptor1.capture(), valueCaptor2.capture(), valueCaptor3.capture(),
      valueCaptor4.capture());
    assertThat(valueCaptor1.getValue()).isSameAs(value1);
    assertThat(valueCaptor2.getValue()).isSameAs(value2);
    assertThat(valueCaptor3.getValue()).isSameAs(value3);
    assertThat(valueCaptor4.getValue()).isSameAs(value4);
  }

  @Test
  void itMethod5ValThrowsNPEForNullBlockArg() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final Object value4 = new Object();
    final Object value5 = new Object();
    final Th5Consumer<Object, Object, Object, Object, Object, Error> block = null;

    assertThatThrownBy(() ->
      it(value1, value2, value3, value4, value5, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void itMethod5Val() {
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
    final Th5Consumer<Object, Object, Object, Object, Object, Error> block = mock(Th5Consumer.class);

    assertThat(
      it(value1, value2, value3, value4, value5, block)
    ).isSameAs(value1);
    verify(block, times(1)).accept(valueCaptor1.capture(), valueCaptor2.capture(), valueCaptor3.capture(),
      valueCaptor4.capture(), valueCaptor5.capture());
    assertThat(valueCaptor1.getValue()).isSameAs(value1);
    assertThat(valueCaptor2.getValue()).isSameAs(value2);
    assertThat(valueCaptor3.getValue()).isSameAs(value3);
    assertThat(valueCaptor4.getValue()).isSameAs(value4);
    assertThat(valueCaptor5.getValue()).isSameAs(value5);
  }
}
