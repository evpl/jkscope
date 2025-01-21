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
package com.plugatar.jkscope;

import com.plugatar.jkscope.function.Th2Consumer;
import com.plugatar.jkscope.function.Th3Consumer;
import com.plugatar.jkscope.function.Th4Consumer;
import com.plugatar.jkscope.function.Th5Consumer;
import com.plugatar.jkscope.function.ThConsumer;
import com.plugatar.jkscope.function.ThRunnable;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static com.plugatar.jkscope.JKScope.with;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests for methods:
 * <ul>
 * <li>{@link JKScope#with(ThRunnable)}</li>
 * <li>{@link JKScope#with(Object, ThConsumer)}</li>
 * <li>{@link JKScope#with(Object, Object, Th2Consumer)}</li>
 * <li>{@link JKScope#with(Object, Object, Object, Th3Consumer)}</li>
 * <li>{@link JKScope#with(Object, Object, Object, Object, Th4Consumer)}</li>
 * <li>{@link JKScope#with(Object, Object, Object, Object, Object, Th5Consumer)}</li>
 * </ul>
 */
@SuppressWarnings("unchecked")
final class WithMethodsTest {

  @Test
  void withMethod0ValThrowsNPEForNullBlockArg() {
    final ThRunnable<Throwable> block = null;

    assertThatThrownBy(() ->
      with(block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void withMethod0Val() {
    final ThRunnable<Error> block = mock(ThRunnable.class);

    with(block);
    verify(block, times(1)).run();
  }

  @Test
  void withMethod1ValThrowsNPEForNullBlockArg() {
    final Object value = new Object();
    final ThConsumer<Object, Error> block = null;

    assertThatThrownBy(() ->
      with(value, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void withMethod1Val() {
    final Object value = new Object();
    final ThConsumer<Object, Error> block = mock(ThConsumer.class);
    final ArgumentCaptor<Object> valueCaptor = ArgumentCaptor.forClass(Object.class);

    with(value, block);
    verify(block, times(1)).accept(valueCaptor.capture());
    assertThat(valueCaptor.getValue()).isSameAs(value);
  }

  @Test
  void withMethod2ValThrowsNPEForNullBlockArg() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Th2Consumer<Object, Object, Error> block = null;

    assertThatThrownBy(() ->
      with(value1, value2, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void withMethod2Val() {
    final Object value1 = new Object();
    final ArgumentCaptor<Object> valueCaptor1 = ArgumentCaptor.forClass(Object.class);
    final Object value2 = new Object();
    final ArgumentCaptor<Object> valueCaptor2 = ArgumentCaptor.forClass(Object.class);
    final Th2Consumer<Object, Object, Error> block = mock(Th2Consumer.class);

    with(value1, value2, block);
    verify(block, times(1)).accept(valueCaptor1.capture(), valueCaptor2.capture());
    assertThat(valueCaptor1.getValue()).isSameAs(value1);
    assertThat(valueCaptor2.getValue()).isSameAs(value2);
  }

  @Test
  void withMethod3ValThrowsNPEForNullBlockArg() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final Th3Consumer<Object, Object, Object, Error> block = null;

    assertThatThrownBy(() ->
      with(value1, value2, value3, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void withMethod3Val() {
    final Object value1 = new Object();
    final ArgumentCaptor<Object> valueCaptor1 = ArgumentCaptor.forClass(Object.class);
    final Object value2 = new Object();
    final ArgumentCaptor<Object> valueCaptor2 = ArgumentCaptor.forClass(Object.class);
    final Object value3 = new Object();
    final ArgumentCaptor<Object> valueCaptor3 = ArgumentCaptor.forClass(Object.class);
    final Th3Consumer<Object, Object, Object, Error> block = mock(Th3Consumer.class);

    with(value1, value2, value3, block);
    verify(block, times(1)).accept(valueCaptor1.capture(), valueCaptor2.capture(), valueCaptor3.capture());
    assertThat(valueCaptor1.getValue()).isSameAs(value1);
    assertThat(valueCaptor2.getValue()).isSameAs(value2);
    assertThat(valueCaptor3.getValue()).isSameAs(value3);
  }

  @Test
  void withMethod4ValThrowsNPEForNullBlockArg() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final Object value4 = new Object();
    final Th4Consumer<Object, Object, Object, Object, Error> block = null;

    assertThatThrownBy(() ->
      with(value1, value2, value3, value4, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void withMethod4Val() {
    final Object value1 = new Object();
    final ArgumentCaptor<Object> valueCaptor1 = ArgumentCaptor.forClass(Object.class);
    final Object value2 = new Object();
    final ArgumentCaptor<Object> valueCaptor2 = ArgumentCaptor.forClass(Object.class);
    final Object value3 = new Object();
    final ArgumentCaptor<Object> valueCaptor3 = ArgumentCaptor.forClass(Object.class);
    final Object value4 = new Object();
    final ArgumentCaptor<Object> valueCaptor4 = ArgumentCaptor.forClass(Object.class);
    final Th4Consumer<Object, Object, Object, Object, Error> block = mock(Th4Consumer.class);

    with(value1, value2, value3, value4, block);
    verify(block, times(1)).accept(valueCaptor1.capture(), valueCaptor2.capture(), valueCaptor3.capture(),
      valueCaptor4.capture());
    assertThat(valueCaptor1.getValue()).isSameAs(value1);
    assertThat(valueCaptor2.getValue()).isSameAs(value2);
    assertThat(valueCaptor3.getValue()).isSameAs(value3);
    assertThat(valueCaptor4.getValue()).isSameAs(value4);
  }

  @Test
  void withMethod5ValThrowsNPEForNullBlockArg() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final Object value4 = new Object();
    final Object value5 = new Object();
    final Th5Consumer<Object, Object, Object, Object, Object, Error> block = null;

    assertThatThrownBy(() ->
      with(value1, value2, value3, value4, value5, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void withMethod5Val() {
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

    with(value1, value2, value3, value4, value5, block);
    verify(block, times(1)).accept(valueCaptor1.capture(), valueCaptor2.capture(), valueCaptor3.capture(),
      valueCaptor4.capture(), valueCaptor5.capture());
    assertThat(valueCaptor1.getValue()).isSameAs(value1);
    assertThat(valueCaptor2.getValue()).isSameAs(value2);
    assertThat(valueCaptor3.getValue()).isSameAs(value3);
    assertThat(valueCaptor4.getValue()).isSameAs(value4);
    assertThat(valueCaptor5.getValue()).isSameAs(value5);
  }
}
