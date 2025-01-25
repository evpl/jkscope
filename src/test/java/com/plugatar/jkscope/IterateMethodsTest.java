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

import com.plugatar.jkscope.JKScope.ItrDeque;
import com.plugatar.jkscope.JKScope.ItrDeque2;
import com.plugatar.jkscope.JKScope.ItrDeque3;
import com.plugatar.jkscope.function.Th2Consumer;
import com.plugatar.jkscope.function.Th3Consumer;
import com.plugatar.jkscope.function.Th3ConsumerIntObj2;
import com.plugatar.jkscope.function.Th4Consumer;
import com.plugatar.jkscope.function.Th4ConsumerIntObj3;
import com.plugatar.jkscope.function.Th5Consumer;
import com.plugatar.jkscope.function.Th5ConsumerIntObj4;
import com.plugatar.jkscope.function.Th6ConsumerIntObj5;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.plugatar.jkscope.JKScope.iterate1;
import static com.plugatar.jkscope.JKScope.iterate2;
import static com.plugatar.jkscope.JKScope.iterate3;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for methods:
 * <ul>
 * <li>{@link JKScope#iterate1(Object, Th2Consumer)}</li>
 * <li>{@link JKScope#iterate1(Object, Th3ConsumerIntObj2)}</li>
 * <li>{@link JKScope#iterate1(Object, Object, Th3Consumer)}</li>
 * <li>{@link JKScope#iterate1(Object, Object, Th4ConsumerIntObj3)}</li>
 * <li>{@link JKScope#iterate2(Object, Object, Th3Consumer)}</li>
 * <li>{@link JKScope#iterate2(Object, Object, Th4ConsumerIntObj3)}</li>
 * <li>{@link JKScope#iterate2(Object, Object, Object, Th4Consumer)}</li>
 * <li>{@link JKScope#iterate2(Object, Object, Object, Th5ConsumerIntObj4)}</li>
 * <li>{@link JKScope#iterate3(Object, Object, Object, Th4Consumer)}</li>
 * <li>{@link JKScope#iterate3(Object, Object, Object, Th5ConsumerIntObj4)}</li>
 * <li>{@link JKScope#iterate3(Object, Object, Object, Object, Th5Consumer)}</li>
 * <li>{@link JKScope#iterate3(Object, Object, Object, Object, Th6ConsumerIntObj5)}</li>
 * </ul>
 */
final class IterateMethodsTest {

  @Test
  void iterate1MethodNullBlockArg() {
    final Object value = new Object();
    final Th2Consumer<Object, ItrDeque<Object>, Error> block = null;

    assertThatThrownBy(() ->
      iterate1(value, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void iterate1Method() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final AtomicInteger counter = new AtomicInteger();
    final List<Object> values = new ArrayList<>();
    final Th2Consumer<Object, ItrDeque<Object>, Error> block = (v, deque) -> {
      values.add(v);
      switch (counter.get()) {
        case 0:
          deque.push(value2);
          break;
        case 1:
          deque.push(value3);
          break;
      }
      counter.getAndIncrement();
    };

    iterate1(value1, block);
    assertThat(values).containsExactly(value1, value2, value3);
  }

  @Test
  void iterate1MethodWithIdxNullBlockArg() {
    final Object value = new Object();
    final Th3ConsumerIntObj2<Object, ItrDeque<Object>, Error> block = null;

    assertThatThrownBy(() ->
      iterate1(value, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void iterate1WithIdxMethod() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final AtomicInteger counter = new AtomicInteger();
    final List<Integer> indices = new ArrayList<>();
    final List<Object> values = new ArrayList<>();
    final Th3ConsumerIntObj2<Object, ItrDeque<Object>, Error> block = (idx, v, deque) -> {
      indices.add(idx);
      values.add(v);
      switch (counter.get()) {
        case 0:
          deque.push(value2);
          break;
        case 1:
          deque.push(value3);
          break;
      }
      counter.getAndIncrement();
    };

    iterate1(value1, block);
    assertThat(indices).containsExactly(0, 1, 2);
    assertThat(values).containsExactly(value1, value2, value3);
  }

  @Test
  void iterate1MethodWithAccNullBlockArg() {
    final Object value = new Object();
    final Object acc = new Object();
    final Th3Consumer<Object, Object, ItrDeque<Object>, Error> block = null;

    assertThatThrownBy(() ->
      iterate1(value, acc, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void iterate1MethodWithAcc() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final Object acc = new Object();
    final AtomicInteger counter = new AtomicInteger();
    final List<Object> values = new ArrayList<>();
    final List<Object> accs = new ArrayList<>();
    final Th3Consumer<Object, Object, ItrDeque<Object>, Error> block = (v, a, deque) -> {
      values.add(v);
      accs.add(a);
      switch (counter.get()) {
        case 0:
          deque.push(value2);
          break;
        case 1:
          deque.push(value3);
          break;
      }
      counter.getAndIncrement();
    };

    iterate1(value1, acc, block);
    assertThat(values).containsExactly(value1, value2, value3);
    assertThat(accs).containsExactly(acc, acc, acc);
  }

  @Test
  void iterate1MethodWithIdxAndAccNullBlockArg() {
    final Object value = new Object();
    final Object acc = new Object();
    final Th4ConsumerIntObj3<Object, Object, ItrDeque<Object>, Error> block = null;

    assertThatThrownBy(() ->
      iterate1(value, acc, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void iterate1MethodWithIdxAndAcc() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final Object acc = new Object();
    final AtomicInteger counter = new AtomicInteger();
    final List<Integer> indices = new ArrayList<>();
    final List<Object> values = new ArrayList<>();
    final List<Object> accs = new ArrayList<>();
    final Th4ConsumerIntObj3<Object, Object, ItrDeque<Object>, Error> block = (idx, v, a, deque) -> {
      indices.add(idx);
      values.add(v);
      accs.add(a);
      switch (counter.get()) {
        case 0:
          deque.push(value2);
          break;
        case 1:
          deque.push(value3);
          break;
      }
      counter.getAndIncrement();
    };

    iterate1(value1, acc, block);
    assertThat(indices).containsExactly(0, 1, 2);
    assertThat(values).containsExactly(value1, value2, value3);
    assertThat(accs).containsExactly(acc, acc, acc);
  }

  @Test
  void iterate2MethodNullBlockArg() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Th3Consumer<Object, Object, ItrDeque2<Object, Object>, Error> block = null;

    assertThatThrownBy(() ->
      iterate2(value1, value2, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void iterate2Method() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final Object value4 = new Object();
    final Object value5 = new Object();
    final Object value6 = new Object();
    final AtomicInteger counter = new AtomicInteger();
    final List<Object> values = new ArrayList<>();
    final Th3Consumer<Object, Object, ItrDeque2<Object, Object>, Error> block = (v1, v2, deque) -> {
      values.add(v1);
      values.add(v2);
      switch (counter.get()) {
        case 0:
          deque.push(value3, value4);
          break;
        case 1:
          deque.push(value5, value6);
          break;
      }
      counter.getAndIncrement();
    };

    iterate2(value1, value2, block);
    assertThat(values).containsExactly(value1, value2, value3, value4, value5, value6);
  }

  @Test
  void iterate2MethodWithIdxNullBlockArg() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Th4ConsumerIntObj3<Object, Object, ItrDeque2<Object, Object>, Error> block = null;

    assertThatThrownBy(() ->
      iterate2(value1, value2, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void iterate2WithIdxMethod() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final Object value4 = new Object();
    final Object value5 = new Object();
    final Object value6 = new Object();
    final AtomicInteger counter = new AtomicInteger();
    final List<Integer> indices = new ArrayList<>();
    final List<Object> values = new ArrayList<>();
    final Th4ConsumerIntObj3<Object, Object, ItrDeque2<Object, Object>, Error> block = (idx, v1, v2, deque) -> {
      indices.add(idx);
      values.add(v1);
      values.add(v2);
      switch (counter.get()) {
        case 0:
          deque.push(value3, value4);
          break;
        case 1:
          deque.push(value5, value6);
          break;
      }
      counter.getAndIncrement();
    };

    iterate2(value1, value2, block);
    assertThat(indices).containsExactly(0, 1, 2);
    assertThat(values).containsExactly(value1, value2, value3, value4, value5, value6);
  }

  @Test
  void iterate2MethodWithAccNullBlockArg() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object acc = new Object();
    final Th4Consumer<Object, Object, Object, ItrDeque2<Object, Object>, Error> block = null;

    assertThatThrownBy(() ->
      iterate2(value1, value2, acc, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void iterate2MethodWithAcc() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final Object value4 = new Object();
    final Object value5 = new Object();
    final Object value6 = new Object();
    final Object acc = new Object();
    final AtomicInteger counter = new AtomicInteger();
    final List<Object> values = new ArrayList<>();
    final List<Object> accs = new ArrayList<>();
    final Th4Consumer<Object, Object, Object, ItrDeque2<Object, Object>, Error> block = (v1, v2, a, deque) -> {
      values.add(v1);
      values.add(v2);
      accs.add(a);
      switch (counter.get()) {
        case 0:
          deque.push(value3, value4);
          break;
        case 1:
          deque.push(value5, value6);
          break;
      }
      counter.getAndIncrement();
    };

    iterate2(value1, value2, acc, block);
    assertThat(values).containsExactly(value1, value2, value3, value4, value5, value6);
    assertThat(accs).containsExactly(acc, acc, acc);
  }

  @Test
  void iterate2MethodWithIdxAndAccNullBlockArg() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object acc = new Object();
    final Th5ConsumerIntObj4<Object, Object, Object, ItrDeque2<Object, Object>, Error> block = null;

    assertThatThrownBy(() ->
      iterate2(value1, value2, acc, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void iterate2MethodWithIdxAndAcc() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final Object value4 = new Object();
    final Object value5 = new Object();
    final Object value6 = new Object();
    final Object acc = new Object();
    final AtomicInteger counter = new AtomicInteger();
    final List<Integer> indices = new ArrayList<>();
    final List<Object> values = new ArrayList<>();
    final List<Object> accs = new ArrayList<>();
    final Th5ConsumerIntObj4<Object, Object, Object, ItrDeque2<Object, Object>, Error> block = (idx, v1, v2, a, deque) -> {
      indices.add(idx);
      values.add(v1);
      values.add(v2);
      accs.add(a);
      switch (counter.get()) {
        case 0:
          deque.push(value3, value4);
          break;
        case 1:
          deque.push(value5, value6);
          break;
      }
      counter.getAndIncrement();
    };

    iterate2(value1, value2, acc, block);
    assertThat(indices).containsExactly(0, 1, 2);
    assertThat(values).containsExactly(value1, value2, value3, value4, value5, value6);
    assertThat(accs).containsExactly(acc, acc, acc);
  }

  @Test
  void iterate3MethodNullBlockArg() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final Th4Consumer<Object, Object, Object, ItrDeque3<Object, Object, Object>, Error> block = null;

    assertThatThrownBy(() ->
      iterate3(value1, value2, value3, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void iterate3Method() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final Object value4 = new Object();
    final Object value5 = new Object();
    final Object value6 = new Object();
    final Object value7 = new Object();
    final Object value8 = new Object();
    final Object value9 = new Object();
    final AtomicInteger counter = new AtomicInteger();
    final List<Object> values = new ArrayList<>();
    final Th4Consumer<Object, Object, Object, ItrDeque3<Object, Object, Object>, Error> block = (v1, v2, v3, deque) -> {
      values.add(v1);
      values.add(v2);
      values.add(v3);
      switch (counter.get()) {
        case 0:
          deque.push(value4, value5, value6);
          break;
        case 1:
          deque.push(value7, value8, value9);
          break;
      }
      counter.getAndIncrement();
    };

    iterate3(value1, value2, value3, block);
    assertThat(values).containsExactly(value1, value2, value3, value4, value5, value6, value7, value8, value9);
  }

  @Test
  void iterate3MethodWithIdxNullBlockArg() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final Th5ConsumerIntObj4<Object, Object, Object, ItrDeque3<Object, Object, Object>, Error> block = null;

    assertThatThrownBy(() ->
      iterate3(value1, value2, value3, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void iterate3WithIdxMethod() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final Object value4 = new Object();
    final Object value5 = new Object();
    final Object value6 = new Object();
    final Object value7 = new Object();
    final Object value8 = new Object();
    final Object value9 = new Object();
    final AtomicInteger counter = new AtomicInteger();
    final List<Integer> indices = new ArrayList<>();
    final List<Object> values = new ArrayList<>();
    final Th5ConsumerIntObj4<Object, Object, Object, ItrDeque3<Object, Object, Object>, Error> block =
      (idx, v1, v2, v3, deque) -> {
        indices.add(idx);
        values.add(v1);
        values.add(v2);
        values.add(v3);
        switch (counter.get()) {
          case 0:
            deque.push(value4, value5, value6);
            break;
          case 1:
            deque.push(value7, value8, value9);
            break;
        }
        counter.getAndIncrement();
      };

    iterate3(value1, value2, value3, block);
    assertThat(indices).containsExactly(0, 1, 2);
    assertThat(values).containsExactly(value1, value2, value3, value4, value5, value6, value7, value8, value9);
  }

  @Test
  void iterate3MethodWithAccNullBlockArg() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final Object acc = new Object();
    final Th5Consumer<Object, Object, Object, Object, ItrDeque3<Object, Object, Object>, Error> block = null;

    assertThatThrownBy(() ->
      iterate3(value1, value2, value3, acc, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void iterate3MethodWithAcc() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final Object value4 = new Object();
    final Object value5 = new Object();
    final Object value6 = new Object();
    final Object value7 = new Object();
    final Object value8 = new Object();
    final Object value9 = new Object();
    final Object acc = new Object();
    final AtomicInteger counter = new AtomicInteger();
    final List<Object> values = new ArrayList<>();
    final List<Object> accs = new ArrayList<>();
    final Th5Consumer<Object, Object, Object, Object, ItrDeque3<Object, Object, Object>, Error> block =
      (v1, v2, v3, a, deque) -> {
        values.add(v1);
        values.add(v2);
        values.add(v3);
        accs.add(a);
        switch (counter.get()) {
          case 0:
            deque.push(value4, value5, value6);
            break;
          case 1:
            deque.push(value7, value8, value9);
            break;
        }
        counter.getAndIncrement();
      };

    iterate3(value1, value2, value3, acc, block);
    assertThat(values).containsExactly(value1, value2, value3, value4, value5, value6, value7, value8, value9);
    assertThat(accs).containsExactly(acc, acc, acc);
  }

  @Test
  void iterate3MethodWithIdxAndAccNullBlockArg() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final Object acc = new Object();
    final Th6ConsumerIntObj5<Object, Object, Object, Object, ItrDeque3<Object, Object, Object>, Error> block = null;

    assertThatThrownBy(() ->
      iterate3(value1, value2, value3, acc, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void iterate3MethodWithIdxAndAcc() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final Object value4 = new Object();
    final Object value5 = new Object();
    final Object value6 = new Object();
    final Object value7 = new Object();
    final Object value8 = new Object();
    final Object value9 = new Object();
    final Object acc = new Object();
    final AtomicInteger counter = new AtomicInteger();
    final List<Integer> indices = new ArrayList<>();
    final List<Object> values = new ArrayList<>();
    final List<Object> accs = new ArrayList<>();
    final Th6ConsumerIntObj5<Object, Object, Object, Object, ItrDeque3<Object, Object, Object>, Error> block =
      (idx, v1, v2, v3, a, deque) -> {
        indices.add(idx);
        values.add(v1);
        values.add(v2);
        values.add(v3);
        accs.add(a);
        switch (counter.get()) {
          case 0:
            deque.push(value4, value5, value6);
            break;
          case 1:
            deque.push(value7, value8, value9);
            break;
        }
        counter.getAndIncrement();
      };

    iterate3(value1, value2, value3, acc, block);
    assertThat(indices).containsExactly(0, 1, 2);
    assertThat(values).containsExactly(value1, value2, value3, value4, value5, value6, value7, value8, value9);
    assertThat(accs).containsExactly(acc, acc, acc);
  }
}
