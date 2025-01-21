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
import com.plugatar.jkscope.function.Th2ConsumerIntObj;
import com.plugatar.jkscope.function.Th3Consumer;
import com.plugatar.jkscope.function.Th3ConsumerIntObj2;
import com.plugatar.jkscope.function.Th4Consumer;
import com.plugatar.jkscope.function.Th4ConsumerIntObj3;
import com.plugatar.jkscope.function.Th5ConsumerIntObj4;
import com.plugatar.jkscope.function.ThConsumer;
import com.plugatar.jkscope.function.ThConsumerInt;
import com.plugatar.jkscope.function.ThRunnable;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static com.plugatar.jkscope.JKScope.repeat;
import static com.plugatar.jkscope.JKScope.repeat1;
import static com.plugatar.jkscope.JKScope.repeat2;
import static com.plugatar.jkscope.JKScope.repeat3;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests for methods:
 * <ul>
 * <li>{@link JKScope#repeat(int, ThRunnable)}</li>
 * <li>{@link JKScope#repeat(int, ThConsumerInt)}</li>
 * <li>{@link JKScope#repeat(int, Object, ThConsumer)}</li>
 * <li>{@link JKScope#repeat(int, Object, Th2ConsumerIntObj)}</li>
 * <li>{@link JKScope#repeat1(int, Object, ThConsumer)}</li>
 * <li>{@link JKScope#repeat1(int, Object, Th2ConsumerIntObj)}</li>
 * <li>{@link JKScope#repeat1(int, Object, Object, Th2Consumer)}</li>
 * <li>{@link JKScope#repeat1(int, Object, Object, Th3ConsumerIntObj2)}</li>
 * <li>{@link JKScope#repeat2(int, Object, Object, Th2Consumer)}</li>
 * <li>{@link JKScope#repeat2(int, Object, Object, Th3ConsumerIntObj2)}</li>
 * <li>{@link JKScope#repeat2(int, Object, Object, Object, Th3Consumer)}</li>
 * <li>{@link JKScope#repeat2(int, Object, Object, Object, Th4ConsumerIntObj3)}</li>
 * <li>{@link JKScope#repeat3(int, Object, Object, Object, Th3Consumer)}</li>
 * <li>{@link JKScope#repeat3(int, Object, Object, Object, Th4ConsumerIntObj3)}</li>
 * <li>{@link JKScope#repeat3(int, Object, Object, Object, Object, Th4Consumer)}</li>
 * <li>{@link JKScope#repeat3(int, Object, Object, Object, Object, Th5ConsumerIntObj4)}</li>
 * </ul>
 */
@SuppressWarnings("unchecked")
final class RepeatMethodsTest {

  @Test
  void repeatMethodNullBlockArg() {
    final ThRunnable<Error> block = null;

    assertThatThrownBy(() ->
      repeat(3, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void repeatMethod() {
    final ThRunnable<Error> block = mock(ThRunnable.class);

    repeat(0, block);
    verify(block, times(0)).run();

    repeat(-3, block);
    verify(block, times(0)).run();

    repeat(3, block);
    verify(block, times(3)).run();
  }

  @Test
  void repeatMethodWithIdxNullBlockArg() {
    final ThConsumerInt<Error> block = null;

    assertThatThrownBy(() ->
      repeat(3, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void repeatMethodWithIdx() {
    final ArgumentCaptor<Integer> idxCaptor = ArgumentCaptor.forClass(Integer.class);
    final ThConsumerInt<Error> block = mock(ThConsumerInt.class);

    repeat(0, block);
    verify(block, times(0)).accept(idxCaptor.capture());
    assertThat(idxCaptor.getAllValues()).isEmpty();

    repeat(-3, block);
    verify(block, times(0)).accept(idxCaptor.capture());
    assertThat(idxCaptor.getAllValues()).isEmpty();

    repeat(3, block);
    verify(block, times(3)).accept(idxCaptor.capture());
    assertThat(idxCaptor.getAllValues()).containsExactly(0, 1, 2);
  }

  @Test
  void repeatMethodWithAccNullBlockArg() {
    final Object acc = new Object();
    final ThConsumer<Object, Error> block = null;

    assertThatThrownBy(() ->
      repeat(3, acc, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void repeatMethodWithAcc() {
    final Object acc = new Object();
    final ArgumentCaptor<Object> accCaptor = ArgumentCaptor.forClass(Object.class);
    final ThConsumer<Object, Error> block = mock(ThConsumer.class);

    assertThat(
      repeat(0, acc, block)
    ).isSameAs(acc);
    verify(block, times(0)).accept(accCaptor.capture());
    assertThat(accCaptor.getAllValues()).isEmpty();

    assertThat(
      repeat(-3, acc, block)
    ).isSameAs(acc);
    verify(block, times(0)).accept(accCaptor.capture());
    assertThat(accCaptor.getAllValues()).isEmpty();

    assertThat(
      repeat(3, acc, block)
    ).isSameAs(acc);
    verify(block, times(3)).accept(accCaptor.capture());
    assertThat(accCaptor.getAllValues()).containsExactly(acc, acc, acc);
  }

  @Test
  void repeatMethodWithIdxAndAccNullBlockArg() {
    final Object acc = new Object();
    final Th2ConsumerIntObj<Object, Error> block = null;

    assertThatThrownBy(() ->
      repeat(3, acc, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void repeatMethodWithIdxAndAcc() {
    final Object acc = new Object();
    final ArgumentCaptor<Object> accCaptor = ArgumentCaptor.forClass(Object.class);
    final ArgumentCaptor<Integer> idxCaptor = ArgumentCaptor.forClass(Integer.class);
    final Th2ConsumerIntObj<Object, Error> block = mock(Th2ConsumerIntObj.class);

    assertThat(
      repeat(0, acc, block)
    ).isSameAs(acc);
    verify(block, times(0)).accept(idxCaptor.capture(), accCaptor.capture());
    assertThat(accCaptor.getAllValues()).isEmpty();
    assertThat(idxCaptor.getAllValues()).isEmpty();

    assertThat(
      repeat(-3, acc, block)
    ).isSameAs(acc);
    verify(block, times(0)).accept(idxCaptor.capture(), accCaptor.capture());
    assertThat(accCaptor.getAllValues()).isEmpty();
    assertThat(idxCaptor.getAllValues()).isEmpty();

    assertThat(
      repeat(3, acc, block)
    ).isSameAs(acc);
    verify(block, times(3)).accept(idxCaptor.capture(), accCaptor.capture());
    assertThat(accCaptor.getAllValues()).containsExactly(acc, acc, acc);
    assertThat(idxCaptor.getAllValues()).containsExactly(0, 1, 2);
  }

  @Test
  void repeat1MethodNullBlockArg() {
    final Object value = new Object();
    final ThConsumer<Object, Error> block = null;

    assertThatThrownBy(() ->
      repeat1(3, value, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void repeat1Method() {
    final Object value = new Object();
    final ArgumentCaptor<Object> valueCaptor = ArgumentCaptor.forClass(Object.class);
    final ThConsumer<Object, Error> block = mock(ThConsumer.class);

    repeat1(0, value, block);
    verify(block, times(0)).accept(valueCaptor.capture());
    assertThat(valueCaptor.getAllValues()).isEmpty();

    repeat1(-3, value, block);
    verify(block, times(0)).accept(valueCaptor.capture());
    assertThat(valueCaptor.getAllValues()).isEmpty();

    repeat1(3, value, block);
    verify(block, times(3)).accept(valueCaptor.capture());
    assertThat(valueCaptor.getAllValues()).containsExactly(value, value, value);
  }

  @Test
  void repeat1MethodWithIdxNullBlockArg() {
    final Object value = new Object();
    final Th2ConsumerIntObj<Object, Error> block = null;

    assertThatThrownBy(() ->
      repeat1(3, value, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void repeat1WithIdxMethod() {
    final Object value = new Object();
    final ArgumentCaptor<Object> valueCaptor = ArgumentCaptor.forClass(Object.class);
    final ArgumentCaptor<Integer> idxCaptor = ArgumentCaptor.forClass(Integer.class);
    final Th2ConsumerIntObj<Object, Error> block = mock(Th2ConsumerIntObj.class);

    repeat1(0, value, block);
    verify(block, times(0)).accept(idxCaptor.capture(), valueCaptor.capture());
    assertThat(valueCaptor.getAllValues()).isEmpty();
    assertThat(idxCaptor.getAllValues()).isEmpty();

    repeat1(-3, value, block);
    verify(block, times(0)).accept(idxCaptor.capture(), valueCaptor.capture());
    assertThat(valueCaptor.getAllValues()).isEmpty();
    assertThat(idxCaptor.getAllValues()).isEmpty();

    repeat1(3, value, block);
    verify(block, times(3)).accept(idxCaptor.capture(), valueCaptor.capture());
    assertThat(valueCaptor.getAllValues()).containsExactly(value, value, value);
    assertThat(idxCaptor.getAllValues()).containsExactly(0, 1, 2);
  }

  @Test
  void repeat1MethodWithAccNullBlockArg() {
    final Object value = new Object();
    final Object acc = new Object();
    final Th2Consumer<Object, Object, Error> block = null;

    assertThatThrownBy(() ->
      repeat1(3, value, acc, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void repeat1WithAccMethod() {
    final Object value = new Object();
    final ArgumentCaptor<Object> valueCaptor = ArgumentCaptor.forClass(Object.class);
    final Object acc = new Object();
    final ArgumentCaptor<Object> accCaptor = ArgumentCaptor.forClass(Object.class);
    final Th2Consumer<Object, Object, Error> block = mock(Th2Consumer.class);

    assertThat(
      repeat1(0, value, acc, block)
    ).isSameAs(acc);
    verify(block, times(0)).accept(valueCaptor.capture(), accCaptor.capture());
    assertThat(valueCaptor.getAllValues()).isEmpty();
    assertThat(accCaptor.getAllValues()).isEmpty();

    assertThat(
      repeat1(-3, value, acc, block)
    ).isSameAs(acc);
    verify(block, times(0)).accept(valueCaptor.capture(), accCaptor.capture());
    assertThat(valueCaptor.getAllValues()).isEmpty();
    assertThat(accCaptor.getAllValues()).isEmpty();

    assertThat(
      repeat1(3, value, acc, block)
    ).isSameAs(acc);
    verify(block, times(3)).accept(valueCaptor.capture(), accCaptor.capture());
    assertThat(valueCaptor.getAllValues()).containsExactly(value, value, value);
    assertThat(accCaptor.getAllValues()).containsExactly(acc, acc, acc);
  }

  @Test
  void repeat1MethodWithIdxAndAccNullBlockArg() {
    final Object value = new Object();
    final Object acc = new Object();
    final Th3ConsumerIntObj2<Object, Object, Error> block = null;

    assertThatThrownBy(() ->
      repeat1(3, value, acc, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void repeat1WithIdxAndAccMethod() {
    final Object value = new Object();
    final ArgumentCaptor<Object> valueCaptor = ArgumentCaptor.forClass(Object.class);
    final Object acc = new Object();
    final ArgumentCaptor<Object> accCaptor = ArgumentCaptor.forClass(Object.class);
    final ArgumentCaptor<Integer> idxCaptor = ArgumentCaptor.forClass(Integer.class);
    final Th3ConsumerIntObj2<Object, Object, Error> block = mock(Th3ConsumerIntObj2.class);

    assertThat(
      repeat1(0, value, acc, block)
    ).isSameAs(acc);
    verify(block, times(0)).accept(idxCaptor.capture(), valueCaptor.capture(), accCaptor.capture());
    assertThat(valueCaptor.getAllValues()).isEmpty();
    assertThat(accCaptor.getAllValues()).isEmpty();
    assertThat(idxCaptor.getAllValues()).isEmpty();

    assertThat(
      repeat1(-3, value, acc, block)
    ).isSameAs(acc);
    verify(block, times(0)).accept(idxCaptor.capture(), valueCaptor.capture(), accCaptor.capture());
    assertThat(accCaptor.getAllValues()).isEmpty();
    assertThat(idxCaptor.getAllValues()).isEmpty();

    assertThat(
      repeat1(3, value, acc, block)
    ).isSameAs(acc);
    verify(block, times(3)).accept(idxCaptor.capture(), valueCaptor.capture(), accCaptor.capture());
    assertThat(valueCaptor.getAllValues()).containsExactly(value, value, value);
    assertThat(accCaptor.getAllValues()).containsExactly(acc, acc, acc);
    assertThat(idxCaptor.getAllValues()).containsExactly(0, 1, 2);
  }

  @Test
  void repeat2MethodNullBlockArg() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Th2Consumer<Object, Object, Error> block = null;

    assertThatThrownBy(() ->
      repeat2(3, value1, value2, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void repeat2Method() {
    final Object value1 = new Object();
    final ArgumentCaptor<Object> valueCaptor1 = ArgumentCaptor.forClass(Object.class);
    final Object value2 = new Object();
    final ArgumentCaptor<Object> valueCaptor2 = ArgumentCaptor.forClass(Object.class);
    final Th2Consumer<Object, Object, Error> block = mock(Th2Consumer.class);

    repeat2(0, value1, value2, block);
    verify(block, times(0)).accept(valueCaptor1.capture(), valueCaptor2.capture());
    assertThat(valueCaptor1.getAllValues()).isEmpty();
    assertThat(valueCaptor2.getAllValues()).isEmpty();

    repeat2(-3, value1, value2, block);
    verify(block, times(0)).accept(valueCaptor1.capture(), valueCaptor2.capture());
    assertThat(valueCaptor1.getAllValues()).isEmpty();
    assertThat(valueCaptor2.getAllValues()).isEmpty();

    repeat2(3, value1, value2, block);
    verify(block, times(3)).accept(valueCaptor1.capture(), valueCaptor2.capture());
    assertThat(valueCaptor1.getAllValues()).containsExactly(value1, value1, value1);
    assertThat(valueCaptor2.getAllValues()).containsExactly(value2, value2, value2);
  }

  @Test
  void repeat2MethodWithIdxNullBlockArg() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Th3ConsumerIntObj2<Object, Object, Error> block = null;

    assertThatThrownBy(() ->
      repeat2(3, value1, value2, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void repeat2WithIdxMethod() {
    final Object value1 = new Object();
    final ArgumentCaptor<Object> valueCaptor1 = ArgumentCaptor.forClass(Object.class);
    final Object value2 = new Object();
    final ArgumentCaptor<Object> valueCaptor2 = ArgumentCaptor.forClass(Object.class);
    final ArgumentCaptor<Integer> idxCaptor = ArgumentCaptor.forClass(Integer.class);
    final Th3ConsumerIntObj2<Object, Object, Error> block = mock(Th3ConsumerIntObj2.class);

    repeat2(0, value1, value2, block);
    verify(block, times(0)).accept(idxCaptor.capture(), valueCaptor1.capture(), valueCaptor2.capture());
    assertThat(valueCaptor1.getAllValues()).isEmpty();
    assertThat(valueCaptor2.getAllValues()).isEmpty();
    assertThat(idxCaptor.getAllValues()).isEmpty();

    repeat2(-3, value1, value2, block);
    verify(block, times(0)).accept(idxCaptor.capture(), valueCaptor1.capture(), valueCaptor2.capture());
    assertThat(valueCaptor1.getAllValues()).isEmpty();
    assertThat(valueCaptor2.getAllValues()).isEmpty();
    assertThat(idxCaptor.getAllValues()).isEmpty();

    repeat2(3, value1, value2, block);
    verify(block, times(3)).accept(idxCaptor.capture(), valueCaptor1.capture(), valueCaptor2.capture());
    assertThat(valueCaptor1.getAllValues()).containsExactly(value1, value1, value1);
    assertThat(valueCaptor2.getAllValues()).containsExactly(value2, value2, value2);
    assertThat(idxCaptor.getAllValues()).containsExactly(0, 1, 2);
  }

  @Test
  void repeat2MethodWithAccNullBlockArg() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object acc = new Object();
    final Th3Consumer<Object, Object, Object, Error> block = null;

    assertThatThrownBy(() ->
      repeat2(3, value1, value2, acc, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void repeat2WithAccMethod() {
    final Object value1 = new Object();
    final ArgumentCaptor<Object> valueCaptor1 = ArgumentCaptor.forClass(Object.class);
    final Object value2 = new Object();
    final ArgumentCaptor<Object> valueCaptor2 = ArgumentCaptor.forClass(Object.class);
    final Object acc = new Object();
    final ArgumentCaptor<Object> accCaptor = ArgumentCaptor.forClass(Object.class);
    final Th3Consumer<Object, Object, Object, Error> block = mock(Th3Consumer.class);

    assertThat(
      repeat2(0, value1, value2, acc, block)
    ).isSameAs(acc);
    verify(block, times(0)).accept(valueCaptor1.capture(), valueCaptor2.capture(), accCaptor.capture());
    assertThat(valueCaptor1.getAllValues()).isEmpty();
    assertThat(valueCaptor2.getAllValues()).isEmpty();
    assertThat(accCaptor.getAllValues()).isEmpty();

    assertThat(
      repeat2(-3, value1, value2, acc, block)
    ).isSameAs(acc);
    verify(block, times(0)).accept(valueCaptor1.capture(), valueCaptor2.capture(), accCaptor.capture());
    assertThat(valueCaptor1.getAllValues()).isEmpty();
    assertThat(valueCaptor2.getAllValues()).isEmpty();
    assertThat(accCaptor.getAllValues()).isEmpty();

    assertThat(
      repeat2(3, value1, value2, acc, block)
    ).isSameAs(acc);
    verify(block, times(3)).accept(valueCaptor1.capture(), valueCaptor2.capture(), accCaptor.capture());
    assertThat(valueCaptor1.getAllValues()).containsExactly(value1, value1, value1);
    assertThat(valueCaptor2.getAllValues()).containsExactly(value2, value2, value2);
    assertThat(accCaptor.getAllValues()).containsExactly(acc, acc, acc);
  }

  @Test
  void repeat2MethodWithIdxAndAccNullBlockArg() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object acc = new Object();
    final Th4ConsumerIntObj3<Object, Object, Object, Error> block = null;

    assertThatThrownBy(() ->
      repeat2(3, value1, value2, acc, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void repeat2WithIdxAndAccMethod() {
    final Object value1 = new Object();
    final ArgumentCaptor<Object> valueCaptor1 = ArgumentCaptor.forClass(Object.class);
    final Object value2 = new Object();
    final ArgumentCaptor<Object> valueCaptor2 = ArgumentCaptor.forClass(Object.class);
    final Object acc = new Object();
    final ArgumentCaptor<Object> accCaptor = ArgumentCaptor.forClass(Object.class);
    final ArgumentCaptor<Integer> idxCaptor = ArgumentCaptor.forClass(Integer.class);
    final Th4ConsumerIntObj3<Object, Object, Object, Error> block = mock(Th4ConsumerIntObj3.class);

    assertThat(
      repeat2(0, value1, value2, acc, block)
    ).isSameAs(acc);
    verify(block, times(0)).accept(idxCaptor.capture(), valueCaptor1.capture(), valueCaptor2.capture(), accCaptor.capture());
    assertThat(valueCaptor1.getAllValues()).isEmpty();
    assertThat(valueCaptor2.getAllValues()).isEmpty();
    assertThat(accCaptor.getAllValues()).isEmpty();
    assertThat(idxCaptor.getAllValues()).isEmpty();

    assertThat(
      repeat2(-3, value1, value2, acc, block)
    ).isSameAs(acc);
    verify(block, times(0)).accept(idxCaptor.capture(), valueCaptor1.capture(), valueCaptor2.capture(),
      accCaptor.capture());
    assertThat(valueCaptor1.getAllValues()).isEmpty();
    assertThat(valueCaptor2.getAllValues()).isEmpty();
    assertThat(accCaptor.getAllValues()).isEmpty();
    assertThat(idxCaptor.getAllValues()).isEmpty();

    assertThat(
      repeat2(3, value1, value2, acc, block)
    ).isSameAs(acc);
    verify(block, times(3)).accept(idxCaptor.capture(), valueCaptor1.capture(), valueCaptor2.capture(),
      accCaptor.capture());
    assertThat(valueCaptor1.getAllValues()).containsExactly(value1, value1, value1);
    assertThat(valueCaptor2.getAllValues()).containsExactly(value2, value2, value2);
    assertThat(accCaptor.getAllValues()).containsExactly(acc, acc, acc);
    assertThat(idxCaptor.getAllValues()).containsExactly(0, 1, 2);
  }


  @Test
  void repeat3MethodNullBlockArg() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final Th3Consumer<Object, Object, Object, Error> block = null;

    assertThatThrownBy(() ->
      repeat3(3, value1, value2, value3, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void repeat3Method() {
    final Object value1 = new Object();
    final ArgumentCaptor<Object> valueCaptor1 = ArgumentCaptor.forClass(Object.class);
    final Object value2 = new Object();
    final ArgumentCaptor<Object> valueCaptor2 = ArgumentCaptor.forClass(Object.class);
    final Object value3 = new Object();
    final ArgumentCaptor<Object> valueCaptor3 = ArgumentCaptor.forClass(Object.class);
    final Th3Consumer<Object, Object, Object, Error> block = mock(Th3Consumer.class);

    repeat3(0, value1, value2, value3, block);
    verify(block, times(0)).accept(valueCaptor1.capture(), valueCaptor2.capture(), valueCaptor3.capture());
    assertThat(valueCaptor1.getAllValues()).isEmpty();
    assertThat(valueCaptor2.getAllValues()).isEmpty();
    assertThat(valueCaptor3.getAllValues()).isEmpty();

    repeat3(-3, value1, value2, value3, block);
    verify(block, times(0)).accept(valueCaptor1.capture(), valueCaptor2.capture(), valueCaptor3.capture());
    assertThat(valueCaptor1.getAllValues()).isEmpty();
    assertThat(valueCaptor2.getAllValues()).isEmpty();
    assertThat(valueCaptor3.getAllValues()).isEmpty();

    repeat3(3, value1, value2, value3, block);
    verify(block, times(3)).accept(valueCaptor1.capture(), valueCaptor2.capture(), valueCaptor3.capture());
    assertThat(valueCaptor1.getAllValues()).containsExactly(value1, value1, value1);
    assertThat(valueCaptor2.getAllValues()).containsExactly(value2, value2, value2);
    assertThat(valueCaptor3.getAllValues()).containsExactly(value3, value3, value3);
  }

  @Test
  void repeat3MethodWithIdxNullBlockArg() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final Th4ConsumerIntObj3<Object, Object, Object, Error> block = null;

    assertThatThrownBy(() ->
      repeat3(3, value1, value2, value3, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void repeat3WithIdxMethod() {
    final Object value1 = new Object();
    final ArgumentCaptor<Object> valueCaptor1 = ArgumentCaptor.forClass(Object.class);
    final Object value2 = new Object();
    final ArgumentCaptor<Object> valueCaptor2 = ArgumentCaptor.forClass(Object.class);
    final Object value3 = new Object();
    final ArgumentCaptor<Object> valueCaptor3 = ArgumentCaptor.forClass(Object.class);
    final ArgumentCaptor<Integer> idxCaptor = ArgumentCaptor.forClass(Integer.class);
    final Th4ConsumerIntObj3<Object, Object, Object, Error> block = mock(Th4ConsumerIntObj3.class);

    repeat3(0, value1, value2, value3, block);
    verify(block, times(0)).accept(idxCaptor.capture(), valueCaptor1.capture(), valueCaptor2.capture(),
      valueCaptor3.capture());
    assertThat(valueCaptor1.getAllValues()).isEmpty();
    assertThat(valueCaptor2.getAllValues()).isEmpty();
    assertThat(valueCaptor3.getAllValues()).isEmpty();
    assertThat(idxCaptor.getAllValues()).isEmpty();

    repeat3(-3, value1, value2, value3, block);
    verify(block, times(0)).accept(idxCaptor.capture(), valueCaptor1.capture(), valueCaptor2.capture(),
      valueCaptor3.capture());
    assertThat(valueCaptor1.getAllValues()).isEmpty();
    assertThat(valueCaptor2.getAllValues()).isEmpty();
    assertThat(valueCaptor3.getAllValues()).isEmpty();
    assertThat(idxCaptor.getAllValues()).isEmpty();

    repeat3(3, value1, value2, value3, block);
    verify(block, times(3)).accept(idxCaptor.capture(), valueCaptor1.capture(), valueCaptor2.capture(), valueCaptor3.capture());
    assertThat(valueCaptor1.getAllValues()).containsExactly(value1, value1, value1);
    assertThat(valueCaptor2.getAllValues()).containsExactly(value2, value2, value2);
    assertThat(valueCaptor3.getAllValues()).containsExactly(value3, value3, value3);
    assertThat(idxCaptor.getAllValues()).containsExactly(0, 1, 2);
  }

  @Test
  void repeat3MethodWithAccNullBlockArg() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final Object acc = new Object();
    final Th4Consumer<Object, Object, Object, Object, Error> block = null;

    assertThatThrownBy(() ->
      repeat3(3, value1, value2, value3, acc, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void repeat3WithAccMethod() {
    final Object value1 = new Object();
    final ArgumentCaptor<Object> valueCaptor1 = ArgumentCaptor.forClass(Object.class);
    final Object value2 = new Object();
    final ArgumentCaptor<Object> valueCaptor2 = ArgumentCaptor.forClass(Object.class);
    final Object value3 = new Object();
    final ArgumentCaptor<Object> valueCaptor3 = ArgumentCaptor.forClass(Object.class);
    final Object acc = new Object();
    final ArgumentCaptor<Object> accCaptor = ArgumentCaptor.forClass(Object.class);
    final Th4Consumer<Object, Object, Object, Object, Error> block = mock(Th4Consumer.class);

    assertThat(
      repeat3(0, value1, value2, value3, acc, block)
    ).isSameAs(acc);
    verify(block, times(0)).accept(valueCaptor1.capture(), valueCaptor2.capture(), valueCaptor3.capture(),
      accCaptor.capture());
    assertThat(valueCaptor1.getAllValues()).isEmpty();
    assertThat(valueCaptor2.getAllValues()).isEmpty();
    assertThat(valueCaptor3.getAllValues()).isEmpty();
    assertThat(accCaptor.getAllValues()).isEmpty();

    assertThat(
      repeat3(-3, value1, value2, value3, acc, block)
    ).isSameAs(acc);
    verify(block, times(0)).accept(valueCaptor1.capture(), valueCaptor2.capture(), valueCaptor3.capture(),
      accCaptor.capture());
    assertThat(valueCaptor1.getAllValues()).isEmpty();
    assertThat(valueCaptor2.getAllValues()).isEmpty();
    assertThat(valueCaptor3.getAllValues()).isEmpty();
    assertThat(accCaptor.getAllValues()).isEmpty();

    assertThat(
      repeat3(3, value1, value2, value3, acc, block)
    ).isSameAs(acc);
    verify(block, times(3)).accept(valueCaptor1.capture(), valueCaptor2.capture(), valueCaptor3.capture(),
      accCaptor.capture());
    assertThat(valueCaptor1.getAllValues()).containsExactly(value1, value1, value1);
    assertThat(valueCaptor2.getAllValues()).containsExactly(value2, value2, value2);
    assertThat(valueCaptor3.getAllValues()).containsExactly(value3, value3, value3);
    assertThat(accCaptor.getAllValues()).containsExactly(acc, acc, acc);
  }

  @Test
  void repeat3MethodWithIdxAndAccNullBlockArg() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final Object acc = new Object();
    final Th5ConsumerIntObj4<Object, Object, Object, Object, Error> block = null;

    assertThatThrownBy(() ->
      repeat3(3, value1, value2, value3, acc, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void repeat3WithIdxAndAccMethod() {
    final Object value1 = new Object();
    final ArgumentCaptor<Object> valueCaptor1 = ArgumentCaptor.forClass(Object.class);
    final Object value2 = new Object();
    final ArgumentCaptor<Object> valueCaptor2 = ArgumentCaptor.forClass(Object.class);
    final Object value3 = new Object();
    final ArgumentCaptor<Object> valueCaptor3 = ArgumentCaptor.forClass(Object.class);
    final Object acc = new Object();
    final ArgumentCaptor<Object> accCaptor = ArgumentCaptor.forClass(Object.class);
    final ArgumentCaptor<Integer> idxCaptor = ArgumentCaptor.forClass(Integer.class);
    final Th5ConsumerIntObj4<Object, Object, Object, Object, Error> block = mock(Th5ConsumerIntObj4.class);

    assertThat(
      repeat3(0, value1, value2, value3, acc, block)
    ).isSameAs(acc);
    verify(block, times(0)).accept(idxCaptor.capture(), valueCaptor1.capture(), valueCaptor2.capture(),
      valueCaptor3.capture(), accCaptor.capture());
    assertThat(valueCaptor1.getAllValues()).isEmpty();
    assertThat(valueCaptor2.getAllValues()).isEmpty();
    assertThat(valueCaptor3.getAllValues()).isEmpty();
    assertThat(accCaptor.getAllValues()).isEmpty();
    assertThat(idxCaptor.getAllValues()).isEmpty();

    assertThat(
      repeat3(-3, value1, value2, value3, acc, block)
    ).isSameAs(acc);
    verify(block, times(0)).accept(idxCaptor.capture(), valueCaptor1.capture(), valueCaptor2.capture(),
      valueCaptor3.capture(), accCaptor.capture());
    assertThat(valueCaptor1.getAllValues()).isEmpty();
    assertThat(valueCaptor2.getAllValues()).isEmpty();
    assertThat(valueCaptor3.getAllValues()).isEmpty();
    assertThat(accCaptor.getAllValues()).isEmpty();
    assertThat(idxCaptor.getAllValues()).isEmpty();

    assertThat(
      repeat3(3, value1, value2, value3, acc, block)
    ).isSameAs(acc);
    verify(block, times(3)).accept(idxCaptor.capture(), valueCaptor1.capture(), valueCaptor2.capture(),
      valueCaptor3.capture(), accCaptor.capture());
    assertThat(valueCaptor1.getAllValues()).containsExactly(value1, value1, value1);
    assertThat(valueCaptor2.getAllValues()).containsExactly(value2, value2, value2);
    assertThat(valueCaptor3.getAllValues()).containsExactly(value3, value3, value3);
    assertThat(accCaptor.getAllValues()).containsExactly(acc, acc, acc);
    assertThat(idxCaptor.getAllValues()).containsExactly(0, 1, 2);
  }
}
