# JKScope

[![Maven Central](https://img.shields.io/maven-central/v/com.plugatar.jkscope/jkscope)](https://central.sonatype.com/artifact/com.plugatar.jkscope/jkscope)
[![Javadoc](https://javadoc.io/badge2/com.plugatar.jkscope/jkscope/javadoc.svg?color=blue)](https://javadoc.io/doc/com.plugatar.jkscope/jkscope)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Mentioned in Awesome Java](https://awesome.re/mentioned-badge.svg)](https://github.com/akullpp/awesome-java)

[![GitHub Actions Workflow Status](https://img.shields.io/github/actions/workflow/status/evpl/jkscope/build.yml?branch=main)](https://github.com/evpl/jkscope)
[![Lines](https://sloc.xyz/github/evpl/jkscope/?category=lines)](https://github.com/evpl/jkscope)
[![Code lines](https://sloc.xyz/github/evpl/jkscope/?category=code)](https://github.com/evpl/jkscope)
[![Hits of Code](https://hitsofcode.com/github/evpl/jkscope?branch=main)](https://hitsofcode.com/github/evpl/jkscope/view?branch=main)

Java scope functions inspired by Kotlin

## Table of Contents

* [Motivation](#motivation)
* [How to use](#how-to-use)
* [Docs](#docs)
  * [JKScope methods](#jkscope-methods)
    * [`with` methods](#with-methods)
    * [`let` methods](#let-methods)
    * [`it` methods](#it-methods)
    * [`use` methods](#use-methods)
    * [`repeat` methods](#repeat-methods)
    * [`iterateOver` methods](#iterateOver-methods)
    * [`iterate` methods](#iterate-methods)
    * [`recur` methods](#recur-methods)
    * [`lazy` methods](#lazy-methods)
  * [Unchecked functions](#unchecked-functions)
  * [Examples](#examples)
    * [Collection initialization](#collection-initialization)
    * [Argument in a method chain](#argument-in-a-method-chain)
    * [Nth Fibonacci number](#nth-fibonacci-number)
    * [Get all related exceptions via recursion](#get-all-related-exceptions-via-recursion)
    * [Get all related exceptions via iteration](#get-all-related-exceptions-via-iteration)

## Motivation

Set of utility methods. Inspired by the [Kotlin scope function](https://kotlinlang.org/docs/scope-functions.html).

## How to use

Java 8+ version required. The library has no dependencies.

Maven:

```xml
<dependency>
  <groupId>com.plugatar.jkscope</groupId>
  <artifactId>jkscope</artifactId>
  <version>3.1</version>
  <scope>compile</scope>
</dependency>
```

Gradle:

```groovy
dependencies {
  implementation 'com.plugatar.jkscope:jkscope:3.1'
}
```

## Docs

### JKScope static methods

Import static methods you need or import them all at once.

```
import static com.plugatar.jkscope.JKScope.*;
```

#### `with` methods

Performs given function block on given values (0 zero to 5).

* `with(ThRunnable)`
* `with(Object, ThConsumer)`
* `with(Object, Object, Th2Consumer)`
* `with(Object, Object, Object, Th3Consumer)`
* `with(Object, Object, Object, Object, Th4Consumer)`
* `with(Object, Object, Object, Object, Object, Th5Consumer)`

```
with(() -> {
  System.out.println("Hello");
});

with("value", v -> {
  System.out.println(v);
});

with("value1", "value2", (v1, v2) -> {
  System.out.println(v1);
  System.out.println(v2);
});
```

#### `let` methods

Performs given function block on given values (from 0 to 5) and returns result.

* `let(ThSupplier)`
* `let(Object, ThFunction)`
* `let(Object, Object, Th2Function)`
* `let(Object, Object, Object, Th3Function)`
* `let(Object, Object, Object, Object, Th4Function)`
* `let(Object, Object, Object, Object, Object, Th5Function)`

```
let(() -> {
  return "Hello";
});

String result = let("value", v -> {
  System.out.println(v);
  return v.toUpperCase();
});

String result = let("value1", "value2", (v1, v2) -> {
  System.out.println(v1);
  System.out.println(v2);
  return v1.toUpperCase() + v2.toUpperCase();
});
```

#### `it` methods

Performs given function block on given value (and from 0 to 4 additional values) and returns this (first) value.

* `it(ThSupplier)`
* `it(Object, ThConsumer)`
* `it(Object, Object, Th2Consumer)`
* `it(Object, Object, Object, Th3Consumer)`
* `it(Object, Object, Object, Object, Th4Consumer)`
* `it(Object, Object, Object, Object, Object, Th5Consumer)`

```
String value = it(() -> {
  return "Hello";
});

String result = it("value", v -> {
  System.out.println(v);
});

String result = it("value1", "value2", (v1, v2) -> {
  System.out.println(v1);
  System.out.println(v2);
});
```

#### `use` methods

Performs given function block (with additional given resources from 0 to 3), then closes all resources in ResourceDeque
and returns result if necessary.

* `use(ThConsumer)`
* `use(AutoCloseable, ThConsumer)`
* `use(AutoCloseable, Th2Consumer)`
* `use(AutoCloseable, AutoCloseable, Th2Consumer)`
* `use(AutoCloseable, AutoCloseable, Th3Consumer)`
* `use(AutoCloseable, AutoCloseable, AutoCloseable, Th3Consumer)`
* `use(AutoCloseable, AutoCloseable, AutoCloseable, Th4Consumer)`
* `use(ThFunction)`
* `use(AutoCloseable, ThFunction)`
* `use(AutoCloseable, Th2Function)`
* `use(AutoCloseable, AutoCloseable, Th2Function)`
* `use(AutoCloseable, AutoCloseable, Th3Function)`
* `use(AutoCloseable, AutoCloseable, AutoCloseable, Th3Function)`
* `use(AutoCloseable, AutoCloseable, AutoCloseable, Th4Function)`

```
use(resources -> {
  AutoCloseableImpl1 ac1 = resources.push(new AutoCloseableImpl1());
  AutoCloseableImpl2 ac2 = resources.push(new AutoCloseableImpl2());
  //...
});

String result = use(resources -> {
  AutoCloseableImpl1 ac1 = resources.push(new AutoCloseableImpl1());
  AutoCloseableImpl2 ac2 = resources.push(new AutoCloseableImpl2());
  //...
  return "result";
});

use(new AutoCloseableImpl1(), new AutoCloseableImpl2(), (ac1, ac2, resources) -> {
  AutoCloseableImpl3 ac3 = resources.push(new AutoCloseableImpl3());
  AutoCloseableImpl4 ac4 = resources.push(new AutoCloseableImpl4());
  //...
});

String result = use(new AutoCloseableImpl1(), new AutoCloseableImpl2(), (ac1, ac2, resources) -> {
  AutoCloseableImpl3 ac3 = resources.push(new AutoCloseableImpl3());
  AutoCloseableImpl4 ac4 = resources.push(new AutoCloseableImpl4());
  //...
  return "result";
});
```

#### `repeat` methods

Performs given function block specified number of times (with additional values from 0 to 3) and returns accumulator
value if specified.

* `repeat(int, ThRunnable)`
* `repeat(int, ThConsumerInt)`
* `repeat(int, Object, ThConsumer)`
* `repeat(int, Object, Th2ConsumerIntObj)`
* `repeat1(int, Object, ThConsumer)`
* `repeat1(int, Object, Th2ConsumerIntObj)`
* `repeat1(int, Object, Object, Th2Consumer)`
* `repeat1(int, Object, Object, Th3ConsumerIntObj2)`
* `repeat2(int, Object, Object, Th2Consumer)`
* `repeat2(int, Object, Object, Th3ConsumerIntObj2)`
* `repeat2(int, Object, Object, Object, Th3Consumer)`
* `repeat2(int, Object, Object, Object, Th4ConsumerIntObj3)`
* `repeat3(int, Object, Object, Object, Th3Consumer)`
* `repeat3(int, Object, Object, Object, Th4ConsumerIntObj3)`
* `repeat3(int, Object, Object, Object, Object, Th4Consumer)`
* `repeat3(int, Object, Object, Object, Object, Th5ConsumerIntObj4)`

```
repeat(10, () -> {
  System.out.println("Hello");
});

repeat(10, idx -> {
  System.out.println("Hello " + idx);
});

List<String> result = repeat(10, new ArrayList<>(), list -> {
  list.add("Hello");
});

List<String> result = repeat(10, new ArrayList<>(), (idx, list) -> {
  list.add("Hello " + idx);
});
```

#### `iterateOver` methods

Performs given function block for every element of given object and returns accumulator value if specified.

* `iterateOver(Object[], ThConsumer)`
* `iterateOver(Object[], Th2ConsumerIntObj)`
* `iterateOver(Object[], Object, Th2Consumer)`
* `iterateOver(Object[], Object, Th3ConsumerIntObj2)`
* `iterateOver(Iterable, ThConsumer)`
* `iterateOver(Iterable, Th2ConsumerIntObj)`
* `iterateOver(Iterable, Object, Th2Consumer)`
* `iterateOver(Iterable, Object, Th3ConsumerIntObj2)`
* `iterateOver(Iterator, ThConsumer)`
* `iterateOver(Iterator, Th2ConsumerIntObj)`
* `iterateOver(Iterator, Object, Th2Consumer)`
* `iterateOver(Iterator, Object, Th3ConsumerIntObj2)`
* `iterateOver(Map, Th2Consumer)`
* `iterateOver(Map, Th3ConsumerIntObj2)`
* `iterateOver(Map, Object, Th3Consumer)`
* `iterateOver(Map, Object, Th4ConsumerIntObj3)`

```
List<String> iterable = List.of("a", "b", "c");

iterateOver(iterable, element -> {
  System.out.println("element: " + element);
});

iterateOver(iterable, (idx, element) -> {
  System.out.println("element " + idx + ": " + element);
});

List<String> result1 = iterateOver(iterable, new ArrayList<>(), (element, acc) -> {
  acc.add(element);
});

List<String> result2 = iterateOver(iterable, new ArrayList<>(), (idx, element, acc) -> {
  acc.add(idx + " " + element);
});

Map<Integer, String> result3 = iterateOver(list, new HashMap<>(), (idx, element, map) ->
  map.put(idx, element)
);
```

#### `iterate` methods

Performs given function block with manual selection of the next elements and returns accumulator value if specified.

* `iterate1(Object, Th2Consumer)`
* `iterate1(Object, Th3ConsumerIntObj2)`
* `iterate1(Object, Object, Th3Consumer)`
* `iterate1(Object, Object, Th4ConsumerIntObj3)`
* `iterate2(Object, Object, Th3Consumer)`
* `iterate2(Object, Object, Th4ConsumerIntObj3)`
* `iterate2(Object, Object, Object, Th4Consumer)`
* `iterate2(Object, Object, Object, Th5ConsumerIntObj4)`
* `iterate3(Object, Object, Object, Th4Consumer)`
* `iterate3(Object, Object, Object, Th5ConsumerIntObj4)`
* `iterate3(Object, Object, Object, Object, Th5Consumer)`
* `iterate3(Object, Object, Object, Object, Th6ConsumerIntObj5)`

```
iterate1(5, (value, nextValues) -> {
  if (value < 10) {
    System.out.println(value);
    nextValues.push(value + 1);
  }
});

iterate1(5, (idx, value, nextValues) -> {
  if (idx < 100 && value < 10) {
    System.out.println(value);
    nextValues.push(value + 1);
  }
});

List<Integer> result1 = iterate1(5, new ArrayList<>(), (value, acc, nextValues) -> {
  if (value < 10) {
    acc.add(value);
    nextValues.push(value + 1);
  }
});

List<Integer> result2 = iterate1(5, new ArrayList<>(), (idx, value, acc, nextValues) -> {
  if (idx < 100 && value < 10) {
    acc.add(value);
    nextValues.push(value + 1);
  }
});
```

#### `recur` methods

Performs given function block recursively and returns block result or accumulator value if specified.

* `recur(ThConsumer)`
* `recur(Th2Consumer)`
* `recur(Object, Th2Consumer)`
* `recur(Object, Th3Consumer)`
* `recur1(Object, Th2Consumer)`
* `recur1(Object, Th3Consumer)`
* `recur1(Object, Object, Th3Consumer)`
* `recur1(Object, Object, Th4Consumer)`
* `recur2(Object, Object, Th3Consumer)`
* `recur2(Object, Object, Th4Consumer)`
* `recur2(Object, Object, Object, Th4Consumer)`
* `recur2(Object, Object, Object, Th5Consumer)`
* `recur3(Object, Object, Object, Th4Consumer)`
* `recur3(Object, Object, Object, Th5Consumer)`
* `recur3(Object, Object, Object, Object, Th5Consumer)`
* `recur3(Object, Object, Object, Object, Th6Consumer)`
* `recur(ThFunction)`
* `recur(Th2Function)`
* `recur1(Object, Th2Function)`
* `recur1(Object, Th3Function)`
* `recur2(Object, Object, Th3Function)`
* `recur2(Object, Object, Th4Function)`
* `recur3(Object, Object, Object, Th4Function)`
* `recur3(Object, Object, Object, Th5Function)`

```
recur1(5, (value, self) -> {
  if (value < 10) {
    System.out.println(value);
    self.accept(value + 1);
  }
});

recur1(5, (depth, value, self) -> {
  if (depth.current() < 100 && value < 10) {
    System.out.println(value);
    self.accept(value + 1);
  }
});

List<Integer> result1 = recur1(5, new ArrayList<>(), (value, acc, self) -> {
  if (value < 10) {
    acc.add(value);
    self.accept(value + 1);
  }
});

List<Integer> result2 = recur1(5, new ArrayList<>(), (depth, value, acc, self) -> {
  if (depth.current() < 50 && value < 10) {
    acc.add(value);
    self.accept(value + 1);
  }
});

Integer result3 = recur1(5, (value, self) -> {
  if (value < 10) {
    return self.apply(value + 1);
  }
  return value;
});

Integer result4 = recur1(5, (depth, value, self) -> {
  if (depth.current() < 100 && value < 10) {
    return self.apply(value + 1);
  }
  return value;
});
```

#### `lazy` methods

Returns a value with lazy initialization.

* `lazy(ThSupplier)`
* `lazy(Object, ThSupplier)`
* `lazy(Lazy.ThreadSafetyMode, ThSupplier)`
* `lazyOf(Object)`

```
Lazy<String> lazyValue1 = lazy(() -> {
  //...
  return "abc";
});

Object externalLock = new Object();
Lazy<String> lazyValue2 = lazy(externalLock, () -> {
  //...
  return "abc";
});

Lazy<String> lazyValue3 = lazy(ThreadSafetyMode.PUBLICATION, () -> {
  //...
  return "abc";
});

Lazy<String> lazyValue4 = lazyOf("abc");
```

### Unchecked functions

All presented functions allow you to not catch any checked exceptions.

```
public static void main(String[] args) {
  URI uri = it(() -> new URI("abc"));
}
```

### Examples

#### Collection initialization

```
Map<String, Integer> map = it(new HashMap<>(), it -> {
  it.put("val1", 1);
  it.put("val2", 2);
});

List<String> list = it(new ArrayList<>(), it -> {
  it.add("val1");
  it.add("val2");
});
```

#### Argument in a method chain

```
new MyBuilder()
  .setFirst("first")
  .setSecond("second")
  .setThird(it(() -> {
    //...
    return "third";
  }))
  .setFourth("fourth")
  .build()
```

#### Nth Fibonacci number

```
int result = recur1(10, (n, func) -> {
  if (n <= 1) {
    return 1;
  } else {
    return n * func.apply(n - 1);
  }
});
```

#### Get all related exceptions via recursion

```
Throwable mainException = ...;
Set<Throwable> allRelated = recur1(mainException, new HashSet<>(), (currentEx, set, self) -> {
  if (currentEx != null && set.add(currentEx)) {
    self.accept(currentEx.getCause());
    for (final Throwable suppressedEx : currentEx.getSuppressed()) {
      self.accept(suppressedEx);
    }
  }
});
```

#### Get all related exceptions via iteration

```
Throwable mainException = ...;
Set<Throwable> allRelated = iterate1(mainException, new HashSet<>(), (currentEx, set, nextValues) -> {
  if (currentEx != null && set.add(currentEx)) {
    nextValues.push(currentEx.getCause());
    for (final Throwable suppressedEx : currentEx.getSuppressed()) {
      nextValues.push(suppressedEx);
    }
  }
});
```
