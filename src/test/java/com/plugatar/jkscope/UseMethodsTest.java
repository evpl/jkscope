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

import com.plugatar.jkscope.JKScope.ResourceDeque;
import com.plugatar.jkscope.function.Th2Consumer;
import com.plugatar.jkscope.function.Th2Function;
import com.plugatar.jkscope.function.Th3Consumer;
import com.plugatar.jkscope.function.Th3Function;
import com.plugatar.jkscope.function.Th4Consumer;
import com.plugatar.jkscope.function.Th4Function;
import com.plugatar.jkscope.function.ThConsumer;
import com.plugatar.jkscope.function.ThFunction;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;

import static com.plugatar.jkscope.JKScope.use;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests for methods:
 * <ul>
 * <li>{@link JKScope#use(ThConsumer)}</li>
 * <li>{@link JKScope#use(AutoCloseable, ThConsumer)}</li>
 * <li>{@link JKScope#use(AutoCloseable, Th2Consumer)}</li>
 * <li>{@link JKScope#use(AutoCloseable, AutoCloseable, Th2Consumer)}</li>
 * <li>{@link JKScope#use(AutoCloseable, AutoCloseable, Th3Consumer)}</li>
 * <li>{@link JKScope#use(AutoCloseable, AutoCloseable, AutoCloseable, Th3Consumer)}</li>
 * <li>{@link JKScope#use(AutoCloseable, AutoCloseable, AutoCloseable, Th4Consumer)}</li>
 * <li>{@link JKScope#use(ThFunction)}</li>
 * <li>{@link JKScope#use(AutoCloseable, ThFunction)}</li>
 * <li>{@link JKScope#use(AutoCloseable, Th2Function)}</li>
 * <li>{@link JKScope#use(AutoCloseable, AutoCloseable, Th2Function)}</li>
 * <li>{@link JKScope#use(AutoCloseable, AutoCloseable, Th3Function)}</li>
 * <li>{@link JKScope#use(AutoCloseable, AutoCloseable, AutoCloseable, Th3Function)}</li>
 * <li>{@link JKScope#use(AutoCloseable, AutoCloseable, AutoCloseable, Th4Function)}</li>
 * </ul>
 */
@SuppressWarnings("unchecked")
final class UseMethodsTest {

  @Test
  void useMethodConsumer0ValAndDequeThrowsNPEForNullBlockArg() {
    final ThConsumer<ResourceDeque, Error> block = null;

    assertThatThrownBy(() ->
      use(block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void useMethodConsumer0ValAndDeque() throws Exception {
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    final ThConsumer<ResourceDeque, Error> block = resources -> {
      resources.push(resource1);
      resources.push(resource2);
    };

    use(block);
    final InOrder inOrder = inOrder(resource2, resource1);
    inOrder.verify(resource2, times(1)).close();
    inOrder.verify(resource1, times(1)).close();
  }

  @Test
  void useMethodConsumer0ValAndDequeBlockException() throws Exception {
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    final RuntimeException blockException = new RuntimeException("block");
    final ThConsumer<ResourceDeque, Error> block = resources -> {
      resources.push(resource1);
      resources.push(resource2);
      throw blockException;
    };

    assertThatCode(() ->
      use(block)
    ).isSameAs(blockException);
    final InOrder inOrder = inOrder(resource2, resource1);
    inOrder.verify(resource2, times(1)).close();
    inOrder.verify(resource1, times(1)).close();
  }

  @Test
  void useMethodConsumer0ValAndDequeCloseException() throws Exception {
    final RuntimeException closeException1 = new RuntimeException("resource1");
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    doThrow(closeException1).when(resource1).close();
    final RuntimeException closeException2 = new RuntimeException("resource2");
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    doThrow(closeException2).when(resource2).close();
    final RuntimeException blockException = new RuntimeException("block");
    final ThConsumer<ResourceDeque, Error> block = resources -> {
      resources.push(resource1);
      resources.push(resource2);
      throw blockException;
    };

    assertThatCode(() ->
      use(block)
    ).isSameAs(blockException)
      .hasSuppressedException(closeException2)
      .hasSuppressedException(closeException1);
    final InOrder inOrder = inOrder(resource2, resource1);
    inOrder.verify(resource2, times(1)).close();
    inOrder.verify(resource1, times(1)).close();
  }

  @Test
  void useMethodConsumer0ValAndDequeBlockExceptionAndCloseException() throws Exception {
    final RuntimeException closeException1 = new RuntimeException("resource1");
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    doThrow(closeException1).when(resource1).close();
    final RuntimeException closeException2 = new RuntimeException("resource2");
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    doThrow(closeException2).when(resource2).close();
    final RuntimeException blockException = new RuntimeException("block");
    final ThConsumer<ResourceDeque, Error> block = resources -> {
      resources.push(resource1);
      resources.push(resource2);
      throw blockException;
    };

    assertThatCode(() ->
      use(block)
    ).isSameAs(blockException)
      .hasSuppressedException(closeException1)
      .hasSuppressedException(closeException2);
    final InOrder inOrder = inOrder(resource2, resource1);
    inOrder.verify(resource2, times(1)).close();
    inOrder.verify(resource1, times(1)).close();
  }

  @Test
  void useMethodConsumer1ValThrowsNPEForNullBlockArg() {
    final AutoCloseable resource = mock(AutoCloseable.class);
    final ThConsumer<AutoCloseable, Error> block = null;

    assertThatThrownBy(() ->
      use(resource, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void useMethodConsumer1ValThrowsNPEForNullResourceArg() {
    final AutoCloseable resource = null;
    final ThConsumer<AutoCloseable, Error> block = mock(ThConsumer.class);

    assertThatThrownBy(() ->
      use(resource, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void useMethodConsumer1Val() throws Exception {
    final AutoCloseable resource = mock(AutoCloseable.class);
    final ArgumentCaptor<AutoCloseable> resourceCaptor = ArgumentCaptor.forClass(AutoCloseable.class);
    final ThConsumer<AutoCloseable, Error> block = mock(ThConsumer.class);

    use(resource, block);
    verify(block, times(1)).accept(resourceCaptor.capture());
    assertThat(resourceCaptor.getValue()).isSameAs(resource);
    verify(resource, times(1)).close();
  }

  @Test
  void useMethodConsumer1ValBlockException() throws Exception {
    final AutoCloseable resource = mock(AutoCloseable.class);
    final RuntimeException blockException = new RuntimeException("block");
    final ThConsumer<AutoCloseable, Error> block = mock(ThConsumer.class);
    doThrow(blockException).when(block).accept(any());

    assertThatCode(() ->
      use(resource, block)
    ).isSameAs(blockException);
    verify(block, times(1)).accept(any());
    verify(resource, times(1)).close();
  }

  @Test
  void useMethodConsumer1ValCloseException() throws Exception {
    final RuntimeException closeException = new RuntimeException("resource");
    final AutoCloseable resource = mock(AutoCloseable.class);
    doThrow(closeException).when(resource).close();
    final ThConsumer<AutoCloseable, Error> block = mock(ThConsumer.class);

    assertThatCode(() ->
      use(resource, block)
    ).isSameAs(closeException);
    verify(block, times(1)).accept(any());
    verify(resource, times(1)).close();
  }

  @Test
  void useMethodConsumer1ValBlockAndCloseException() throws Exception {
    final RuntimeException closeException = new RuntimeException("resource");
    final AutoCloseable resource = mock(AutoCloseable.class);
    doThrow(closeException).when(resource).close();
    final RuntimeException blockException = new RuntimeException("block");
    final ThConsumer<AutoCloseable, Error> block = mock(ThConsumer.class);
    doThrow(blockException).when(block).accept(any());

    assertThatCode(() ->
      use(resource, block)
    ).isSameAs(blockException)
      .hasSuppressedException(closeException);
    verify(block, times(1)).accept(any());
    verify(resource, times(1)).close();
  }

  @Test
  void useMethodConsumer1ValAndDequeThrowsNPEForNullBlockArg() {
    final AutoCloseable resource = mock(AutoCloseable.class);
    final Th2Consumer<AutoCloseable, ResourceDeque, Error> block = null;

    assertThatThrownBy(() ->
      use(resource, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void useMethodConsumer1ValAndDequeThrowsNPEForNullResourceArg() {
    final AutoCloseable resource = null;
    final Th2Consumer<AutoCloseable, ResourceDeque, Error> block = null;

    assertThatThrownBy(() ->
      use(resource, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void useMethodConsumer1ValAndDeque() throws Exception {
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    final Th2Consumer<AutoCloseable, ResourceDeque, Error> block = (res1, resources) -> {
      resources.push(resource2);
    };

    use(resource1, block);
    final InOrder inOrder = inOrder(resource2, resource1);
    inOrder.verify(resource2, times(1)).close();
    inOrder.verify(resource1, times(1)).close();
  }

  @Test
  void useMethodConsumer1ValAndDequeBlockException() throws Exception {
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    final RuntimeException blockException = new RuntimeException("block");
    final Th2Consumer<AutoCloseable, ResourceDeque, Error> block = (res1, resources) -> {
      resources.push(resource2);
      throw blockException;
    };

    assertThatCode(() ->
      use(resource1, block)
    ).isSameAs(blockException);
    final InOrder inOrder = inOrder(resource2, resource1);
    inOrder.verify(resource2, times(1)).close();
    inOrder.verify(resource1, times(1)).close();
  }

  @Test
  void useMethodConsumer1ValAndDequeCloseException() throws Exception {
    final RuntimeException closeException1 = new RuntimeException("resource1");
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    doThrow(closeException1).when(resource1).close();
    final RuntimeException closeException2 = new RuntimeException("resource2");
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    doThrow(closeException2).when(resource2).close();
    final Th2Consumer<AutoCloseable, ResourceDeque, Error> block = (res1, resources) -> {
      resources.push(resource2);
    };

    assertThatCode(() ->
      use(resource1, block)
    ).isSameAs(closeException2)
      .hasSuppressedException(closeException1);
    final InOrder inOrder = inOrder(resource2, resource1);
    inOrder.verify(resource2, times(1)).close();
    inOrder.verify(resource1, times(1)).close();
  }

  @Test
  void useMethodConsumer1ValAndDequeBlockAndCloseException() throws Exception {
    final RuntimeException closeException1 = new RuntimeException("resource1");
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    doThrow(closeException1).when(resource1).close();
    final RuntimeException closeException2 = new RuntimeException("resource2");
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    doThrow(closeException2).when(resource2).close();
    final RuntimeException blockException = new RuntimeException("block");
    final Th2Consumer<AutoCloseable, ResourceDeque, Error> block = (res1, resources) -> {
      resources.push(resource2);
      throw blockException;
    };

    assertThatCode(() ->
      use(resource1, block)
    ).isSameAs(blockException)
      .hasSuppressedException(closeException1)
      .hasSuppressedException(closeException1);
    final InOrder inOrder = inOrder(resource2, resource1);
    inOrder.verify(resource2, times(1)).close();
    inOrder.verify(resource1, times(1)).close();
  }

  @Test
  void useMethodConsumer2ValThrowsNPEForNullBlockArg() {
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    final Th2Consumer<AutoCloseable, AutoCloseable, Error> block = null;

    assertThatThrownBy(() ->
      use(resource1, resource2, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void useMethodConsumer2ValThrowsNPEForNullResource1Arg() {
    final AutoCloseable resource1 = null;
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    final Th2Consumer<AutoCloseable, AutoCloseable, Error> block = mock(Th2Consumer.class);

    assertThatThrownBy(() ->
      use(resource1, resource2, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void useMethodConsumer2ValThrowsNPEForNullResource2Arg() {
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    final AutoCloseable resource2 = null;
    final Th2Consumer<AutoCloseable, AutoCloseable, Error> block = mock(Th2Consumer.class);

    assertThatThrownBy(() ->
      use(resource1, resource2, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void useMethodConsumer2Val() throws Exception {
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    final ArgumentCaptor<AutoCloseable> resourceCaptor1 = ArgumentCaptor.forClass(AutoCloseable.class);
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    final ArgumentCaptor<AutoCloseable> resourceCaptor2 = ArgumentCaptor.forClass(AutoCloseable.class);
    final Th2Consumer<AutoCloseable, AutoCloseable, Error> block = mock(Th2Consumer.class);

    use(resource1, resource2, block);
    verify(block, times(1)).accept(resourceCaptor1.capture(), resourceCaptor2.capture());
    assertThat(resourceCaptor1.getValue()).isSameAs(resource1);
    assertThat(resourceCaptor2.getValue()).isSameAs(resource2);
    final InOrder inOrder = inOrder(resource2, resource1);
    inOrder.verify(resource2, times(1)).close();
    inOrder.verify(resource1, times(1)).close();
  }

  @Test
  void useMethodConsumer2ValBlockException() throws Exception {
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    final RuntimeException blockException = new RuntimeException("block");
    final Th2Consumer<AutoCloseable, AutoCloseable, Error> block = mock(Th2Consumer.class);
    doThrow(blockException).when(block).accept(any(), any());

    assertThatCode(() ->
      use(resource1, resource2, block)
    ).isSameAs(blockException);
    verify(block, times(1)).accept(any(), any());
    verify(resource1, times(1)).close();
    verify(resource2, times(1)).close();
  }

  @Test
  void useMethodConsumer2ValCloseException() throws Exception {
    final RuntimeException closeException1 = new RuntimeException("resource1");
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    doThrow(closeException1).when(resource1).close();
    final RuntimeException closeException2 = new RuntimeException("resource2");
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    doThrow(closeException2).when(resource2).close();
    final Th2Consumer<AutoCloseable, AutoCloseable, Error> block = mock(Th2Consumer.class);

    assertThatCode(() ->
      use(resource1, resource2, block)
    ).isSameAs(closeException2)
      .hasSuppressedException(closeException1);
    verify(block, times(1)).accept(any(), any());
    verify(resource1, times(1)).close();
    verify(resource2, times(1)).close();
  }

  @Test
  void useMethodConsumer2ValBlockExceptionAndCloseException() throws Exception {
    final RuntimeException closeException1 = new RuntimeException("resource1");
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    doThrow(closeException1).when(resource1).close();
    final RuntimeException closeException2 = new RuntimeException("resource2");
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    doThrow(closeException2).when(resource2).close();
    final RuntimeException blockException = new RuntimeException("block");
    final Th2Consumer<AutoCloseable, AutoCloseable, Error> block = mock(Th2Consumer.class);
    doThrow(blockException).when(block).accept(any(), any());

    assertThatCode(() ->
      use(resource1, resource2, block)
    ).isSameAs(blockException)
      .hasSuppressedException(closeException1)
      .hasSuppressedException(closeException2);
    verify(block, times(1)).accept(any(), any());
    verify(resource1, times(1)).close();
    verify(resource2, times(1)).close();
  }

  @Test
  void useMethodConsumer2ValDequeThrowsNPEForNullBlockArg() {
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    final Th3Consumer<AutoCloseable, AutoCloseable, ResourceDeque, Error> block = null;

    assertThatThrownBy(() ->
      use(resource1, resource2, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void useMethodConsumer2ValDequeThrowsNPEForNullResource1Arg() {
    final AutoCloseable resource1 = null;
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    final Th3Consumer<AutoCloseable, AutoCloseable, ResourceDeque, Error> block = mock(Th3Consumer.class);

    assertThatThrownBy(() ->
      use(resource1, resource2, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void useMethodConsumer2ValDequeThrowsNPEForNullResource2Arg() {
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    final AutoCloseable resource2 = null;
    final Th3Consumer<AutoCloseable, AutoCloseable, ResourceDeque, Error> block = mock(Th3Consumer.class);

    assertThatThrownBy(() ->
      use(resource1, resource2, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void useMethodConsumer2ValDeque() throws Exception {
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    final AutoCloseable resource3 = mock(AutoCloseable.class);
    final Th3Consumer<AutoCloseable, AutoCloseable, ResourceDeque, Error> block = (res1, res2, resources) -> {
      resources.push(resource3);
    };

    use(resource1, resource2, block);
    final InOrder inOrder = inOrder(resource3, resource2, resource1);
    inOrder.verify(resource3, times(1)).close();
    inOrder.verify(resource2, times(1)).close();
    inOrder.verify(resource1, times(1)).close();
  }

  @Test
  void useMethodConsumer2ValDequeBlockException() throws Exception {
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    final AutoCloseable resource3 = mock(AutoCloseable.class);
    final RuntimeException blockException = new RuntimeException("block");
    final Th3Consumer<AutoCloseable, AutoCloseable, ResourceDeque, Error> block = (res1, res2, resources) -> {
      resources.push(resource3);
      throw blockException;
    };

    assertThatCode(() ->
      use(resource1, resource2, block)
    ).isSameAs(blockException);
    verify(resource1, times(1)).close();
    verify(resource2, times(1)).close();
    verify(resource3, times(1)).close();
  }

  @Test
  void useMethodConsumer2ValDequeCloseException() throws Exception {
    final RuntimeException closeException1 = new RuntimeException("resource1");
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    doThrow(closeException1).when(resource1).close();
    final RuntimeException closeException2 = new RuntimeException("resource2");
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    doThrow(closeException2).when(resource2).close();
    final RuntimeException closeException3 = new RuntimeException("resource3");
    final AutoCloseable resource3 = mock(AutoCloseable.class);
    doThrow(closeException3).when(resource3).close();
    final Th3Consumer<AutoCloseable, AutoCloseable, ResourceDeque, Error> block = (res1, res2, resources) -> {
      resources.push(resource3);
    };

    assertThatCode(() ->
      use(resource1, resource2, block)
    ).isSameAs(closeException3)
      .hasSuppressedException(closeException2)
      .hasSuppressedException(closeException1);
    verify(resource1, times(1)).close();
    verify(resource2, times(1)).close();
    verify(resource3, times(1)).close();
  }

  @Test
  void useMethodConsumer2ValDequeBlockExceptionAndCloseException() throws Exception {
    final RuntimeException closeException1 = new RuntimeException("resource1");
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    doThrow(closeException1).when(resource1).close();
    final RuntimeException closeException2 = new RuntimeException("resource2");
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    doThrow(closeException2).when(resource2).close();
    final RuntimeException closeException3 = new RuntimeException("resource3");
    final AutoCloseable resource3 = mock(AutoCloseable.class);
    doThrow(closeException3).when(resource3).close();
    final RuntimeException blockException = new RuntimeException("block");
    final Th3Consumer<AutoCloseable, AutoCloseable, ResourceDeque, Error> block = (res1, res2, resources) -> {
      resources.push(resource3);
      throw blockException;
    };

    assertThatCode(() ->
      use(resource1, resource2, block)
    ).isSameAs(blockException)
      .hasSuppressedException(closeException1)
      .hasSuppressedException(closeException2)
      .hasSuppressedException(closeException3);
    verify(resource1, times(1)).close();
    verify(resource2, times(1)).close();
    verify(resource3, times(1)).close();
  }

  @Test
  void useMethodConsumer3ValThrowsNPEForNullBlockArg() {
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    final AutoCloseable resource3 = mock(AutoCloseable.class);
    final Th3Consumer<AutoCloseable, AutoCloseable, AutoCloseable, Error> block = null;

    assertThatThrownBy(() ->
      use(resource1, resource2, resource3, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void useMethodConsumer3ValThrowsNPEForNullResource1Arg() {
    final AutoCloseable resource1 = null;
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    final AutoCloseable resource3 = mock(AutoCloseable.class);
    final Th3Consumer<AutoCloseable, AutoCloseable, AutoCloseable, Error> block = mock(Th3Consumer.class);

    assertThatThrownBy(() ->
      use(resource1, resource2, resource3, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void useMethodConsumer3ValThrowsNPEForNullResource2Arg() {
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    final AutoCloseable resource2 = null;
    final AutoCloseable resource3 = mock(AutoCloseable.class);
    final Th3Consumer<AutoCloseable, AutoCloseable, AutoCloseable, Error> block = mock(Th3Consumer.class);

    assertThatThrownBy(() ->
      use(resource1, resource2, resource3, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void useMethodConsumer3ValThrowsNPEForNullResource3Arg() {
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    final AutoCloseable resource3 = null;
    final Th3Consumer<AutoCloseable, AutoCloseable, AutoCloseable, Error> block = mock(Th3Consumer.class);

    assertThatThrownBy(() ->
      use(resource1, resource2, resource3, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void useMethodConsumer3Val() throws Exception {
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    final ArgumentCaptor<AutoCloseable> resourceCaptor1 = ArgumentCaptor.forClass(AutoCloseable.class);
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    final ArgumentCaptor<AutoCloseable> resourceCaptor2 = ArgumentCaptor.forClass(AutoCloseable.class);
    final AutoCloseable resource3 = mock(AutoCloseable.class);
    final ArgumentCaptor<AutoCloseable> resourceCaptor3 = ArgumentCaptor.forClass(AutoCloseable.class);
    final Th3Consumer<AutoCloseable, AutoCloseable, AutoCloseable, Error> block = mock(Th3Consumer.class);

    use(resource1, resource2, resource3, block);
    verify(block, times(1)).accept(resourceCaptor1.capture(), resourceCaptor2.capture(), resourceCaptor3.capture());
    assertThat(resourceCaptor1.getValue()).isSameAs(resource1);
    assertThat(resourceCaptor2.getValue()).isSameAs(resource2);
    assertThat(resourceCaptor3.getValue()).isSameAs(resource3);
    final InOrder inOrder = inOrder(resource3, resource2, resource1);
    inOrder.verify(resource3, times(1)).close();
    inOrder.verify(resource2, times(1)).close();
    inOrder.verify(resource1, times(1)).close();
  }

  @Test
  void useMethodConsumer3ValBlockException() throws Exception {
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    final AutoCloseable resource3 = mock(AutoCloseable.class);
    final RuntimeException blockException = new RuntimeException("block");
    final Th3Consumer<AutoCloseable, AutoCloseable, AutoCloseable, Error> block = mock(Th3Consumer.class);
    doThrow(blockException).when(block).accept(any(), any(), any());

    assertThatCode(() ->
      use(resource1, resource2, resource3, block)
    ).isSameAs(blockException);
    verify(block, times(1)).accept(any(), any(), any());
    verify(resource1, times(1)).close();
    verify(resource2, times(1)).close();
    verify(resource3, times(1)).close();
  }


  @Test
  void useMethodConsumer3ValCloseException() throws Exception {
    final RuntimeException closeException1 = new RuntimeException("resource1");
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    doThrow(closeException1).when(resource1).close();
    final RuntimeException closeException2 = new RuntimeException("resource2");
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    doThrow(closeException2).when(resource2).close();
    final RuntimeException closeException3 = new RuntimeException("resource3");
    final AutoCloseable resource3 = mock(AutoCloseable.class);
    doThrow(closeException3).when(resource3).close();
    final Th3Consumer<AutoCloseable, AutoCloseable, AutoCloseable, Error> block = mock(Th3Consumer.class);

    assertThatCode(() ->
      use(resource1, resource2, resource3, block)
    ).isSameAs(closeException3)
      .hasSuppressedException(closeException1)
      .hasSuppressedException(closeException2);
    verify(block, times(1)).accept(any(), any(), any());
    verify(resource1, times(1)).close();
    verify(resource2, times(1)).close();
    verify(resource3, times(1)).close();
  }

  @Test
  void useMethodConsumer3ValBlockExceptionAndCloseException() throws Exception {
    final RuntimeException closeException1 = new RuntimeException("resource1");
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    doThrow(closeException1).when(resource1).close();
    final RuntimeException closeException2 = new RuntimeException("resource2");
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    doThrow(closeException2).when(resource2).close();
    final RuntimeException closeException3 = new RuntimeException("resource3");
    final AutoCloseable resource3 = mock(AutoCloseable.class);
    doThrow(closeException3).when(resource3).close();
    final RuntimeException blockException = new RuntimeException("block");
    final Th3Consumer<AutoCloseable, AutoCloseable, AutoCloseable, Error> block = mock(Th3Consumer.class);
    doThrow(blockException).when(block).accept(any(), any(), any());

    assertThatCode(() ->
      use(resource1, resource2, resource3, block)
    ).isSameAs(blockException)
      .hasSuppressedException(closeException1)
      .hasSuppressedException(closeException2);
    verify(block, times(1)).accept(any(), any(), any());
    verify(resource1, times(1)).close();
    verify(resource2, times(1)).close();
  }


  @Test
  void useMethodConsumer3ValDequeThrowsNPEForNullBlockArg() {
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    final AutoCloseable resource3 = mock(AutoCloseable.class);
    final Th4Consumer<AutoCloseable, AutoCloseable, AutoCloseable, ResourceDeque, Error> block = null;

    assertThatThrownBy(() ->
      use(resource1, resource2, resource3, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void useMethodConsumer3ValDequeThrowsNPEForNullResource1Arg() {
    final AutoCloseable resource1 = null;
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    final AutoCloseable resource3 = mock(AutoCloseable.class);
    final Th4Consumer<AutoCloseable, AutoCloseable, AutoCloseable, ResourceDeque, Error> block =
      mock(Th4Consumer.class);

    assertThatThrownBy(() ->
      use(resource1, resource2, resource3, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void useMethodConsumer3ValDequeThrowsNPEForNullResource2Arg() {
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    final AutoCloseable resource2 = null;
    final AutoCloseable resource3 = mock(AutoCloseable.class);
    final Th4Consumer<AutoCloseable, AutoCloseable, AutoCloseable, ResourceDeque, Error> block =
      mock(Th4Consumer.class);

    assertThatThrownBy(() ->
      use(resource1, resource2, resource3, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void useMethodConsumer3ValDequeThrowsNPEForNullResource3Arg() {
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    final AutoCloseable resource3 = null;
    final Th4Consumer<AutoCloseable, AutoCloseable, AutoCloseable, ResourceDeque, Error> block =
      mock(Th4Consumer.class);

    assertThatThrownBy(() ->
      use(resource1, resource2, resource3, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void useMethodConsumer3ValDeque() throws Exception {
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    final AutoCloseable resource3 = mock(AutoCloseable.class);
    final AutoCloseable resource4 = mock(AutoCloseable.class);
    final Th4Consumer<AutoCloseable, AutoCloseable, AutoCloseable, ResourceDeque, Error> block =
      (res1, res2, res3, resources) -> {
        resources.push(resource4);
      };

    use(resource1, resource2, resource3, block);
    final InOrder inOrder = inOrder(resource4, resource3, resource2, resource1);
    inOrder.verify(resource4, times(1)).close();
    inOrder.verify(resource3, times(1)).close();
    inOrder.verify(resource2, times(1)).close();
    inOrder.verify(resource1, times(1)).close();
  }

  @Test
  void useMethodConsumer3ValDequeBlockException() throws Exception {
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    final AutoCloseable resource3 = mock(AutoCloseable.class);
    final AutoCloseable resource4 = mock(AutoCloseable.class);
    final RuntimeException blockException = new RuntimeException("block");
    final Th4Consumer<AutoCloseable, AutoCloseable, AutoCloseable, ResourceDeque, Error> block =
      (res1, res2, res3, resources) -> {
        resources.push(resource4);
        throw blockException;
      };

    assertThatCode(() ->
      use(resource1, resource2, resource3, block)
    ).isSameAs(blockException);
    verify(resource1, times(1)).close();
    verify(resource2, times(1)).close();
    verify(resource3, times(1)).close();
    verify(resource4, times(1)).close();
  }

  @Test
  void useMethodConsumer3ValDequeCloseException() throws Exception {
    final RuntimeException closeException1 = new RuntimeException("resource1");
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    doThrow(closeException1).when(resource1).close();
    final RuntimeException closeException2 = new RuntimeException("resource2");
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    doThrow(closeException2).when(resource2).close();
    final AutoCloseable resource3 = mock(AutoCloseable.class);
    final RuntimeException closeException4 = new RuntimeException("resource4");
    final AutoCloseable resource4 = mock(AutoCloseable.class);
    doThrow(closeException4).when(resource4).close();
    final Th4Consumer<AutoCloseable, AutoCloseable, AutoCloseable, ResourceDeque, Error> block =
      (res1, res2, res4, resources) -> {
        resources.push(resource4);
      };

    assertThatCode(() ->
      use(resource1, resource2, resource3, block)
    ).isSameAs(closeException4)
      .hasSuppressedException(closeException2)
      .hasSuppressedException(closeException1);
    verify(resource1, times(1)).close();
    verify(resource2, times(1)).close();
    verify(resource3, times(1)).close();
    verify(resource4, times(1)).close();
  }

  @Test
  void useMethodConsumer3ValDequeBlockExceptionAndCloseException() throws Exception {
    final RuntimeException closeException1 = new RuntimeException("resource1");
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    doThrow(closeException1).when(resource1).close();
    final RuntimeException closeException2 = new RuntimeException("resource2");
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    doThrow(closeException2).when(resource2).close();
    final AutoCloseable resource3 = mock(AutoCloseable.class);
    final RuntimeException closeException4 = new RuntimeException("resource4");
    final AutoCloseable resource4 = mock(AutoCloseable.class);
    doThrow(closeException4).when(resource4).close();
    final RuntimeException blockException = new RuntimeException("block");
    final Th4Consumer<AutoCloseable, AutoCloseable, AutoCloseable, ResourceDeque, Error> block =
      (res1, res2, res3, resources) -> {
        resources.push(resource4);
        throw blockException;
      };

    assertThatCode(() ->
      use(resource1, resource2, resource3, block)
    ).isSameAs(blockException)
      .hasSuppressedException(closeException1)
      .hasSuppressedException(closeException2)
      .hasSuppressedException(closeException4);
    verify(resource1, times(1)).close();
    verify(resource2, times(1)).close();
    verify(resource3, times(1)).close();
    verify(resource4, times(1)).close();
  }

  @Test
  void useMethodFunction0ValAndDequeThrowsNPEForNullBlockArg() {
    final ThFunction<ResourceDeque, Object, Error> block = null;

    assertThatThrownBy(() ->
      use(block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void useMethodFunction0ValAndDeque() throws Exception {
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    final Object result = new Object();
    final ThFunction<ResourceDeque, Object, Error> block = resources -> {
      resources.push(resource1);
      resources.push(resource2);
      return result;
    };

    assertThat(
      use(block)
    ).isSameAs(result);
    final InOrder inOrder = inOrder(resource2, resource1);
    inOrder.verify(resource2, times(1)).close();
    inOrder.verify(resource1, times(1)).close();
  }

  @Test
  void useMethodFunction0ValAndDequeBlockException() throws Exception {
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    final RuntimeException blockException = new RuntimeException("block");
    final ThFunction<ResourceDeque, Object, Error> block = resources -> {
      resources.push(resource1);
      resources.push(resource2);
      throw blockException;
    };

    assertThatCode(() ->
      use(block)
    ).isSameAs(blockException);
    final InOrder inOrder = inOrder(resource2, resource1);
    inOrder.verify(resource2, times(1)).close();
    inOrder.verify(resource1, times(1)).close();
  }

  @Test
  void useMethodFunction0ValAndDequeCloseException() throws Exception {
    final RuntimeException closeException1 = new RuntimeException("resource1");
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    doThrow(closeException1).when(resource1).close();
    final RuntimeException closeException2 = new RuntimeException("resource2");
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    doThrow(closeException2).when(resource2).close();
    final RuntimeException blockException = new RuntimeException("block");
    final ThFunction<ResourceDeque, Object, Error> block = resources -> {
      resources.push(resource1);
      resources.push(resource2);
      throw blockException;
    };

    assertThatCode(() ->
      use(block)
    ).isSameAs(blockException)
      .hasSuppressedException(closeException2)
      .hasSuppressedException(closeException1);
    final InOrder inOrder = inOrder(resource2, resource1);
    inOrder.verify(resource2, times(1)).close();
    inOrder.verify(resource1, times(1)).close();
  }

  @Test
  void useMethodFunction0ValAndDequeBlockExceptionAndCloseException() throws Exception {
    final RuntimeException closeException1 = new RuntimeException("resource1");
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    doThrow(closeException1).when(resource1).close();
    final RuntimeException closeException2 = new RuntimeException("resource2");
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    doThrow(closeException2).when(resource2).close();
    final RuntimeException blockException = new RuntimeException("block");
    final ThFunction<ResourceDeque, Object, Error> block = resources -> {
      resources.push(resource1);
      resources.push(resource2);
      throw blockException;
    };

    assertThatCode(() ->
      use(block)
    ).isSameAs(blockException)
      .hasSuppressedException(closeException1)
      .hasSuppressedException(closeException2);
    final InOrder inOrder = inOrder(resource2, resource1);
    inOrder.verify(resource2, times(1)).close();
    inOrder.verify(resource1, times(1)).close();
  }

  @Test
  void useMethodFunction1ValThrowsNPEForNullBlockArg() {
    final AutoCloseable resource = mock(AutoCloseable.class);
    final ThFunction<AutoCloseable, Object, Error> block = null;

    assertThatThrownBy(() ->
      use(resource, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void useMethodFunction1ValThrowsNPEForNullResourceArg() {
    final AutoCloseable resource = null;
    final ThFunction<AutoCloseable, Object, Error> block = mock(ThFunction.class);

    assertThatThrownBy(() ->
      use(resource, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void useMethodFunction1Val() throws Exception {
    final AutoCloseable resource = mock(AutoCloseable.class);
    final ArgumentCaptor<AutoCloseable> resourceCaptor = ArgumentCaptor.forClass(AutoCloseable.class);
    final Object result = new Object();
    final ThFunction<AutoCloseable, Object, Error> block = mock(ThFunction.class);
    doReturn(result).when(block).apply(any());

    assertThat(
      use(resource, block)
    ).isSameAs(result);
    verify(block, times(1)).apply(resourceCaptor.capture());
    assertThat(resourceCaptor.getValue()).isSameAs(resource);
    verify(resource, times(1)).close();
  }

  @Test
  void useMethodFunction1ValBlockException() throws Exception {
    final AutoCloseable resource = mock(AutoCloseable.class);
    final RuntimeException blockException = new RuntimeException("block");
    final ThFunction<AutoCloseable, Object, Error> block = mock(ThFunction.class);
    doThrow(blockException).when(block).apply(any());

    assertThatCode(() ->
      use(resource, block)
    ).isSameAs(blockException);
    verify(block, times(1)).apply(any());
    verify(resource, times(1)).close();
  }

  @Test
  void useMethodFunction1ValCloseException() throws Exception {
    final RuntimeException closeException = new RuntimeException("resource");
    final AutoCloseable resource = mock(AutoCloseable.class);
    doThrow(closeException).when(resource).close();
    final ThFunction<AutoCloseable, Object, Error> block = mock(ThFunction.class);

    assertThatCode(() ->
      use(resource, block)
    ).isSameAs(closeException);
    verify(block, times(1)).apply(any());
    verify(resource, times(1)).close();
  }

  @Test
  void useMethodFunction1ValBlockAndCloseException() throws Exception {
    final RuntimeException closeException = new RuntimeException("resource");
    final AutoCloseable resource = mock(AutoCloseable.class);
    doThrow(closeException).when(resource).close();
    final RuntimeException blockException = new RuntimeException("block");
    final ThFunction<AutoCloseable, Object, Error> block = mock(ThFunction.class);
    doThrow(blockException).when(block).apply(any());

    assertThatCode(() ->
      use(resource, block)
    ).isSameAs(blockException)
      .hasSuppressedException(closeException);
    verify(block, times(1)).apply(any());
    verify(resource, times(1)).close();
  }

  @Test
  void useMethodFunction1ValAndDequeThrowsNPEForNullBlockArg() {
    final AutoCloseable resource = mock(AutoCloseable.class);
    final Th2Function<AutoCloseable, ResourceDeque, Object, Error> block = null;

    assertThatThrownBy(() ->
      use(resource, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void useMethodFunction1ValAndDequeThrowsNPEForNullResourceArg() {
    final AutoCloseable resource = null;
    final Th2Function<AutoCloseable, ResourceDeque, Object, Error> block = null;

    assertThatThrownBy(() ->
      use(resource, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void useMethodFunction1ValAndDeque() throws Exception {
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    final Object result = new Object();
    final Th2Function<AutoCloseable, ResourceDeque, Object, Error> block = (res1, resources) -> {
      resources.push(resource2);
      return result;
    };

    assertThat(
      use(resource1, block)
    ).isSameAs(result);
    final InOrder inOrder = inOrder(resource2, resource1);
    inOrder.verify(resource2, times(1)).close();
    inOrder.verify(resource1, times(1)).close();
  }

  @Test
  void useMethodFunction1ValAndDequeBlockException() throws Exception {
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    final RuntimeException blockException = new RuntimeException("block");
    final Th2Function<AutoCloseable, ResourceDeque, Object, Error> block = (res1, resources) -> {
      resources.push(resource2);
      throw blockException;
    };

    assertThatCode(() ->
      use(resource1, block)
    ).isSameAs(blockException);
    final InOrder inOrder = inOrder(resource2, resource1);
    inOrder.verify(resource2, times(1)).close();
    inOrder.verify(resource1, times(1)).close();
  }

  @Test
  void useMethodFunction1ValAndDequeCloseException() throws Exception {
    final RuntimeException closeException1 = new RuntimeException("resource1");
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    doThrow(closeException1).when(resource1).close();
    final RuntimeException closeException2 = new RuntimeException("resource2");
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    doThrow(closeException2).when(resource2).close();
    final Th2Function<AutoCloseable, ResourceDeque, Object, Error> block = (res1, resources) -> {
      resources.push(resource2);
      return new Object();
    };

    assertThatCode(() ->
      use(resource1, block)
    ).isSameAs(closeException2)
      .hasSuppressedException(closeException1);
    final InOrder inOrder = inOrder(resource2, resource1);
    inOrder.verify(resource2, times(1)).close();
    inOrder.verify(resource1, times(1)).close();
  }

  @Test
  void useMethodFunction1ValAndDequeBlockAndCloseException() throws Exception {
    final RuntimeException closeException1 = new RuntimeException("resource1");
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    doThrow(closeException1).when(resource1).close();
    final RuntimeException closeException2 = new RuntimeException("resource2");
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    doThrow(closeException2).when(resource2).close();
    final RuntimeException blockException = new RuntimeException("block");
    final Th2Function<AutoCloseable, ResourceDeque, Object, Error> block = (res1, resources) -> {
      resources.push(resource2);
      throw blockException;
    };

    assertThatCode(() ->
      use(resource1, block)
    ).isSameAs(blockException)
      .hasSuppressedException(closeException1)
      .hasSuppressedException(closeException1);
    final InOrder inOrder = inOrder(resource2, resource1);
    inOrder.verify(resource2, times(1)).close();
    inOrder.verify(resource1, times(1)).close();
  }

  @Test
  void useMethodFunction2ValThrowsNPEForNullBlockArg() {
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    final Th2Function<AutoCloseable, AutoCloseable, Object, Error> block = null;

    assertThatThrownBy(() ->
      use(resource1, resource2, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void useMethodFunction2ValThrowsNPEForNullResource1Arg() {
    final AutoCloseable resource1 = null;
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    final Th2Function<AutoCloseable, AutoCloseable, Object, Error> block = mock(Th2Function.class);

    assertThatThrownBy(() ->
      use(resource1, resource2, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void useMethodFunction2ValThrowsNPEForNullResource2Arg() {
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    final AutoCloseable resource2 = null;
    final Th2Function<AutoCloseable, AutoCloseable, Object, Error> block = mock(Th2Function.class);

    assertThatThrownBy(() ->
      use(resource1, resource2, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void useMethodFunction2Val() throws Exception {
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    final ArgumentCaptor<AutoCloseable> resourceCaptor1 = ArgumentCaptor.forClass(AutoCloseable.class);
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    final ArgumentCaptor<AutoCloseable> resourceCaptor2 = ArgumentCaptor.forClass(AutoCloseable.class);
    final Object result = new Object();
    final Th2Function<AutoCloseable, AutoCloseable, Object, Error> block = mock(Th2Function.class);
    doReturn(result).when(block).apply(any(), any());

    assertThat(
      use(resource1, resource2, block)
    ).isSameAs(result);
    verify(block, times(1)).apply(resourceCaptor1.capture(), resourceCaptor2.capture());
    assertThat(resourceCaptor1.getValue()).isSameAs(resource1);
    assertThat(resourceCaptor2.getValue()).isSameAs(resource2);
    final InOrder inOrder = inOrder(resource2, resource1);
    inOrder.verify(resource2, times(1)).close();
    inOrder.verify(resource1, times(1)).close();
  }

  @Test
  void useMethodFunction2ValBlockException() throws Exception {
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    final RuntimeException blockException = new RuntimeException("block");
    final Th2Function<AutoCloseable, AutoCloseable, Object, Error> block = mock(Th2Function.class);
    doThrow(blockException).when(block).apply(any(), any());

    assertThatCode(() ->
      use(resource1, resource2, block)
    ).isSameAs(blockException);
    verify(block, times(1)).apply(any(), any());
    verify(resource1, times(1)).close();
    verify(resource2, times(1)).close();
  }

  @Test
  void useMethodFunction2ValCloseException() throws Exception {
    final RuntimeException closeException1 = new RuntimeException("resource1");
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    doThrow(closeException1).when(resource1).close();
    final RuntimeException closeException2 = new RuntimeException("resource2");
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    doThrow(closeException2).when(resource2).close();
    final Th2Function<AutoCloseable, AutoCloseable, Object, Error> block = mock(Th2Function.class);

    assertThatCode(() ->
      use(resource1, resource2, block)
    ).isSameAs(closeException2)
      .hasSuppressedException(closeException1);
    verify(block, times(1)).apply(any(), any());
    verify(resource1, times(1)).close();
    verify(resource2, times(1)).close();
  }

  @Test
  void useMethodFunction2ValBlockExceptionAndCloseException() throws Exception {
    final RuntimeException closeException1 = new RuntimeException("resource1");
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    doThrow(closeException1).when(resource1).close();
    final RuntimeException closeException2 = new RuntimeException("resource2");
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    doThrow(closeException2).when(resource2).close();
    final RuntimeException blockException = new RuntimeException("block");
    final Th2Function<AutoCloseable, AutoCloseable, Object, Error> block = mock(Th2Function.class);
    doThrow(blockException).when(block).apply(any(), any());

    assertThatCode(() ->
      use(resource1, resource2, block)
    ).isSameAs(blockException)
      .hasSuppressedException(closeException1)
      .hasSuppressedException(closeException2);
    verify(block, times(1)).apply(any(), any());
    verify(resource1, times(1)).close();
    verify(resource2, times(1)).close();
  }

  @Test
  void useMethodFunction2ValDequeThrowsNPEForNullBlockArg() {
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    final Th3Function<AutoCloseable, AutoCloseable, ResourceDeque, Object, Error> block = null;

    assertThatThrownBy(() ->
      use(resource1, resource2, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void useMethodFunction2ValDequeThrowsNPEForNullResource1Arg() {
    final AutoCloseable resource1 = null;
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    final Th3Function<AutoCloseable, AutoCloseable, ResourceDeque, Object, Error> block = mock(Th3Function.class);

    assertThatThrownBy(() ->
      use(resource1, resource2, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void useMethodFunction2ValDequeThrowsNPEForNullResource2Arg() {
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    final AutoCloseable resource2 = null;
    final Th3Function<AutoCloseable, AutoCloseable, ResourceDeque, Object, Error> block = mock(Th3Function.class);

    assertThatThrownBy(() ->
      use(resource1, resource2, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void useMethodFunction2ValDeque() throws Exception {
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    final AutoCloseable resource3 = mock(AutoCloseable.class);
    final Object result = new Object();
    final Th3Function<AutoCloseable, AutoCloseable, ResourceDeque, Object, Error> block = (res1, res2, resources) -> {
      resources.push(resource3);
      return result;
    };

    assertThat(
      use(resource1, resource2, block)
    ).isSameAs(result);
    final InOrder inOrder = inOrder(resource3, resource2, resource1);
    inOrder.verify(resource3, times(1)).close();
    inOrder.verify(resource2, times(1)).close();
    inOrder.verify(resource1, times(1)).close();
  }

  @Test
  void useMethodFunction2ValDequeBlockException() throws Exception {
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    final AutoCloseable resource3 = mock(AutoCloseable.class);
    final RuntimeException blockException = new RuntimeException("block");
    final Th3Function<AutoCloseable, AutoCloseable, ResourceDeque, Object, Error> block = (res1, res2, resources) -> {
      resources.push(resource3);
      throw blockException;
    };

    assertThatCode(() ->
      use(resource1, resource2, block)
    ).isSameAs(blockException);
    verify(resource1, times(1)).close();
    verify(resource2, times(1)).close();
    verify(resource3, times(1)).close();
  }

  @Test
  void useMethodFunction2ValDequeCloseException() throws Exception {
    final RuntimeException closeException1 = new RuntimeException("resource1");
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    doThrow(closeException1).when(resource1).close();
    final RuntimeException closeException2 = new RuntimeException("resource2");
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    doThrow(closeException2).when(resource2).close();
    final RuntimeException closeException3 = new RuntimeException("resource3");
    final AutoCloseable resource3 = mock(AutoCloseable.class);
    doThrow(closeException3).when(resource3).close();
    final Th3Function<AutoCloseable, AutoCloseable, ResourceDeque, Object, Error> block = (res1, res2, resources) -> {
      resources.push(resource3);
      return new Object();
    };

    assertThatCode(() ->
      use(resource1, resource2, block)
    ).isSameAs(closeException3)
      .hasSuppressedException(closeException2)
      .hasSuppressedException(closeException1);
    verify(resource1, times(1)).close();
    verify(resource2, times(1)).close();
    verify(resource3, times(1)).close();
  }

  @Test
  void useMethodFunction2ValDequeBlockExceptionAndCloseException() throws Exception {
    final RuntimeException closeException1 = new RuntimeException("resource1");
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    doThrow(closeException1).when(resource1).close();
    final RuntimeException closeException2 = new RuntimeException("resource2");
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    doThrow(closeException2).when(resource2).close();
    final RuntimeException closeException3 = new RuntimeException("resource3");
    final AutoCloseable resource3 = mock(AutoCloseable.class);
    doThrow(closeException3).when(resource3).close();
    final RuntimeException blockException = new RuntimeException("block");
    final Th3Function<AutoCloseable, AutoCloseable, ResourceDeque, Object, Error> block = (res1, res2, resources) -> {
      resources.push(resource3);
      throw blockException;
    };

    assertThatCode(() ->
      use(resource1, resource2, block)
    ).isSameAs(blockException)
      .hasSuppressedException(closeException1)
      .hasSuppressedException(closeException2)
      .hasSuppressedException(closeException3);
    verify(resource1, times(1)).close();
    verify(resource2, times(1)).close();
    verify(resource3, times(1)).close();
  }

  @Test
  void useMethodFunction3ValThrowsNPEForNullBlockArg() {
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    final AutoCloseable resource3 = mock(AutoCloseable.class);
    final Th3Function<AutoCloseable, AutoCloseable, AutoCloseable, Object, Error> block = null;

    assertThatThrownBy(() ->
      use(resource1, resource2, resource3, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void useMethodFunction3ValThrowsNPEForNullResource1Arg() {
    final AutoCloseable resource1 = null;
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    final AutoCloseable resource3 = mock(AutoCloseable.class);
    final Th3Function<AutoCloseable, AutoCloseable, AutoCloseable, Object, Error> block = mock(Th3Function.class);

    assertThatThrownBy(() ->
      use(resource1, resource2, resource3, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void useMethodFunction3ValThrowsNPEForNullResource2Arg() {
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    final AutoCloseable resource2 = null;
    final AutoCloseable resource3 = mock(AutoCloseable.class);
    final Th3Function<AutoCloseable, AutoCloseable, AutoCloseable, Object, Error> block = mock(Th3Function.class);

    assertThatThrownBy(() ->
      use(resource1, resource2, resource3, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void useMethodFunction3ValThrowsNPEForNullResource3Arg() {
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    final AutoCloseable resource3 = null;
    final Th3Function<AutoCloseable, AutoCloseable, AutoCloseable, Object, Error> block = mock(Th3Function.class);

    assertThatThrownBy(() ->
      use(resource1, resource2, resource3, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void useMethodFunction3Val() throws Exception {
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    final ArgumentCaptor<AutoCloseable> resourceCaptor1 = ArgumentCaptor.forClass(AutoCloseable.class);
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    final ArgumentCaptor<AutoCloseable> resourceCaptor2 = ArgumentCaptor.forClass(AutoCloseable.class);
    final AutoCloseable resource3 = mock(AutoCloseable.class);
    final ArgumentCaptor<AutoCloseable> resourceCaptor3 = ArgumentCaptor.forClass(AutoCloseable.class);
    final Object result = new Object();
    final Th3Function<AutoCloseable, AutoCloseable, AutoCloseable, Object, Error> block = mock(Th3Function.class);
    doReturn(result).when(block).apply(any(), any(), any());

    assertThat(
      use(resource1, resource2, resource3, block)
    ).isSameAs(result);
    verify(block, times(1)).apply(resourceCaptor1.capture(), resourceCaptor2.capture(), resourceCaptor3.capture());
    assertThat(resourceCaptor1.getValue()).isSameAs(resource1);
    assertThat(resourceCaptor2.getValue()).isSameAs(resource2);
    assertThat(resourceCaptor3.getValue()).isSameAs(resource3);
    final InOrder inOrder = inOrder(resource3, resource2, resource1);
    inOrder.verify(resource3, times(1)).close();
    inOrder.verify(resource2, times(1)).close();
    inOrder.verify(resource1, times(1)).close();
  }

  @Test
  void useMethodFunction3ValBlockException() throws Exception {
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    final AutoCloseable resource3 = mock(AutoCloseable.class);
    final RuntimeException blockException = new RuntimeException("block");
    final Th3Function<AutoCloseable, AutoCloseable, AutoCloseable, Object, Error> block = mock(Th3Function.class);
    doThrow(blockException).when(block).apply(any(), any(), any());

    assertThatCode(() ->
      use(resource1, resource2, resource3, block)
    ).isSameAs(blockException);
    verify(block, times(1)).apply(any(), any(), any());
    verify(resource1, times(1)).close();
    verify(resource2, times(1)).close();
    verify(resource3, times(1)).close();
  }


  @Test
  void useMethodFunction3ValCloseException() throws Exception {
    final RuntimeException closeException1 = new RuntimeException("resource1");
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    doThrow(closeException1).when(resource1).close();
    final RuntimeException closeException2 = new RuntimeException("resource2");
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    doThrow(closeException2).when(resource2).close();
    final RuntimeException closeException3 = new RuntimeException("resource3");
    final AutoCloseable resource3 = mock(AutoCloseable.class);
    doThrow(closeException3).when(resource3).close();
    final Th3Function<AutoCloseable, AutoCloseable, AutoCloseable, Object, Error> block = mock(Th3Function.class);

    assertThatCode(() ->
      use(resource1, resource2, resource3, block)
    ).isSameAs(closeException3)
      .hasSuppressedException(closeException1)
      .hasSuppressedException(closeException2);
    verify(block, times(1)).apply(any(), any(), any());
    verify(resource1, times(1)).close();
    verify(resource2, times(1)).close();
    verify(resource3, times(1)).close();
  }

  @Test
  void useMethodFunction3ValBlockExceptionAndCloseException() throws Exception {
    final RuntimeException closeException1 = new RuntimeException("resource1");
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    doThrow(closeException1).when(resource1).close();
    final RuntimeException closeException2 = new RuntimeException("resource2");
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    doThrow(closeException2).when(resource2).close();
    final RuntimeException closeException3 = new RuntimeException("resource3");
    final AutoCloseable resource3 = mock(AutoCloseable.class);
    doThrow(closeException3).when(resource3).close();
    final RuntimeException blockException = new RuntimeException("block");
    final Th3Function<AutoCloseable, AutoCloseable, AutoCloseable, Object, Error> block = mock(Th3Function.class);
    doThrow(blockException).when(block).apply(any(), any(), any());

    assertThatCode(() ->
      use(resource1, resource2, resource3, block)
    ).isSameAs(blockException)
      .hasSuppressedException(closeException1)
      .hasSuppressedException(closeException2);
    verify(block, times(1)).apply(any(), any(), any());
    verify(resource1, times(1)).close();
    verify(resource2, times(1)).close();
  }


  @Test
  void useMethodFunction3ValDequeThrowsNPEForNullBlockArg() {
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    final AutoCloseable resource3 = mock(AutoCloseable.class);
    final Th4Function<AutoCloseable, AutoCloseable, AutoCloseable, ResourceDeque, Object, Error> block = null;

    assertThatThrownBy(() ->
      use(resource1, resource2, resource3, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void useMethodFunction3ValDequeThrowsNPEForNullResource1Arg() {
    final AutoCloseable resource1 = null;
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    final AutoCloseable resource3 = mock(AutoCloseable.class);
    final Th4Function<AutoCloseable, AutoCloseable, AutoCloseable, ResourceDeque, Object, Error> block =
      mock(Th4Function.class);

    assertThatThrownBy(() ->
      use(resource1, resource2, resource3, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void useMethodFunction3ValDequeThrowsNPEForNullResource2Arg() {
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    final AutoCloseable resource2 = null;
    final AutoCloseable resource3 = mock(AutoCloseable.class);
    final Th4Function<AutoCloseable, AutoCloseable, AutoCloseable, ResourceDeque, Object, Error> block =
      mock(Th4Function.class);

    assertThatThrownBy(() ->
      use(resource1, resource2, resource3, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void useMethodFunction3ValDequeThrowsNPEForNullResource3Arg() {
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    final AutoCloseable resource3 = null;
    final Th4Function<AutoCloseable, AutoCloseable, AutoCloseable, ResourceDeque, Object, Error> block =
      mock(Th4Function.class);

    assertThatThrownBy(() ->
      use(resource1, resource2, resource3, block)
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void useMethodFunction3ValDeque() throws Exception {
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    final AutoCloseable resource3 = mock(AutoCloseable.class);
    final AutoCloseable resource4 = mock(AutoCloseable.class);
    final Object result = new Object();
    final Th4Function<AutoCloseable, AutoCloseable, AutoCloseable, ResourceDeque, Object, Error> block =
      (res1, res2, res3, resources) -> {
        resources.push(resource4);
        return result;
      };

    assertThat(
      use(resource1, resource2, resource3, block)
    ).isSameAs(result);
    final InOrder inOrder = inOrder(resource4, resource3, resource2, resource1);
    inOrder.verify(resource4, times(1)).close();
    inOrder.verify(resource3, times(1)).close();
    inOrder.verify(resource2, times(1)).close();
    inOrder.verify(resource1, times(1)).close();
  }

  @Test
  void useMethodFunction3ValDequeBlockException() throws Exception {
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    final AutoCloseable resource3 = mock(AutoCloseable.class);
    final AutoCloseable resource4 = mock(AutoCloseable.class);
    final RuntimeException blockException = new RuntimeException("block");
    final Th4Function<AutoCloseable, AutoCloseable, AutoCloseable, ResourceDeque, Object, Error> block =
      (res1, res2, res3, resources) -> {
        resources.push(resource4);
        throw blockException;
      };

    assertThatCode(() ->
      use(resource1, resource2, resource3, block)
    ).isSameAs(blockException);
    verify(resource1, times(1)).close();
    verify(resource2, times(1)).close();
    verify(resource3, times(1)).close();
    verify(resource4, times(1)).close();
  }

  @Test
  void useMethodFunction3ValDequeCloseException() throws Exception {
    final RuntimeException closeException1 = new RuntimeException("resource1");
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    doThrow(closeException1).when(resource1).close();
    final RuntimeException closeException2 = new RuntimeException("resource2");
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    doThrow(closeException2).when(resource2).close();
    final AutoCloseable resource3 = mock(AutoCloseable.class);
    final RuntimeException closeException4 = new RuntimeException("resource4");
    final AutoCloseable resource4 = mock(AutoCloseable.class);
    doThrow(closeException4).when(resource4).close();
    final Th4Function<AutoCloseable, AutoCloseable, AutoCloseable, ResourceDeque, Object, Error> block =
      (res1, res2, res4, resources) -> {
        resources.push(resource4);
        return new Object();
      };

    assertThatCode(() ->
      use(resource1, resource2, resource3, block)
    ).isSameAs(closeException4)
      .hasSuppressedException(closeException2)
      .hasSuppressedException(closeException1);
    verify(resource1, times(1)).close();
    verify(resource2, times(1)).close();
    verify(resource3, times(1)).close();
    verify(resource4, times(1)).close();
  }

  @Test
  void useMethodFunction3ValDequeBlockExceptionAndCloseException() throws Exception {
    final RuntimeException closeException1 = new RuntimeException("resource1");
    final AutoCloseable resource1 = mock(AutoCloseable.class);
    doThrow(closeException1).when(resource1).close();
    final RuntimeException closeException2 = new RuntimeException("resource2");
    final AutoCloseable resource2 = mock(AutoCloseable.class);
    doThrow(closeException2).when(resource2).close();
    final AutoCloseable resource3 = mock(AutoCloseable.class);
    final RuntimeException closeException4 = new RuntimeException("resource4");
    final AutoCloseable resource4 = mock(AutoCloseable.class);
    doThrow(closeException4).when(resource4).close();
    final RuntimeException blockException = new RuntimeException("block");
    final Th4Function<AutoCloseable, AutoCloseable, AutoCloseable, ResourceDeque, Object, Error> block =
      (res1, res2, res3, resources) -> {
        resources.push(resource4);
        throw blockException;
      };

    assertThatCode(() ->
      use(resource1, resource2, resource3, block)
    ).isSameAs(blockException)
      .hasSuppressedException(closeException1)
      .hasSuppressedException(closeException2)
      .hasSuppressedException(closeException4);
    verify(resource1, times(1)).close();
    verify(resource2, times(1)).close();
    verify(resource3, times(1)).close();
    verify(resource4, times(1)).close();
  }
}
