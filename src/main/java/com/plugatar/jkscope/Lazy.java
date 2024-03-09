package com.plugatar.jkscope;

import com.plugatar.jkscope.function.ThConsumer;
import com.plugatar.jkscope.function.ThFunction;
import com.plugatar.jkscope.function.ThPredicate;
import com.plugatar.jkscope.function.ThSupplier;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

import static com.plugatar.jkscope.Utils.UNINITIALIZED_VALUE;
import static com.plugatar.jkscope.Utils.blockArgNotNull;
import static com.plugatar.jkscope.Utils.uncheckedCast;

/**
 * Represents a value with lazy initialization.
 *
 * @param <V> the type of the value
 */
public interface Lazy<V> extends ThSupplier<V, RuntimeException>, BaseScope<V, Lazy<V>> {

  /**
   * Returns {@code true} if a value for this Lazy instance has been already initialized, and {@code false} otherwise.
   *
   * @return {@code true} if a value for this Lazy instance has been already initialized, and {@code false} otherwise
   */
  boolean isInitialized();

  /**
   * Returns value.
   *
   * @return value
   */
  @Override
  V get();

  @Override
  default Lazy<V> also(final ThConsumer<? super V, ?> block) {
    blockArgNotNull(block);
    ThConsumer.unchecked(block).accept(this.get());
    return this;
  }

  @Override
  default Lazy<V> letIt(final ThConsumer<? super V, ?> block) {
    blockArgNotNull(block);
    ThConsumer.unchecked(block).accept(this.get());
    return this;
  }

  @Override
  default <R> R letOut(final ThFunction<? super V, ? extends R, ?> block) {
    blockArgNotNull(block);
    return ThFunction.unchecked(block).apply(this.get());
  }

  @Override
  default <R> Opt<R> letOpt(final ThFunction<? super V, ? extends R, ?> block) {
    blockArgNotNull(block);
    return Opt.of(ThFunction.unchecked(block).apply(this.get()));
  }

  @Override
  default Opt<V> takeIf(final ThPredicate<? super V, ?> block) {
    blockArgNotNull(block);
    final V v = this.get();
    return ThPredicate.unchecked(block).test(v)
      ? Opt.of(v)
      : Opt.empty();
  }

  @Override
  default Opt<V> takeUnless(final ThPredicate<? super V, ?> block) {
    blockArgNotNull(block);
    final V v = this.get();
    return ThPredicate.unchecked(block).test(v)
      ? Opt.empty()
      : Opt.of(v);
  }

  /**
   * Returns a new {@link Lazy} instance that uses the specified initialization function and the
   * {@link ThreadSafetyMode#SYNCHRONIZED} thread-safety mode. The returned instance uses itself to synchronize on.
   *
   * @param initializer the value initializer
   * @param <V>         the type of the value
   * @return new {@link Lazy} instance
   * @throws NullPointerException if {@code initializer} arg is null
   */
  static <V> Lazy<V> of(final ThSupplier<? extends V, ?> initializer) {
    if (initializer == null) { throw new NullPointerException("initializer arg is null"); }
    return new Synchronized<>(null, initializer);
  }

  /**
   * Returns a new {@link Lazy} instance that uses the specified initialization function and the
   * {@link ThreadSafetyMode#SYNCHRONIZED} thread-safety mode. The returned instance uses the specified lock object to
   * synchronize on.
   *
   * @param lock        the lock object
   * @param initializer the value initializer
   * @param <V>         the type of the value
   * @return new {@link Lazy} instance
   * @throws NullPointerException if {@code lock} or {@code initializer} arg is null
   */
  static <V> Lazy<V> of(final Object lock,
                        final ThSupplier<? extends V, ?> initializer) {
    if (lock == null) { throw new NullPointerException("lock arg is null"); }
    if (initializer == null) { throw new NullPointerException("initializer arg is null"); }
    return new Synchronized<>(lock, initializer);
  }

  /**
   * Returns a new {@link Lazy} instance that uses the specified initialization function and thread-safety mode. For
   * {@link ThreadSafetyMode#SYNCHRONIZED} the returned instance uses itself to synchronize on.
   *
   * @param threadSafetyMode the thread safety mode
   * @param initializer      the value initializer
   * @param <V>              the type of the value
   * @return new {@link Lazy} instance
   * @throws NullPointerException if {@code threadSafetyMode} or {@code initializer} arg is null
   */
  static <V> Lazy<V> of(final ThreadSafetyMode threadSafetyMode,
                        final ThSupplier<? extends V, ?> initializer) {
    if (threadSafetyMode == null) { throw new NullPointerException("threadSafetyMode arg is null"); }
    if (initializer == null) { throw new NullPointerException("initializer arg is null"); }
    switch (threadSafetyMode) {
      case SYNCHRONIZED:
        return new Synchronized<>(null, initializer);
      case PUBLICATION:
        return new SafePublication<>(initializer);
      case NONE:
        return new Unsafe<>(initializer);
      default:
        throw new Error("impossible");
    }
  }

  /**
   * Returns a new {@link Lazy} instance that is already initialized with the specified value.
   *
   * @param value the value
   * @param <V>   the type of the value
   * @return new {@link Lazy} instance
   */
  static <V> Lazy<V> ofValue(final V value) {
    return value == null
      ? uncheckedCast(Initialized.NULL)
      : new Initialized<>(value);
  }

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

  /**
   * {@link ThreadSafetyMode#SYNCHRONIZED} {@link Lazy} implementation.
   *
   * @param <V> the type of the value
   */
  final class Synchronized<V> implements Lazy<V>, Serializable {
    private static final long serialVersionUID = 1L;
    private final Object lock;
    private ThSupplier<? extends V, ?> initializer;
    private volatile Object value;

    /**
     * Ctor.
     *
     * @param lock        the lock object ({@code null} means {@code this} object)
     * @param initializer the value initializer
     */
    private Synchronized(final Object lock,
                         final ThSupplier<? extends V, ?> initializer) {
      this.lock = lock == null ? this : lock;
      this.initializer = initializer;
      this.value = UNINITIALIZED_VALUE;
    }

    @Override
    public boolean isInitialized() {
      return this.value != UNINITIALIZED_VALUE;
    }

    @Override
    public V get() {
      final Object v1 = this.value;
      if (v1 != UNINITIALIZED_VALUE) {
        return uncheckedCast(v1);
      }
      synchronized (this.lock) {
        final Object v2 = this.value;
        if (v2 != UNINITIALIZED_VALUE) {
          return uncheckedCast(v2);
        }
        final V newValue = ThSupplier.unchecked(this.initializer).get();
        this.value = newValue;
        this.initializer = null;
        return newValue;
      }
    }

    @Override
    public String toString() {
      return this.isInitialized()
        ? Objects.toString(this.value)
        : "Lazy value not initialized yet";
    }

    private Object writeReplace() {
      return Lazy.ofValue(this.get());
    }
  }

  /**
   * {@link ThreadSafetyMode#PUBLICATION} {@link Lazy} implementation.
   *
   * @param <V> the type of the value
   */
  final class SafePublication<V> implements Lazy<V>, Serializable {
    private static final long serialVersionUID = 1L;
    private static final AtomicReferenceFieldUpdater<SafePublication<?>, Object> VALUE_UPDATER =
      uncheckedCast(AtomicReferenceFieldUpdater.newUpdater(SafePublication.class, Object.class, "value"));
    private volatile ThSupplier<? extends V, ?> initializer;
    private volatile Object value;

    /**
     * Ctor.
     *
     * @param initializer the value initializer
     */
    private SafePublication(final ThSupplier<? extends V, ?> initializer) {
      this.initializer = initializer;
      this.value = UNINITIALIZED_VALUE;
    }

    @Override
    public boolean isInitialized() {
      return this.value != UNINITIALIZED_VALUE;
    }

    @Override
    public V get() {
      final Object v = this.value;
      if (v != UNINITIALIZED_VALUE) {
        return uncheckedCast(v);
      }
      final ThSupplier<? extends V, ?> init = this.initializer;
      if (init != null) {
        final V newValue = ThSupplier.unchecked(init).get();
        if (VALUE_UPDATER.compareAndSet(this, UNINITIALIZED_VALUE, newValue)) {
          this.initializer = null;
          return newValue;
        }
      }
      return uncheckedCast(this.value);
    }

    @Override
    public String toString() {
      return this.isInitialized()
        ? Objects.toString(this.value)
        : "Lazy value not initialized yet";
    }

    private Object writeReplace() {
      return Lazy.ofValue(this.get());
    }
  }

  /**
   * {@link ThreadSafetyMode#NONE} {@link Lazy} implementation.
   *
   * @param <V> the type of the value
   */
  final class Unsafe<V> implements Lazy<V>, Serializable {
    private static final long serialVersionUID = 1L;
    private ThSupplier<? extends V, ?> initializer;
    private Object value;

    /**
     * Ctor.
     *
     * @param initializer the value initializer
     */
    private Unsafe(final ThSupplier<? extends V, ?> initializer) {
      this.initializer = initializer;
      this.value = UNINITIALIZED_VALUE;
    }

    @Override
    public boolean isInitialized() {
      return this.value != UNINITIALIZED_VALUE;
    }

    @Override
    public V get() {
      final Object v = this.value;
      if (v != UNINITIALIZED_VALUE) {
        return uncheckedCast(v);
      }
      final ThSupplier<? extends V, ?> init = this.initializer;
      if (init != null) {
        final V newValue = ThSupplier.unchecked(init).get();
        this.value = newValue;
        this.initializer = null;
        return newValue;
      }
      return uncheckedCast(this.value);
    }

    @Override
    public String toString() {
      return this.isInitialized()
        ? Objects.toString(this.value)
        : "Lazy value not initialized yet";
    }

    private Object writeReplace() {
      return Lazy.ofValue(this.get());
    }
  }

  /**
   * Initialized {@link Lazy} implementation.
   *
   * @param <V> the type of the value
   */
  final class Initialized<V> implements Lazy<V>, Serializable {
    private static final long serialVersionUID = 1L;
    private static final Initialized<?> NULL = new Initialized<>(null);
    private final V value;

    /**
     * Ctor.
     *
     * @param value the value
     */
    private Initialized(final V value) {
      this.value = value;
    }

    @Override
    public boolean isInitialized() {
      return true;
    }

    @Override
    public V get() {
      return this.value;
    }

    @Override
    public String toString() {
      return Objects.toString(this.get());
    }
  }
}
