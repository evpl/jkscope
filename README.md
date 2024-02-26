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
  * [JKScope interface methods](#jkscope-interface-methods)
    * [`letIt` and `also`](#letit-and-also)
    * [`takeIf` and `takeUnless`](#takeif-and-takeunless)
    * [`letOut`](#letout)
    * [`letOpt`](#letopt)
  * [JKScope static methods](#jkscope-static-methods)
    * [`run`, `runCatching` and `runRec`](#run-runcatching-and-runrec)
    * [`with`, `withInt`, `withLong`, `withDouble` and `withResource`](#with-withint-withlong-withdouble-and-withresource)
    * [`let` variations](#let-variations)
  * [`Opt` monad](#opt-monad)
  * [Unchecked functions](#unchecked-functions)
  * [Examples](#examples)
    * [Collection initialization](#collection-initialization)
    * [Argument in a method chain](#argument-in-a-method-chain)
    * [Nth Fibonacci number](#nth-fibonacci-number)
    * [Method argument processing](#method-argument-processing)
    * [Safe resources](#safe-resources)

## Motivation

Inspired by the [Kotlin scope function](https://kotlinlang.org/docs/scope-functions.html) I want to make my Java code
more structured and readable.

## How to use

Java 8+ version required. The library has no dependencies. All you need is this (get the latest
version [here](https://github.com/evpl/jkscope/releases)).

Maven:

```xml

<dependency>
  <groupId>com.plugatar.jkscope</groupId>
  <artifactId>jkscope</artifactId>
  <version>2.2</version>
  <scope>compile</scope>
</dependency>
```

Gradle:

```groovy
dependencies {
    implementation 'com.plugatar.jkscope:jkscope:2.2'
}
```

## Docs

### JKScope interface methods

You need to implement `JKScope` interface to use these methods.

```
class MyObject implements JKScope<MyObject> { }
```

#### `letIt` and `also`

Both methods are the same and differ in the name only. Methods perform the function block on this object and return this
object.

```
MyDTO myDTO = new MyDTO().letIt(it -> {
  it.setProperty("value");
  it.setAnother("another value");
});

MyResource myResource = new MyResource().also(it -> it.init());
```

#### `takeIf` and `takeUnless`

`takeIf` method performs the function block on this object and returns `Opt` monad of this object if the condition is
met, or it returns empty `Opt` instance if the condition is not met. And `takeUnless` method has reverse logic.

```
new MyObject().takeIf(it -> it.getInt() > 10).takeUnless(it -> it.getInt() > 20).letIt(it -> System.out.println(it));
```

#### `letOut`

`letOut` method performs given function block on this object and returns result.

```
Integer value = new MyObject().letOut(it -> it.getInt());
```

#### `letOpt`

`letOpt` method performs given function block on this object and returns `Opt` monad of result.

```
new MyObject().letOpt(it -> it.getInt()).takeIf(it -> it > 10).letIt(it -> System.out.println(it));
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

#### `with`, `withInt`, `withLong`, `withDouble` and `withResource`

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

`withResource` method does the same thing, but with a `AutoCloseable` resource and closes this resource.

#### `let` variations

`let` returns `Opt` instance of given value, `letNonNull` returns `Opt` instance of given value of given value or
empty `Opt` instance if given value is null.

```
let(value).takeNonNull().takeUnless(it -> it.isEmpty()).takeIf(it -> it.length() < 100).letIt(it -> System.out.println(it));

letNonNull(value).takeUnless(it -> it.isEmpty()).takeIf(it -> it.length() < 100).letIt(it -> System.out.println(it));
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

`letWith`, `letIntWith`, `letLongWith`, `letDoubleWith` methods accept values and returning the result of function
block.

```
int value = letWith("42", it -> Integer.valueOf(it));
```

`letWithResource` method does the same thing, but with a `AutoCloseable` resource and closes this resource.

### `Opt` monad

The `Opt` monad is similar in meaning to Java `Optional`, but allows the null value.

`Opt` monad contains some `Optional` methods and scope functions methods:

```
String result = Opt.of(value).takeIf(it -> it.length() > 10).orElse("");

String result = Opt.of(value).takeNonNull().orElseGet(() -> "");

String result = Opt.of(value).takeIf(it -> it.length() > 10).orElseThrow(() -> new IllegalArgumentException());
```

### Unchecked functions

All presented functions allow you to not process checked exceptions.

```
public static void main(String[] args) {
  URI uri = let(() -> new URI("abc"));
}
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

#### Argument in a method chain

```
new MyBuilder()
  .setFirst("first")
  .setSecond("second")
  .setThird(let(() -> {
    //...
    return "third";
  }))
  .setFourth("fourth")
  .build()
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

#### Method argument processing

```
public static String checkNonNullNonEmptyStr(String value) {
  return let(value)
    .takeNonNull().throwIfEmpty(NullPointerException::new)
    .takeUnless(String::isEmpty).throwIfEmpty(IllegalArgumentException::new)
    .get();
}
```

#### Safe resources

```
class MyResource implements AutoCloseable {
  //...
}

withResource(new MyResource(), it -> {
  //...
});
```
