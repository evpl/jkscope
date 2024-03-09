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

import com.plugatar.jkscope.Lazy.ThreadSafetyMode;
import com.plugatar.jkscope.function.ThConsumer;
import com.plugatar.jkscope.function.ThFunction;
import com.plugatar.jkscope.function.ThPredicate;
import com.plugatar.jkscope.function.ThSupplier;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static com.plugatar.jkscope.SerializationUtils.deserialize;
import static com.plugatar.jkscope.SerializationUtils.serialize;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for {@link Lazy}.
 */
final class LazyTest {

  //region NPE check for instance methods

  @Test
  void alsoMethodThrowsNPEForNullArg() {
    final Lazy<Object> synchronizedLazy = Lazy.of(ThreadSafetyMode.SYNCHRONIZED, Object::new);
    final Lazy<Object> publicationLazy = Lazy.of(ThreadSafetyMode.PUBLICATION, Object::new);
    final Lazy<Object> noneLazy = Lazy.of(ThreadSafetyMode.NONE, Object::new);
    final Lazy<Object> initializedLazy = Lazy.ofValue(new Object());
    final ThConsumer<Object, Throwable> block = null;

    assertThatThrownBy(() -> synchronizedLazy.also(block))
      .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> publicationLazy.also(block))
      .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> noneLazy.also(block))
      .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> initializedLazy.also(block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void letItMethodThrowsNPEForNullArg() {
    final Lazy<Object> synchronizedLazy = Lazy.of(ThreadSafetyMode.SYNCHRONIZED, Object::new);
    final Lazy<Object> publicationLazy = Lazy.of(ThreadSafetyMode.PUBLICATION, Object::new);
    final Lazy<Object> noneLazy = Lazy.of(ThreadSafetyMode.NONE, Object::new);
    final Lazy<Object> initializedLazy = Lazy.ofValue(new Object());
    final ThConsumer<Object, Throwable> block = null;

    assertThatThrownBy(() -> synchronizedLazy.letIt(block))
      .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> publicationLazy.letIt(block))
      .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> noneLazy.letIt(block))
      .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> initializedLazy.letIt(block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void letOutMethodThrowsNPEForNullArg() {
    final Lazy<Object> synchronizedLazy = Lazy.of(ThreadSafetyMode.SYNCHRONIZED, Object::new);
    final Lazy<Object> publicationLazy = Lazy.of(ThreadSafetyMode.PUBLICATION, Object::new);
    final Lazy<Object> noneLazy = Lazy.of(ThreadSafetyMode.NONE, Object::new);
    final Lazy<Object> initializedLazy = Lazy.ofValue(new Object());
    final ThFunction<Object, Object, Throwable> block = null;

    assertThatThrownBy(() -> synchronizedLazy.letOut(block))
      .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> publicationLazy.letOut(block))
      .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> noneLazy.letOut(block))
      .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> initializedLazy.letOut(block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void letOptMethodThrowsNPEForNullArg() {
    final Lazy<Object> synchronizedLazy = Lazy.of(ThreadSafetyMode.SYNCHRONIZED, Object::new);
    final Lazy<Object> publicationLazy = Lazy.of(ThreadSafetyMode.PUBLICATION, Object::new);
    final Lazy<Object> noneLazy = Lazy.of(ThreadSafetyMode.NONE, Object::new);
    final Lazy<Object> initializedLazy = Lazy.ofValue(new Object());
    final ThFunction<Object, Object, Throwable> block = null;

    assertThatThrownBy(() -> synchronizedLazy.letOpt(block))
      .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> publicationLazy.letOpt(block))
      .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> noneLazy.letOpt(block))
      .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> initializedLazy.letOpt(block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void takeIfMethodThrowsNPEForNullArg() {
    final Lazy<Object> synchronizedLazy = Lazy.of(ThreadSafetyMode.SYNCHRONIZED, Object::new);
    final Lazy<Object> publicationLazy = Lazy.of(ThreadSafetyMode.PUBLICATION, Object::new);
    final Lazy<Object> noneLazy = Lazy.of(ThreadSafetyMode.NONE, Object::new);
    final Lazy<Object> initializedLazy = Lazy.ofValue(new Object());
    final ThPredicate<Object, Throwable> block = null;

    assertThatThrownBy(() -> synchronizedLazy.takeIf(block))
      .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> publicationLazy.takeIf(block))
      .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> noneLazy.takeIf(block))
      .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> initializedLazy.takeIf(block))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void takeUnlessMethodThrowsNPEForNullArg() {
    final Lazy<Object> synchronizedLazy = Lazy.of(ThreadSafetyMode.SYNCHRONIZED, Object::new);
    final Lazy<Object> publicationLazy = Lazy.of(ThreadSafetyMode.PUBLICATION, Object::new);
    final Lazy<Object> noneLazy = Lazy.of(ThreadSafetyMode.NONE, Object::new);
    final Lazy<Object> initializedLazy = Lazy.ofValue(new Object());
    final ThPredicate<Object, Throwable> block = null;

    assertThatThrownBy(() -> synchronizedLazy.takeUnless(block))
      .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> publicationLazy.takeUnless(block))
      .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> noneLazy.takeUnless(block))
      .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> initializedLazy.takeUnless(block))
      .isInstanceOf(NullPointerException.class);
  }

  //endregion

  //region Logic check for instance methods

  @Test
  void getAndIsInitializedMethods() {
    final Object value = new Object();
    final ThSupplier<Object, Throwable> initializer = () -> value;
    final Lazy<Object> synchronizedLazy = Lazy.of(ThreadSafetyMode.SYNCHRONIZED, initializer);
    final Lazy<Object> publicationLazy = Lazy.of(ThreadSafetyMode.PUBLICATION, initializer);
    final Lazy<Object> noneLazy = Lazy.of(ThreadSafetyMode.NONE, initializer);
    final Lazy<Object> initializedLazy = Lazy.ofValue(value);

    checkGetAndInitializedMethodsForNonInitialized(synchronizedLazy, value);
    checkGetAndInitializedMethodsForNonInitialized(publicationLazy, value);
    checkGetAndInitializedMethodsForNonInitialized(noneLazy, value);
    checkGetAndInitializedMethodsForInitialized(initializedLazy, value);
  }

  @Test
  void getAndIsInitializedMethodsThrowsException() {
    final Throwable throwable = new Throwable();
    final ThSupplier<Object, Throwable> initializer = () -> { throw throwable; };
    final Lazy<Object> synchronizedLazy = Lazy.of(ThreadSafetyMode.SYNCHRONIZED, initializer);
    final Lazy<Object> publicationLazy = Lazy.of(ThreadSafetyMode.PUBLICATION, initializer);
    final Lazy<Object> noneLazy = Lazy.of(ThreadSafetyMode.NONE, initializer);

    checkGetAndInitializedMethodsThrowsExceptionForNonInitialized(synchronizedLazy, throwable);
    checkGetAndInitializedMethodsThrowsExceptionForNonInitialized(publicationLazy, throwable);
    checkGetAndInitializedMethodsThrowsExceptionForNonInitialized(noneLazy, throwable);
  }

  private static void checkGetAndInitializedMethodsForNonInitialized(final Lazy<?> lazy,
                                                                     final Object value) {
    assertThat(lazy.isInitialized())
      .isFalse();
    assertThat(lazy.isInitialized())
      .isFalse();
    assertThat(lazy.get())
      .isSameAs(value);
    assertThat(lazy.isInitialized())
      .isTrue();
    assertThat(lazy.get())
      .isSameAs(value);
    assertThat(lazy.isInitialized())
      .isTrue();
  }

  private static void checkGetAndInitializedMethodsForInitialized(final Lazy<?> lazy,
                                                                  final Object value) {
    assertThat(lazy.isInitialized())
      .isTrue();
    assertThat(lazy.isInitialized())
      .isTrue();
    assertThat(lazy.get())
      .isSameAs(value);
    assertThat(lazy.isInitialized())
      .isTrue();
    assertThat(lazy.get())
      .isSameAs(value);
    assertThat(lazy.isInitialized())
      .isTrue();
  }

  private static void checkGetAndInitializedMethodsThrowsExceptionForNonInitialized(final Lazy<?> lazy,
                                                                                    final Object throwable) {
    assertThat(lazy.isInitialized())
      .isFalse();
    assertThat(lazy.isInitialized())
      .isFalse();
    assertThatThrownBy(() -> lazy.get())
      .isSameAs(throwable);
    assertThat(lazy.isInitialized())
      .isFalse();
    assertThatThrownBy(() -> lazy.get())
      .isSameAs(throwable);
    assertThat(lazy.isInitialized())
      .isFalse();
  }

  @Test
  void alsoMethod() {
    final Object value = new Object();
    final ThSupplier<Object, Throwable> initializer = () -> value;
    final Lazy<Object> synchronizedLazy = Lazy.of(ThreadSafetyMode.SYNCHRONIZED, initializer);
    final Lazy<Object> publicationLazy = Lazy.of(ThreadSafetyMode.PUBLICATION, initializer);
    final Lazy<Object> noneLazy = Lazy.of(ThreadSafetyMode.NONE, initializer);
    final Lazy<Object> initializedLazy = Lazy.ofValue(value);
    final AtomicReference<Object> valueRef = new AtomicReference<>();
    final ThConsumer<Object, Throwable> block = arg -> valueRef.set(arg);

    assertThat(synchronizedLazy.also(block))
      .isSameAs(synchronizedLazy);
    assertThat(valueRef.get())
      .isSameAs(value);

    valueRef.set(null);
    assertThat(publicationLazy.also(block))
      .isSameAs(publicationLazy);
    assertThat(valueRef.get())
      .isSameAs(value);

    valueRef.set(null);
    assertThat(noneLazy.also(block))
      .isSameAs(noneLazy);
    assertThat(valueRef.get())
      .isSameAs(value);

    valueRef.set(null);
    assertThat(initializedLazy.also(block))
      .isSameAs(initializedLazy);
    assertThat(valueRef.get())
      .isSameAs(value);
  }

  @Test
  void letItMethod() {
    final Object value = new Object();
    final ThSupplier<Object, Throwable> initializer = () -> value;
    final Lazy<Object> synchronizedLazy = Lazy.of(ThreadSafetyMode.SYNCHRONIZED, initializer);
    final Lazy<Object> publicationLazy = Lazy.of(ThreadSafetyMode.PUBLICATION, initializer);
    final Lazy<Object> noneLazy = Lazy.of(ThreadSafetyMode.NONE, initializer);
    final Lazy<Object> initializedLazy = Lazy.ofValue(value);
    final AtomicReference<Object> valueRef = new AtomicReference<>();
    final ThConsumer<Object, Throwable> block = arg -> valueRef.set(arg);

    assertThat(synchronizedLazy.letIt(block))
      .isSameAs(synchronizedLazy);
    assertThat(valueRef.get())
      .isSameAs(value);

    valueRef.set(null);
    assertThat(publicationLazy.letIt(block))
      .isSameAs(publicationLazy);
    assertThat(valueRef.get())
      .isSameAs(value);

    valueRef.set(null);
    assertThat(noneLazy.letIt(block))
      .isSameAs(noneLazy);
    assertThat(valueRef.get())
      .isSameAs(value);

    valueRef.set(null);
    assertThat(initializedLazy.letIt(block))
      .isSameAs(initializedLazy);
    assertThat(valueRef.get())
      .isSameAs(value);
  }

  @Test
  void letOutMethod() {
    final Object value = new Object();
    final ThSupplier<Object, Throwable> initializer = () -> value;
    final Lazy<Object> synchronizedLazy = Lazy.of(ThreadSafetyMode.SYNCHRONIZED, initializer);
    final Lazy<Object> publicationLazy = Lazy.of(ThreadSafetyMode.PUBLICATION, initializer);
    final Lazy<Object> noneLazy = Lazy.of(ThreadSafetyMode.NONE, initializer);
    final Lazy<Object> initializedLazy = Lazy.ofValue(value);
    final AtomicReference<Object> valueRef = new AtomicReference<>();
    final Object result = new Object();
    final ThFunction<Object, Object, Throwable> block = arg -> {
      valueRef.set(arg);
      return result;
    };

    assertThat(synchronizedLazy.letOut(block))
      .isSameAs(result);
    assertThat(valueRef.get())
      .isSameAs(value);

    valueRef.set(null);
    assertThat(publicationLazy.letOut(block))
      .isSameAs(result);
    assertThat(valueRef.get())
      .isSameAs(value);

    valueRef.set(null);
    assertThat(noneLazy.letOut(block))
      .isSameAs(result);
    assertThat(valueRef.get())
      .isSameAs(value);

    valueRef.set(null);
    assertThat(initializedLazy.letOut(block))
      .isSameAs(result);
    assertThat(valueRef.get())
      .isSameAs(value);
  }

  @Test
  void letOptMethod() {
    final Object value = new Object();
    final ThSupplier<Object, Throwable> initializer = () -> value;
    final Lazy<Object> synchronizedLazy = Lazy.of(ThreadSafetyMode.SYNCHRONIZED, initializer);
    final Lazy<Object> publicationLazy = Lazy.of(ThreadSafetyMode.PUBLICATION, initializer);
    final Lazy<Object> noneLazy = Lazy.of(ThreadSafetyMode.NONE, initializer);
    final Lazy<Object> initializedLazy = Lazy.ofValue(value);
    final AtomicReference<Object> valueRef = new AtomicReference<>();
    final Object result = new Object();
    final ThFunction<Object, Object, Throwable> block = arg -> {
      valueRef.set(arg);
      return result;
    };

    assertThat(synchronizedLazy.letOpt(block).get())
      .isSameAs(result);
    assertThat(valueRef.get())
      .isSameAs(value);

    valueRef.set(null);
    assertThat(publicationLazy.letOpt(block).get())
      .isSameAs(result);
    assertThat(valueRef.get())
      .isSameAs(value);

    valueRef.set(null);
    assertThat(noneLazy.letOpt(block).get())
      .isSameAs(result);
    assertThat(valueRef.get())
      .isSameAs(value);

    valueRef.set(null);
    assertThat(initializedLazy.letOpt(block).get())
      .isSameAs(result);
    assertThat(valueRef.get())
      .isSameAs(value);
  }

  @Test
  void takeIfMethod() {
    final Object value = new Object();
    final ThSupplier<Object, Throwable> initializer = () -> value;
    final Lazy<Object> synchronizedLazy = Lazy.of(ThreadSafetyMode.SYNCHRONIZED, initializer);
    final Lazy<Object> publicationLazy = Lazy.of(ThreadSafetyMode.PUBLICATION, initializer);
    final Lazy<Object> noneLazy = Lazy.of(ThreadSafetyMode.NONE, initializer);
    final Lazy<Object> initializedLazy = Lazy.ofValue(value);
    final AtomicReference<Object> valueRef = new AtomicReference<>();
    final ThPredicate<Object, Throwable> blockTrue = arg -> {
      valueRef.set(arg);
      return true;
    };
    final ThPredicate<Object, Throwable> blockFalse = arg -> false;

    assertThat(synchronizedLazy.takeIf(blockTrue).get())
      .isSameAs(value);
    assertThat(valueRef.get())
      .isSameAs(value);
    assertThat(synchronizedLazy.takeIf(blockFalse).isEmpty())
      .isTrue();

    valueRef.set(null);
    assertThat(publicationLazy.takeIf(blockTrue).get())
      .isSameAs(value);
    assertThat(valueRef.get())
      .isSameAs(value);
    assertThat(publicationLazy.takeIf(blockFalse).isEmpty())
      .isTrue();

    valueRef.set(null);
    assertThat(noneLazy.takeIf(blockTrue).get())
      .isSameAs(value);
    assertThat(valueRef.get())
      .isSameAs(value);
    assertThat(noneLazy.takeIf(blockFalse).isEmpty())
      .isTrue();

    valueRef.set(null);
    assertThat(initializedLazy.takeIf(blockTrue).get())
      .isSameAs(value);
    assertThat(valueRef.get())
      .isSameAs(value);
    assertThat(initializedLazy.takeIf(blockFalse).isEmpty())
      .isTrue();
  }

  @Test
  void takeUnlessMethod() {
    final Object value = new Object();
    final ThSupplier<Object, Throwable> initializer = () -> value;
    final Lazy<Object> synchronizedLazy = Lazy.of(ThreadSafetyMode.SYNCHRONIZED, initializer);
    final Lazy<Object> publicationLazy = Lazy.of(ThreadSafetyMode.PUBLICATION, initializer);
    final Lazy<Object> noneLazy = Lazy.of(ThreadSafetyMode.NONE, initializer);
    final Lazy<Object> initializedLazy = Lazy.ofValue(value);
    final AtomicReference<Object> valueRef = new AtomicReference<>();
    final ThPredicate<Object, Throwable> blockFalse = arg -> {
      valueRef.set(arg);
      return false;
    };
    final ThPredicate<Object, Throwable> blockTrue = arg -> true;

    assertThat(synchronizedLazy.takeUnless(blockFalse).get())
      .isSameAs(value);
    assertThat(valueRef.get())
      .isSameAs(value);
    assertThat(synchronizedLazy.takeUnless(blockTrue).isEmpty())
      .isTrue();

    valueRef.set(null);
    assertThat(publicationLazy.takeUnless(blockFalse).get())
      .isSameAs(value);
    assertThat(valueRef.get())
      .isSameAs(value);
    assertThat(publicationLazy.takeUnless(blockTrue).isEmpty())
      .isTrue();

    valueRef.set(null);
    assertThat(noneLazy.takeUnless(blockFalse).get())
      .isSameAs(value);
    assertThat(valueRef.get())
      .isSameAs(value);
    assertThat(noneLazy.takeUnless(blockTrue).isEmpty())
      .isTrue();

    valueRef.set(null);
    assertThat(initializedLazy.takeUnless(blockFalse).get())
      .isSameAs(value);
    assertThat(valueRef.get())
      .isSameAs(value);
    assertThat(initializedLazy.takeUnless(blockTrue).isEmpty())
      .isTrue();
  }

  @Test
  void toStringMethod() {
    final Object value = "some value";
    final ThSupplier<Object, Throwable> initializer = () -> value;
    final Lazy<Object> synchronizedLazy = Lazy.of(ThreadSafetyMode.SYNCHRONIZED, initializer);
    final Lazy<Object> publicationLazy = Lazy.of(ThreadSafetyMode.PUBLICATION, initializer);
    final Lazy<Object> noneLazy = Lazy.of(ThreadSafetyMode.NONE, initializer);
    final Lazy<Object> initializedLazy = Lazy.ofValue(value);

    assertThat(synchronizedLazy)
      .hasToString("Lazy value not initialized yet");
    assertThat(publicationLazy)
      .hasToString("Lazy value not initialized yet");
    assertThat(noneLazy)
      .hasToString("Lazy value not initialized yet");
    assertThat(initializedLazy)
      .hasToString("some value");

    synchronizedLazy.get();
    assertThat(synchronizedLazy)
      .hasToString("some value");

    publicationLazy.get();
    assertThat(publicationLazy)
      .hasToString("some value");

    noneLazy.get();
    assertThat(noneLazy)
      .hasToString("some value");
  }

  @Test
  void serializationAndDeserialization() throws Exception {
    final Object value = "some value";
    final ThSupplier<Object, Throwable> initializer = () -> value;
    final Lazy<Object> synchronizedLazy = Lazy.of(ThreadSafetyMode.SYNCHRONIZED, initializer);
    final Lazy<Object> publicationLazy = Lazy.of(ThreadSafetyMode.PUBLICATION, initializer);
    final Lazy<Object> noneLazy = Lazy.of(ThreadSafetyMode.NONE, initializer);
    final Lazy<Object> initializedLazy = Lazy.ofValue(value);

    assertThat(deserialize(serialize(synchronizedLazy)))
      .isInstanceOf(Lazy.Initialized.class)
      .extracting(obj -> ((Lazy.Initialized<?>) obj).get())
      .isEqualTo(value);

    assertThat(deserialize(serialize(publicationLazy)))
      .isInstanceOf(Lazy.Initialized.class)
      .extracting(obj -> ((Lazy.Initialized<?>) obj).get())
      .isEqualTo(value);

    assertThat(deserialize(serialize(noneLazy)))
      .isInstanceOf(Lazy.Initialized.class)
      .extracting(obj -> ((Lazy.Initialized<?>) obj).get())
      .isEqualTo(value);

    assertThat(deserialize(serialize(initializedLazy)))
      .isInstanceOf(Lazy.Initialized.class)
      .extracting(obj -> ((Lazy.Initialized<?>) obj).get())
      .isEqualTo(value);
  }

  //endregion

  //region NPE check for static methods

  @Test
  void ofInitializerStaticMethodThrowsNPEForNullArg() {
    final ThSupplier<Object, Throwable> nullInitializer = null;

    assertThatThrownBy(() -> Lazy.of(nullInitializer))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void ofLockInitializerStaticMethodThrowsNPEForNullArg() {
    final Object lock = new Object();
    final Object nullLock = null;
    final ThSupplier<Object, Throwable> initializer = () -> new Object();
    final ThSupplier<Object, Throwable> nullInitializer = null;

    assertThatThrownBy(() -> Lazy.of(nullLock, initializer))
      .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> Lazy.of(lock, nullInitializer))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void ofThreadSafetyModeAndInitializerStaticMethodThrowsNPEForNullArg() {
    final ThreadSafetyMode threadSafetyMode = ThreadSafetyMode.SYNCHRONIZED;
    final ThreadSafetyMode nullThreadSafetyMode = null;
    final ThSupplier<Object, Throwable> initializer = () -> new Object();
    final ThSupplier<Object, Throwable> nullInitializer = null;

    assertThatThrownBy(() -> Lazy.of(nullThreadSafetyMode, initializer))
      .isInstanceOf(NullPointerException.class);
    assertThatThrownBy(() -> Lazy.of(threadSafetyMode, nullInitializer))
      .isInstanceOf(NullPointerException.class);
  }

  //endregion

  //region Logic check for static methods

  @Test
  void ofInitializerStaticMethod() {
    final ThSupplier<Object, Throwable> initializer = () -> new Object();

    assertThat(Lazy.of(initializer))
      .isInstanceOf(Lazy.Synchronized.class);
  }

  @Test
  void ofLockAndInitializerStaticMethod() {
    final Object lock = new Object();
    final ThSupplier<Object, Throwable> initializer = () -> new Object();

    assertThat(Lazy.of(lock, initializer))
      .isInstanceOf(Lazy.Synchronized.class);
  }

  @Test
  void ofThreadSafetyModeAndInitializerStaticMethod() {
    final ThSupplier<Object, Throwable> initializer = () -> new Object();

    assertThat(Lazy.of(ThreadSafetyMode.SYNCHRONIZED, initializer))
      .isInstanceOf(Lazy.Synchronized.class);
    assertThat(Lazy.of(ThreadSafetyMode.PUBLICATION, initializer))
      .isInstanceOf(Lazy.SafePublication.class);
    assertThat(Lazy.of(ThreadSafetyMode.NONE, initializer))
      .isInstanceOf(Lazy.Unsafe.class);
  }

  @Test
  void ofValueStaticMethod() {
    final Object value = new Object();

    assertThat(Lazy.ofValue(value))
      .isInstanceOf(Lazy.Initialized.class);
  }

  //endregion
}
