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
import com.plugatar.jkscope.function.Th4ConsumerIntObj3;
import com.plugatar.jkscope.function.ThConsumer;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.plugatar.jkscope.JKScope.iterateOver;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests for methods:
 * <ul>
 * <li>{@link JKScope#iterateOver(Object[], ThConsumer)}</li>
 * <li>{@link JKScope#iterateOver(Object[], Th2ConsumerIntObj)}</li>
 * <li>{@link JKScope#iterateOver(Object[], Object, Th2Consumer)}</li>
 * <li>{@link JKScope#iterateOver(Object[], Object, Th3ConsumerIntObj2)}</li>
 * <li>{@link JKScope#iterateOver(Iterable, ThConsumer)}</li>
 * <li>{@link JKScope#iterateOver(Iterable, Th2ConsumerIntObj)}</li>
 * <li>{@link JKScope#iterateOver(Iterable, Object, Th2Consumer)}</li>
 * <li>{@link JKScope#iterateOver(Iterable, Object, Th3ConsumerIntObj2)}</li>
 * <li>{@link JKScope#iterateOver(Iterator, ThConsumer)}</li>
 * <li>{@link JKScope#iterateOver(Iterator, Th2ConsumerIntObj)}</li>
 * <li>{@link JKScope#iterateOver(Iterator, Object, Th2Consumer)}</li>
 * <li>{@link JKScope#iterateOver(Iterator, Object, Th3ConsumerIntObj2)}</li>
 * <li>{@link JKScope#iterateOver(Map, Th2Consumer)}</li>
 * <li>{@link JKScope#iterateOver(Map, Th3ConsumerIntObj2)}</li>
 * <li>{@link JKScope#iterateOver(Map, Object, Th3Consumer)}</li>
 * <li>{@link JKScope#iterateOver(Map, Object, Th4ConsumerIntObj3)}</li>
 * </ul>
 */
@SuppressWarnings("unchecked")
final class IterateOverMethodsTest {

  @Test
  void iterateOverMethodArrayNullBlockArg() {
    final Object[] array = {};
    final ThConsumer<Object, Error> block = null;

    assertThatThrownBy(() ->
      iterateOver(array, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void iterateOverMethodArray() {
    final Object[] array = {"a", "b", "c"};
    final ArgumentCaptor<Object> elementsCaptor = ArgumentCaptor.forClass(Object.class);
    final ThConsumer<Object, Error> block = mock(ThConsumer.class);

    iterateOver(array, block);
    verify(block, times(3)).accept(elementsCaptor.capture());
    assertThat(elementsCaptor.getAllValues()).containsExactly("a", "b", "c");
  }

  @Test
  void iterateOverMethodArrayWithIdxNullBlockArg() {
    final Object[] array = {};
    final Th2ConsumerIntObj<Object, Error> block = null;

    assertThatThrownBy(() ->
      iterateOver(array, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void iterateOverMethodArrayWithIdx() {
    final Object[] array = {"a", "b", "c"};
    final ArgumentCaptor<Object> elementsCaptor = ArgumentCaptor.forClass(Object.class);
    final ArgumentCaptor<Integer> idxCaptor = ArgumentCaptor.forClass(Integer.class);
    final Th2ConsumerIntObj<Object, Error> block = mock(Th2ConsumerIntObj.class);

    iterateOver(array, block);
    verify(block, times(3)).accept(idxCaptor.capture(), elementsCaptor.capture());
    assertThat(elementsCaptor.getAllValues()).containsExactly("a", "b", "c");
    assertThat(idxCaptor.getAllValues()).containsExactly(0, 1, 2);
  }

  @Test
  void iterateOverMethodArrayWithAccNullBlockArg() {
    final Object[] array = {};
    final Object acc = new Object();
    final Th2Consumer<Object, Object, Error> block = null;

    assertThatThrownBy(() ->
      iterateOver(array, acc, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void iterateOverMethodArrayWithAcc() {
    final Object[] array = {"a", "b", "c"};
    final ArgumentCaptor<Object> elementsCaptor = ArgumentCaptor.forClass(Object.class);
    final Object acc = new Object();
    final ArgumentCaptor<Object> accCaptor = ArgumentCaptor.forClass(Object.class);
    final Th2Consumer<Object, Object, Error> block = mock(Th2Consumer.class);

    iterateOver(array, acc, block);
    verify(block, times(3)).accept(elementsCaptor.capture(), accCaptor.capture());
    assertThat(elementsCaptor.getAllValues()).containsExactly("a", "b", "c");
    assertThat(accCaptor.getAllValues()).containsExactly(acc, acc, acc);
  }

  @Test
  void iterateOverMethodArrayWithIdxAndAccNullBlockArg() {
    final Object[] array = {};
    final Object acc = new Object();
    final Th3ConsumerIntObj2<Object, Object, Error> block = null;

    assertThatThrownBy(() ->
      iterateOver(array, acc, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void iterateOverMethodArrayWithIdxAndAcc() {
    final Object[] array = {"a", "b", "c"};
    final ArgumentCaptor<Object> elementsCaptor = ArgumentCaptor.forClass(Object.class);
    final Object acc = new Object();
    final ArgumentCaptor<Object> accCaptor = ArgumentCaptor.forClass(Object.class);
    final ArgumentCaptor<Integer> idxCaptor = ArgumentCaptor.forClass(Integer.class);
    final Th3ConsumerIntObj2<Object, Object, Error> block = mock(Th3ConsumerIntObj2.class);

    iterateOver(array, acc, block);
    verify(block, times(3)).accept(idxCaptor.capture(), elementsCaptor.capture(), accCaptor.capture());
    assertThat(elementsCaptor.getAllValues()).containsExactly("a", "b", "c");
    assertThat(accCaptor.getAllValues()).containsExactly(acc, acc, acc);
    assertThat(idxCaptor.getAllValues()).containsExactly(0, 1, 2);
  }

  @Test
  void iterateOverMethodIterableNullBlockArg() {
    final Iterable<Object> iterable = new ArrayList<>();
    final ThConsumer<Object, Error> block = null;

    assertThatThrownBy(() ->
      iterateOver(iterable, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void iterateOverMethodIterable() {
    final Iterable<Object> iterable = Arrays.asList("a", "b", "c");
    final ArgumentCaptor<Object> elementsCaptor = ArgumentCaptor.forClass(Object.class);
    final ThConsumer<Object, Error> block = mock(ThConsumer.class);

    iterateOver(iterable, block);
    verify(block, times(3)).accept(elementsCaptor.capture());
    assertThat(elementsCaptor.getAllValues()).containsExactly("a", "b", "c");
  }

  @Test
  void iterateOverMethodIterableWithIdxNullBlockArg() {
    final Iterable<Object> iterable = new ArrayList<>();
    final Th2ConsumerIntObj<Object, Error> block = null;

    assertThatThrownBy(() ->
      iterateOver(iterable, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void iterateOverMethodIterableWithIdx() {
    final Iterable<Object> iterable = Arrays.asList("a", "b", "c");
    final ArgumentCaptor<Object> elementsCaptor = ArgumentCaptor.forClass(Object.class);
    final ArgumentCaptor<Integer> idxCaptor = ArgumentCaptor.forClass(Integer.class);
    final Th2ConsumerIntObj<Object, Error> block = mock(Th2ConsumerIntObj.class);

    iterateOver(iterable, block);
    verify(block, times(3)).accept(idxCaptor.capture(), elementsCaptor.capture());
    assertThat(elementsCaptor.getAllValues()).containsExactly("a", "b", "c");
    assertThat(idxCaptor.getAllValues()).containsExactly(0, 1, 2);
  }

  @Test
  void iterateOverMethodIterableWithAccNullBlockArg() {
    final Iterable<Object> iterable = new ArrayList<>();
    final Object acc = new Object();
    final Th2Consumer<Object, Object, Error> block = null;

    assertThatThrownBy(() ->
      iterateOver(iterable, acc, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void iterateOverMethodIterableWithAcc() {
    final Iterable<Object> iterable = Arrays.asList("a", "b", "c");
    final ArgumentCaptor<Object> elementsCaptor = ArgumentCaptor.forClass(Object.class);
    final Object acc = new Object();
    final ArgumentCaptor<Object> accCaptor = ArgumentCaptor.forClass(Object.class);
    final Th2Consumer<Object, Object, Error> block = mock(Th2Consumer.class);

    iterateOver(iterable, acc, block);
    verify(block, times(3)).accept(elementsCaptor.capture(), accCaptor.capture());
    assertThat(elementsCaptor.getAllValues()).containsExactly("a", "b", "c");
    assertThat(accCaptor.getAllValues()).containsExactly(acc, acc, acc);
  }

  @Test
  void iterateOverMethodIterableWithIdxAndAccNullBlockArg() {
    final Iterable<Object> iterable = new ArrayList<>();
    final Object acc = new Object();
    final Th3ConsumerIntObj2<Object, Object, Error> block = null;

    assertThatThrownBy(() ->
      iterateOver(iterable, acc, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void iterateOverMethodIterableWithIdxAndAcc() {
    final Iterable<Object> iterable = Arrays.asList("a", "b", "c");
    final ArgumentCaptor<Object> elementsCaptor = ArgumentCaptor.forClass(Object.class);
    final Object acc = new Object();
    final ArgumentCaptor<Object> accCaptor = ArgumentCaptor.forClass(Object.class);
    final ArgumentCaptor<Integer> idxCaptor = ArgumentCaptor.forClass(Integer.class);
    final Th3ConsumerIntObj2<Object, Object, Error> block = mock(Th3ConsumerIntObj2.class);

    iterateOver(iterable, acc, block);
    verify(block, times(3)).accept(idxCaptor.capture(), elementsCaptor.capture(), accCaptor.capture());
    assertThat(elementsCaptor.getAllValues()).containsExactly("a", "b", "c");
    assertThat(accCaptor.getAllValues()).containsExactly(acc, acc, acc);
    assertThat(idxCaptor.getAllValues()).containsExactly(0, 1, 2);
  }

  @Test
  void iterateOverMethodIteratorNullBlockArg() {
    final Iterator<Object> iterator = Collections.emptyIterator();
    final ThConsumer<Object, Error> block = null;

    assertThatThrownBy(() ->
      iterateOver(iterator, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void iterateOverMethodIterator() {
    final Iterator<Object> iterator = Arrays.<Object>asList("a", "b", "c").iterator();
    final ArgumentCaptor<Object> elementsCaptor = ArgumentCaptor.forClass(Object.class);
    final ThConsumer<Object, Error> block = mock(ThConsumer.class);

    iterateOver(iterator, block);
    verify(block, times(3)).accept(elementsCaptor.capture());
    assertThat(elementsCaptor.getAllValues()).containsExactly("a", "b", "c");
  }

  @Test
  void iterateOverMethodIteratorWithIdxNullBlockArg() {
    final Iterator<Object> iterator = Collections.emptyIterator();
    final Th2ConsumerIntObj<Object, Error> block = null;

    assertThatThrownBy(() ->
      iterateOver(iterator, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void iterateOverMethodIteratorWithIdx() {
    final Iterator<Object> iterator = Arrays.<Object>asList("a", "b", "c").iterator();
    final ArgumentCaptor<Object> elementsCaptor = ArgumentCaptor.forClass(Object.class);
    final ArgumentCaptor<Integer> idxCaptor = ArgumentCaptor.forClass(Integer.class);
    final Th2ConsumerIntObj<Object, Error> block = mock(Th2ConsumerIntObj.class);

    iterateOver(iterator, block);
    verify(block, times(3)).accept(idxCaptor.capture(), elementsCaptor.capture());
    assertThat(elementsCaptor.getAllValues()).containsExactly("a", "b", "c");
    assertThat(idxCaptor.getAllValues()).containsExactly(0, 1, 2);
  }

  @Test
  void iterateOverMethodIteratorWithAccNullBlockArg() {
    final Iterator<Object> iterator = Collections.emptyIterator();
    final Object acc = new Object();
    final Th2Consumer<Object, Object, Error> block = null;

    assertThatThrownBy(() ->
      iterateOver(iterator, acc, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void iterateOverMethodIteratorWithAcc() {
    final Iterator<Object> iterator = Arrays.<Object>asList("a", "b", "c").iterator();
    final ArgumentCaptor<Object> elementsCaptor = ArgumentCaptor.forClass(Object.class);
    final Object acc = new Object();
    final ArgumentCaptor<Object> accCaptor = ArgumentCaptor.forClass(Object.class);
    final Th2Consumer<Object, Object, Error> block = mock(Th2Consumer.class);

    iterateOver(iterator, acc, block);
    verify(block, times(3)).accept(elementsCaptor.capture(), accCaptor.capture());
    assertThat(elementsCaptor.getAllValues()).containsExactly("a", "b", "c");
    assertThat(accCaptor.getAllValues()).containsExactly(acc, acc, acc);
  }

  @Test
  void iterateOverMethodIteratorWithIdxAndAccNullBlockArg() {
    final Iterator<Object> iterator = Collections.emptyIterator();
    final Object acc = new Object();
    final Th3ConsumerIntObj2<Object, Object, Error> block = null;

    assertThatThrownBy(() ->
      iterateOver(iterator, acc, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void iterateOverMethodIteratorWithIdxAndAcc() {
    final Iterator<Object> iterator = Arrays.<Object>asList("a", "b", "c").iterator();
    final ArgumentCaptor<Object> elementsCaptor = ArgumentCaptor.forClass(Object.class);
    final Object acc = new Object();
    final ArgumentCaptor<Object> accCaptor = ArgumentCaptor.forClass(Object.class);
    final ArgumentCaptor<Integer> idxCaptor = ArgumentCaptor.forClass(Integer.class);
    final Th3ConsumerIntObj2<Object, Object, Error> block = mock(Th3ConsumerIntObj2.class);

    iterateOver(iterator, acc, block);
    verify(block, times(3)).accept(idxCaptor.capture(), elementsCaptor.capture(), accCaptor.capture());
    assertThat(elementsCaptor.getAllValues()).containsExactly("a", "b", "c");
    assertThat(accCaptor.getAllValues()).containsExactly(acc, acc, acc);
    assertThat(idxCaptor.getAllValues()).containsExactly(0, 1, 2);
  }


  @Test
  void iterateOverMethodMapNullBlockArg() {
    final Map<Object, Object> map = Collections.emptyMap();
    final Th2Consumer<Object, Object, Error> block = null;

    assertThatThrownBy(() ->
      iterateOver(map, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void iterateOverMethodMap() {
    final Map<Object, Object> map = mapOf(
      "key a", "val a",
      "key b", "val b",
      "key c", "val c"
    );
    final ArgumentCaptor<Object> keyCaptor = ArgumentCaptor.forClass(Object.class);
    final ArgumentCaptor<Object> valueCaptor = ArgumentCaptor.forClass(Object.class);
    final Th2Consumer<Object, Object, Error> block = mock(Th2Consumer.class);

    iterateOver(map, block);
    verify(block, times(3)).accept(keyCaptor.capture(), valueCaptor.capture());
    assertThat(keyCaptor.getAllValues()).containsExactly("key a", "key b", "key c");
    assertThat(valueCaptor.getAllValues()).containsExactly("val a", "val b", "val c");
  }

  @Test
  void iterateOverMethodMapWithIdxNullBlockArg() {
    final Map<Object, Object> map = Collections.emptyMap();
    final Th3ConsumerIntObj2<Object, Object, Error> block = null;

    assertThatThrownBy(() ->
      iterateOver(map, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void iterateOverMethodMapWithIdx() {
    final Map<Object, Object> map = mapOf(
      "key a", "val a",
      "key b", "val b",
      "key c", "val c"
    );
    final ArgumentCaptor<Object> keyCaptor = ArgumentCaptor.forClass(Object.class);
    final ArgumentCaptor<Object> valueCaptor = ArgumentCaptor.forClass(Object.class);
    final ArgumentCaptor<Integer> idxCaptor = ArgumentCaptor.forClass(Integer.class);
    final Th3ConsumerIntObj2<Object, Object, Error> block = mock(Th3ConsumerIntObj2.class);

    iterateOver(map, block);
    verify(block, times(3)).accept(idxCaptor.capture(), keyCaptor.capture(), valueCaptor.capture());
    assertThat(keyCaptor.getAllValues()).containsExactly("key a", "key b", "key c");
    assertThat(valueCaptor.getAllValues()).containsExactly("val a", "val b", "val c");
    assertThat(idxCaptor.getAllValues()).containsExactly(0, 1, 2);
  }

  @Test
  void iterateOverMethodMapWithAccNullBlockArg() {
    final Map<Object, Object> map = Collections.emptyMap();
    final Object acc = new Object();
    final Th3Consumer<Object, Object, Object, Error> block = null;

    assertThatThrownBy(() ->
      iterateOver(map, acc, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void iterateOverMethodMapWithAcc() {
    final Map<Object, Object> map = mapOf(
      "key a", "val a",
      "key b", "val b",
      "key c", "val c"
    );
    final ArgumentCaptor<Object> keyCaptor = ArgumentCaptor.forClass(Object.class);
    final ArgumentCaptor<Object> valueCaptor = ArgumentCaptor.forClass(Object.class);
    final Object acc = new Object();
    final ArgumentCaptor<Object> accCaptor = ArgumentCaptor.forClass(Object.class);
    final Th3Consumer<Object, Object, Object, Error> block = mock(Th3Consumer.class);

    iterateOver(map, acc, block);
    verify(block, times(3)).accept(keyCaptor.capture(), valueCaptor.capture(), accCaptor.capture());
    assertThat(keyCaptor.getAllValues()).containsExactly("key a", "key b", "key c");
    assertThat(valueCaptor.getAllValues()).containsExactly("val a", "val b", "val c");
    assertThat(accCaptor.getAllValues()).containsExactly(acc, acc, acc);
  }

  @Test
  void iterateOverMethodMapWithIdxAndAccNullBlockArg() {
    final Map<Object, Object> map = Collections.emptyMap();
    final Object acc = new Object();
    final Th4ConsumerIntObj3<Object, Object, Object, Error> block = null;

    assertThatThrownBy(() ->
      iterateOver(map, acc, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void iterateOverMethodMapWithIdxAndAcc() {
    final Map<Object, Object> map = mapOf(
      "key a", "val a",
      "key b", "val b",
      "key c", "val c"
    );
    final ArgumentCaptor<Object> keyCaptor = ArgumentCaptor.forClass(Object.class);
    final ArgumentCaptor<Object> valueCaptor = ArgumentCaptor.forClass(Object.class);
    final Object acc = new Object();
    final ArgumentCaptor<Object> accCaptor = ArgumentCaptor.forClass(Object.class);
    final ArgumentCaptor<Integer> idxCaptor = ArgumentCaptor.forClass(Integer.class);
    final Th4ConsumerIntObj3<Object, Object, Object, Error> block = mock(Th4ConsumerIntObj3.class);

    iterateOver(map, acc, block);
    verify(block, times(3)).accept(idxCaptor.capture(), keyCaptor.capture(), valueCaptor.capture(),
      accCaptor.capture());
    assertThat(keyCaptor.getAllValues()).containsExactly("key a", "key b", "key c");
    assertThat(valueCaptor.getAllValues()).containsExactly("val a", "val b", "val c");
    assertThat(accCaptor.getAllValues()).containsExactly(acc, acc, acc);
    assertThat(idxCaptor.getAllValues()).containsExactly(0, 1, 2);
  }

  private static Map<Object, Object> mapOf(final Object key1,
                                           final Object value1,
                                           final Object key2,
                                           final Object value2,
                                           final Object key3,
                                           final Object value3) {
    final Map<Object, Object> map = new LinkedHashMap<>();
    map.put(key1, value1);
    map.put(key2, value2);
    map.put(key3, value3);
    return map;
  }
}
