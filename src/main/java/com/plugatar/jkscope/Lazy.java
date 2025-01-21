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

import com.plugatar.jkscope.function.ThSupplier;

import java.util.function.Supplier;

/**
 * Represents a value with lazy initialization.
 *
 * @param <V> the type of the value
 */
public interface Lazy<V> extends ThSupplier<V, RuntimeException>, Supplier<V> {

  /**
   * Returns {@code true} if a value for this Lazy instance has been already initialized, and {@code false} otherwise.
   *
   * @return {@code true} if a value for this Lazy instance has been already initialized, and {@code false} otherwise
   */
  boolean isInitialized();

  /**
   * Calculate and return the value.
   *
   * @return value
   */
  @Override
  V get();

  /**
   * Specifies how a {@link Lazy} instance synchronizes initialization among multiple threads.
   */
  enum ThreadSafetyMode {
    /**
     * Locks are used to ensure that only a single thread can initialize the {@link Lazy} instance.
     */
    SYNCHRONIZED,
    /**
     * Initializer function can be called several times on concurrent access to uninitialized {@link Lazy} instance
     * value, but only the first returned value will be used as the value of {@link Lazy} instance.
     */
    PUBLICATION,
    /**
     * No locks are used to synchronize access to the {@link Lazy} instance value. If the instance is accessed from
     * multiple threads, its behavior is undefined.
     */
    NONE
  }
}
