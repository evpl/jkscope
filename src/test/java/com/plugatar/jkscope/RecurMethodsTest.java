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

import com.plugatar.jkscope.JKScope.RecurDepth;
import com.plugatar.jkscope.function.Th2Consumer;
import com.plugatar.jkscope.function.Th2Function;
import com.plugatar.jkscope.function.Th3Consumer;
import com.plugatar.jkscope.function.Th3Function;
import com.plugatar.jkscope.function.Th4Consumer;
import com.plugatar.jkscope.function.Th4Function;
import com.plugatar.jkscope.function.Th5Consumer;
import com.plugatar.jkscope.function.Th5Function;
import com.plugatar.jkscope.function.Th6Consumer;
import com.plugatar.jkscope.function.ThConsumer;
import com.plugatar.jkscope.function.ThFunction;
import com.plugatar.jkscope.function.ThRunnable;
import com.plugatar.jkscope.function.ThSupplier;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.plugatar.jkscope.JKScope.recur;
import static com.plugatar.jkscope.JKScope.recur1;
import static com.plugatar.jkscope.JKScope.recur2;
import static com.plugatar.jkscope.JKScope.recur3;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for methods:
 * <ul>r
 * <li>{@link JKScope#recur(ThConsumer)}</li>
 * <li>{@link JKScope#recur(Th2Consumer)}</li>
 * <li>{@link JKScope#recur(Object, Th2Consumer)}</li>
 * <li>{@link JKScope#recur(Object, Th3Consumer)}</li>
 * <li>{@link JKScope#recur1(Object, Th2Consumer)}</li>
 * <li>{@link JKScope#recur1(Object, Th3Consumer)}</li>
 * <li>{@link JKScope#recur1(Object, Object, Th3Consumer)}</li>
 * <li>{@link JKScope#recur1(Object, Object, Th4Consumer)}</li>
 * <li>{@link JKScope#recur2(Object, Object, Th3Consumer)}</li>
 * <li>{@link JKScope#recur2(Object, Object, Th4Consumer)}</li>
 * <li>{@link JKScope#recur2(Object, Object, Object, Th4Consumer)}</li>
 * <li>{@link JKScope#recur2(Object, Object, Object, Th5Consumer)}</li>
 * <li>{@link JKScope#recur3(Object, Object, Object, Th4Consumer)}</li>
 * <li>{@link JKScope#recur3(Object, Object, Object, Th5Consumer)}</li>
 * <li>{@link JKScope#recur3(Object, Object, Object, Object, Th5Consumer)}</li>
 * <li>{@link JKScope#recur3(Object, Object, Object, Object, Th6Consumer)}</li>
 * <li>{@link JKScope#recur(ThFunction)}</li>
 * <li>{@link JKScope#recur(Th2Function)}</li>
 * <li>{@link JKScope#recur1(Object, Th2Function)}</li>
 * <li>{@link JKScope#recur1(Object, Th3Function)}</li>
 * <li>{@link JKScope#recur2(Object, Object, Th3Function)}</li>
 * <li>{@link JKScope#recur2(Object, Object, Th4Function)}</li>
 * <li>{@link JKScope#recur3(Object, Object, Object, Th4Function)}</li>
 * <li>{@link JKScope#recur3(Object, Object, Object, Th5Function)}</li>
 * </ul>
 */
final class RecurMethodsTest {

  @Test
  void recurMethodNullBlockArg() {
    final ThConsumer<ThRunnable<?>, ?> block = null;

    assertThatThrownBy(() ->
      recur(block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void recurMethod() {
    final AtomicInteger counter = new AtomicInteger();
    final ThConsumer<ThRunnable<?>, ?> block = self -> {
      if (counter.getAndIncrement() < 2) {
        self.run();
      }
    };

    recur(block);
    assertThat(counter.get()).isEqualTo(3);
  }

  @Test
  void recurMethodWithDepthNullBlockArg() {
    final Th2Consumer<RecurDepth, ThRunnable<?>, ?> block = null;

    assertThatThrownBy(() ->
      recur(block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void recurMethodWithDepth() {
    final AtomicInteger counter = new AtomicInteger();
    final List<Integer> depths = new ArrayList<>();
    final Th2Consumer<RecurDepth, ThRunnable<?>, ?> block = (depth, self) -> {
      depths.add(depth.current());
      if (counter.getAndIncrement() < 2) {
        self.run();
      }
      depths.add(depth.current());
    };

    recur(block);
    assertThat(counter.get()).isEqualTo(3);
    assertThat(depths).containsExactly(0, 1, 2, 2, 1, 0);
  }

  @Test
  void recurMethodWithAccNullBlockArg() {
    final Th2Consumer<Object, ThRunnable<?>, ?> block = null;

    assertThatThrownBy(() ->
      recur(block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void recurMethodWithAcc() {
    final Object acc = new Object();
    final AtomicInteger counter = new AtomicInteger();
    final List<Object> accs = new ArrayList<>();
    final Th2Consumer<Object, ThRunnable<?>, ?> block = (a, self) -> {
      accs.add(a);
      if (counter.getAndIncrement() < 2) {
        self.run();
      }
    };

    recur(acc, block);
    assertThat(counter.get()).isEqualTo(3);
    assertThat(accs).containsExactly(acc, acc, acc);
  }

  @Test
  void recurMethodWithDepthAndAccNullBlockArg() {
    final Object acc = new Object();
    final Th3Consumer<RecurDepth, Object, ThRunnable<?>, ?> block = null;

    assertThatThrownBy(() ->
      recur(acc, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void recurMethodWithDepthAndAcc() {
    final Object acc = new Object();
    final AtomicInteger counter = new AtomicInteger();
    final List<Integer> depths = new ArrayList<>();
    final List<Object> accs = new ArrayList<>();
    final Th3Consumer<RecurDepth, Object, ThRunnable<?>, ?> block = (depth, a, self) -> {
      depths.add(depth.current());
      accs.add(a);
      if (counter.getAndIncrement() < 2) {
        self.run();
      }
      depths.add(depth.current());
    };

    recur(acc, block);
    assertThat(counter.get()).isEqualTo(3);
    assertThat(depths).containsExactly(0, 1, 2, 2, 1, 0);
    assertThat(accs).containsExactly(acc, acc, acc);
  }

  @Test
  void recur1MethodNullBlockArg() {
    final Object value = new Object();
    final Th2Consumer<Object, ThConsumer<Object, ?>, ?> block = null;

    assertThatThrownBy(() ->
      recur1(value, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void recur1Method() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final AtomicInteger counter = new AtomicInteger();
    final List<Object> values = new ArrayList<>();
    final Th2Consumer<Object, ThConsumer<Object, ?>, ?> block = (v, self) -> {
      values.add(v);
      switch (counter.getAndIncrement()) {
        case 0:
          self.accept(value2);
          break;
        case 1:
          self.accept(value3);
          break;
      }
    };

    recur1(value1, block);
    assertThat(counter.get()).isEqualTo(3);
    assertThat(values).containsExactly(value1, value2, value3);
  }

  @Test
  void recur1MethodWithDepthNullBlockArg() {
    final Object value = new Object();
    final Th3Consumer<RecurDepth, Object, ThConsumer<Object, ?>, ?> block = null;

    assertThatThrownBy(() ->
      recur1(value, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void recur1MethodWithDepth() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final AtomicInteger counter = new AtomicInteger();
    final List<Integer> depths = new ArrayList<>();
    final List<Object> values = new ArrayList<>();
    final Th3Consumer<RecurDepth, Object, ThConsumer<Object, ?>, ?> block = (depth, v, self) -> {
      depths.add(depth.current());
      values.add(v);
      switch (counter.getAndIncrement()) {
        case 0:
          self.accept(value2);
          break;
        case 1:
          self.accept(value3);
          break;
      }
      depths.add(depth.current());
    };

    recur1(value1, block);
    assertThat(counter.get()).isEqualTo(3);
    assertThat(depths).containsExactly(0, 1, 2, 2, 1, 0);
    assertThat(values).containsExactly(value1, value2, value3);
  }

  @Test
  void recur1MethodWithAccNullBlockArg() {
    final Object value = new Object();
    final Object acc = new Object();
    final Th3Consumer<Object, Object, ThConsumer<Object, ?>, ?> block = null;

    assertThatThrownBy(() ->
      recur1(value, acc, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void recur1MethodWithAcc() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final Object acc = new Object();
    final AtomicInteger counter = new AtomicInteger();
    final List<Object> values = new ArrayList<>();
    final List<Object> accs = new ArrayList<>();
    final Th3Consumer<Object, Object, ThConsumer<Object, ?>, ?> block = (v, a, self) -> {
      values.add(v);
      accs.add(a);
      switch (counter.getAndIncrement()) {
        case 0:
          self.accept(value2);
          break;
        case 1:
          self.accept(value3);
          break;
      }
    };

    recur1(value1, acc, block);
    assertThat(counter.get()).isEqualTo(3);
    assertThat(accs).containsExactly(acc, acc, acc);
  }

  @Test
  void recur1MethodWithDepthAndAccNullBlockArg() {
    final Object value = new Object();
    final Object acc = new Object();
    final Th4Consumer<RecurDepth, Object, Object, ThConsumer<Object, ?>, ?> block = null;

    assertThatThrownBy(() ->
      recur1(value, acc, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void recur1MethodWithDepthAndAcc() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final Object acc = new Object();
    final AtomicInteger counter = new AtomicInteger();
    final List<Integer> depths = new ArrayList<>();
    final List<Object> values = new ArrayList<>();
    final List<Object> accs = new ArrayList<>();
    final Th4Consumer<RecurDepth, Object, Object, ThConsumer<Object, ?>, ?> block = (depth, v, a, self) -> {
      depths.add(depth.current());
      values.add(v);
      accs.add(a);
      switch (counter.getAndIncrement()) {
        case 0:
          self.accept(value2);
          break;
        case 1:
          self.accept(value3);
          break;
      }
      depths.add(depth.current());
    };

    recur1(value1, acc, block);
    assertThat(counter.get()).isEqualTo(3);
    assertThat(depths).containsExactly(0, 1, 2, 2, 1, 0);
    assertThat(values).containsExactly(value1, value2, value3);
    assertThat(accs).containsExactly(acc, acc, acc);
  }

  @Test
  void recur2MethodNullBlockArg() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Th3Consumer<Object, Object, Th2Consumer<Object, Object, ?>, ?> block = null;

    assertThatThrownBy(() ->
      recur2(value1, value2, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void recur2Method() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final Object value4 = new Object();
    final Object value5 = new Object();
    final Object value6 = new Object();
    final AtomicInteger counter = new AtomicInteger();
    final List<Object> values = new ArrayList<>();
    final Th3Consumer<Object, Object, Th2Consumer<Object, Object, ?>, ?> block = (v1, v2, self) -> {
      values.add(v1);
      values.add(v2);
      switch (counter.getAndIncrement()) {
        case 0:
          self.accept(value3, value4);
          break;
        case 1:
          self.accept(value5, value6);
          break;
      }
    };

    recur2(value1, value2, block);
    assertThat(counter.get()).isEqualTo(3);
    assertThat(values).containsExactly(value1, value2, value3, value4, value5, value6);
  }

  @Test
  void recur2MethodWithDepthNullBlockArg() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Th4Consumer<RecurDepth, Object, Object, Th2Consumer<Object, Object, ?>, ?> block = null;

    assertThatThrownBy(() ->
      recur2(value1, value2, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void recur2MethodWithDepth() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final Object value4 = new Object();
    final Object value5 = new Object();
    final Object value6 = new Object();
    final AtomicInteger counter = new AtomicInteger();
    final List<Integer> depths = new ArrayList<>();
    final List<Object> values = new ArrayList<>();
    final Th4Consumer<RecurDepth, Object, Object, Th2Consumer<Object, Object, ?>, ?> block = (depth, v1, v2, self) -> {
      depths.add(depth.current());
      values.add(v1);
      values.add(v2);
      switch (counter.getAndIncrement()) {
        case 0:
          self.accept(value3, value4);
          break;
        case 1:
          self.accept(value5, value6);
          break;
      }
      depths.add(depth.current());
    };

    recur2(value1, value2, block);
    assertThat(counter.get()).isEqualTo(3);
    assertThat(depths).containsExactly(0, 1, 2, 2, 1, 0);
    assertThat(values).containsExactly(value1, value2, value3, value4, value5, value6);
  }

  @Test
  void recur2MethodWithAccNullBlockArg() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object acc = new Object();
    final Th4Consumer<Object, Object, Object, Th2Consumer<Object, Object, ?>, ?> block = null;

    assertThatThrownBy(() ->
      recur2(value1, value2, acc, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void recur2MethodWithAcc() {
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
    final Th4Consumer<Object, Object, Object, Th2Consumer<Object, Object, ?>, ?> block = (v1, v2, a, self) -> {
      values.add(v1);
      values.add(v2);
      accs.add(a);
      switch (counter.getAndIncrement()) {
        case 0:
          self.accept(value3, value4);
          break;
        case 1:
          self.accept(value5, value6);
          break;
      }
    };

    recur2(value1, value2, acc, block);
    assertThat(counter.get()).isEqualTo(3);
    assertThat(accs).containsExactly(acc, acc, acc);
    assertThat(values).containsExactly(value1, value2, value3, value4, value5, value6);
  }

  @Test
  void recur2MethodWithDepthAndAccNullBlockArg() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object acc = new Object();
    final Th5Consumer<RecurDepth, Object, Object, Object, Th2Consumer<Object, Object, ?>, ?> block = null;

    assertThatThrownBy(() ->
      recur2(value1, value2, acc, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void recur2MethodWithDepthAndAcc() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final Object value4 = new Object();
    final Object value5 = new Object();
    final Object value6 = new Object();
    final Object acc = new Object();
    final AtomicInteger counter = new AtomicInteger();
    final List<Integer> depths = new ArrayList<>();
    final List<Object> values = new ArrayList<>();
    final List<Object> accs = new ArrayList<>();
    final Th5Consumer<RecurDepth, Object, Object, Object, Th2Consumer<Object, Object, ?>, ?> block =
      (depth, v1, v2, a, self) -> {
        depths.add(depth.current());
        values.add(v1);
        values.add(v2);
        accs.add(a);
        switch (counter.getAndIncrement()) {
          case 0:
            self.accept(value3, value4);
            break;
          case 1:
            self.accept(value5, value6);
            break;
        }
        depths.add(depth.current());
      };

    recur2(value1, value2, acc, block);
    assertThat(counter.get()).isEqualTo(3);
    assertThat(depths).containsExactly(0, 1, 2, 2, 1, 0);
    assertThat(values).containsExactly(value1, value2, value3, value4, value5, value6);
    assertThat(accs).containsExactly(acc, acc, acc);
  }

  @Test
  void recur3MethodNullBlockArg() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final Th4Consumer<Object, Object, Object, Th3Consumer<Object, Object, Object, ?>, ?> block = null;

    assertThatThrownBy(() ->
      recur3(value1, value2, value3, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void recur3Method() {
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
    final Th4Consumer<Object, Object, Object, Th3Consumer<Object, Object, Object, ?>, ?> block = (v1, v2, v3, self) -> {
      values.add(v1);
      values.add(v2);
      values.add(v3);
      switch (counter.getAndIncrement()) {
        case 0:
          self.accept(value4, value5, value6);
          break;
        case 1:
          self.accept(value7, value8, value9);
          break;
      }
    };

    recur3(value1, value2, value3, block);
    assertThat(counter.get()).isEqualTo(3);
    assertThat(values).containsExactly(value1, value2, value3, value4, value5, value6, value7, value8, value9);
  }

  @Test
  void recur3MethodWithDepthNullBlockArg() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final Th5Consumer<RecurDepth, Object, Object, Object, Th3Consumer<Object, Object, Object, ?>, ?> block = null;

    assertThatThrownBy(() ->
      recur3(value1, value2, value3, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void recur3MethodWithDepth() {
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
    final List<Integer> depths = new ArrayList<>();
    final List<Object> values = new ArrayList<>();
    final Th5Consumer<RecurDepth, Object, Object, Object, Th3Consumer<Object, Object, Object, ?>, ?> block =
      (depth, v1, v2, v3, self) -> {
        depths.add(depth.current());
        values.add(v1);
        values.add(v2);
        values.add(v3);
        switch (counter.getAndIncrement()) {
          case 0:
            self.accept(value4, value5, value6);
            break;
          case 1:
            self.accept(value7, value8, value9);
            break;
        }
        depths.add(depth.current());
      };

    recur3(value1, value2, value3, block);
    assertThat(counter.get()).isEqualTo(3);
    assertThat(depths).containsExactly(0, 1, 2, 2, 1, 0);
    assertThat(values).containsExactly(value1, value2, value3, value4, value5, value6, value7, value8, value9);
  }

  @Test
  void recur3MethodWithAccNullBlockArg() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final Object acc = new Object();
    final Th5Consumer<Object, Object, Object, Object, Th3Consumer<Object, Object, Object, ?>, ?> block = null;

    assertThatThrownBy(() ->
      recur3(value1, value2, value3, acc, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void recur3MethodWithAcc() {
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
    final Th5Consumer<Object, Object, Object, Object, Th3Consumer<Object, Object, Object, ?>, ?> block =
      (v1, v2, v3, a, self) -> {
        values.add(v1);
        values.add(v2);
        values.add(v3);
        accs.add(a);
        switch (counter.getAndIncrement()) {
          case 0:
            self.accept(value4, value5, value6);
            break;
          case 1:
            self.accept(value7, value8, value9);
            break;
        }
      };

    recur3(value1, value2, value3, acc, block);
    assertThat(counter.get()).isEqualTo(3);
    assertThat(accs).containsExactly(acc, acc, acc);
    assertThat(values).containsExactly(value1, value2, value3, value4, value5, value6, value7, value8, value9);
  }

  @Test
  void recur3MethodWithDepthAndAccNullBlockArg() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final Object acc = new Object();
    final Th6Consumer<RecurDepth, Object, Object, Object, Object, Th3Consumer<Object, Object, Object, ?>, ?> block =
      null;

    assertThatThrownBy(() ->
      recur3(value1, value2, value3, acc, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void recur3MethodWithDepthAndAcc() {
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
    final List<Integer> depths = new ArrayList<>();
    final List<Object> values = new ArrayList<>();
    final List<Object> accs = new ArrayList<>();
    final Th6Consumer<RecurDepth, Object, Object, Object, Object, Th3Consumer<Object, Object, Object, ?>, ?> block =
      (depth, v1, v2, v3, a, self) -> {
        depths.add(depth.current());
        values.add(v1);
        values.add(v2);
        values.add(v3);
        accs.add(a);
        switch (counter.getAndIncrement()) {
          case 0:
            self.accept(value4, value5, value6);
            break;
          case 1:
            self.accept(value7, value8, value9);
            break;
        }
        depths.add(depth.current());
      };

    recur3(value1, value2, value3, acc, block);
    assertThat(counter.get()).isEqualTo(3);
    assertThat(depths).containsExactly(0, 1, 2, 2, 1, 0);
    assertThat(values).containsExactly(value1, value2, value3, value4, value5, value6, value7, value8, value9);
    assertThat(accs).containsExactly(acc, acc, acc);
  }

  @Test
  void recurMethodFunctionNullBlockArg() {
    final ThFunction<ThSupplier<Object, ?>, Object, ?> block = null;

    assertThatThrownBy(() ->
      recur(block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void recurMethodFunction() {
    final AtomicInteger counter = new AtomicInteger();
    final Object result = new Object();
    final ThFunction<ThSupplier<Object, ?>, Object, ?> block = self -> {
      if (counter.getAndIncrement() < 2) {
        return self.get();
      }
      return result;
    };

    assertThat(
      recur(block)
    ).isSameAs(result);
    assertThat(counter.get()).isEqualTo(3);
  }

  @Test
  void recurMethodFunctionWithDepthNullBlockArg() {
    final Th2Function<RecurDepth, ThSupplier<Object, ?>, Object, ?> block = null;

    assertThatThrownBy(() ->
      recur(block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void recurMethodFunctionWithDepth() {
    final AtomicInteger counter = new AtomicInteger();
    final List<Integer> depths = new ArrayList<>();
    final Object result = new Object();
    final Th2Function<RecurDepth, ThSupplier<Object, ?>, Object, ?> block = (depth, self) -> {
      depths.add(depth.current());
      if (counter.getAndIncrement() < 2) {
        try {
          return self.get();
        } finally {
          depths.add(depth.current());
        }
      }
      try {
        return result;
      } finally {
        depths.add(depth.current());
      }
    };

    assertThat(
      recur(block)
    ).isSameAs(result);
    assertThat(counter.get()).isEqualTo(3);
    assertThat(depths).containsExactly(0, 1, 2, 2, 1, 0);
  }

  @Test
  void recur1MethodFunctionNullBlockArg() {
    final Object value = new Object();
    final Th2Function<Object, ? super ThFunction<Object, Object, ?>, Object, ?> block = null;

    assertThatThrownBy(() ->
      recur1(value, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void recur1MethodFunction() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final Object result = new Object();
    final AtomicInteger counter = new AtomicInteger();
    final List<Object> values = new ArrayList<>();
    final Th2Function<Object, ? super ThFunction<Object, Object, ?>, Object, ?> block = (v, self) -> {
      values.add(v);
      switch (counter.getAndIncrement()) {
        case 0:
          return self.apply(value2);
        case 1:
          return self.apply(value3);
      }
      return result;
    };

    assertThat(
      recur1(value1, block)
    ).isSameAs(result);
    assertThat(counter.get()).isEqualTo(3);
    assertThat(values).containsExactly(value1, value2, value3);
  }

  @Test
  void recur1MethodFunctionWithDepthNullBlockArg() {
    final Object value = new Object();
    final Th3Function<RecurDepth, Object, ThFunction<Object, Object, ?>, Object, ?> block = null;

    assertThatThrownBy(() ->
      recur1(value, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void recur1MethodFunctionWithDepth() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final Object result = new Object();
    final AtomicInteger counter = new AtomicInteger();
    final List<Integer> depths = new ArrayList<>();
    final List<Object> values = new ArrayList<>();
    final Th3Function<RecurDepth, Object, ThFunction<Object, Object, ?>, Object, ?> block =
      (depth, v, self) -> {
        depths.add(depth.current());
        values.add(v);
        try {
          switch (counter.getAndIncrement()) {
            case 0:
              return self.apply(value2);
            case 1:
              return self.apply(value3);
          }
          return result;
        } finally {
          depths.add(depth.current());
        }
      };

    assertThat(
      recur1(value1, block)
    ).isSameAs(result);
    assertThat(counter.get()).isEqualTo(3);
    assertThat(depths).containsExactly(0, 1, 2, 2, 1, 0);
    assertThat(values).containsExactly(value1, value2, value3);
  }

  @Test
  void recur2MethodFunctionNullBlockArg() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Th3Function<Object, Object, Th2Function<Object, Object, Object, ?>, Object, ?> block = null;

    assertThatThrownBy(() ->
      recur2(value1, value2, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void recur2MethodFunction() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final Object value4 = new Object();
    final Object value5 = new Object();
    final Object value6 = new Object();
    final Object result = new Object();
    final AtomicInteger counter = new AtomicInteger();
    final List<Object> values = new ArrayList<>();
    final Th3Function<Object, Object, Th2Function<Object, Object, Object, ?>, Object, ?> block = (v1, v2, self) -> {
      values.add(v1);
      values.add(v2);
      switch (counter.getAndIncrement()) {
        case 0:
          return self.apply(value3, value4);
        case 1:
          return self.apply(value5, value6);
      }
      return result;
    };

    assertThat(
      recur2(value1, value2, block)
    ).isSameAs(result);
    assertThat(counter.get()).isEqualTo(3);
    assertThat(values).containsExactly(value1, value2, value3, value4, value5, value6);
  }

  @Test
  void recur2MethodFunctionWithDepthNullBlockArg() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Th4Function<RecurDepth, Object, Object, Th2Function<Object, Object, Object, ?>, Object, ?> block = null;

    assertThatThrownBy(() ->
      recur2(value1, value2, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void recur2MethodFunctionWithDepth() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final Object value4 = new Object();
    final Object value5 = new Object();
    final Object value6 = new Object();
    final Object result = new Object();
    final AtomicInteger counter = new AtomicInteger();
    final List<Integer> depths = new ArrayList<>();
    final List<Object> values = new ArrayList<>();
    final Th4Function<RecurDepth, Object, Object, Th2Function<Object, Object, Object, ?>, Object, ?> block =
      (depth, v1, v2, self) -> {
        depths.add(depth.current());
        values.add(v1);
        values.add(v2);
        try {
          switch (counter.getAndIncrement()) {
            case 0:
              return self.apply(value3, value4);
            case 1:
              return self.apply(value5, value6);
          }
          return result;
        } finally {
          depths.add(depth.current());
        }
      };

    assertThat(
      recur2(value1, value2, block)
    ).isSameAs(result);
    assertThat(counter.get()).isEqualTo(3);
    assertThat(depths).containsExactly(0, 1, 2, 2, 1, 0);
    assertThat(values).containsExactly(value1, value2, value3, value4, value5, value6);
  }

  @Test
  void recur3MethodFunctionNullBlockArg() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final Th4Function<Object, Object, Object, Th3Function<Object, Object, Object, Object, ?>, Object, ?> block = null;

    assertThatThrownBy(() ->
      recur3(value1, value2, value3, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void recur3MethodFunction() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final Object value4 = new Object();
    final Object value5 = new Object();
    final Object value6 = new Object();
    final Object value7 = new Object();
    final Object value8 = new Object();
    final Object value9 = new Object();
    final Object result = new Object();
    final AtomicInteger counter = new AtomicInteger();
    final List<Object> values = new ArrayList<>();
    final Th4Function<Object, Object, Object, Th3Function<Object, Object, Object, Object, ?>, Object, ?> block =
      (v1, v2, v3, self) -> {
        values.add(v1);
        values.add(v2);
        values.add(v3);
        switch (counter.getAndIncrement()) {
          case 0:
            return self.apply(value4, value5, value6);
          case 1:
            return self.apply(value7, value8, value9);
        }
        return result;
      };

    assertThat(
      recur3(value1, value2, value3, block)
    ).isSameAs(result);
    assertThat(counter.get()).isEqualTo(3);
    assertThat(values).containsExactly(value1, value2, value3, value4, value5, value6, value7, value8, value9);
  }

  @Test
  void recur3MethodFunctionWithDepthNullBlockArg() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final Th5Function<RecurDepth, Object, Object, Object, Th3Function<Object, Object, Object, Object, ?>, Object, ?> block =
      null;

    assertThatThrownBy(() ->
      recur3(value1, value2, value3, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void recur3MethodFunctionWithDepth() {
    final Object value1 = new Object();
    final Object value2 = new Object();
    final Object value3 = new Object();
    final Object value4 = new Object();
    final Object value5 = new Object();
    final Object value6 = new Object();
    final Object value7 = new Object();
    final Object value8 = new Object();
    final Object value9 = new Object();
    final Object result = new Object();
    final AtomicInteger counter = new AtomicInteger();
    final List<Integer> depths = new ArrayList<>();
    final List<Object> values = new ArrayList<>();
    final Th5Function<RecurDepth, Object, Object, Object, Th3Function<Object, Object, Object, Object, ?>, Object, ?> block =
      (depth, v1, v2, v3, self) -> {
        depths.add(depth.current());
        values.add(v1);
        values.add(v2);
        values.add(v3);
        try {
          switch (counter.getAndIncrement()) {
            case 0:
              return self.apply(value4, value5, value6);
            case 1:
              return self.apply(value7, value8, value9);
          }
          return result;
        } finally {
          depths.add(depth.current());
        }
      };

    assertThat(
      recur3(value1, value2, value3, block)
    ).isSameAs(result);
    assertThat(counter.get()).isEqualTo(3);
    assertThat(depths).containsExactly(0, 1, 2, 2, 1, 0);
    assertThat(values).containsExactly(value1, value2, value3, value4, value5, value6, value7, value8, value9);
  }
}
