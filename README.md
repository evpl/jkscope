# JKScope

Java scope functions in the Kotlin style

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.plugatar.jkscope/jkscope/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.plugatar.jkscope/jkscope)
[![Javadoc](https://javadoc.io/badge2/com.plugatar.jkscope/jkscope/javadoc.svg)](https://javadoc.io/doc/com.plugatar.jkscope/jkscope)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

## Table of Contents

* [How to use](#How-to-use)
* [Example](#Examples)

## How to use

Requires Java 8+ version.

Maven:

```xml

<dependency>
  <groupId>com.plugatar.jkscope</groupId>
  <artifactId>jkscope</artifactId>
  <version>1.0</version>
</dependency>
```

Gradle:

```groovy
dependencies {
    implementation 'com.plugatar.jkscope:jkscope:1.0'
}
```

## Examples

### Static methods

```java
import static com.plugatar.jkscope.JKScope.let;
import static com.plugatar.jkscope.JKScope.run;
import static com.plugatar.jkscope.JKScope.with;
import static com.plugatar.jkscope.JKScope.withNonNull;

class ExampleTest {

  @Test
  void letMethod() {
    let(() -> {
      System.out.println("ok");
    });
  }

  @Test
  void runMethod() {
    int result = run(() -> {
      System.out.println("ok");
      return 12;
    });
  }

  @Test
  void withMethod() {
    String value1 = "a";
    String value2 = "b";
    String value3 = "c";

    with(value2, it -> {
      System.out.println(it);
    });

    String result = with(value1, value2, (it1, it2) -> it1 + it2);

    with(value1, value2, value3, (it1, it2, it3) -> {
      System.out.println(it1 + it2 + it3);
    });

    with(value2).takeNonNull().takeIf(it -> it.length() > 3).let(it -> System.out.println(it));
  }

  @Test
  void withNonNullMethod() {
    String value1 = "a";
    String value2 = null;
    String value3 = "c";

    withNonNull(value2, it -> {
      System.out.println(it);
    });

    withNonNull(value1, value2, (it1, it2) -> it1 + it2).let(it -> System.out.println(it));

    withNonNull(value1, value2, value3, (it1, it2, it3) -> {
      System.out.println(it1 + it2 + it3);
    });

    withNonNull(value2).takeNonNull().takeUnless(it -> it.length() < 3).let(it -> System.out.println(it));
  }
}
```

### Instance methods

```java
public class MyClass implements JKScope<MyClass> {

  public MyClass() {
  }

  public void method1() {
  }

  public String method2() {
    return "abc";
  }
}

class ExampleTest {

  @Test
  void instance() {
    MyClass myClass = new MyClass().also(it -> it.method1());

    myClass.apply(it -> it.method1()).apply(it -> it.method2());

    myClass.let(it -> {
      it.method1();
      it.method2();
    });

    String result = myClass.run(it -> it.method2());

    myClass.takeIf(it -> it.method2().length() > 3).let(it -> {
      System.out.println("ok");
    });
  }
}
```
