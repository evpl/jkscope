# JKScope

![Last version](https://img.shields.io/badge/last_version-2.0-blue "Last version")
[![Maven Central](https://img.shields.io/maven-central/v/com.plugatar.jkscope/jkscope)](https://central.sonatype.com/artifact/com.plugatar.jkscope/jkscope)
[![Javadoc](https://javadoc.io/badge2/com.plugatar.jkscope/jkscope/javadoc.svg)](https://javadoc.io/doc/com.plugatar.jkscope/jkscope)
![GitHub Actions Workflow Status](https://img.shields.io/github/actions/workflow/status/evpl/jkscope/tests.yml?branch=main)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

Java scope functions inspired by Kotlin

## Table of Contents

* [Motivation](#motivation)
* [How to use](#how-to-use)
* [Docs](#docs)
  * [JKScope interface methods](#jkscope-interface-methods)
    * [`let` and `also`](#let-and-also)
    * [`takeIf` and `takeUnless`](#takeif-and-takeunless)
    * [`letOut`](#letout)
    * [`letOpt`](#letopt)
  * [JKScope static methods](#jkscope-static-methods)
    * [`run`, `runCatching` and `runRec`](#run-runcatching-and-runrec)
    * [`with`, `withInt`, `withLong` and `withDouble`](#with-withint-withlong-and-withdouble)
    * [`let` variations](#let-variations)
  * [`Opt` monad](#opt-monad)
  * [Examples](#examples)
    * [Collection initialization](#collection-initialization)
    * [Nth Fibonacci Number](#nth-fibonacci-number)

## Motivation

Inspired by the [Kotlin scope function](https://kotlinlang.org/docs/scope-functions.html) I want to reduce the number of
lines of my Java code and make this code more readable.

This library should have been written at least for this feature alone 😄

```
Map<String, Integer> map = let(new HashMap<>(), it -> {
  it.put("val1", 1);
  it.put("val2", 2);
});
```

It is also worth noting that all presented functions allow you to not process checked exceptions.

```
public static void main(String[] args) {
  URI uri = let(() -> new URI("abc"));
}
```

## How to use

Java 1.8+ version required. The library has no dependencies. All you need is this (get the latest
version [here](https://github.com/evpl/jkscope/releases)).

Maven:

```xml

<dependency>
  <groupId>com.plugatar.jkscope</groupId>
  <artifactId>jkscope</artifactId>
  <version>2.0</version>
</dependency>
```

Gradle:

```groovy
dependencies {
    implementation 'com.plugatar.jkscope:jkscope:2.0'
}
```

## Docs

### JKScope interface methods

You need to implement `JKScope` interface to use these methods.

```
class MyObject implements JKScope<MyObject> { }
```

#### `let` and `also`

Both methods are the same and differ in the name only. Methods perform the function block on this object and return this
object.

```
MyDTO myDTO = new MyDTO().let(it -> {
  it.setProperty("value");
  it.setAnother("another value");
});

MyResource myResource = new MyResource().also(it -> it.init());
```

#### `takeIf` and `takeUnless`

`takeIf` method performs the function block on this object and returns `Opt` monad of this object if the condition is
met, or it returns empty `Opt` instance if the condition is not met. And `takeUnless` method has reverse logic.

```
new MyObject().takeIf(it -> it.getInt() > 10).takeUnless(it -> it.getInt() > 20).let(it -> System.out.println(it));
```

#### `letOut`

`takeIf` method performs given function block on this object and returns result.

```
Integer value = new MyObject().letOut(it -> it.getInt());
```

#### `letOpt`

`takeIf` method performs given function block on this object and returns `Opt` monad of result.

```
new MyObject().letOpt(it -> it.getInt()).takeIf(it -> it > 10).let(it -> System.out.println(it));
```

### JKScope static methods

Import static methods you need or import them all at once.

```
import static com.plugatar.jkscope.JKScope.*;
```

#### `run`, `runCatching` and `runRec`

`run` just runs given function block, `runCatching` runs ignore any Throwable, `runRec` runs function block allowing
yourself to be called recursively.

`run` method simply runs given function block, `runCatching` runs ignore any thrown Throwable, `runRec` runs function
block, allowing itself to be called recursively.

```
run(() -> {
  System.out.println("Hi");
});

runCatching(() -> {
  System.out.println("Hi");
});

runRec(func -> {
  if (new Random().nextInt(0, 100) == 50) {
    func.run();
  }
});
```

#### `with`, `withInt`, `withLong` and `withDouble`

These methods perform given function block on given values.

```
with(value, it -> {
  System.out.println(value);
});

with(value1, value2, (v1, v2) -> {
  System.out.println(v1);
  System.out.println(v2);
});
```

#### `let` variations

`let` returns `Opt` instance of given value, `letNonNull` returns `Opt` instance of given value of given value or
empty `Opt` instance if given value is null.

```
let(value).takeNonNull().takeUnless(it -> it.isEmpty()).takeIf(it -> it.length() < 100).let(it -> System.out.println(it));

letNonNull(value).takeUnless(it -> it.isEmpty()).takeIf(it -> it.length() < 100).let(it -> System.out.println(it));
```

`let`, `letInt`, `letLong` and `letDouble` returns result of function block.

```
String value = let(() -> {
  //...
  return "val";
});
```

`let`, `letInt`, `letLong` and `letDouble` methods can also receive a value, process it using a function block, and
return that value.

```
String value = let("val", it -> {
  System.out.println(it);
});
```

`letRec`, `letIntRec`, `letLongRec` and `letDoubleRec` accept initial value and allow you to process it recursively
returning the result.

```
int value = letIntRec(10, (n, func) -> {
  if (n < 2) {
    return n;
  }
  return func.apply(n - 1) + func.apply(n - 2);
});
```

`letWith` methods accept values and returning the result of function block.

```
int value = letWith("42", it -> Integer.valueOf(it));
```

### `Opt` monad

The `Opt` monad is similar in meaning to Java `Optional`, but allows the null value.

`Opt` monad contains standard `Optional` methods and scope functions methods:

```
String result = Opt.of("value").takeIf(it -> it.length() > 10).orElse("");

String result = Opt.of("value").takeIf(it -> it.length() > 10).orElseGet(() -> "");

String result = Opt.of("value").takeIf(it -> it.length() > 10).orElseThrow(() -> new IllegalArgumentException());
```

### Examples

#### Collection initialization

```
Map<String, Integer> map = let(new HashMap<>(), it -> {
  it.put("val1", 1);
  it.put("val2", 2);
});

List<String> list = let(new ArrayList<>(), it -> {
  it.add("val1");
  it.add("val2");
});
```

#### Nth Fibonacci number

```
int value = letIntRec(10, (n, func) -> {
  if (n < 2) {
    return n;
  }
  return func.apply(n - 1) + func.apply(n - 2);
});
```

